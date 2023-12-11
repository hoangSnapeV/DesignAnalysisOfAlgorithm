import java.util.ArrayList;
import java.util.List;

public class MainTestABC {
    public static void main(String[] args) {
        // Test data
        ArrayList<String> initialPop = new ArrayList<>(List.of("acg", "cat", "gtt", "tgc", "tcc"));
        ArrayList<String> encodedPopulation = Initialization_IMCRO.encodingPopulation(initialPop);
        List<Integer> molecule = Decoder.decodeToIntList(encodedPopulation);

        int n = initialPop.size();
        List<Integer> frequencies = FrequencyCalculator.frequency(molecule);

        // Test checkAllElements function

        ArtificialBeeColony moleculeColony = new ArtificialBeeColony(initialPop, molecule, frequencies, 1000, n, 1000);
        List<Integer> bestStructure = moleculeColony.solve();
        System.out.println(bestStructure);
    }
}
