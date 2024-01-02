public class Coordonnees {

/**
 * La classe Coordonnees représente les coordonnées (i,j) d'une Carte sur la Table
 * ou i représenta la ligne et j la colonne
 * Cette classe est utilisée uniquement pour intéragir avec l'utilisateur
 *  */

    private int x = 0;
    private int y = 0;

    /**
     * Pre-requis : x,y>=0
     * Action : Construit des Coordonnées ayant x comme numéro de ligne et y comme numéro de colonne
     */
    public Coordonnees(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Pre-requis : input est sous la forme  suivante : int,int
     * Action : Construit des Coordonnées ayant x comme numéro de ligne et y comme numéro de colonne
     */
    public Coordonnees(String input) {
        if (formatEstValide(input)){
            String[] splited = input.split(",");
            //splitted est un tableau de String qui contient les sous chaines de caracteres contenues dans input et séparées par ','
            this.x = Integer.parseInt(splited[0]);
            this.y = Integer.parseInt(splited[1]);
        } else System.out.println("Format invalide");
    }

    /**
     * Action : Retourne le numéro de la ligne
     */
    public int getLigne() {
        return this.x;
    }

    /**
     * Action : Retourne le numéro de la colonne
     */
    public int getColonne() {
        return this.y;
    }

    /**
     * Pre-requis : aucun
     * Action : Retourne vrai si la variable input est dans un format valide à savoir int,int
     * Aide : On peut utiliser Ut.estNombre pour vérifier qu'une chaîne de caractères est bien un nombre.
     */
    public static boolean formatEstValide(String input) {
        if (input.length() != 3 || input.charAt(1) != ','){
            return false;
        }
        String x = String.valueOf(input.charAt(0));
        String y = String.valueOf(input.charAt(2));
        return Ut.estNombre(x) && Ut.estNombre(y);
    }
}
