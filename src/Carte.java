
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
        return "" + this.couleur + this.nbFigure + "-" + this.figure.toString(texture.toString()) + "-"+ this.texture.getAbreviation();
    }

    public String formeCarte(){
        StringBuilder figure = new StringBuilder(" ");
        StringBuilder borde = new StringBuilder("--");
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < this.nbFigure; i++) {
            figure.append(this.figure.toString(texture.toString()) + " ");
        }
        for (int i = 0; i < figure.length(); i++) {
            borde.append("-");
            space.append(" ");
        }
        return "" + this.couleur + borde + "\n|" + figure + "|" + "\n|" + space + "|\n" + borde;
    }

}
