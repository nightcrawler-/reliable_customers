package com.cafrecode;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void main() {
    }

    @Test
    void getBestCustomers() throws FileNotFoundException, ParseException {
        assertEquals(0, Main.getBestCustomers("/home/frederick/Desktop/dev/lendable/Lendable Coding Assessment - Reliable Customers/transaction_data_1.csv", 0).get("ACC1").currentStreak);
    }

    @Test
    void parseDate() throws ParseException {
        assertEquals("Mon Jan 02 00:00:00 EAT 2017", Main.parseDate("2017-01-02 00:00:00").toString());
    }

}