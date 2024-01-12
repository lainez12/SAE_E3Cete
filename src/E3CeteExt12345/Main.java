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
        String un = Couleur.aleatoire();
        String dos = Couleur.aleatoire();
        String tres = Couleur.aleatoire();
        System.out.println();
        System.out.println(Couleur.bold());
        System.out.println(Couleur.aleatoire() + "                           ////////////////////////////////////");
        System.out.println(Couleur.aleatoire() + "                           //                                //");
        System.out.println(Couleur.aleatoire() + "                           //    "+un+"EEEEE  "+dos+"XX    XX    "+tres+"CCCC     "+Couleur.aleatoire() + "//");
        System.out.println(Couleur.aleatoire() + "                           //    "+un+"E       "+dos+"X    X    "+tres+"C         "+Couleur.aleatoire() + "//");
        System.out.println(Couleur.aleatoire() + "                           //    "+un+"EEEE     "+dos+"XXXX    "+tres+"C          "+Couleur.aleatoire() + "//");
        System.out.println(Couleur.aleatoire() + "                           //    "+un+"E      "+dos+"X      X   "+tres+"C         "+Couleur.aleatoire() + "//");
        System.out.println(Couleur.aleatoire() + "                           //    "+un+"EEEEE  "+dos+"XX    XX    "+tres+"CCCC     "+Couleur.aleatoire() + "//");
        System.out.println(Couleur.aleatoire() + "                           //                                //");
        System.out.println(Couleur.aleatoire() + "                           ////////////////////////////////////");
        System.out.println(Couleur.resetCouleur());
    }


}
