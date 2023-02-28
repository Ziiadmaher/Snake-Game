import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Graphics extends JPanel implements ActionListener {

    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    static final int TICK_SIZE = 30;
    static final int BOARD_SIZE = (WIDTH * HEIGHT) / (TICK_SIZE * TICK_SIZE);
    final Font font = new Font("TimesRoman", Font.BOLD,20);

    int[] snakePosX = new int[BOARD_SIZE];
    int[] snakePosY = new int[BOARD_SIZE];
    int snakeLength;
    Food food;
    int foodEaten;
    char direction = 'R';
    boolean isMoving = false;
    final Timer timer = new Timer(100,this);



     public Graphics() {
         this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
         this.setBackground(Color.DARK_GRAY);
         this.setFocusable(true);
         this.addKeyListener(new KeyAdapter() {
             @Override
             public void keyPressed(KeyEvent e) {
                 if (isMoving){
                     switch (e.getKeyCode()){
                         case KeyEvent.VK_A:
                             if (direction != 'R'){
                                 direction = 'L';
                             }
                             break;
                         case KeyEvent.VK_D:
                             if (direction != 'L'){
                                 direction = 'R';
                             }
                             break;
                         case KeyEvent.VK_W:
                             if (direction != 'D'){
                                 direction = 'U';
                             }
                             break;
                         case KeyEvent.VK_S:
                             if (direction != 'U'){
                                 direction = 'D';
                             }
                             break;
                     }
                 } else {
                     start();
                 }
             }
         });
         start();
     }

     protected void start(){
         snakePosX = new int[BOARD_SIZE];
         snakePosY = new int[BOARD_SIZE];
         snakeLength = 5;
         direction = 'R';
         foodEaten = 0;
         isMoving = true;
         timer.start();
         spawnFood();
     }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < HEIGHT/TICK_SIZE; i++) {
            g.drawLine(i*TICK_SIZE,0,i*TICK_SIZE,HEIGHT);
            g.drawLine(0,i*TICK_SIZE,WIDTH,i*TICK_SIZE);

        }

        if (isMoving){
            harder();
            g.setColor(Color.RED);
            g.fillOval(food.getPosX(), food.getPosY(), TICK_SIZE,TICK_SIZE);

            g.setColor(Color.PINK);
            for (int i = 0; i < snakeLength; i++) {
                g.fillRect(snakePosX[i],snakePosY[i],TICK_SIZE,TICK_SIZE);
            }
        } else {
            String scoreText = String.format("You lost!!.. Score: %d. Press any key to play again",foodEaten);
            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString(scoreText,(WIDTH - getFontMetrics(g.getFont()).stringWidth(scoreText)) / 2 ,
                    HEIGHT / 2 );
        }
    }

    protected void harder(){
         if (foodEaten > 5){
             Timer timer1 = new Timer(50,this);

         }
    }

    protected void move(){
         for (int i = snakeLength; i > 0; i--){
             snakePosX[i] = snakePosX[i-1];
             snakePosY[i] = snakePosY[i-1];
         }
         switch (direction){
             case 'U' -> snakePosY[0] -= TICK_SIZE;
             case 'D' -> snakePosY[0] += TICK_SIZE;
             case 'R' -> snakePosX[0] += TICK_SIZE;
             case 'L' -> snakePosX[0] -= TICK_SIZE;
         }
    }
    protected void spawnFood(){
        food = new Food();
         }
    protected void eatFood(){
         if (snakePosX[0] == food.getPosX() && snakePosY[0] == food.getPosY()){
             snakeLength++;
             foodEaten++;
             spawnFood();
         }
    }


    protected void collisionTest(){
        for (int i = snakeLength; i > 0; i--) {
            if ((snakePosX[0] == snakePosX[i] && snakePosY[0] == snakePosY[i])){
                isMoving = false;
                break;
            }
        }

        if (snakePosX[0] < 0 || snakePosX[0] > WIDTH - TICK_SIZE || snakePosY[0] < 0
        || snakePosY[0] > HEIGHT - TICK_SIZE){
            isMoving = false;
        }

        if (!isMoving){
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isMoving){
            move();
        }
        collisionTest();
        repaint();
        eatFood();

    }
}
