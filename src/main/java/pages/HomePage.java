package pages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;


public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    //*********Page Methods*********
    public void goToHomepage() {
        driver.get(BASE_URL);
    }

    //*********Verifications*********
    public void verifyPageTitle(){
        String expectedPageTitle = "Welcome: Mercury Tours";
        Assert.assertEquals(expectedPageTitle , getPageTitle());
    }

}