package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public final class WebDriverFactory {

    private WebDriverFactory() {
    }

    public static WebDriver create() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        if ("chrome".equals(browser)) {
            return new ChromeDriver();
        }
        if ("firefox".equals(browser)) {
            return new FirefoxDriver();
        }
        throw new IllegalArgumentException("Неподдерживаемый браузер: " + browser);
    }
}
