import java.util.*;

public abstract class IMCRO {
    /*
    popsize: popsize the List Of Molecule Instance
    ke: Kinetic energy
    numHit: Number of collisions by a molecule
    optimal : This attribute will hold the Molecule Instance with the lowest PE 
    kElossrate: Percentage of the upper limit of KE reduction
    molecoll: Threshold to determine the type of chemical reaction: uni-molecule or inter-molecule
    apha/beta: Threshold values for the intensification and diversification

     */

    protected Molecule optmial = null;
    protected double moleColl = 0.2;
    Random rand = new Random();
    protected int alpha = rand.nextInt(91) + 10;  // Generates a random integer between 10 and 100
    protected  int beta = rand.nextInt(91) + 10;   // Generates a random integer between 10 and 100
    protected double KELossRate = 0.6;
    protected double init_ke = 100;
    protected double buffer = 0;
    private ArrayList<ArrayList<Integer>> checkSequences ;
    protected abstract ArrayList<Molecule> inter(Molecule molecule1,Molecule molecule2);
    protected abstract Molecule wall (Molecule molecule);
    protected abstract ArrayList<Molecule> dec(Molecule molecule);
    protected abstract Molecule syn(Molecule molecule1,Molecule molecule2);
    protected abstract double fit_func(Molecule molecule);

    ArrayList<Molecule> pop = new ArrayList<>();
    Operators ops = new Operators();
    int interations = 100;

    private ArrayList<ArrayList<Integer>> convertSequences(ArrayList<String> sequences) {
        Map<Character, Integer> nucleotideDict = new HashMap<>();
        nucleotideDict.put('a', 0);
        nucleotideDict.put('c', 1);
        nucleotideDict.put('g', 2);
        nucleotideDict.put('t', 3);

        ArrayList<ArrayList<Integer>> convertedSequences = new ArrayList<>();
        for (String sequence : sequences) {
            ArrayList<Integer> convertedSequence = new ArrayList<>();
            for (char nucleotide : sequence.toCharArray()) {
                convertedSequence.add(nucleotideDict.get(nucleotide));
            }
            convertedSequences.add(convertedSequence);
        }
        return convertedSequences;
    }

    public IMCRO(ArrayList<Molecule> pop,ArrayList<String> checkSequences){
        this.pop = pop;
        this.checkSequences = this.convertSequences(checkSequences);
        
        this.pop.forEach(molecule->{
            molecule.setPe(this.fit_func(molecule));
            molecule.setKe(this.init_ke);
            molecule.update();
            if (this.optmial==null) {
               this.optmial = new Molecule(molecule); 
            }else if(molecule.getSupersequence().size() < this.optmial.getSupersequence().size()){
                this.optmial = new Molecule(molecule);
            }
        });
    }

    public Molecule getMostOptimalMolecule(){
        return this.optmial;
    }
    
    /*
    The Algorithm starts here randomly pick which reaction uni(randomly take 1 molecule from popsize) or inter (randomly take 2 molecule from popsize) 
    The number of iteration is abritrary 
     */
    public void run(){
        int i=0;
        while (i!=this.interations) {
            i+=1;
            double t = Math.random();
            if (t>this.moleColl || this.pop.size()<2) {
                this.uni_moleReact();
            }else{
                this.inter_moleReact();
            }
        }
    }


    private void uni_moleReact(){
        Molecule m = pop.get(rand.nextInt(pop.size()));
        if(m.getNumOfHits() > this.alpha){
            this.decomposition(m);
        }else{
            this.onWall(m);
        }
    }
    private void inter_moleReact(){
        Collections.shuffle(pop);
        Molecule m1 = pop.get(0);
        Molecule m2 = pop.get(1);
        if (m1.getKe()<=this.beta && m2.getKe()<= this.beta) {
            this.synthesis(m1, m2);
        }else{
            this.interaction(m1, m2);
        }
    }

    private boolean isSubsequence(List<Integer> sub, List<Integer> main) {
        int subIndex = 0, mainIndex = 0;
        while (subIndex < sub.size() && mainIndex < main.size()) {
            if (sub.get(subIndex).equals(main.get(mainIndex))) {
                subIndex++;
            }
            mainIndex++;
        }
        return subIndex == sub.size();
    }

    private boolean isSupersequence(List<Integer> seq) {
        ArrayList<ArrayList<Integer>> subsequences = this.checkSequences;

        for (List<Integer> subseq : subsequences) {
            if (!isSubsequence(subseq, seq)) {
                return false;
            }
        }
        return true;
    }
    /*
    If m is the current optimal solution, save it to the optimal.
     */
    private void update(Molecule molecule){
        if (molecule.getSupersequence().size()<this.optmial.getSupersequence().size()
        && isSupersequence(molecule.getSupersequence()) ) {
            this.optmial = molecule;
        }
    }
    private void decomposition(Molecule molecule){
        // plus 1 to number of hit into molecule itself
        molecule.setNumOfHits(molecule.getNumOfHits()+1);

        ArrayList<Molecule> newMolecules = dec(molecule);
        Molecule new1 = new Molecule(newMolecules.get(0));
        Molecule new2 = new Molecule(newMolecules.get(1));
        double tmp = molecule.getPe() + molecule.getKe() - new1.getPe() - new2.getPe();

        if (tmp>=0 || tmp+this.buffer>=0) {
            if (tmp >= 0) {
                double q =Math.random();
                new1.setKe(tmp*q);
                new2.setKe(tmp*(1-q));
            }else{
                new1.setKe((tmp+this.buffer) * Math.random()*Math.random());
                new2.setKe((tmp+this.buffer-new1.getKe()) * Math.random()*Math.random());
                this.buffer = this.buffer + tmp - new1.getKe() - new2.getKe();
            }
            new1.update();;
            new2.update();
            this.pop.remove(molecule);
            this.pop.add(new1);
            this.pop.add(new2);
            this.update(new1);
            this.update(new2);
        }
    }

    private void onWall(Molecule molecule){
        molecule.setNumOfHits(molecule.getNumOfHits()+1);

        Molecule newMolecule = new Molecule(this.wall(molecule));
        newMolecule.setPe(this.fit_func(newMolecule));
        double tmp = molecule.getPe() + molecule.getKe() - newMolecule.getPe();
        if (tmp>=0) {
            double q = KELossRate + (1 - KELossRate) * rand.nextDouble();
            newMolecule.setKe(tmp*q);
            newMolecule.update();
            this.buffer+=tmp*(1-q);
            this.pop.remove(molecule);
            this.pop.add(newMolecule);
            this.update(newMolecule);
        }
    }
    private void interaction(Molecule molecule1,Molecule molecule2){
        molecule1.setNumOfHits(molecule1.getNumOfHits()+1);
        molecule1.setNumOfHits(molecule1.getNumOfHits()+1);

        ArrayList<Molecule> newMolecules = this.inter(molecule1, molecule2);
        Molecule new1 = new Molecule(newMolecules.get(0));
        Molecule new2 = new Molecule(newMolecules.get(1));
        new1.setPe(this.fit_func(new1));
        new2.setPe(this.fit_func(new2));
    
        double tmp = molecule1.getPe() + molecule2.getPe() + molecule1.getKe() + molecule2.getKe() - new1.getPe() - new2.getPe();
        if (tmp >= 0) {
            double q = Math.random();
            new1.setKe(tmp * q);
            new2.setKe(tmp * (1 - q));
            new1.update();
            new2.update();
    
            // Assuming pop is an ArrayList<Molecule> and update is a method defined elsewhere
            pop.remove(molecule1);
            pop.remove(molecule2);
            pop.add(new1);
            pop.add(new2);
            update(new1);
            update(new2);
        }
    }
    private void synthesis(Molecule molecule1, Molecule molecule2) {
        molecule1.setNumOfHits(molecule1.getNumOfHits() + 1);
        molecule2.setNumOfHits(molecule2.getNumOfHits() + 1);
    
        // Assuming syn and fitFunc methods are defined and return appropriate values
        Molecule newMolecule = syn(molecule1, molecule2);
        newMolecule.setPe(this.fit_func(newMolecule));
    
        double tmp = molecule1.getPe() + molecule2.getPe() + molecule1.getKe() + molecule2.getKe() - newMolecule.getPe();
        if (tmp >= 0) {
            newMolecule.setKe(tmp);
            newMolecule.update();
    
            // Assuming pop is an ArrayList<Molecule> and update is a method defined elsewhere
            pop.remove(molecule1);
            pop.remove(molecule2);
            pop.add(newMolecule);
            update(newMolecule);
        }
    }
    
    
}
