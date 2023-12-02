package org.simonscode;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class SteamKeyActivator {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "./geckodriver");

        WebDriver driver = new FirefoxDriver();

        List<String> lines = Files.readAllLines(new File("./keys.txt").toPath());

        driver.get("https://store.steampowered.com/account/registerkey");

        Duration timeout = Duration.ofSeconds(300);
        for (String line : lines) {
            String[] parts = line.split(" ");
            String key = parts[0];
            try {
                WebElement textbox = new WebDriverWait(driver, timeout)
                        .until(elementToBeClickable(xpath("//*[@id=\"product_key\"]")));
                textbox.clear();
                textbox.sendKeys(key);
                Thread.sleep(200);

                WebElement agreeBox = new WebDriverWait(driver, timeout)
                        .until(elementToBeClickable(xpath("//*[@id=\"accept_ssa\"]")));
                if (!agreeBox.isSelected()){
                    agreeBox.click();
                }
                Thread.sleep(200);

                WebElement confirmKey = new WebDriverWait(driver, timeout)
                        .until(elementToBeClickable(xpath("//*[@id=\"register_btn\"]")));
                confirmKey.click();
                Thread.sleep(2000);

                WebElement activateMoreButton = new WebDriverWait(driver, timeout)
                        .until(elementToBeClickable(xpath("/html/body/div[1]/div[7]/div[6]/div[3]/div[2]/div/div[1]/div[1]/div[2]/div[3]/a[1]")));
                activateMoreButton.click();
                Thread.sleep(200);
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        driver.quit();
    }
}
