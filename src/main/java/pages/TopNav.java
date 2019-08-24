package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class TopNav extends BasePage {

    public TopNav(WebDriver driver) {
        super(driver);
    }

    //*********Locators*********
    private String lnkRegister = "td.mouseout a[href*='mercuryregister.php']";
    private String lnkSignIn = "td.mouseout a[href*='mercurysignon.php']";

    //*********Page Methods*********
    public void clickRegisterUser(){
        click(By.cssSelector(lnkRegister));
    }

    public void clickSignInUser(){
        click(By.cssSelector(lnkSignIn));
    }

}