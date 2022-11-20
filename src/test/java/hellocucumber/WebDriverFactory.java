package hellocucumber;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory {

    public static WebDriver createWebDriver() throws MalformedURLException {
        String webDriver = System.getProperty("browser", "firefox");
        String isRemote = System.getProperty("remote", "false");

        DesiredCapabilities dc = new DesiredCapabilities();
        switch (webDriver) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--window-size=1920,1200");
                firefoxOptions.addArguments("--disable-gpu");
                firefoxOptions.addArguments("--disable-notifications");
                firefoxOptions.addArguments("--no-sandbox");
                firefoxOptions.addArguments("--disable-dev-shm-usage");
                firefoxOptions.setHeadless(true);

                if (isRemote.equals("true")) {
                    dc.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                    dc.setCapability("browserName", "firefox");
                    return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
                } else {
                    return new FirefoxDriver(firefoxOptions);
                }
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--window-size=1920,1200");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--headless");

                if (isRemote.equals("true")) {
                    dc.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    dc.setCapability("browserName", "chrome");
                    return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
                } else {
                    return new ChromeDriver(chromeOptions);
                }
            default:
                throw new RuntimeException("Unsupported webDriver: " + webDriver);
        }
    }
}