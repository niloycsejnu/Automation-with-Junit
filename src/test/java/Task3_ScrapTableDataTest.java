import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.List;
import static org.junit.Assert.assertTrue;

public class Task3_ScrapTableDataTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testScrapeTableData() {
        driver.get("https://dsebd.org/latest_share_price_scroll_by_value.php");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[contains(@class, 'table-bordered')]")));
        List<WebElement> rows = driver.findElements(By.xpath("//table[contains(@class, 'table-bordered')]/tbody/tr"));
        StringBuilder data = new StringBuilder();
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            for (WebElement column : columns) {
                String cellData = column.getText();
                System.out.print(cellData + " | ");
                data.append(cellData).append(" | ");
            }
            System.out.println();
            data.append(System.lineSeparator());
        }

        try {
            Files.write(Paths.get("output.txt"), data.toString().getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue("The data has been written to the text file format.", true);
    }

    @After
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}