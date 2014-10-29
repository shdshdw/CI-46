package com.TSP.Group46;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Maze {
	private int width;
	private int height;
	ArrayList<Tile> tiles;
	private int startX, startY, endX, endY;
	private String mazepath;
	private int totalProducts;
	
	public Maze(int width, int height, int startX, int startY, int endX, int endY, int totalProducts) {
		tiles = new ArrayList<Tile>();
		
		this.width = width;
		this.height = height;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.totalProducts = totalProducts;
	}
	
	public void addTile(int x, int y, boolean walkable, double pheromones, Product product) {
		Tile t = new Tile(x, y, walkable);
		t.setPheromones(pheromones);
		t.setProduct(product);
		tiles.add(t);
		
		this.mazepath = "";
	}
	
	public Maze(String coorpath, String mazepath, String productpath) {
		tiles = new ArrayList<Tile>();
		
		readFile(coorpath, mazepath, productpath);
		this.mazepath = mazepath;
	}
	
	/**
	 * Read the coordinates file, maze file and product file
	 * @param coorpath coordinate file path
	 * @param mazepath maze file path
	 * @param productpath product file path
	 */
	public void readFile(String coorpath, String mazepath, String productpath) {
		try {
			// Read Start And End Coordinates
			BufferedReader coorinput = new BufferedReader(new FileReader(coorpath));
			String start = coorinput.readLine();
			startX = Integer.parseInt(start.split("\\W")[0]);
			startY = Integer.parseInt(start.split("\\W")[2]);
			
			String end = coorinput.readLine();
			endX = Integer.parseInt(end.split("\\W")[0]);
			endY = Integer.parseInt(end.split("\\W")[2]);
			coorinput.close();
			
			// Read Width And Height
			BufferedReader mazeinput = new BufferedReader(new FileReader(mazepath));
			String line = mazeinput.readLine();
			String[] size = line.split(" ");
			width = Integer.parseInt(size[0]);
			height = Integer.parseInt(size[1]);
			
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
			
			mazeinput.close();
			
			// Read Products
			BufferedReader productinput = new BufferedReader(new FileReader(productpath));
			int count = Integer.parseInt(productinput.readLine().split(";")[0]);
			totalProducts = count;
			for(int x = 1; x <= count; x++) {
				String product = productinput.readLine();
				String productcoor = product.substring((x + ":").length());
				int productX = Integer.parseInt(productcoor.split("\\W")[1]);
				int productY = Integer.parseInt(productcoor.split("\\W")[3]);
				
				Product p = new Product(x);
				getTile(productX, productY).setProduct(p);
			}
			productinput.close();
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

	public int getCountProducts() {
		return totalProducts;
	}
}
