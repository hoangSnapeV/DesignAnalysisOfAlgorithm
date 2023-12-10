
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {
   public static boolean isSubsequence(List<Integer> sub, List<Integer> main) {
        int subIndex = 0, mainIndex = 0;
        while (subIndex < sub.size() && mainIndex < main.size()) {
            if (sub.get(subIndex).equals(main.get(mainIndex))) {
                subIndex++;
            }
            mainIndex++;
        }
        return subIndex == sub.size();
    }

    public static boolean isSupersequence(List<Integer> seq) {
        List<List<Integer>> subsequences = Arrays.asList(
            Arrays.asList(0, 1, 2), 
            Arrays.asList(1, 0, 3), 
            Arrays.asList(2, 3, 3), 
            Arrays.asList(3, 2, 1)
        );

        for (List<Integer> subseq : subsequences) {
            if (!isSubsequence(subseq, seq)) {
                return false;
            }
        }
        return true;
    }

    // Main method for testing
    public static void main(String[] args) {
        // Example usage
        // ArrayList<Molecule> molecules = Initialization.initialization(20, Arrays.asList("acg", "cat", "gtt", "tgc"));
        // for(Molecule i : molecules){
        //     System.out.println(isSupersequence(i.getSupersequence()));
        // }
    } 
}
