package E3CeteBase;

/**
 * La classe Carte représente une carte possèdant une figure répétée un certain nombre de fois avec une texture et une couleur.
 * On a besoin de connaître :
 * - La figure représentée,
 * - Le nombre de fois où la figure est représentée,
 * - La couleur de la figure,
 * - La texture de la figure.
 */
public class Carte  {
    private Couleur couleur;
    private int nbFigure;
    private Figure figure;
    private Texture texture;

    /**
     * Pre-requis : nbFigures > 0
     * Action : Construit une carte contenant nbFigures "figures" qui possèdent une "texture" et une "couleur"
     * Exemple : new Carte(Couleur.ROUGE, 2, Figure.OVALE, Texture.PLEIN) représente une carte contenant 2 figures ovales rouge et pleines
     */

    public Carte(Couleur couleur, int nbFigures, Figure figure, Texture texture) {
        this.couleur = couleur;
        this.nbFigure = nbFigures;
        this.figure = figure;
        this.texture = texture;
    }

    /**
     * Résultat : Le nombre de fois où la figure est représentée sur la carte.
     */

    public int getNbFigures() {
        return this.nbFigure;
    }

    /**
     * Résultat : La figure représentée sur la carte.
     */

    public Figure getFigure() {
        return this.figure;
    }

    /**
     * Résultat : La couleur représentée sur les figures de la carte.
     */

    public Couleur getCouleur() {
        return this.couleur;
    }

    /**
     * Résultat : La texture représentée sur les figures de la carte.
     */

    public Texture getTexture() {
        return this.texture;
    }

    /**
     * Action : compare les attributs de "this" et de "carte"
     * afin de déterminer si this est plus petit, égal ou plus grand que "carte"
     *
     * L'odre d'importance des attrbiuts est celui donné dans le constructeur (du plus prioritaire au moins prioritaire) :
     * Couleur, nombre de figures, figure, texture.
     * Pour comparer les couleurs, les figures et les textures, on utilisera leur position (ordinal) dans
     * leurs énumérations respectives.
     * Ainsi, pour toute paire {c1,c2} de Carte, c1 est inférieure à c2 si et seulement si
     * la valeur de c1 est inférieure à celle de c2 pour la caractéristique ayant la plus grande priorité
     * parmi celles pour lesquelles c1 et c2 ont des valeurs différentes.
     *
     *
     * Résultat :
     *  0 si "this" est égal à "carte"
     *  Un nombre négatif si "this" est inférieur à "carte"
     *  Un nombre strictement positif si "this "est supérieur à "carte"
     */

    public int compareTo(Carte carte) {
        if (cartesEgaux(carte)){
            return 0;
        } else if (!carteSup(carte)) {
            return 1;
        } else return -1;
    }

    public boolean cartesEgaux(Carte carte){
        return this.couleur.ordinal() == carte.couleur.ordinal() && this.nbFigure == carte.nbFigure && this.figure.ordinal() == carte.figure.ordinal() && this.texture.ordinal() == carte.texture.ordinal();
    }

    public boolean carteSup(Carte carte){
        if (this.couleur.ordinal() != carte.couleur.ordinal()){
            return this.couleur.ordinal() < carte.couleur.ordinal();
        }
        if (this.nbFigure != carte.nbFigure){
            return this.nbFigure < carte.nbFigure;
        }
        if (this.figure.ordinal() != carte.figure.ordinal()){
            return this.figure.ordinal() < carte.figure.ordinal();
        }
        return this.texture.ordinal() < carte.texture.ordinal();
    }

    /**
     * Résultat :
     * Une chaîne de caractères représentant la carte de la manière suivante :
     *  - Le texte est coloré selon la couleur de la carte
     *  - La chaîne de caractères retournée doit faire apparaitre toutes les caractériqtiques d'une carte sauf la couleur puisque le texte est affiché en couleur
     *  (Vous devez choisir une représentation agréable pour l'utilisateur)
     */

    @Override
    public String toString() {
        return "" + this.couleur + this.nbFigure + "-" + this.figure.toString(texture.toString()) + "-"+ this.texture.getAbreviation() + Couleur.resetCouleur();
    }

    public static String afficherCartes(Carte[] cartes, int larguer){
        StringBuilder table = new StringBuilder();
        for (int i = 0; i < cartes.length; i+=larguer) {
            table.append(border(cartes.length,i,larguer,cartes));
            table.append("\n");
            table.append(contentCartes(cartes,i,larguer));
            table.append(border(cartes.length,i,larguer,cartes));
            table.append("\n\n");
        }
        table.append(Couleur.resetCouleur());
        return table.toString();
    }
    public static String contentCartes(Carte[] cartes, int i, int larguer){
        StringBuilder table = new StringBuilder();
        int newLarguer = 0;
        if (i+larguer <= cartes.length){
            newLarguer = larguer;
        } else {
            newLarguer = cartes.length-i;
        }
        for (int k = 1; k < 4; k++) {
            for (int j = 0; j < newLarguer; j++) {
                table.append(cartes[i + j].couleur).append("| ").append(printFigure(cartes[i + j], k)).append(" | \t\t");
            }
            table.append("\n");
        }
        return table.toString();
    }

    public static String afficherCartesGrand(Carte[] cartes, int larguer){
        StringBuilder table = new StringBuilder();
        for (int i = 0; i < cartes.length; i+=larguer) {
            table.append(border(cartes.length,i,larguer,cartes));
            table.append("\n");
            table.append(contentCartesGrand(cartes,i,larguer));
            table.append(border(cartes.length,i,larguer,cartes));
            table.append("\n\n");
        }
        table.append(Couleur.resetCouleur());
        return table.toString();
    }
    public static String contentCartesGrand(Carte[] cartes, int i, int larguer){
        StringBuilder table = new StringBuilder();
        int newLarguer = 0;
        if (i+larguer <= cartes.length){
            newLarguer = larguer;
        } else {
            newLarguer = cartes.length-i;
        }
        for (int j = 0; j < newLarguer; j++) {
            table.append(cartes[i + j].couleur).append("| ").append(cartes[i+j].figure).append(" | \t\t");
        }
        table.append("\n");
        for (int j = 0; j < newLarguer; j++) {
            table.append(cartes[i + j].couleur).append("| ").append(cartes[i+j].nbFigure).append(" | \t\t");
        }
        table.append("\n");
        for (int j = 0; j < newLarguer; j++) {
            table.append(cartes[i + j].couleur).append("| ").append(cartes[i+j].texture.getAbreviation()).append(" | \t\t");
        }
        table.append("\n");
        return table.toString();
    }

    public static String border(int length, int i, int larguer, Carte[] cartes){
        StringBuilder table = new StringBuilder();
        int newLarguer = 0;
        if (i+larguer <= length){
            newLarguer = larguer;
        } else {
            newLarguer = length-i;
        }
        for (int j = 0; j < newLarguer; j++) {
                table.append(cartes[i + j].couleur).append("----- \t\t");
        }
        return table.toString();
    }

    public static String printFigure(Carte c, int ligne){
        if (c.nbFigure >= ligne){
            return c.figure.toString(c.texture.toString());
        } else return " ";
    }

    public static int[] getCouleurs(Carte[] cartes){
        int[] couleurs = new int[cartes.length];
        for (int i = 0; i < cartes.length; i++) {
            couleurs[i] = cartes[i].getCouleur().ordinal();
        }
        return couleurs;
    }

    public static int[] getNbsFigures(Carte[] cartes){
        int[] nbsFigures = new int[cartes.length];
        for (int i = 0; i < cartes.length; i++) {
            nbsFigures[i] = cartes[i].getNbFigures();
        }
        return nbsFigures;
    }


    public static int[] getTextures(Carte[] cartes){
        int[] textures = new int[cartes.length];
        for (int i = 0; i < cartes.length; i++) {
            textures[i] = cartes[i].getTexture().ordinal();
        }
        return textures;
    }

    public static int[] getFigures(Carte[] cartes){
        int[] figures = new int[cartes.length];
        for (int i = 0; i < cartes.length; i++) {
            figures[i] = cartes[i].getFigure().ordinal();
        }
        return figures;
    }

}
