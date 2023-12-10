import java.util.List;

public class ElementChecker {

    /**
     * Checks if all elements from a predefined list are present in the given list.
     *
     * @param myList List of integers to check.
     * @return True if all elements are present, false otherwise.
     */
    public static boolean checkAllElements(List<Integer> myList) {
        List<Integer> elementsToCheck = List.of(0, 1, 2, 3);
        return myList.containsAll(elementsToCheck);
    }
}
