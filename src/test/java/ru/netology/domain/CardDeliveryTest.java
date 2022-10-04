package ru.netology.domain;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardDeliveryTest {
    @BeforeEach
    void open() {
        Selenide.open("http://localhost:9999");
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        // принимает на сколько дней сдвинуть дату и возвращает в нужном формате
    }

    @Test
    void succesForm() {
        Configuration.holdBrowserOpen = true;

        String planningDate = generateDate(4);

        $("[data-test-id = city] input").setValue("Тамбов");
        $("[data-test-id = date] .input__control").click();
        $("[data-test-id = date] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id = date] .input__control").sendKeys(BACK_SPACE);
        $("[data-test-id = date] .input__control").setValue(planningDate);
        $("[data-test-id = name] .input__control").setValue("Елена Банько");
        $("[data-test-id = phone] .input__control").setValue("+79336657088");
        $("[data-test-id = agreement]").click();
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id = notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id = notification] [class='notification__content']")
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));
    }
}