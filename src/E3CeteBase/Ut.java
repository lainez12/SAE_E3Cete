package E3CeteBase;// dmaj : 24 septembre 2023

import java.util.Random;
import java.util.Scanner;

public class Ut {

    public static void afficher(String ch) {
        System.out.print(ch);
    }

    public static void afficherSL(String ch) {
        afficher(ch);
        passerLigne();
    }

    public static void afficher(int nb) {
        System.out.print(nb + "");
    }

    public static void afficherSL(int nb) {
        afficher(nb);
        passerLigne();
    }

    /**
     * Retour a la ligne :
     */

    public static void passerLigne() {
        System.out.println();
    }

    public static void passerALaLigne() {
        passerLigne();
    }

    public static void sauterALaLigne() {
        passerLigne();
    }

    public static void sautLigne() {
        passerLigne();
    }

    public static void afficher(double nb) {
        System.out.print(nb + "");
    }

    public static void afficherSL(double nb) {
        afficher(nb);
        passerLigne();
    }

    public static void afficher(float nb) {
        System.out.print(nb + "");
    }

    public static void afficherSL(float nb) {
        afficher(nb);
        passerLigne();
    }

    public static void afficher(char c) {
        System.out.print(c + "");
    }

    public static void afficherSL(char c) {
        afficher(c);
        passerLigne();
    }

    public static void afficher(boolean b) {
        System.out.print(b + "");
    }

    public static void afficherSL(boolean b) {
        afficher(b);
        passerLigne();
    }

    public static void afficher(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + "\t");
            }
            passerALaLigne();
        }
    }

    public static void afficherSL(int[][] mat) {
        afficher(mat);
        passerLigne();
    }

    public static int saisirEntier() {
        Scanner clavier = new Scanner(System.in);
        String s = clavier.nextLine();
        int lu;
        try {
            lu = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            System.err.println("Ce n'est pas un entier valide");
            return saisirEntier(); // ou System.exit(1)
        }
        return lu;
    }

    /** pré-requis : min <= max
     *  résultat :   un entier saisi compris entre min et max, avec re-saisie éventuelle jusqu'à ce qu'il le soit
     */
    public static int saisirEntierMinMax(int min, int max){
        int n;
        do {
            System.out.println("saisir un entire entre " + min +" et " + max);
            n = saisirEntier();

        }while (n<min || n>max);
        return n;
    }  // fin saisirEntierMinMax


    public static double saisirDouble() {
        return saisirReel();
    }

    public static double saisirReel() {
        Scanner clavier = new Scanner(System.in);
        String s = clavier.nextLine();
        double lu;
        try {
            lu = Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            System.err.println("Ce n'est pas un entier valide");
            return saisirReel(); // ou System.exit(1)
        }
        return lu;
    }

    public static float saisirFlottant() {

        Scanner clavier = new Scanner(System.in);
        String s = clavier.nextLine();
        float lu;
        try {
            lu = Float.parseFloat(s);
        } catch (NumberFormatException ex) {
            System.err.println("Ce n'est pas un reel valide");
            return saisirFlottant();
        }
        return lu;
    }

    public static char saisirCaractere() {

        Scanner clavier = new Scanner(System.in);
        char lu = clavier.next().charAt(0);
        return lu;
    }

    public static boolean saisirBooleen() {

        Scanner clavier = new Scanner(System.in);
        boolean lu = clavier.nextBoolean();
        return lu;
    }

    public static String saisirChaine() {

        Scanner clavier = new Scanner(System.in);
        String s = clavier.nextLine();
        return s;
    }

    public static char[] saisirCharArray() {

        Scanner clavier = new Scanner(System.in);
        String s = clavier.nextLine();
        char[] tab = s.toCharArray();
        return tab;
    }


    public static int alphaToIndex(char c) {
        // Prerequis : c est un caractere entre 'a' et 'z'
        // Resultat : la valeur 0 pour 'a', 12 pour 'm', 25 pour 'z'...
        return (int) c - 97;
    }

    public static char indexToAlpha(int i) {
        // Prerequis : i est un entier entre 0 et 25
        //             (par exemple, indice d'un tableau)
        // Resultat : la valeur 'a' pour 0, 'm' pour 12, 'z' pour 25...b
        return (char) (i + 97);
    }

    /**
     * @param a entier
     * @param b entier
     *          Pre-requis : aucun
     * @return le PGCD de a et b en utilisant l'algorithme d'Euclide
     */
    public static int pgcd(int a, int b) {

        a = Math.abs(a);
        b = Math.abs(b);
        int temp;
        while (b != 0) {
            temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static int randomMinMax(int min, int max) {
        // Resultat : un entier entre min et max choisi aleatoirement
        Random rand = new Random();
        int res = rand.nextInt(max - min + 1) + min;
        // System.out.println(res + " in [" + min + "," + max + "]");
        // assert min <= res && res <= max : "tirage aleatoire hors des bornes";
        return res;
    }

    public static void clearConsole() {
        // Action : efface la console (le terminal)
        System.out.print("\033[H\033[2J");
    }

    public static void pause(int timeMilli) {
        // Action : suspend le processus courant pendant timeMilli millisecondes
        try {
            Thread.sleep(timeMilli);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean estEntier(double n) {
        // Resultat : vrai ssi n est un reel qui est aussi un entier (exemple: 5.0)
        return (int) n == n;
    }

    public static int modulo2(int a, int b) {
        // Pré-requis : b != 0 mais b peut être négatif
        // Résultat : a modulo b, un nombre entre 0 et b-1 si b est positif, entre b+1 et 0 sinon.
        //            Suit la définition : a mod b := a - b * E(a/b)
        // Remarque : l'opérateur modulo de Java ne suit pas cette définition et
        //            peut rendre un nombre de signe opposé à b.
        return a - b * (int) Math.floor((double) a / b);
    }

    public static boolean estBissextile(int an) {
        // Resultat : vrai ssi an est bissextile
        return an % 400 == 0 || an % 4 == 0 && an % 100 != 0;
    }

    public static boolean estNombre(String chaine) {
        try {
            Integer.parseInt(chaine);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static long getTempsExecution(Runnable methodeSansArguments) {
        long startTime = System.nanoTime();
        methodeSansArguments.run();
        long endTime = System.nanoTime();
        return ((endTime - startTime) / 1000000);
    }

    //PR: Tab n'est pas vide.
    //Return vrai ssi x est inclus dans le tab
    public static boolean estInclu(int[] tab, int x){
        for (int valeurs : tab) {
            if (valeurs == x) return true;
        }
        return false;
    }

    //PR: Tab n'est pas vide.
    //Return un tab avec les nombres de nums en order ordre croissant
    public static int[] order(int[] nums){//Ext 1
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                if (nums[j] < nums[i]){
                    int min = nums[j];
                    nums[j] = nums[i];
                    nums[i] = min;
                }
            }
        }
        return nums;
    }



    //PR: min < max.
    //Return vrai ssi val est <= max et >= min
    public static boolean estDansleRange(int min, int max, int val){
        return val <= max && val >=min;
    }

    //PR: Tab n'est pas vide.
    //Action parcours le table depuis l'incide 1 et met dans chaque caisse le valeur dans l'indice 0 plus l'indice i
    //Example tab{2,?,?,?} -->resulta tab{2,3,4,5}
    public static void tabSommeIetIndice0(int[] tab){
        for (int i = 1; i < tab.length; i++) {
            tab[i] = i+tab[0];
        }
    }

    //PR: Tab n'est pas vide, nbMax es positif, tab[1]<nbMax.
    //Return tab avec la valeur du dernier indice augmenté ssi il est < nbMax du contraire
    //le dernier indice prend la valuer d'indice 0 + i et continue son parcours du tab
    //jusqu' à trouve une caisse et pouvoir l'augmenter + 1;
    public static int[] augmDernierIndice(int[] tab, int nbMax){
        for (int i = tab.length-1; i > 0; i--) {
            if(tab[i] < nbMax){
                tab[i]++;
                return tab;
            } else {
                tab[i] = -1;
                int temp = i+tab[0];
                while (temp < nbMax && estInclu(tab,temp)){
                    temp++;
                }
                tab[i] = temp;
            }
        }
        return tab;
    }

    //PR: Tab n'est pas vide.
    //Action parcours la table et met dans une variable temp le valeur de tab[i]
    //et dans chaque caisse le valeur de la prochine caisse, si temp est inclus dans tab retur true.
    //Return true ssi il n'y pas des doublons de valuers dans le tab.
    public static boolean tabAvecDoublons(int[] tab){
        for (int i = 0; i < tab.length - 1; i++) {
            int temp = tab[i];
            tab[i] = tab[i+1];
            if (estInclu(tab,temp)){
                tab[i] = temp;
                return true;
            } else {
                tab[i] = temp;
            }
        }
        return false;
    }

    // Function pour calculer le factoriel du n, PR: n est positif
    static double factoriel(int n) {
        if (n > 1) {
            return n * factoriel(n - 1);
        }
        return 1;
    }

    // Function pour calculer les combinación de nCk, PR: n>=k>0 (n et k sont positif
    public static double combinaison(int n, int k) {
        return factoriel(n) / (factoriel(k) * factoriel(n - k));
    }

} // end class




	
