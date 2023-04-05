package configuration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.io.IOException;

import static configuration.ConfigurationRetriever.getConfig;

public class BrowserEnv {

    private final String browserName;
    private final boolean headlessBrowser;
    private WebDriver driver;

    public BrowserEnv() throws IOException {
        Configuration configuration = getConfig();
        this.browserName = configuration.isSpecified("browser") ? configuration.getProperty("browser").toString() : "firefox";
        this.headlessBrowser = configuration.isSpecified("headless") && Boolean.parseBoolean(configuration.getProperty("headless").toString());
        this.initBrowserSettings();
    }

    private void initBrowserSettings() {
        switch (this.browserName) {
            case "chrome" -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                WebDriverManager.chromedriver().setup();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--remote-allow-origins=*");
                if (this.headlessBrowser) {
                    chromeOptions.addArguments("--headless");
                }
                driver = new ChromeDriver(chromeOptions);
            }
            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                WebDriverManager.firefoxdriver().setup();
                firefoxOptions.addArguments("start-maximized");
                if (this.headlessBrowser) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
            }
            case "internet explorer" -> {
                InternetExplorerOptions defaultOptions = new InternetExplorerOptions();
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver(defaultOptions);
            }
            default -> throw new UnsupportedOperationException("Unsupported browser selected.");
        }
        driver.get(System.getProperty("url"));
    }

    public WebDriver getDriver() {
        return this.driver;
    }

}
