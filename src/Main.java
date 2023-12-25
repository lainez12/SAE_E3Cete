public class Main {

    /**
     * Action : lance une partie de jeu "E3Cète"
     */
    public static void main(String[]args) {

        Carte nu = new Carte(Couleur.ROUGE,2,Figure.CARRE,Texture.VIDE);
        System.out.println(nu+"\n");
        System.out.println(nu.formeCarte());
    }

}
