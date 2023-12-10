import java.util.List;

public class initialization2 {

    /**
     * Initializes a list of encoded strings based on a population of strings.
     *
     * @param popSize      The size of the population.
     * @param setOfStrings The set of strings to use for supersequence generation.
     * @return The list of initialized encoded strings.
     */
    public static List<String> initialization(int popSize, List<String> setOfStrings) {
        // Call populationGeneration method from Initialization class
        List<String> initialPopulation = Initialization.populationGeneration(popSize, setOfStrings);
        // Call encodingPopulation method from Initialization class
        List<String> encodedPopulation = Initialization.encodingPopulation(initialPopulation);

        return encodedPopulation;
    }

    // Other methods if needed...

    public static void main(String[] args) {
        // Example usage
        int popSize = 10;
        List<String> setOfStrings = List.of("acg", "cat", "gtt", "tgc");
        List<String> result = initialization(popSize, setOfStrings);

        // Print or use the result as needed
        System.out.println(result);
    }
}
