package com.remproyects.screenmatch.main;

import com.remproyects.screenmatch.data.JsonTransformer;
import com.remproyects.screenmatch.models.data_files.SeasonData;
import com.remproyects.screenmatch.models.data_files.SerieData;
import com.remproyects.screenmatch.models.defined.Category;
import com.remproyects.screenmatch.models.files.Episode;
import com.remproyects.screenmatch.models.files.Serie;
import com.remproyects.screenmatch.online.ApiUsage;
import com.remproyects.screenmatch.online.util.Keys;
import com.remproyects.screenmatch.repository.EpisodeRepository;
import com.remproyects.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final ApiUsage apiUsage = new ApiUsage();
    private List<Serie> series;
    private EpisodeRepository episodeRepository;
    private SerieRepository serieRepository;

    public Main(SerieRepository serieRepository, EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
        this.serieRepository = serieRepository;
        core();
    }

    private void core() {

        char option = 0;

        while (option != '0') {
            option = showMenu("""
                    Elije una opcion
                    1. Buscar serie
                    2. Buscar episodio
                    3. Mostrar todas las series
                    4. Buscar series por titulo
                    5. ¡Top 5 series!
                    6. Buscar por categoria
                    7. Buscar con maximo temporadas y minima evaluacion
                    8. Buscar episodio por nombre
                    9. Top 5 Episodios de una serie
                    
                    0. Salir
                    """).toLowerCase().charAt(0);

            switch (option) {
                case '0':
                    break;
                case '1':
                    serie();
                    break;
                case '2':
                    episode();
                    break;
                case '3':
                    showSeries();
                    break;
                case '4':
                    searchSerieByTitle();
                    break;
                case '5':
                    topFiveSeries();
                    break;
                case '6':
                    searchSerieByGenre();
                    break;
                case '7':
                    searchBySeasonsAndRating();
                    break;
                case '8':
                    searchEpisodeByTitle();
                    break;
                case '9':
                    topFiveEpisodes();
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void topFiveEpisodes() {
        String serieName = showMenu("Dame el nombre de la serie");
        Optional<Serie> serie = serieRepository.findByTitleContainsIgnoreCase(serieName);
        if(serie.isPresent()) {
            List<Episode> topEpisodes = episodeRepository.findTop5BySerieOrderByRatingDesc(serie.get());
            topEpisodes.forEach(System.out::println);
        } else {
            System.out.println("Esa serie no existe");
        }
    }

    private void searchEpisodeByTitle() {
        String name = showMenu("Dame el nombre del capitulo");
        List<Episode> episodes = episodeRepository.findByTitleContainingIgnoreCase(name);
        episodes.forEach(System.out::println);
    }

    private void searchBySeasonsAndRating() {
        String qry = showMenu("""
                Dame las maximas temporadas y la calificacion minima
                Formato -> [(temporadas) (calificacion)]
                """);
        Integer seasons;
        Double rating;

        seasons = Integer.valueOf(qry.split(" ")[0]);
        rating = Double.valueOf(qry.split(" ")[1]);

        List<Serie> series = serieRepository.findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(seasons, rating);

        series.forEach(S ->
                System.out.println("Titulo: " + S.getTitle() + " Rating: " + S.getRating() + " Temporadas: " + S.getTotalSeasons()));

    }

    private void searchSerieByGenre() {
        String genre = showMenu("Escribe la categoria");
        List<Serie> series = serieRepository.findByGenre(Category.fromString(genre));
        series.forEach(System.out::println);
    }

    private void topFiveSeries() {
        List<Serie> topFive = serieRepository.findTop5ByOrderByRatingDesc();
        topFive.forEach(S -> System.out.println("Titulo: " + S.getTitle() + " Evaluacion: " + S.getRating()));
    }

    private void searchSerieByTitle() {
        String tittle = showMenu("Dame el titulo de la serie");
        Optional<Serie> found = serieRepository.findByTitleContainsIgnoreCase(tittle);

        if(found.isPresent()) {
            System.out.println("La serie buscada es");
            System.out.println(found.get());
        } else {
            System.out.println("No se ha podido encontrar la serie2");
        }
    }

    private void showSeries() {
        series = serieRepository.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getTitle))
                .forEach(System.out::println);
    }

    private void serie() {
        String serieName = showMenu("Dame el nombre de la serie");
        SerieData serieData = searchSerie(serieName);
        Serie serie = new Serie(serieData);
        serieRepository.save(serie);

    }

    private void episode() {
        showSeries();
        String serieName = showMenu("Dame el nombre de la serie");
        Optional<Serie> serieOptional = series.stream()
                .filter(N -> N.getTitle().toLowerCase().contains(serieName.toLowerCase()))
                .findFirst();

        if(serieOptional.isPresent()) {
            Serie found = serieOptional.get();

            List<SeasonData> seasons = new ArrayList<>();
            for(int i = 1;i <= found.getTotalSeasons();i++) {
                String json = apiUsage.getData("https://www.omdbapi.com/?apikey=" + Keys.ombdKey + "&t=" + serieName.replace(" ", "+") + "&season=" + i);
                seasons.add(new JsonTransformer().fromJson(json, SeasonData.class));
            }

            List<Episode> episodes = seasons.stream()
                    .flatMap(S -> S.episodes().stream()
                            .map(E -> new Episode(S.season(),E)))
                    .collect(Collectors.toList());

            found.setEpisodes(episodes);
            serieRepository.save(found);
        }


    }

    private SeasonData searchSeason(String name, String n) {
        return searchSeason(name,String.valueOf(n));
    }

    private SeasonData searchSeason(String name, int n) {
        String uri = "https://www.omdbapi.com/?apikey=" + Keys.ombdKey + "&t=" + name + "&season=" + n;
        String json = apiUsage.getData(uri.replace(" ", "+"));
        return new JsonTransformer().fromJson(json, SeasonData.class);
    }

    private SerieData searchSerie(String name) {
        String uri = "https://www.omdbapi.com/?apikey=" + Keys.ombdKey + "&t=" + name;
        String json = apiUsage.getData(uri.replace(" ", "+"));
        return new JsonTransformer().fromJson(json, SerieData.class);
    }

    public String showMenu(String s) {
        System.out.println(s);
        return scanner.nextLine();
    }
}
