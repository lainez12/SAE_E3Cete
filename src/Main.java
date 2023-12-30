public class Main {

    /**
     * Action : lance une partie de jeu "E3Cète"
     */
    public static void main(String[]args) {
        Jeu parti = new Jeu();
        parti.jouer();

        /*Carte carte = new Carte(Couleur.JAUNE,3,Figure.OVALE,Texture.VIDE);
        Carte carte2 = new Carte(Couleur.ROUGE,2,Figure.OVALE,Texture.PLEIN);
        Carte carte3 = new Carte(Couleur.BLEU,1,Figure.CARRE,Texture.HACHURE);
        System.out.print(carte + " ");
        System.out.print(carte2);
        System.out.println(" " + carte3);
        System.out.println(carte.formeCarte());
        System.out.println(carte2.formeCarte());
        System.out.println(carte3.formeCarte());*/
    }

}
