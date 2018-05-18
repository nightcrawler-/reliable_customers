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

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        // write your code here

        List<CustomerRecord> results = getBestCustomers(args[0], Integer.parseInt(args[1]));

        for (CustomerRecord result : results) {
            System.out.print(result.account + ", ");
        }
    }


    public static List<CustomerRecord> getBestCustomers(String filepath, int max) throws FileNotFoundException, ParseException {
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
                customerRecord.currentStreak = current.currentStreak;

                if (customerRecord.date.get(Calendar.DAY_OF_YEAR) - current.date.get(Calendar.DAY_OF_YEAR) == 1) {
                    customerRecord.currentStreak++;
                }
                results.put(customerRecord.account, customerRecord);

            }

        }

        List<CustomerRecord> recordsByStreak = new ArrayList<>(results.values());

        recordsByStreak.sort(new Comparator<CustomerRecord>() {
            @Override
            public int compare(CustomerRecord o1, CustomerRecord o2) {
                if (o1.currentStreak < o2.currentStreak) {
                    return 1;
                } else if (o1.currentStreak > o2.currentStreak) {
                    return -1;
                } else {
                    return o1.account.compareToIgnoreCase(o2.account);
                }
                //instead of returning 0 when equal, further sort by name

            }
        });

        for (CustomerRecord customerRecord : recordsByStreak) {
            System.out.println(customerRecord.toString());
        }

        return recordsByStreak.subList(0, max);
    }

    private static ArrayList<CustomerRecord> parseRecords(Scanner scanner) throws ParseException {
        ArrayList<CustomerRecord> customerRecords = new ArrayList<>();

        while (scanner.hasNext()) {
            String[] data = scanner.nextLine().split(",");
            customerRecords.add(new CustomerRecord(data[0], parseDate(data[2]), 0));
        }

        customerRecords.sort(new Comparator<CustomerRecord>() {
            @Override
            public int compare(CustomerRecord o1, CustomerRecord o2) {
                return o1.date.compareTo(o2.date);
            }
        });

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

        @Override
        public String toString() {
            return "CustomerRecord{" +
                    "account='" + account + '\'' +
                    ", date=" + date.getTime() +
                    ", currentStreak=" + currentStreak +
                    '}';
        }
    }

}
