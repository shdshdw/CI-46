package com.AntColony.Group46;

public class Tile {
	private boolean walkable;
	private int x, y;
	private double pheromones;
	private double chance;
	
	public Tile(int x, int y, boolean walkable) {
		this.x = x;
		this.y = y;
		this.walkable = walkable;
		this.pheromones = 0d;
		this.chance = 0d;
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
	
	public double getPheromones() {
		return pheromones;
	}
	
	public void setPheromones(double pheromones) {
		this.pheromones = pheromones;
	}
	
	public void setChance(double chance) {
		this.chance = chance;
	}
	
	public double getChance() {
		return chance;
	}
	
	
}
