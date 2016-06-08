import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Game extends JPanel{
	public static void main(String[] args) {
		
		Unit p1 = new Unit(new Coord(2, 1), 3, 20, 8, 3, 5, Class.Gunner, TeamType.PLAYER);
		Unit p2 = new Unit(new Coord(1, 3), 5, 22, 7, 6, 5, Class.Swordsman, TeamType.PLAYER);
		
		Unit e1 = new Unit(new Coord(6, 6), 3, 12, 8, 5, 2, Class.Swordsman, TeamType.ENEMY);
		Unit e2 = new Unit(new Coord(5, 7), 5, 13, 6, 6, 6, Class.Gunner, TeamType.ENEMY);
		
		Team p = new Team(new Unit[]{p1, p2}, TeamType.PLAYER);
		Team e = new Team(new Unit[]{e1, e2}, TeamType.ENEMY);
		
		GameBoard b = new GameBoard(new Map("src/test"), p, e);
		Game g = new Game(b);
		
		
        JFrame application = new JFrame();                            // the program itself
        application.setBackground(Color.DARK_GRAY);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // set frame to exit
                                                                      // when it is closed
        application.add(g);           


        application.setSize(500, 400);         // window is 500 pixels wide, 400 high
        application.setVisible(true); 
        
		PlayerInput pi = new PlayerInput();
		g.addKeyListener(pi);
        
        final Timer timer = new Timer(60, null);
		ActionListener listener = new ActionListener() {
		    @Override 
		    public void actionPerformed(ActionEvent e) {
		    	pi.addPressed();
		        g.update(pi);
		        pi.reset();
		    }
		};
		timer.addActionListener(listener);
		timer.start();

	}
	
	public void update(PlayerInput pi) {
		// TODO Auto-generated method stub
		if (pi.keyDown(KeyEvent.VK_W)) {
			gb.move(Direction.UP);
		}
		if (pi.keyDown(KeyEvent.VK_A)) {
			gb.move(Direction.LEFT);
		}
		if (pi.keyDown(KeyEvent.VK_S)) {
			gb.move(Direction.DOWN);
		}
		if (pi.keyDown(KeyEvent.VK_D)) {
			gb.move(Direction.RIGHT);
		}
		if (pi.keyDown(KeyEvent.VK_O)) {
			gb.press();
		}
		if (pi.keyDown(KeyEvent.VK_P)) {
			gb.back();
		}
		if (pi.keyDown(KeyEvent.VK_SPACE)) {
			if (gb.isUnitOn(gb.map.cursor)) {
				System.out.println(gb.unitOn(gb.map.cursor));
			}
		}
		repaint();
//		System.out.println(gb.state);
	}









	public int W = 40;
	public int H = 40;
	public GameBoard gb;
	public Game(GameBoard b) {
		super();
		gb = b;
		setLocation(0, 0);
		setSize(480 + 17, 360 + 39);
		setBackground(Color.DARK_GRAY);
		setFocusable(true);
		setVisible(true);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
        int width = getWidth();             // width of window in pixels
        int height = getHeight();           // height of window in pixels
		
        drawMap(g);
        
        drawCursor(g, gb.map.cursor);
        drawUnits(g);
        drawHighlights(g);
        drawActionMenu(g);
	}
	
	private void drawCell(Graphics g, Color c, int x, int y) {
		g.setColor(c);
		g.fillRect(x, y, W - 1, H - 1);
	}
	
	private void drawCursor(Graphics g, Coord c) {
		g.setColor(Color.WHITE);
		g.drawRect(c.x * W - 1, c.y * H - 1, W, H);
		g.drawRect(c.x * W, c.y * H, W - 2, H - 2);
	}
	
	private void drawMap(Graphics g) {
		for (int col = 0; col < gb.map.width; col ++) {
			for (int row = 0; row < gb.map.height; row ++) {
				Coord c = new Coord(col, row);
				drawCell(g, gb.map.getCell(c).type.img(), W * col, H * row); 
	        }
	    }
	}
	
	private void drawUnits(Graphics g) {
		for (Unit u : gb.player.units) {
			g.drawImage(u.image(), u.position.x * W + 4, u.position.y * H + 4, null);
		}
		for (Unit u : gb.enemy.units) {
			g.drawImage(u.image(), u.position.x * W + 4, u.position.y * H + 4, null);
		}
	}
	
	private void drawHighlights(Graphics g) {
		if (gb.state == GameBoard.State.SELECT_DESTINATION) {
			if (gb.isUnitOn(gb.map.marked)) {
				Unit uoi = gb.unitOn(gb.map.marked);
				Coord[] blue = gb.canMoveMap(uoi);
				g.setColor(new Color(20, 20, 250, 90));
				for (Coord spot : blue) {
					g.fillRect(spot.x * W, spot.y * H, W - 1, H - 1);
				}
				
				Coord[] red = gb.canAttackMap(uoi);
				g.setColor(new Color(250, 20, 20, 90));
				for (Coord spot : red) {
					g.fillRect(spot.x * W, spot.y * H, W - 1, H - 1);
				}
			}
		}
		if (gb.state == GameBoard.State.SELECT_UNIT) {
			if (gb.isUnitOn(gb.map.cursor)) {
				Unit uoi = gb.unitOn(gb.map.cursor);
				Coord[] blue = gb.canMoveMap(uoi);
				g.setColor(new Color(20, 20, 250, 90));
				for (Coord spot : blue) {
					g.fillRect(spot.x * W, spot.y * H, W - 1, H - 1);
				}
				
				Coord[] red = gb.canAttackMap(uoi);
				g.setColor(new Color(250, 20, 20, 90));
				for (Coord spot : red) {
					g.fillRect(spot.x * W, spot.y * H, W - 1, H - 1);
				}
			}
		}
	}
	
	private void drawActionMenu(Graphics g) {
		if (gb.state == GameBoard.State.SELECT_ACTION) {
			g.setColor(Color.WHITE);
			String[] arr = gb.actionMenu.toStrings();
			g.setColor(Color.BLACK);
			g.fillRect(400, 5, 50, 10 + arr.length * 10);
			for (int i = 0; i < arr.length; i ++) {
				g.setColor(Color.WHITE);
				if (gb.actionMenu.state == ActionMenu.State.Primary) {
					if (gb.actionMenu.menu.selected == i) {
						g.setColor(Color.CYAN);
					}
				}
				g.drawString(arr[i], 400, 20 + (i * 10));
			}
				
		}
	}
	
	
}
