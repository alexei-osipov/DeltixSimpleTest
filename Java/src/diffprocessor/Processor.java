package diffprocessor;

/**
 * Solution for Deltix SimpleTest task by Alexei Osipov.
 *
 * @author VavilauA
 * @author Alexei Osipov
 */
public class Processor {
    private final long limit;

    public Processor(long limit) {
        this.limit = limit;
    }

    public void doProcess(SortedLimitedList<Double> mustBeEqualTo, SortedLimitedList<Double> expectedOutput) {
        // TODO: make "mustBeEqualTo" list equal to "expectedOutput".
        // 0. Processor will be created once and then will be used billion times.
        // 1. Use methods: AddFirst, AddLast, AddBefore, AddAfter, Remove to modify list.
        // 2. Do not change expectedOutput list.
        // 3. At any time number of elements in list could not exceed the "Limit".
        // 4. "Limit" will be passed into Processor's constructor. All "mustBeEqualTo" and "expectedOutput" lists will have the same "Limit" value.
        // 5. At any time list elements must be in non-descending order.
        // 6. Implementation must perform minimal possible number of actions (AddFirst, AddLast, AddBefore, AddAfter, Remove).
        // 7. Implementation must be fast and do not allocate excess memory.

        // This implementation scans both lists one by one element and updates "mustBeEqualTo" when necessary.

        // This implementation assumes that in usually mustBeEqualTo list's size limit is not reached during processing.
        // If that assumption holds true then processing finishes in single pass.
        // If not then full second pass will be executed.

        boolean syncComplete = syncListsPass(mustBeEqualTo, expectedOutput);
        if (!syncComplete) {
            // Do second pass
            syncComplete = syncListsPass(mustBeEqualTo, expectedOutput);
        }
        assert syncComplete;
    }

    /**
     * Tries to update {@code sourceList} to hold same elements as {@code targetList} in single pass.
     *
     * <p>It may fail to do that in single pass due to size limit of {@code sourceList}. Then
     * <ul>
     *     <li>Processing will switch to "limited mode". In "limited mode" all following "add*" operations on {@code sourceList} are skipped.</li>
     *     <li>At the end execution of method it's guarantied that {@code sourceList} contain only elements that included in {@code targetList}.</li>
     *     <li>Method will return {@code false}.</li>
     * </ul>
     *
     * It's guarantied that second execution of this method method will succeed.
     * First pass is guaranteed to remove all extra elements from {@code sourceList}.
     * Second pass is guaranteed to add all missing elements to {@code sourceList}.
     *
     * TODO: It's possible to return exact position where limit was reached. That will allow to execute second pass from the
     *
     * @param sourceList list that's being updated
     * @param targetList list that is not changed
     *
     * @return {@code true} if sourceList was successfully updated to be same as targetList or false otherwise
     */
    private boolean syncListsPass(SortedLimitedList<Double> sourceList, SortedLimitedList<Double> targetList) {
        SortedLimitedList.Entry<Double> source = sourceList.getFirst(); // "iterator" for "sourceList" list
        SortedLimitedList.Entry<Double> target = targetList.getFirst(); // "iterator" for "targetList" list
        SortedLimitedList.Entry<Double> free; // Temporary variable for "remove" operation
        boolean limitReached = false; // If true than we reached limit for "sourceList" and we have to execute second pass

        while (source != null || target != null) {
            if (source == null) {
                // Source list depleted => we need to copy tail from target
                copyTail(sourceList, target);
                break;
            }
            if (target == null) {
                // Target list depleted => we need to remove remaining elements from source list
                deleteTail(sourceList, source);
                break;
            }

            Double sourceVal = source.getValue();
            Double targetVal = target.getValue();
            int comparisonResult = sourceVal.compareTo(targetVal);
            if (comparisonResult == 0) {
                // Elements match, skip them
                source = source.getNext();
                target = target.getNext();
            } else if (comparisonResult < 0) {
                // sourceVal < targetVal
                // Source has extra value. We have to remove it.
                free = source;
                source = source.getNext();
                sourceList.remove(free);
            } else {
                // sourceVal > targetVal
                // Source has missing value. We have to add it.

                // We may hit size limit here. Check it.
                if (sourceList.getCount() == limit) {
                    limitReached = true;
                }

                if (!limitReached) {
                    sourceList.addBefore(source, targetVal);
                }

                target = target.getNext();
            }
        }
        return !limitReached;
    }

    private void copyTail(SortedLimitedList<Double> sourceList, SortedLimitedList.Entry<Double> target) {
        while (target != null) {
            // Note: we can't hit size limit here because at this point size of source is less than size of target list.
            sourceList.addLast(target.getValue());
            target = target.getNext();
        }
    }

    private void deleteTail(SortedLimitedList<Double> sourceList, SortedLimitedList.Entry<Double> source) {
        SortedLimitedList.Entry<Double> free;
        while (source != null) {
            free = source;
            source = source.getNext();
            sourceList.remove(free);
        }
    }
}
