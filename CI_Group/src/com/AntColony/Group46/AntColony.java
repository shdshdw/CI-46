package com.AntColony.Group46;

import java.util.ArrayList;


public class AntColony {
	private int maxants = 4;
	private int maxiterations = 100;
	private double p = 0.1;
	
	private ArrayList<AntRenew> ants;
	private Maze mainMaze;
	
	private String coorfile = "easy coordinates.txt";
	private String mazefile = "easy maze.txt";
	
	public AntColony() {
		mainMaze = new Maze(coorfile, mazefile);
		
		for(int x = 0; x < maxiterations; x++) {
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
			
			for(AntRenew a: ants) {
				try {
					a.getThread().join();
					System.out.println(a.getWalkedPath().size());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			for(AntRenew a: ants) {
				ArrayList<Tile> done = new ArrayList<Tile>();
				int pathlength = a.getWalkedPath().size();
				for(Tile t: a.getWalkedPath()) {
					if(!done.contains(t)) {
						mainMaze.getTile(t.getX(), t.getY()).setPheromones(mainMaze.getTile(t.getX(), t.getY()).getPheromones() + (1000d / pathlength));
						done.add(t);
					}
				}
			}
			
			for(Tile t: mainMaze.getTiles()) {
				t.setPheromones(t.getPheromones() * (1d - p));
			}
			
		}
		
		for(int y = 0; y < mainMaze.getHeight(); y++) {
			for(int x = 0; x < mainMaze.getWidth(); x++) {
				System.out.print(mainMaze.getTile(x, y).getPheromones() + " ");
			}
			System.out.println();
		}
		
		echoDirections();
		
		System.out.println("Finished!");
	}
	
	private void echoDirections() {
		int x = mainMaze.getStartX();
		int y = mainMaze.getStartY();
		int previousMove = -1;
		while(x != mainMaze.getEndX() || y != mainMaze.getEndY()) {
			int direction = -1;
			Tile bestNeighbor = mainMaze.bestNeighbor(x, y);
			if(bestNeighbor.getX() > x) {
				direction = 0;
			} else if(bestNeighbor.getX() < x) {
				direction = 2;
			} else if(bestNeighbor.getY() > y) {
				direction = 3;
			} else {
				direction = 1;
			}
			System.out.print(direction + ", ");
			
			x = bestNeighbor.getX();
			y = bestNeighbor.getY();
		}
	}
	
	public static void main(String[] args) {
		new AntColony();
	}
}
