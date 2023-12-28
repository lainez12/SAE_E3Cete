/**
 * La classe Jeu permet de faire des parties du jeu "E3Cète" soit avec un humain, soit avec un ordinateur.
 *
 * Règles :
 *
 *  - On possède un paquet de cartes qui représentent entre une et trois figures (losange, carre ou ovale), une texture
 *   (vide, hachuré ou plein) et une couleur (rouge, jaune ou bleu). La cardinalité des énumérations est fixée à 3 pour cette partie 2 de la SAE uniquement.
 *
 *  - Une table 3x3 permet de stocker 9 cartes. Au début de la partie, on dispose 9 cartes sur la table, piochées sur le dessus du paquet.
 *
 *  - A chaque tour, le joueur doit essayer de trouver un E3C.
 *
 *  - Le joueur doit désigner trois cartes par leurs coordonnées.
 *      - Si ces cartes forment un E3C, il gagne trois points sur son score.
 *      - Si ce n'est pas un E3C, il perd 1 point sur son score.
 *
 *   - Les trois cartes sont remplacées par de nouvelles cartes piochées dans le paquet.
 *
 *   - La partie se termine quand il n'y a plus de cartes dans le paquet (même s'il reste des cartes sur la table).
 *
 * On a donc besoin :
 *
 * - D'un paquet pour stocker toutes les cartes et avoir une pioche.
 * - D'une table.
 * - De quoi stocker le score du joueur (humain ou ordinateur).
 */
public class Jeu {
    private Paquet paq;
    private Table tab;
    private int score;
    private int score2;

    /**
     * Action :
     * - Initialise le jeu "E3Cète" en initialisant le score du joueur, le paquet et la table.
     * - La table doit être remplie.
     */

    public Jeu() {
        this.paq = new Paquet(Couleur.values(), 3, Figure.values(), Texture.values());
        this.tab = new Table(3, 3);
        this.score = 0;
        this.score2 = 0;
    }

    /**
     * Action : Pioche autant de cartes qu'il y a de numéros de cartes dans le tableau numerosDeCartes et les place
     * aux positions correspondantes sur la table.
     */

    public void piocherEtPlacerNouvellesCartes(int[] numerosDeCartes) {
        Carte[] cartesPioches = this.paq.piocher(numerosDeCartes.length);
        this.tab.placeCartes(cartesPioches);
    }

    /**
     * Action : Ré-initialise les données et variables du jeu afin de rejouer une nouvelle partie.
     */

    public void resetJeu() {
        this.paq = new Paquet(Couleur.values(), 3, Figure.values(), Texture.values());
        this.tab = new Table(3, 3);
        this.score2 = 0;
        this.score = 0;
    }

    /**
     * Résullat : Vrai si les cartes passées en paramètre forment un E3C.
     */

    public static boolean estUnE3C(Carte[] cartes) {
        Carte c1 = cartes[0];
        Carte c2 = cartes[1];
        Carte c3 = cartes[2];
        if (memeAttributs(1,c1,c2,c3)){
            return diffAttributs(2,c1,c2,c3) && diffAttributs(3,c1,c2,c3) && diffAttributs(4,c1,c2,c3);
        } else if (memeAttributs(2,c1,c2,c3) && memeAttributs(4,c1,c2,c3)) {
            return diffAttributs(1,c1,c2,c3) && diffAttributs(3,c1,c2,c3);
        } else if (memeAttributs(3,c1,c2,c3)) {
            return diffAttributs(1,c1,c2,c3) && diffAttributs(2,c1,c2,c3) && diffAttributs(4,c1,c2,c3);
        } else {
            return diffAttributs(1,c1,c2,c3) && diffAttributs(2,c1,c2,c3) && diffAttributs(3,c1,c2,c3) && diffAttributs(4,c1,c2,c3);
        }

    }

    public static boolean memeAttributs(int attribut, Carte c1, Carte c2, Carte c3){
        switch (attribut){
            case 1:
                return c1.getCouleur() == c2.getCouleur() && c2.getCouleur() == c3.getCouleur() && c3.getCouleur() == c1.getCouleur();
            case 2:
                return c1.getNbFigures() == c2.getNbFigures() && c2.getNbFigures() == c3.getNbFigures() && c3.getNbFigures() == c1.getNbFigures();
            case 3:
                return c1.getFigure() == c2.getFigure() && c2.getFigure() == c3.getFigure() && c3.getFigure() == c1.getFigure();
            case 4:
                return c1.getTexture() == c2.getTexture() && c2.getTexture() == c3.getTexture() && c3.getTexture() == c1.getTexture();
            default:
                return c1.compareTo(c2) == c2.compareTo(c3);
        }
    }

    public static boolean diffAttributs(int attribut, Carte c1, Carte c2, Carte c3){
        switch (attribut){
            case 1:
                return c1.getCouleur() != c2.getCouleur() && c2.getCouleur() != c3.getCouleur() && c3.getCouleur() != c1.getCouleur();
            case 2:
                return c1.getNbFigures() != c2.getNbFigures() && c2.getNbFigures() != c3.getNbFigures() && c3.getNbFigures() != c1.getNbFigures();
            case 3:
                return c1.getFigure() != c2.getFigure() && c2.getFigure() != c3.getFigure() && c3.getFigure() != c1.getFigure();
            case 4:
                return c1.getTexture() != c2.getTexture() && c2.getTexture() != c3.getTexture() && c3.getTexture() != c1.getTexture();
            default:
                return c1.compareTo(c2) != c2.compareTo(c3);
        }
    }
    /**
     * Action : Recherche un E3C parmi les cartes disposées sur la table.
     * Résullat :
     *  - Si un E3C existe, un tableau contenant les numéros de cartes (de la table) qui forment un E3C.
     *  - Sinon, la valeur null.
     */

    public int[] chercherE3CSurTableOrdinateur() {
        for (int i = 0; i < tab.getTaille(); i++) {
            for (int j = 0; j < tab.getTaille(); j++) {
                for (int k = 0; k < tab.getTaille(); k++) {
                    Carte[] cartes = new Carte[]{tab.getCarte(i),tab.getCarte(j),tab.getCarte(k)};
                    if (estUnE3C(cartes)){
                        return new int[]{i,j,k};
                    }
                }
            }
        }
        return null;
    }

    /**
     * Action : Sélectionne alétoirement trois cartes sur la table.
     * La sélection ne doit pas contenir de doublons
     * Résullat : un tableau contenant les numéros des cartes sélectionnées alétaoirement
     */

    public int[] selectionAleatoireDeCartesOrdinateur() {
        int i,j,k;
        do {
            i = Ut.randomMinMax(0,tab.getTaille()-1);
            j = Ut.randomMinMax(0,tab.getTaille()-1);
            k = Ut.randomMinMax(0,tab.getTaille()-1);
        } while (i != j && j != k && i != k);
        return new int[]{i,j,k};
    }

    /**
     * Résullat : Vrai si la partie en cours est terminée.
     */

    public boolean partieEstTerminee() {
        throw new RuntimeException("Méthode non implémentée ! Effacez cette ligne et écrivez le code nécessaire");
    }

    /**
     * Action : Fait jouer un tour à un joueur humain.
     * La Table et le score du joueur sont affichés.
     * Le joueur sélectionne 3 cartes.
     *  - Si c'est un E3C, il gagne trois points.
     *  - Sinon, il perd un point.
     * Les cartes sélectionnées sont remplacées.
     * Divers messages d'informations doivent être affichés pour l'ergonomie.
     */

    public void jouerTourHumain() {

    }

    /**
     * Action : Fait jouer une partie à un joueur humain.
     * A la fin, le score final du joueur est affiché.
     */

    public void jouerHumain() {

    }

    /**
     * Action : Fait jouer un tour à l'ordinateur.
     * La Table et le score de l'ordinateur sont affichés.
     * L'ordinateur sélectionne des cartes :
     *  - L'ordinateur essaye toujours de trouver un E3C sur la table. S'il en trouve un, il gagne donc trois points.
     *  - S'il n'en trouve pas, il se rabat sur 3 cartes sélectionnées aléatoirement et perd un point.
     * Les cartes sélectionnées sont remplacées.
     * Divers messages d'informations doivent être affichés pour l'ergonomie.
     */

    public void joueurTourOrdinateur() {

    }

    /**
     * Action : Fait jouer une partie à l'ordinateur.
     * Une pause est faite entre chaque tour (500 ms ou plus) afin de pouvoir observer la progression de l'ordinateur.
     * A la fin, le score final de l'ordinateur est affiché.
     * Rappel : Ut.pause(temps) permet de faire une pause de "temps" millisecondes
     */

    public void jouerOrdinateur() {

    }

    /**
     * Action : Permet de lancer des parties de "E3Cète" au travers d'un menu.
     * Le menu permet au joueur de sélectionner une option parmi :
     *  - humain : lance une partie avec un joueur humain
     *  - ordinateur : lance une partie avec un ordinateur
     *  - terminer : arrête le programme.
     * Après la fin de chaque partie, les données de jeu sont ré-initialisées et le menu est ré-affiché
     * (afin de faire une nouvelle sélection).
     * Les erreurs de saisie doivent être gérées (si l'utilisateur sélectionne une option inexistante).
     * Divers messages d'informations doivent être affichés pour l'ergonomie.
     */

    public void jouer() {

    }
}
