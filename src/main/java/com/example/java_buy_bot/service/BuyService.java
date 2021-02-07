package com.example.java_buy_bot.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BuyService {

    @Value("${login.username}")
    private String username;

    @Value("${login.password}")
    private String password;

    @Value("${shoe.buy.url}")
    private String url;

    @Value("${credit.card.ccv}")
    private String ccv;

    @Value("${shoe.size}")
    private String size;

    @Value("${buy.shoe}")
    private boolean buyShoe;

    @Value("${threads}")
    private int threads;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void buyShoe() {
        for (int i = 0; i < threads; i ++) {
            executorService.submit(() -> {
                final WebDriver webDriver = ThreadGuard.protect(new ChromeDriver());
                webDriver.get(url);
                login(webDriver);
                selectSizeAndBuy(webDriver);
                putInThatCcv(webDriver);

                if (buyShoe) {
                    submitOrder(webDriver);
                }
            });
        }
    }

    private void login(final WebDriver webDriver) {

        webDriver.findElement(By.ByXPath.xpath("//button[@type='button' and text()='Join / Log In']")).click();

        final WebElement emailInput = new WebDriverWait(webDriver, Duration.ofSeconds(10).getSeconds())
                .until(ExpectedConditions.presenceOfElementLocated(By.ByTagName.name("emailAddress")));

        // Login Modal has popped up
        emailInput.sendKeys(username);

        // can now input psswd
        webDriver.findElement(By.ByName.name("password")).sendKeys(password);

        // press login button
        webDriver.findElement(By.ByXPath.xpath("//input[@type='button' and @value='SIGN IN']")).click();
    }

    private void selectSizeAndBuy(final WebDriver webDriver) {
        final String buttonElem = String.format("//button[@type='button' and contains(text(), '%s')]", size);
        final WebElement sizeOption = new WebDriverWait(webDriver, Duration.ofDays(1).toDays())
                .until(item -> item.findElement(By.ByXPath.xpath(buttonElem)));

        /* Find the size. We are waiting a Day to find thee size because this gives opportunity
        to start the app early in the morning, and not worry about it. */
        sizeOption.click();

        // Got our size, press the buy button
        webDriver.findElement(By.ByXPath.xpath("//button[@type='button' and contains(text(), 'Buy')]")).click();
    }

    private void putInThatCcv(final WebDriver webDriver) {
        // At this point we are being asked to put in CCV (have CC in acct pls)
        final WebElement ccvModal = new WebDriverWait(webDriver, Duration.ofHours(1).toHours())
                .until(item -> item.findElement(By.id("cvNumber")));

        ccvModal.sendKeys(ccv);

        // Save the CCV
        webDriver.findElement(By.ByXPath.xpath("//button[@type='button' and text()='Save & Continue']")).click();
    }

    private void submitOrder(final WebDriver webDriver) {
        // Thee big guy, submit the order
        final WebElement submitOrder = new WebDriverWait(webDriver, Duration.ofHours(1).toHours())
                .until(item -> item.findElement(By.ByXPath.xpath("//button[@type='button' and text()='Submit Order']")));

        submitOrder.click();
    }
}
