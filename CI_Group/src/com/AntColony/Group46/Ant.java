package com.AntColony.Group46;

import java.util.ArrayList;
import java.util.Random;

public class Ant implements Runnable{
	private Thread t;
	
	private int x;
	private int y;
	
	private Maze maze;
	private ArrayList<Tile> walkedPath;
	private Random rand;
	
	private int previousMove = -1;
	
	public Ant(int x, int y, Maze maze) {
		this.setX(x);
		this.setY(y);
		this.setMaze(maze);
		
		walkedPath = new ArrayList<Tile>();
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
		t = new Thread(this, "Ant");
		t.start();
	}
	
	public Thread getThread() {
		return t;
	}
	
	// 0 = EAST, 1 = NORTH, 2 = WEST, 3 = SOUTH
	
	@Override
	public void run() {
		while(x != maze.getEndX() || y != maze.getEndY()) {
			walkedPath.add(maze.getTile(x, y));
			boolean[] possible = new boolean[4];
			for(int x = 0; x < possible.length; x++) { possible[x] = false; }
			if(maze.isWalkable(x + 1, y)) { possible[0] = true; }
			if(maze.isWalkable(x, y - 1)) { possible[1] = true; }
			if(maze.isWalkable(x - 1, y)) { possible[2] = true; }
			if(maze.isWalkable(x, y + 1)) { possible[3] = true; }
			int countTrue = 0;
			for(int x = 0; x < possible.length; x++) { if(possible[x]) { countTrue++; } }
			int direction = -1;
			if(countTrue == 1 && previousMove > -1) {
				maze.getTile(x, y).setWalkable(false);
				for(int x = 0; x < possible.length; x++) { if(possible[x]) { direction = x; } }
			} else {
				while(direction == -1 || direction == (previousMove + 2) % 4) {
					direction = rand.nextInt(4);
					if(!possible[direction]) { direction = -1; }
				}				
			}
			if(direction == 0) { setX(getX() + 1); }
			else if(direction == 1) { setY(getY() - 1); }
			else if(direction == 2) { setX(getX() - 1); }
			else if(direction == 3) { setY(getY() + 1); }
			previousMove = direction;
		}
		walkedPath.add(maze.getTile(x, y));
	}

	public ArrayList<Tile> getWalkedPath() {
		return walkedPath;
	}
}
