package com.TSP.Group46;

public class Calculations {

	/**
	 * Get Direction from Tile to Tile
	 * @param from Current Tile
	 * @param to Next Tile
	 * @return The direction from current Tile to next Tile
	 */
	public static int getDirection(Tile from, Tile to) {
		if(from.getX() > to.getX()) {
			return 2;
		} else if(from.getX() < to.getX()) {
			return 0;
		} else if(from.getY() > to.getY()) {
			return 1;
		} else {
			return 3;
		}
	}
	
	/**
	 * Get Direction from Tile to coordinates
	 * @param from Current Tile
	 * @param toX Next X
	 * @param toY Next Y
	 * @return The direction from the current Tile to the next X,Y
	 */
	public static int getDirection(Tile from, int toX, int toY) {
		if(from.getX() > toX) {
			return 2;
		} else if(from.getX() < toX) {
			return 0;
		} else if(from.getY() > toY) {
			return 1;
		} else {
			return 3;
		}
	}
	
	/**
	 * Check whether the Tiles are equal
	 * @param tile1 Tile1
	 * @param tile2 Tile 2
	 * @return Boolean whether the Tiles are equal
	 */
	public static boolean tileEquals(Tile tile1, Tile tile2) {
		if(tile1.getX() == tile2.getX() && tile1.getY() == tile2.getY()) {
			return true;
		} 
		return false;
	}
	
}
