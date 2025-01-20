package modele;

public class Joueur {

    private String nom;
    private int score;


    /**
     * Constructeur de la Classe Joueur
     * @param nom le nom du joueur
     * */
    public Joueur(String nom){
        this.nom = nom;
        this.score = 0;
    }

    /**
     *
     * @return le nom du joueur
     */
    public String getNom(){
        return nom;
    }

    /**
     *
     * @return le score du joueur.
     */
    public int getScore(){
        return score;
    }

    /**
     * Incrémente le score du joueur de 1 (agit comme un setteur pour l'attribut)
     * */
    public void increaseScore(){
        score += 1;
    }

    /**
     * Compare les scores de deux joueurs
     * @param J1 Premier argument de type Joueur
     * @param J2 Second argument de type Joueur
     * @return null si les joueurs sont ex-aequo, le joueur avec le plus haut score sinon
     * */
    public static Joueur getGagnant(Joueur J1,Joueur J2){
        if (J1.getScore() == J2.getScore()){
            return null;
        }
        else if (J1.getScore() > J2.getScore()) {
            return J1;
        }
        else return J2;
    }


    /**
     * Affiche l'état du joueur.
     * @return
     */
    @Override
    public String toString() {
        return "Joueur{" +
                "nom='" + nom +
                '}';
    }
}

