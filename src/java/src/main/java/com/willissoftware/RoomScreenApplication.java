package com.willissoftware;

import com.willissoftware.service.PhotoLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoomScreenApplication {

	@Autowired
	//PhotoLibrary photolibrary;

	public static void main(String[] args) {
		SpringApplication.run(RoomScreenApplication.class, args);

		//photol

	}
}
