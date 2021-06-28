package ru.netology.domain.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.data.DBHelper;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.page.ServisPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestBuy {
    ServisPage servisPage = new ServisPage();


    @BeforeEach
    void shouldCleanDataBaseAndOpenWeb() {
        DBHelper.cleanDB();
        open(System.getProperty("website"));
        servisPage.buy();
    }


    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldValidByeApproved() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.expectApprovadBank();
        val expected = DataHelper.getFirstCardStatus();
        if (expected == DataHelper.getSecondCardStatus()) {
            servisPage.errorBankRefusal();
        } else {
            val actual = DBHelper.getStatusPaymentBye();
            assertEquals(expected, actual);
        }
    }

    @Test
        ///должна быть ошибка?
    void shouldValidByeDeclined() {
        servisPage.clear();
        val cardNumber = DataHelper.getSecondCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.expectApprovadBank();
        val expected = DataHelper.getSecondCardStatus();
        if (expected == DataHelper.getSecondCardStatus()) {
            servisPage.errorBankRefusal();
        } else {
            val actual = DBHelper.getStatusPaymentBye();
            assertEquals(expected, actual);
        }

    }

    @Test
    void shouldRandomCard() {
        servisPage.clear();
        val cardNumber = DataHelper.getRandomCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorBankRefusal();
    }

    @Test
    void shouldCardNumberHalfway() {
        servisPage.clear();
        val cardNumber = DataHelper.getCardNumberHalfway();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
    void shouldZeroCardNumber() {
        servisPage.clear();
        val cardNumber = DataHelper.getZeroCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorBankRefusal();
    }

    @Test
    void shouldCardNumberEmpty() {
        servisPage.clear();
        val cardNumber = DataHelper.getCardNumberEmpty();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
        //проходит операция с 00 месяцем
    void shouldZeroMonth() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getZeroMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
    void shouldInvalidMonth() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getInvalidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidDurationCard();
    }

    @Test
    void shouldEmptyMonth() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
    void shouldInvalidYear() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorCardExpired();
    }

    @Test
    void shouldEmptyYear() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getEmptyYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
        /// допускает русские буквы
    void shouldInvalidOwnerCard() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getInvalidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
    void shouldEmptyOwnerCard() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getEmptyOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorFieldMandatory();
    }

    @Test
        // в поле владелец допускает цифры
    void shouldInvalidOwnerCardNumbers() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getInvalidOwnerCardNumbers();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
        // в поле владелец допускает символы
    void shoudlInvalidOwnerCardSymbols() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getInvalidOwnerCardSymbols();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
        // в поле владелец допускаются прописные символы
    void shoudlInvalidOwnerCardUppercaseLetters() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getInvalidOwnerCardUppercaseLetters();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }


    @Test
    void shoulInvalidCvs() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getInvalidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
    void shoulEmptyCvs() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getEmptyCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
        //проходит CVS 000 ошибка или нет?
    void shoulZeroCvs() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getZeroCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
        // в поле владелец 1 символ
    void shoudlInvalidOwnerCardMinSymbols() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getEmptyOwnerMinSymbols();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorOwner();
    }

    @Test
        // в поле владелец 100 символов
    void shoudlInvalidOwnerCardMaxSymbols() {
        servisPage.clear();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getEmptyOwnerCardMaxSumbols();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorOwner();
    }


}
