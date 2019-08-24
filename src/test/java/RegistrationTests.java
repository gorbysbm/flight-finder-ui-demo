import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import util.CookieHelper;
import util.LocalDriverManager;
import util.StringUtilities;

import java.io.IOException;

public class RegistrationTests extends BaseTests {

    //*********Tests*********

    @DataProvider(name="getTestRegisterUser")
    public Object[][] getTestRegisterUserDataProvider() throws IOException {
        return getDataProvider("testRegisterUser");
    }

    @Test(groups = {"functional" , "validFlightRegistration"}, dataProvider = "getTestRegisterUser",description =
            "User navigates to Home page, clicks on Register and submits valid Registration info" )
    public void testRegisterUser(String description,String firstName,String lastName,String phoneNumber,String email,
                                 String streetAddress,String city,String state,String postCode,String country, String password) {
        WebDriver driver = LocalDriverManager.getDriver();
        HomePage homePage = new HomePage(driver);
        RegistrationPage regPage = new RegistrationPage(driver);
        TopNav topNav = new TopNav(driver);

        //generate unique email
        email = StringUtilities.generateUniqueId() +email;
        homePage.goToHomepage();
        topNav.clickRegisterUser();
        regPage.registerUser(firstName,lastName,phoneNumber,email,streetAddress,city,state,postCode,password);
        regPage.verifyRegistrationSuccess();
        setUserRegValues(email, password, firstName, lastName, phoneNumber,streetAddress,  city, state , postCode, country);
    }

    @Test(enabled = false ,groups = {"functional"}, description =
            "User navigates to Home page, clicks on Register and submits invalid Registration info. " +
                    "User should not see Registration Success Confirmation message" )
    public void testRegisterUserInvalidData() {
        WebDriver driver = LocalDriverManager.getDriver();
        HomePage homePage = new HomePage(driver);
        RegistrationPage regPage = new RegistrationPage(driver);
        TopNav topNav = new TopNav(driver);

        homePage.goToHomepage();
        topNav.clickRegisterUser();
        regPage.clickSubmit();
        regPage.verifyRegistrationFail();
    }

    //*********Helper Methods*********

    //Sets attributes into an object that can be accessed by other tests
    private void setUserRegValues(String email, String password, String firstName, String lastName, String phoneNumber,
                                  String streetAddress, String city, String state, String postCode, String country) {
        ITestContext ctx = Reporter.getCurrentTestResult().getTestContext();

        ctx.setAttribute("email",email);
        ctx.setAttribute("password", password);
        ctx.setAttribute("firstName", firstName);
        ctx.setAttribute("lastName", lastName);
        ctx.setAttribute("phoneNumber", phoneNumber);
        ctx.setAttribute("streetAddress", streetAddress);
        ctx.setAttribute("city", city);
        ctx.setAttribute("state", state);
        ctx.setAttribute("postCode", postCode);
        ctx.setAttribute("country", country);

        CookieHelper.storeCurrentSessionCookies(ctx);
    }

}
