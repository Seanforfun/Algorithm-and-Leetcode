package ca.mcmaster.offer;

import java.util.ArrayList;
import java.util.List;

public class Question9_2 {
	public static class Point{
		int x;
		int y;
		public Point(int x, int y){
			this.x = x;
			this.y = y;
		}
		public void print(){
			System.out.println(x + "   " + y);
		}
	}
	public static boolean getPath(int x, int y, List<Point> path){
		Point p = new Point(x, y);
		path.add(p);
		if(x == 0 && y == 0)	//已经在原点
			return true;
		boolean success = false;
		if(x > 0 && isFree(x - 1, y)){	//尝试向上移动
			success = getPath(x - 1, y, path);
		}
		if(!success && y > 0 && isFree(x, y - 1)){
			success = getPath(x, y - 1, path);
		}
		if(!success)
			path.add(p);
		return success;
	}
	public static boolean isFree(int x, int y){
		if(x == 2 && y == 3)
			return false;
		return true;
	}
	public static void main(String[] args) {
		List<Point> path = new ArrayList<>();
		System.out.println(getPath(2, 3, path));
		for(Point p : path)
			p.print();
	}
}
