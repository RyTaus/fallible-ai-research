import javax.swing.JFrame;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.BorderLayout; 


public class Game extends JPanel{
	public static void main(String[] args) {
		
		Game g = new Game(new Map("src/test"));
		
        JFrame application = new JFrame();                            // the program itself
        
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // set frame to exit
                                                                      // when it is closed
        application.add(g);           


        application.setSize(500, 400);         // window is 500 pixels wide, 400 high
        application.setVisible(true);          

	}
	public int W = 32;
	public int H = 32;
	public Map map;
	public Game(Map m) {
		super();
		map = m;
		setLocation(0, 0);
		setSize(480 + 17, 360 + 39);
		setBackground(Color.ORANGE);
		setFocusable(true);
		setVisible(true);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g); 
        int width = getWidth();             // width of window in pixels
        int height = getHeight();           // height of window in pixels
		
        drawMap(g);
        g.setColor(Color.BLACK);
		g.drawString("Hello, World", 100, 150); 
	}
	
	private void drawCell(Graphics g, Color c, int x, int y, int w, int h) {
		g.setColor(c);
		g.fillRect(x, y, w, h);
	}
	
	private void drawMap(Graphics g) {
		 for (int col = 0; col < map.width; col ++) {
	        	for (int row = 0; row < map.height; row ++) {
	        		drawCell(g, map.getCell(col, row).type.img(), W * col, H * row, W, H); 
	        	}
	        }
	}
	
	
}
