import java.awt.*;
public class Button
{
    public String text;
    public Color hcolor;
    public Color color;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean hovered;
    public Button(int px, int py, int pwidth, int pheight, String ptext, int[] pcolor)
    {
        text=ptext;
        color=new Color(pcolor[0],pcolor[1],pcolor[2]);
        hcolor=new Color(pcolor[0]/2,pcolor[1]/2,pcolor[2]/2);
        x=px;
        y=py;
        width=pwidth;
        height=pheight;
        hovered=false;
    }
    public Color getColor()
    {
        if(hovered)
            return hcolor;
        return color;
    }
    public boolean overlap(int mx, int my)
    {
        if(mx>=x && mx<=(x+width) && my>=y && my<=(y+height))
            return hovered=true;
        return hovered=false;
    }
    public Point textPos()
    {
        int length=text.length();
        int ty=(height/2)+8+y;
        int tx=(width/2)-(5*length)+x;
        return new Point(tx,ty);
    }
    public void draw(Graphics g)
    {
        Font cfont=g.getFont();
        g.setFont(new Font("Serif",Font.BOLD, 20));
        g.setColor(getColor());
        g.fillRect(x,y,width,height);
        g.setColor(new Color(0,0,0));
        g.drawString(text, textPos().x, textPos().y);
        g.setFont(cfont);
    }
}
