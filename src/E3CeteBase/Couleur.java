package E3CeteBase;

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
    JAUNE("\033[38;5;11m"),
    BLEU("\u001B[34m"),
    C4_PINK_DEEP("\033[38;5;199m"),
    C5_SPRINGGREEN4("\033[38;5;29m"),
    C6_TURQUOISE4("\033[38;5;30m"),
    C7_DODGERBLUE1("\033[38;5;33m"),
    C8_GREEN3("\033[38;5;34m"),
    C9_FUCHSIA("\033[38;5;13m"),
    C10_DARKRED("\033[38;5;52m"),
    C11_GREY50("\033[38;5;244m"),
    C12_ORANGERED1("\033[38;5;202m"),
    C13_ORANGE3("\033[38;5;172m"),
    C14_CHARTREUSE1("\033[38;5;118m"),
    C15_HOTPINK("\033[38;5;205m"),
    C16_PURPLE4("\033[38;5;55m"),
    C17_WHITE("\033[38;5;15m"),
    C18_OLIVE("\033[38;5;3m"),
    C19_DARKSLATEGRAY1("\033[38;5;123m"),
    C20_SILVER("\033[38;5;7m");

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

    public static Couleur[] values(int range){
        Couleur[] all = Couleur.values();
        Couleur[] nouveau = new Couleur[range];
        for (int i = 0; i < nouveau.length; i++) {
            nouveau[i] = all[i];
        }
        return nouveau;
    }
}
