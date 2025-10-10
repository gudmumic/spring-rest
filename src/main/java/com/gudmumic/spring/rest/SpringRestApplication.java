package com.gudmumic.spring.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

@SpringBootApplication
public class SpringRestApplication {

	public static void main(String[] args) {
        Properties mysqlProps = new Properties();
        try (FileReader reader = new FileReader("./src/main/resources/application-docker-mysql.properties")) {
            mysqlProps.load(reader);
            mysqlProps.forEach((key, value) -> System.setProperty(key.toString(), value.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
		SpringApplication.run(SpringRestApplication.class, args);
	}

}
