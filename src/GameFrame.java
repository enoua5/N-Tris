import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
public class GameFrame extends JFrame implements KeyListener
{
    public static final int width=500;
    public static final int height=700;
    
    public Game c;
    JLabel label;
    public GameFrame(int x, int y)
    {
        super("N-TRIS");
        c=new Game(width, height);
        c.setPreferredSize(new Dimension(width, height));
        this.add(c);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setResizable(false);
        this.pack();
        this.setLocation(x,y);
        this.setVisible(true);
    }
    // https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html
    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyPressed(KeyEvent e)
    {
        int k=e.getKeyCode();
        if(k==69 || k==38)//E, or up
            Request.rotateClock=true;
        if(k==81)//Q
            Request.rotateCounter=true;
        if(k==65 || k==37)//A or left
            Request.left=true;
        if(k==68 || k==39)//D or right
            Request.right=true;
        if(k==83 || k==40)//S or down
            Request.soft=true;
        if(k==32)//space
            Request.drop=true;
    }
    @Override
    public void keyReleased(KeyEvent e){}
}
