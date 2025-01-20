package modele.ia;

import modele.Plateau;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategieAleatoireNim implements Strategie{

    Plateau plateau;

    public StrategieAleatoireNim(Plateau plateau){
        this.plateau = plateau;
    }

    @Override
    public List<Integer> generer() {
        List<List<Integer>> coupsPossibles = new ArrayList<>();

        for (int i = 0; i < plateau.getLesTas().size(); i++) {
            int tas = plateau.getLesTas().get(i);
            int limite = plateau.getLimite();


            for (int j = 1; j <= Math.min(tas, limite); j++) {
                List<Integer> coup = new ArrayList<>();
                coup.add(i + 1);
                coup.add(j);
                coupsPossibles.add(coup);
            }
        }
        int indexCoupChoisi = (int) (Math.random() * coupsPossibles.size());

        return coupsPossibles.get(indexCoupChoisi);
    }
}
