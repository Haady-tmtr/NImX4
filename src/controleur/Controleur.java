package controleur;

import vue.Ihm;

/**
 * Classe abstraite représentant le contrôleur d'un jeu.
 * Cette classe gère le cycle de jeu, incluant l'initialisation, la boucle de jeu,
 * et l'affichage des résultats pour chaque partie et pour la session complète.
 */
public abstract class Controleur {

    /**
     * Indique si une nouvelle partie doit être jouée.
     */
    public boolean rejouer = true;

    /**
     * Méthode principale pour gérer le cycle de jeu.
     * Continue à jouer des parties tant que {@code rejouer} est vrai.
     */
    public void jouer() {
        while (rejouer) {
            initialiserPartie();
            while (finPartie()) { // Assumption: The loop should continue while the game is not finished
                jouerPartie();
            }
            resultatPartie();
        }
        resultatSession();
    }

    /**
     * Initialise une nouvelle partie du jeu.
     * Doit être implémentée par les sous-classes.
     */
    public abstract void initialiserPartie(); 

    /**
     * Gère la boucle d'une partie unique.
     * Doit être implémentée par les sous-classes.
     */
    public abstract void jouerPartie();

    /**
     * Affiche ou gère les résultats d'une partie.
     * Doit être implémentée par les sous-classes.
     */
    public abstract void resultatPartie();

    /**
     * Affiche ou gère les résultats de la session de jeu complète.
     * Doit être implémentée par les sous-classes.
     */
    public abstract void resultatSession();

    /**
     * Vérifie si la partie actuelle est terminée.
     * 
     * @return {@code true} si la partie est terminée, {@code false} sinon.
     */
    public abstract boolean finPartie();
}
