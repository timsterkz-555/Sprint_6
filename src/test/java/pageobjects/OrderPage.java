package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Поле «Имя» — input с placeholder="* Имя"
    private final By nameInput = By.cssSelector("input[placeholder='* Имя']");

    // Поле «Фамилия» — input с placeholder="* Фамилия"
    private final By surnameInput = By.cssSelector("input[placeholder='* Фамилия']");

    // Поле «Адрес» — input с placeholder="* Адрес: куда привезти заказ"
    private final By addressInput = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']");

    // Поле «Станция метро» — input с placeholder="* Станция метро"
    private final By metroInput = By.cssSelector("input[placeholder='* Станция метро']");

    // Поле «Телефон» — input с placeholder="* Телефон: на него позвонит курьер"
    private final By phoneInput = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка «Далее» — кнопка с видимым текстом «Далее»
    private final By nextButton = By.xpath("//button[normalize-space()='Далее']");

    // Поле даты доставки — input с placeholder="* Когда привезти самокат"
    private final By deliveryDateInput = By.cssSelector("input[placeholder='* Когда привезти самокат']");

    // Открытый календарь выбора даты — элемент с классом react-datepicker
    private final By datePicker = By.cssSelector(".react-datepicker");

    // Выпадающий список срока аренды — элемент с классом Dropdown-control
    private final By rentalPeriodDropdown = By.cssSelector(".Dropdown-control");

    // Цвет «чёрный жемчуг» — id="black"
    private final By blackColorCheckbox = By.id("black");

    // Цвет «серая безысходность» — id="grey"
    private final By greyColorCheckbox = By.id("grey");

    // Поле комментария — input с placeholder="Комментарий для курьера"
    private final By commentInput = By.cssSelector("input[placeholder='Комментарий для курьера']");

    // Кнопка отправки заказа — кнопка «Заказать» рядом с кнопкой «Назад»
    private final By orderButton = By.xpath(
            "//button[normalize-space()='Заказать' and preceding-sibling::button[normalize-space()='Назад']]"
    );

    // Окно подтверждения — элемент с текстом «Хотите оформить заказ?»
    private final By confirmationDialog = By.xpath("//div[contains(text(), 'Хотите оформить заказ')]");

    // Кнопка подтверждения в открытом окне — кнопка с видимым текстом «Да»
    private final By confirmButton = By.xpath("//button[text()='Да']");

    // Сообщение об успешном заказе — элемент с текстом «Заказ оформлен»
    private final By orderSuccessMessage = By.xpath("//*[contains(normalize-space(), 'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void fillCustomerData(String name, String surname, String address, String metro, String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).sendKeys(name);
        driver.findElement(surnameInput).sendKeys(surname);
        driver.findElement(addressInput).sendKeys(address);
        selectMetroStation(metro);
        driver.findElement(phoneInput).sendKeys(phone);
    }

    private void selectMetroStation(String metro) {
        WebElement metroField = driver.findElement(metroInput);
        metroField.click();
        metroField.sendKeys(metro);

        By metroOption = By.xpath(
                "//*[contains(@class, 'select-search__select')]//*[normalize-space()='" + metro + "']"
        );
        wait.until(ExpectedConditions.elementToBeClickable(metroOption)).click();
    }

    public void clickNext() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryDateInput));
    }

    public void fillRentalData(String deliveryDate, String rentalPeriod, String color, String comment) {
        WebElement dateField = wait.until(ExpectedConditions.elementToBeClickable(deliveryDateInput));
        dateField.sendKeys(deliveryDate);
        dateField.sendKeys(Keys.ESCAPE);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(datePicker));

        selectRentalPeriod(rentalPeriod);
        selectColor(color);
        driver.findElement(commentInput).sendKeys(comment);
    }

    private void selectRentalPeriod(String rentalPeriod) {
        wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodDropdown)).click();
        By rentalPeriodOption = By.xpath(
                "//div[contains(@class, 'Dropdown-option') and normalize-space()='" + rentalPeriod + "']"
        );
        wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodOption)).click();
    }

    private void selectColor(String color) {
        By colorCheckbox;
        if ("black".equals(color)) {
            colorCheckbox = blackColorCheckbox;
        } else if ("grey".equals(color)) {
            colorCheckbox = greyColorCheckbox;
        } else {
            throw new IllegalArgumentException("Неизвестный цвет самоката: " + color);
        }
        wait.until(ExpectedConditions.elementToBeClickable(colorCheckbox)).click();
    }

    public void clickOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationDialog));
    }

    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
    }

    public boolean isOrderCreated() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage)).isDisplayed();
    }
}
