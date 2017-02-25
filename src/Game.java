import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
public class Game extends JPanel implements MouseListener, MouseMotionListener
{
    private int width;
    private int height;
    private boolean graphicsInit;
    
    public Board boardState;
    public boolean blockFalling;
    public int score;
    public int lines;
    public int fallDelay;
    public int untilFall;
    public boolean playing;
    public int gracePeriod;
    public boolean outsideBoard;
    public int level;
    public int linesToLevel;
    public Mino next;
    public Mino falling;
    public boolean restart;
    public Button retry;
    public Game(int w, int h)
    {
        width=w;
        height=h;
        graphicsInit=false;
        
        addMouseMotionListener(this);
        addMouseListener(this);
        
        boardState=new Board(N.n);
        blockFalling=false;
        score=0;
        lines=0;
        fallDelay=40;
        untilFall=fallDelay;
        playing=true;
        outsideBoard=false;
        level=0;
        linesToLevel=2;
        next=new Mino();
        falling=new Mino();
        restart=false;
    }
    private void initGraphics(Graphics g)
    {
        //background
        g.setColor(new Color(0x20, 0x20, 0x20));
        g.fillRect(0,0,width, height);
        //useful variables
        int boardWidth=height/2;
        int boardOffset=width-boardWidth;
        double squareSize=(double)boardWidth/((int)Math.ceil(N.n*2.5));
        int bottompad=(int)Math.ceil(squareSize*((height/squareSize)-(N.n*5)));
        int statWidth=width-boardWidth;
        //seperate board and stats
        g.setColor(new Color(0, 0, 0));
        g.fillRect(boardOffset-1, 0, 2, height);
        //place "blocks" on extra row
        g.setColor(new Color(0x80, 0x80, 0x80));
        g.fillRect(boardOffset, height-bottompad, boardWidth, bottompad);
        //stats
        int FONTSIZE=18;
        g.setFont(new Font("Serif", Font.BOLD, FONTSIZE));
        g.setColor(new Color(0xff, 0xff, 0xff));
        g.drawString("NEXT", (statWidth-(4*FONTSIZE))/2, FONTSIZE*2);
        g.drawString("SCORE", (statWidth-(5*FONTSIZE))/2, FONTSIZE*10);
        //g.drawString("0", FONTSIZE, FONTSIZE*12);
        g.drawString("LINES", (statWidth-(5*FONTSIZE))/2, FONTSIZE*15);
        //g.drawString("0", FONTSIZE, FONTSIZE*17);
        g.drawString("LEVEL", (statWidth-(5*FONTSIZE))/2, FONTSIZE*20);
        //g.drawString("0", FONTSIZE, FONTSIZE*22);
        g.drawString("GOAL", (statWidth-(4*FONTSIZE))/2, FONTSIZE*25);
        //graphicsInit=true;
    }
    public void paint(Graphics g)
    {
        if(!graphicsInit)
            initGraphics(g);
        drawScores(g);
        boardState.draw(g);
        falling.draw(g);
        drawGrid(g);
        next.draw(g, true);
        drawGameOver(g);
    }
    public void drawGameOver(Graphics g)
    {
        if(!playing)
        {
            int boardWidth=height/2;
            int boardOffset=width-boardWidth;
            g.setFont(new Font("Serif", Font.BOLD, 36));
            g.setColor(new Color(255,255,255));
            g.drawString("Game Over", boardOffset+((boardWidth-(9*27))/2)+10, height/2);
            retry.draw(g);
        }
    }
    public void drawGrid(Graphics g)
    {
        int boardWidth=height/2;
        int boardOffset=width-boardWidth;
        double squareSize=(double)boardWidth/((int)Math.ceil(N.n*2.5));
        g.setColor(new Color(0,0,0));
        for(double x=boardOffset; x<width; x+=squareSize)
            g.fillRect((int)x, 0, 1, height);
        for(double y=squareSize; y<height; y+=squareSize)
            g.fillRect(boardOffset, (int)y, boardWidth, 1);
    }
    public void drawScores(Graphics g)
    {
        int FONTSIZE=18;
        g.setFont(new Font("Serif", Font.BOLD, FONTSIZE));
        g.setColor(new Color(0xff, 0xff, 0xff));
        g.drawString(score+"00", FONTSIZE, FONTSIZE*12);
        g.drawString(lines+"", FONTSIZE, FONTSIZE*17);
        g.drawString(level+"", FONTSIZE, FONTSIZE*22);
        g.drawString((linesToLevel-lines)+"", FONTSIZE, FONTSIZE*27);
    }
    public void levelUp()
    {
        while(lines>=linesToLevel)
        {
            level++;
            fallDelay-=4;
            if(fallDelay<0)
                fallDelay=0;
            linesToLevel*=2;
        }
    }
    public boolean canMoveDown()
    {
        for(int x=falling.x; x<falling.x+falling.piece[falling.rot].length; x++)
            for(int y=falling.y; y<falling.y+falling.piece[falling.rot][x-falling.x].length; y++)
                if(y+1<N.n*6)
                {
                    if(falling.piece[falling.rot][x-falling.x][y-falling.y].solid)
                        if(boardState.board[x][y+1].solid)
                            return false;
                }
                else if(falling.piece[falling.rot][x-falling.x][y-falling.y].solid)
                    return false;
        return true;
    }
    public boolean checkOverlap()
    {
        for(int x=falling.x; x<falling.x+falling.piece[0].length; x++)
            for(int y=falling.y; y<falling.y+falling.piece[0].length; y++)
                if(falling.piece[falling.rot][x-falling.x][y-falling.y].solid)
                {
                    if(x<0)
                        return true;
                    if(x>=(int)Math.ceil(N.n*2.5))
                        return true;
                    if(y>=N.n*6)
                        return true;
                    if(boardState.board[x][y].solid)
                        return true;
                }
        return false;
    }
    public void gameLoop()
    {
        if(playing)
        {
            boolean preventDefault=false;
            if(Request.rotateClock)
            {
                int spins=0;
                do
                {
                    spins++;
                    falling.rot++;
                    if(falling.rot==4)
                        falling.rot=0;
                } while(checkOverlap()&&spins<4);
                Request.rotateClock=false;
            }
            if(Request.rotateCounter)
            {
                int spins=0;
                do
                {
                    spins++;
                    falling.rot--;
                    if(falling.rot==-1)
                        falling.rot=3;
                } while(checkOverlap()&&spins<4);
                Request.rotateCounter=false;
            }
            if(Request.left)
            {
                falling.x--;
                if(checkOverlap())
                    falling.x++;
                else
                    preventDefault=true;
                Request.left=false;
            }
            if(Request.right)
            {
                falling.x++;
                if(checkOverlap())
                    falling.x--;
                else
                    preventDefault=true;
                Request.right=false;
            }
            if(Request.soft)
            {
                if(canMoveDown())
                {
                    falling.y++;
                    untilFall=fallDelay;
                }
                Request.soft=false;
            }
            if(Request.drop)
            {
                while(canMoveDown())
                    falling.y++;
                gracePeriod=2;
                Request.drop=false;
            }
            if(!preventDefault)
            {
                if(!blockFalling)
                {
                    falling=next;
                    next=new Mino();
                    boardState.clearLines();
                    blockFalling=true;
                }
                else
                {
                    if(canMoveDown())
                    {
                        untilFall--;
                        if(untilFall<=0)
                        {
                            falling.y++;
                            untilFall=fallDelay;
                        }
                    }
                    else
                    {
                        boolean overTop=false;
                        for(int x=0; x<boardState.board.length; x++)
                            if(boardState.board[x][N.n-1].solid)
                                overTop=true;
                        if(overTop)
                        {
                            int boardWidth=height/2;
                            int boardOffset=width-boardWidth;
                            playing=false;
                            retry=new Button(boardOffset+(boardWidth-100)/2, (int)(height/1.9),
                                100, 50, "Quit", new int[]{255, 0, 0});
                        }
                        if(gracePeriod==0)
                            gracePeriod=52;
                        gracePeriod--;
                        if(gracePeriod==1)
                        {
                            for(int x=falling.x; x<falling.x+falling.piece[0].length; x++)
                            for(int y=falling.y; y<falling.y+falling.piece[0].length; y++)
                            if(falling.piece[falling.rot][x-falling.x][y-falling.y].solid)
                            boardState.board[x][y]=falling.piece[falling.rot][x-falling.x][y-falling.y];
                            blockFalling=false;
                            gracePeriod=0;
                        }
                    }
                }
            }
        }
        Main.game.pack();
        repaint();
    }
    
    // https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html
    @Override
    public void mousePressed(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}
    @Override
    public void mouseClicked(MouseEvent e)
    {
        int x=e.getX();
        int y=e.getY();
        if(!playing)
        if(retry.overlap(x,y))
        {
            restart=true;
            Main.menu.c.started=false;
            Main.game.setVisible(false);
            Main.menu.setVisible(true);
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e)
    {
        int x=e.getX();
        int y=e.getY();
        if(!playing)
        retry.overlap(x,y);
    }
    @Override
    public void mouseDragged(MouseEvent e){}
}