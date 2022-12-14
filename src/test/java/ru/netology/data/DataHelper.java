package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getMonth(0);
        String year = getYear(1);
        String cvv = faker.number().digits(3);
        return new Card("1444444444444444", month, year, holder, cvv);
    }

}
