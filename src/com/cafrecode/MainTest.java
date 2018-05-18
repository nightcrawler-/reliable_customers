package com.cafrecode;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void main() {
    }

    @Test
    void getBestCustomers() throws FileNotFoundException, ParseException {
        assertEquals(1, Main.getBestCustomers("/home/frederick/Desktop/dev/lendable/Lendable Coding Assessment - Reliable Customers/transaction_data_1.csv", 1).size());
        assertEquals("ACC2", Main.getBestCustomers("/home/frederick/Desktop/dev/lendable/Lendable Coding Assessment - Reliable Customers/transaction_data_1.csv", 1).get(0).account);

        assertEquals(2, Main.getBestCustomers("/home/frederick/Desktop/dev/lendable/Lendable Coding Assessment - Reliable Customers/transaction_data_2.csv", 2).size());
        assertEquals("ACC1", Main.getBestCustomers("/home/frederick/Desktop/dev/lendable/Lendable Coding Assessment - Reliable Customers/transaction_data_2.csv", 1).get(0).account);

        assertEquals(3, Main.getBestCustomers("/home/frederick/Desktop/dev/lendable/Lendable Coding Assessment - Reliable Customers/transaction_data_3.csv", 3).size());
        assertEquals("ACC143", Main.getBestCustomers("/home/frederick/Desktop/dev/lendable/Lendable Coding Assessment - Reliable Customers/transaction_data_3.csv", 3).get(0).account);


    }

    @Test
    void parseDate() throws ParseException {
        assertEquals("Mon Jan 02 00:00:00 EAT 2017", Main.parseDate("2017-01-02 00:00:00").toString());
    }

}