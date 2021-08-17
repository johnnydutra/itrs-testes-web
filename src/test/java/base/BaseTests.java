package base;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;

import java.util.concurrent.TimeUnit;

public class BaseTests {

    private static WebDriver driver;
    protected HomePage homePage;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\training\\irts-testes-web\\src\\test\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void loadHomePage() {
        driver.get("https://marcelodebittencourt.com/demoprestashop/");
        homePage = new HomePage(driver);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

}
