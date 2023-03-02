package Utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class setup {
    public WebDriver driver;
    private String applicationUrl,browser;
    private int implicitWaitSeconds;


    @BeforeClass
    public void setUp(){
        setupBrowser();
        loadURL();
    }
    @AfterClass
    public void tearDown (){
        driver.quit();
    }
    private void loadURL() {
        driver.get(applicationUrl);
    }


    private void setupChromeDriver(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds));
    }
    private void setupFirefoxDriver(){
        WebDriverManager.firefoxdriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds));
    }

    private void setupBrowser() {
        try {

            FileInputStream configFile = new FileInputStream("src/test/resources/resources.properties");
            Properties config = new Properties();
            config.load(configFile);
            applicationUrl = config.getProperty("url");
            implicitWaitSeconds = Integer.parseInt(config.getProperty("implicitWait"));
            browser = config.getProperty("browser");
            if (browser.equals("chrome")) {
                setupChromeDriver();
            }
            if (browser.equals("firefox")){
                setupFirefoxDriver();
            }

        }
        catch (IOException e) {

            throw new RuntimeException(e);
        }

    }
}
