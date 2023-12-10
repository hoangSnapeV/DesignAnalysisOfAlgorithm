
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maintest {
  
    // Main method for testing
    public static void main(String[] args) {
        List<String> initialPop = List.of("acg", "cat", "gtt", "tgc");
        int popsize = 100;
        int iteration = 1000;
        AntColonyOptimizer acoInstance = new AntColonyOptimizer(initialPop, popsize, iteration);
        List<Integer> result = acoInstance.solve();
        System.out.println(result);
    } 
}
