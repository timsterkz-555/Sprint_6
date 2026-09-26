package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pageobjects.MainPage;
import pageobjects.OrderPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderTest extends BaseTest {

    static Stream<Arguments> orderData() {
        return Stream.of(
                Arguments.of(
                        "верхняя кнопка", true,
                        "Кира", "Лайт", "Токио", "Черкизовская", "87775554444",
                        "двое суток", "black", "Напишите"
                ),
                Arguments.of(
                        "нижняя кнопка", false,
                        "Торфин", "Клиф", "Винланд 15", "Сокольники", "87012227777",
                        "трое суток", "grey", "Ватсап"
                )
        );
    }

    @DisplayName("Успешное оформление заказа самоката")
    @ParameterizedTest(name = "{0}")
    @MethodSource("orderData")
    void testOrderCanBeCreated(
            String scenarioName,
            boolean useTopOrderButton,
            String name,
            String surname,
            String address,
            String metro,
            String phone,
            String rentalPeriod,
            String color,
            String comment
    ) {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);
        String deliveryDate = LocalDate.now()
                .plusDays(2)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        mainPage.open();
        if (useTopOrderButton) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        orderPage.fillCustomerData(name, surname, address, metro, phone);
        orderPage.clickNext();
        orderPage.fillRentalData(deliveryDate, rentalPeriod, color, comment);
        orderPage.clickOrder();
        orderPage.confirmOrder();

        assertTrue(orderPage.isOrderCreated(), "Окно с сообщением «Заказ оформлен» не появилось");
    }
}
