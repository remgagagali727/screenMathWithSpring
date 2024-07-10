package com.remproyects.screenmatch;

import com.remproyects.screenmatch.main.Main;
import com.remproyects.screenmatch.repository.EpisodeRepository;
import com.remproyects.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
	@Autowired
	private SerieRepository serieRepository;
	@Autowired
	private EpisodeRepository episodeRepository;

	@Override
	public void run(String... args) throws Exception {
		new Main(serieRepository, episodeRepository);

	}

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);

	}

}
