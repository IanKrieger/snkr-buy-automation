package com.example.java_buy_bot;

import com.example.java_buy_bot.service.BuyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.File;

@SpringBootApplication
public class BuyBotApplication implements CommandLineRunner {

    final BuyService buyService;

    public BuyBotApplication(BuyService buyService) {
        this.buyService = buyService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BuyBotApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final File file = ResourceUtils.getFile("src/main/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        buyService.buyShoe();
    }
}

