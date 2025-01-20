package vue;
import modele.Joueur;
import modele.Grille;
import modele.Plateau;

import java.util.List;
import java.util.Scanner;

public abstract class Ihm {
    /* Méthodes d'affichage simples */

    /** Affiche l'état actuel de la partie dans la console
     * @param etatPartie le toString de la partie de jeu
     * */
    public void afficherPartie(String etatPartie){
        System.out.println(etatPartie);
    }

    /** Affiche le nom du joueur qui doit jouer dans la console
     * @param joueur le joueur à qui c'est le tour de jouer
     * */
    public void afficherJoueurActuel(Joueur joueur){
        System.out.println(joueur.getNom() + " à vous de jouer !");
    }

    /** Affiche le gagnant du tour.
     * @param joueur qui est le joueur gagnant.
     * */
    public void afficherGagnantPartie(Joueur joueur){
        System.out.println("Le gagnant de cette partie est " + joueur.getNom());
    }

    /** Affiche les scores finaux et le nom du vainqueur
     * @param gagnant qui est le joueur gagnant.
     * @param perdant qui est le joueur perdant.
     * */
    public void afficherGagnantFinal(Joueur gagnant,Joueur perdant){
        System.out.println("Le joueur " + gagnant.getNom() + " a gagné " + gagnant.getScore() + " partie(s), et le joueur "+
                perdant.getNom() + " en a gagné "+ perdant.getScore() + ".Le gagnant est "+ gagnant.getNom());
    }

    /** Affiche les scores finaux et ex-Aequo
     * @param j1 le joueur 1.
     * @param j2 le joueur 2.
     * */
    public void afficherExAequo(Joueur j1, Joueur j2){
        System.out.println("Le joueur " + j1.getNom() + " a gagné " + j1.getScore() + " partie(s), et le joueur "+
                j2.getNom() + " en a gagné "+ j2.getScore()+ ". Vous êtes ex-aequo.");
    }


    /* Méthodes d'affichage d'erreurs */

    /** Informe le joueur que son coup était incorrect lors du traitement dans le controleur frontal
     * */
    public void afficherCoupIncorrect(String nomJ) {
        System.out.println("Echec de votre coup " + nomJ + ".Veuillez réesayer !");
    }


    /* Méthodes d'intéraction avec Scanner */

    /** Demande à un joueur d'entrer son nom/pseudo dans la console
     * @return un String contenant le nom du joueur
     * */
    public String demanderNom(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez votre nom : ");
        return sc.next();
    }

    /** Demande à un joueur d'entrer son coup sous la forme "n" la colonne dans laquelle il souhaite jouer
     * @return int l'indice de la colonne choisie
     * */
     public abstract <T> T demanderCoup();



    /** Demande au joueur si il souhaite refaire une partie avec les mêmes paramètres ou si il souhaite arreter.
     * Le joueur devra réecrire sa réponse tant qu'elle sera différente de oui ou non.
     * @return String "oui" ou "non"
     * */
    public String rejouer(){
        Scanner sc = new Scanner(System.in);
        String rejoue = "";

        while (!rejoue.equals("oui") && !rejoue.equals("non")) {
            System.out.println("Voulez vous rejouer ? Si oui, entrez 'oui' sinon, entrez 'non' !");

            if (sc.hasNextLine()) {
                rejoue = sc.nextLine().toLowerCase();
            }
        }
        return rejoue;
    }
}
