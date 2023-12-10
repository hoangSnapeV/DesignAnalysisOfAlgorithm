import java.util.ArrayList;
import java.util.List;

public class Decoder {

    /**
     * Decodes a list of encoded strings to a list of integers.
     *
     * @param encodedPopulation List of encoded strings.
     * @return List of integers.
     */
    public static List<Integer> decodeToIntList(List<String> encodedPopulation) {
        List<Integer> intList = new ArrayList<>();
        for (String encodedStr : encodedPopulation) {
            for (char c : encodedStr.toCharArray()) {
                intList.add(Character.getNumericValue(c));
            }
        }
        return intList;
    }
}
