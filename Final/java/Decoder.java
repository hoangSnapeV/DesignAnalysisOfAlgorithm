
import java.util.ArrayList;
import java.util.List;

public class Decoder {
    /*
    OBject: Decodes an encoded population to a list of integers.

    Input:
        encoded_population (list<string>): List of encoded strings.

    Output:
        A list <int> decoded from the given encoded population.
     */
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
