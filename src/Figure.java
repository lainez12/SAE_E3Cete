public enum Figure {

    /**
     * Représente la figure (forme) d'une Carte : ovale , triangle ...
     */

    /*J'ai utilisé cette page  https://cloford.com/resources/charcodes/utf-8_geometric.htm*/

    CARRE("■"),
    TRIANGLE("▲"),
    RECTANGLE("▮");
    private final String caract;

    Figure(String chara){
        this.caract = chara;
    }

    public String toString(){
        return caract;
    }

}
