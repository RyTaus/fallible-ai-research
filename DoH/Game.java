import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;




public class Game extends JPanel{
	public static void main(String[] args) {
		
		Unit p1 = new Unit(new Coord(2, 1), 3, 20, 8, 11, 5, Class.Gunner, TeamType.PLAYER);
		Unit p2 = new Unit(new Coord(1, 3), 5, 22, 4, 11, 5, Class.Swordsman, TeamType.PLAYER);
		
		Unit e1 = new Unit(new Coord(6, 2), 3, 12, 8, 11, 2, Class.Swordsman, TeamType.ENEMY);
		Unit e2 = new Unit(new Coord(5, 2), 5, 13, 6, 11, 6, Class.Gunner, TeamType.ENEMY);
		
		Team p = new Team(new Unit[]{p1, p2}, TeamType.PLAYER);
		Team e = new Team(new Unit[]{e1, e2}, TeamType.ENEMY);
		
		
		Gene gene = new Gene();
		
		GameBoard b = new GameBoard(new Map("src/test"), p, e, new HumanPlayer(), new EnemyPlayer());
		Game g = new Game(b);
		g.playGame(true);
		

	}
	
	public void playGame(boolean visual) {
		Global.visual = visual;
	    JFrame application = new JFrame();                            // the program itself
	    application.setBackground(Color.DARK_GRAY);
	    application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // set frame to exit
	                                                                  // when it is closed
	    application.add(this);           
	
	
	    application.setSize(500, 400);         // window is 500 pixels wide, 400 high
	    application.setVisible(true); 

		PlayerInput pi = new PlayerInput();
		this.addKeyListener(pi);
        
        final Timer timer = new Timer(1000/60, null);
		ActionListener listener = new ActionListener() {
		    @Override 
		    public void actionPerformed(ActionEvent e) {
		    	pi.addPressed();
		        if (update(pi)) {
		        	application.setVisible(false);
		        	application.dispose();
		        	System.out.println(gb.toString());
		        	isDone = true;
		        	timer.stop();

		        }
		        pi.reset();
		    }
		};
		timer.addActionListener(listener);
		timer.start();
//		while(true) {
//	    	pi.addPressed();
//	        if (update(pi)) {
//	        	application.setVisible(false);
//	        	application.dispose();
//	        	break;
//	        }
//	        pi.reset();
//	        
//		}
		while (!isDone) {
			System.out.println(isDone);
		}
	}
	
	public boolean update(PlayerInput pi) {
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
					gb.selectUnit(p.getUnit());
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

		if (gb.phase == GameBoard.Phase.PLAYER) {
			if (lastCopyTurn != gb.turn) {
				if (copy.equals(gb.toString())) {
					return true;
				}
				copy = gb.toString();
			}
			lastCopyTurn = gb.turn;
		}
		if (Global.visual) {
			repaint();
		}
		return gb.update();
//		System.out.println(gb.state);
	}









	public int W = 40;
	public int H = 40;
	public GameBoard gb;
	public String copy = "qwre";
	public int lastCopyTurn = -1;
	public boolean isDone = false;
	public Game(GameBoard b) {
		super();
		gb = b;
		setLocation(0, 0);
		setSize(480 + 17, 360 + 39);
		setBackground(Color.DARK_GRAY);
		setFocusable(true);
		setVisible(true);
		
	}
	
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		super.paintComponent(g); 
        int width = getWidth();             // width of window in pixels
        int height = getHeight();           // height of window in pixels
		
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawMap(g);
        drawHighlights(g);
        
        drawCursor(g, gb.map.getRelativeCoord(gb.map.cursor));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        drawUnits(g);

        drawActionMenu(g);
        drawUnitInfo(g);
        drawTerrainInfo(g);
	}
	
	private Coord[] getShape(Coord co) {
		Coord[] v = new Coord[4]; // tl, tr, br, bl
		double x = co.x;
		double y = co.y;
		int offset = - 2 * W;
		int offsetY = 0;
		
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
		double yMax = Global.screenHeight;
		double y = yi;
		return (int) ((y * H) - (Math.floor(yMax/ H) * (y)));
	}
	
	private int Xmargin(double y) {
		return (int) (30 * gb.map.height - 1 - y);
	}
	
	private int Xwidth(double yi) {
//		return (int) (W * .5 + (W * .5 * y));
		double yMax = Global.screenHeight;
		double perY = 2;
		double y = yMax - 1 - yi;
		return (int) ((perY * y) - W);
	}
	
	private double X(double x) {
		return (Global.screenHeight/ 2) - x;
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
		for (int col = gb.map.topLeft.x; col < gb.map.topLeft.x + Global.screenWidth; col ++) {
			for (int row = gb.map.topLeft.y; row < gb.map.topLeft.y + Global.screenHeight; row ++) {
				Coord c = new Coord(col, row);
				Coord drawLoc = gb.map.getRelativeCoord(c);
				drawCell(g, gb.map.getCell(c).type.img(), new Coord(drawLoc.x, drawLoc.y)); 
				
			}
	    }
//		g.setColor(Color.magenta);
//		g.fillPolygon(new int[]{10, 6, 44, 40}, new int[]{10, 40, 40, 10}, 4);
	}
	
	private void drawUnits(Graphics g) {
		Coord tl;
		Coord br;
		int x;
		int y;
		int width;
		int height;
		for (Unit u : gb.player.units) {
			tl = gb.map.getRelativeCoord(u.position);
			br = gb.map.getRelativeCoord(u.position).getPointTo(Direction.DOWN).getPointTo(Direction.RIGHT);
			Coord pos = gb.map.getRelativeCoord(u.position);
			if (tl.y >= 0 && tl.y < Global.screenHeight && tl.x >= 0 && tl.x < Global.screenWidth) {
				Coord[] p = getShape(tl);
				tl = getShape(tl)[0];
				x = (p[0].x + p[3].x) / 2;
				y = p[0].y;
				width = (int) (((p[1].x + p[2].x) / 2 - (p[0].x + p[3].x) / 2));
				double ratio = (double)width / u.image().getWidth();
				height = (int) (u.image().getHeight() * ratio);
				g.drawImage(u.image().getScaledInstance(width, height, Image.SCALE_SMOOTH), x + u.offSet.x, y + u.offSet.y, null);
				drawHealthBar(g, u);
			}
		}
		for (Unit u : gb.enemy.units) {
			tl = gb.map.getRelativeCoord(u.position);
			if (tl.y >= 0 && tl.y < Global.screenHeight && tl.x >= 0 && tl.x < Global.screenWidth) {
				tl = getShape(tl)[0];
				g.drawImage(u.image(), tl.x + u.offSet.x, tl.y + u.offSet.y, null);
				drawHealthBar(g, u);
			}
		}
	}
	
	private int min(int a, int b) {
		return (a < b) ? a : b;
	}
	
	private int max(int a, int b) {
		return (a > b) ? a : b;
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
					if (t.y >= 0 && t.y < Global.screenHeight && t.x >= 0 && t.x < Global.screenWidth) {
						g.fillPolygon(new int[]{cs[0].x,  cs[1].x,  cs[2].x, cs[3].x}, 
								new int[]{cs[0].y,  cs[1].y,  cs[2].y, cs[3].y}, 4);
					}
				}
				
				Coord[] red = gb.canAttackMap(uoi);
				g.setColor(new Color(250, 20, 20, 90));
				for (Coord spot : red) {
					t = gb.map.getRelativeCoord(spot);
					Coord[] cs = getShape(t);
					if (t.y >= 0 && t.y < Global.screenHeight && t.x >= 0 && t.x < Global.screenWidth) {
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
						if (t.y >= 0 && t.y < Global.screenHeight && t.x >= 0 && t.x < Global.screenWidth) {
							g.fillPolygon(new int[]{cs[0].x,  cs[1].x,  cs[2].x, cs[3].x}, 
									new int[]{cs[0].y,  cs[1].y,  cs[2].y, cs[3].y}, 4);
						}
					}
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
	
	
    private void drawActionMenu(Graphics g) {
        if (gb.getPlayer(gb.phase).getClass() == new HumanPlayer().getClass()) {
            if (gb.state == GameBoard.State.SELECT_ACTION) {
                int top = (Global.screenHeight) *H;
                int left = H * 4;
                g.setColor(Color.WHITE);
                String[] arr = gb.actionMenu.toStrings();
                g.setColor(Color.BLACK);
                g.fillRect(left, top, 80, 20 * arr.length);
                for (int i = 0; i < arr.length; i ++) {
                    g.setColor(Color.WHITE);
                    if (gb.actionMenu.state == ActionMenu.State.Primary) {
                        if (gb.actionMenu.menu.selected == i) {
                            g.setColor(Color.CYAN);
                        }
                    }
                    g.drawString(arr[i], left + 2, top + 15 + 20*(i));
                }  
            }
        }
    }
    
    private void drawUnitInfo(Graphics g) {
        if (gb.getPlayer(gb.phase).getClass() == new HumanPlayer().getClass()) {
            if (gb.state == GameBoard.State.SELECT_UNIT || gb.state == GameBoard.State.SELECT_DESTINATION) {
            	Unit uoi = null;
                if (gb.isUnitOn(gb.map.cursor) && gb.state == GameBoard.State.SELECT_UNIT) {
                    uoi = gb.unitOn(gb.map.cursor);
                }  else {
                	uoi = gb.unitOn(gb.map.marked);
                }
                if (uoi == null) {
                	return;
                }
                    int left = 0;
                    int top = (Global.screenHeight) *H;
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
	
    private void drawTerrainInfo(Graphics g) {
        int top = (Global.screenHeight) *H;
        int w = 80;
        g.setColor(new Color(250, 250, 250, 120));
        g.fillRect((Global.screenWidth)*W - w, top, w, 70);
        g.setColor(Color.BLACK);
        g.drawString(gb.map.getCell(gb.map.cursor).type.toString(), (Global.screenWidth)*W - w + 8, top + 20);
        g.drawString("AVD: " + gb.map.getCell(gb.map.cursor).type.avoid, (Global.screenWidth)*W - w + 8, top + 40);
        g.drawString("DEF: " + gb.map.getCell(gb.map.cursor).type.defense, (Global.screenWidth)*W - w + 8, top + 60);

    }
}
