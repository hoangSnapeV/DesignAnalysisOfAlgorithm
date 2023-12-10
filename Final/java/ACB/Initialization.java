import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Initialization {

    public static void main(String[] args) {
        // Example usage
        ArrayList<Molecule> molecules = initialization(20, Arrays.asList("acg", "cat", "gtt", "tgc"));
        for (Molecule m : molecules) {
            System.out.println(m);
        }
    }

    /**
     * Inserts a symbol into a string at a specified position.
     *
     * @param srcString      The original string.
     * @param insertedString The string to be inserted.
     * @param pos            The position at which to insert the string.
     * @return The modified string.
     */
    public static String insertSymbol(String srcString, String insertedString, int pos) {
        return new StringBuilder(srcString).insert(pos, insertedString).toString();
    }

    /**
     * Generates a supersequence by combining a set of strings.
     *
     * @param setOfStrings The set of strings to combine.
     * @return The generated supersequence.
     */
    public static String supersequenceGenerate(List<String> setOfStrings) {
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
     * Generates a population of supersequences.
     *
     * @param popSize      The size of the population.
     * @param setOfStrings The set of strings to use for supersequence generation.
     * @return The generated population.
     */
    public static List<String> populationGeneration(int popSize, List<String> setOfStrings) {
        List<String> population = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            population.add(supersequenceGenerate(setOfStrings));
        }
        return population;
    }

    /**
     * Encodes a population of supersequences using a character-to-integer mapping.
     *
     * @param initialPopulation The initial population of supersequences.
     * @return The encoded population.
     */
    public static ArrayList<String> encodingPopulation(List<String> initialPopulation) {
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
        Random random = new Random();
        List<Integer> l = string.chars().map(Character::getNumericValue).boxed().collect(Collectors.toList());
        ArrayList<Integer> supersequence = new ArrayList<>();
        l.forEach(t -> supersequence.add(t));
        return new Molecule(new double[]{random.nextDouble(), random.nextDouble()}, supersequence);
    }

    /**
     * Initializes a list of molecules based on a given population and set of strings.
     *
     * @param popSize       The size of the population.
     * @param setOfStrings  The set of strings to use for supersequence generation.
     * @return The list of initialized molecules.
     */
    public static ArrayList<Molecule> initialization(int popSize, List<String> setOfStrings) {
        List<String> initialPopulation = populationGeneration(popSize, setOfStrings);
       
        List<String> encodedPopulation = encodingPopulation(initialPopulation);
       
        ArrayList<Molecule> molecules = new ArrayList<>();
        for (String s : encodedPopulation) {
            molecules.add(createMolecule(s));
        }
        return molecules;
    }
}
