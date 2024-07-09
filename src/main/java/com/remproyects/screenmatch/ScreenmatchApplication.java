package com.remproyects.screenmatch;

import com.remproyects.screenmatch.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		new Main();

	}

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);

	}

}
