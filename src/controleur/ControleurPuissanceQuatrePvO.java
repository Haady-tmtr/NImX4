package controleur;

import modele.Grille;
import modele.Joueur;
import modele.ia.Strategie;
import modele.ia.StrategieAvecRotationX4;
import vue.IhmPuissanceQuatre;

/**
 * Classe qui contrôle le déroulement d'une partie de Puissance 4 en mode joueur contre ordinateur.
 */
public class ControleurPuissanceQuatrePvO extends Controleur {

    private Grille grille;
    private Joueur joueur, IA;
    private IhmPuissanceQuatre ihm;
    private Strategie strategie;

    private boolean endedByRotation;
    private boolean canRotate;
    private boolean havePlayed;

    private int nbrRotationJoueur, nbrRotationIA;
    private int numColonne, numLigne;
    private int typeCoup;

    private int[] dernierCoup;

    private String couleurGagnant;

    /**
     * Constructeur de la classe ControleurPuissanceQuatrePvO pour la partie avec l'IHM.
     *
     * @param ihm un objet IhmPuissanceQuatre qui permet au contrôleur d'interagir avec l'utilisateur.
     */
    public ControleurPuissanceQuatrePvO(IhmPuissanceQuatre ihm) {
        this.ihm = ihm;
        grille = new Grille();
        joueur = new Joueur(ihm.demanderNom());
        IA = new Joueur("IA");
        couleurGagnant = "";
    }

    @Override
    public void initialiserPartie() {
        dernierCoup = new int[2]; // On doit ajouter le dernier coup joué à ce tableau à chaque tour
        // Il sert à utiliser GameOn donc c'est indispensable

        endedByRotation = false;
        couleurGagnant = "";

        // Initialisation choix de Rotation
        String choixRotation = ihm.demanderReglesSpeciales();
        canRotate = choixRotation.equals("y");

        // Initialisation de la Stratégie
        strategie = new StrategieAvecRotationX4(grille);

        nbrRotationJoueur = 0;
        nbrRotationIA = 0;
    }

    @Override
    public void jouerPartie() {
        typeCoup = -1;
        havePlayed = false;

        ihm.afficherPartie(grille.toString());
        ihm.afficherJoueurActuel(joueur);

        while (typeCoup < 0 || typeCoup > 2) { // On fait un tour de boucle sauf si l'entrée du joueur est incorrecte

            if (canRotate) typeCoup = ihm.demanderTypeCoup();
            else typeCoup = 0; // si on ne peut pas faire de rotation alors on ne demande rien au joueur

            if (typeCoup == 1 || typeCoup == 2) { // rotations
                if (nbrRotationJoueur++ < 4) { // si le joueur peut effectuer une rotation
                    if (typeCoup == 1) grille.rotationGauche();
                    else grille.rotationDroite();

                    couleurGagnant = grille.findRow(); // on cherche une ligne de n'importe quelle couleur dans l'ensemble de la grille
                    if (couleurGagnant != null) {
                        endedByRotation = true;
                    }
                    havePlayed = true;
                } else {
                    ihm.afficherRotationImpossible();
                    typeCoup = -1;
                }
            } else if (typeCoup == 0) { // Coup classique
                numColonne = ihm.demanderCoup() - 1;

                while (!havePlayed) { // tant que le joueur n'a pas fait un coup correct
                    if (grille.estValide(numColonne)) {
                        numLigne = grille.chercherLigne(numColonne);
                        if (grille.estValide(numLigne)) {
                            dernierCoup = grille.jouerCoup(numColonne, numLigne, grille.getRedCircle());
                            havePlayed = true;
                        } else {
                            ihm.afficherCoupIncorrect(joueur.getNom());
                            numColonne = ihm.demanderCoup() - 1;
                        }
                    } else {
                        ihm.afficherCoupIncorrect(joueur.getNom());
                        numColonne = ihm.demanderCoup() - 1;
                    }
                }
            }
            couleurGagnant = grille.getRedCircle(); // couleurGagnant désigne la couleur du dernier joueur
        }
        if (finPartie()) { // on vérifie si le tour du joueur a terminé la partie
            int[] bestMove = strategie.generer(); // génération du coup de l'ia

            if (bestMove[0] == -1) { 
                grille.rotationGauche();
                endedByRotation = true;
            } else if (bestMove[0] == -2) {
                grille.rotationDroite();
                endedByRotation = true;
            } else {
                dernierCoup = grille.jouerCoup(bestMove[1], bestMove[0], grille.getYellowCircle()); // application du coup
            }
            couleurGagnant = grille.getYellowCircle();
        }
    }

    @Override
    public void resultatPartie() {
        ihm.afficherPartie(grille.toString());

        if (grille.notFull()) {
            if (couleurGagnant.equals(grille.getRedCircle())) {
                joueur.increaseScore();
                ihm.afficherGagnantPartie(joueur);
            } else if (couleurGagnant.equals(grille.getYellowCircle())) {
                IA.increaseScore();
                ihm.afficherGagnantPartie(IA);
            }
        } else {
            ihm.afficherGrillePleine();
        }

        grille = new Grille();

        String reponse = ihm.rejouer();
        if (!reponse.equals("oui")) {   // Si on veut rejouer !
            rejouer = false;
        }
    }

    @Override
    public void resultatSession() {
        Joueur gagnant = Joueur.getGagnant(joueur, IA);
        if (gagnant == null) {
            ihm.afficherExAequo(joueur, IA);
        } else if (gagnant == joueur) {
            ihm.afficherGagnantFinal(joueur, IA);
        } else ihm.afficherGagnantFinal(IA, joueur);
    }

    @Override
    public boolean finPartie() {
        return !endedByRotation && grille.notFull() && (grille.getRowSize(dernierCoup[0], dernierCoup[1], couleurGagnant) < 4);
    }

}
