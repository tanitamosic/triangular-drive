package e2e.login;

import e2e.E2ETestBase;
import org.testng.annotations.Test;
import pages.LoginPage;


public class LoginE2ETest extends E2ETestBase {

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
