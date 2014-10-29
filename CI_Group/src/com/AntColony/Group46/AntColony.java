package com.AntColony.Group46;

import java.io.PrintWriter;
import java.util.ArrayList;


public class AntColony {
	private int maxants = 200;
	private int maxiterations = 50;
	private double p = 0.6d;
	private double pheromonespath = 50d;
	
	private ArrayList<AntRenew> ants;
	private Maze mainMaze;
	
	private String coorfile = "medium coordinates.txt";
	private String mazefile = "medium maze.txt";
	
	public AntColony() {
		mainMaze = new Maze(coorfile, mazefile);
		
		for(int x = 0; x < maxiterations; x++) {
			int totalLengthPath = 0;
			ants = new ArrayList<AntRenew>();
			
			// Create new Ants, add them to the colony and let them start walking.
			for(int a = 0; a < maxants; a++) {
				Maze subMaze = new Maze(mainMaze.getWidth(), mainMaze.getHeight(), mainMaze.getStartX(), mainMaze.getStartY(), mainMaze.getEndX(), mainMaze.getEndY());
				for(Tile t: mainMaze.getTiles()) {
					subMaze.addTile(t.getX(), t.getY(), t.getWalkable(), t.getPheromones());
				}
				
				AntRenew ant;
				
				// Check whether it is the first time in the maze.
				if(x == 0) { 
					ant = new AntRenew(true, subMaze.getStartX(), subMaze.getStartY(), subMaze);
				} else {
					ant = new AntRenew(false, subMaze.getStartX(), subMaze.getStartY(), subMaze);
				}
				
				ant.start();
				ants.add(ant);
			}
			
			// Update the pheromones from the maze with the evaporation factor.
			for(Tile t: mainMaze.getTiles()) {
				t.setPheromones(t.getPheromones() * (1d - p));
			}
			
			// Check whether the all ants are finished and update the mainMaze pheromones accordingly
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
		
		// Start an Ant for the last bestPath
		AntRenew finishedAnt = new AntRenew(false, mainMaze.getStartX(), mainMaze.getStartY(), mainMaze);
		finishedAnt.start();
		try {
			finishedAnt.getThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		printMaze(finishedAnt.getWalkedPath());
		
		System.out.println("Finished!");
	}
	
	/**
	 * Print the best Path
	 * @param walkedPath The best walkedPath
	 */
	private void printMaze(ArrayList<Tile> walkedPath) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(mainMaze.getMazepath() + " bestpath.txt", "UTF-8");
			writer.println(walkedPath.size() - 1 + ";");
			writer.println(mainMaze.getStartX() + ", " + mainMaze.getStartY() + ";");
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

	public static void main(String[] args) {
		new AntColony();
	}
}
