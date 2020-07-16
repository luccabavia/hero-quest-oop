import exceptions.UnknownItemException;

/**
 * Class which initialize game  
 */
public class Runner {

    public static void main(String[] args) throws UnknownItemException {

        Game g = new Game();
        g.startGameLoop();
    }
}
