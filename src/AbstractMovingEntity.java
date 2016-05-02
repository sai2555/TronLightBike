
/**
 * abstract class for a moving entity
 * @author Max Topelson
 *
 */
public abstract class AbstractMovingEntity extends AbstractEntity {

	/**
	 * horizontal velocity
	 */
	private double dx;
	/**
	 * vertical velocity
	 */
    private double dy;

    /**
     * constructs a abstract moving entity
     * @param x x coordinate
     * @param y y coordinate
     * @param width width of entity
     * @param height height of entity
     * @param r red color value
     * @param g green color value
     * @param b blue color value
     */
    public AbstractMovingEntity(double x, double y, double width, double height, float r, float g, float b) {
    	super(x, y, width, height, r, g, b);
        dx = 0;
        dy = 0;
    }

    @Override
    /**
     * updates current position of entity based on delta
     * @param delta difference between last frame and current frame
     */
    public void update(int delta) {
        setX(getX() + (delta * dx)); //sets x coordinate
        setY(getY() + (delta * dy)); //sets y coordinate
    }

    /**
     * gets horizontal velocity
     * @return dx
     */
    public double getDX() {
        return dx;
    }

    /**
     * gets vertical velocity
     * @return dy
     */
    public double getDY() {
        return dy;
    }

    /**
     * sets horizontal velocity
     * @param dxIn new horizontal velocity
     */
    public void setDX(double dxIn) {
        dx = dxIn;
    }

    /**
     * sets vertical velocity
     * @param dyIn new vertical velocity
     */
    public void setDY(double dyIn) {
        dy = dyIn;
    }
    
    /**
     * checks if entity touches side/vertical boundaries
     * @param screenHeight height of screen
     * @return true if entity is touching side/vertical boundaries; false otherwise
     */
    public boolean verticalBoundaryTouches(int screenHeight) {
    	return getY() < 0 || (getY() + getHeight()) > screenHeight; //checks if current y coordinate is < 0 or > the height of the screen
    }
    
    /**
     * checks if entity touches top/bottom/horizontal boundaries
     * @param screenHeight width of screen
     * @return true if entity is touching top/bottom/horizontal boundaries; false otherwise
     */
    public boolean horizontalBoundaryTouches(int screenWidth) {
    	return getX() < 0 || (getX() + getWidth()) > screenWidth; //checks if current y coordinate is < 0 or > the width of the screen
    }
    
    /**
     * checks if entity collides with another entity or a boundary
     * @param screenWidth width of the screen
     * @param screenHeight height of the screen
     * @return true if entity collides with another entity or boundary; false otherwise
     */
    public boolean collisionDetection(int screenWidth, int screenHeight) {
    	if(horizontalBoundaryTouches(screenWidth)) {
    		return true;
    	}
    	if(verticalBoundaryTouches(screenHeight)) {
    	}
    	return false;
    }
}

