import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glRectd;

public class Trail extends AbstractEntity {
	
	public Trail(double x, double y, double width, double height, float r, float g, float b) {
		super(x, y, width, height, r, g, b);
	}

	@Override
	public void draw() {
		glColor3f(getR(), getG(), getB());
		glRectd(getX(), getY(), getX() + getWidth(), getY() + getHeight());
	}

	@Override
	public void update(int delta) {
		//Do Nothing
	}
}