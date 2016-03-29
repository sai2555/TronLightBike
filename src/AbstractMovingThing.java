
public abstract class AbstractMovingThing extends AbstractThing implements MovingThing {

	private double dx;
    private double dy;

    public AbstractMovingThing(double x, double y, double width, double height, float r, float g, float b) {
    	super(x, y, width, height, r, g, b);
        dx = 0;
        dy = 0;
    }

    @Override
    public void update(int delta) {
        setX(getX() + (delta * dx));
        setY(getY() + (delta * dy));
    }

    public double getDX() {
        return dx;
    }

    public double getDY() {
        return dy;
    }

    public void setDX(double dxIn) {
        dx = dxIn;
    }

    public void setDY(double dyIn) {
        dy = dyIn;
    }
    
    public boolean verticalBoundaryTouches(int screenHeight) {
    	return getY() < 0 || (getY() + getHeight()) > screenHeight;
    }
    
    public boolean horizontalBoundaryTouches(int screenWidth) {
    	return getX() < 0 || (getX() + getWidth()) > screenWidth;
    }
    
    public boolean collisionDetection(int screenWidth, int screenHeight) {
    	if(horizontalBoundaryTouches(screenWidth)) {
    		setDX(-getDX());
    		return true;
    	}
    	if(verticalBoundaryTouches(screenHeight)) {
    		setDY(-getDY());
    	}
    	return false;
    }
}
