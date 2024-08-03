package mainjava;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
class GamePanel extends JPanel implements ActionListner, KeyListener{
    
    //body position
    public int[] snakexlenght=new int[750];//array//store body pos
    public int[] snakeylength=new int[750];
    private  int lengthofsnake=3;//initial length
    
     
    private int[] xpos={25,50,75,100,125,150,175,200,225,250,275,300,325,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] ypos={75,100,125,150,175,200,225,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625 };//enemy pos
    
    private Random random=new Random();
    private int enemyX,enemyY;
    
    
    //snake direction
    private boolean left=false ;
    private boolean right=true;
    private boolean up=false;
    private boolean down=false;
    private int moves=0;//intial stage
    private int score=0;
    private boolean gameOver=false;
    
    private ImageIcon snaketitle=new ImageIcon( getClass().getResource("titlee.jpg"));
    private ImageIcon leftmouth=new ImageIcon( getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth=new ImageIcon( getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth=new ImageIcon( getClass().getResource("upmouth.png"));
    private ImageIcon downmouth=new ImageIcon( getClass().getResource("downmouth.png"));
    private ImageIcon snakeimage=new ImageIcon( getClass().getResource("snakeimage.png"));
    private ImageIcon enemy=new ImageIcon( getClass().getResource("Apples .png"));

    //timer class 
    private Timer timer;
    private int delay=100;
        
    // constructor
    GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        
        timer = new Timer(delay, new ActionListener() {
            @Override
            //change snake head pos after 100ms
            public void actionPerformed(ActionEvent e) {
                for(int i=lengthofsnake-1;i>0;i--){
                    snakexlenght[i]=snakexlenght[i-1];
                    snakeylength[i]=snakeylength[i-1];
                }
                if(left){
                    snakexlenght[0]=snakexlenght[0]-25;
                }
        if(right){
            snakexlenght[0]=snakexlenght[0]+25;
        }
        
        if(up)
        {
            snakeylength[0]=snakeylength[0]-25;
        }
        if(down)
        {
            snakeylength[0]=snakeylength[0]+25;
        }
        
        if(snakexlenght[0]>850)snakexlenght[0]=25;
        if(snakexlenght[0]<25)snakexlenght[0]=850;
         
        if(snakeylength[0]>625)snakeylength[0]=75;
        if(snakeylength[0]<75)snakeylength[0]=625;
        
        collidesWithEnemy();
        collidesWithBody();
        repaint();
            }
        });
        timer.start();
        // for set enemy position
        newEnemy();
    }
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);   // to change body of generated methods, choose tools | templates
        //border of title image
        g.setColor(Color.white);//border
        g.drawRect(24, 10,851,55 );
        g.drawRect(24,74,851,576);   
        snaketitle.paintIcon(this, g, 25, 11);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(25,75,850,575);
        //intial stage of snake
        if(moves==0)
        { 
            //x pos head
             snakexlenght[0]=100;
             snakexlenght[1]=75;
             snakexlenght[2]=50;
             //y pos head
              snakeylength[0]=100;
              snakeylength[1]=100;
              snakeylength[2]=100;
        }
        //draw a mouth of snake
        if(left)
        {
            leftmouth.paintIcon(this, g, snakexlenght[0],snakeylength[0]);
            
        }
         if(right)
        {
            rightmouth.paintIcon(this, g, snakexlenght[0],snakeylength[0]);
            
        }
          if(up)
        {
            upmouth.paintIcon(this, g, snakexlenght[0],snakeylength[0]);
            
        }
            if(down)
        {
           downmouth.paintIcon(this, g, snakexlenght[0],snakeylength[0]);
            
        }
            //for create snake body
            for(int i=1;i<lengthofsnake;i++)
            {
                snakeimage.paintIcon(this, g, snakexlenght[i],snakeylength[i]);
            }
            enemy.paintIcon(this, g, enemyX, enemyY);
            if(gameOver)
            {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial",Font.BOLD,50));
                g.drawString("Game Over",300, 300);
                
                g.setFont(new Font("Arial",Font.PLAIN,20));
                g.drawString("Press SPACE to Restart",320, 350);
            }
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN,14));
            g.drawString("Score :"+score, 750, 30);
            g.drawString("Length :"+lengthofsnake, 750, 50);

            g.dispose();
   
    }    

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            restart();
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right))
        {
            left=true;
            right=false;
            up=false;
            down=false;
            moves++;
        }
         if(e.getKeyCode()==KeyEvent.VK_RIGHT &&(!left))
        {
            left=false;
            right=true;
            up=false;
            down=false;
            moves++;
        }
         if(e.getKeyCode()==KeyEvent.VK_UP && (!down))
        {
            left=false;
            right=false;
            up=true;
            down=false;
            moves++;
        }
         if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up))
        {
            left=false;
            right=false;
            up=false;
            down=true;
            moves++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void newEnemy() {
        enemyX=xpos[random.nextInt(32)];
        enemyY=ypos[random.nextInt(21)];
        
        for(int i=lengthofsnake-1;i>=0;i--)
        {
          if(snakexlenght[i]==enemyX && snakeylength[i]==enemyY)
          {
              newEnemy();
          }

        }
        
        
    }
    private void collidesWithEnemy()
    {
        if(snakexlenght[0]==enemyX && snakeylength[0]==enemyY)
        {
            newEnemy();
            lengthofsnake++;
            score++;
        }
    }
    private void collidesWithBody()
    {
        for(int i=lengthofsnake-1;i>0;i--)
        {
            if(snakexlenght[i]==snakexlenght[0] && snakeylength[i]==snakeylength[0])
            {
                timer.stop();
                gameOver=true;
            }
               
        }
    }
     private void restart()
     {
         gameOver=false;
         moves=0;
         score=0;
         lengthofsnake=3;
         left=false;
         right=true;
         up=false;
         down=false;
         timer.start();
         repaint();
     }
    }
