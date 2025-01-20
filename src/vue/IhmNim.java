package vue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IhmNim extends Ihm {  // Donc changement ici de la part de Mohamed avec le extends

    /* demanderCoup()
     * Entree :
     * Sortie : Un tableau de 2 entiers contenant en i = 0 le tas sur lequel on souhaite jouer
     *          et en i = 1 le nombre d'allumettes que l'on veut retirer (entre 1 et 3)
     * Cette fonction demande au joueur d'entrer son coup sous la forme (n m)
     * puis vérifie si les informations données sont conformes aux exigeances du système
     * */
    public List<Integer> demanderCoup() {
        Scanner sc = new Scanner(System.in);
        List<Integer> coup = new ArrayList<>();

        System.out.println("Entrez votre coup sous la forme : colonne nombreAllumettes : ");

        while (true) {
            /* BUG à corriger : on peut rajouter n'importe quoi après "n m " cela marchera
             *  mais le programme intercepte les autres problèmes type "2 e 3 a"  ou "e 2 3"
             *  qui sont des réponses incorrectes */

            if (coup.size() == 2){
                return coup;
            }

            if (sc.hasNextInt() && coup.size() < 2) {
                coup.add(sc.nextInt());
            }

            else {
                System.out.println("Vous devez entrer votre coup sous la forme : colonne nombreAllumettes : ");
                coup.clear();
                sc.nextLine();
            }

        }
    }

    /**
     * Demande au joueur le nombre de tas avec lesquels il veut jouer la partie.
     * @return un entier représentant le nombre de tas voulu par le joueur.
     */
    public int recupererNbrTasPartie(){
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Entrez le nombre de tas avec lequels vous souhaitez jouer : ");

            while (!sc.hasNextInt() && sc.hasNext()) {
                System.out.println("Vous devez entrer un nombre : ");
                sc.nextLine();
            }

            int nbrTas = sc.nextInt();
            if (nbrTas < 1 || nbrTas > 9) {
                System.out.println("Le nombre de tas doit être compris entre 1 et 9 (inclus)");
                // On définit une limite inférieure et supérieure
            }
            else return nbrTas;
        }
    }

    /**
     * Demande au joueur s'il veut ajouter une contrainte sur le nombre d'allumettes maximum à retirer.
     * @return un entier représentant la limite souhaitée par le joueur.
     */
    public int demanderContrainte(){
        System.out.println("Voulez vous ajouter une contrainte sur le nombre d'allumettes maximum à retirer ? Si oui, entrez ce nombre, sinon entrez 0.");
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextInt()){
            return sc.nextInt();
        }
        return 0;
    }
}
