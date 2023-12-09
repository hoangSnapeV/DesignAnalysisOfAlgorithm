

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String [] args){
        ArrayList<String> set_of_subsequences = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.print("Please Enter number of Subsequences : ");
        int number_of_subs = sc.nextInt();
        
        sc.nextLine();

        for(int i = 0; i<number_of_subs;i++){
            System.out.print("Sequence "+(i+1)+" : ");
            set_of_subsequences.add(sc.nextLine());
        }
        System.out.print("Please Enter number of Supersequence generated from the subsequences : ");
        int popSize = sc.nextInt();
        sc.close();


        ArrayList<Molecule> molecules = Initialization.initialization(popSize,set_of_subsequences);
        IMCRO algo = new MYIMCRO(molecules,set_of_subsequences);
        algo.run();
        System.out.println(algo.getMostOptimalMolecule());
    }
}
