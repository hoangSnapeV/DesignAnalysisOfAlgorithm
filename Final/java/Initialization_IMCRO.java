
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


public class Initialization_IMCRO {

    public static String insertSymbol(String srcString, String insertedString, int pos) {
        return new StringBuilder(srcString).insert(pos, insertedString).toString();
    }

    /*
    Make a copy of the set_of_strings parameter for maintaining the original set
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
    /*
     * Create number of popsizes base on set of strings input, it called supersequences.   
     */
    public static List<String> populationGeneration(int popSize, List<String> setOfStrings) {
        List<String> population = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            population.add(supersequenceGenerate(setOfStrings));
        }
        return population;
    }

    /*
     * Represent symbols from the alphabet and the
    encoding sequence. Ex: a -> 0, c -> 1...
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

    public static Molecule createMolecule(String string) {
        Random random = new Random();
        List<Integer> l = string.chars().map(Character::getNumericValue).boxed().collect(Collectors.toList());
        ArrayList<Integer> supersequence = new ArrayList<>();
        l.forEach(t -> supersequence.add(t));
        return new Molecule(new double[]{random.nextDouble(), random.nextDouble()},supersequence);
    }

    /*
     * Create number of popsizes base on set of strings input, it called supersequences. Moreover, it use function encoding to repesent symbol to integer.    
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
