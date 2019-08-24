package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class FlightSelectPage extends BasePage {

    public FlightSelectPage(WebDriver driver) {
        super(driver);
    }

    //*********Locators*********
    private String rdoArrivalFlight = "input[name='inFlight'][value='%s']";
    private String rdoDepartureFlight = "input[name='outFlight'][value='%s']";
    private String btnContinue = "input[name='reserveFlights']";

    //*********Page Methods*********
    public void selectDepartureFlight(String departure) {
        click(By.cssSelector(String.format(rdoDepartureFlight, departure)));
    }

    public void selectArrivalFlight(String arrival) {
        click(By.cssSelector(String.format(rdoArrivalFlight, arrival)));
    }

    public void clickContinue() {
        click(By.cssSelector(btnContinue));
    }

    public void selectDepartureAndArrivalFlights(String departureFlight, String arrivalFlight, FlightSelectPage flightSelectPage) {
        selectDepartureFlight(departureFlight);
        selectArrivalFlight(arrivalFlight);
        clickContinue();
    }
    //*********Verifications*********

}