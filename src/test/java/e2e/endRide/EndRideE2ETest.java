package e2e.endRide;

import e2e.E2ETestBase;
import org.testng.annotations.Test;
import pages.DriverEndRidePage;
public class EndRideE2ETest extends E2ETestBase {

    @Test
    public void endRide() throws InterruptedException {
        DriverEndRidePage endRidePage = new DriverEndRidePage(driver);
        endRidePage.initRide();
        //Thread.sleep(1000);
        endRidePage.goToLoginPage();
        endRidePage.clickOnLoginBtn();
        endRidePage.login();
        //Thread.sleep(1000);
        endRidePage.acceptRide();
        //Thread.sleep(5000);
        endRidePage.endRide();
        //Thread.sleep(1000);
        endRidePage.logout();

    }

    @Test void EmergencyStop() throws InterruptedException {
        DriverEndRidePage endRidePage = new DriverEndRidePage(driver);
        endRidePage.initRide();
        //Thread.sleep(1000);
        endRidePage.goToLoginPage();
        endRidePage.clickOnLoginBtn();
        endRidePage.login();
        //Thread.sleep(1000);
        endRidePage.acceptRide();
        //Thread.sleep(5000);
        endRidePage.emergencyStop();
        //Thread.sleep(1000);
        endRidePage.sendReason();
        //Thread.sleep(1000);
        endRidePage.logout();

    }
}
