package com.gudmumic.spring.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class SpringRestApplication {

	public static void main(String[] args) {
        Properties environmentProps = new Properties();
        if (args.length > 0 && args[0].contains(".properties")) {
            try (FileReader reader = new FileReader(args[0])) {
                environmentProps.load(reader);
                environmentProps.forEach((key, value) -> System.setProperty(key.toString(), value.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SpringApplication.run(SpringRestApplication.class, args);
	}

}
