package com.TSP.Group46;

import java.util.ArrayList;

public class TSP {
	private int maxants = 100;
	private int maxiterations = 30;
	private double p = 0.6d;
	private double pheromonespath = 200d;
	
	private ArrayList<Ant> ants;
	private Maze mainMaze;
	
	private String coorfile = "hard coordinates.txt";
	private String mazefile = "hard maze.txt";
	private String productfile = "tsp product.txt";

	public TSP() {
		mainMaze = new Maze(coorfile, mazefile, productfile);
		
		for(int x = 0; x < maxiterations; x++) {
			int totalLengthPath = 0;
			ants = new ArrayList<Ant>();
			
			for(int a = 0; a < maxants; a++) {
				Maze subMaze = new Maze(mainMaze.getWidth(), mainMaze.getHeight(), mainMaze.getStartX(), mainMaze.getStartY(), mainMaze.getEndX(), mainMaze.getEndY());
				for(Tile t: mainMaze.getTiles()) {
					subMaze.addTile(t.getX(), t.getY(), t.getWalkable(), t.getPheromones());
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
			
			for(Tile t: mainMaze.getTiles()) {
				t.setPheromones(t.getPheromones() * (1d - p));
			}
			
			for(Ant a: ants) {
				try {
					a.getThread().join();
					int pathlength = a.getWalkedPath().size();
					totalLengthPath += pathlength;
					for(Tile t: a.getWalkedPath()) {
							mainMaze.getTile(t.getX(), t.getY()).setPheromones(mainMaze.getTile(t.getX(), t.getY()).getPheromones() + (pheromonespath / pathlength));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("Iteration nr. " + x);
			System.out.println("Gemiddelde lengte van paden: " + totalLengthPath / maxants);
		}
		
		mainMaze.getBestPath(mainMaze.getStartX(), mainMaze.getStartY());
		
		System.out.println("Finished!");
	}
	
	public static void main(String[] args) {
		new TSP();
	}
}
