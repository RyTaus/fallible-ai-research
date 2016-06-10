import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Game extends JPanel{
	public static void main(String[] args) {
		
		Unit p1 = new Unit(new Coord(2, 1), 3, 20, 8, 3, 5, Class.Gunner, TeamType.PLAYER);
		Unit p2 = new Unit(new Coord(1, 3), 5, 22, 4, 6, 5, Class.Swordsman, TeamType.PLAYER);
		
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
        
        final Timer timer = new Timer(1000/60, null);
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
		if (gb.getPlayer(gb.phase).getClass() == new HumanPlayer().getClass()) {
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
		} else {
			Player p = gb.getPlayer(gb.phase);
//			p.makeMove(gb, gb.getTeam(gb.phase));
			switch(gb.state) {
				case SELECT_UNIT:
					p.makeMove(gb, gb.getTeam(gb.phase));
					gb.selectUnit();
					break;
				case SELECT_DESTINATION:
					gb.moveUnit(p.getUnit(), p.getDestination());
					break;
				case MOVE:
					break;
				case SELECT_ACTION:
					gb.performAction(p.getUnit(), p.getAction(), p.getTarget());
					break;
				case BATTLE:
					break;
			}
		}
		gb.update();
		repaint();
//		System.out.println(gb.state);
	}









	public int W = 50;
	public int H = 50;
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
        drawHighlights(g);
        
        drawCursor(g, gb.map.getRelativeCoord(gb.map.cursor));
        drawUnits(g);

        drawActionMenu(g);
        drawUnitInfo(g);
        drawTerrainInfo(g);
	}
	
	private Coord[] getShape(Coord co) {
		Coord[] v = new Coord[4]; // tl, tr, br, bl
		double x = co.x;
		double y = co.y;
		int offset = -W;
		int offsetY = H/2;
		
		Coord tl = new Coord((int) getX(x, y) + offset,
				getY(y) + offsetY);
		Coord tr = new Coord((int) getX(x + 1, y) - 1 + offset,
				getY(y) + offsetY);
		Coord br = new Coord((int) getX(x + 1, y + 1) - 1 + offset,
				getY(y + 1) -1 + offsetY);
		Coord bl = new Coord((int) getX(x, y + 1) + offset,
				getY(y + 1) - 1 + offsetY);
		
		v[0] = tl;
		v[1] =  tr;
		v[2] =  br;
		v[3] =  bl;
		return v;
	}	
	
	private int getY(double yi) {
		double yMax = gb.map.SCREEN_HEIGHT;
		double y = yi;
		return (int) ((y * H) - (8 * (yMax - 1 - y)));
	}
	
	private int Xmargin(double y) {
		return (int) (30 * gb.map.height - y);
	}
	
	private int Xwidth(double yi) {
//		return (int) (W * .5 + (W * .5 * y));
		double yMax = gb.map.SCREEN_HEIGHT;
		double perY = 2;
		double y = yMax - 1 - yi;
		return (int) ((perY * y) - W);
	}
	
	private double X(double x) {
		return (gb.map.SCREEN_WIDTH/ 2) - x;
	}
	
	private int getX(double xi, double yi) {
		double x = X(xi);
		double y = yi;
		return (int) (Xmargin(y) + (Xwidth(y) * x));
	}
	
	

	
	private void drawCell(Graphics g, Color c, Coord co) {
//		g.setColor(c);
//		g.fillRect(co.x* W, co.y * H, W - 1, H - 1);
		g.setColor(c);
		Coord[] cs = getShape(co);
		g.fillPolygon(new int[]{cs[0].x,  cs[1].x,  cs[2].x, cs[3].x}, 
				new int[]{cs[0].y,  cs[1].y,  cs[2].y, cs[3].y}, 4);
	}
	
	
	private void drawCursor(Graphics g, Coord c) {
		g.setColor(Color.WHITE);
		Coord[] cs = getShape(c);

		g.drawPolygon(new int[]{cs[0].x + 1,  cs[1].x - 1,  cs[2].x - 1, cs[3].x + 1}, 
				new int[]{cs[0].y + 1,  cs[1].y + 1,  cs[2].y - 1, cs[3].y - 1}, 4);
		g.drawPolygon(new int[]{cs[0].x + 2,  cs[1].x - 2,  cs[2].x - 2, cs[3].x + 2}, 
				new int[]{cs[0].y + 2,  cs[1].y + 2,  cs[2].y - 2, cs[3].y - 2}, 4);
		g.setColor(new Color(250, 250, 250, 80));
		g.fillPolygon(new int[]{cs[0].x,  cs[1].x,  cs[2].x, cs[3].x}, 
				new int[]{cs[0].y,  cs[1].y,  cs[2].y, cs[3].y}, 4);
	}
	
	private void drawMap(Graphics g) {
		for (int col = gb.map.topLeft.x; col < gb.map.topLeft.x + gb.map.SCREEN_WIDTH; col ++) {
			for (int row = gb.map.topLeft.y; row < gb.map.topLeft.y + gb.map.SCREEN_HEIGHT; row ++) {
				Coord c = new Coord(col, row);
				Coord drawLoc = gb.map.getRelativeCoord(c);
				drawCell(g, gb.map.getCell(c).type.img(), new Coord(drawLoc.x, drawLoc.y)); 
				
			}
	    }
//		g.setColor(Color.magenta);
//		g.fillPolygon(new int[]{10, 6, 44, 40}, new int[]{10, 40, 40, 10}, 4);
	}
	
	private void drawUnits(Graphics g) {
		Coord relPos;
		for (Unit u : gb.player.units) {
			relPos = gb.map.getRelativeCoord(u.position);
			if (relPos.y >= 0 && relPos.y < gb.map.SCREEN_HEIGHT && relPos.x >= 0 && relPos.x < gb.map.SCREEN_WIDTH) {
				relPos = getShape(relPos)[0];
				g.drawImage(u.image(), relPos.x + u.offSet.x, relPos.y + u.offSet.y, null);
				drawHealthBar(g, u);
			}
		}
		for (Unit u : gb.enemy.units) {
			relPos = gb.map.getRelativeCoord(u.position);
			if (relPos.y >= 0 && relPos.y < gb.map.SCREEN_HEIGHT && relPos.x >= 0 && relPos.x < gb.map.SCREEN_WIDTH) {
				relPos = getShape(relPos)[0];
				g.drawImage(u.image(), relPos.x + u.offSet.x, relPos.y + u.offSet.y, null);
				drawHealthBar(g, u);
			}
		}
	}
	
	private void drawHighlights(Graphics g) {
		if (gb.phase == GameBoard.Phase.PLAYER) {
			if (gb.state == GameBoard.State.SELECT_DESTINATION || gb.state == GameBoard.State.SELECT_UNIT) {
				Unit uoi = null;
				if (gb.isUnitOn(gb.map.marked) && gb.state == GameBoard.State.SELECT_DESTINATION) {
					uoi = gb.unitOn(gb.map.marked);
				}
				if (gb.isUnitOn(gb.map.cursor) &&gb.state == GameBoard.State.SELECT_UNIT ) {
					uoi = gb.unitOn(gb.map.cursor);
				}
				if (uoi == null) {
					return;
				}
				Coord[] blue = gb.canMoveMapVisual(uoi);
				Coord t;
				g.setColor(new Color(20, 20, 250, 90));
				for (Coord spot : blue) {
					t = gb.map.getRelativeCoord(spot);
					Coord[] cs = getShape(t);
					if (t.y >= 0 && t.y < gb.map.SCREEN_HEIGHT && t.x >= 0 && t.x < gb.map.SCREEN_WIDTH) {
						g.fillPolygon(new int[]{cs[0].x,  cs[1].x,  cs[2].x, cs[3].x}, 
								new int[]{cs[0].y,  cs[1].y,  cs[2].y, cs[3].y}, 4);
					}
				}
				
				Coord[] red = gb.canAttackMap(uoi);
				g.setColor(new Color(250, 20, 20, 90));
				for (Coord spot : red) {
					t = gb.map.getRelativeCoord(spot);
					Coord[] cs = getShape(t);
					if (t.y >= 0 && t.y < gb.map.SCREEN_HEIGHT && t.x >= 0 && t.x < gb.map.SCREEN_WIDTH) {
						g.fillPolygon(new int[]{cs[0].x,  cs[1].x,  cs[2].x, cs[3].x}, 
								new int[]{cs[0].y,  cs[1].y,  cs[2].y, cs[3].y}, 4);
					}
				}
			}
			if (gb.state == GameBoard.State.SELECT_ACTION) {
				if (gb.isUnitOn(gb.map.cursor)) {
					Unit uoi = gb.unitOn(gb.map.cursor);
					Coord[] red = gb.webOut(new Coord[]{uoi.position}, uoi.job.range);
					Coord t;
					g.setColor(new Color(250, 20, 20, 90));
					for (Coord spot : red) {
						t = gb.map.getRelativeCoord(spot);
						Coord[] cs = getShape(t);
						if (t.y >= 0 && t.y < gb.map.SCREEN_HEIGHT && t.x >= 0 && t.x < gb.map.SCREEN_WIDTH) {
							g.fillPolygon(new int[]{cs[0].x,  cs[1].x,  cs[2].x, cs[3].x}, 
									new int[]{cs[0].y,  cs[1].y,  cs[2].y, cs[3].y}, 4);
						}
					}
				}
			}
		}
	}
	
	private void drawActionMenu(Graphics g) {
		if (gb.getPlayer(gb.phase).getClass() == new HumanPlayer().getClass()) {
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
	
	private void drawHealthBar(Graphics g, Unit u) {
		Coord relPos = gb.map.getRelativeCoord(u.position);
		relPos = getShape(relPos)[0];

		
		g.setColor(Color.WHITE);
		g.fillRoundRect(relPos.x + u.offSet.x - 1 + 4, relPos.y  + u.offSet.y - 2 - 3, 25, 6, 3, 3);
		g.setColor(Color.BLACK);
		g.fillRoundRect(relPos.x  + u.offSet.x + 4, relPos.y  + u.offSet.y - 1 - 3, 23, 4, 3, 3);
		g.setColor(Color.BLUE);
		g.fillRoundRect(relPos.x  + u.offSet.x + 4, relPos.y  + u.offSet.y - 1 - 3,
				23, 4, 3, 3);
		
		
		g.setColor(Color.WHITE);
		g.fillRoundRect(relPos.x  + u.offSet.x + 1, relPos.y  -5 + u.offSet.y - 1 - 3, 42, 7, 3, 3);
		g.setColor(Color.BLACK);
		g.fillRoundRect(relPos.x  + u.offSet.x + 2, relPos.y  -5 + u.offSet.y - 3, 40, 5, 3, 3);
		g.setColor(u.getHealthColor());
		g.fillRoundRect(relPos.x  + u.offSet.x + 2, relPos.y  -5 + u.offSet.y - 3,
				(int) Math.floor(40 * ((double)u.currHP / (double)u.maxHP)), 5, 3, 3);
		
	}
	
    private void drawUnitInfo(Graphics g) {
        if (gb.getPlayer(gb.phase).getClass() == new HumanPlayer().getClass()) {
            if (gb.state == GameBoard.State.SELECT_UNIT) {
                if (gb.isUnitOn(gb.map.cursor)) {
                    Unit uoi = gb.unitOn(gb.map.cursor);
                    int left = 0;
                    int top = (gb.map.SCREEN_HEIGHT) *H;
                    g.setColor(new Color(250, 250, 250, 120));
                    g.fillRect(left, top, 140, 70);
                    g.setColor(Color.BLACK);
                    g.drawString(uoi.job.name(), left + 8, top + 20);
                    g.drawString(uoi.currHP + "/" + uoi.maxHP, left + 8, top + 40);
                    g.drawString("STR: " + uoi.str, left + 90, top + 20);
                    g.drawString("DEF: " + uoi.def, left + 90, top + 40);
                    g.drawString("SPD: " + uoi.speed, left + 90, top + 60);
                }

            }
        }
    }
	
    private void drawTerrainInfo(Graphics g) {
        int top = (gb.map.SCREEN_HEIGHT) *H;
        int w = 80;
        g.setColor(new Color(250, 250, 250, 120));
        g.fillRect((gb.map.SCREEN_WIDTH)*W - w, top, w, 70);
        g.setColor(Color.BLACK);
        g.drawString(gb.map.getCell(gb.map.cursor).type.toString(), (gb.map.SCREEN_WIDTH)*W - w + 8, top + 20);
        g.drawString("AVD: " + gb.map.getCell(gb.map.cursor).type.avoid, (gb.map.SCREEN_WIDTH)*W - w + 8, top + 40);
        g.drawString("DEF: " + gb.map.getCell(gb.map.cursor).type.defense, (gb.map.SCREEN_WIDTH)*W - w + 8, top + 60);

    }
}
