// import java.util.ArrayList;
// import java.util.List;

// public class Decoder {
//     public static List<Integer> decodeToIntList(List<String> encodedPopulation) {
//         List<Integer> intList = new ArrayList<>();
//         for (String encodedStr : encodedPopulation) {
//             for (char c : encodedStr.toCharArray()) {
//                 intList.add(Character.getNumericValue(c));
//             }
//         }
//         return intList;
//     }
// }


import java.util.ArrayList;
import java.util.List;

public class Decoder {
    public static ArrayList<Integer> decodeToIntList(List<String> encodedPopulation) {
        ArrayList<Integer> intList = new ArrayList<>();
        for (String encodedStr : encodedPopulation) {
            for (char c : encodedStr.toCharArray()) {
                intList.add(Character.getNumericValue(c));
            }
        }
        return intList;
    }
}
