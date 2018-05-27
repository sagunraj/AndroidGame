package np.com.sagunraj.androidgame;

import java.util.Random;

public class Star {
    private int starx, stary, maxX, maxY, speed;
    public Star(int x, int y) {
        maxX = x;
        maxY = y;
        Random random = new Random();
        speed = random.nextInt(20);
        starx = random.nextInt(maxX);
        stary = random.nextInt(maxY);
    }

    public int getX() {
        return starx;
    }

    public int getY() {
        return stary;
    }

    public void update(int playerSpeed) {
        starx = starx - playerSpeed - speed;
        if(starx<0){
            starx = maxX;
            Random generator = new Random();
            stary = generator.nextInt(maxY);
            speed = generator.nextInt(20);
        }
    }
}
