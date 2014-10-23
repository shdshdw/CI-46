package com.AntColony.Group46;

import java.util.ArrayList;
import java.util.Random;

public class AntRenew  implements Runnable {

	private Thread t;

	private int x;
	private int y;

	private Maze maze;
	private ArrayList<Tile> walkedPath;
	private ArrayList<Tile> alreadyWalked;
	private int previousMove = -1;
	private Random rand;

	private boolean first;
	
	public AntRenew(boolean first, int x, int y, Maze maze) {
		this.first = first;
		this.setX(x);
		this.setY(y);
		this.setMaze(maze);
		
		walkedPath = new ArrayList<Tile>();
		alreadyWalked = new ArrayList<Tile>();
		rand = new Random();
	}
	
	public void run() {
		while(x != maze.getEndX() || y != maze.getEndY()) {
			int direction = -1;
			if(walkedPath.contains(maze.getTile(x, y))) {
				int beginIndex = walkedPath.indexOf(maze.getTile(x, y));
				for(int i = walkedPath.size() - 1; i > beginIndex; i--) {
					walkedPath.remove(i);
				}
			} else {
				walkedPath.add(maze.getTile(x, y));				
			}
			
			if(!alreadyWalked.contains(maze.getTile(x, y))) {
				alreadyWalked.add(maze.getTile(x, y));
			}
			
			
			ArrayList<Tile> posDir = getPossibleDirections();
			if(posDir.size() == 1) {
				maze.getTile(x, y).setWalkable(false);
				direction = getDirection(posDir.get(0));
			} else {
				if(first) {
					int already = 0;
					for(Tile t: posDir) {
						if(alreadyWalked.contains(t)) {
							already++;
						}
					}
					if(already == posDir.size()) {
						while(direction == -1 || direction == (previousMove + 2) % 4) {
							direction = getDirection(posDir.get(rand.nextInt(posDir.size())));						
						}						
					} else {
						for(Tile t: posDir) {
							if(!alreadyWalked.contains(t)) {
								direction = getDirection(t);
								break;
							}
						}
					}
				} else {
					ArrayList<Integer> setOfDirections = new ArrayList<Integer>();
					for(Tile t: posDir) {
						int dir = getDirection(t);
						for(int x = 0; x < t.getPheromones(); x++) {
							setOfDirections.add(dir);
						}
					}
					while(direction == -1 || direction == (previousMove + 2) % 4) {
						direction = setOfDirections.get(rand.nextInt(setOfDirections.size()));						
					}
				}
			}
			
			if(direction == 1) {
				setY(y - 1);
			} else if(direction == 0) {
				setX(x + 1);
			} else if(direction == 2) {
				setX(x - 1);
			} else if(direction == 3) {
				setY(y + 1);
			}
			previousMove = direction;
		}
		walkedPath.add(maze.getTile(x, y));
	}
	
	// 0 = EAST, 1 = NORTH, 2 = WEST, 3 = SOUTH
	private ArrayList<Tile> getPossibleDirections() {
		ArrayList<Tile> posDir = new ArrayList<Tile>();
		
		if(maze.isWalkable(x + 1, y)) {
			posDir.add(maze.getTile(x + 1, y));
		}
		
		if(maze.isWalkable(x - 1, y)) {
			posDir.add(maze.getTile(x - 1, y));
		}
		
		if(maze.isWalkable(x, y + 1)) {
			posDir.add(maze.getTile(x, y + 1));
		}
		
		if(maze.isWalkable(x, y - 1)) {
			posDir.add(maze.getTile(x, y - 1));		
		}
		
		return posDir;
	}
	
	private int getDirection(Tile t) {
		if(t.getX() > x) {
			return 0;
		} else if(t.getX() < x) {
			return 2;
		} else if(t.getY() > y) {
			return 3;
		} else if(t.getY() < y) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	public ArrayList<Tile> getWalkedPath() {
		return walkedPath;
	}
	
	public synchronized void start() {
		t = new Thread(this, "Ant");
		t.start();
	}

	public Thread getThread() {
		return t;
	}

}
