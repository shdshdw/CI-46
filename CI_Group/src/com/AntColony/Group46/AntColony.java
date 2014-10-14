package com.AntColony.Group46;

import java.util.ArrayList;


public class AntColony {
	private int maxants = 1000;
	private int maxiterations = 100;
	
	private ArrayList<Ant> ants;
	private Maze maze;
	
	public AntColony() {
		maze = new Maze("easy coordinates.txt", "easy maze.txt");
		
		for(int x = 0; x < maxiterations; x++) {
			ants = new ArrayList<Ant>();
			
			for(int a = 0; a < maxants; a++) {
				Ant ant = new Ant(maze.getStartX(), maze.getStartY(), maze);
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
			
			System.out.println("Finished!");
		}
	}
	
	public static void main(String[] args) {
		new AntColony();
	}
}
