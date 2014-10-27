package com.AntColony.Group46;

import java.io.PrintWriter;
import java.util.ArrayList;


public class AntColony {
	private int maxants = 100;
	private int maxiterations = 30;
	private double p = 0.6d;
	private double pheromonespath = 200d;
	
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
				Maze subMaze = new Maze(mainMaze.getWidth(), mainMaze.getHeight(), mainMaze.getStartX(), mainMaze.getStartY(), mainMaze.getEndX(), mainMaze.getEndY());
				for(Tile t: mainMaze.getTiles()) {
					subMaze.addTile(t.getX(), t.getY(), t.getWalkable(), t.getPheromones());
				}
				
				AntRenew ant;
				if(x == 0) { 
					ant = new AntRenew(true, subMaze.getStartX(), subMaze.getStartY(), subMaze);
				} else {
					ant = new AntRenew(false, subMaze.getStartX(), subMaze.getStartY(), subMaze);
				}
				
				ant.start();
				ants.add(ant);
			}
			
			for(Tile t: mainMaze.getTiles()) {
				t.setPheromones(t.getPheromones() * (1d - p));
			}
			
			for(AntRenew a: ants) {
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
	
	private void printFirstMaze(AntRenew antRenew) {
		antRenew.start(); 
		try {
			antRenew.getThread().join();
			System.out.println("SUP");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ArrayList<Tile> walkedPath = antRenew.getWalkedPath();
		PrintWriter writer;
		try {
			writer = new PrintWriter("hard bestpath.txt", "UTF-8");
			writer.println(walkedPath.size() - 1 + ";");
			writer.println(1 + ", " + 1 + ";");
			Tile previousTile = walkedPath.get(0);
			walkedPath.remove(0);
			for(Tile i : walkedPath) {
				writer.print(Calculations.getDirection(previousTile, i) + ";");
				previousTile = i;
			}
			writer.close();		
		} catch (Exception e) {
			e.printStackTrace();
		}
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
