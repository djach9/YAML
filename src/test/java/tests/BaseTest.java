package tests;

import configuration.BrowserEnv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BaseTest {
    protected static WebDriver driver;
    protected static Logger logger = LoggerFactory.getLogger("tests.BaseTest.class");



    @BeforeAll
    static void setUp() throws IOException {
        BrowserEnv browserEnv = new BrowserEnv();
        driver = browserEnv.getDriver();
        logger.info("Driver is opened");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        logger.info("Driver is closed");
    }
}
