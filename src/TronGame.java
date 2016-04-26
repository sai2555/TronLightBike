import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.util.ArrayList;

public class TronGame {

    private static final int WIDTH = 1250;
    private static final int HEIGHT = 937;
    private static final double BIKE_SIZE = 8.0;
    private static long lastFrame;
    private static ArrayList<Bike> bikes;
    private static int gameState = 1;
    private static Texture texture;

    public static void main(String[] args) {
        setUpDisplay();
        setUpOpenGL();
        while (!Display.isCloseRequested()) {
           switch(gameState) {
	           case 1:
	        	   displayScreen("menuImage");
	        	   setUpEntities();
	               setUpTimer();
	        	   gameState = 2;
	            case 2:
	            	playGame();
	            	break;
	            case 3:
	            	endGame();
	            	break;
            }
        }
       Display.destroy();
       System.exit(1);
    }
    
    public static void displayScreen(String textureName) {
    	try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle("Tron Light Bike");

        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
        try {
            // Load the menuImage texture from "res/images/menuImage.png"
            texture = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/" + textureName + ".png")));
        } catch (IOException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
//        glMatrixMode(GL_PROJECTION);
//        glOrtho(0, 640, 480, 0, 1, -1);
//        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        boolean spaceTapped = false;
        while (!spaceTapped) {
        	if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
     		   Display.destroy();
            	   System.exit(1);
     	    }
     	    if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
     	    	spaceTapped = true;
     	    }
            glClear(GL_COLOR_BUFFER_BIT);
            texture.bind();
//            glBegin(GL_TRIANGLES);
//            glTexCoord2f(1, 0);
//            glVertex2i(450, 10);
//            glTexCoord2f(0, 0);
//            glVertex2i(10, 10);
//            glTexCoord2f(0, 1);
//            glVertex2i(10, 450);
//            glTexCoord2f(0, 1);
//            glVertex2i(10, 450);
//            glTexCoord2f(1, 1);
//            glVertex2i(450, 450);
//            glTexCoord2f(1, 0);
//            glVertex2i(450, 10);
//            glEnd();
                        glBegin(GL_QUADS);
                        glTexCoord2f(0, 0);
                        glVertex2i(0, 0); // Upper-left
                        glTexCoord2f(1, 0);
                        glVertex2i(1850, 0); // Upper-right
                        glTexCoord2f(1, 1);
                        glVertex2i(1850, 1850); // Bottom-right
                        glTexCoord2f(0, 1);
                        glVertex2i(0,1850 ); // Bottom-left
                        glEnd();
            Display.update();
            Display.sync(60);
        }
        // Release the resources of the menuImage texture
        texture.release();
    }
    
	public static void playGame(){
		render();
		logic(getDelta());
		areWinners();
		input();
		Display.update();
		Display.sync(60);
	}
	
	public static void endGame() {
		gameState = 1;
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
    	bikes.get(4).drive(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5), Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2),
    			Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3), Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1));
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
            Display.setTitle("Tron Light Bike");
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
//        try {
//            // Load the wood texture from "res/images/wood.png"
//            wood = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/wood.png")));
//        } catch (IOException e) {
//            e.printStackTrace();
//            Display.destroy();
//            System.exit(1);
//        }
    }

    private static void setUpEntities() {
        bikes = new ArrayList<Bike>(0);
    	bikes.add(new Bike(300, 200, BIKE_SIZE, BIKE_SIZE, 1.0f, 0.6471f, 0.0f, 2, "RIGHT", "Orange"));
        bikes.add(new Bike(300, 700, BIKE_SIZE, BIKE_SIZE, 0.8549f, 1.0f, 0.0f, 2, "UP", "Yellow"));
        bikes.add(new Bike(800, 700, BIKE_SIZE, BIKE_SIZE, 0.4784f, 1.0f, 0.9843f, 2, "LEFT", "Blue"));
        bikes.add(new Bike(800, 200, BIKE_SIZE, BIKE_SIZE, 0.2235f, 1.0f, 0.0784f, 2, "DOWN", "Green"));
        bikes.add(new Bike(500, 500, BIKE_SIZE, BIKE_SIZE, 1.0f, 0.0f, 0.0f, 2, "UP", "Red"));
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
		String winner = "";
		for(Bike bike: bikes) {
			if(bike.isAlive()) {
				alive++;
				winner = bike.getName();
			}
		}
		if(alive < 2) {
			gameState = 3;
			if(alive == 1) {
				displayScreen(winner);
			} else {
				displayScreen("Tie");
			}
			endGame();
		}
	}
}
