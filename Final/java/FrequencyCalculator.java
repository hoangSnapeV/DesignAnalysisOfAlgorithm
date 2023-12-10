// import java.util.*;

// public class FrequencyCalculator {
//     public static List<Integer> frequency(List<Integer> molecule) {
//         Map<Integer, Integer> array1 = new HashMap<>();
//         for (int symbol : molecule) {
//             array1.put(symbol, array1.getOrDefault(symbol, 0) + 1);
//         }

//         List<Integer> resultArray = new ArrayList<>();
//         List<Integer> myKeys = new ArrayList<>(array1.keySet());
//         Collections.sort(myKeys);

//         for (Integer key : myKeys) {
//             int value = array1.get(key);
//             for (int i = 0; i < value; i++) {
//                 resultArray.add(key);
//             }
//         }

//         return resultArray;
//     }
// }


import java.util.*;

public class FrequencyCalculator {
    public static <T extends Comparable<T>> List<T> frequency(List<T> molecule) {
        Map<T, Integer> array1 = new HashMap<>();
        for (T symbol : molecule) {
            array1.put(symbol, array1.getOrDefault(symbol, 0) + 1);
        }

        List<T> resultArray = new ArrayList<>();
        List<T> myKeys = new ArrayList<>(array1.keySet());
        myKeys.sort(Comparator.naturalOrder());

        for (T key : myKeys) {
            int value = array1.get(key);
            for (int i = 0; i < value; i++) {
                resultArray.add(key);
            }
        }

        return resultArray;
    }
}
