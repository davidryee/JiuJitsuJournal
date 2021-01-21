package com.David.JiuJitsuJournal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.David.JiuJitsuJournal")
@SpringBootApplication
public class JiuJitsuJournalApplication {
	public static void main(String[] args) {
		SpringApplication.run(JiuJitsuJournalApplication.class, args);
	}
}
