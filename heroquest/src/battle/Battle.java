package battle;

/**
 * Class for instance two chacracter combat
 */

public class Battle {

    private static Battle instance;
    private Character charA;
    private Character charB;

    private Battle() {

    }

    public static Battle getInstance() {
        if (instance == null) {
            instance = new Battle();
        }
        return instance;
    }


}
