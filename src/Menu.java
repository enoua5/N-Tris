import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
public class Menu extends JPanel implements MouseListener, MouseMotionListener
{
    public boolean started;
    public Button start;
    public Button minus;
    public Button plus;
    public Menu()
    {
        started=false;
        minus=new Button(150, 250, 100, 50, "-", new int[]{255, 0, 0});
        plus=new Button(250, 250, 100, 50, "+", new int[]{255, 0, 0});
        start=new Button(150, 300, 200, 50, "Start", new int[]{0, 255, 0});
        addMouseMotionListener(this);
        addMouseListener(this);
    }
    public void paint(Graphics g)
    {
        g.setColor(new Color(0x40, 0x40, 0x40));
        g.fillRect(0,0,500,500);
        g.setColor(new Color(0xff, 0xff, 0xff));
        int FONTSIZE=36;
        g.setFont(new Font("Serif", Font.BOLD, FONTSIZE));
        String title=N.n+"-Block Tetris";
        g.drawString(title,
            (500-(title.length()*(FONTSIZE/2)))/2, 100);
        minus.draw(g);
        plus.draw(g);
        start.draw(g);
    }
    private void start()
    {
        Main.menu.setVisible(false);
        Main.game=new GameFrame(Main.menu.getLocation().x, Main.menu.getLocation().y);
        started=true;
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
        if(start.overlap(x,y))
            start();
        if(minus.overlap(x,y))
            if(N.n>1)
                N.n--;
        if(plus.overlap(x,y))
            if(N.n<50)
                N.n++;
    }
    
    @Override
    public void mouseMoved(MouseEvent e)
    {
        int x=e.getX();
        int y=e.getY();
        start.overlap(x,y);
        minus.overlap(x,y);
        plus.overlap(x,y);
    }
    @Override
    public void mouseDragged(MouseEvent e){}
}