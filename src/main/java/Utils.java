import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public class Utils {

    public static void scroll(WebDriver driver, int height) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + height + ")");
    }

    public static String generatePhoneNumber() {
        int min = 1000000;
        int max = 9999999;
        int suffix = (int) (Math.random() * (max - min) + min);
        return "017" + suffix;
    }

}

