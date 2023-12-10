import java.util.List;

public class ElementChecker {
    public static boolean checkAllElements(List<Integer> myList) {
        List<Integer> elementsToCheck = List.of(0, 1, 2, 3);
        return myList.containsAll(elementsToCheck);
    }
}
