package util.image.processing;

import java.util.LinkedList;
import java.util.Vector;

import util.math2d.Matrix2D;
import util.math2d.Point2D;

public class BaseImageProcessing {
	public static Vector<boolean[][]> getSegments(boolean[][] base, int mode){
		Vector<boolean[][]> result = new Vector<boolean[][]>();
		boolean[][] binaryImg = Matrix2D.copy(base);
		Point2D firstNonEmpty = getFirstNonEmpty(binaryImg);
		while(firstNonEmpty!=null){
			boolean[][] currSegment = new boolean[base.length][base[0].length];
			floodEraseFrom(binaryImg,firstNonEmpty,mode,currSegment);
			result.add(currSegment);
			firstNonEmpty = getFirstNonEmpty(binaryImg);
		}
		return result;
	}
	
	public static boolean[][] getInnerBorder (boolean[][] pixels){
        boolean[][] result = new boolean[pixels.length][pixels[0].length];
        for(int x=0;x<pixels.length;x++){
			boolean topMostAdded=false;
			boolean bottomMostAdded=false;
            for(int y=0;y<pixels[x].length;y++){
				boolean edgeLeft = ( (x>0 && !pixels[x-1][y] && pixels[x][y]) || (x==0 && pixels[x][y]) );
				boolean edgeRight = ( (x<pixels.length-1 && pixels[x][y] && !pixels[x+1][y]) || (x==pixels.length-1 && pixels[x][y]) );
                boolean edgeTop = ( (y>0 && !pixels[x][y-1] && pixels[x][y]) || (y==0 && pixels[x][y]) );
                boolean edgeBottom = ( (y<pixels[x].length-1 && pixels[x][y] && !pixels[x][y+1]) || (y==pixels[x].length-1 && pixels[x][y]) );

                result[x][y] = (edgeBottom || edgeTop || edgeRight || edgeLeft);
            }
        }
		return result;
    }
	
	public static boolean[][] getOuterBorder (boolean[][] pixels){
        boolean[][] result = new boolean[pixels.length][pixels[0].length];
        for(int x=0;x<pixels.length;x++){
			boolean topMostAdded=false;
			boolean bottomMostAdded=false;
            for(int y=0;y<pixels[x].length;y++){
				boolean edgeLeft = (x<pixels.length-1 && pixels[x+1][y] && !pixels[x][y]);
				boolean edgeRight = (x>0 && !pixels[x][y] && pixels[x-1][y]);
                boolean edgeTop = (y>0 && pixels[x][y-1] && !pixels[x][y]);
                boolean edgeBottom = (y<pixels[x].length-1 && !pixels[x][y] && pixels[x][y+1]);

                result[x][y] = (edgeBottom || edgeTop || edgeRight || edgeLeft);
            }
        }
		return result;
    }

	protected static void floodEraseFrom(boolean[][] pixels, Point2D startingPixel, int mode, boolean[][] targetPixels){
		int x=(int)(startingPixel.x);
		int y=(int)(startingPixel.y);
		if(pixels[x][y]){
			LinkedList<Point2D> queue = new LinkedList<Point2D>();
			queue.add(new Point2D(x,y));
			while(!queue.isEmpty()) {
				Point2D point = queue.remove();
				if(point.x>=0 && point.y>=0 && point.x<pixels.length && point.y<pixels[0].length){
					//targetPixels[(int)(point.x)][(int)(point.y)]=false;
					if(pixels[(int)(point.x)][(int)(point.y)]){
						pixels[(int)(point.x)][(int)(point.y)]=false;
						targetPixels[(int)(point.x)][(int)(point.y)]=true;
						//System.out.println(Matrix2D.print(targetPixels));
						//System.out.println("-------------------------");
						if(mode==0){
							queue.add(new Point2D(point.x+1, point.y));
							queue.add(new Point2D(point.x-1, point.y));
							queue.add(new Point2D(point.x, point.y+1));
							queue.add(new Point2D(point.x, point.y-1));
						} else if(mode==1){
							queue.add(new Point2D(point.x+1, point.y+1));
							queue.add(new Point2D(point.x+1, point.y));
							queue.add(new Point2D(point.x+1, point.y-1));
							queue.add(new Point2D(point.x-1, point.y+1));
							queue.add(new Point2D(point.x-1, point.y));
							queue.add(new Point2D(point.x-1, point.y-1));
							queue.add(new Point2D(point.x, point.y+1));
							queue.add(new Point2D(point.x, point.y-1));
						} else {
							System.out.println("wrong flood erase mode");
						}
					}
				}
			}
		}
	}
	
	public static boolean[][] getFloodFilledArea(boolean[][] pixels, int startingX, int startingY, int mode){ return getFloodFilledArea(pixels,new Point2D(startingX,startingY), mode); }
	public static boolean[][] getFloodFilledArea(boolean[][] pixels, Point2D startingPixel, int mode){
		boolean[][] temp = Matrix2D.copy(pixels);
		boolean[][] result = new boolean[pixels.length][pixels[0].length];
		floodEraseFrom(temp,startingPixel,mode,result);
		return result;
	}
		
	protected static Point2D getFirstNonEmpty(boolean[][] pixels){
		for(int x=0;x<pixels.length;x++){
			for(int y=0;y<pixels[0].length;y++){
				if(pixels[x][y]){ return new Point2D(x,y); }
			}
		}
		return null;
	}
	
}
