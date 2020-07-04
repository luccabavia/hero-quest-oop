import entity.Entity;
import entity.character.Character;
import entity.character.hero.HeroType;
import entity.character.hero.Hero;
import map.Map;
import map.MapMode;

import java.util.ArrayList;
import java.util.Random;

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

        int i = 0;
        while (!exit) {
            drawBoard();
            //readInput();
            movementPhase();
            i++;
            if (i > 20) {
                exit = true;
            }
        }
        System.out.println("Game terminated. Bye!");
    }

    // Decides whether the map is standard or random
    private void configure() {
        this.map = Map.getInstance();//MapMode.DEFAULT);
        this.map.setGameMode(MapMode.DEFAULT);
//        this.hero = choseHero();
    }

    private Hero selectHero(HeroType heroType) {


        return null;
    }

    private void drawBoard(){
        //Display.print(hero.getStatus());
        this.map.drawMap();

    }

    private void readInput(){
        hero.move();
    }

    private void movementPhase() {

        Random rand = new Random();

        ArrayList<Character> chars = new ArrayList<>();
        chars = this.map.getCharacter();

        for (Character c : chars) {
            c.move(1);//rand.nextInt(6) + 1);
        }

    }
}
