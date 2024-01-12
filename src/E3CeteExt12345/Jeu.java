package E3CeteExt12345;

import E3CeteBase.Carte;
import E3CeteBase.Paquet;
import E3CeteBase.Couleur;
import E3CeteBase.Figure;
import E3CeteBase.Texture;
import E3CeteBase.Ut;

import java.util.concurrent.TimeUnit;


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
    private int exc = 0;

    /**
     * Action :
     * - Initialise le jeu "E3Cète" en initialisant le score du joueur, le paquet et la table.
     * - La table doit être remplie.
     */

    public Jeu() {
        System.out.println("\"Combien de valeurs souhaitez-vous sélectionner parmi les 20 pour les caractéristique?\"");
        this.exc = Ut.saisirEntierMinMax(3,20);
        this.paq = new Paquet(Couleur.values(this.exc),this.exc,Figure.values(this.exc),Texture.values(this.exc));
        int hauteur = 0;
        int larguer = 0;
        do {
            System.out.println("Quel est l'hauter de votre table?");
            hauteur = Ut.saisirEntierMinMax(1,20);
            System.out.println("Quel est la larguer de votre table?");
            larguer = Ut.saisirEntierMinMax(1,20);
        }while (!this.possibleTable(hauteur,larguer));
        this.tab = new Table(hauteur, larguer);
        this.score = 0;
    }

    private boolean possibleTable(int h, int l){
        int taillePaq = this.paq.getIndiceCarteRestante();
        boolean rep = (h*l)%this.exc == 0;
        if (!rep){
            System.out.println("La table n'est pas divisible par " + this.exc);
        }
        rep = (h*l) >= this.exc;
        if (!rep){
            System.out.println("La table est trop petit");
        }
        rep = (taillePaq-(h*l))%this.exc == 0;
        if (!rep){
            System.out.println("La quantite de cartes dans paquet n'est pas divisible par " + this.exc);
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

     public static boolean estUnExC(Carte[] cartes) {
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

     public int[] chercherExCSurTableOrdinateur() {
        int taille = tab.getCartesSurTable();
        System.out.println("Cartes sur Table " + taille);
        int[] exc = new int[this.exc];
        for (int i = 0; i <= taille-this.exc; i++) {
            exc[0] = i;
            Ut.tabSommeIetIndice0(exc);
            if (chercherExCTouslesCombinaison(exc,taille) != null){
                return exc;
            }
        }

        return null;
    }

    public int[] chercherExCTouslesCombinaison(int[] tabCartes, int cartesSurTable){
         Carte[] cartes = this.tab.getCartes(tabCartes);
         if (estUnExC(cartes))return tabCartes;
         do {
             Ut.augmDernierIndice(tabCartes,cartesSurTable-1);
             cartes = this.tab.getCartes(tabCartes);
             if (!Ut.tabAvecDoublons(tabCartes) && estUnExC(cartes))return tabCartes;
         }while (tabCartes[1] < cartesSurTable-1 || tabCartes[this.exc-1] < cartesSurTable-1);
         return null;
    }

    /**
     * Action : Sélectionne alétoirement trois cartes sur la table.
     * La sélection ne doit pas contenir de doublons
     * Résullat : un tableau contenant les numéros des cartes sélectionnées alétaoirement
     */

    public int[] selectionAleatoireDeCartesOrdinateur() {
        int[] selection = new int[this.exc];
        int cartesTable = tab.getCartesSurTable()-1;
        if (this.exc == cartesTable+1){
            selection[0] = 0;
            Ut.tabSommeIetIndice0(selection);
            return selection;
        }
        for (int i = 0; i < this.exc; i++) {
            int essaie;
            do {
                essaie = Ut.randomMinMax(0,cartesTable);
            } while (Ut.estInclu(selection,essaie));
            selection[i] = essaie;
        }
        return selection;
    }

    /**
     * Résullat : Vrai si la partie en cours est terminée.
     */

    public boolean partieEstTerminee() {
        return this.paq.estVide() && this.tab.getCartesSurTable() < this.exc;
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
        System.out.println(Couleur.resetCouleur() + "veuillez séléctioner "+ this.exc +" cartes pour tenter de réaliser un E3C.");
        int[] cartejoueur = this.tab.selectionnerCartesJoueur(this.exc);
        Carte[] cartesJ = new Carte[3];
        this.tab.afficherSelection(cartejoueur);
        for (int i = 0; i < 3; i++) {
            cartesJ[i] = this.tab.getCarte(cartejoueur[i]);
        }

        if (estUnExC(cartesJ)){
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
        int[] cartesTemp = chercherExCSurTableOrdinateur();
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
            Ut.pause(3500);
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
        int difficulter = 0;
        do{
            System.out.println(Couleur.bold() + Couleur.BLEU + "tapez 1 pour jouer \n"+ Couleur.JAUNE+ "tapez 2 pour que l'ordi joue \n" +Couleur.C8_GREEN3 + "tapez 3 pour jouer avec du temps \n" + Couleur.ROUGE + "tapez 4 pour mettre fin au programe" + Couleur.resetCouleur());
            rep = Ut.saisirEntierMinMax(1,4);
            if (rep == 1){
                jouerHumain();
            }
            else if (rep == 2) {
                jouerOrdinateur();
            }
            else if (rep == 3){
                System.out.println( Couleur.bold() + Couleur.C9_FUCHSIA + "tapez 1 pour la verssion 1(temps partie)\n" + Couleur.C4_PINK_DEEP + "tapez 2 pour la verssion 2(temps au tour)" + Couleur.resetCouleur());
                rep = Ut.saisirEntierMinMax(1,2);
                System.out.println(Couleur.bold() + Couleur.C19_DARKSLATEGRAY1 + "Sélectioner votre niveau de difficulter en tapant " + Couleur.ROUGE + "1 pour facile, " + Couleur.JAUNE + "2 pour moyen et " + Couleur.C15_HOTPINK + "3 pour difficile" + Couleur.resetCouleur());
                difficulter = Ut.saisirEntierMinMax(1,3);
                if (rep == 1){
                    jouerHumainTempsPartie(difficulter);
                } else if (rep == 2) {
                    jouerHumainTempsTours(difficulter);
                }
            }
            resetJeu();
        }while (rep != 4);
        System.out.println("Merci d'avoir joué a notre jeu");
    }


    public void jouerTourHumainTempsPartie() {
        System.out.println("Votre score est de " + this.score);
        System.out.println("La table est la suivante \n" + this.tab);


        System.out.println("Coisissez entre \n tenter une E3C en tapant 1 \n remplacer une carte en tapant 2");
        int choixJoueur = Ut.saisirEntierMinMax(1, 2);
        if (choixJoueur == 1) {
            System.out.println(Couleur.resetCouleur() + "veuillez séléctioner "+ this.exc +" cartes pour tenter de réaliser un E3C.");
            int[] cartejoueur = this.tab.selectionnerCartesJoueur(this.exc);
            Carte[] cartesJ = new Carte[3];
            this.tab.afficherSelection(cartejoueur);
            for (int i = 0; i < 3; i++) {
                cartesJ[i] = this.tab.getCarte(cartejoueur[i]);
            }
            if (estUnExC(cartesJ)) {
                System.out.println("Bravo vous avez réaliser un E3C. \nVous gagner donc 3 points.");
                this.score += 3;
                System.out.println("Des nouveeles cartes remplace celle séléctioner sur la table");
                piocherEtPlacerNouvellesCartes(cartejoueur);
            } else {
                System.out.println(Couleur.resetCouleur() + "\nDommage ce n'est pas un E3C \n Vous perdez 3 points.");
                this.score -= 3;
            }
        }
        else {
            System.out.println("Séléctionez la carte que vous voulez remplacer");
            int[] carteRemplacer = this.tab.selectionnerCartesJoueur(1);
            piocherEtPlacerNouvellesCartes(carteRemplacer);
        }
    }


    public void jouerHumainTempsPartie(int difficulter) {
        demmarreTable();
        int tempsPartie;
        int tempScore = this.score;
        if (difficulter == 1){
            tempsPartie = 15 * 60;
        } else if (difficulter == 2){
            tempsPartie = 10 * 60;
        }
        else {
            tempsPartie = 5 * 60;
        }
        long secondes = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        long secondes2 = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        long tempsDeLaPartie = secondes2 - secondes;
        System.out.println(" Le temps de la parti est de " + tempsPartie + " seconde");
        long tempsRestant = tempsPartie - tempsDeLaPartie;
        while (!partieEstTerminee() && tempsDeLaPartie < tempsPartie){
            System.out.println(" il vous reste " + tempsRestant + " seconde dans la partie");
            tempScore = this.score;
            jouerTourHumainTempsPartie();
            secondes2 = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            tempsDeLaPartie = secondes2 - secondes;
            if (tempsDeLaPartie > tempsPartie){
                this.score = tempScore;
                System.out.println("Le temps de la partie est terminée");
            }
            tempsRestant = tempsPartie - tempsDeLaPartie;
        }
        System.out.println("il vous restez " + tempsRestant + " seconde");
        System.out.println("Votre score final est de " + this.score);
    }


    public void jouerTourHumainTempsTours() {
        System.out.println("Votre score est de " + this.score);
        System.out.println("La table est la suivante \n" + this.tab);


        System.out.println("Coisissez entre \n tenter une E3C en tapant 1 \n remplacer une carte en tapant 2");
        int choixJoueur = Ut.saisirEntierMinMax(1, 2);
        if (choixJoueur == 1) {
            System.out.println(Couleur.resetCouleur() + "veuillez séléctioner "+ this.exc +" cartes pour tenter de réaliser un E3C.");
            int[] cartejoueur = this.tab.selectionnerCartesJoueur(this.exc);
            Carte[] cartesJ = new Carte[3];
            this.tab.afficherSelection(cartejoueur);
            for (int i = 0; i < 3; i++) {
                cartesJ[i] = this.tab.getCarte(cartejoueur[i]);
            }
            if (estUnExC(cartesJ)) {
                System.out.println("Bravo vous avez réaliser un E3C. \nVous gagner donc 3 points.");
                this.score += 3;
                System.out.println("Des nouveeles cartes remplace celle séléctioner sur la table");
                piocherEtPlacerNouvellesCartes(cartejoueur);
            } else {
                System.out.println(Couleur.resetCouleur() + "\nDommage ce n'est pas un E3C \n La partie se termine");
                this.score -= 1;
            }
        }
        else {
            System.out.println("Séléctionez la carte que vous voulez remplacer");
            int[] carteRemplacer = this.tab.selectionnerCartesJoueur(1);
            piocherEtPlacerNouvellesCartes(carteRemplacer);
        }
    }

    public void jouerHumainTempsTours(int difficulter) {
        demmarreTable();
        int tempsTours;
        int tempScore = this.score;
        if (difficulter == 1){
            tempsTours = 45;
        } else if (difficulter == 2){
            tempsTours = 30;
        }
        else {
            tempsTours = 15;
        }
        long secondes = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        long secondes2 = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        long tempsDuTours = secondes - secondes2;
        System.out.println("Le temps du tours est de " + tempsTours + " seconde");
        while (!partieEstTerminee() && tempsDuTours < tempsTours && tempScore <= this.score){
            secondes2 = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            tempScore = this.score;
            jouerTourHumainTempsTours();
            secondes = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            tempsDuTours = secondes - secondes2;
            if (tempsDuTours > tempsTours){
                this.score = tempScore;
                System.out.println("Vous avez depassé la limite de temps");
            }
        }
        System.out.println("Votre score final est de " + this.score);
    }
}

