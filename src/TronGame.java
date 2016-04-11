import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.util.ArrayList;

public class TronGame {

    private static final int WIDTH = 1250;
    private static final int HEIGHT = 937;
    private static final double BIKE_SIZE = 8.0;
    private static boolean isRunning = true;
    private static boolean isWatching = true;
    private static long lastFrame;
    private static ArrayList<Bike> bikes;
    private static int gameState = 1;

    public static void main(String[] args) {
        setUpDisplay();
        setUpOpenGL();
        setUpEntities();
        setUpTimer();
        while (isRunning) {
//            switch(gameState) {
//            case 1:
//            	loadMenu();
//            	break;
//            case 2:
//            	playGame();
//            	break;
//            case 3:
//            	endGame();
//            	break;
//            }
        	render();
            logic(getDelta());
            areWinners();
            input();
            Display.update();
            Display.sync(60);
            if (Display.isCloseRequested()) {
                isRunning = false;
                isWatching = false;
            }
        }
        while(isWatching) {
        	if(Display.isCloseRequested()) {
        		isWatching = false;
        	}
        }
        Display.destroy();
        System.exit(1);
    }

    private static void input() {
    	bikes.get(0).drive(Keyboard.isKeyDown(Keyboard.KEY_UP), Keyboard.isKeyDown(Keyboard.KEY_DOWN),
    			Keyboard.isKeyDown(Keyboard.KEY_RIGHT), Keyboard.isKeyDown(Keyboard.KEY_LEFT));
    	bikes.get(1).drive(Keyboard.isKeyDown(Keyboard.KEY_W), Keyboard.isKeyDown(Keyboard.KEY_S),
    			Keyboard.isKeyDown(Keyboard.KEY_D), Keyboard.isKeyDown(Keyboard.KEY_A));
    	bikes.get(2).drive(Keyboard.isKeyDown(Keyboard.KEY_I), Keyboard.isKeyDown(Keyboard.KEY_K),
    			Keyboard.isKeyDown(Keyboard.KEY_L), Keyboard.isKeyDown(Keyboard.KEY_J));
    	bikes.get(3).drive(Keyboard.isKeyDown(Keyboard.KEY_T), Keyboard.isKeyDown(Keyboard.KEY_G),
    			Keyboard.isKeyDown(Keyboard.KEY_H), Keyboard.isKeyDown(Keyboard.KEY_F));
    }

    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private static int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }

    private static void setUpDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle("LightBike");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
    }

    private static void setUpOpenGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
    }

    private static void setUpEntities() {
        bikes = new ArrayList<Bike>(0);
    	bikes.add(new Bike(300, 200, BIKE_SIZE, BIKE_SIZE, 1.0f, 1.0f, 1.0f, 2, "RIGHT"));
        bikes.add(new Bike(300, 700, BIKE_SIZE, BIKE_SIZE, 0.9568f, 0.7882f, 0.1058f, 2, "UP"));
        bikes.add(new Bike(800, 700, BIKE_SIZE, BIKE_SIZE, 0.4784f, 1.0f, 0.9843f, 2, "LEFT"));
        bikes.add(new Bike(800, 200, BIKE_SIZE, BIKE_SIZE, 0.2235f, 1.0f, 0.0784f, 2, "DOWN"));
    }

    private static void setUpTimer() {
        lastFrame = getTime();
    }

    private static void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        for(Bike b: bikes) {
        	b.draw();
        }
    }

    private static void logic(int delta) {
    	for(Bike b: bikes) {
        	b.update(delta);
        }
        eliminateDeadRiders();
    }

	private static void eliminateDeadRiders() {
		for(int i = 0; i < bikes.size(); i++) {
			bikes.get(i).collisionDetection(WIDTH, HEIGHT);
			for(int x = 0; x < bikes.size(); x++) {
				if(i != x && bikes.get(i).isAlive() && bikes.get(x).isAlive()) {
					if(bikes.get(i).collidesWith(bikes.get(x))) {
						bikes.get(i).die();
						bikes.get(x).die();
					}
					bikes.get(i).hitsOtherTrails(bikes.get(x).getLightTrail());
				}
			}
		}
	}
	
	public static void areWinners() {
		int alive = 0;
		for(Bike bike: bikes) {
			if(bike.isAlive()) alive++;
		}
		if(alive < 2) {
			if(alive == 1) {
//				isRunning = false;
//				System.out.println("Winner!!!! Good Job Rider");
			} else {
				isRunning = false;
				System.out.println("No Winners this time");
			}
		}
	}
}