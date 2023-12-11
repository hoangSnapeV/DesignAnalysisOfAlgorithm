import java.util.ArrayList;
import java.util.List;

public class MainTestIMCRO {
   public static void main(String[] args) {
        ArrayList<String> initialPop =new ArrayList<>(List.of("acg", "cat", "gtt", "tgc","tcc"));
        ArrayList<Molecule> molecules = Initialization_IMCRO.initialization(100, initialPop);
        IMCRO myimcro1 = new MYIMCRO(molecules, initialPop);
        myimcro1.run();
        System.out.println(myimcro1.getMostOptimalMolecule());
    } 
}
