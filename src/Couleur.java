public enum Couleur {

    /**
     * Représente la couleur d'une Carte : jaune, rouge ...
     * En plus de donner une liste énumérative des couleurs possibles,
     * cette enumération doit permettre à la méthode toString d'une Carte de réaliser un affichage en couleur.
     */

    /*
    * https://gist.github.com/JBlond/2fea43a3049b38287e5e9cefc87b2124
    * https://en.wikipedia.org/wiki/ANSI_escape_code#SGR_(Select_Graphic_Rendition)_parameters:~:text=bright%20background%20color-,Colors,-%5Bedit%5D
    * https://stackoverflow.com/questions/4842424/list-of-ansi-color-escape-sequences
    * https://colors.sh/
    * */
    ROUGE("\u001B[31m"),
    VERT("\u001B[32m"),
    BLEU("\u001B[34m");

    private final String code;

    Couleur(String code) {
        this.code = code;
    }

    public static String resetCouleur() {
        return "\u001B[0m";
    }
    public static String bold(){return "\033[1m";}
    public static String souligne(){return "\033[4m";}

    public String toString(){
        return code;
    }
}
