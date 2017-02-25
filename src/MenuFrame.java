import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
public class MenuFrame extends JFrame implements KeyListener
{
    public Menu c;
    JLabel label;
    public MenuFrame()
    {
        super("N-TRIS");
        c=new Menu();
        this.add(c);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setSize(500,500);
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)(d.getWidth()-500)/2,
            (int)(d.getHeight()-500)/4);
        this.setResizable(false);
        this.setVisible(true);
    }
    // https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html
    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyPressed(KeyEvent e){}
    @Override
    public void keyReleased(KeyEvent e){}
}
