# Reliable Customers

Code challenge:

        //read file
        //skip first line -- is titles
        //next lines as string
        //establish sub strings by splitting at commas
        //transaction amount irrelevant
        //parse date

        //create list sorted by date (assumption of ordered list fails)
        //loop through list and determine if for each customer record, the previous payment falls a day back, if true, increment streak
        //multiple streaks per customer, resort to the largest for comparison
        //convert resulting hashmap to list (un-ordered)
        //sort list by streak then by account name
        //split list at n

### Prerequisites

Java 8
JUnit5

## Running the tests

With JUnit5 in your classpath, swap the file paths on MainTest.java with the actual locations on the local machine.

## Authors

* **Frederick Nyawaya**
