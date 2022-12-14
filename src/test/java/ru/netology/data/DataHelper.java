package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
