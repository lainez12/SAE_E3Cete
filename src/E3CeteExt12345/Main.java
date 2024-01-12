package E3CeteExt12345;

import E3CeteBase.Couleur;

public class Main {

    /**
     * Action : lance une partie de jeu "E3Cète"
     */
    public static void main(String[]args) {
        imprimeJeu2();
        System.out.println();
        Jeu parti = new Jeu();
        parti.jouer();
        imprimeJeu();
    }

    public static void imprimeJeu(){
        System.out.println();
        System.out.println(Couleur.bold());
        System.out.println(Couleur.aleatoire() + "                           ////////////////////////////////////");
        System.out.println(Couleur.aleatoire() + "                           //                                //");
        System.out.println(Couleur.aleatoire() + "                           //    EEEEE  XX    XX    CCCC     //");
        System.out.println(Couleur.aleatoire() + "                           //    E       X    X    C         //");
        System.out.println(Couleur.aleatoire() + "                           //    EEEE     XXXX    C          //");
        System.out.println(Couleur.aleatoire() + "                           //    E      X      X   C         //");
        System.out.println(Couleur.aleatoire() + "                           //    EEEEE  XX    XX    CCCC     //");
        System.out.println(Couleur.aleatoire() + "                           //                                //");
        System.out.println(Couleur.aleatoire() + "                           ////////////////////////////////////");
        System.out.println(Couleur.resetCouleur());
    }

    public static void imprimeJeu2(){
        String uno;
        String dos;
        String tres;
        String cuatro;
        do {
            uno = Couleur.aleatoire();
            dos = Couleur.aleatoire();
            tres = Couleur.aleatoire();
            cuatro = Couleur.aleatoire()+Couleur.bold();
        }while (cuatro.charAt(4) == uno.charAt(4) || cuatro.charAt(4) == dos.charAt(4) || cuatro.charAt(4) == tres.charAt(4));
        System.out.println();
        System.out.println(cuatro + "                           ////////////////////////////////////");
        System.out.println(cuatro + "                           //                                //");
        System.out.println(cuatro + "                           //    "+uno+"EEEEE  "+dos+"XX    XX    "+tres+"CCCC     "+cuatro + "//");
        System.out.println(cuatro + "                           //    "+uno+"E       "+dos+"X    X    "+tres+"C         "+cuatro + "//");
        System.out.println(cuatro + "                           //    "+uno+"EEEE     "+dos+"XXXX    "+tres+"C          "+cuatro + "//");
        System.out.println(cuatro + "                           //    "+uno+"E      "+dos+"X      X   "+tres+"C         "+cuatro + "//");
        System.out.println(cuatro + "                           //    "+uno+"EEEEE  "+dos+"XX    XX    "+tres+"CCCC     "+cuatro + "//");
        System.out.println(cuatro + "                           //                                //");
        System.out.println(cuatro + "                           ////////////////////////////////////");
        System.out.println(Couleur.resetCouleur());
    }


}
