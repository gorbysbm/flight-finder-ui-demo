package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LeftNav extends BasePage {

    public LeftNav(WebDriver driver) {
        super(driver);
    }

    //*********Locators*********
    private String lnkFlights = "tr.mouseout a[href*='mercuryreservation.php']";

    //*********Page Methods*********
    public void clickFlights(){
        click(By.cssSelector(lnkFlights));
    }

}