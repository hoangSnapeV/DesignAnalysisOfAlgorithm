import java.util.List;

public class initialization_ACO {
    public static List<String> initialization(int popSize, List<String> setOfStrings) {
        // Call populationGeneration method
        List<String> initialPopulation = Initialization_IMCRO.populationGeneration(popSize, setOfStrings);
        // Call encodingPopulation method
        List<String> encodedPopulation = Initialization_IMCRO.encodingPopulation(initialPopulation);

        return encodedPopulation ;
       
    }

}

