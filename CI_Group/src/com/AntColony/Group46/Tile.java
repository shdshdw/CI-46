package com.AntColony.Group46;

public class Tile {
	private boolean walkable;
	private int x, y;
	
	public Tile(int x, int y, boolean walkable) {
		this.x = x;
		this.y = y;
		this.walkable = walkable;
	}
	
	public int getY() {
		return y;
	}
	
	public int getX() {
		return x;
	}
	
	public boolean getWalkable() {
		return walkable;
	}
	
	public void setWalkable(boolean bool) {
		walkable = bool;
	}
}
