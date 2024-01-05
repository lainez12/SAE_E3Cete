package E3CeteExt;

import E3CeteBase.Carte;
import E3CeteBase.Coordonnees;
import E3CeteBase.Ut;

/**
 * La classe Table représente une table de jeu contenant des cartes.
 *
 * La table est représentée graphiquement par une matrice.
 * On peut donc avoir des tables de dimensions 3x3, 3x4, 4x4, 5x5, 10x15...
 * En mémoire, la Table est représentée par un simple tableau (à une dimension)
 * Quand elle est initialisée, la table est vide.
 *
 * Pour désigner une carte sur la table, on utilise des coordonées (i,j) ou i représenta la ligne et j la colonne.
 * Les lignes et colonnes sont numérotés à partir de 1.
 * Les cartes sont numérotées à partir de 1.
 *
 * Par exemple, sur une table 3x3, la carte en position (1,1) est la premiere carte du tableau, soit celle à l'indice 0.
 * La carte (2,1) => carte numéro 4 stockée à l'indice 3  dans le tableau représenatnt la table
 * La carte (3,3) => carte numéro 9 stockée à l'indice 8  dans le tableau représenatnt la table
 */
public class Table {

    private Carte[] table;
    private int hauteur = 0;
    private int larguer = 0;
    private int cartesSurTable;

    /**
     * Pre-requis : hauteur >=3, largeur >=3
     *
     * Action : Construit une table "vide" avec les dimensions précisées en paramètre.
     *
     * Exemple : hauteur : 3, largeur : 3 => construit une table 3x3 (pouvant donc accueillir 9 cartes).
     */

    public Table(int hauteur, int larguer){
        this.larguer = larguer;
        this.hauteur = hauteur;
        this.table = new Carte[this.getTaille()];
    }



    /**
     * Résullat : Le nombre de cartes que la table peut stocker.
     */

    public int getTaille() {
        return this.larguer*this.hauteur;
    }

    public boolean tableEstVide(){return this.cartesSurTable < 1; };//pour ext 1

    /**
     * Pre-requis : la table est pleine
     * Action : Affiche des cartes de la table sous forme de matrice
     * L'affichage des cartes doit respecter le format défini dans la classe Carte (chaque carte doit donc être colorée).
     * On ne donne volontairement pas d'exemple puisque celà depend du choix fait pour votre représentation de Carte
     */

    public String toString() {
        return Carte.afficherCartes(this.table, this.larguer);
    }

    /**
     * Résullat : Vrai la carte située aux coordonnées précisées en paramètre est une carte possible pour la table.
     */
    public boolean carteExiste(Coordonnees coordonnees) {
        boolean ligne = coordonnees.getLigne() < this.hauteur;
        boolean colonne = coordonnees.getColonne() < this.larguer;
        return ligne && colonne;
    }

    /**
     * Pre-requis :
     *  Il reste des cartes sur la table.
     *
     * Action : Fait sélectionner au joueur (par saisie de ses coordonnées) une carte valide (existante) de la table.
     * L'algorithme doit faire recommencer la saisie au joueur s'il ne saisit pas une carte valide.
     *
     * Résullat : Le numéro de carte sélectionné.
     *
     */

    public int faireSelectionneUneCarte() {
        String input;
        Coordonnees carteCoordonnes;
        do {
            System.out.println("Veuillez saisir le coordonnes de la carte à selectioner (example: 1,1)");
            input = Ut.saisirChaine();
            carteCoordonnes =  new Coordonnees(input);
        } while (!Coordonnees.formatEstValide(input) || !carteExiste(carteCoordonnes));

        return numCarte(carteCoordonnes);
    }

    //PR: Carte Exite dans la table; Action : retourne le nombre de carte dans le tableu de this
    public int numCarte(Coordonnees c){
        int numCarte = 0;
        if (c.getLigne() > 0){
            numCarte = (this.larguer * c.getLigne());
        } else {
            numCarte = c.getLigne();
        }
        numCarte += c.getColonne();
        return numCarte;
    }

    /**
     * Pre-requis : 1<=nbCartes <= nombre de Cartes de this
     * Action : Fait sélectionner nbCartes Cartes au joueur  sur la table en le faisant recommencer jusqu'à avoir une sélection valide.
     * Il ne doit pas y avoir de doublons dans les numéros de cartes sélectionnées.
     * Résullat : Un tableau contenant les numéros de cartes sélectionnées.
     */

    public int[] selectionnerCartesJoueur(int nbCartes) {
        int numCarte;
        int compteurCartes = 0;
        int[] cartesJouer = new int[nbCartes];
        do {
            numCarte = faireSelectionneUneCarte();
            if (doublons(numCarte,compteurCartes,cartesJouer)){
                System.out.println("Le num de carte est :" + (numCarte+1)); //delete
                cartesJouer[compteurCartes] = numCarte;
                compteurCartes++;
            } else {
                System.out.println("Error doublon, resseyez.");
            }
        } while (!(compteurCartes == nbCartes));
        this.cartesSurTable -= compteurCartes;
        return cartesJouer;
    }

    public boolean doublons(int numCarte, int compteur, int[] cartes){
        for (int i = 0; i < compteur; i++) {
            if (cartes[i] == numCarte){
                return false;
            }
        }
        return true;
    }

    //PR: cartes.length <= nbCartes possibles de stocker dans la table
    public void placeCartes(Carte[] cartes, int[] posCartes){
        for (int i = 0; i < cartes.length; i++){
            this.table[posCartes[i]] = cartes[i];
            this.cartesSurTable++;
        }
    }

    public void effaceCartes(int[] posCartes){
       posCartes = Ut.order(posCartes);
        for (int posCarte : posCartes) {
            this.effaceCarte(posCarte);
        }
        Carte[] cartes = new Carte[this.table.length];
        System.arraycopy(this.table, 0, cartes, 0, cartes.length);
        this.table = new Carte[cartes.length-posCartes.length];
        System.arraycopy(cartes, 0, this.table, 0, this.table.length);
    }

    public void effaceCarte(int x){
        for (int i = x; i < this.table.length-1; i++) {
            this.table[i] = this.table[i+1];
        }
    }

    /**
     * Action : Affiche les cartes de la table correspondantes aux numéros de cartes contenus dans selection
     * Exemple de format d'affichage : "Sélection : 2-O-H 3-O-H 2-C-H"
     * Les cartes doivent être affichées "colorées"
     */

    public void afficherSelection(int[] selection) {
        for (int i = 0; i < selection.length; i++) {
            System.out.print(this.table[selection[i]] + " ");
        }
    }

    public Carte getCarte(int numCarte){
        return this.table[numCarte];
    }

    public int getCartesSurTable() {
        return this.cartesSurTable;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLarguer() {
        return larguer;
    }

    public void setCartesSurTable(int cartesSurTable) {
        this.cartesSurTable = cartesSurTable;
    }
}
