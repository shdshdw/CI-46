package com.AntColony.Group46;

import java.util.ArrayList;
import java.util.Random;

public class Ant implements Runnable {
	private Thread t;

	private int x;
	private int y;

	private Maze maze;
	private ArrayList<Tile> walkedPath;
	private Random rand;

	private int previousMove = -1;
	private boolean first;

	public Ant(boolean first, int x, int y, Maze maze) {
		this.first = first;
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
		while (x != maze.getEndX() || y != maze.getEndY()) {
			walkedPath.add(maze.getTile(x, y));
			Tile[] possible = new Tile[4];
			for (int x = 0; x < possible.length; x++) {
				possible[x] = null;
			}
			if (maze.isWalkable(x + 1, y)) {
				possible[0] = maze.getTile(x + 1, y);
			}
			if (maze.isWalkable(x, y - 1)) {
				possible[1] = maze.getTile(x, y - 1);
			}
			if (maze.isWalkable(x - 1, y)) {
				possible[2] = maze.getTile(x - 1, y);
			}
			if (maze.isWalkable(x, y + 1)) {
				possible[3] = maze.getTile(x, y + 1);
			}
			int countTrue = 0;
			for (int x = 0; x < possible.length; x++) {
				if (possible[x] != null) {
					countTrue++;
				}
			}
			int direction = -1;
			if (countTrue == 0) {
				System.out.println("STUCK!");
			} else if (countTrue == 1 && previousMove > -1) {
				maze.getTile(x, y).setWalkable(false);
				for (int x = 0; x < possible.length; x++) {
					if (possible[x] != null) {
						direction = x;
					}
				}
			} else {
				if(first) {
					while (direction == -1 || direction == (previousMove + 2) % 4) {
						direction = rand.nextInt(4);
						if (possible[direction] == null) {
							direction = -1;
						}
					}
				} else {
					double totalPheromones = 0d;
					for (int x = 0; x < possible.length; x++) {
						if(possible[x] != null) {
							totalPheromones += possible[x].getPheromones();
						}
					}
					for (int x = 0; x < possible.length; x++) {
						if(possible[x] != null) {
							possible[x].setChance(possible[x].getPheromones() / totalPheromones);
							for(int y = 0; y < x; y++) {
								if(possible[y] != null){
									possible[x].setChance(possible[x].getChance() + possible[y].getChance());
								}
							}
						}
					}
					
					while(direction == -1) {
						double randum = rand.nextDouble();
						System.out.println("WHILE");
						for (int x = 0; x < possible.length; x++) {
							if (possible[x] != null && possible[x].getChance() < randum) {
								direction = x;
								System.out.println(direction);
								break;
							}
						}						
					}
					
					for (int x = 0; x < possible.length; x++) {
						if(possible[x] != null) {
							possible[x].setChance(0d);
						}
					}
				}				
			}
			if (direction == 0) {
				setX(getX() + 1);
			} else if (direction == 1) {
				setY(getY() - 1);
			} else if (direction == 2) {
				setX(getX() - 1);
			} else if (direction == 3) {
				setY(getY() + 1);
			}
			previousMove = direction;
		}
		walkedPath.add(maze.getTile(x, y));
	}

	public ArrayList<Tile> getWalkedPath() {
		return walkedPath;
	}
}
