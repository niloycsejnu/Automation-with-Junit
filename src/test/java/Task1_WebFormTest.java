import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Task1_WebFormTest {

    WebDriver driver;
    Faker faker;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        faker = new Faker();
    }

    @Test
    @DisplayName("Automate Web Form Submission")
    public void automateWebForm() throws InterruptedException {
        driver.get("https://www.digitalunite.com/practice-webform-learners");
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
        Utils.scroll(driver, 300);

        List<WebElement> formFields = driver.findElements(By.className("form-control"));
        formFields.get(0).sendKeys(faker.name().fullName()); // Full Name
        formFields.get(1).sendKeys(Utils.generatePhoneNumber()); // Random Phone Number
        formFields.get(2).sendKeys(new SimpleDateFormat("MM/dd/yyyy").format(new Date())); // Today's Date
        formFields.get(3).sendKeys(faker.internet().emailAddress()); // Random Email
        formFields.get(4).sendKeys("Hello! I am Niloy Datta."); // Introduction

        Utils.scroll(driver, 200);

        WebElement uploadButton = driver.findElement(By.id("edit-uploadocument-upload"));
        String filePath = new File("src/test/resources/sqa.jpg").getAbsolutePath();
        uploadButton.sendKeys(filePath);
        Thread.sleep(3000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Remove']")));

        Utils.scroll(driver, 100);

        driver.findElement(By.id("edit-age")).click();
        Thread.sleep(1000);

        Utils.scroll(driver, 100);

        driver.findElement(By.id("edit-submit")).click();
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Thank you for your submission!')]")));

        assertEquals("Thank you for your submission!", successMessage.getText());
    }

    @AfterAll
    public void tearDown() {
       driver.quit();
    }
}
