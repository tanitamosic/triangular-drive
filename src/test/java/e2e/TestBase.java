package e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver driver;

    @BeforeSuite
    public void initializeWebDriver() {
        String os = System.getProperty("os.name");
//        if(os.equals("Linux")){
//            System.setProperty("webdriver.chrome.driver", "./chromedriver/linux/chromedriver");
//        } else{
//            System.setProperty("webdriver.chrome.driver", "./chromedriver/windows/chromedriver.exe");
//        }
        System.setProperty("webdriver.gecko.driver", "./geckodriver/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
    }

    @AfterSuite
    public void quitDriver() {
        driver.quit();
    }
}
