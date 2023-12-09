import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        // Test data
        List<Integer> molecule = List.of(0, 1, 2, 3);
        List<String> encodedPopulation = List.of("0123", "3012", "2301");
        
        // Test frequency function
        List<Integer> frequencies = FrequencyCalculator.frequency(molecule);
        System.out.println("Frequencies: " + frequencies);

        // Test decodeToIntList function
        List<Integer> decodedIntList = Decoder.decodeToIntList(encodedPopulation);
        System.out.println("Decoded Int List: " + decodedIntList);

        // Test checkAllElements function
        boolean allElementsPresent = ElementChecker.checkAllElements(decodedIntList);
        System.out.println("All Elements Present: " + allElementsPresent);
    }
}
