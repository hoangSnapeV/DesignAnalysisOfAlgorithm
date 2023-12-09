import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Define initial parameters
        int popSize = 20;
        List<String> initialPopulation = List.of("acg", "cat", "gtt", "tgc");

        // Encode the initial population and decode it to obtain the molecule
        List<String> encodedPopulation = Initialization.encodingPopulation(initialPopulation);
        List<Integer> decodedIntList = Decoder.decodeToIntList(encodedPopulation);

        // Calculate the number of molecules
        int n = initialPopulation.size();

        // Calculate frequencies for the molecule
        List<Integer> frequencies = FrequencyCalculator.frequency(decodedIntList);

        // Set the maximum number of cycles
        int maxCycles = 1000;

        // Create an instance of ArtificialBeeColony
        ArtificialBeeColony moleculeColony = new ArtificialBeeColony(initialPopulation, frequencies,decodedIntList, popSize, n, maxCycles);

        // Solve the optimization problem
        List<Integer> bestStructure = moleculeColony.solve();

        // Print the result
        System.out.println("Best Structure for ACB: " + bestStructure);
    }
}
