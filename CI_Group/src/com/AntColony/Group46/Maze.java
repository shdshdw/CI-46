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
			//System.out.println("StartX: " + startX + ", StartY: " + startY);
			
			String end = coorinput.readLine();
			endX = Integer.parseInt(end.split("\\W")[0]);
			endY = Integer.parseInt(end.split("\\W")[2]);
			//System.out.println("EndX: " + endX + ", EndY: " + endY);
			coorinput.close();
			
			// Read Width And Height
			BufferedReader mazeinput = new BufferedReader(new FileReader(mazepath));
			String line = mazeinput.readLine();
			String[] size = line.split(" ");
			width = Integer.parseInt(size[0]);
			height = Integer.parseInt(size[1]);
			//System.out.println("Maze Width: " + width + ", Maze Height: " + height);
			
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
			//System.out.println("Tile Size: " + tiles.size());
			
			mazeinput.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Tile getTile(int x, int y) {
		for(Tile t: tiles) {
			if(t.getX() == x && t.getY() == y) {
				return t;
			}
		}
		return null;
	}
	
	public Tile bestNeighbor(int x, int y) {
		ArrayList<Tile> posDir = new ArrayList<Tile>();
		if(getTile(x + 1, y) != null ) { posDir.add(getTile(x + 1, y)); }
		if(getTile(x - 1, y) != null) { posDir.add(getTile(x - 1, y)); }
		if(getTile(x, y + 1) != null) { posDir.add(getTile(x, y + 1)); }
		if(getTile(x, y - 1) != null ) { posDir.add(getTile(x, y - 1)); } 
		
		double highPer = posDir.get(0).getPheromones();
		Tile bestNeighbor = posDir.get(0);
		for(Tile t: posDir) {
			if(t.getPheromones() > highPer) {
				bestNeighbor = t;
				highPer = t.getPheromones();
			}
		}
		
		return bestNeighbor;
	}
	
	public boolean isWalkable(int x, int y) {
		if(x < 0 || y < 0 || y > height - 1|| x > width - 1) { return false; }
		return getTile(x, y).getWalkable();
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

	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
