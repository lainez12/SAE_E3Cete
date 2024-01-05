package E3CeteExt;

import E3CeteBase.Carte;
import E3CeteBase.Paquet;
import E3CeteBase.Couleur;
import E3CeteBase.Figure;
import E3CeteBase.Texture;
import E3CeteBase.Ut;



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
        System.out.println("\"Combien de valeurs souhaitez-vous sélectionner parmi les 20 pour la caractéristique Couleur?\"");
        int couleurs = Ut.saisirEntierMinMax(1,20);
        System.out.println("\"Et pour la caractéristique Figure? (aussi avec 20 valeurs)\"");
        int figures = Ut.saisirEntierMinMax(1,20);
        System.out.println("\"Et pour la caractéristique Nombre_De_Figures_Max?\"");
        int nbF = Ut.saisirEntierMinMax(1,20);
        System.out.println("\"Et pour la caractéristique Texture? (aussi avec 20 valeurs)\"");
        int textures = Ut.saisirEntierMinMax(1,20);
        this.paq = new Paquet(Couleur.values(couleurs),nbF,Figure.values(figures),Texture.values(textures));
        int hauteur = 0;
        int larguer = 0;
        do {
            System.out.println("Quel est l'hauter de votre table?");
            hauteur = Ut.saisirEntier();
            System.out.println("Quel est l'hauter de votre table?");
            larguer = Ut.saisirEntier();
        }while (!this.possibleTable(hauteur,larguer));
        this.tab = new Table(hauteur, larguer);
        this.score = 0;
    }

    private boolean possibleTable(int h, int l){
        boolean rep = (h*l) <= (this.paq.getIndiceCarteRestante()/2);
        if (!rep){
            System.out.println("La table est trop grande, Ressayez");
        }
        return rep;
    }

    /**
     * Action : Pioche autant de cartes qu'il y a de numéros de cartes dans le tableau numerosDeCartes et les place
     * aux positions correspondantes sur la table.
     */

    public void piocherEtPlacerNouvellesCartes(int[] numerosDeCartes) {
        Carte[] cartesPioches = this.paq.piocher(numerosDeCartes.length);
        if (cartesPioches != null) {
            this.tab.placeCartes(cartesPioches, numerosDeCartes);
        } else this.tab.effaceCartes(numerosDeCartes);
    }

    /**
     * Action : Ré-initialise les données et variables du jeu afin de rejouer une nouvelle partie.
     */

    public void resetJeu() {
        this.paq.setIndiceCarteRestante();
        this.paq.melanger();
        this.tab = new Table(tab.getHauteur(), tab.getLarguer());
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

     public int[] chercherE3COuPlusSurTableOrdinateur() {
        int taille = tab.getCartesSurTable();
        int[][] tousLesE3C = new int[1][3];
        int compteurE3C = 0;
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (i == j) continue;
                for (int k = 0; k < taille; k++) {
                    if (i == k || j == k){ continue;}
                    Carte[] cartes = this.tab.getCartes(new int[]{i,j,k});
                    if (estUnE3C(cartes)) {
                        tousLesE3C = Ut.ajoute(tousLesE3C,new int[]{i, j, k});
                        compteurE3C++;
                    }
                }
            }
        }
        if (compteurE3C > 0){
            tousLesE3C = effacerDuplE3C(tousLesE3C);
            int[] taillesEXC = new int[compteurE3C];
            for (int i = 0; i < compteurE3C; i++) {
                taillesEXC[i] = maxDuE3C(tousLesE3C[i]).length;
            }
            int e3cAvecPlus = Ut.indicePlusHaut(taillesEXC);
            return maxDuE3C(tousLesE3C[e3cAvecPlus]);
        }

        return null;
    }

    public int[] maxDuE3C(int[] e3c){
        int[] nouveau = Ut.copieDuTab(e3c);
        int[] test = Ut.copieDuTab(nouveau);
        do {
            test = ajouterUnEXC(test);
            if (test != null) nouveau = Ut.copieDuTab(test);
        } while (test != null);
        return nouveau;
    }
    public int[] ajouterUnEXC(int[] eXc){
        int taille = tab.getCartesSurTable();
        int[] nouveauEXC = Ut.copieDuTab(eXc);
        for (int i = 0; i < taille; i++) {
            if (!Ut.estInclu(eXc,i)){
                nouveauEXC = Ut.ajoute(nouveauEXC,i);
                Carte[] cartes = this.tab.getCartes(nouveauEXC);
                if (estUnE3C(cartes)) return nouveauEXC;
            }
        }
        return null;
    }

    public int[][] effacerDuplE3C(int[][] tousLesE3C){
        int[][] nouvelle = Ut.copieDuTab(tousLesE3C);
        for (int i = 0; i < tousLesE3C.length; i++) {
            for (int j = 0; j < tousLesE3C.length; j++) {
                if (i == j) continue;
                if (e3cEgaux(tousLesE3C[i], tousLesE3C[j])){
                    Ut.effacerTabDansMatrice(nouvelle,j);
                }
            }
        }
        return nouvelle;
    }

    public boolean e3cEgaux(int[] e3c1, int[] e3c2){
        boolean[] egaux = new boolean[3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e3c1[i] == e3c2[j]) {
                    egaux[i] = true;
                }
            }
        }
        return egaux[0] && egaux[1] && egaux[2];
    }

    /**
     * Action : Sélectionne alétoirement trois cartes sur la table.
     * La sélection ne doit pas contenir de doublons
     * Résullat : un tableau contenant les numéros des cartes sélectionnées alétaoirement
     */

    public int[] selectionAleatoireDeCartesOrdinateur() {
        int i,j,k;
        do {
            i = Ut.randomMinMax(0,tab.getCartesSurTable()-1);
            j = Ut.randomMinMax(0,tab.getCartesSurTable()-1);
            k = Ut.randomMinMax(0,tab.getCartesSurTable()-1);
        } while (i == j || j == k || i == k);
        return new int[]{i,j,k};
    }

    /**
     * Résullat : Vrai si la partie en cours est terminée.
     */

    public boolean partieEstTerminee() {
        return this.paq.getIndiceCarteRestante() < 3 && this.tab.getCartesSurTable() < 3;
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
        this.tab.setCartesSurTable(tab.getCartesSurTable()-cartejoueur.length);
        piocherEtPlacerNouvellesCartes(cartejoueur);

    }

    /**
     * Action : Fait jouer une partie à un joueur humain.
     * A la fin, le score final du joueur est affiché.
     */

    public void jouerHumain() {
        demmarreTable();
        while (!partieEstTerminee()){
            jouerTourHumain();
        }
        System.out.println("Votre score final est de " + this.score);
    }

    public void demmarreTable(){
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
        int[] cartesTemp = chercherE3COuPlusSurTableOrdinateur();
        if (cartesTemp == null){
            System.out.println(Couleur.resetCouleur() + "\nL'ordi n'a pas trouvé de E3C donc il séléctionne des cartes au hasard");
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
        this.tab.setCartesSurTable(tab.getCartesSurTable()-cartesTemp.length);
        piocherEtPlacerNouvellesCartes(cartesTemp);
    }

    /**
     * Action : Fait jouer une partie à l'ordinateur.
     * Une pause est faite entre chaque tour (500 ms ou plus) afin de pouvoir observer la progression de l'ordinateur.
     * A la fin, le score final de l'ordinateur est affiché.
     * Rappel : Ut.pause(temps) permet de faire une pause de "temps" millisecondes
     */

    public void jouerOrdinateur() {
        demmarreTable();
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

