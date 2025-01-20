package vue;

import java.util.Scanner;

public class IhmPuissanceQuatre extends Ihm{ // Donc changement ici de la part de Mohamed avec le extends

    /**
     * Demande à un joueur d'entrer son coup sous la forme "n" la colonne dans laquelle il souhaite jouer
     *
     * @return int l'indice de la colonne choisie
     */
    @Override
    public Integer demanderCoup(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Entrez le numéro de la colonne sur laquelle vous souhaitez jouer : ");

        while (!sc.hasNextInt() && sc.hasNext()) {
            System.out.println("Vous devez entrer un nombre : ");
            sc.nextLine();
        }
        return sc.nextInt();
    }

    /** Affiche le résultat d'une partie où la grille a été remplie sans qu'il n'y ai de gagnant
     * out : Message en console
     * */
    public void afficherGrillePleine(){
        System.out.println("La grille est remplie mais aucun joueur n'a réussi à aligner 4 pions il n'y a donc pas de gagnant.");
    }

    /** Demande au joueur s'il souhaite intéger la règle spéciale du puissance 4: les rotations à gauche et à droite
     * @return sc.next() la réponse y = Yes, n = No du joueur si c'est réponse n'est pas correcte le controleur réappelera la méthode.
     * */
    public String demanderReglesSpeciales() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Voulez-vous que les joueurs puissent effectuer des rotations de la grille durant la partie (4 par Joueur maximum) ? (y/n) ");
        return sc.next();
    }

    /** Demande au joueur quel type de coup il souhaite effectuer, 0 pour poser un pion et 1/2 pour les rotations.
     * @return sc.nextInt() le choix du joueur, en cas de réponse incorrecte le controleur reposera la question.
     * */
    public int demanderTypeCoup(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Quelle action souhaitez-vous effectuer ? '0': jouer un coup, '1': effectuer une rotation à gauche, '2': effectuer une rotation à droite. ");
        while (!sc.hasNextInt() && sc.hasNext()) {
            System.out.println("Vous devez entrer un nombre : ");
            sc.nextLine();
        }
        return sc.nextInt();
    }

    /**
     * Informe au joueur qu'il ne peut plus effectuer de rotation.
     */
    public void afficherRotationImpossible() {
        System.out.println("Vous ne pouvez plus effectuer de rotation durant cette partie");
    }
}
