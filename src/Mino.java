import java.awt.*;
public class Mino
{
    public Square[][][] piece;
    public int x;
    public int y;
    public int rot;
    public Mino()
    {
        Color color=Square.genFullSat();
        piece=new Square[4][N.n][N.n];
        for(int x=0; x<N.n; x++)
            for(int y=0; y<N.n; y++)
                piece[0][x][y]=new Square();
        int x=N.n/2;
        int y=N.n/2;
        piece[0]=createBlock(N.n, color);
        piece[0]=trim(piece[0]);
        piece[0]=pad(piece[0]);
        piece[1]=rotate(copy(piece[0]));
        piece[2]=rotate(copy(piece[1]));
        piece[3]=rotate(copy(piece[2]));
        this.x=((int)Math.ceil(N.n*2.5)-piece[0].length)/2;
        this.y=N.n-piece[rot][0].length;
        rot=0;
    }
    public void draw(Graphics g)
    {
        int boardWidth=Main.game.height/2;
        int boardOffset=Main.game.width-boardWidth;
        double squareSize=(double)boardWidth/((int)Math.ceil(N.n*2.5));
        for(int i=0; i<piece[rot].length; i++)
            for(int j=0; j<piece[rot][i].length; j++)
            {
                Square sqr=piece[rot][i][j];
                if(sqr.solid)
                {
                    g.setColor(sqr.color);
                    int drawX=boardOffset+
                        (int)(squareSize*(x+i));
                    int drawY=(int)(squareSize*
                        (y+j-N.n));
                    g.fillRect(drawX, drawY,
                        (int)squareSize, (int)squareSize);
                }
            }
    }
    public void draw(Graphics g, boolean inNextBox)
    {
        int boardWidth=Main.game.height/2;
        int boardOffset=Main.game.width-boardWidth;
        double squareSize=(boardOffset-10)/(piece[0].length+1);
        int statWidth=Main.game.width-boardWidth;
        if(inNextBox)
        for(int i=0; i<piece[rot].length; i++)
            for(int j=0; j<piece[rot][i].length; j++)
            {
                Square sqr=piece[rot][i][j];
                if(sqr.solid)
                {
                    g.setColor(new Color(0,0,0));
                    g.fillRect((int)(i*squareSize)+10, (int)(j*squareSize)+42,
                        (int)squareSize, (int)squareSize);
                    g.setColor(sqr.color);
                    g.fillRect((int)(i*squareSize)+11, (int)(j*squareSize)+43,
                        (int)squareSize-2, (int)squareSize-2);
                }
            }
       else draw(g);
    }
    private int[][] add(int[][] array, int[] cords)
    {
        int[][] ret=new int[array.length+1][2];
        for(int i=0; i<array.length; i++)
            ret[i]=array[i];
        ret[array.length]=cords;
        return ret;
    }
    private Square[][] createBlock(int s, Color c)
    {
        if(s==1)
        {
            Square[][] ret=new Square[(N.n*2)+1][(N.n*2)+1];
            for(int x=0; x<ret.length; x++)
                for(int y=0; y<ret[x].length; y++)
                    ret[x][y]=new Square();
            ret[N.n][N.n]=new Square(c);
            return ret;
        }
        else
        {
            Square[][] ret=createBlock(s-1, c);
            int[][] cords=new int[0][2];
            for(int x=0; x<ret.length; x++)
                for(int y=0; y<ret[x].length; y++)
                    if(!ret[x][y].solid)
                    {
                        boolean adj=false;
                        if(x>0)
                            if(ret[x-1][y].solid)
                                adj=true;
                        if(y>0)
                            if(ret[x][y-1].solid)
                                adj=true;
                        if(x<ret.length-1)
                            if(ret[x+1][y].solid)
                                adj=true;
                        if(y<ret[x].length-1)
                            if(ret[x][y+1].solid)
                                adj=true;
                        if(adj)
                            cords=add(cords, new int[]{x, y});
                    }
            int[] loc=cords[(int)(Math.random()*cords.length)];
            ret[loc[0]][loc[1]]=new Square(c);
            return ret;
        }
    }
    private Square[][] pad(Square[][] oldPiece)
    {
        int width=oldPiece.length;
        int height=oldPiece[0].length;
        if(width<height)
        {
            Square[][] newPiece=new Square[height][height];
            int padding=height-width;
            int offset=(height-width)/2;
            int x=0;
            for(x=0; x<offset; x++)
                for(int y=0; y<height; y++)
                    newPiece[x][y]=new Square();
            for(x=x; x<offset+width; x++)
                for(int y=0; y<height; y++)
                    newPiece[x][y]=oldPiece[x-offset][y];
            for(x=x; x<height; x++)
                for(int y=0; y<height; y++)
                    newPiece[x][y]=new Square();
            return newPiece;
        }
        else if(width>height)
        {
            Square[][] newPiece=new Square[width][width];
            int padding=width-height;
            int offset=(width-height)/2;
            int y=0;
            for(y=0; y<offset; y++)
                for(int x=0; x<width; x++)
                    newPiece[x][y]=new Square();
            for(y=y; y<offset+height; y++)
                for(int x=0; x<width; x++)
                    newPiece[x][y]=oldPiece[x][y-offset];
            for(y=y; y<width; y++)
                for(int x=0; x<width; x++)
                    newPiece[x][y]=new Square();
            return newPiece;
        }
        else return oldPiece;
    }
    private Square[][] trim(Square[][] oldPiece)
    {
        int left=oldPiece.length;
        int right=-1;
        int top=oldPiece[0].length;
        int bottom=-1;
        for(int x=0; x<oldPiece.length; x++)
            for(int y=0; y<oldPiece[0].length; y++)
                if(oldPiece[x][y].solid)
                {
                    if(y>bottom) bottom=y;
                    if(x<left) left=x;
                    if(x>right) right=x;
                    if(y<top) top=y;
                }
        Square[][] newPiece=new Square[right-left+1][bottom-top+1];
        for(int x=left; x<=right; x++)
            for(int y=top; y<=bottom; y++)
                newPiece[x-left][y-top]=oldPiece[x][y];
        return newPiece;
    }
    private Square[][] copy(Square[][] oldPiece)
    {
        Square[][] newPiece=new Square[oldPiece.length][oldPiece[0].length];
        for(int i=0; i<oldPiece.length; i++)
            for(int j=0; j<oldPiece[0].length; j++)
                newPiece[i][j]=oldPiece[i][j];
        return newPiece;
    }
    private Square[][] rotate(Square[][] oldPiece)
    {
        Square[][] newpiece=new Square[oldPiece[0].length][oldPiece.length];
        for(int x=0; x<oldPiece.length; x++)
            for(int y=0; y<oldPiece[0].length; y++)
                newpiece[y][x]=oldPiece[x][oldPiece.length-1-y];
        return newpiece;
    }
}