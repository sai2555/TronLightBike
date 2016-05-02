import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glRectd;
/**
 * 
 * @author Leon, Max
 *
 */
public class Trail extends AbstractEntity {
	/**
	 * Trail Class Constructor
	 * @param x x coor
	 * @param y y coor
	 * @param width width
	 * @param height height
	 * @param r r value
	 * @param g g value
	 * @param b b value
	 */
	public Trail(double x, double y, double width, double height, float r, float g, float b) {
		super(x, y, width, height, r, g, b);
	}
	
	@Override
	/**
	 * Draws the light trail
	 */
	public void draw() {
		glColor3f(getR(), getG(), getB());
		glRectd(getX(), getY(), getX() + getWidth(), getY() + getHeight());
		glColor3f(1,1,1);
	}

	@Override
	public void update(int delta) {
		//Do Nothing because this entity does not move
	}
}