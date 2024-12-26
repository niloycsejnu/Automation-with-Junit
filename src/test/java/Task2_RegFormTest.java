import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertTrue;

public class Task2_RegFormTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500);");
    }

    @Test
    public void testRegistrationForm() throws InterruptedException {

        driver.get("https://demo.wpeverest.com/user-registration/guest-registration-form/");
        driver.findElement(By.id("first_name")).sendKeys("Niloy"); // First Name
        driver.findElement(By.id("last_name")).sendKeys("Datta"); // Last Name
        Random rand = new Random();
        String uniqueEmail = "niloydatta" + rand.nextInt(1000000) + "@example.com";
        driver.findElement(By.id("user_email")).sendKeys(uniqueEmail); // Email
        driver.findElement(By.id("radio_1665627729_Male")).click();
        driver.findElement(By.id("user_pass")).sendKeys("nil@data!12");

        List<WebElement> dateOfBirthField = driver.findElements(By.cssSelector("[type=text]"));
        WebElement dateOfBirth = dateOfBirthField.get(2);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value', '2000-11-18')", dateOfBirth);

        driver.findElement(By.id("input_box_1665629217")).sendKeys("Bangladeshi");

        List<WebElement> phoneNumber = driver.findElements(By.id("phone_1665627880"));
        phoneNumber.get(1).sendKeys("0123456789");
        Utils.scroll(driver, 1200);
        WebElement countryDropdown = driver.findElement(By.id("country_1665629257"));
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText("Bangladesh");

        WebElement termsCheckbox = driver.findElement(By.id("privacy_policy_1665633140"));
        if (!termsCheckbox.isSelected()) {
            termsCheckbox.click();
        }

        WebElement submitButton = driver.findElement(By.xpath("//button[contains(@class, 'ur-submit-button')]"));
        submitButton.click();

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ur-submit-message-node")));
        assertTrue(successMessage.isDisplayed());
        assertTrue(successMessage.getText().contains("User successfully registered."));
    }

    @After
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}