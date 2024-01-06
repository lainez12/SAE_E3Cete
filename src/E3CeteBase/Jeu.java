package E3CeteBase;

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

    /**
     * Action :
     * - Initialise le jeu "E3Cète" en initialisant le score du joueur, le paquet et la table.
     * - La table doit être remplie.
     */

    public Jeu() {
        this.paq = new Paquet(Couleur.values(3), 3, Figure.values(3), Texture.values(3));
        this.tab = new Table(3, 3);
        this.score = 0;
    }

    /**
     * Action : Pioche autant de cartes qu'il y a de numéros de cartes dans le tableau numerosDeCartes et les place
     * aux positions correspondantes sur la table.
     */

    public void piocherEtPlacerNouvellesCartes(int[] numerosDeCartes) {
        Carte[] cartesPioches = this.paq.piocher(numerosDeCartes.length);
        this.tab.placeCartes(cartesPioches,numerosDeCartes);
    }

    /**
     * Action : Ré-initialise les données et variables du jeu afin de rejouer une nouvelle partie.
     */

    public void resetJeu() {
        this.paq.setIndiceCarteRestante();
        this.paq.melanger();
        this.score = 0;
    }
    /**
     * Résullat : Vrai si les cartes passées en paramètre forment un E3C.
     */

public static boolean estUnE3C(Carte[] cartes) {
            int[] couleurs = Carte.getCouleurs(cartes);
            int[] nbFigures = Carte.getNbsFigures(cartes);
            int[] figures = Carte.getFigures(cartes);
            int[] textures = Carte.getTextures(cartes);
            return attributsSansError(couleurs,nbFigures,figures,textures);
        }

        public static boolean attributsSansError(int[] couleurs, int[] nbFigures, int[] figures, int[] textures){
            int[][] attributsTab =  {couleurs,nbFigures,figures,textures};
            for (int[] attributs : attributsTab) {
                    boolean attributMeme = memeAttributs(attributs);
                    if (!attributMeme){
                            if (!diffAttributs(attributs)){
                                    return false;
                                }
                        }
                }
            return true;
        }

    public static boolean memeAttributs(int[] attributs){
        for (int i = 0; i < attributs.length; i++) {
            for (int j = 0; j < attributs.length; j++) {
                if (attributs[i] != attributs[j]){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean diffAttributs(int[] attributs){
        for (int i = 0; i < attributs.length; i++) {
            for (int j = 0; j < attributs.length; j++) {
                if (attributs[i] == attributs[j] && j != i){
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Action : Recherche un E3C parmi les cartes disposées sur la table.
     * Résullat :
     *  - Si un E3C existe, un tableau contenant les numéros de cartes (de la table) qui forment un E3C.
     *  - Sinon, la valeur null.
     */

    public int[] chercherE3CSurTableOrdinateur() {
        int taille = tab.getTaille();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (i == j) continue;
                for (int k = 0; k < taille; k++) {
                    if (i == k || j == k) continue;
                    Carte carteI = tab.getCarte(i);
                    Carte carteJ = tab.getCarte(j);
                    Carte carteK = tab.getCarte(k);
                    Carte[] cartes = new Carte[]{carteI, carteJ, carteK};
                    if (estUnE3C(cartes)) {
                        return new int[]{i, j, k};
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
        } while (i == j || j == k || i == k);
        return new int[]{i,j,k};
    }

    /**
     * Résullat : Vrai si la partie en cours est terminée.
     */

    public boolean partieEstTerminee() {
        return this.paq.estVide();
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
        System.out.println("Votre score est de " + this.score);
        System.out.println("La table est la suivante \n" + this.tab);
        System.out.println(Couleur.resetCouleur() + "veuillez séléctioner 3 cartes pour tenter de réaliser un E3C.");
        int[] cartejoueur = this.tab.selectionnerCartesJoueur(3);
        Carte[] cartesJ = new Carte[3];
        this.tab.afficherSelection(cartejoueur);
        for (int i = 0; i < 3; i++) {
            cartesJ[i] = this.tab.getCarte(cartejoueur[i]);
        }
        if (estUnE3C(cartesJ)){
            System.out.println("Bravo vous avez réaliser un E3C. \nVous gagner donc 3 point.");
            this.score += 3;
        }
        else {
            System.out.println(Couleur.resetCouleur() + "\nDommage ce n'est pas un E3C \n Vous perdez un point.");
            this.score -= 1;
        }
        System.out.println("Des nouveeles cartes remplace celle séléctioner sur la table");
        piocherEtPlacerNouvellesCartes(cartejoueur);

    }

    /**
     * Action : Fait jouer une partie à un joueur humain.
     * A la fin, le score final du joueur est affiché.
     */

    public void jouerHumain() {
        demmarreJeu();
        while (!partieEstTerminee()){
            jouerTourHumain();
        }
        System.out.println("Votre score final est de " + this.score);
    }

    public void demmarreJeu(){
        int[] table = new int[this.tab.getTaille()];
        for (int i = 0; i < table.length; i++) {
            table[i] = i;
        }
        piocherEtPlacerNouvellesCartes(table);
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
        System.out.println("Le score de l'ordi est de " + this.score);
        System.out.println("La table est la suivante \n" + this.tab);
        int[] cartesTemp = chercherE3CSurTableOrdinateur();
        if (cartesTemp == null){
            System.out.println(Couleur.resetCouleur() + "\nL'ordi n'a pas trouvé de E3C donc il séléctinne des cartes au hasard");
            cartesTemp = selectionAleatoireDeCartesOrdinateur();
            this.tab.afficherSelection(cartesTemp);
            this.score -= 1;
        }
        else {
            this.tab.afficherSelection(cartesTemp);
            System.out.println(Couleur.resetCouleur() + "\nL'ordi a trouvé un E3C");
            this.score += 3;
        }
        System.out.println(Couleur.resetCouleur() + "\nDes nouveeles cartes remplace celle séléctioner sur la table");
        piocherEtPlacerNouvellesCartes(cartesTemp);
    }

    /**
     * Action : Fait jouer une partie à l'ordinateur.
     * Une pause est faite entre chaque tour (500 ms ou plus) afin de pouvoir observer la progression de l'ordinateur.
     * A la fin, le score final de l'ordinateur est affiché.
     * Rappel : Ut.pause(temps) permet de faire une pause de "temps" millisecondes
     */

    public void jouerOrdinateur() {
        demmarreJeu();
        while (!partieEstTerminee()){
            joueurTourOrdinateur();
            /*Ut.pause(3500);*/
        }
        System.out.println("LE score final de l'ordi est de " + this.score);
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
        int rep = 0;
        do{
            System.out.println(Couleur.bold() + Couleur.BLEU + "tapez 1 pour jouer \n"+ Couleur.JAUNE+ "tapez 2 pour que l'ordi joue \n" + Couleur.ROUGE + "tapez 3 pour mettre fin au programe" + Couleur.resetCouleur());
            rep = Ut.saisirEntierMinMax(1,3);
            if (rep == 1){
                jouerHumain();
            }
            else if (rep == 2) {
                jouerOrdinateur();
            }
            resetJeu();
        }while (rep != 3);
        System.out.println("Merci d'avoir joué a notre jeu");
    }
}

