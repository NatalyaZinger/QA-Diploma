package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Card {
        private String cardNumber;
        private String month;
        private String year;
        private String cardHolder;
        private String cvv;
    }

    public static Card getApprovedCard() {
        return new Card("4444444444444441", "11", "23", "Alex Pushkin", "456");
    }

    public static Card getDeclinedCard() {
        return new Card("4444444444444442", "11", "23", "Alex Pushkin", "456");
    }

    public static Card getEmptyCard() {
        return new Card("", "", "", "", "");
    }

    public static String getMonth(int plusMonth) {
        return LocalDate.now().plusMonths(plusMonth).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getYear(int plusYear) {
        return LocalDate.now().plusYears(plusYear).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static Card getCardNotInBase() {
        Faker faker = new Faker();
        String number = faker.number().digits(16);
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getMonth(0);
        String year = getYear(1);
        String cvv = faker.number().digits(3);
        return new Card(number, month, year, holder, cvv);
    }

    public static Card getNumberCard11Symbols() {
        Faker faker = new Faker();
        String number = faker.number().digits(11);
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getMonth(0);
        String year = getYear(1);
        String cvv = faker.number().digits(3);
        return new Card(number, month, year, holder, cvv);
    }

    public static Card getCardPastMonth() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getMonth(-1);
        String year = getYear(0);
        String cvv = faker.number().digits(3);
        return new Card("4444444444444441", month, year, holder, cvv);
    }

    public static Card getCardPastYear() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getMonth(0);
        String year = getYear(-1);
        String cvv = faker.number().digits(3);
        return new Card("4444444444444441", month, year, holder, cvv);
    }

    public static Card getCardMonth00ThisYear() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String year = getYear(0);
        String cvv = faker.number().digits(3);
        return new Card("4444444444444441", "00", year, holder, cvv);
    }

    public static Card getCardMonth00FutureYear() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String year = getYear(1);
        String cvv = faker.number().digits(3);
        return new Card("4444444444444441", "00", year, holder, cvv);
    }


    public static Card getCardMonthOneNumber() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = faker.number().digit();
        String year = getYear(1);
        String cvv = faker.number().digits(3);
        return new Card("4444444444444441", month, year, holder, cvv);
    }

    public static Card getCardMonthOver12() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String year = getYear(0);
        String cvv = faker.number().digits(3);
        return new Card("4444444444444441", "13", year, holder, cvv);
    }

    public static Card getCardYearOverCurrentPlus5() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getMonth(0);
        String year = getYear(6);
        String cvv = faker.number().digits(3);
        return new Card("4444444444444441", month, year, holder, cvv);
    }

    public static Card getCardYear00() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getMonth(0);
        String cvv = faker.number().digits(3);
        return new Card("4444444444444441", month, "00", holder, cvv);
    }

    public static Card getCardYearOneNumber() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getMonth(0);
        String year = faker.number().digit();
        String cvv = faker.number().digits(3);
        return new Card("4444444444444441", month, year, holder, cvv);
    }

    public static Card getCyrillicCardHolder() {
        Faker faker = new Faker(new Locale("ru"));
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getMonth(0);
        String year = getYear(3);
        String cvv = faker.number().digits(3);
        return new Card("4444444444444441", month, year, holder, cvv);
    }

    public static Card getOneWorldCardHolder() {
        Faker faker = new Faker();
        String holder = faker.name().firstName();
        String month = getMonth(0);
        String year = getYear(3);
        String cvv = faker.number().digits(3);
        return new Card("4444444444444441", month, year, holder, cvv);
    }

    public static Card getSpecialSymbolsCardHolder() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " $%*&)";
        String month = getMonth(0);
        String year = getYear(3);
        String cvv = faker.number().digits(3);
        return new Card("4444444444444441", month, year, holder, cvv);
    }

    public static Card getNumberCardHolder() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.number().digit();
        String month = getMonth(0);
        String year = getYear(3);
        String cvv = faker.number().digits(3);
        return new Card("4444444444444441", month, year, holder, cvv);
    }

    public static Card getCVV2number() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getMonth(0);
        String year = getYear(1);
        String cvv = faker.number().digits(2);
        return new Card("4444444444444441", month, year, holder, cvv);
    }

    public static Card getCVV1number() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getMonth(0);
        String year = getYear(1);
        String cvv = faker.number().digits(1);
        return new Card("4444444444444441", month, year, holder, cvv);
    }

}
