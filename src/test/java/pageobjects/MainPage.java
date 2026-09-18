package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private static final String PAGE_URL = "https://qa-scooter.education-services.ru/";
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Верхняя кнопка «Заказать» — первая кнопка с текстом «Заказать»
    private final By topOrderButton = By.xpath("(//button[normalize-space()='Заказать'])[1]");

    // Нижняя кнопка «Заказать» — последняя кнопка с текстом «Заказать»
    private final By bottomOrderButton = By.xpath("(//button[normalize-space()='Заказать'])[last()]");

    // Кнопка принятия cookie — кнопка с текстом «да все привыкли»
    private final By acceptCookiesButton = By.xpath("//button[normalize-space()='да все привыкли']");

    // Вопрос FAQ — id="accordion__heading-{index}"
    private By question(int index) {
        return By.id("accordion__heading-" + index);
    }

    // Ответ FAQ — id="accordion__panel-{index}"
    private By answer(int index) {
        return By.id("accordion__panel-" + index);
    }

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get(PAGE_URL);
        wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton)).click();
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void clickTopOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(topOrderButton)).click();
    }

    public void clickBottomOrderButton() {
        scrollToBottom();
        wait.until(ExpectedConditions.elementToBeClickable(bottomOrderButton)).click();
    }

    public void clickQuestion(int index) {
        WebElement questionElement = wait.until(ExpectedConditions.elementToBeClickable(question(index)));
        questionElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(answer(index)));
    }

    public String getAnswerText(int index) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(answer(index))).getText();
    }
}
