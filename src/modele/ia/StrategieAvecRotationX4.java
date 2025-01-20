package modele.ia;

import modele.Grille;

import java.util.*;

/**
 * Implémente une stratégie de jeu avec rotations pour un jeu de Puissance 4.
 */
public class StrategieAvecRotationX4 implements Strategie {

    private Grille grille;

    /**
     * Constructeur qui initialise la stratégie avec la grille de jeu spécifiée.
     * 
     * @param grille La grille de jeu utilisée pour cette stratégie.
     */
    public StrategieAvecRotationX4(Grille grille){
        this.grille = grille;
    }

    /**
     * Génère le meilleur coup possible en tenant compte des rotations.
     * 
     * @return Un tableau de deux entiers représentant le meilleur coup possible,
     *         ou {-1} si une rotation gauche permet de gagner,
     *         ou {-2} si une rotation droite permet de gagner.
     */
    @Override
    public int[] generer() {
        Map<int[], Integer> lesScores = new HashMap<>(); // association clé = coup, valeur = score
        List<int[]> lesCoups = getCoupsPossibles();

        Grille newGrille = new Grille(grille.getCopieOfGrille());
        newGrille.rotationGauche();
        if (grille.getYellowCircle().equals(newGrille.findRow())){
            return new int[]{-1};
        }

        newGrille = new Grille(grille.getCopieOfGrille());
        newGrille.rotationDroite();
        if (grille.getYellowCircle().equals(newGrille.findRow())){
            return new int[]{-2};
        }

        for (int[] coup : lesCoups){
            int lineSize = grille.getRowSize(coup[0], coup[1], grille.getYellowCircle());
            int playerLineSize = grille.getRowSize(coup[0], coup[1], grille.getRedCircle());

            if (lineSize >= 4){
                lesScores.put(coup, 7);
            }
            else if (playerLineSize == 4) {
                lesScores.put(coup, 6);
            }
            else if (lineSize == 3) {
                lesScores.put(coup, 5);
            }
            else if (playerLineSize == 3) {
                lesScores.put(coup, 4);
            }
            else if (lineSize == 2) {
                lesScores.put(coup, 3);
            }
            else if (playerLineSize == 2) {
                lesScores.put(coup, 2);
            }
            else {
                lesScores.put(coup, 1);
            }
        }
        
        int maxScore = 0;
        int[] bestMove = null;

        for(Map.Entry<int[], Integer> entry : lesScores.entrySet()){
            if(entry.getValue() > maxScore){
                maxScore = entry.getValue();
                bestMove = entry.getKey();
            }
        }
        return bestMove;
    }

    /**
     * Obtient tous les coups possibles pour le joueur actuel.
     * 
     * @return Une liste de tableaux de deux entiers représentant les coups possibles.
     */
    public List<int[]> getCoupsPossibles(){
        List<int[]> lesCoups = new ArrayList<>();

        // Parcours des colonnes de la grille
        for (int i = 0; i < 7; i++){
            int ligne = grille.chercherLigne(i);
            if (ligne != -1){
                lesCoups.add(new int[]{ligne, i});
            }
        }
        return lesCoups;
    }
}
