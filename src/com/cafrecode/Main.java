package com.cafrecode;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by frederick on 18/05/2018
 */
public class Main {

    public static void main(String[] args) {
        // write your code here
    }


    public static CustomerRecord getBestCustomers(String filepath, int max) throws FileNotFoundException, ParseException {
        //read file
        //skip first line as is titles
        //next lines as string
        //establish sub strings by splitting at commas
        //transaction amount irrelevant
        //parse date
        //work magic
        //ordered list, start with date, establish trend
        //content already sorted by date
        //algo to count consecutive occurances of account number*
        //even though sorted by date, assumptions for consecutive days will fail
        //check if next date is consecutive

        Scanner scanner = new Scanner(new File(filepath));
        scanner.nextLine();

        //to find streak:
        //use Calendar class for comparison? only increment by one if new date is next to current date
        HashMap<String, CustomerRecord> results = new HashMap<>();

        for (CustomerRecord customerRecord : parseRecords(scanner)) {
            //try get record and put new if null, increment streak
            //if not null perfom ops
            CustomerRecord current = results.get(customerRecord.account);

            if (current == null) {
                customerRecord.currentStreak++;
                results.put(customerRecord.account, customerRecord);
            } else {
                //compare date difference
                //decide increment or not
                if (customerRecord.date.get(Calendar.DAY_OF_YEAR) - current.date.get(Calendar.DAY_OF_YEAR) == 1) {
                    customerRecord.currentStreak = current.currentStreak;
                    customerRecord.currentStreak++;
                    results.put(customerRecord.account, customerRecord);
                }
            }

        }

        List<CustomerRecord> recordsByStreak = new ArrayList<>(results.values());

        Comparator<CustomerRecord> sortByStreak = (p, o) -> p.currentStreak;
        Comparator<CustomerRecord> sortByAccount = (p, o) -> p.account.compareToIgnoreCase(o.account);

        recordsByStreak.stream().sorted(sortByStreak.thenComparing(sortByAccount));

        return recordsByStreak.get(0);
    }

    private static ArrayList<CustomerRecord> parseRecords(Scanner scanner) throws ParseException {
        ArrayList<CustomerRecord> customerRecords = new ArrayList<>();

        while (scanner.hasNext()) {
            String[] data = scanner.nextLine().split(",");
            customerRecords.add(new CustomerRecord(data[0], parseDate(data[2]), 0));
        }
        return customerRecords;
    }

    public static Calendar parseDate(String date) throws ParseException {
        //2017-01-02 00:00:00
        String pattern = "yyy-MM-dd HH:mm:ss";
        SimpleDateFormat parser = new SimpleDateFormat(pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(parser.parse(date));
        return cal;
    }

    static class CustomerRecord {
        public String account;
        public Calendar date;
        public int currentStreak;

        public CustomerRecord(String account, Calendar date, int currentStreak) {
            this.account = account;
            this.date = date;
            this.currentStreak = currentStreak;
        }
    }

}
