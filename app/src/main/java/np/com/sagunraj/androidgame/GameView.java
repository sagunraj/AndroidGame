package np.com.sagunraj.androidgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable {
    private Thread gameThread;
    boolean playing = true;
    private Canvas canvas;
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    Context context;
    private Player player;
    List<Star> stars = new ArrayList<>();

    public GameView(MainActivity mainActivity, int x, int y) {
        super(mainActivity);
        surfaceHolder = getHolder();
        paint = new Paint();
        context = mainActivity;
        int num = 100;
        for(int i = 0; i<num; i++){
            Star s = new Star(x, y);
            stars.add(s);
        }
        player = new Player(mainActivity, x, y);
    }

    @Override
    public void run() {
        while(playing){
            draws();
            control();
            update();
        }
    }

    private void update() {
        player.update();
        for(Star s: stars){
            s.update(player.getSpeed());
        }
    }

    private void control() {
        try {
            gameThread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void draws() {
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            paint.setColor(Color.WHITE);
            paint.setTextSize(25);
            paint.setStrokeWidth(4);
            for(Star s:stars){
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(GameView.this);
        gameThread.start();
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            player.stopBoosting();
        }
        else if(event.getAction() == MotionEvent.ACTION_DOWN){
            player.startBoosting();
        }
        return true;
    }
}
