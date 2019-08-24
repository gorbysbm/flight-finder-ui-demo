import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pages.FlightFinderPage;
import pages.HomePage;
import pages.LoginPage;
import pages.TopNav;
import util.LocalDriverManager;

public class LoginTests extends BaseTests {

    //*********Tests*********

    @Test(dependsOnGroups ={"validFlightRegistration"} ,groups = {"functional", "validLogin"},
            description = "User navigates to Sign in page, submits a previously created username and password")
    public void testValidLogin() {
        WebDriver driver = LocalDriverManager.getDriver();
        FlightFinderPage flightFinderPage = new FlightFinderPage(driver);
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        TopNav topNav = new TopNav(driver);
        ITestContext ctx = Reporter.getCurrentTestResult().getTestContext();
        String userName = (String) ctx.getAttribute("email");
        String password = (String) ctx.getAttribute("password");

        homePage.goToHomepage();
        topNav.clickSignInUser();
        loginPage.loginUser(userName,password);
        //A successful login will redirect the user to the flight finder page
        flightFinderPage.verifyPageTitle();
    }

    @Test(groups = {"functional"},
            description = "User navigates to Sign in page, submits a wrong username and wrong password")
    public void testInvalidLogin() {
        WebDriver driver = LocalDriverManager.getDriver();
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        TopNav topNav = new TopNav(driver);
        String userName= "InvalidUsername162763154655";
        String password= "Password12345";

        homePage.goToHomepage();
        topNav.clickSignInUser();
        loginPage.loginUser(userName,password);
        loginPage.verifyNotLoggedIn();
    }

    //*********Helper Methods*********
}
