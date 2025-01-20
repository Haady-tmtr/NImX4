package modele.ia;

import modele.Plateau;

import java.util.ArrayList;
import java.util.List;

public class StrategieGagnanteNim implements Strategie{
    Plateau plateau;
    private List<Integer> lesTas;


    /**
     * Constructeur de la classe StrategieGagnanteNim
     * @param plateau qui represente le plateau de jeu des joueurs.
     */
    public StrategieGagnanteNim(Plateau plateau){
        this.plateau = plateau;
        lesTas = plateau.getLesTas();
    }

    /**
     * Methode privée, qui permet de calculer le ou exclusif du nombre d'allumettes de chaque tas.
     * @return un entier representant le resultat de ce calcul.
     */
    private int calculXor(){
        int resultatXor=0;
        int allumette = 0;
        for(int i=0; i<lesTas.size();i++){
            //allumette = lesTas.get(i);
            resultatXor ^= lesTas.get(i);

        }
        return resultatXor;
    }

    /**
     * Methode qui génère le coup que l'ordinateur devra jouer pour appliquer uns strategie gagnante.
     * @return coupIA : une List<Integer> contenant le coup de l'IA sous la forme
     * numero du tas-nombre d'allumettes à retirer.
     */
    @Override
    public List<Integer> generer() {
        List<Integer> coupIA = new ArrayList<>();
        int resultatXor = calculXor();
        if (resultatXor != 0) {
            int allumette = 0;
            for(int i=0;i< lesTas.size();i++){
                allumette = lesTas.get(i) ^ resultatXor;
                if (allumette <= lesTas.get(i)) {
                    int deduction = lesTas.get(i) - allumette;
                    coupIA.add(i+1);  // souci ici ?
                    coupIA.add(deduction);
                    break;

                }
            }
        }
        else{
            for(int i=0;i< lesTas.size();i++){
                if (lesTas.get(i) >= 1){
                    coupIA.add(i+1); // Les indices
                    coupIA.add(1);
                    break;
                }
            }

        }
        return coupIA;

    }
}









