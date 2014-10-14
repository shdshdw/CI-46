package com.AntColony.Group46;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class Maze {
	private int width;
	private int height;
	ArrayList<Tile> tiles;
	private int startX, startY, endX, endY;
	
	public Maze(String coorpath, String mazepath) {
		tiles = new ArrayList<Tile>();
		
		readFile(coorpath, mazepath);
	}
	
	public void readFile(String coorpath, String mazepath) {
		try {
			// Read Start And End Coordinates
			BufferedReader coorinput = new BufferedReader(new FileReader(coorpath));
			String start = coorinput.readLine();
			startX = Integer.parseInt(start.split("\\W")[0]);
			startY = Integer.parseInt(start.split("\\W")[2]);
			System.out.println("StartX: " + startX + ", StartY: " + startY);
			
			String end = coorinput.readLine();
			endX = Integer.parseInt(end.split("\\W")[0]);
			endY = Integer.parseInt(end.split("\\W")[2]);
			System.out.println("EndX: " + endX + ", EndY: " + endY);
			coorinput.close();
			
			// Read Width And Height
			BufferedReader mazeinput = new BufferedReader(new FileReader(mazepath));
			String line = mazeinput.readLine();
			String[] size = line.split(" ");
			width = Integer.parseInt(size[0]);
			height = Integer.parseInt(size[1]);
			System.out.println("Maze Width: " + width + ", Maze Height: " + height);
			
			// Init tiles
			for(int y = 0; y < height; y++) {
				line = mazeinput.readLine();
				String[] values = line.split(" ");
				for(int x = 0; x < width; x++) {
					int value = Integer.parseInt(values[x]);
					if(value == 0) {
						tiles.add(new Tile(x, y, false));
					} else {
						tiles.add(new Tile(x, y, true));
					}
				}
			}
			System.out.println("Tile Size: " + tiles.size());
			
			mazeinput.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public int getStartX() {
		return startX;
	}
	
	public int getStartY() {
		return startY;
	}
	
	public int getEndX() {
		return endX;
	}
	
	public int getEndY() {
		return endY;
	}
}