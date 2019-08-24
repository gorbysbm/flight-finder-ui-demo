package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import util.LocalDriverManager;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BasePage {
    final WebDriver driver;
    private WebDriverWait wait;
    int EXPLICIT_WAIT_DEFAULT_TIMEOUT = 5;
    public static String BASE_URL = "http://www.newtours.demoaut.com";
    By documentBody = By.cssSelector("body");
    
    
    public BasePage (WebDriver driver){
        this.driver = driver;
        setExplicitWaitToDefault();
    }

    public void openPage (String url) {
        LocalDriverManager.getDriver().get(url);
    }

    public void refreshPage(){
        LocalDriverManager.getDriver().navigate().refresh();
    }

    //Wait for presence of elements before proceeding with action
    public List<WebElement> waitForPresenceOfAllElements(By elementBy) {
        return getExplicitWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(elementBy));
    }

    //Wait for presence of element before proceeding with action
    public WebElement waitForPresenceOfElement(By elementBy) {
        return getExplicitWait().until(ExpectedConditions.presenceOfElementLocated(elementBy));
    }

    //Wait for Visibility of element before proceeding with action
    public List<WebElement> waitForVisibilityOfAllElements(By elementBy) {
        return getExplicitWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementBy));
    }

    public WebElement waitForVisibilityOfElement(By elementBy) {
        return getExplicitWait().until(ExpectedConditions.visibilityOfElementLocated(elementBy));
    }

    public void waitForInvincibility(By elementBy){
        getExplicitWait().until(ExpectedConditions.invisibilityOfElementLocated(elementBy));
    }

    //Wait for Clickability of element before proceeding with action
    public void waitForClickable(By elementBy) {
        getExplicitWait().until(ExpectedConditions.elementToBeClickable(elementBy));
    }

    public void waitForClickable(WebElement element) {
        getExplicitWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForSelected(WebElement element){
       getExplicitWait().until(ExpectedConditions.elementToBeSelected(element));
    }

    public Boolean waitForTextUpdate(By elementBy, String expectedText){
        return getExplicitWait().until(ExpectedConditions.textToBe(elementBy, expectedText));
    }

    public void click (By elementBy) {
        waitForClickable(elementBy);
        driver.findElement(elementBy).click();
    }

    public void click (WebElement element) {
        waitForClickable(element);
        element.click();
    }

    public void mouseOver (By elementBy) {
        Actions action = new Actions(driver);
        WebElement we = driver.findElement(elementBy);
        action.moveToElement(we).build().perform();
    }

    public WebElement getPresentElement(By elementBy){
        waitForPresenceOfAllElements(elementBy);
        return driver.findElement(elementBy);
    }


    public List<WebElement> getAllElementsPresent(By elementBy){
        waitForPresenceOfAllElements(elementBy);
        return driver.findElements(elementBy);
    }

    public boolean isElementPresent(By locatorKey) {
        try {
            waitForPresenceOfElement(locatorKey);
            return true;
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    public List<WebElement> getAllElementsVisible(By elementBy){
        waitForVisibilityOfAllElements(elementBy);
        return driver.findElements(elementBy);
    }

    public WebElement getVisibleElement(By elementBy) {
        return waitForVisibilityOfElement(elementBy);
    }

    //fix occasional: stale element reference: element is not attached to the page
    public void waitForStalenessOfElement(WebElement element) {
        try{
            getExplicitWait().until(ExpectedConditions.stalenessOf(element));
        }catch (TimeoutException e){

        }
    }

    public void waitForRefreshElement(WebElement element, By elementBy ){
        waitForStalenessOfElement(element);
        waitForPresenceOfAllElements(elementBy);
    }

    public void clickWithJavascript(By elementBy){
        waitForPresenceOfAllElements(elementBy);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", driver.findElement(elementBy));
    }

    public void selectFromDropdown(By elementBy, String itemName){
        Select dropdown = new Select(driver.findElement(elementBy));
        dropdown.selectByValue(itemName);
    }


    public void clearAndTypeText(By elementBy, String text) {
        waitForPresenceOfAllElements(elementBy);
        driver.findElement(elementBy).clear();
        driver.findElement(elementBy).sendKeys(text);
    }

    public String getPageTitle(){
        return driver.getTitle();
    }

    //Note: Selenium's getText doesn't always work
    public String getText (By elementBy) {
        waitForPresenceOfAllElements(elementBy);
        return driver.findElement(elementBy).getText();
    }

    public String getTextByAttribute(By elementBy){
        String foundText = "";
        waitForPresenceOfAllElements(elementBy);

        foundText = driver.findElement(elementBy).getAttribute("innerHTML") ;
        if (foundText.length() > 0){
            return foundText;
        }
        else{
            foundText = driver.findElement(elementBy).getAttribute("value") ;
        }
        return foundText;
    }

    public String readCellInTable (By elementBy, int rowIndex, int cellIndex) {
        waitForPresenceOfAllElements(elementBy);
        WebElement baseTable = driver.findElement(elementBy);
        List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));

        //basic boundary case check
        if (rowIndex>=tableRows.size())
            Assert.fail( "Invalid position of table row, it should be greater than 0 and <="+ tableRows.size());
        List<WebElement> tableCells = tableRows.get(rowIndex).findElements(By.tagName("td"));
        if (cellIndex>=tableCells.size())
            Assert.fail( "Invalid position of cell, it should be greater than 0 and <="+ tableCells.size());

        return tableCells.get(cellIndex).getText();
    }

    public void scrollIntoView(WebDriver driver, WebElement element){
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public void scrollPageDown(){
        waitForPresenceOfAllElements(documentBody);
        driver.findElement(documentBody).sendKeys((Keys.PAGE_DOWN));
    }

    public void scrollPageUp(){
        waitForPresenceOfAllElements(documentBody);
        driver.findElement(documentBody).sendKeys((Keys.PAGE_UP));
    }
    public void switchToPopup() {
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> s = driver.getWindowHandles();
        Iterator<String> ite = s.iterator();
        while (ite.hasNext()) {
            String popupHandle = ite.next().toString();
            if (!popupHandle.contains(mainWindowHandle)) {
                driver.switchTo().window(popupHandle);
            }
        }
    }

    public void switchToIframe(By elementBy){
        getExplicitWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(elementBy));

    }
    public void switchOutOfIframe(){
        driver.switchTo().defaultContent();
    }


    public void moveSlider(WebElement slider, int xOffset) {
        Actions move = new Actions(driver);
        Action action = move.dragAndDropBy(slider, xOffset, 0).build();
        action.perform();
    }

    //Explicit wait. Used to wait a specific time for slow elements in DOM to load
    public void setExplicitWait(int explicitWait) {
        wait = new WebDriverWait(driver, explicitWait);
    }
    public WebDriverWait getExplicitWait() {
        return wait;
    }

    public void setExplicitWaitToDefault() {
        setExplicitWait(EXPLICIT_WAIT_DEFAULT_TIMEOUT);
    }
}
