package com.tbland.whale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WhaleApplication {

	public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(encoder.encode("111111"));
		System.out.println(encoder.encode("222222"));
		System.out.println(encoder.encode("333333"));
		System.out.println(encoder.encode("444444"));
		SpringApplication.run(WhaleApplication.class, args);
	}

}
