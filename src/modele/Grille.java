package modele;

import java.util.Arrays;

public class Grille {

    private String[][] grille;

    private final String blueSquare = "\uD83D\uDFE6"; // Emoji d'un carré bleu
    private final String yellowCircle = "\uD83D\uDFE1"; // Emoji d'un cercle jaune
    private final String redCircle = "\uD83D\uDD34"; // Emoji d'un cercle rouge
    private final String blackCircle = "⚫";       // Emoji d'un cercle noir

    /**
     * Crée une grille de jeu de taille 7*7 pour le Puissance 4.
     */
    public Grille(){
        grille = new String[7][7];
    }

    /**
     * Crée une grille de jeu avec une grille donnée.
     * @param grille Grille de jeu initiale.
     */
    public Grille(String[][] grille){
        this.grille = grille;
    }

    /**
     * Retourne une copie de la grille actuelle.
     * @return Copie de la grille.
     */
    public String[][] getCopieOfGrille(){
        String[][] newGrille = new String[7][7];
        for (int i = 0; i < 7; i++){
            newGrille[i] = Arrays.copyOf(grille[i], 7);
        }
        return newGrille;
    }

    /**
     * Retourne la couleur du pion à la position spécifiée.
     * @param colonne Colonne de la position.
     * @param ligne Ligne de la position.
     * @return Couleur du pion.
     * @throws IllegalArgumentException Si les coordonnées sont invalides.
     */
    public String getCouleur(int colonne, int ligne){
        if (estValide(colonne) && estValide(ligne)) return grille[ligne][colonne];
        else throw new IllegalArgumentException();
    }

    /**
     * Retourne un emoji de couleur jaune.
     * @return Emoji cercle jaune.
     */
    public String getYellowCircle() {
        return yellowCircle;
    }

    /**
     * Retourne un emoji de couleur rouge.
     * @return Emoji cercle rouge.
     */
    public String getRedCircle() {
        return redCircle;
    }

    /**
     * Cherche la ligne correspondante au numéro de colonne entré par l'utilisateur.
     * @param colonne Numéro de colonne entré par l'utilisateur.
     * @return Le numéro de ligne correspondant s'il existe, sinon retourne -1.
     */
    public int chercherLigne(int colonne){
        for (int i = 6; i >= 0; i--) {
            if (grille[i][colonne] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Effectue le coup donné en paramètre et modifie la grille du jeu.
     * @param colonne Numéro de la colonne où jouer.
     * @param ligne Numéro de la ligne où jouer.
     * @param couleur Couleur du pion associé au joueur.
     * @return Coordonnées du coup joué.
     * @throws IllegalArgumentException Si le coup n'est pas valide.
     */
    public int[] jouerCoup(int colonne, int ligne, String couleur){
        if (estValide(colonne) && estValide(ligne)) {
            grille[ligne][colonne] = couleur;
            return new int[]{ligne, colonne};
        }
        else throw new IllegalArgumentException();
    }

    /**
     * Retourne la longueur des lignes partant d'une position selon l'état de la partie.
     * @param ligne Numéro de ligne valide.
     * @param colonne Numéro de colonne entré par l'utilisateur.
     * @param couleur Couleur du pion associé au joueur.
     * @return Longueur des lignes trouvées.
     */
    public int getRowSize(int ligne, int colonne, String couleur) {
        int[] longueurs = new int[4];
        int compteur = 1;
        int indice = -1;

        int[][] voisins = {{ligne-1, colonne}, {ligne+1, colonne}, {ligne+1, colonne-1}, {ligne-1, colonne+1},
                {ligne, colonne-1}, {ligne, colonne+1}, {ligne-1, colonne-1}, {ligne+1, colonne+1}};

        while(indice++ < 7) {
            int[] smtDep = new int[]{ligne, colonne};
            int[] voisin = voisins[indice];
            int[] alt = new int[2];

            for(int i = 0; i < 3; i++) {
                if(((voisin[0] >= 0) && (voisin[0] <= 6) && (voisin[1] >= 0) && (voisin[1] <= 6)) && (couleur.equals(grille[voisin[0]][voisin[1]]))) {
                    alt[0] = voisin[0];
                    alt[1] = voisin[1];

                    voisin[0] += (voisin[0] - smtDep[0]);
                    voisin[1] += (voisin[1] - smtDep[1]);
                    smtDep[0] = alt[0];
                    smtDep[1] = alt[1];
                    compteur++;
                }
                else break;
            }

            if (indice % 2 == 1) {
                longueurs[indice / 2] = compteur;
                compteur = 1;
            }
        }
        Arrays.sort(longueurs);
        return longueurs[3];
    }

    /**
     * Retourne false si au moins un des emplacements de la première ligne est vide, donc la grille n'est pas pleine,
     * et true sinon.
     * @return True si la grille n'est pas pleine, false sinon.
     */
    public boolean notFull(){
        for (String elmt : grille[0]){
            if (elmt == null){
                return true;
            }
        }
        return false;
    }

    /**
     * Teste si l'utilisateur a entré un numéro de colonne valide.
     * @param nbr Numéro de la colonne entré par l'utilisateur.
     * @return True si le numéro de colonne est valide, false sinon.
     */
    public boolean estValide(int nbr) {
        return nbr >= 0 && nbr < 7;
    }

    /**
     * Effectue une rotation gauche comme voulu par le joueur.
     */
    public void rotationGauche() {
        Grille newGrille = new Grille();
        String elmtActuel;

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                elmtActuel = grille[i][j];
                if (elmtActuel != null){
                    newGrille.jouerCoup(i, newGrille.chercherLigne(i), elmtActuel);
                }
            }
        }
        grille = newGrille.grille;
    }

    /**
     * Effectue une rotation droite comme voulu par le joueur.
     */
    public void rotationDroite() {
        Grille newGrille = new Grille();
        String elmtActuel;

        for(int i = 0; i < 7; i++){
            for(int j = 6; j > -1; j--){
                elmtActuel = grille[i][j];
                if (elmtActuel != null){
                    newGrille.jouerCoup(6 - i, newGrille.chercherLigne(6 - i), elmtActuel);
                }
            }
        }
        grille = newGrille.grille;
    }

    /**
     * Teste s'il existe au moins une ligne de 4 pions de la même couleur dans la grille.
     * @return La couleur gagnante si elle existe, null sinon.
     */
    public String findRow(){
        String couleur;
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                couleur = grille[i][j];
                if (couleur != null && (getRowSize(i, j, couleur) >= 4)){
                    return couleur;
                }
            }
        }
        return null;
    }

    /**
     * Affiche l'état actuel de la grille.
     * @return La grille sous forme de chaîne de caractères.
     */
    @Override
    public String toString(){
        String ch = blueSquare.repeat(9);
        for (int i = 0; i < 7; i++){
            ch += "\n" + blueSquare;
            for (String cell : grille[i]){
                if (cell != null){
                    ch += cell;
                }
                else ch += blackCircle;
            }
            ch += blueSquare;
        }
        ch += "\n" + blueSquare.repeat(9);

        return ch;
    }
}
