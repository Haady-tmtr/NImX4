package modele;

import java.util.ArrayList;
import java.util.List;

public class Plateau {

    private List<Integer> lesTas;
    private int limite;

    /**
     * Crée un plateau avec un nombre de tas spécifié.
     * @param nbrTasPartie Nombre de tas pour la partie.
     */
    public Plateau(int nbrTasPartie){
        lesTas = new ArrayList<>();
        limite = 0;
        initPlateau(nbrTasPartie);
    }

    /**
     * Retourne la limite de coups possibles.
     * @return La limite actuelle.
     */
    public int getLimite(){ 
        return limite;
    }

    /**
     * Définit la limite de coups possibles.
     * @param limite La nouvelle limite.
     */
    public void setLimite(int limite){ 
        this.limite = limite;
    }

    /**
     * Retourne la liste des tas.
     * @return La liste des tas.
     */
    public List<Integer> getLesTas() {
        return lesTas;
    }

    /**
     * Initialise le plateau avec le nombre de tas spécifié.
     * @param nbrTasPartie Nombre de tas à initialiser.
     */
    public void initPlateau(int nbrTasPartie) {
        lesTas.clear(); // Vide la liste des tas

        for (int i = 1; i <= nbrTasPartie; i++) {
            lesTas.add(i * 2 - 1);
        }
    }

    /**
     * Effectue un coup sur le plateau.
     * @param coup Liste contenant le numéro du tas et le nombre d'allumettes à retirer.
     * @throws IllegalArgumentException Si le coup n'est pas valide.
     */
    public void effectuerCoup(List<Integer> coup){
        if (estValide(coup)) {
            lesTas.set(coup.get(0) - 1, lesTas.get(coup.get(0) - 1) - coup.get(1));
        } else {
            throw new IllegalArgumentException("Coup non valide");
        }
    }

    /**
     * Vérifie si un coup est valide.
     * @param coup Liste contenant le numéro du tas et le nombre d'allumettes à retirer.
     * @return True si le coup est valide, false sinon.
     */
    public boolean estValide(List<Integer> coup){
        if ((coup.get(0) <= lesTas.size()) && (coup.get(1) > 0) && ((coup.get(1) <= limite) || limite == 0)){
            // Si on ne souhaite pas que le joueur retire plus de 3 allumettes
            return lesTas.get(coup.get(0) - 1) >= coup.get(1);
        }
        return false;
    }

    /**
     * Vérifie si le jeu est toujours en cours.
     * @return True si le jeu est en cours, false sinon.
     */
    public boolean gameOn(){
        for (int tas : lesTas){
            if (tas != 0){
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne une représentation en chaîne de caractères du plateau.
     * @return Représentation du plateau.
     */
    @Override
    public String toString(){
        String ch = "\n";

        for (int i = 0; i < lesTas.size(); i++){
            ch += "Tas " + (i + 1) + " : ";
            ch += "| ".repeat(lesTas.get(i));
            ch += "\n";
        }
        return ch;
    }
}
