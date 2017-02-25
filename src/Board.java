import java.awt.*;
public class Board
{
    public Square[][] board;
    public Board(int n)
    {
        //board has extra n squares to hold piece before it comes into view
        board=new Square[(int)Math.ceil((double)n*2.5)][n*6];
        for(int x=0; x<board.length; x++)
            for(int y=0; y<board[0].length; y++)
                board[x][y]=new Square();
    }
    public void draw(Graphics g)
    {
        //useful variables
        int height=Main.game.height;
        int width=Main.game.width;
        int boardWidth=height/2;
        int boardOffset=width-boardWidth;
        double squareSize=(double)boardWidth/((int)Math.ceil(N.n*2.5));
        for(int x=0; x<board.length; x++)
            for(int y=0; y<board[0].length; y++)
                if(board[x][y].solid)
                {
                    g.setColor(board[x][y].color);
                    int drawX=boardOffset+(int)(squareSize*x);
                    int drawY=(int)(squareSize*(y-N.n));
                    g.fillRect(drawX, drawY,
                        (int)squareSize, (int)squareSize);
                }
    }
    public void clearLines()
    {
        Game game=Main.game.c;
        int bounty=1;
        for(int y=0; y<board[0].length; y++)
        {
            boolean cleared=true;
            for(int x=0; x<board.length; x++)
                if(!board[x][y].solid)
                    cleared=false;
            if(cleared)
            {
                game.score+=bounty;
                game.lines++;
                bounty++;
                for(int n=y; n>0; n--)
                    for(int s=0; s<board.length; s++)
                        board[s][n]=board[s][n-1];
                y--;
                game.levelUp();
            }
        }
    }
}
