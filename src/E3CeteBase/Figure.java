package E3CeteBase;

public enum Figure {

    /**
     * Représente la figure (forme) d'une Carte : ovale , triangle ...
     */

    /*J'ai utilisé cette page  https://cloford.com/resources/charcodes/utf-8_geometric.htm
    *                          https://altcodeunicode.com/alt-codes-geometric-shape-symbols/ */

    CARRE("■","■","▧","□"),
    LOSANGE("◆","◆","⬖","⬦"),
    OVALE("⬮","⬮","◍", "⬯"),
    F4("▢"),
    F5("▣"),
    F6("▤"),
    F7("▥"),
    F8("▦"),
    F9("▬"),
    F10("▮"),
    F11("▶"),
    F12("▲"),
    F13("▼"),
    F14("◀"),
    F15("◉"),
    F16("◖"),
    F17("◢"),
    F18("◣"),
    F19("❒"),
    F20("⬟");
    private final String defaut;
    private final String plein;
    private final String hachure;
    private final String vide;

    Figure(String defaut, String plein, String hachure, String vide){
        this.defaut = defaut;
        this.plein = plein;
        this.hachure = hachure;
        this.vide = vide;
    }

    Figure(String defaut){
        this.defaut = defaut;
        this.plein = "";
        this.hachure = "";
        this.vide = "";
    }

    public String toString(String texture){
        if (texture == "PLEIN"){
            return plein;
        } else if (texture == "HACHURE"){
            return hachure;
        } else {
            return vide;
        }
    }


    public String toString(){
        return defaut;
    }

    public static Figure[] values(int range){
        Figure[] all = Figure.values();
        Figure[] nouveau = new Figure[range];
        for (int i = 0; i < nouveau.length; i++) {
            nouveau[i] = all[i];
        }
        return nouveau;
    }

}
