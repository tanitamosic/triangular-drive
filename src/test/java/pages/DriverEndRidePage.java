package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class DriverEndRidePage {

    public static final String PAGE_URL = "http://localhost:4200/";
    public static final String InitRide_URL = "http://localhost:8080/api/test/initRideForE2ETest";

    private final WebDriver driver;

    public DriverEndRidePage(WebDriver driver){
        this.driver = driver;

    }

    public void initRide() {
        driver.get(InitRide_URL);
    }

    public void goToLoginPage(){
        this.driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void clickOnLoginBtn() {
        (new WebDriverWait(driver, Duration.of(3L, ChronoUnit.SECONDS)))
                .until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }

    public void login() {
        inputEmail("milanakeljic@gmail.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(3L, ChronoUnit.SECONDS));
        inputPassword("milanakelj");
        (new WebDriverWait(driver, Duration.of(3L, ChronoUnit.SECONDS)))
                .until(ExpectedConditions.elementToBeClickable(attemptLoginBtn)).click();
    }

    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10L, ChronoUnit.SECONDS));
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();

    }


    private void inputPassword(String pass) {
        Actions a = new Actions(driver);
        a.moveToElement(passwordField);
        clickOnWebElement(passwordField);
        passwordField.clear();
        passwordField.sendKeys(pass);
    }

    private void inputEmail(String email) {
        Actions a = new Actions(driver);
        a.moveToElement(emailField);
        clickOnWebElement(emailField);
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void acceptRide(){
        clickOnWebElement(getAcceptButton());
    }

    public void endRide(){
        clickOnWebElement(getEndRideButton());
    }

    public void emergencyStop(){
        clickOnWebElement(getEmergencyStopButton());
    }

    public void sendReason() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10L, ChronoUnit.SECONDS));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }
    private void clickOnWebElement(WebElement ele) {
        (new WebDriverWait(driver, Duration.of(3L, ChronoUnit.SECONDS)))
                .until(ExpectedConditions.elementToBeClickable(ele)).click();
    }


    WebElement getAcceptButton(){
        return driver.findElement(By.id("acceptBtn"));
    }

    WebElement getEndRideButton(){
        return driver.findElement(By.id("endRideBtn"));
    }

    WebElement getEmergencyStopButton(){
        return driver.findElement(By.id("emergencyStopBtn"));
    }


    @FindBy(how = How.ID, using = "loginBtn")
    private WebElement loginBtn;

    @FindBy(how = How.ID, using = "float-input-email")
    private WebElement emailField;

    @FindBy(how = How.ID, using = "float-input-password")
    private WebElement passwordField;

    @FindBy(how = How.ID, using = "attemptLoginBtn")
    private WebElement attemptLoginBtn;

    @FindBy(how=How.ID, using="logoutBtn")
    private WebElement logoutBtn;


}
