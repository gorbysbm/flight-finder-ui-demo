package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.log4testng.Logger;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private static final Logger LOGGER = Logger.getLogger(LoginPage.class);

    //*********Locators*********
    private String txtUsername = "table input[name='userName']";
    private String txtPassword = "table input[name='password']";
    private String lnkSignIn = "td.mouseout a[href*='mercurysignon.php']";
    private String btnLogin = "table input[name='login']";

    //*********Page Methods*********

    public void enterUsername(String username){
        clearAndTypeText(By.cssSelector(txtUsername),username);
    }

    public void enterPassword(String password){
        clearAndTypeText(By.cssSelector(txtPassword),password);
    }

    public void clickLogin(){
        click(By.cssSelector(btnLogin));
    }

    public void loginUser(String userName, String password) {
        LOGGER.info(String.format(">>> Attempting to login username: %s and passsword: %s",userName, password));
        enterUsername(userName);
        enterPassword(password);
        clickLogin();
    }

    public void verifyNotLoggedIn() {
        //Sign in link is still present
        waitForVisibilityOfElement(By.cssSelector(lnkSignIn));
        //username field is still present
        waitForVisibilityOfElement(By.cssSelector(txtUsername));
        //password field is still present
        waitForVisibilityOfElement(By.cssSelector(txtPassword));
    }

    //*********Verifications*********

}