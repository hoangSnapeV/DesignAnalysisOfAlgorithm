
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Operators {

    public static void main(String [] args){
        Operators operators = new Operators();

        int [] m1 = {0,2,3,1,2};
        int [] m2 = {2,3,0,0,2};
        
        ArrayList<Integer> mol1 = new ArrayList<Integer>();
        ArrayList<Integer> mol2 = new ArrayList<Integer>();
        for(int i : m1){
            mol1.add(i);
        }
        for(int i : m2){
            mol2.add(i);
        }
        // for(ArrayList<Integer> i :operators.intermolecular(mol1, mol2)){
        //     System.out.println(i.toString());
        // }
        // System.out.println(operators.decomposition(mol1));

        // System.out.println(operators.synthesis(mol1, mol2));
    }

    // OnWall Ineffective Collision
    public ArrayList<Integer> onWall(ArrayList<Integer> molecule) {
        ArrayList<Integer> m = new ArrayList<>(molecule);
        Random rand = new Random();
        int vLow = 0;
        int vUpper = 3;
        int i = rand.nextInt(molecule.size());
        int j = rand.nextInt(vUpper - vLow + 1) + vLow;

        if (molecule.get(i) + j <= vUpper) {
            m.set(i, molecule.get(i) + j);
        } else {
            if (molecule.get(i) - j < 0) {
                m.set(i, 0);
            } else {
                m.set(i, molecule.get(i) - j);
            }
        }
        return m;
    }

    // Intermolecular Ineffective Collision
    public ArrayList<ArrayList<Integer>> intermolecular(ArrayList<Integer> molecule1, ArrayList<Integer> molecule2) {
        int length1 = molecule1.size();
        int length2 = molecule2.size();

        ArrayList<Integer> m1 = new ArrayList<>(molecule1);
        ArrayList<Integer> m2 = new ArrayList<>(molecule2);
        ArrayList<Integer> m11 = new ArrayList<>();
        ArrayList<Integer> m22 = new ArrayList<>();

        for(int i = 0;i <length1;i++){
            m11.add(0);
        }
        for(int i = 0;i < length2;i++){
            m22.add(0);
        }



        int limit = Math.min(length1, length2);

        Random rand = new Random();
        int x1 = rand.nextInt(limit - 1);
        int x2 = rand.nextInt((limit - x1 - 1)) + x1 + 1;


        for (int i = 0; i < limit; i++) {
            if (i < x1 || i > x2) {
                m1.set(i,m1.get(i));
                m2.set(i,m2.get(i));
            } else if(i>=x1 && x2>=i) {
                m1.set(i,m2.get(i));
                m2.set(i,m1.get(i));
            }
        }

        // Second crossover for m1
        for (int j = 0; j < length1; j++) {
            if (j < x1) {
                m11.set(length1 - x1 + j, m1.get(j));
            } else if (j >= x1 && j <= x2) {
                m11.set(length1 - x1 - x2 + j - 1, m1.get(j));
            } else {
                m11.set(j - x2 - 1, m1.get(j));
            }
        }

        // Second crossover for m2
        for (int j = 0; j < length2; j++) {
            if (j < x1) {
                m22.set(length2 - x1 + j, m2.get(j));
            } else if (j >= x1 && j <= x2) {
                m22.set(length2 - x1 - x2 + j - 1, m2.get(j));
            } else {
                m22.set(j - x2 - 1, m2.get(j));
            }
        }
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        result.add(m11);
        result.add(m22);

        // Return as an array of ArrayList<Integer>
        return result;
    }

    public ArrayList<ArrayList<Integer>> decomposition(ArrayList<Integer> molecule){

        int moleculeLength = molecule.size();
        
        Random rand = new Random();
        int a = rand.nextInt(moleculeLength + 1) - moleculeLength; // a is from -len(molecule) to 0
        int b = rand.nextInt(moleculeLength + 1); // b is from 0 to len(molecule)

        ArrayList<Integer> m1 = new ArrayList<>();
        ArrayList<Integer> m2 = new ArrayList<>();

        for(int i = 0;i <moleculeLength;i++){
            m1.add(0);
        }
        for(int i = 0;i < moleculeLength ;i++){
            m2.add(0);
        }

        for(int i = 0 ;i<moleculeLength;i++){
            if(i+1<=-a){
                m1.set(moleculeLength-(-a)+i, molecule.get(i));
            }else{
                m1.set(i-(-a), molecule.get(i));
            }
        }

        for(int j =0;j<moleculeLength;j++){
            if(j+1<=moleculeLength-b){
                m2.set(j+b, molecule.get(j));
            }else{
                m2.set(j-moleculeLength+b, molecule.get(j));
            }
        }

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        result.add(m1);
        result.add(m2);

        return result;
    }
    public ArrayList<Integer> synthesis(ArrayList<Integer> molecule1,ArrayList<Integer> molecule2 )
    {
        HashMap <Integer, Integer> array1 = new HashMap<>();
        for(Integer i : molecule1){
            if (!array1.containsKey(i)) {
               array1.put(i, 0); 
            }
            array1.put(i, array1.get(i)+1);
        }

        HashMap <Integer, Integer> array2 = new HashMap<>();
        for(Integer i : molecule2){
            if (!array2.containsKey(i)) {
               array2.put(i, 0); 
            }
            array2.put(i, array2.get(i)+1);
        }

        int molecule1Length = molecule1.size();
        int molecule2Length = molecule2.size();
        int limit = Math.min(molecule1Length, molecule2Length);


        ArrayList<Integer> m_prime ;
        if (molecule1Length<molecule2Length) {
            m_prime = new ArrayList<>(molecule2);
        }else{
            m_prime = new ArrayList<>(molecule1);
        }

        for(int i =0 ;i<limit;i++){
            int symbol1 = molecule1.get(i);
            int symbol2 = molecule2.get(i);

            int frequency_in_array1 = array1.getOrDefault(symbol1,0);
            int frequency_in_array2 = array2.getOrDefault(symbol2,0);

            if(frequency_in_array1 >= frequency_in_array2){
                m_prime.set(i, symbol1);
            }else{
                m_prime.set(i, symbol2);
            }
        }
        return m_prime;
    }


}