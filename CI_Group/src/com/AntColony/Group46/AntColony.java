package com.AntColony.Group46;

import java.util.ArrayList;


public class AntColony {
	private int maxants = 100;
	private int maxiterations = 30;
	private double p = 0.6d;
	private double pheromonespath = 200d;
	
	private ArrayList<AntRenew> ants;
	private Maze mainMaze;
	
	private String coorfile = "medium coordinates.txt";
	private String mazefile = "medium maze.txt";
	
	public AntColony() {
		mainMaze = new Maze(coorfile, mazefile);
		
		for(int x = 0; x < maxiterations; x++) {
			int totalLengthPath = 0;
			ants = new ArrayList<AntRenew>();
			
			for(int a = 0; a < maxants; a++) {
				Maze subMaze = new Maze(coorfile, mazefile);
				for(Tile t: mainMaze.getTiles()) {
					subMaze.getTile(t.getX(), t.getY()).setPheromones(t.getPheromones());
				}
				boolean first = false;
				if(x == 0) { first = true; }
				AntRenew ant = new AntRenew(first, subMaze.getStartX(), subMaze.getStartY(), subMaze);
				ant.start();
				ants.add(ant);
			}
			
			for(Tile t: mainMaze.getTiles()) {
				t.setPheromones(t.getPheromones() * (1d - p));
			}
			
			for(AntRenew a: ants) {
				try {
					a.getThread().join();
					totalLengthPath += a.getWalkedPath().size();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			for(AntRenew a: ants) {
				ArrayList<Tile> done = new ArrayList<Tile>();
				int pathlength = a.getWalkedPath().size();
				for(Tile t: a.getWalkedPath()) {
					if(!done.contains(t)) {
						mainMaze.getTile(t.getX(), t.getY()).setPheromones(mainMaze.getTile(t.getX(), t.getY()).getPheromones() + (pheromonespath / pathlength));
						done.add(t);
					}
				}
			}
			
			
			System.out.println("Iteration nr. " + x);
			System.out.println("Gemiddelde lengte van paden: " + totalLengthPath / maxants);
		}
		
		mainMaze.getBestPath(mainMaze.getStartX(), mainMaze.getStartY());
		
		System.out.println("Finished!");
	}
	
	public static void main(String[] args) {
		new AntColony();
	}
}
