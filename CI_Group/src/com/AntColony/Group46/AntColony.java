package com.AntColony.Group46;

import java.util.ArrayList;


public class AntColony {
	private int maxants = 1;
	private int maxiterations = 2;
	
	private ArrayList<Ant> ants;
	private Maze mainMaze;
	
	private String coorfile = "easy coordinates.txt";
	private String mazefile = "easy maze.txt";
	
	public AntColony() {
		mainMaze = new Maze(coorfile, mazefile);
		
		for(int x = 0; x < maxiterations; x++) {
			ants = new ArrayList<Ant>();
			
			for(int a = 0; a < maxants; a++) {
				Maze subMaze = new Maze(coorfile, mazefile);
				for(Tile t: mainMaze.getTiles()) {
					subMaze.getTile(t.getX(), t.getY()).setPheromones(t.getPheromones());
				}
				boolean first = false;
				if(x == 0) { first = true; }
				Ant ant = new Ant(first, subMaze.getStartX(), subMaze.getStartY(), subMaze);
				ant.start();
				ants.add(ant);
			}
			
			for(Ant a: ants) {
				try {
					a.getThread().join();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			for(Ant a: ants) {
				ArrayList<Tile> done = new ArrayList<Tile>();
				int pathlength = a.getWalkedPath().size();
				for(Tile t: a.getWalkedPath()) {
					if(!done.contains(t)) {
						mainMaze.getTile(t.getX(), t.getY()).setPheromones(mainMaze.getTile(t.getX(), t.getY()).getPheromones() + (double)(100d / pathlength));
						done.add(t);
					}
				}
			}
			
			for(int yy = 0; yy < mainMaze.getHeight(); yy++) {
				for(int xx = 0; xx < mainMaze.getWidth(); xx++) {
					System.out.print((int)mainMaze.getTile(xx, yy).getPheromones() + " ");
				}
				System.out.println();
			}
			
			System.out.println("Finished!");
		}
	}
	
	public static void main(String[] args) {
		new AntColony();
	}
}
