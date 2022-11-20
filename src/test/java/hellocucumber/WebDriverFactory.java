package hellocucumber;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {
    public static WebDriver createWebDriver() {
        String webDriver = System.getProperty("browser", "firefox");
        switch (webDriver) {
            case "firefox":
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary(firefoxBinary);
                firefoxOptions.setHeadless(true);
                return new FirefoxDriver(firefoxOptions);
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(
                        "--disable-gpu",
                        "--window-size=1920,1200",
                        "--ignore-certificate-errors",
                        "--disable-extensions",
                        "--no-sandbox",
                        "--disable-dev-shm-usage");
                chromeOptions.addArguments("--headless");
                return new ChromeDriver(chromeOptions);
            default:
                throw new RuntimeException("Unsupported webDriver: " + webDriver);
        }
    }
}