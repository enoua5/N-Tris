public class Main
{
    public static final int FRAMERATE=50;
    
    public static MenuFrame menu=new MenuFrame();
    public static GameFrame game;
    public static void main(String[] args)
    throws InterruptedException
    {
        while(!menu.c.started)
        {
            Thread.sleep(1000/FRAMERATE);
            menu.c.repaint();
        }
        while(true)
        {
            Thread.sleep(1000/FRAMERATE);
            game.c.gameLoop();
        }
    }
}
