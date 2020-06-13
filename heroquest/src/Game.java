import entity.Entity;
import entity.character.hero.Hero;
import entity.character.hero.Dummy;
import map.Map;

public class Game {

    private boolean exit;
    private Hero hero;
    private Map map;

    public Game() {
        
        configure();

    }

    public void start() {
        exit = false;
        System.out.println("Game started!");

        while (!exit) {
            drawBoard();
            //readInput();
            updateBoard();
            exit=true;
        }
        System.out.println("Game terminated. Bye!");
    }

    // Decides whether the map is standard or random
    private void configure() {
        this.map = Map.getInstance("");

        this.hero = choseHero();
    }

    private Hero choseHero() {
        return new Dummy(map, 0, 0, 3);
    }

    private void drawBoard(){
        //Display.print(hero.getStatus());
        this.map.drawMap();

    }

    private void readInput(){
        hero.move();
    }

    private void updateBoard(){}
}
