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

public class BrowserEnv {
    private final String browserName;
    private final boolean headlessBrowser;
    private final String currentEnvName;
    private WebDriver driver;


    public BrowserEnv() throws IOException {

        Configuration configuration = ConfigurationRetriever.getConfig();

        this.browserName = configuration.isSpecified("browser") ? configuration.getProperty("browser").toString() : "firefox";
        this.headlessBrowser = configuration.isSpecified("headless") && Boolean.parseBoolean(configuration.getProperty("headless").toString());
        this.currentEnvName = configuration.getProperty("environment").toString();
        this.initBrowserSettings();

    }

    private void initBrowserSettings() throws IOException {
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
        Configuration configuration = ConfigurationRetriever.getConfig();

        driver.get(configuration.getEnvironment(currentEnvName).get("url").toString());
    }

    public WebDriver getDriver() {
        return this.driver;
    }
}
