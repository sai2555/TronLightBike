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

/**
 * Tron Light Bike Game
 * @author Max Topelson, Sai Maddhi, Leon Shen, Jackson Celich
 *
 */
public class TronGame {
	/**
	 * width of screen
	 */
    private static final int WIDTH = 1920;
    /**
     * height of screen
     */
    private static final int HEIGHT = 1035;
    /**
     * size of bike
     */
    private static final double BIKE_SIZE = 8.0;
    /**
     * last updated frame
     */
    private static long lastFrame;
    /**
     * array of bikes playing in the game
     */
    private static ArrayList<Bike> bikes;
    /**
     * 1 is the menu
     * 2 plays the game
     * 3 goes back to state 1
     */
    private static int gameState = 1;
    /**
     * texture used to display the screen
     */
    private static Texture texture;

    public static void main(String[] args) {
        setUpDisplay(); //sets up display
        setUpOpenGL(); //sets up OpenGL
        while (!Display.isCloseRequested()) { //as long as display is open
           switch(gameState) { //switch statement for the different game states
	           case 1:
	        	   displayScreen("menuImage"); //sets up menu screen
	        	   setUpEntities(); //sets up the bikes from variable "bikes"
	               setUpTimer(); //sets up timer for the game
	        	   gameState = 2; //changes game state to 2 in order to play the game
	            case 2:
	            	playGame(); //plays the tron game
	            	break;
            }
        }
       Display.destroy(); //closes display
       System.exit(1); //ends program
    }
    
    /**
     * displays a picture being used for the current screen
     * @param textureName name of the picture being displayed
     */
    private static void displayScreen(String textureName) {
    	try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); //sets display with width 
            														//WIDTH and height HEIGHT
            Display.setTitle("Tron Light Bike"); //sets title of the screen to Tron Light Bike

        } catch (LWJGLException e) { //if the picture is not found
            e.printStackTrace(); //print error to console          
            Display.destroy(); //close display
            System.exit(1); //end program
        }
        try {
            // Load the menuImage texture from "res/images/menuImage.png"
            texture = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/" + textureName + ".png")));
        } catch (IOException e) { //if the picture is not found
            e.printStackTrace(); //print error to console 
            Display.destroy(); //close display
            System.exit(1); //end program
        }
        glEnable(GL_TEXTURE_2D);
        boolean spaceTapped = false; //boolean variable for space bar being pressed
        while (!spaceTapped) { //while space bar isn't tapped
        	if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){ //checks if escape key is pressed
        		Display.destroy(); //closes display
        		System.exit(1); //end program
     	    }
     	    if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){ //checks if space key is pressed
     	    	spaceTapped = true; //space bar is tapped
     	    }
            glClear(GL_COLOR_BUFFER_BIT);
            texture.bind();
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
    
    /**
     * plays the tron game
     */
	public static void playGame(){
		render(); //draw bikes
		logic(getDelta()); //updates frame
		areWinners(); //checks who the winners are
		input(); //get keyboard input
		Display.update();
		Display.sync(60);
	}

	/**
	 * keyboard input used to control the bikes
	 */
    private static void input() {
    	bikes.get(0).drive(Keyboard.isKeyDown(Keyboard.KEY_UP), Keyboard.isKeyDown(Keyboard.KEY_DOWN), //1st bike gets arrow key controls
    			Keyboard.isKeyDown(Keyboard.KEY_RIGHT), Keyboard.isKeyDown(Keyboard.KEY_LEFT));
    	bikes.get(1).drive(Keyboard.isKeyDown(Keyboard.KEY_W), Keyboard.isKeyDown(Keyboard.KEY_S), //2nd bike gets w-a-s-d controls
    			Keyboard.isKeyDown(Keyboard.KEY_D), Keyboard.isKeyDown(Keyboard.KEY_A));
    	bikes.get(2).drive(Keyboard.isKeyDown(Keyboard.KEY_I), Keyboard.isKeyDown(Keyboard.KEY_K), //3rd bike gets i-j-k-l controls
    			Keyboard.isKeyDown(Keyboard.KEY_L), Keyboard.isKeyDown(Keyboard.KEY_J));
    	bikes.get(3).drive(Keyboard.isKeyDown(Keyboard.KEY_T), Keyboard.isKeyDown(Keyboard.KEY_G), //4th bike gets t-f-g-h controls
    			Keyboard.isKeyDown(Keyboard.KEY_H), Keyboard.isKeyDown(Keyboard.KEY_F));
    	bikes.get(4).drive(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5), Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2), //5th bike gets numpad controls
    			Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3), Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1));
    	bikes.get(5).drive(Keyboard.isKeyDown(Keyboard.KEY_NUMPADEQUALS), Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8), //5th bike gets numpad controls
    			Keyboard.isKeyDown(Keyboard.KEY_NUMPAD9), Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7));
    }

    /**
     * gets time
     * @return time
     */
    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * gets delta
     * @return time difference between last frame and current frame
     */
    private static int getDelta() {
        long currentTime = getTime(); //gets time of current frame
        int delta = (int) (currentTime - lastFrame); //finds difference between last frame and current frame
        lastFrame = getTime(); //last frame is set to the current frame
        return delta;
    }

    /**
     * sets up display
     */
    private static void setUpDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); //sets display with width of WIDTH and height of HEIGHT
            Display.setTitle("Tron Light Bike"); //sets title of display to Tron Light Bike
            Display.create(); //creates display
        } catch (LWJGLException e) {
            e.printStackTrace(); //prints error
            Display.destroy(); //closes display
            System.exit(1); //ends program
        }
    }

    /**
     * sets up OpenGL
     * displays screen
     */
    private static void setUpOpenGL() {
        glMatrixMode(GL_PROJECTION); 
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
    }

    /**
     * creates all the bikes by adding them the arraylist
     */
    private static void setUpEntities() {
        bikes = new ArrayList<Bike>(0);
    	bikes.add(new Bike(WIDTH/7, HEIGHT/2, BIKE_SIZE, BIKE_SIZE, 1.0f, 0.6471f, 0.0f, 2, "DOWN", "Orange")); //adds orange bike
        bikes.add(new Bike(WIDTH*2/7, HEIGHT/2, BIKE_SIZE, BIKE_SIZE, 0.8549f, 1.0f, 0.1058f, 2, "UP", "Yellow")); //adds yellow bike
        bikes.add(new Bike(WIDTH*3/7, HEIGHT/2, BIKE_SIZE, BIKE_SIZE, 0.4784f, 1.0f, 0.9843f, 2, "DOWN", "Blue")); //adds blue bike
        bikes.add(new Bike(WIDTH*4/7, HEIGHT/2, BIKE_SIZE, BIKE_SIZE, 0.2235f, 1.0f, 0.0784f, 2, "UP", "Green")); //adds green bike
        bikes.add(new Bike(WIDTH*5/7, HEIGHT/2, BIKE_SIZE, BIKE_SIZE, 1.0f, 0.0f, 0.0f, 2, "DOWN", "Red")); //adds red bike
        bikes.add(new Bike(WIDTH*6/7, HEIGHT/2, BIKE_SIZE, BIKE_SIZE, 1.0f, 1.0f, 1.0f, 2, "UP", "White")); //adds white bike
    }

    /**
     * sets up timer used in game
     */
    private static void setUpTimer() {
        lastFrame = getTime();
    }

    /**
     * draws bikes
     */
    private static void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        for(Bike b: bikes) {
        	b.draw(); //draws bike at current location
        }
    }

    /**
     * updates bikes based on last frame
     * @param delta the difference between the last frame and current frame
     */
    private static void logic(int delta) {
    	for(Bike b: bikes) {
        	b.update(delta); //updates current position of the bike
        }
        eliminateDeadRiders(); //eliminates a bike if it collides with a trail, player or wall
    }

    /**
     * eliminates a bike if it collides with a trail, player or wall
     */
	private static void eliminateDeadRiders() {
		for(int i = 0; i < bikes.size(); i++) { //runs through array list of bikes
			bikes.get(i).collisionDetection(WIDTH, HEIGHT); //checks if one of the bikes collides with a wall
			for(int x = 0; x < bikes.size(); x++) { //runs through array list of bikes
				if(i != x && bikes.get(i).isAlive() && bikes.get(x).isAlive()) { 
					if(bikes.get(i).collidesWith(bikes.get(x))) { //checks if two seperate bikes collide with each other
						bikes.get(i).die(); //kills both bikes that collide with each other
						bikes.get(x).die();
					}
					bikes.get(i).hitsOtherTrails(bikes.get(x).getLightTrail()); //kills bike that collides into the other bikes trail
				}
			}
		}
	}
	
	/**
	 * checks how many bikes are alive every frame
	 * if there are more than 1 bike then game continues
	 * if there is 1 bike alive it is the winner
	 * if there are 0 bikes alive than there are no winners
	 */
	public static void areWinners() {
		int alive = 0; //creates an int variable for how many bikes are still alive
		String winner = ""; //creates a string variable for the winners name
		for(Bike bike: bikes) { //runs through list of bikes
			if(bike.isAlive()) { //checks if current bike is alive
				alive++; //increments alive
				winner = bike.getColorName(); //winner name becomes current bike
			}
		}
		if(alive < 2) { //checks if there are less than 2 bikes alive
			if(alive == 1) { //if there is one bike alive
				displayScreen(winner); //displays the winner
			} else {
				displayScreen("Tie"); //displays that there are no winners
			}
			gameState = 1; //sets game state to 1
			for(Bike bike: bikes) {
				bike.drawScore();
			}
		}
	}
}