package hellocucumber.ui_steps_definition;

import hellocucumber.WebDriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HrmSteps {
    private final WebDriver driver = new WebDriverFactory().createWebDriver();
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("I am on the OrangeHRM login page")
    public void I_visit_hrm() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @When("I input {string} as {string}")
    public void input_text(String value, String locator) {
        By element = By.name(locator);
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(element)));
        driver.findElement(element).sendKeys(value);
    }

    @When("I click login button")
    public void click_login() {
        WebElement element = driver.findElement(By.xpath("//button[@type='submit']"));
        element.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("login")));
    }

    @Then("login success and profile dropdown should be displayed")
    public void check_profile_page() {
        wait.until(ExpectedConditions.urlContains("dashboard"));
        By element = By.xpath("//*[@class='oxd-userdropdown']");
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(element)));
    }

    @After()
    public void closeBrowser() {
        driver.quit();
    }
}