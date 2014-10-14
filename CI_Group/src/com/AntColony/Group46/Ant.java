package com.AntColony.Group46;

public class Ant implements Runnable{
	private Thread t;
	
	private int x;
	private int y;
	
	private Maze maze;
	
	public Ant(int x, int y) {
		this.setX(x);
		this.setY(y);
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
	
	@Override
	public void run() {
		System.out.println("Im Running!");
	}
}
