import java.util.*;

public class FrequencyCalculator {

    /**
     * Calculates the frequency of each element in the given list.
     *
     * @param molecule List of integers.
     * @return List of integers sorted by frequency.
     */
    public static List<Integer> frequency(List<Integer> molecule) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        // Count the frequency of each symbol in the molecule
        for (int symbol : molecule) {
            frequencyMap.put(symbol, frequencyMap.getOrDefault(symbol, 0) + 1);
        }

        List<Integer> resultArray = new ArrayList<>();
        List<Integer> myKeys = new ArrayList<>(frequencyMap.keySet());
        Collections.sort(myKeys);

        // Populate the result array with elements sorted by frequency
        for (Integer key : myKeys) {
            int value = frequencyMap.get(key);
            for (int i = 0; i < value; i++) {
                resultArray.add(key);
            }
        }

        return resultArray;
    }
}
