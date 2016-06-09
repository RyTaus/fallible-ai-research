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
    
    public Coord cursor;
    public Coord marked;

    public Coord topLeft;
    public int SCREEN_WIDTH;
    public int SCREEN_HEIGHT;
    public int MARGIN;
    
    public Map(String fileName) {
        map = readFile(fileName);
        width = map.length;
        height = map[0].length;
        cursor = new Coord(3, 3);
        marked = new Coord(3, 3);
        topLeft = new Coord(0,0);
        SCREEN_WIDTH = 8;
        SCREEN_HEIGHT = 8;
        MARGIN = 2;
    }

    public Coord getRelativeCoord(Coord c) {
        return c.getDirection(topLeft);
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
    
    public Cell getCell(Coord c) {
        return map[c.x][c.y];
    }

    public void moveCursor(Direction dir) {
        Coord c = dir.toCoord();

        int newX = cursor.x + c.x;
        int newY = cursor.y + c.y;
        
        if (newX < 0 || newX > width - 1) {
            newX = cursor.x;
        }
        
        if (newY < 0 || newY > height - 1) {
            newY = cursor.y;
        }

        Coord nc = new Coord(newX, newY);
        Coord scrCurs = getRelativeCoord(nc);

        if (scrCurs.y >= SCREEN_HEIGHT - MARGIN && (topLeft.y + SCREEN_HEIGHT) < height) {
            topLeft.y += 1;
        } else if (scrCurs.x >= SCREEN_WIDTH - MARGIN && (topLeft.x + SCREEN_WIDTH) < width) {
            topLeft.x += 1;
        } else if (scrCurs.y < MARGIN && (topLeft.y - 1) >= 0) {
            topLeft.y -= 1;
        } else if (scrCurs.x < MARGIN && (topLeft.x - 1) >= 0) {
            topLeft.x -= 1;
        }

        cursor.moveTo(nc);
        System.out.println(cursor);
        System.out.println(topLeft);
        System.out.println(getRelativeCoord(cursor));
        System.out.println("____________");
    }
    
}