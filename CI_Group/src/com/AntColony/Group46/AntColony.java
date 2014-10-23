package com.AntColony.Group46;

import java.util.ArrayList;


public class AntColony {
	private int maxants = 10;
	private int maxiterations = 2;
	private double p = 0.6d;
	private double pheromonespath = 2000d;
	
	private ArrayList<AntRenew> ants;
	private Maze mainMaze;
	
	private String coorfile = "hard coordinates.txt";
	private String mazefile = "hard maze.txt";
	
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
					System.out.println("YUP");
					totalLengthPath += a.getWalkedPath().size();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			for(AntRenew a: ants) {
				int pathlength = a.getWalkedPath().size();
				for(Tile t: a.getWalkedPath()) {
						mainMaze.getTile(t.getX(), t.getY()).setPheromones(mainMaze.getTile(t.getX(), t.getY()).getPheromones() + (pheromonespath / pathlength));
				}
			}
			
			
			System.out.println("Iteration nr. " + x);
			System.out.println("Gemiddelde lengte van paden: " + totalLengthPath / maxants);
		}
		
		printMaze(mainMaze);
		
		mainMaze.getBestPath(mainMaze.getStartX(), mainMaze.getStartY());
		
		System.out.println("Finished!");
	}
	
	private void printMaze(Maze maze) {
		for(int y = 0; y < maze.getHeight(); y++) {
			for(int x = 0; x < maze.getWidth(); x++) {
				System.out.print(maze.getTile(x, y).getPheromones());
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		new AntColony();
	}
}
