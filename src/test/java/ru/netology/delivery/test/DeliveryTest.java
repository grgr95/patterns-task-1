package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input ").setValue(DataGenerator.generateCity("ru"));
        $ ("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME), Keys.BACK_SPACE);
        $ ("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[data-test-id='name'] input ").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone'] input ").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
        $(".notification__content").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate),
                Duration.ofSeconds(15)).shouldBe(visible);
        $ ("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME), Keys.BACK_SPACE);
        $ ("[data-test-id='date'] input").setValue(secondMeetingDate);
        $$("button").find(exactText("Запланировать")).click();
        $(byText("Необходимо подтверждение")).shouldBe(visible, Duration.ofMillis(15000));
        $(withText("Необходимо подтверждение")).shouldBe(visible, Duration.ofSeconds(15));
        $$("button").find(exactText("Перепланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
        $(".notification__content").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate),
                Duration.ofSeconds(15)).shouldBe(visible);

    }
}