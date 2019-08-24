import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import util.CookieHelper;
import util.LocalDriverManager;

import java.io.IOException;

public class FlightFinderTests extends BaseTests {

    //*********Tests*********

    @DataProvider(name="getTestPurchaseFlight")
    public Object[][] getTestPurchaseFlightDataProvider() throws IOException {
        return getDataProvider("testPurchaseFlight");
    }
    @Test(dependsOnGroups ={"validLogin"} ,dataProvider = "getTestPurchaseFlight",
            groups = {"functional","E2EflightPurchase"},  description = "User logs in and selects a roundtrip flight," +
            " then purchases the flight")
    public void testPurchaseRoundTripFlight(String description, String serviceClass, String passengerCount, String departureCity,
                                   String arrivalCity, String departureFlight, String arrivalFlight, String creditCardNumber) {
        ITestContext ctx = Reporter.getCurrentTestResult().getTestContext();
        WebDriver driver = LocalDriverManager.getDriver();
        HomePage homePage = new HomePage(driver);
        FlightBookingPage flightBookingPage = new FlightBookingPage(driver);
        FlightBookingConfirmationPage flightBookConfirmPage = new FlightBookingConfirmationPage(driver);
        FlightFinderPage flightFinderPage = new FlightFinderPage(driver);
        FlightSelectPage flightSelectPage = new FlightSelectPage(driver);
        LeftNav leftNav = new LeftNav(driver);

        homePage.goToHomepage();
        CookieHelper.applyPriorSessionCookies(ctx);
        leftNav.clickFlights();

        flightFinderPage.selectFlightDetailsAndSearch(departureCity, arrivalCity);
        flightSelectPage.selectDepartureAndArrivalFlights(departureFlight, arrivalFlight, flightSelectPage);
        flightBookingPage.bookFlight(driver, ctx, creditCardNumber);
        flightBookConfirmPage.verifySuccessfulBooking(serviceClass, passengerCount, departureCity, arrivalCity, departureFlight, arrivalFlight, ctx, flightBookConfirmPage);
    }

    @Test(groups = {"functional"},  description = "A guest user tries navigating to flight purchase page," +
            " and is redirected to the homepage")
    public void testGuestUserUnableToPurchaseFlight() {
        WebDriver driver = LocalDriverManager.getDriver();
        HomePage homePage = new HomePage(driver);
        LeftNav leftNav = new LeftNav(driver);

        homePage.goToHomepage();
        leftNav.clickFlights();
        //verify Guest user is redirected back to homepage
        homePage.verifyPageTitle();
    }

}


