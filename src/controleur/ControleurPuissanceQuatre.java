package controleur;

import modele.Grille;
import modele.Joueur;
import vue.IhmPuissanceQuatre;


public class ControleurPuissanceQuatre extends Controleur {

    private Grille grille;
    private Joueur j1,j2,joueurActuel;
    private IhmPuissanceQuatre ihm;

    private boolean endedByRotation;
    private boolean canRotate;
    private boolean havePlayed;

    private int nbrRotationJ1, nbrRotationJ2, nbrRotationJoueurActuel;
    private int numColonne, numLigne;
    private int typeCoup;

    private int[] dernierCoup;

    private String couleurGagnant, couleurJoueurActuel;

    /** ControleurJeuPuissanceQuatre()
     * Constructeur de la classe ControleurJeuPuissanceQuatre pour la partie avec l'ihm
     * @param ihm un objet ihm qui permet au controleur d'intéragir par ce biais avec l'utilisateur
     * */
    public ControleurPuissanceQuatre(IhmPuissanceQuatre ihm){
        this.ihm = ihm;
        grille = new Grille();
        j1 = new Joueur(ihm.demanderNom());
        j2 = new Joueur(ihm.demanderNom());
    }

    @Override
    public void initialiserPartie() {
        joueurActuel = j2; //ici on iniatilise le joueur actuel et sa couleur à l'envers
        couleurJoueurActuel = grille.getYellowCircle();

        dernierCoup = new int[2]; // On doit ajouter le dernier coup joué à ce tab à tout les tours
        // Il sert à utiliser GameOn donc c'est indispensable

        endedByRotation = false;
        couleurGagnant = null;

        // Initialisation choix de Rotation
        String choixRotation = ihm.demanderReglesSpeciales();
        canRotate = choixRotation.equals("y");

        nbrRotationJ1 = 0;
        nbrRotationJ2 = 0;
        nbrRotationJoueurActuel = 0; // Reinitialise les compteurs de rotations
    }

    @Override
    public void jouerPartie() {
        if (joueurActuel == j1) { // car on les inverse au début de la boucle
            // comme ça le joueur actuel à la fin de la boucle est le dernier joueur ayant jouer et donc le gagnant si la partie est finie
            joueurActuel = j2;
            couleurJoueurActuel = grille.getYellowCircle();

            nbrRotationJ1 = nbrRotationJoueurActuel;
            nbrRotationJoueurActuel = nbrRotationJ2;
        } else {
            joueurActuel = j1;
            couleurJoueurActuel = grille.getRedCircle();

            nbrRotationJ2 = nbrRotationJoueurActuel; // on actualise le nbrRotation du joueur qui vient de jouer
            nbrRotationJoueurActuel = nbrRotationJ1; // On passe le compteur du prochain joueur
        }
        typeCoup = -1;
        havePlayed = false;

        ihm.afficherPartie(grille.toString());
        ihm.afficherJoueurActuel(joueurActuel);

        while (typeCoup < 0 || typeCoup > 2) {
            if (canRotate) typeCoup = ihm.demanderTypeCoup();
            else typeCoup = 0; // si on ne peut pas faire de rotation alors on ne demande rien au joueur

            if (typeCoup == 1 || typeCoup == 2){ // rotations

                if(nbrRotationJoueurActuel++ < 4) { // si le joueur peut effectuer une rotation
                    if (typeCoup == 1) grille.rotationGauche();
                    else grille.rotationDroite();

                    couleurGagnant = grille.findRow();
                    if (couleurGagnant != null){
                        endedByRotation = true;
                    }
                    havePlayed = true;
                }
                else {
                    ihm.afficherRotationImpossible();
                    typeCoup = -1;
                }
            }

            else if (typeCoup == 0) {
                numColonne = ihm.demanderCoup() - 1;

                while (!havePlayed) {
                    if (grille.estValide(numColonne)) {
                        numLigne = grille.chercherLigne(numColonne);
                        if (grille.estValide(numLigne)) {
                            dernierCoup = grille.jouerCoup(numColonne, numLigne, couleurJoueurActuel); // Parametres changeront sûrement
                            havePlayed = true;
                        } else {
                            ihm.afficherCoupIncorrect(joueurActuel.getNom());
                            numColonne = ihm.demanderCoup() - 1;
                        }
                    } else {
                        ihm.afficherCoupIncorrect(joueurActuel.getNom());
                        numColonne = ihm.demanderCoup() - 1;
                    }
                }
            }
        }
    }


    @Override
    public void resultatPartie() {
        ihm.afficherPartie(grille.toString());

        if (grille.notFull()){
            if (endedByRotation) {
                if (couleurGagnant.equals(grille.getRedCircle())) {
                    j1.increaseScore();
                    ihm.afficherGagnantPartie(j1);
                } else if (couleurGagnant.equals(grille.getYellowCircle())) {
                    j2.increaseScore();
                    ihm.afficherGagnantPartie(j2);
                }
            }
            else {
                joueurActuel.increaseScore();
                ihm.afficherGagnantPartie(joueurActuel);
            }
        }
        else {
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
        Joueur gagnant = Joueur.getGagnant(j1,j2);
        if (gagnant == null){
            ihm.afficherExAequo(j1,j2);
        }
        else if (gagnant == j1) {
            ihm.afficherGagnantFinal(j1,j2);
        }
        else ihm.afficherGagnantFinal(j2,j1);
    }


    @Override
    public boolean finPartie() {
        return !endedByRotation && grille.notFull() && (grille.getRowSize(dernierCoup[0], dernierCoup[1], couleurJoueurActuel) < 4 );
    }

}
