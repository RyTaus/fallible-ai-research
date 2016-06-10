import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class PathFinder {
	
	public Coord[] tree;
	public int moves;
	private GameBoard gb;
	private Map m;
	private Unit u;
	
	public PathFinder(GameBoard ma, Unit un, Coord starting) {
		gb = ma;
		u = un;
		moves = u.job.movement;
		tree = new Coord[getLength(moves)];
		tree[0] = new Coord(starting.x, starting.y);
		m = gb.map;
		
		fillTree();
	}
	
	public PathFinder(GameBoard ma, Unit un) {
		this.gb = ma;
		this.u = un;
		moves = u.job.movement;
		tree = new Coord[getLength(moves)];
		tree[0] = new Coord(u.position.x, u.position.y);
		m = gb.map;
		
		fillTree();
	}
	
	private void fillTree() {
		Direction n = Direction.UP;
		Direction e = Direction.RIGHT;
		Direction s = Direction.DOWN;
		Direction w = Direction.LEFT;

		for(int i = 0; i < getLength(moves - 1); i ++) {
			addNext(i, n);
			addNext(i, e);
			addNext(i, s);
			addNext(i, w);
		}
	}
	
	private void addNext(int init, Direction dir) {
		if (tree[init] == null) {
			int temp = init;
			int counter = 0;
			while (tree[temp] == null) {
				temp = getPrev(temp);
				counter += 1;
			}
			
			if (gb.checkToAdd(tree[temp].getPointTo(dir), u)) {
				if (counter == m.getCell(tree[temp].getPointTo(dir)).moveAffect(u)) {
					tree[getNext(init, dir)] = tree[temp].getPointTo(dir);
				} else {
					tree[getNext(init, dir)] = null;
				}
			}
		} else {
			if (gb.checkToAdd(tree[init].getPointTo(dir), u)) {
				if (m.getCell(tree[init].getPointTo(dir)).moveAffect(u) == 0) {
					tree[getNext(init, dir)] = tree[init].getPointTo(dir);
				} else {
					tree[getNext(init, dir)] = null;
				}
			}
		}

	
	}
	
	public ArrayList<Direction> getPurePathTo(Coord p) {
		ArrayList<Direction> temp = getPathTo(p);
		ArrayList<Direction> toReturn = new ArrayList<Direction>();
		for (Direction d: temp) {
			if (d != Direction.STAY) {
				toReturn.add(d);
			}
		}
		if (toReturn.isEmpty()) {
			toReturn.add(Direction.STAY);
		}
		return toReturn;
	}
	
	public ArrayList<Direction> getPathTo(Coord p) {
		int distance = p.distance(tree[0]);
		int start = getLength(distance - 1);
		int position = 0;
		for (int i = start; i < tree.length; i ++) {
			if (tree[i] != null) {
				if (tree[i].equals(p)) {
					position = i;
					break;
				}
			}
		}
		
		int temp = 0;
		while (getLength(temp) <= position) {
			temp +=1;
		}
		
		ArrayList<Direction> steps = new ArrayList<Direction>();
		for (int i = temp; i > 0; i --) {
		//	System.out.println("  Step Number: " + i + " -- " + intToDir(position) + " -- " + tree[position] + " -- " + position);
			steps.add(0, intToDir(position));
			position = (position - 1)/4;
		}
	//	System.out.println("  Step Number: " + 0 + " -- " + "NONE" + " -- " + tree[0] + " -- " + 0);
		return steps;
	}
	
	public int getNext(int i, Direction dir) {
		return i*4 + dirToInt(dir);
	}
	
	public int getPrev(int i) {
		return (i - 1)/4;
	}
	public Direction intToDir(int i) {
		if (tree[i] == null) {
			return Direction.STAY;
		}
		int temp = i % 4;
		if (temp == 1) {
			return Direction.UP;
		} else if (temp == 2) {
			return Direction.RIGHT;
		} else if (temp == 3) {
			return Direction.DOWN;
		} else if (temp == 0) {
			return Direction.LEFT;
		} else {
			return Direction.STAY;
		}
	}
	
	public boolean isValidPath(ArrayList<Direction> currentPath) {
		if (currentPath.size() > moves) {
			return false;
		} 
		Coord destination = new Coord(u.position.x, u.position.y);
		ArrayList<Coord> CoordsTraveled = new ArrayList<Coord>();
		CoordsTraveled.add(new Coord(u.position.x, u.position.y));
		for(Direction d: currentPath) {
			destination = destination.getPointTo(d);
			if (d != Direction.STAY) {
				CoordsTraveled.add(CoordsTraveled.get(CoordsTraveled.size() - 1).getPointTo(d));
			}
		}
		for (int i = 0; i < CoordsTraveled.size(); i ++) {
			for (int j = i + 1; j < CoordsTraveled.size(); j ++) {
				if (CoordsTraveled.get(i).equals(CoordsTraveled.get(j))) {
					return false;
				}
			}
		}
		return true;
		
	}
	
	public int getLength(int m) {
		int toReturn = 0;
		for (int i = 0; i <= m; i ++) {
			toReturn += (int) Math.pow(4, i);
		}
		return toReturn;
	}
	
	private int dirToInt(Direction dir) {
		if (dir == Direction.UP) {
			return 1;
		} else if (dir == Direction.RIGHT) {
			return 2;
		} else if (dir == Direction.DOWN) {
			return 3;
		} else if (dir == Direction.LEFT) {
			return 4;
		} else {
			return 0;
		}
	}
}
	

	