import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.*;
/**
 * Bike Class that extends Abstract Moving Entity
 * @author Sai Maddhi
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
	public String getName(){
		return colorString;
	}
	/**
	 * The 
	 * @param dIn
	 */
	private void setUpInitialSettings(String dIn) {
		switch(dIn) {
		case "DOWN":
			setDY(0.1);
			setDX(0.0);
			upDown = true;
			lightTrail.add(new Trail(getX() + getWidth() / 2 - LT_THICKNESS / 2,
					getY() + getHeight() / 2 + LT_THICKNESS / 2, LT_THICKNESS, 0, getR(), getG(), getB()));
			break;
		case "UP":
			setDY(-0.1);
			setDX(0.0);
			upDown = true;
			lightTrail.add(new Trail(getX() + getWidth() / 2 - LT_THICKNESS / 2,
					getY() + getHeight() / 2 + LT_THICKNESS / 2, LT_THICKNESS, 0, getR(), getG(), getB()));
			break;
		case "RIGHT":
			setDX(0.1);
			setDY(0.0);
			upDown = false;
			lightTrail.add(new Trail(getX() + getWidth() / 2 - LT_THICKNESS / 2,
					getY() + getHeight() / 2 - LT_THICKNESS, 0, LT_THICKNESS, getR(), getG(), getB()));
			break;
		default:
			setDX(-0.1);
			setDY(0.0);
			upDown = false;
			lightTrail.add(new Trail(getX() + getWidth() / 2 + LT_THICKNESS / 2,
					getY() + getHeight() / 2 - LT_THICKNESS / 2, 0, LT_THICKNESS, getR(), getG(), getB()));
			break;
		}
	}

	@Override
	public void draw() {
		if(isAlive) {
			glDisable(GL_TEXTURE_2D);
			glColor3f(getR(), getG(), getB());
			glRectd(getX(), getY(), getX() + getWidth(), getY() + getHeight());
		}
	}

	public void drive(boolean up, boolean down, boolean right, boolean left) {
		if(!up && !down && !right && !left) return;
		if(isAlive) {
			if(((direction.equals("UP") || direction.equals("DOWN")) && lightTrail.get(currentLT).getHeight() > getWidth() / 2) || 
					(((direction.equals("RIGHT") || direction.equals("LEFT")) && lightTrail.get(currentLT).getWidth() > getWidth() / 2))) {
				if (up || down) {
					if (up && !direction.equals("DOWN")) {
						if(direction.equals("UP")) {
							if(speeding) {
								setDX(0);
								setDY(-.1);
								speeding = false;
							} else {
								setDX(0);
								setDY(-.2);
								speeding = true;
							}
						} else {
							setDX(0);
							setDY(-.1);
							speeding = false;
							oldDirection = direction;
							direction = "UP";
						}
					} else if (down && !direction.equals("UP")) {
						if(direction.equals("DOWN")) {
							if(speeding) {
								setDX(0);
								setDY(.1);
								speeding = false;
							} else {
								setDX(0);
								setDY(.2);
								speeding = true;
							}
						} else {
							setDX(0);
							setDY(.1);
							speeding = false;
							oldDirection = direction;
							direction = "DOWN";
						}
					}
					changedDirection = (!upDown) ? true : false;
					upDown = true;
				} else if(left || right) {
					if (right && !direction.equals("LEFT")) {
						if(direction.equals("RIGHT")) {
							if(speeding) {
								setDY(0);
								setDX(.1);
								speeding = false;
							} else {
								setDY(0);
								setDX(.2);
								speeding = true;
							}
						} else {
							setDY(0);
							setDX(.1);
							speeding = false;
							oldDirection = direction;
							direction = "RIGHT";
						}
					} else if (left && !direction.equals("RIGHT")) {
						if(direction.equals("LEFT")) {
							if(speeding) {
								setDY(0);
								setDX(-.1);
								speeding = false;
							} else {
								setDY(0);
								setDX(-.2);
								speeding = true;
							} 
						} else {
							setDY(0);
							setDX(-.1);
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
	public void update(int delta) {
		if(isAlive) {
			super.update(delta);
			if(changedDirection) {
				makeNewLightTrail();
			} else {
				updateLightTrail();
			}
			changedDirection = false;
			drawLightTrails();
		}
	}

	public void updateLightTrail() {
		if(isAlive) {
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

	public void makeNewLightTrail() {
		if(isAlive) {
			Trail lt = lightTrail.get(currentLT);
			currentLT++;
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

	public void drawLightTrails() {
		if(isAlive) {
			glDisable(GL_TEXTURE_2D);
			for(Trail trail: lightTrail) {
				trail.draw();
			}
		}
	}
	
	public void die() {
		isAlive = false;
	}
	
	@Override
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
	
	public ArrayList<Trail> getLightTrail() {
		return lightTrail;
	}
	
	public boolean hitsOtherTrails(ArrayList<Trail> trails) {
		for(Trail trail: trails) {
			if(trail.collidesWith(this)) { 
				die();
				return true;
			}
		}
		return false;
	}
	
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
	
	public boolean isAlive() { 
		return isAlive;
	}
}