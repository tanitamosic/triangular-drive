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

public class LoginPage {

    public static final String PAGE_URL = "http://localhost:4200/";

    private final WebDriver driver;

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

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void clickOnLoginBtn() {
        (new WebDriverWait(driver, Duration.of(3L, ChronoUnit.SECONDS)))
                .until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }

    private void clickOnWebElement(WebElement ele) {
        (new WebDriverWait(driver, Duration.of(3L, ChronoUnit.SECONDS)))
                .until(ExpectedConditions.elementToBeClickable(ele)).click();
    }

    public void correctlyFillLoginForm() {
        inputEmail("markotrijanic@gmail.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(3L, ChronoUnit.SECONDS));
        inputPassword("markotrija");
    }

    public void customFill(String email, String pass){
        inputEmail(email);
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(3L, ChronoUnit.SECONDS));
        inputPassword(pass);
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

    public void login() {
        (new WebDriverWait(driver, Duration.of(3L, ChronoUnit.SECONDS)))
                .until(ExpectedConditions.elementToBeClickable(attemptLoginBtn)).click();
    }

    public void keyboardFaceSmash() {
        inputEmail("DefentiellyNotValidEmail@not.an.email.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(3L, ChronoUnit.SECONDS));
        inputPassword("dumbpassword123");
    }

    public void dismissAlert() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10L, ChronoUnit.SECONDS));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10L, ChronoUnit.SECONDS));
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();

    }
}
