import java.awt.*;

public abstract class AbstractEntity implements Entity {	
	
	private double x; //Keeps track of the x value
    private double y; //Keeps track of the y value
    private double width; // Keeps track of the width value
    private double height; // Keeps track of the height value 
    private float[] color; //Keeps track of what color each bike is going to be 
    private final Rectangle collisionBox = new Rectangle(); // Makes the collision box of the bike so it tells when a bike dies
    
    /**
     * 
     * @param xIn X coord 
     * @param yIn Y coord 
     * @param widthIn Width
     * @param heightIn Height 
     * @param r Red value
     * @param g Green Value
     * @param b Blue Value 
     */
    public AbstractEntity(double xIn, double yIn, double widthIn, double heightIn, float r, float g, float b) {
        x = xIn; //Assigns x to xIn
        y = yIn; //Assigns y to yIn
        width = widthIn; //Assigns width to widthIn
        height = heightIn; //Assigns height to heightIn
        //Array of color(RGB)
        color = new float[3]; //Initializes array of colors to 3 values 
        color[0] = r; //Color 0 will be red
        color[1] = g; // Color 1 is Green 
        color[2] = b;// Color 2 is Blue 
    }

    @Override
    /**
     * Determines the x and y location 
     */
    public void setLocation(double xIn, double yIn) {
        x = xIn; //Changes x value to xIn
        y = yIn; //Changes y value to yIn
    }

    @Override
    /**
     * Parameter takes in xIn and changes the x value to xIn
     */
    public void setX(double xIn) {
    	x = xIn; //Changes x to the value of xIn
    }

    @Override
    /**
     * Parameter takes in yIn and changes the y value to yIn
     */
    public void setY(double yIn) {
        y = yIn; //Assigns y value to the parameter yIn
    }

    @Override
    /**
     * Sets width based on parameter
     */
    public void setWidth(double widthIn) {
        width = widthIn; //Assigns width to widthIn
    }

    @Override
    /**
     * Sets height based on parameter
     */
    public void setHeight(double heightIn) {
        height = heightIn; //Assigns height to heightIn
    }

    @Override
    /**
     * @return xcoor
     */
    public double getX() {
        return x; //Returns the values of x 
    }

    @Override
    /**
     * @return ycoor
     */
    public double getY() {
        return y; //Returns the values of y
    }

    @Override
    /**
     * @return height 
     */
    public double getHeight() {
        return height; //Returns the value of height 
    }

    @Override
    /**
     * @return width
     */
    public double getWidth() {
        return width; //Returns the value of width 
    }

    @Override
    /**
     * Returns true of it does collides with another entity false of it doesn't 
     */
    public boolean collidesWith(Entity other) {
        collisionBox.setBounds((int) x, (int) y, (int) width, (int) height);
        return collisionBox.intersects(other.getX(), other.getY(), other.getWidth(), other.getHeight()); //Returns true or false depending on if it is colliding with an entity
    }
    /**
     * getter for color
     * @return r value
     */
    public float getR() {
    	return color[0]; //Returns the value of Red 
    }
    /**
     * getter for color
     * @return g value
     */
    public float getG() {
    	return color[1]; //Returns the value of Green 
    }
    /**
     * getter for color
     * @return b value
     */
    public float getB() {
    	return color[2]; //Returns the value of blue 
    }
}