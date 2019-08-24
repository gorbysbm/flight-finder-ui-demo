package util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

import java.io.File;

public class CaptureArtifacts {

    public static final String ARTIFACT_DIRECTORY = System.getProperty("user.dir")+"/out/test/resources/";
    private static final Logger LOGGER = Logger.getLogger(CaptureArtifacts.class);


    public static void captureScreenShot(WebDriver driver, String testName){

        try {
            File screenshotsDir = new File(ARTIFACT_DIRECTORY);
            String fullScreenshotPath = screenshotsDir.getAbsolutePath() + File.separatorChar
                    + testName + driver.hashCode() + ".png";
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdir();
            }
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(fullScreenshotPath));
            LOGGER.info(">> Captured Screenshot for: " +testName + driver.hashCode() + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
