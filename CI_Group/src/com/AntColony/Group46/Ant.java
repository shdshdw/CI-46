package com.AntColony.Group46;

import java.util.ArrayList;
import java.util.Random;

public class Ant implements Runnable{
	private Thread t;
	
	private int x;
	private int y;
	
	private Maze maze;
	private ArrayList<Tile> walkedWay;
	private Random rand;
	
	public Ant(int x, int y, Maze maze) {
		this.setX(x);
		this.setY(y);
		this.setMaze(maze);
		
		walkedWay = new ArrayList<Tile>();
		rand = new Random();
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public synchronized void start() {
		t = new Thread("Ant");
		t.start();
	}
	
	public Thread getThread() {
		return t;
	}
	
	// 0 = EAST, 1 = NORTH, 2 = WEST, 3 = SOUTH
	
	@Override
	public void run() {
		walkedWay.add(maze.getTile(x, y));
		while(x != maze.getEndX() || y != maze.getEndY()) {
			boolean[] possible = new boolean[4];
			for(int x = 0; x < possible.length; x++) { possible[x] = false; }
			if(maze.isWalkable(x + 1, y)) { possible[0] = true; }
			if(maze.isWalkable(x, y - 1)) { possible[1] = true; }
			if(maze.isWalkable(x - 1, y)) { possible[2] = true; }
			if(maze.isWalkable(x, y + 1)) { possible[3] = true; }
			int direction = -1;
			while(direction == -1) {
				rand.
			}
		}
	}
}
