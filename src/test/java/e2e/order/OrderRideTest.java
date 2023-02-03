package e2e.order;

import e2e.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ClientPage;
import pages.LoginPage;

public class OrderRideTest extends TestBase {

    private void login(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnLoginBtn();
        loginPage.correctlyFillLoginForm();
        loginPage.login();
    }

    private void failedPaymentLogin(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnLoginBtn();
        loginPage.customFill("valdagarovic@gmail.com","valdagarov");
        loginPage.login();
    }
    @Test
    public void successfulOrder() {
        login();
        ClientPage clientPage = new ClientPage(driver);
        clientPage.setStartStreet("Mise Dimitrijevica");
        clientPage.setStartNumber("4");
        clientPage.setFinalStreet("Karadjordjeva");
        clientPage.setFinalNumber("5");
        clientPage.clickOnSearch();
        clientPage.clickOnRequest();
        clientPage.dismissAlert();
    }

    @Test
    public void paymentFailed() {
        failedPaymentLogin();
        ClientPage clientPage = new ClientPage(driver);
        clientPage.setStartStreet("Mise Dimitrijevica");
        clientPage.setStartNumber("4");
        clientPage.setFinalStreet("Karadjordjeva");
        clientPage.setFinalNumber("5");
        clientPage.clickOnSearch();
        clientPage.clickOnRequest();
        clientPage.dismissAlert();
    }

    @Test
    public void noDrivers() {
        login();
        ClientPage clientPage = new ClientPage(driver);
        clientPage.setStartStreet("Mise Dimitrijevica");
        clientPage.setStartNumber("4");
        clientPage.setFinalStreet("Karadjordjeva");
        clientPage.setFinalNumber("5");
        clientPage.clickOnSearch();
        clientPage.clickOnRequest();
        clientPage.dismissAlert();

    }

}
