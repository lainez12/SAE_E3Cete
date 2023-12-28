public enum Figure {

    /**
     * Représente la figure (forme) d'une Carte : ovale , triangle ...
     */

    /*J'ai utilisé cette page  https://cloford.com/resources/charcodes/utf-8_geometric.htm
    *                          https://altcodeunicode.com/alt-codes-geometric-shape-symbols/ */

    CARRE("■","▧","□"),
    LOSANGE("◆","⬖","⬦"),
    OVALE("⬮","◍", "⬯");
    private final String plein;
    private final String hachure;
    private final String vide;

    Figure(String plein, String hachure, String vide){
        this.plein = plein;
        this.hachure = hachure;
        this.vide = vide;
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

}
