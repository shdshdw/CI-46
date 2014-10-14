package com.AntColony.Group46;

import java.util.ArrayList;


public class AntColony {
	private int maxants = 1000;
	private int maxiterations = 100;
	
	private ArrayList<Ant> ants;
	private Maze maze;
	
	public AntColony() {
		maze = new Maze("easy coordinates.txt", "easy maze.txt");
		
		ants = new ArrayList<Ant>();
		
		for(int x = 0; x < maxants; x++) {
			ants.add(new Ant(maze.getStartX(), maze.getStartY()));
		}
		
		for(int x = 0; x < maxiterations; x++) {
			
		}
	}
	
	public static void main(String[] args) {
		new AntColony();
	}
}
