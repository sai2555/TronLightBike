import java.awt.*;

public abstract class AbstractEntity implements Entity {	
	
	private double x;
    private double y;
    private double width;
    private double height;
    private float[] color;
    private final Rectangle collisionBox = new Rectangle();
    
    public AbstractEntity(double xIn, double yIn, double widthIn, double heightIn, float r, float g, float b) {
        x = xIn;
        y = yIn;
        width = widthIn;
        height = heightIn;
        //Array of color(RGB)
        color = new float[3];
        color[0] = r;
        color[1] = g;
        color[2] = b;
    }

    @Override
    public void setLocation(double xIn, double yIn) {
        x = xIn;
        y = yIn;
    }

    @Override
    public void setX(double xIn) {
    	x = xIn;
    }

    @Override
    public void setY(double yIn) {
        y = yIn;
    }

    @Override
    public void setWidth(double widthIn) {
        width = widthIn;
    }

    @Override
    public void setHeight(double heightIn) {
        height = heightIn;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public boolean collidesWith(Entity other) {
        collisionBox.setBounds((int) x, (int) y, (int) width, (int) height);
        return collisionBox.intersects(other.getX(), other.getY(), other.getWidth(), other.getHeight());
    }
    
    public float getR() {
    	return color[0];
    }
    
    public float getG() {
    	return color[1];
    }
    
    public float getB() {
    	return color[2];
    }
}
