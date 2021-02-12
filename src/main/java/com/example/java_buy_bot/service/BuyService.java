package com.example.java_buy_bot.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
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

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyService.class);
    private static final long WAIT = 100000L;

    public void buyShoe() throws InterruptedException {
        final WebDriver webDriver = ThreadGuard.protect(new ChromeDriver());
        webDriver.get(url);
        login(webDriver);
        selectSizeAndBuy(webDriver);
        putInThatCcv(webDriver);
        submitOrder(webDriver);
    }

    private void login(final WebDriver webDriver) {
        final WebElement logIn = new WebDriverWait(webDriver, WAIT)
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='button' and text()='Join / Log In']")));
        logIn.click();

        final WebElement emailInput = new WebDriverWait(webDriver,WAIT)
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("emailAddress")));
        // Login Modal has popped up
        emailInput.sendKeys(username);

        // can now input psswd
        final WebElement passwordInput = new WebDriverWait(webDriver,WAIT)
            .until(ExpectedConditions.presenceOfElementLocated(By.tagName("password")));
        passwordInput.sendKeys(password);

        final WebElement signIn = new WebDriverWait(webDriver, WAIT)
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='button' and @value='SIGN IN']")));

        // press login button
        signIn.click();
    }

    private void selectSizeAndBuy(final WebDriver webDriver) {
        final WebElement container = new WebDriverWait(webDriver, WAIT)
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@data-qa='size-dropdown' and contains(text(), 'M 10')]")));

        /* Find the size. We are waiting a Day to find thee size because this gives opportunity
        to start the app early in the morning, and not worry about it. */
        container.click();

        final WebElement buyButton = new WebDriverWait(webDriver, WAIT)
            .until(ExpectedConditions.presenceOfElementLocated((By.xpath("//button[@data-qa='feed-buy-cta']")));

        buyButton.click();
    }

    private void putInThatCcv(final WebDriver webDriver) {
        // At this point we are being asked to put in CCV (have CC in acct pls)
        final WebElement ccvModal = new WebDriverWait(webDriver, WAIT)
            .until(ExpectedConditions.presenceOfElementLocated((By.id("cvNumber")));

        ccvModal.sendKeys(ccv);

        // Save the CCV
        final WebElement save = new WebDriverWait(webDriver, WAIT)
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='button' and text()='Save & Continue']")));
        save.click();
    }

    private void submitOrder(final WebDriver webDriver) {
        // Thee big guy, submit the order
        final WebElement submitOrder = new WebDriverWait(webDriver, WAIT)
            .until(item -> item.findElement(By.ByXPath.xpath("//button[@type='button' and text()='Submit Order']")));

        if (buyShoe) {
            submitOrder.click();
        } else {
            LOGGER.info(submitOrder.getText());
        }
    }
}
