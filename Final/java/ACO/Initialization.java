import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Initialization {

    public static void main(String[] args) {
        // Example usage of the initialization function
        ArrayList<Molecule> molecules = initialization(20, Arrays.asList("acg", "cat", "gtt", "tgc"));
        for (Molecule m : molecules) {
            System.out.println(m);
        }
    }

    /**
     * Inserts a string into another string at a specified position.
     *
     * @param srcString      The original string.
     * @param insertedString The string to be inserted.
     * @param pos            The position at which to insert the string.
     * @return The modified string after insertion.
     */
    public static String insertSymbol(String srcString, String insertedString, int pos) {
        // Implementation details for inserting a string into another string.
        return new StringBuilder(srcString).insert(pos, insertedString).toString();
    }

    /**
     * Generates a supersequence by randomly combining strings from a set.
     *
     * @param setOfStrings The set of strings to combine.
     * @return The generated supersequence.
     */
    public static String supersequenceGenerate(List<String> setOfStrings) {
        // Implementation details for generating a supersequence.
        Random random = new Random();
        List<String> copiedSetOfStrings = new ArrayList<>(setOfStrings);
        String supersequence = copiedSetOfStrings.remove(random.nextInt(copiedSetOfStrings.size()));

        for (String s : copiedSetOfStrings) {
            int counter = 0;
            for (char j : s.toCharArray()) {
                int insertedPos = random.nextInt(supersequence.length() - counter + 1) + counter;
                if (insertedPos == supersequence.length() || j != supersequence.charAt(insertedPos)) {
                    supersequence = insertSymbol(supersequence, String.valueOf(j), insertedPos);
                }
                counter = insertedPos + 1;
            }
        }
        return supersequence;
    }

    /**
     * Generates a population of strings by repeatedly creating supersequences.
     *
     * @param popSize      The size of the population.
     * @param setOfStrings The set of strings to use for supersequence generation.
     * @return The generated population of strings.
     */
    public static List<String> populationGeneration(int popSize, List<String> setOfStrings) {
        // Implementation details for generating a population of strings.
        List<String> population = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            population.add(supersequenceGenerate(setOfStrings));
        }
        return population;
    }

    /**
     * Encodes a population of strings into a list of integers based on a character-to-integer mapping.
     *
     * @param initialPopulation The initial population of strings.
     * @return The encoded population as a list of integers.
     */
    public static ArrayList<String> encodingPopulation(List<String> initialPopulation) {
        // Implementation details for encoding a population of strings.
        ArrayList<String> encodedPopulation = new ArrayList<>();
        Map<Character, Integer> dict = Map.of('a', 0, 'c', 1, 'g', 2, 't', 3);

        for (String s : initialPopulation) {
            StringBuilder k = new StringBuilder();
            for (char c : s.toCharArray()) {
                k.append(dict.get(c));
            }
            encodedPopulation.add(k.toString());
        }
        return encodedPopulation;
    }

    /**
     * Creates a molecule object based on an encoded string.
     *
     * @param string The encoded string.
     * @return The created molecule.
     */
    public static Molecule createMolecule(String string) {
        // Implementation details for creating a molecule.
        Random random = new Random();
        List<Integer> l = string.chars().map(Character::getNumericValue).boxed().collect(Collectors.toList());
        ArrayList<Integer> supersequence = new ArrayList<>();
        l.forEach(t -> supersequence.add(t));
        return new Molecule(new double[]{random.nextDouble(), random.nextDouble()}, supersequence);
    }

    /**
     * Initializes a list of molecules based on a population of strings.
     *
     * @param popSize      The size of the population.
     * @param setOfStrings The set of strings to use for supersequence generation.
     * @return The list of initialized molecules.
     */
    public static ArrayList<Molecule> initialization(int popSize, List<String> setOfStrings) {
        // Implementation details for initializing molecules.
        List<String> initialPopulation = populationGeneration(popSize, setOfStrings);
        System.out.println("Population Generation:");
        System.out.println(initialPopulation);

        List<String> encodedPopulation = encodingPopulation(initialPopulation);
        System.out.println("Encoding:");
        System.out.println(encodedPopulation);

        ArrayList<Molecule> molecules = new ArrayList<>();
        for (String s : encodedPopulation) {
            molecules.add(createMolecule(s));
        }
        return molecules;
    }
}
