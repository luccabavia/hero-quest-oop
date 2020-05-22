public class Game {

    private boolean exit;

    public Game() {
        
        configure();

    }

    public void start() {
        exit = false;
        System.out.println("Game started!");

        while (!exit) {
            drawBoard();
            readInput();
            updateBoard();
        }
        System.out.println("Game terminated. Bye!");
    }

    // Decides whether the map is standard or random
    public void configure(){}

    private void drawBoard(){}

    private void readInput(){}

    private void updateBoard(){}
}
