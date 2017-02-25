import java.awt.*;
public class Square
{
    public boolean solid;
    public Color color;
    public Square()
    {
        solid=false;
        color=new Color(0,0,0);
    }
    public Square(Color pColor)
    {
        solid=true;
        color=pColor;
    }
    public static Color genFullSat()
    {
        int full=(int)Math.floor(Math.random()*3);
        int sec=full;
        while(sec==full)
            sec=(int)Math.floor(Math.random()*3);
        int vsec=(int)Math.floor(Math.random()*256);
        int[] rgb=new int[3];
        rgb[full]=255;
        rgb[sec]=vsec;
        return new Color(rgb[0], rgb[1], rgb[2]);
    }
}
