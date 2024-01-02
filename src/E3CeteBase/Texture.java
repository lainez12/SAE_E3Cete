package E3CeteBase;

public enum Texture {

    /**
     * Représente la texture d'une Carte : pleine , à pois...
     */

    PLEIN("P"),
    HACHURE("H"),
    VIDE("V");

    private final String abreviation;

    Texture(String abreviation){
        this.abreviation = abreviation;
    }

    public String getAbreviation(){
        return this.abreviation;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
