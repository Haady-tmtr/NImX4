import controleur.*;
import vue.Ihm;
import vue.IhmNim;
import vue.IhmPuissanceQuatre;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("A quel jeu voulez-vous jouer ? (1 : X4; 2 : Nim) ");
        Scanner sc = new Scanner(System.in);
        int choix = 0;

        if (sc.hasNextInt()) choix = sc.nextInt();

        System.out.println("Souhaitez-vous jouer contre l'IA ? (y/n) ");

        String choixIA = sc.next();


        Controleur controleurJeu = fabriquerControleur(choix,choixIA);
        if (controleurJeu == null) {
            throw new IllegalArgumentException("Vous n'avez pas entré un chiffre exact ! ");
        } else {
            controleurJeu.jouer();
        }

    }

    private static Controleur fabriquerControleur(int choix,String choixIA){
        Ihm ihm;
        if (choix == 1) {
            ihm = new IhmPuissanceQuatre();
            if (choixIA.equals("y")){
                return new ControleurPuissanceQuatrePvO((IhmPuissanceQuatre) ihm);
            }
            else {
                return new ControleurPuissanceQuatre((IhmPuissanceQuatre) ihm);
            }
            // Penser aussi à la sequence pour le X4 (les if et tout )
        } else if (choix == 2) {
            ihm = new IhmNim();
            if (choixIA.equals("y")){
                return new ControleurNimPvO((IhmNim) ihm);
            }
            else {
                return new ControleurNim((IhmNim) ihm);
            }

        }
        return null;
    }




}