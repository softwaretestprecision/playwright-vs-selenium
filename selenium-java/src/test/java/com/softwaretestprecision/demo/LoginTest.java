package com.softwaretestprecision.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Map;

/**
 * The same two tests as ../playwright/tests/login.spec.ts, written in Selenium 4 + TestNG.
 * Selenium Manager (built into Selenium 4.6+) downloads the matching ChromeDriver automatically.
 */
public class LoginTest {

    private static final String LOGIN_URL = "https://practicetestautomation.com/practice-test-login/";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        if (!"false".equals(System.getProperty("headless"))) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1280,800", "--no-sandbox", "--disable-dev-shm-usage");
        // Turn off Chrome's password manager. Its "password found in a data breach" dialog can
        // appear after a login and silently block clicks. Selenium can't see browser dialogs.
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false));
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(LOGIN_URL);
    }

    @Test
    public void validCredentialsLogTheUserIn() {
        driver.findElement(By.id("username")).sendKeys("student");
        driver.findElement(By.id("password")).sendKeys("Password123");
        driver.findElement(By.id("submit")).click();

        // Selenium does not auto-wait on assertions, so we wait explicitly.
        wait.until(ExpectedConditions.urlContains("logged-in-successfully"));
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        Assert.assertEquals(heading.getText(), "Logged In Successfully");
        Assert.assertTrue(driver.findElement(By.linkText("Log out")).isDisplayed());
    }

    @Test
    public void invalidUsernameShowsAnError() {
        driver.findElement(By.id("username")).sendKeys("incorrectUser");
        driver.findElement(By.id("password")).sendKeys("Password123");
        driver.findElement(By.id("submit")).click();

        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        Assert.assertEquals(error.getText(), "Your username is invalid!");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
