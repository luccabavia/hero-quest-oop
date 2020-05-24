package map;

import character.hero.Hero;
import java.util.Arrays;
import java.util.Scanner;

public class Map {
	private static String readMap[] = new String[20];
	private static char map[][] = new char[20][40];
	//private static char map_comp[][] = new char[20][20];
	
	//Refazer hasItem
	public static boolean hasItem(int[] i) {
			return false;
	}
	
	public static void importMap() {
		Scanner keyboard = new Scanner(System.in);
		int contador = 0;
		boolean running = true ;
		
		//Read of Map
		System.out.println(" Enter Map when finish press q to finish ");
		while ( running ) {
			String command = keyboard . nextLine ();
			if (command.compareTo ("q") == 0) {
				running = false ;
			}else {
				if (contador < 20)
					readMap[contador++] = command;
			}
		}
		//Retira os espaços de mapa
		for(contador = 0; contador < 20; contador++)
			readMap[contador] = readMap[contador].replace("  ", "");
		
		//Transforma em uma matriz de caracteres
		for(int i = 0; i < 20; i++)
			map[i] = readMap[i].toCharArray();
		
	}
	//Fazer função
		public static void updateMap() {
			int position[] = Hero.returnPosition();
			map[position[0]][(2*position[1])] = 'H';
			map[position[0]][(2*position[1])+1] = 'H';
			map[position[2]][(2*position[3])] = '_';
			map[position[2]][(2*position[3])+1] = '_';
		}
	
	//Fazer função
	public static void drawMap() {
		System.out.println();
		System.out.println();
		int i, j;
		for(i = 0; i < 20; i++) {
			for(j = 0; j < 38; j++) {
				System.out.print(map[i][j++]);
				System.out.print(map[i][j]+"  ");
			}
			System.out.print(map[i][j++]);
			System.out.println(map[i][j]);
		}
	}
	
	//Refazer isAvaiable
	public static boolean isAvailable(int X, int Y) {
		if (X < 0 || Y < 0 || Y == 20 || X == 20)
			return false;
		if(map[X][(2*Y)+1] == '_')
			return true;
		return false;
	}
}
