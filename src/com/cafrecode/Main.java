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

        List<CustomerRecord> results = getBestCustomers(args[0], Integer.parseInt(args[1]));

        for (CustomerRecord result : results) {
            System.out.print(result.account + ", ");
        }
    }

    /**
     * @param filepath
     * @param max      - the number of good customers to return
     * @return
     * @throws FileNotFoundException
     * @throws ParseException
     */
    public static List<CustomerRecord> getBestCustomers(String filepath, int max) throws FileNotFoundException, ParseException {
        //read file
        //skip first line -- is titles
        //next lines as string
        //establish sub strings by splitting at commas
        //transaction amount irrelevant
        //parse date

        //create list sorted by date
        //loop through list and determine if for each customer record, the previous payment falls a day back, if true, increment streak
        //convert resulting hashmap to list (un-ordered)
        //sort list by streak then by account name
        //split list at n

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
                customerRecord.streaks.add(1);
                results.put(customerRecord.account, customerRecord);
            } else {
                //compare date difference
                //decide increment or not
                customerRecord.currentStreak = current.currentStreak;
                customerRecord.streaks = current.streaks;

                if (customerRecord.date.get(Calendar.DAY_OF_YEAR) - current.date.get(Calendar.DAY_OF_YEAR) == 1) {
                    customerRecord.currentStreak++;
                    customerRecord.streaks.set(customerRecord.streaks.size() - 1, customerRecord.streaks.get(customerRecord.streaks.size() - 1) + 1);
                    //means streak is still on, add to**
                }else{
                    //means streak was broken, create new empty streak
                    customerRecord.streaks.add(1);
                }
                results.put(customerRecord.account, customerRecord);

            }

        }

        List<CustomerRecord> recordsByStreak = new ArrayList<>(results.values());

        recordsByStreak.sort(new Comparator<CustomerRecord>() {
            @Override
            public int compare(CustomerRecord o1, CustomerRecord o2) {
                if (o2.getLongestStreak() > o1.getLongestStreak()) {
                    return 1;
                } else if (o2.getLongestStreak() < o1.getLongestStreak()) {
                    return -1;
                } else {
                    //instead of returning 0 when equal, further sort by name
                    return o1.account.compareToIgnoreCase(o2.account);
                }

            }
        });

        for (CustomerRecord customerRecord : recordsByStreak) {
            System.out.println(customerRecord.toString());
        }

        return recordsByStreak.subList(0, max);
    }

    /**
     * Return valid objects from csv file
     *
     * @param scanner
     * @return
     * @throws ParseException
     */
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

    /**
     * Converts date strings (fixed format) to Calendar object
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Calendar parseDate(String date) throws ParseException {
        //2017-01-02 00:00:00
        String pattern = "yyy-MM-dd HH:mm:ss";
        SimpleDateFormat parser = new SimpleDateFormat(pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(parser.parse(date));
        return cal;
    }

    /**
     * S
     */
    static class CustomerRecord {
        public String account;
        public Calendar date;
        public int currentStreak;

        //should keep a list of streaks, and have an accessor for longest streak
        public List<Integer> streaks = new ArrayList<>();

        public CustomerRecord(String account, Calendar date, int currentStreak) {
            this.account = account;
            this.date = date;
            this.currentStreak = currentStreak;
        }

        public int getLongestStreak() {
            streaks.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.compareTo(o1);
                }
            });

            return streaks.get(0);
        }

        @Override
        public String toString() {
            return "CustomerRecord{" +
                    "account='" + account + '\'' +
                    ", date=" + date.getTime() +
                    ", currentStreak=" + currentStreak +
                    ", streaks=" + getLongestStreak() +
                    '}';
        }
    }

}
