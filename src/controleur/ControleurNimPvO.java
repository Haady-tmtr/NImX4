package controleur;

import modele.Joueur;
import modele.Plateau;
import modele.ia.Strategie;
import modele.ia.StrategieAleatoireNim;
import modele.ia.StrategieGagnanteNim;
import vue.IhmNim;

import java.util.ArrayList;
import java.util.List;

public class ControleurNimPvO extends Controleur {
    private IhmNim ihm;
    private Plateau plateau;
    private Joueur j1,IA;  // un joueur IA
    private int nbrTasPartie;
    // attributs des differentes methodes
    private boolean havePlayed;
    private Joueur joueurActuel;
    private int limite;
    private List<Integer> coup = new ArrayList<>();


    private Strategie laStrategie;


    /**
     * Le controleur du jeu de Nim, quand il s'agit de faire une partie contre l'ordinateur.
     * @param ihm un objet ihm qui permet au controleur d'intéragir par ce biais avec l'utilisateur
     */
    public ControleurNimPvO (IhmNim ihm){
        this.ihm = ihm;


        nbrTasPartie = ihm.recupererNbrTasPartie();
        plateau = new Plateau(nbrTasPartie);
        j1 = new Joueur(ihm.demanderNom());
        IA = new Joueur("IA");


    }

    /**
     * Initialise le plateau du jeu de Nim. C'est là qu'on determinera la strategie que l'IA utilisera.
     */
    @Override
    public void initialiserPartie() {
        // on (ré)initialise le plateau
        plateau.initPlateau(nbrTasPartie);
        joueurActuel = j1;

        limite = ihm.demanderContrainte();
        while (limite < 0){
            limite = ihm.demanderContrainte();
        }
        plateau.setLimite(limite);

        /// if limite = 0 ==> strategie gagnante else strategie aleatoire
        if (limite == 0){
            laStrategie = new StrategieGagnanteNim(plateau);
            //coup = laStrategie.generer();  // Je recup la strategie gagnante ici
        }
        else {
            laStrategie = new StrategieAleatoireNim(plateau);
            //coup = laStrategie.generer(); // La strategie aleatoire, d'un autre type ?
        }


    }

    /**
     * Teste à chaque tour de jeu si le joueur n'a pas encore joué.  S'il n'a pas encore joué on lui permet d'éffectuer son coup et on
     * passe la main à l'autre joueur, ici l'IA.
     */
    @Override
    public void jouerPartie() {
        while (!havePlayed){
            System.out.println(coup);
            if (plateau.estValide(coup)){
                plateau.effectuerCoup(coup);
                havePlayed = true;
            }
            else{ // En principe , on n'entrera jamais ici avec l'ia, puisque le coup de l'ia est toujours adapté
                ihm.afficherCoupIncorrect(joueurActuel.getNom());
                ihm.afficherPartie(plateau.toString());
                coup = ihm.demanderCoup();
            }
        }
        if (joueurActuel == j1) joueurActuel = IA;  // Changement du joueur à l'IA
        else joueurActuel = j1;

    }

    /**
     * Selon le principe du jeu de Nim, cette fonction augmente le score des joueurs à chaque partie gagnée, et demande aux joueurs s'ils
     * veulent rejouer une partie ou arrêter.
     */
    @Override
    public void resultatPartie() {
        if (joueurActuel==j1){
            IA.increaseScore();
            ihm.afficherGagnantPartie(IA);
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

    /**
     * Lorsque les joueurs décident de ne plus rejouer, cette fonction determine le vainqueur pour chaque session de jeu.
     */
    @Override
    public void resultatSession() {
        Joueur gagnant = Joueur.getGagnant(j1,IA);
        if (gagnant == null){
            ihm.afficherExAequo(j1,IA);
        }
        else if (gagnant == j1) {
            ihm.afficherGagnantFinal(j1,IA);
        }
        else ihm.afficherGagnantFinal(IA,j1);

    }

    /**
     * Teste avant chaque coup des joueurs que le plateau n'est pas vide avant de permettre à ceux-ci d'effectuer leurs coup.
     * Si le joueur est l'IA, on applique sa strategie et on obtient son coup, sinon
     * on demande au joueur  son coup.
     * @return un booleen sur l'état du plateau, s'il est completement vide ou non.
     */
    @Override
    public boolean finPartie() {
        while (plateau.gameOn()) {
            havePlayed = false;
            if (joueurActuel.getNom().equals("IA")){
                ihm.afficherPartie(plateau.toString());
                coup = laStrategie.generer(); // la strat gagnante
            }
            else {
                ihm.afficherJoueurActuel(joueurActuel);
                ihm.afficherPartie(plateau.toString());
                coup = ihm.demanderCoup();

            }
            return true;
        }
        return false;
    }
}
