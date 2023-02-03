package e2e.login;

import e2e.TestBase;
import org.testng.annotations.Test;
import pages.LoginPage;


public class LoginE2ETest extends TestBase {

    @Test
    public void successfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnLoginBtn();
        loginPage.correctlyFillLoginForm();
        loginPage.login();
        loginPage.logout();
    }


    @Test
    public void failedLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnLoginBtn();
        loginPage.keyboardFaceSmash();
        loginPage.login();
        loginPage.dismissAlert();
    }
}
