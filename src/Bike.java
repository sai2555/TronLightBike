import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.*;
/**
 * Bike Class that extends Abstract Moving Entity
 * @author Sai Maddhi, Leon Shen, Max Topleson
 *
 */
public class Bike extends AbstractMovingEntity {
	/**
	 * holds the light trails of the bike
	 */
	private ArrayList<Trail> lightTrail;
	/**
	 * the current light trail being manipulated
	 */
	private int currentLT;
	/**
	 * true if bike is traveling up or down
	 */
	private boolean upDown;
	/**
	 * the thickness of the light trail
	 */
	private final int LT_THICKNESS;
	/**
	 * true if bike recently changed direction
	 */
	private boolean changedDirection;
	/**
	 * The current direction of the bike
	 */
	private String direction;
	/**
	 * The old direction the bike was moving in
	 */
	private String oldDirection;
	/**
	 * true if bike is alive
	 */
	private boolean isAlive;
	/**
	 * true if bike is moving twice the speed
	 */
	private boolean speeding;
	/**
	 * speed of the bikes
	 */
	private double speed = 0.1;
	/**
	 * The name of the color of the bike
	 */
	private String colorString;
	/**
	 * The bike constructor
	 * @param x the x coord
	 * @param y the y coor
	 * @param width the width
	 * @param height the height
	 * @param r the r value of the color
	 * @param g the g value of the color
	 * @param b the b value of the color
	 * @param lightTrailThicknessIn the light trail thickness
	 * @param directionIn the direction to start traveling in
	 * @param colorStringIn the name of the color
	 */
	public Bike(double x, double y, double width, double height, float r, float g, float b, int lightTrailThicknessIn, String directionIn, String colorStringIn) {
		super(x, y, width, height, r, g, b);
		LT_THICKNESS = lightTrailThicknessIn;
		isAlive = true;
		direction = directionIn;
		oldDirection = directionIn;
		lightTrail = new ArrayList<Trail>();
		currentLT = 0;
		changedDirection = false;
		setUpInitialSettings(directionIn);
		speeding = false;
		colorString = colorStringIn;
	}
	/**
	 * returns the name of the color string
	 * @return the color string
	 */
	public String getColorName(){
		return colorString;
	}
	/**
	 * The method that positions the bikes in their initial positions with their initial colors
	 * @param dIn
	 */
	private void setUpInitialSettings(String dIn) {
		switch(dIn) {//Depending on the direction that the bike was constructed with, set up the bikes
		case "DOWN":
			setDY(speed);
			setDX(0.0);//set the bike going down
			upDown = true;// bike is moving up and down so it is set to true
			lightTrail.add(new Trail(getX() + getWidth() / 2 - LT_THICKNESS / 2,
					getY() + getHeight() / 2 + LT_THICKNESS / 2, LT_THICKNESS, 0, getR(), getG(), getB()));//make a new light trail in the 
					// direction that the bike is currently traveling in which is down
			break;
		case "UP":
			setDY(-speed);
			setDX(0.0);//Set the bike going up
			upDown = true;// updown is true because it is traveling up
			lightTrail.add(new Trail(getX() + getWidth() / 2 - LT_THICKNESS / 2,
					getY() + getHeight() / 2 + LT_THICKNESS / 2, LT_THICKNESS, 0, getR(), getG(), getB()));//make a new light trail in the 
			// direction that the bike is currently traveling in which is up
			break;
		case "RIGHT":
			setDX(speed);
			setDY(0.0);//sets the bike going right
			upDown = false;//Since it is traveling right, upDown is false
			lightTrail.add(new Trail(getX() + getWidth() / 2 - LT_THICKNESS / 2,
					getY() + getHeight() / 2 - LT_THICKNESS, 0, LT_THICKNESS, getR(), getG(), getB()));//make a new light trail in the 
			// direction that the bike is currently traveling in which is right
			break;
		default:
			setDX(-speed);
			setDY(0.0);//Set the bike going left
			upDown = false;//Since it is traveling left, upDown is false
			lightTrail.add(new Trail(getX() + getWidth() / 2 + LT_THICKNESS / 2,
					getY() + getHeight() / 2 - LT_THICKNESS / 2, 0, LT_THICKNESS, getR(), getG(), getB()));//make a new light trail in the 
			// direction that the bike is currently traveling in which is left
			break;
		}
	}

	@Override
	/**
	 * Draws the bike
	 */
	public void draw() {
		if(isAlive) {
			glDisable(GL_TEXTURE_2D);
			glColor3f(getR(), getG(), getB());
			glRectd(getX(), getY(), getX() + getWidth(), getY() + getHeight());//Draws a rectangle for the bike
			glColor3f(0,0,0);
		}
	}
	/**
	 * Drives the bike according to the keyboard inputs
	 * @param up true if the user clicked the up control
	 * @param down true if the user clicked the down control
	 * @param right true if the user clicked the right control
	 * @param left true if the user clicked the right control
	 */
	public void drive(boolean up, boolean down, boolean right, boolean left) {
		if(!up && !down && !right && !left) return;//If no controls were clicked, dont change the bikes direction
		if(isAlive) {
			/*
			 * Only let the user drive the bike in a certain direction if the bike has traveled at least more than the width of the light
			 * trail. If the user clicks left and he was traveling 
			 */
			if(((direction.equals("UP") || direction.equals("DOWN")) && lightTrail.get(currentLT).getHeight() > getWidth() / 2) || 
					(((direction.equals("RIGHT") || direction.equals("LEFT")) && lightTrail.get(currentLT).getWidth() > getWidth() / 2))) {
				if (up || down) {//If user wants to move bike up or down
					/*
					 * If bike was not going down, make bike go up; if it was already going up, make the bike go faster if it is not speeding
					 * and slower if it is speeding
					 */
					if (up && !direction.equals("DOWN")) {
						if(direction.equals("UP")) {
							if(speeding) {
								setDX(0);
								setDY(-speed);
								speeding = false;
							} else {
								setDX(0);
								setDY(-speed*2);
								speeding = true;
							}
						} else {
							setDX(0);
							setDY(-speed);
							speeding = false;
							oldDirection = direction;
							direction = "UP";
						}
					} else if (down && !direction.equals("UP")) {
						/*
						 * User can only make bike go down if it was not going up; if it is going down already, have it speed based on
						 * whether it was speeding or not.
						 */
						if(direction.equals("DOWN")) {
							if(speeding) {
								setDX(0);
								setDY(speed);
								speeding = false;
							} else {
								setDX(0);
								setDY(speed*2);
								speeding = true;
							}
						} else {
							setDX(0);
							setDY(speed);
							speeding = false;
							oldDirection = direction;
							direction = "DOWN";
						}
					}
					changedDirection = (!upDown) ? true : false;
					upDown = true;
				} else if(left || right) {
					/*
					 * User can only go right if they were not going left; if already going right, have bike speed based on whether it was
					 * speeding or not
					 */
					if (right && !direction.equals("LEFT")) {
						if(direction.equals("RIGHT")) {
							if(speeding) {
								setDY(0);
								setDX(speed);
								speeding = false;
							} else {
								setDY(0);
								setDX(speed*2);
								speeding = true;
							}
						} else {
							setDY(0);
							setDX(speed);
							speeding = false;
							oldDirection = direction;
							direction = "RIGHT";
						}
					} else if (left && !direction.equals("RIGHT")) {
						/*
						 * Bike can only go left if not going right; if already going left, have bike speed based on whether it was speeding
						 */
						if(direction.equals("LEFT")) {
							if(speeding) {
								setDY(0);
								setDX(-speed);
								speeding = false;
							} else {
								setDY(0);
								setDX(-speed*2);
								speeding = true;
							} 
						} else {
							setDY(0);
							setDX(-speed);
							speeding = false;
							oldDirection = direction;
							direction = "LEFT";
						}
					}
					changedDirection = (upDown) ? true : false;
					upDown = false;
				}
			}
		}
	}

	@Override
	/**
	 * Updates the light trails if the direction is not changed
	 * If the direction is changed then a new light trail will be made 
	 */
	public void update(int delta) {
		if(isAlive) { //Checks if the bike is not dead 
			super.update(delta);
			if(changedDirection) { //If the bike changes directions then a new light trail will be formed 
				makeNewLightTrail();
			} else {
				updateLightTrail(); //Else it would update the current light trail in the direction it is going in
			}
			changedDirection = false; //if changeDirection was true, then it sets it to false so that another light trail is not created 
			drawLightTrails(); //Draws the light trail
		}
	}
	/**
	 * This method updates the current light trail of the bike based on the direction it is moving in
	 */
	public void updateLightTrail() {
		if(isAlive) { // Checks if the bike is alive
			Trail lt = lightTrail.get(currentLT);
			if(direction.equals("UP")) {
				double distance = lt.getY() - (getY() + getHeight());
				lt.setY(getY() + getHeight());
				lt.setHeight(distance+lt.getHeight());
			} else if(direction.equals("DOWN")) {
				double distance = (getY()) - (lt.getY() + lt.getHeight());
				lt.setHeight(distance+lt.getHeight());
			} else if(direction.equals("RIGHT")) {
				double distance = getX() - (lt.getX() + lt.getWidth());
				lt.setWidth(lt.getWidth()+distance);
			} else {
				double distance = lt.getX() - (getX() + getWidth());
				lt.setX(getX() + getWidth());
				lt.setWidth(distance+lt.getWidth());
			}
		}
	}
	/**
	 * Creates a new light trail
	 */
	public void makeNewLightTrail() {
		if(isAlive) {
			Trail lt = lightTrail.get(currentLT);
			currentLT++;
			/*
			 * If bike was going up/down/left/right, make a new trail in the new direction
			 */
			if(oldDirection.equals("UP")) {
				double distance = lt.getY() - (getY() + getHeight() / 2);
				lt.setY(getY() + getHeight() / 2 - LT_THICKNESS);
				lt.setHeight(distance+lt.getHeight()+LT_THICKNESS);
				if(direction.equals("RIGHT")) {
					lightTrail.add(new Trail(getX() + getWidth() / 2 - LT_THICKNESS / 2,
							getY() + getHeight() / 2 - LT_THICKNESS, 0, LT_THICKNESS, getR(), getG(), getB()));
				} else {
					lightTrail.add(new Trail(getX() + getWidth() / 2 + LT_THICKNESS / 2,
							getY() + getHeight() / 2 - LT_THICKNESS, 0, LT_THICKNESS, getR(), getG(), getB()));
				}
			} else if(oldDirection.equals("DOWN")){
				double distance = (getY() + getHeight() / 2) - (lt.getY() + lt.getHeight());
				lt.setHeight(distance+lt.getHeight()+LT_THICKNESS/2);
				if(direction.equals("RIGHT")) {
					lightTrail.add(new Trail(getX() + getWidth() / 2 - LT_THICKNESS / 2,
							getY() + getHeight() / 2 - LT_THICKNESS / 2, 0, LT_THICKNESS, getR(), getG(), getB()));
				} else {
					lightTrail.add(new Trail(getX() + getWidth() / 2 + LT_THICKNESS / 2,
							getY() + getHeight() / 2 - LT_THICKNESS / 2, 0, LT_THICKNESS, getR(), getG(), getB()));
				}
			} else if(oldDirection.equals("RIGHT")) {
				double distance = (getX() + getWidth() / 2) - (lt.getX() + lt.getWidth());
				lt.setWidth(lt.getWidth()+distance+LT_THICKNESS/2);
				if(direction.equals("UP")) {
					lightTrail.add(new Trail(getX() + getWidth() / 2 - LT_THICKNESS / 2,
							getY() + getHeight() / 2 + LT_THICKNESS / 2, LT_THICKNESS, 0, getR(), getG(), getB()));
				} else {
					lightTrail.add(new Trail(getX() + getWidth() / 2 - LT_THICKNESS / 2,
							getY() + getHeight() / 2 - LT_THICKNESS, LT_THICKNESS, 0, getR(), getG(), getB()));
				}
			} else {
				double distance = lt.getX() - (getX() + getWidth() / 2);
				lt.setX(getX() + getWidth() / 2 - LT_THICKNESS / 2);
				lt.setWidth(distance+lt.getWidth()+LT_THICKNESS/2);
				if(direction.equals("UP")) {
					lightTrail.add(new Trail(getX() + getWidth() / 2 - LT_THICKNESS / 2,
							getY() + getHeight() / 2 + LT_THICKNESS / 2, LT_THICKNESS, 0, getR(), getG(), getB()));
				} else {
					lightTrail.add(new Trail(getX() + getWidth() / 2 - LT_THICKNESS / 2,
							getY() + getHeight() / 2 - LT_THICKNESS, LT_THICKNESS, 0, getR(), getG(), getB()));
				}
			}
		}
	}
	/**
	 * Draws the light trails on the screen
	 */
	public void drawLightTrails() {
		if(isAlive) { // Checks if the bike is alive
			glDisable(GL_TEXTURE_2D); // Disables Texture 2D 
			for(Trail trail: lightTrail) {
				trail.draw();
			}
		}
	}
	/**
	 * "Kills" bike; disables all methods with the if(isAlive) check
	 */
	public void die() {
		isAlive = false;
	}
	
	@Override
	/**
	 * Determines if a bike collides with  the boundaries or if it collides with another trail
	 */
	public boolean collisionDetection(int screenWidth, int screenHeight) {
		if(horizontalBoundaryTouches(screenWidth)) {
    		die();
    		return true;
    	}
    	if(verticalBoundaryTouches(screenHeight)) {
    		die();
    		return true;
    	}
    	hitsOwnTrails();
    	return false;
	}
	/**
	 * @return the light trail so it can be used for a different class 
	 */
	public ArrayList<Trail> getLightTrail() {
		return lightTrail; //Returns lightTrail array list 
	}
	/**
	 * 
	 * @param trails An Arraylist of trails of the other bike
	 * @return Changes the state of a bike when it hits another trail
	 */
	public boolean hitsOtherTrails(ArrayList<Trail> trails) {
		for(Trail trail: trails) {
			if(trail.collidesWith(this)) { 
				die();
				return true;
			}
		}
		return false;
	}
	/**
	 * If a bike hits its own trail then it dies
	 */
	public void hitsOwnTrails() {
		for(int i = 0; i < currentLT-1; i++) {
			if(i >= 0) {
				if(lightTrail.get(i).collidesWith(this)) {
					die();
					return;
				}
			}
		}
	}
	/**
	 * 
	 * @return checks if a bike is alive or not 
	 */
	public boolean isAlive() { 
		return isAlive;
	}
	public void drawScore() {
		
	}
}