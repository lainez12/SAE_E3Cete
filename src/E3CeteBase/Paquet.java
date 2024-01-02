package E3CeteBase;

import java.util.Random;

/**
 * La classe Paquet représente un paquet de cartes.
 * Les cartes sont stockées dans un tableau fixe et un indice (entier) permet de connaître le nombre de cartes
 * restantes (non piochées) dans le paquet. Quand on pioche, cet indice diminue.
 * Dans les traitements, on considère alors seulement les cartes se trouvant entre 0 et cet indice (exclus).
 * Par conséquent, on ne supprime pas vraiment les cartes piochées, on les ignore juste.
 * On a donc besoin de connaître :
 * - Le tableau stockant les cartes.
 * - Le nombre de cartes restantes dans le paquet.
 */


public class Paquet {

    private Carte[] tabCartes;
    private int indiceCarteRestante;

    public int getIndiceCarteRestante() {
        return indiceCarteRestante;
    }

    /**
     * Pre-requis : figures.length > 0, couleurs.length > 0, textures.length > 0, nbFiguresMax > 0
     *
     * Action : Construit un paquet de cartes mélangé contenant toutes les cartes incluant 1 à nbFiguresMax figures
     * qu'il est possibles de créer en combinant les différentes figures, couleurs et textures précisées en paramètre.
     * Bien sûr, il n'y a pas de doublons.
     *
     * Exemple :
     *  - couleurs = [Rouge, Jaune]
     *  - nbFiguresMax = 2
     *  - figures = [A, B]
     *  - textures = [S, T]
     *  Génère un paquet (mélangé) avec toutes les combinaisons de cartes possibles pour ces caractéristiques : 1-A-S (rouge), 1-A-T (rouge), etc...
     */

    public Paquet(Couleur[] couleurs, int nbFiguresMax, Figure[] figures, Texture[] textures) {
        this.tabCartes = new Carte[getNombreCartesAGenerer(couleurs, nbFiguresMax, figures, textures)];
        int comptCarte = 0;
        for (int i = 0; i < couleurs.length; i++) {
            Couleur tempCoul = couleurs[i];
            for (int j = 1; j <= nbFiguresMax; j++) {
                int tempNbFigure = j;
                for (int k = 0; k < figures.length; k++) {
                    Figure tempFigures = figures[k];
                    for (int l = 0; l < textures.length ; l++) {
                        Texture tempTextures = textures[l];
                        Carte tempCarte = new Carte(tempCoul,tempNbFigure,tempFigures,tempTextures);
                        this.tabCartes[comptCarte] = tempCarte;
                        comptCarte ++;
                    }
                }
            }
        }
        this.indiceCarteRestante = comptCarte;  //peut etre mieux
        this.melanger();
    }

    /**
     * Action : Construit un paquet par recopie en copiant les données du paquet passé en paramètre.
     */

    public Paquet(Paquet paquet) {
        this.tabCartes = new Carte[paquet.tabCartes.length];
        for (int i = 0; i < paquet.tabCartes.length; i++) {
            this.tabCartes[i] = paquet.tabCartes[i];
        }
        this.indiceCarteRestante = paquet.indiceCarteRestante;
    }


    /**
     * Pre-requis : figures.length > 0, couleurs.length > 0, textures.length > 0, nbFiguresMax > 0
     *
     * Resultat : Le nombre de cartes uniques contenant entre 1 et nbFiguresMax figures qu'il est possible de générer en
     * combinant les différentes figures, couleurs et textures précisées en paramètre.
     */

    public static int getNombreCartesAGenerer(Couleur[] couleurs, int nbFiguresMax, Figure[] figures, Texture[] textures) {
        return couleurs.length * nbFiguresMax * figures.length * textures.length;
    }

    /**
     * Action : Mélange aléatoirement les cartes restantes dans le paquet.
     * Attention, on rappelle que le paquet peut aussi contenir des cartes déjà piochées qu'il faut ignorer.
     */

    public void melanger() {
        Random random = new Random();
        int compt =0;
        int tempi1;
        int tempi2;
        Carte carte1;
        Carte carte2;
        do {
            tempi1 = random.nextInt(this.indiceCarteRestante);
            tempi2 = random.nextInt(this.indiceCarteRestante);
            carte1 = this.tabCartes[tempi1];
            carte2 = this.tabCartes[tempi2];
            this.tabCartes[tempi1] = carte2;
            this.tabCartes[tempi2] = carte1;
            compt ++;
        }while (compt < 200);
    }

    /**
     * Action : Calcule et renvoie un paquet trié à partir du paquet courant (this) selon la méthode du tri selection.
     * Le tri est effectué à partir des données du paquet courant (this) mais celui-ci ne doit pas être modifié !
     * Une nouvelle instance du paquet est traitée et renvoyée.
     * On rappelle que le paquet peut aussi contenir des cartes déjà piochées  qu'il faut ignorer (voir partie 2 de la SAE).
     * Le tri doit fonctionner que le Paquet soit plein ou non.
     * https://www.youtube.com/watch?v=Ns4TPTC8whw&t=2s vidéo explicative
     */

    public Paquet trierSelection() {
        Paquet tempPaquet = new Paquet(this);
        Carte temp;
        for (int i = 0; i < this.indiceCarteRestante - 1; i++) {
            int minindice = i;
            for (int j = i + 1; j < this.indiceCarteRestante; j++) {
                if (tempPaquet.tabCartes[minindice].compareTo(tempPaquet.tabCartes[j]) == 1){
                    minindice = j;
                }
            }
            temp = tempPaquet.tabCartes[i];
            tempPaquet.tabCartes[i] = tempPaquet.tabCartes[minindice];
            tempPaquet.tabCartes[minindice] = temp;
        }
        return tempPaquet;
    }

    /**
     * Action : Calcule et renvoie un paquet trié à partir du paquet courant (this) selon la méthode du tri bulles.
     * Le tri est effectué à partir des données du paquet courant (this) mais celui-ci ne doit pas être modifié !
     * Une nouvelle instance du paquet est traitée et renvoyée.
     * On rappelle que le paquet peut aussi contenir des cartes déjà piochées  qu'il faut ignorer (voir partie 2 de la SAE).
     * Le tri doit fonctionner que le Paquet soit plein ou non.
     * https://www.youtube.com/watch?v=lyZQPjUT5B4&embeds_referring_euri=https%3A%2F%2Fwww.developpez.com%2F&source_ve_path=Mjg2NjY&feature=emb_logo
     * vidéo explicative
     */

    public Paquet trierBulles() {
        Paquet tempPaquet = new Paquet(this);
        Carte temp;
        for (int i = 0; i < this.indiceCarteRestante; i++){
            for (int j = 0; j < this.indiceCarteRestante - i - 1 ; j++) {
                if (tempPaquet.tabCartes[j].compareTo(tempPaquet.tabCartes[j + 1]) == 1){
                    temp = tempPaquet.tabCartes[j];
                    tempPaquet.tabCartes[j] = tempPaquet.tabCartes[j + 1];
                    tempPaquet.tabCartes[j + 1] = temp;
                }
            }
        }
        return tempPaquet;
    }

    /**
     * Action : Calcule et renvoie un paquet trié à partir du paquet courant (this) selon la méthode du tri insertion.
     * Le tri est effectué à partir des données du paquet courant (this) mais celui-ci ne doit pas être modifié !
     * Une nouvelle instance du paquet est traitée et renvoyée.
     * On rappelle que le paquet peut aussi contenir des cartes déjà piochées  qu'il faut ignorer (voir partie 2 de la SAE).
     * Le tri doit fonctionner que le Paquet soit plein ou non.
     * https://www.youtube.com/watch?v=ROalU379l3U&t=1s : vidéo explicative
     */

    public Paquet trierInsertion() {
        Paquet tempPaquet = new Paquet(this);
        Carte temp;
        for (int i = 1; i < this.indiceCarteRestante; i++) {
            temp = tempPaquet.tabCartes[i];
            int j;
            for (j = i; j > 0; j--) {
                if (tempPaquet.tabCartes[j - 1].compareTo(temp) == 1) {
                    tempPaquet.tabCartes[j] = tempPaquet.tabCartes[j - 1];
                } else {
                    break;
                }
            }
            tempPaquet.tabCartes[j] = temp;
        }
        return tempPaquet;
    }

    /**
     * Action : Permet de tester les différents tris.
     * On doit s'assurer que chaque tri permette effectivement de trier un paquet mélangé.
     * Des messages d'informations devront être affichés.
     * La méthode est "static" et ne s'effectue donc pas sur la paquet courant "this".
     */
    public static void testTris() {
        Paquet paquetMelange = new Paquet(Couleur.values(),1, Figure.values(), Texture.values());
        System.out.println("Paquet initial :");
        System.out.println(paquetMelange.toString());

        paquetMelange.melanger();
        System.out.println("Paquet mélangé :");
        System.out.println(paquetMelange.toString());

        Paquet paquetTrieBulles = new Paquet(paquetMelange);
        System.out.println("Paquet bientot trié par bulles :");
        System.out.println(paquetTrieBulles.toString());
        System.out.println("Le paquet est trié (bulles) : ");
        System.out.println(paquetTrieBulles.trierBulles().toString());

        Paquet paquetTrieInsertion = new Paquet(paquetMelange);
        System.out.println("Paquet bientot trié par insertion :");
        System.out.println(paquetTrieInsertion.toString());
        System.out.println("Le paquet est trié (insertion) : ");
        System.out.println(paquetTrieInsertion.trierInsertion().toString());

        Paquet paquetTrieSelection = new Paquet(paquetMelange);
        System.out.println("Paquet bientot trié par sélection :");
        System.out.println(paquetTrieSelection.toString());
        System.out.println("Le paquet est trié (sélection) : ");
        System.out.println(paquetTrieSelection.trierSelection().toString());
    }


    /**
     * Pre-requis : 0 < nbCartes <= nombre de cartes restantes dans le paquet.
     *
     * Action : Pioche nbCartes Cartes au dessus du Paquet this (et met à jour son état).
     *
     * Résultat : Un tableau contenant les nbCartes Cartes piochees dans le Paquet.
     *
     * Exemple :
     * Contenu paquet : [A,B,C,D,E,F,G]
     * Nombre de cartes restantes : 5. On considère donc seulement les cartes de 0 à 4.
     *
     * piocher(3)
     * Contenu paquet : [A,B,C,D,E,F,G]
     * Nombre de cartes restantes : 2
     * Renvoie [E,D,C]
     */

    public Carte[] piocher(int nbCartes) {
        Carte[] tempTabCarte = new Carte[nbCartes];
        if (peutPicoher(nbCartes)){
            for (int i = 0; i < nbCartes; i++) {
                tempTabCarte[i] = this.tabCartes[this.indiceCarteRestante - (i+1)];
            }
            this.indiceCarteRestante -= nbCartes;
        }
        return tempTabCarte;
    }

    /**
     * Résultat : Vrai s'il reste assez de cartes dans le paquet pour piocher nbCartes.
     */

    public boolean peutPicoher(int nbCartes) {
        return this.indiceCarteRestante >= nbCartes;
    }

    /**
     * Résultat : Vrai s'il ne reste plus aucune cartes dans le paquet.
     */

    public boolean estVide() {
        if (this.indiceCarteRestante < 1){
            return true;
        }
        return false;
    }

    /**
     * Résultat : Une chaîne de caractères représentant le paquet sous la forme d'un tableau
     * [X, Y, Z...] représentant les cartes restantes dans le paquet.
     *
     * Exemple :
     * Contenu paquet : 1-O-P (rouge), 2-C-V (jaune), 3-L-P (jaune), 3-L-P (rouge), 1-L-V (bleu)
     * Nombre de cartes restantes : 3
     * Retourne [1-O-P, 2-C-V, 3-L-P] (et chaque représentation d'une carte est coloré selon la couleur de la carte...)
     */

    @Override
    public String toString() {
        StringBuilder rep = new StringBuilder("[");
        for (int i = 0; i < this.indiceCarteRestante; i++) {
            rep.append(this.tabCartes[i] + " ");
        }
        rep.append(Couleur.resetCouleur() + "]");
        return rep.toString();
    }

    public void setIndiceCarteRestante() {
        this.indiceCarteRestante = this.tabCartes.length;
    }
}