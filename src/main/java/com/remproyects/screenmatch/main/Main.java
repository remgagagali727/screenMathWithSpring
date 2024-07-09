package com.remproyects.screenmatch.main;

import com.remproyects.screenmatch.data.JsonTransformer;
import com.remproyects.screenmatch.models.data_files.EpisodeData;
import com.remproyects.screenmatch.models.data_files.SeasonData;
import com.remproyects.screenmatch.models.data_files.SerieData;
import com.remproyects.screenmatch.models.files.Episode;
import com.remproyects.screenmatch.online.ApiUsage;
import com.remproyects.screenmatch.online.util.Keys;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private final Scanner scanner = new Scanner(System.in);

    public Main() throws URISyntaxException {
        ApiUsage apiUsage = new ApiUsage();

        String name = showMenu("Escribe el nombre de la serie que deaseas buscar").replace(' ', '+');

        String json = apiUsage.getData(
                "https://www.omdbapi.com/?apikey=" + Keys.ombdKey + "&t=" + name
        );

        SerieData series = new JsonTransformer().fromJson(json, SerieData.class);

        List<SeasonData> seasons = new ArrayList<>();

        System.out.println("Total seasons = " + series.totalSeasons());

        for(int i = 0;i < series.totalSeasons();i++) {
            String serieJson = apiUsage.getData(
                    "https://www.omdbapi.com/?apikey=" + Keys.ombdKey + "&t="
                            + name
                            + "&season=" + (i + 1)
            );
            SeasonData sData = new JsonTransformer().fromJson(serieJson, SeasonData.class);
            seasons.add(sData);
        }

        //mostrar solo el titulo de los episodios para las temporadas
        //for(SeasonData seasonData : seasons) {
        //    for(EpisodeData episodeData : seasonData.episodes()) {
        //        System.out.println("Temporada: " + seasonData.season() + " Episodio: " + episodeData.title());
        //    }
        //}

        List<EpisodeData> episodes = seasons.stream()
                        .flatMap(E -> E.episodes().stream())
                        .collect(Collectors.toList());

        System.out.println("Los mejores 5 capitulos de todos los tiempos");

        episodes.stream()
                .filter(E -> !E.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .map(E -> E.title().toUpperCase())
                .limit(5)
                .forEach(System.out::println);


        List<Episode> episodesClass = seasons.stream()
                .flatMap(S -> S.episodes().stream()
                        .map(E -> new Episode(S.season(),E)))
                .collect(Collectors.toList());

        //episodesClass.forEach(System.out::println);

        //Busqueda de episodios a partir de X año
        //Integer date = Integer.valueOf(showMenu("Indica el año apartir el cual deseas ver episodios"));

        //LocalDate searchDate = LocalDate.of(date, 1, 1);

        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");

        //episodesClass.stream()
                //.filter(E -> E.getRelease() != null && E.getRelease().isAfter(searchDate))
                //.forEach(E -> {
                    //System.out.println("Temporada: " + E.getSeason()
                            //+ " Episodio " + E.getEpisode()
                            //+ " Fecha Lanzamiento " + E.getRelease());
                //});

        //Buscar episodios por pedaso de titulo
//        String search = showMenu("Dame el chacho o titulo completo para buscar");
//
//        Optional<Episode> episode = episodesClass.stream()
//                .filter(E -> E.getTitle().toLowerCase().contains(search.toLowerCase()))
//                .findFirst();
//
//        if(episode.isPresent()) {
//            System.out.println(episode.get());
//        } else {
//            System.out.println("No se encontro un episodio");
//        }

        Map<Integer, Double> map = episodesClass.stream()
                .filter(E -> E.getRating() > 0d)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getRating)));

        System.out.println(map);

        DoubleSummaryStatistics statistics = episodesClass.stream()
                .filter(E -> E.getRating() > 0d)
                .collect(Collectors.summarizingDouble(Episode::getRating));

        System.out.println("La media de las evaluaciones es " + statistics.getAverage());
        System.out.println("El episodio mejor evaluado tiene " + statistics.getMax());
    }

    public String showMenu(String s) {
        System.out.println(s);
        return scanner.nextLine();
    }
}
