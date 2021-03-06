package com.TSP.Group46;

import java.util.ArrayList;
import java.util.Random;

public class Ant implements Runnable {

	private Thread t;

	private int x;
	private int y;

	private Maze maze;
	private ArrayList<Tile> walkedPath;
	private ArrayList<Tile> alreadyWalked;
	private ArrayList<ArrayList<Tile>> productPaths;
	private boolean[] productsVisited;
	private int previousMove = -1;
	private Random rand;

	private boolean first;
	
	public Ant(boolean first, int x, int y, Maze maze) {
		this.first = first;
		this.setX(x);
		this.setY(y);
		this.setMaze(maze);
		
		productsVisited = new boolean[maze.getCountProducts()];
		for(int p = 0; p < productsVisited.length; p++) {
			productsVisited[p] = false;
		}
		
		walkedPath = new ArrayList<Tile>();
		alreadyWalked = new ArrayList<Tile>();
		productPaths = new ArrayList<ArrayList<Tile>>();
		rand = new Random();
	}
	
	/**
	 * Run the algorithm.
	 */
	public void run() {
		// Check is all products found and Ant is at the end, otherwise run again
		while(!foundAllProduct() || x != maze.getEndX() || y != maze.getEndY()) {
			int direction = -1;
			// Check the walkedPath for loops.
			if(walkedPath.contains(maze.getTile(x, y))) {
				int beginIndex = walkedPath.indexOf(maze.getTile(x, y));
				for(int i = walkedPath.size() - 1; i > beginIndex; i--) {
					walkedPath.remove(i);
				}
			} else {
				walkedPath.add(maze.getTile(x, y));
				// If product is on Tile, add to visited products and get the walked productPath.
				if(maze.getTile(x, y).isProduct() && !productsVisited[maze.getTile(x, y).getProduct().getNumber() - 1]) {
					productsVisited[maze.getTile(x, y).getProduct().getNumber() - 1] = true;
					productPaths.add(walkedPath);
					walkedPath = new ArrayList<Tile>();
					walkedPath.add(maze.getTile(x, y));
				}
			}			
			
			// Get possible directions.
			ArrayList<Tile> posDir = getPossibleDirections();
			if(posDir.size() == 1) {
				maze.getTile(x, y).setWalkable(false);
				direction = getDirection(posDir.get(0));
			} else {
				// Run this algorithm, when maze is walked for the first time, so random.
				if(first) {
					if(!alreadyWalked.contains(maze.getTile(x, y))) {
						alreadyWalked.add(maze.getTile(x, y));
					}
					
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
					ArrayList<Tile> isInSetOfDirections = new ArrayList<Tile>();
					
					// Add the directions according to the pheromones.
					for(Tile t: posDir) {
						int dir = getDirection(t);
						if((int)t.getPheromones() > 0) {
							isInSetOfDirections.add(t);
						}
						for(int x = 0; x < (int)t.getPheromones(); x++) {
							setOfDirections.add(dir);
						}
					}
					
					// Choose a direction according the pheromones.
					if(isInSetOfDirections.size() == 1 && getDirection(isInSetOfDirections.get(0)) == (previousMove + 2) % 4) {
						posDir.remove(isInSetOfDirections.get(0));
						direction = getDirection(posDir.get(rand.nextInt(posDir.size())));
					} else {
						while(direction == -1 || direction == (previousMove + 2) % 4) {
							if(setOfDirections.size() == 0) {
								direction = getDirection(posDir.get(rand.nextInt(posDir.size())));
							} else {
								direction = setOfDirections.get(rand.nextInt(setOfDirections.size()));
							}						
						}						
					}
				}
			}
			
			// Update X and Y to the direction.
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
		productPaths.add(walkedPath);
	}
	
	/**
	 * Get possible directions of the current Ant.
	 * @return ArrayList of possible Tiles
	 */
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
	
	/**
	 * Get the direction for the next tile
	 * @param t Next tile
	 * @return an int for the direction to walk
	 */
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
	
	/**
	 * Check whether the Ant found all products
	 * @return Boolean whether the Ant found all products
	 */
	private boolean foundAllProduct() {
		for(int x = 0; x < productsVisited.length; x++) {
			if(!productsVisited[x]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get the productPaths
	 * @return ArrayList of ArrayLists of Tiles
	 */
	public ArrayList<ArrayList<Tile>> getProductPaths() {
		return productPaths;
	}

}
