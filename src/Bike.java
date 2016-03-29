import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

public class Bike extends AbstractMovingThing {

	private ArrayList<Trail> lightTrail;
	private int currentLT;
	private boolean upDown;
	private final int LT_THICKNESS = 4;
	private boolean changedDirection;
	private String direction;
	private String oldDirection;
	private boolean isAlive;

	public Bike(double x, double y, double width, double height, float r, float g, float b) {
		super(x, y, width, height, r, g, b);
		isAlive = true;
		setDY(-.1);
		direction = "UP";
		oldDirection = "UP";
		upDown = true;
		lightTrail = new ArrayList<Trail>();
		currentLT = 0;
		lightTrail.add(new Trail(getX() + getWidth() / 2 - LT_THICKNESS / 2, getY() + getHeight(), LT_THICKNESS, 0, getR(),
				getG(), getB()));
		changedDirection = false;
	}

	@Override
	public void draw() {
		if(isAlive) {
			glColor3f(getR(), getG(), getB());
			glRectd(getX(), getY(), getX() + getWidth(), getY() + getHeight());
		}
	}

	public void drive(boolean up, boolean down, boolean right, boolean left) {
		if(isAlive) {
			if (up || down) {
				if (up && !direction.equals("DOWN")) {
					setDX(0);
					setDY(-.1);
					oldDirection = direction;
					direction = "UP";
				} else if (down && !direction.equals("UP")) {
					setDX(0);
					setDY(.1);
					oldDirection = direction;
					direction = "DOWN";
				}
				changedDirection = (!upDown) ? true : false;
				upDown = true;
			} else if(left || right) {
				if (right && !direction.equals("LEFT")) {
					setDY(0);
					setDX(.1);
					oldDirection = direction;
					direction = "RIGHT";
				} else if (left && !direction.equals("RIGHT")) {
					setDY(0);
					setDX(-.1);
					oldDirection = direction;
					direction = "LEFT";
				}
				changedDirection = (upDown) ? true : false;
				upDown = false;
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