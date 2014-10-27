package com.TSP.Group46;

public class Calculations {

	// 0 = EAST, 1 = NORTH, 2 = WEST, 3 = SOUTH
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
	
	public static boolean tileEquals(Tile tile1, Tile tile2) {
		if(tile1.getX() == tile2.getX() && tile1.getY() == tile2.getY()) {
			return true;
		} 
		return false;
	}
	
}
