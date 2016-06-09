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
//          p.makeMove(gb, gb.getTeam(gb.phase));
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
//      System.out.println(gb.state);
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
        drawHighlights(g);
        
        drawCursor(g, gb.map.getRelativeCoord(gb.map.cursor));
        drawUnits(g);

        drawActionMenu(g);
        drawUnitInfo(g);
        drawTerrainInfo(g);
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
        for (int col = gb.map.topLeft.x; col < gb.map.topLeft.x + gb.map.SCREEN_WIDTH; col ++) {
            for (int row = gb.map.topLeft.y; row < gb.map.topLeft.y + gb.map.SCREEN_HEIGHT; row ++) {
                Coord c = new Coord(col, row);
                Coord drawLoc = gb.map.getRelativeCoord(c);
                drawCell(g, gb.map.getCell(c).type.img(), W * drawLoc.x, H * drawLoc.y); 
            }
        }
    }
    
    private void drawUnits(Graphics g) {
        Coord relPos;
        for (Unit u : gb.player.units) {
            relPos = gb.map.getRelativeCoord(u.position);
            if (relPos.y >= 0 && relPos.y < gb.map.SCREEN_HEIGHT && relPos.x >= 0 && relPos.x < gb.map.SCREEN_WIDTH) {
                g.drawImage(u.image(), relPos.x * W + 4 + u.offSet.x, relPos.y * H + 4 + u.offSet.y, null);
                drawHealthBar(g, u);
            }
        }
        for (Unit u : gb.enemy.units) {
            relPos = gb.map.getRelativeCoord(u.position);
            if (relPos.y >= 0 && relPos.y < gb.map.SCREEN_HEIGHT && relPos.x >= 0 && relPos.x < gb.map.SCREEN_WIDTH) {
                g.drawImage(u.image(), relPos.x * W + 4 + u.offSet.x, relPos.y * H + 4 + u.offSet.y, null);
                drawHealthBar(g, u);
            }
        }
    }
    
    private void drawHighlights(Graphics g) {
        if (gb.phase == GameBoard.Phase.PLAYER) {
            if (gb.state == GameBoard.State.SELECT_DESTINATION) {
                if (gb.isUnitOn(gb.map.marked)) {
                    Unit uoi = gb.unitOn(gb.map.marked);
                    Coord[] blue = gb.canMoveMapVisual(uoi);
                    Coord t;
                    g.setColor(new Color(20, 20, 250, 90));
                    for (Coord spot : blue) {
                        t = gb.map.getRelativeCoord(spot);
                        if (t.y >= 0 && t.y < gb.map.SCREEN_HEIGHT && t.x >= 0 && t.x < gb.map.SCREEN_WIDTH) {
                            g.fillRect(t.x * W, t.y * H, W - 1, H - 1);
                        }
                    }
                    
                    Coord[] red = gb.canAttackMap(uoi);
                    g.setColor(new Color(250, 20, 20, 90));
                    for (Coord spot : red) {
                        t = gb.map.getRelativeCoord(spot);
                        if (t.y >= 0 && t.y < gb.map.SCREEN_HEIGHT && t.x >= 0 && t.x < gb.map.SCREEN_WIDTH) {
                            g.fillRect(t.x * W, t.y * H, W - 1, H - 1);
                        }
                    }
                }
            }
            if (gb.state == GameBoard.State.SELECT_UNIT) {
                if (gb.isUnitOn(gb.map.cursor)) {
                    Unit uoi = gb.unitOn(gb.map.cursor);
                    Coord[] blue = gb.canMoveMapVisual(uoi);
                    Coord t;
                    g.setColor(new Color(20, 20, 250, 90));
                    for (Coord spot : blue) {
                        t = gb.map.getRelativeCoord(spot);
                        if (t.y >= 0 && t.y < gb.map.SCREEN_HEIGHT && t.x >= 0 && t.x < gb.map.SCREEN_WIDTH) {
                            g.fillRect(t.x * W, t.y * H, W - 1, H - 1);
                        }
                    }
                    
                    Coord[] red = gb.canAttackMap(uoi);
                    g.setColor(new Color(250, 20, 20, 90));
                    for (Coord spot : red) {
                        t = gb.map.getRelativeCoord(spot);
                        if (t.y >= 0 && t.y < gb.map.SCREEN_HEIGHT && t.x >= 0 && t.x < gb.map.SCREEN_WIDTH) {
                            g.fillRect(t.x * W, t.y * H, W - 1, H - 1);
                        }
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
                        if (t.y >= 0 && t.y < gb.map.SCREEN_HEIGHT && t.x >= 0 && t.x < gb.map.SCREEN_WIDTH) {
                            g.fillRect(t.x * W, t.y * H, W - 1, H - 1);
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
        g.setColor(Color.BLACK);
        g.fillRoundRect(relPos.x * W + u.offSet.x, relPos.y * H -5 + u.offSet.y, 40, 5, 3, 3);
        g.setColor(u.getHealthColor());
        g.fillRoundRect(relPos.x * W + u.offSet.x, relPos.y * H -5 + u.offSet.y,
                (int) Math.floor(40 * ((float)u.currHP / (float)u.maxHP)), 5, 3, 3);
    }
    
    private void drawUnitInfo(Graphics g) {
        if (gb.getPlayer(gb.phase).getClass() == new HumanPlayer().getClass()) {
            if (gb.state == GameBoard.State.SELECT_UNIT) {
                if (gb.isUnitOn(gb.map.cursor)) {
                    Unit uoi = gb.unitOn(gb.map.cursor);
                    int left = 0;
                    int top = (gb.map.height -1) *H;
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
        int top = (gb.map.height -1) *H;
        g.setColor(new Color(250, 250, 250, 120));
        g.fillRect(170, top, (gb.map.width-1)*W - 170, 70);
        g.setColor(Color.BLACK);
        g.drawString(gb.map.getCell(gb.map.cursor).type.toString(), 178, top + 20);
        g.drawString("Avoid Bonus: " + gb.map.getCell(gb.map.cursor).type.avoid, 178, top + 40);
        g.drawString("Defense Bonus: " + gb.map.getCell(gb.map.cursor).type.defense, 178, top + 60);

    }
}
