package ru.netology.domain.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.domain.data.DBHelper;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.page.ServisPage;

import static com.codeborne.selenide.Configuration.startMaximized;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBuyCredit {

   ServisPage servisPage = new ServisPage();

    @BeforeEach
    void shouldCleanDataBaseAndOpenWeb() {
        startMaximized = true;
        open(System.getProperty("website"));
        servisPage.buyCredit();
        servisPage.clear();
    }
    @AfterEach
    void cleanDataBases() {
        DBHelper.cleanDB();
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
            val actual = DBHelper.getStatusPaymentByeCredit();
            assertEquals(expected, actual);
        }
    }

    @Test
        ///должна быть ошибка?
    void shouldValidByeDeclined() {
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
            val actual = DBHelper.getStatusPaymentByeCredit();
            assertEquals(expected, actual);
        }
    }


    @Test
    void shouldRandomCard() {
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
        val cardNumber = DataHelper.getCardNumberEmpty();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
        //проходит операция с 00 месяцом
    void shoulZeroMonth() {
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getZeroMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
    void shoulInvalidMonth() {
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getInvalidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidDurationCard();
    }

    @Test
    void shoulEmptyMonth() {
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
    void shoulInvalidYear() {
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidYear();
        val owner = DataHelper.getValidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorCardExpired();
    }

    @Test
    void shoulEmptyYear() {
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
    void shoulInvalidOwnerCard() {
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getInvalidOwnerCard();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorInvalidFormat();
    }

    @Test
    void shoulEmptyOwnerCard() {
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
    void shoulInvalidOwnerCardNumbers() {
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
    void shoulInvalidOwnerCardSymbols() {
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
    void shoulInvalidOwnerCardUppercaseLetters() {
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
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getEmptyOwnerMinSymbols();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorOwner();
    }

    @Test
        // в поле владелец 100 симвалов
    void shoudlInvalidOwnerCardMaxSymbols() {
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getEmptyOwnerCardMaxSumbols();
        val cvs = DataHelper.getValidCvs();
        servisPage.fillFields(cardNumber, month, year, owner, cvs);
        servisPage.errorOwner();
    }
}