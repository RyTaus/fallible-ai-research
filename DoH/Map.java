import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public class Map {
	
	public int width;
	public int height;
	public Cell[][] map;
	
	public Map(String fileName) {
		map = readFile(fileName);
		width = map.length;
		height = map[0].length;
	}
	
	public Cell[][] readFile(String s) {
		try {
			int width = 0;
			int row = 0;
			char[][] charArray = new char[256][256];

			InputStream is;
			is = new FileInputStream(s);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			
			String line;
			while ((line = br.readLine()) != null) {
		        String[] nums = line.split("");
		        if (nums.length > width) {
		        	width = nums.length;
		        }
		        for (int col = 0; col < nums.length; col++) {
		            char n = nums[col].charAt(0);
		            charArray[col][row] = n;  
		        }
		        row++;
		    }
		    
			Cell[][] m = new Cell[width][row];
			for (int col = 0; col < width; col++) {
				for (int row1 = 0; row1 < row; row1++) {
		            m[col][row1] = new Cell(charArray[col][row1]);
				}
			}
			br.close();
			return m;
		} catch (FileNotFoundException fnfe) {
			System.out.println("fnfe");
		} catch (IOException ioe) {
			System.out.println("ioe");
		}
		System.out.println("NOOOOOO");
		return null;
	}
	
	public Cell getCell(int col, int row) {
		return map[col][row];
	}
	

}
