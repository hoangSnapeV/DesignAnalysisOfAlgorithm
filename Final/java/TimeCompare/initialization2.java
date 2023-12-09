


import java.util.List;


public class initialization2 {
    public static List<String> initialization(int popSize, List<String> setOfStrings) {
        // Call populationGeneration method
        List<String> initialPopulation = Initialization.populationGeneration(popSize, setOfStrings);
        // Call encodingPopulation method
        List<String> encodedPopulation = Initialization.encodingPopulation(initialPopulation);

        return encodedPopulation ;
       
    }
    
    // Other methods if needed...

    public static void main(String[] args) {
        // Example usage
        int popSize = 10;
        List<String> setOfStrings = List.of("acg", "cat", "gtt", "tgc");
        List<String> result = initialization(popSize, setOfStrings);

        // Print or use the result as needed
        // System.out.println(result);
    }
    }

