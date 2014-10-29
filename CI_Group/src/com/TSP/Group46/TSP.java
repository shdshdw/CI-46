package com.TSP.Group46;

import java.io.PrintWriter;
import java.util.ArrayList;

public class TSP {
	private int maxants = 40;
	private int maxiterations = 10;
	private double p = 0.6d;
	private double pheromonespath = 50d;
	
	private ArrayList<Ant> ants;
	private Maze mainMaze;
	
	private String coorfile = "hard coordinates.txt";
	private String mazefile = "hard maze.txt";
	private String productfile = "tsp products.txt";

	public TSP() {
		mainMaze = new Maze(coorfile, mazefile, productfile);
		
		for(int x = 0; x < maxiterations; x++) {
			ants = new ArrayList<Ant>();
			
			// Create new ants for colony and let them begin to walk.
			for(int a = 0; a < maxants; a++) {
				Maze subMaze = new Maze(mainMaze.getWidth(), mainMaze.getHeight(), mainMaze.getStartX(), mainMaze.getStartY(), mainMaze.getEndX(), mainMaze.getEndY(), mainMaze.getCountProducts());
				for(Tile t: mainMaze.getTiles()) {
					subMaze.addTile(t.getX(), t.getY(), t.getWalkable(), t.getPheromones(), t.getProduct());
				}
				
				Ant ant;
				if(x == 0) { 
					ant = new Ant(true, subMaze.getStartX(), subMaze.getStartY(), subMaze);
				} else {
					ant = new Ant(false, subMaze.getStartX(), subMaze.getStartY(), subMaze);
				}
				
				ant.start();
				ants.add(ant);
			}
			
			// Update the main maze to the evaporation factor
			for(Tile t: mainMaze.getTiles()) {
				t.setPheromones(t.getPheromones() * (1d - p));
			}
			
			// Update the main maze to the pheromones from the Ants
			for(Ant a: ants) {
				try {
					a.getThread().join();
					for(ArrayList<Tile> paths: a.getProductPaths()) {
						int productpathlength = paths.size();
						for(Tile t: paths) {
							mainMaze.getTile(t.getX(), t.getY()).setPheromones(mainMaze.getTile(t.getX(), t.getY()).getPheromones() + (pheromonespath / productpathlength));							
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("Iteration nr. " + x);
		}
		
		// Run the last Ant for the best path.
		Ant finishedAnt = new Ant(false, mainMaze.getStartX(), mainMaze.getStartY(), mainMaze);
		finishedAnt.start();
		try {
			finishedAnt.getThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		printFile(finishedAnt.getProductPaths());
		
		System.out.println("Finished!");
	}
	
	/**
	 * Print the best path and take products
	 * @param productPaths The best product paths
	 */
	private void printFile(ArrayList<ArrayList<Tile>> productPaths) {
		int totalSteps = 0;
		ArrayList<String> sentences = new ArrayList<String>();
		for(ArrayList<Tile> paths: productPaths) {
			Tile previousTile = null;
			for(Tile t: paths) {
				Tile currentTile = t;
				if(currentTile.isProduct()) {
					sentences.add("take product #" + currentTile.getProduct().getNumber() + ";");
					currentTile.setProduct(null);
					totalSteps++;
				}
				if(previousTile != null) {
					sentences.add(Calculations.getDirection(previousTile, currentTile) + ";");
					totalSteps++;
				}
				previousTile = currentTile;
			}
		}
		writeBestPath(sentences, totalSteps);
	}
	
	// PrintWrite the best path for the products
	private void writeBestPath(ArrayList<String> directions, int totalSteps) {
		PrintWriter writer;
		try {
			writer = new PrintWriter("product bestpath.txt", "UTF-8");
			writer.println(totalSteps + ";");
			writer.println(mainMaze.getStartX() + ", " + mainMaze.getStartY() + ";");
			for(String i : directions) {
				writer.println(i);
			}
			writer.close();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new TSP();
	}
}
