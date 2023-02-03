package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ClientPage {
    public static final String PAGE_URL = "http://localhost:4200/client/home";

    private final WebDriver driver;
    @FindBy(id = "start")
    private WebElement startStreet;

    @FindBy(id = "start_number")
    private WebElement startNumber;

    @FindBy(id = "final")
    private WebElement finalStreet;

    @FindBy(id = "final_number")
    private WebElement finalNumber;

    @FindBy(id="search_btn")
    private WebElement searchBtn;

    @FindBy(id="request_btn")
    private WebElement requestBtn;

    @FindBy(id="distance")
    private WebElement distanceLabel;

    //driver.switchTo().alert().accept();
    //driver.switchTo().alert().getText();
    public ClientPage(WebDriver driver) {
        this.driver = driver;
        this.driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void setStartStreet(String str){
        Actions a = new Actions(driver);
        a.moveToElement(startStreet);
        clickOnWebElement(startStreet);
        startStreet.clear();
        startStreet.sendKeys(str);
    }
    public void setStartNumber(String str){
        Actions a = new Actions(driver);
        a.moveToElement(startNumber);
        clickOnWebElement(startNumber);
        startNumber.clear();
        startNumber.sendKeys(str);
    }
    public void setFinalStreet(String str){
        Actions a = new Actions(driver);
        a.moveToElement(finalStreet);
        clickOnWebElement(finalStreet);
        finalStreet.clear();
        finalStreet.sendKeys(str);
    }
    public void setFinalNumber(String str){
        Actions a = new Actions(driver);
        a.moveToElement(finalNumber);
        clickOnWebElement(finalNumber);
        finalNumber.clear();
        finalNumber.sendKeys(str);
    }

    public void clickOnSearch(){
        (new WebDriverWait(driver, Duration.of(10L, ChronoUnit.SECONDS)))
                .until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
    }

    public void clickOnRequest() {
        clickOnWebElement(requestBtn);
    }

    private void clickOnWebElement(WebElement ele) {
        (new WebDriverWait(driver, Duration.of(3L, ChronoUnit.SECONDS)))
                .until(ExpectedConditions.elementToBeClickable(ele)).click();
    }

    public void dismissAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10L, ChronoUnit.SECONDS));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

}
