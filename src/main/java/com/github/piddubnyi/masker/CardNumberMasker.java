package com.github.piddubnyi.masker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CardNumberMasker {

    public static void main(String[] args) {
        SpringApplication.run(CardNumberMasker.class, args);
    }

}
