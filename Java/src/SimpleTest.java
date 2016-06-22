import diffprocessor.Processor;
import diffprocessor.SortedLimitedList;

import java.util.List;

/**
 * Created by VavilauA on 6/19/2015.
 */
public class SimpleTest {
    static Processor diffProcessor = new Processor(10);
    static SortedLimitedList<Double> sortedList1 = new SortedLimitedList<Double>(10);
    static SortedLimitedList<Double> sortedList2 = new SortedLimitedList<Double>(10);
    static SortedLimitedList<Double> sortedList1_ = new SortedLimitedList<Double>(10);
    static SortedLimitedList<Double> sortedList2_ = new SortedLimitedList<Double>(10);
    static int test = 0;
    static void doTest(int operations) {
        try {
            diffProcessor.doProcess(sortedList1, sortedList2);
            if (!sortedList1.equals(sortedList2) || !sortedList1.equals(sortedList2_) || sortedList1.getPerformedOperations() != operations) {
                System.out.println("Test case: " + ++test + " Failed. Inputs: mustBeEqual: [" + sortedList1_ + "] expected: [" + sortedList2_ + "]");
                System.out.println("Test case: " + ++test + " Failed. Inputs: mustBeEqual: [" + sortedList1_ + "] expected: [" + sortedList2_ + "]");
                System.out.println("Your output " + sortedList1);
                System.out.println("Performed operations: " + sortedList1.getPerformedOperations() + ", expected " + operations);
                System.out.println();
            } else {
                System.out.println("Test case: " + ++test + " Succeed. Inputs: mustBeEqual: [" + sortedList1_ + "] expected: [" + sortedList2_ + "]");
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Test case: " + ++test + " Exception. Inputs: mustBeEqual: [" + sortedList1_ + "] expected: [" + sortedList2_ + "]");
            System.out.println();
            diffProcessor.doProcess(sortedList1_, sortedList2_);
        }
    }

    static void test(Double[] array1, Double[] array2, int operations) {
        sortedList1.fromArray(array1);
        sortedList2.fromArray(array2);
        sortedList1_.fromArray(array1);
        sortedList2_.fromArray(array2);
        doTest(operations);
    }

    static void test(List<Double> list1, List<Double> list2, int operations) {
        sortedList1.fromList(list1);
        sortedList2.fromList(list2);
        sortedList1_.fromList(list1);
        sortedList2_.fromList(list2);
        doTest(operations);
    }


    public static void main(String[] args) {
        Double[] needToBeEqual_0 = { 0.0, 1.0, 2.0, 3.0, 4.0, 6.0, 7.0 };
        Double[] expected_0 = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0};
        test(needToBeEqual_0, expected_0, 1);

        Double[] needToBeEqual_1 = { 0.0, 1.0, 2.0, 3.0, 4.0, 6.0, 7.0};
        Double[] expected_1 = { 1.0, 2.0, 3.0, 3.0};
        test(needToBeEqual_1, expected_1, 5);

        Double[] needToBeEqual_2 = { 0.0 };
        Double[] expected_2 = { 7.0 };
        test(needToBeEqual_2, expected_2, 2);

        Double[] needToBeEqual_3 = { 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
        Double[] expected_3 = { 9.0, 9.0, 9.0, 9.0, 9.0, 9.0, 9.0, 9.0, 9.0, 9.0 };
        test(needToBeEqual_3, expected_3, 17);

        Double[] needToBeEqual_4 = { 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 3.0};
        Double[] expected_4 = { 0.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 3.0};
        test(needToBeEqual_4, expected_4, 16);

        Double[] needToBeEqual_5 = { 0.0, 1.0, 2.0, 3.0, 4.0, 6.0, 7.0};
        Double[] expected_5 = { };
        test(needToBeEqual_5, expected_5, 7);

        Double[] needToBeEqual_6 = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
        Double[] expected_6 = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        test(needToBeEqual_6, expected_6, 2);

        // Extra test
        Double[] needToBeEqual_7 = { 0.0, 0.0};
        Double[] expected_7 = { 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        test(needToBeEqual_7, expected_7, 10);


        Double[] needToBeEqual_8 = { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 2.0, 2.0, 2.0 };
        Double[] expected_8 = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        test(needToBeEqual_8, expected_8, 10);
    }
}
