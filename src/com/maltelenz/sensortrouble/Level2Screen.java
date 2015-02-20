package com.maltelenz.sensortrouble;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;

import com.maltelenz.framework.Game;
import com.maltelenz.framework.Graphics;
import com.maltelenz.framework.Input.TouchEvent;
import com.maltelenz.framework.Screen;

public class Level2Screen extends LevelScreen {

    float touchLength = 500;
    float timeLeft = touchLength;
    
    int touching = 0;
    
    int circleRadius = 300;

    public Level2Screen(Game game) {
        super(game);
        state = GameState.Running;
    }

    @Override
    protected void updateGameRunning(List<TouchEvent> touchEvents, float deltaTime) {
        
        if (touching != 0) {
            timeLeft -= deltaTime;
        }
        
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);

            if (event.type == TouchEvent.TOUCH_DOWN) {
                touching++;
            }
            if (event.type == TouchEvent.TOUCH_UP) {
                touching--;
            }
        }
        if (timeLeft < 0) {
            state = GameState.Finished;
        }
    }

    @Override
    float percentDone() {
        return (touchLength - timeLeft)/touchLength;
    }

    @Override
    int levelsDone() {
        return 1;
    }

    void drawRunningUI() {
        Graphics g = game.getGraphics();
        g.clearScreen(ColorPalette.background);

        Paint circlePainter = new Paint();
        circlePainter.setColor(ColorPalette.cherry);
        circlePainter.setStyle(Style.STROKE);
        circlePainter.setStrokeWidth(5);
        g.drawCircle(g.getWidth()/2, g.getHeight()/2, circleRadius, circlePainter);

        Paint largePainter = new Paint();
        largePainter.setTextSize(150);
        largePainter.setTextAlign(Paint.Align.CENTER);
        largePainter.setAntiAlias(true);
        largePainter.setColor(Color.WHITE);
        largePainter.setTypeface(Typeface.DEFAULT_BOLD);
        g.drawStringCentered(String.format("%.1f", timeLeft), largePainter);
    }

    @Override
    protected Screen nextLevel() {
        return (new Level3Screen(game));
    }
}