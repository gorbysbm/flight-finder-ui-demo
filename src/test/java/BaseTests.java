import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.log4testng.Logger;
import util.CaptureArtifacts;
import util.LocalDriverManager;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BaseTests {
    private static final Logger LOGGER = Logger.getLogger(BaseTests.class);
    Path PROJECT_ROOT = FileSystems.getDefault().getPath("").toAbsolutePath();
    private static DesiredCapabilities capabilities = new DesiredCapabilities();
    private static String browserName;
    private static boolean isDocker;
    private int pageLoadTimeOut = 45;


    @BeforeSuite(alwaysRun = true)
    @Parameters({"useDocker","env", "browserName"})
    public void beforeSuite(boolean useDocker , @Optional("") String env, @Optional("") String browserName) {
        BaseTests.browserName = browserName;
        BaseTests.isDocker = useDocker;

        if (browserName.toLowerCase().equals("chrome")) {
            capabilities = DesiredCapabilities.chrome();
        } else if (browserName.toLowerCase().equals("firefox")) {
            capabilities = DesiredCapabilities.firefox();
        } else if (browserName.toLowerCase().equals("safari")) {
            capabilities = DesiredCapabilities.safari();
        } else{ //Defaulting to Chrome if no browser specified in params
            BaseTests.browserName="chrome";
            capabilities = DesiredCapabilities.chrome();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setup (Method method) throws MalformedURLException {
        WebDriver driver = null;
        if (isDocker){
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
        }
        else{
            driver = initiateLocalDriver();
        }

        LOGGER.info(">>Test Name and Selenium session ID: "+method.getName()+ driver.hashCode());

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeOut, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        LocalDriverManager.setWebDriver(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult) {
        WebDriver driver = LocalDriverManager.getDriver();

        if(null != driver) {
            if(ITestResult.FAILURE == testResult.getStatus()){
                CaptureArtifacts.captureScreenShot(driver, testResult.getName());
            }
            driver.close();
            driver.quit();
        }
    }
    public Object[][] getDataProvider(String testName) throws IOException {
        String filePath= PROJECT_ROOT.toString()+"/src/test/java/resources/dataproviders/"+testName+".csv";
        Reader reader = Files.newBufferedReader(Paths.get(filePath));

        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        List<String[]> found = csvReader.readAll();
        Object[][] dataProviderObj = found.toArray(new Object[found.size()][]);

        return dataProviderObj;
    }


    private WebDriver initiateLocalDriver() {
        WebDriver driver = null;
        if (browserName.toLowerCase().equals("chrome")) {
            driver = new ChromeDriver();
        } else if (browserName.toLowerCase().equals("firefox")) {
            driver = new FirefoxDriver();
        } else if (browserName.toLowerCase().equals("safari")) {
            driver = new SafariDriver();
        }

        return driver;
    }

}
