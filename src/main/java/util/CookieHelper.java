package util;

import org.openqa.selenium.Cookie;
import org.testng.ITestContext;

import java.util.Set;

public class CookieHelper {

    public static void storeCurrentSessionCookies(ITestContext ctx){
        Set<Cookie> allCookies = LocalDriverManager.getDriver().manage().getCookies();
        ctx.setAttribute("driverCookies", allCookies);
    }

    //Only cookies from current domain of the browser can be applied (i.e. homepage). It can't be applied to "About: Blank" page
    public static void applyPriorSessionCookies(ITestContext ctx){
        clearCurrentSessionCookies();
        Set<Cookie> allCookies  = (Set<Cookie>)ctx.getAttribute("driverCookies");
        for(Cookie cookie : allCookies)
        {
            LocalDriverManager.getDriver().manage().addCookie(cookie);
        }
        //Refresh causes cookies to get applied to the session.
        LocalDriverManager.getDriver().navigate().refresh();
    }

    public static void clearCurrentSessionCookies(){
        LocalDriverManager.getDriver().manage().deleteAllCookies();
    }

}
