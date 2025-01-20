package controleur;

import modele.Plateau;
import vue.IhmNim;
import modele.Joueur;

import java.util.ArrayList;
import java.util.List;

public class ControleurNim extends Controleur{
    private IhmNim ihm;

    private Plateau plateau;
    private Joueur j1,j2;
    private int nbrTasPartie;
    // attributs des differentes methodes
    private boolean havePlayed;
    private Joueur joueurActuel;
    private int limite;
    private List<Integer> coup = new ArrayList<>();


    /**
     * Le controleur du jeu de Nim, quand il s'agit de faire une partie entre deux joueurs humains.
     * @param ihm un objet ihm qui permet au controleur d'intéragir par ce biais avec l'utilisateur
     */
    public ControleurNim(IhmNim ihm) {
        this.ihm = ihm;

        nbrTasPartie = ihm.recupererNbrTasPartie();
        plateau = new Plateau(nbrTasPartie);
        j1 = new Joueur(ihm.demanderNom());
        j2 = new Joueur(ihm.demanderNom());
    }

    /**
     * Initialise le plateau du jeu de Nim.
     */
    public void initialiserPartie(){
        // on (ré)initialise le plateau
        plateau.initPlateau(nbrTasPartie);
        joueurActuel = j1;

        limite = ihm.demanderContrainte();
        while (limite < 0){
            limite = ihm.demanderContrainte();
        }
        plateau.setLimite(limite);

        /// if limite = 0 ==> strategie gagnante else strategie aleatoire

    }



    /**
     * Teste avant chaque coup des joueurs que le plateau n'est pas vide avant de permettre à ceux-çi d'effectuer leurs coup.
     * @return un booleen sur l'état du plateau, s'il est completement vide ou non.
     */
    public boolean finPartie() {
        while (plateau.gameOn()) {
            havePlayed = false;

            ihm.afficherJoueurActuel(joueurActuel);
            ihm.afficherPartie(plateau.toString());

            // on ré
            coup = ihm.demanderCoup();
            jouerPartie();

        }
        return false;
    }


    /** Teste à chaque tour de jeu si le joueur n'a pas encore joué.  S'il n'a pas encore joué on lui permet d'éffectuer son coup et on
     * passe la main à l'autre joueur.
     */
    public void jouerPartie(){
        while (!havePlayed){
            if (plateau.estValide(coup)){
                plateau.effectuerCoup(coup);
                havePlayed = true;
            }
            else{
                ihm.afficherCoupIncorrect(joueurActuel.getNom());
                ihm.afficherPartie(plateau.toString());
                coup = ihm.demanderCoup();
            }
        }
        if (joueurActuel == j1) joueurActuel = j2;
        else joueurActuel = j1;


    }


    /**
     * Lorsque les joueurs décident de ne plus rejouer, cette fonction determine le vainqueur pour chaque session de jeu.
     */
    public void resultatSession(){
        Joueur gagnant = Joueur.getGagnant(j1,j2);
        if (gagnant == null){
            ihm.afficherExAequo(j1,j2);
        }
        else if (gagnant == j1) {
            ihm.afficherGagnantFinal(j1,j2);
        }
        else ihm.afficherGagnantFinal(j2,j1);

    }


    /**
     * Selon le principe du jeu de Nim, cette fonction augmente le score des joueurs à chaque partie gagnée, et demande aux joueurs s'ils
     * veulent rejouer une partie ou arrêter.
     */
    public void resultatPartie(){
        if (joueurActuel==j1){
            j2.increaseScore();
            ihm.afficherGagnantPartie(j2);
        }
        else {
            j1.increaseScore();
            ihm.afficherGagnantPartie(j1);

        }

        String reponse= ihm.rejouer() ;
        if (!reponse.equals("oui")){   // Si on veut rejouer !
            rejouer=false;
        }
    }
}
