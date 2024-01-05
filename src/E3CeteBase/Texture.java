package E3CeteBase;

public enum Texture {

    /**
     * Représente la texture d'une Carte : pleine , à pois...
     */

    PLEIN("P"),
    HACHURE("H"),
    VIDE("V"),
    T4_SATIN("S"),
    T5_LIN("L"),
    T6_SOIE("O"),
    T7_DENIM("D"),
    T8_CUIR("U"),
    T9_TRICOT("T"),
    T10_ORGANZA("R"),
    T11_TWEED("W"),
    T12_BROCAT("B"),
    T13_FEUTRE("F"),
    T14_MOHAIR("M"),
    T15_CHENILLE("C"),
    T16_FLANELLE("N"),
    T17_JACQUARD("J"),
    T18_DENTELLE("E"),
    T19_JERSEY("Y"),
    T20_SEERSUCKER("K");

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

    public static Texture[] values(int range){
        Texture[] all = Texture.values();
        Texture[] nouveau = new Texture[range];
        for (int i = 0; i < nouveau.length; i++) {
            nouveau[i] = all[i];
        }
        return nouveau;
    }
}
