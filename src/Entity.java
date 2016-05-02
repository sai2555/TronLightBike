
public interface Entity {
	
	/**
	 * draws the entity
	 */
	public void draw();
	
	/**
	 * updates entity's position based on delta
	 * @param delta difference between last frame and current frame
	 */
    public void update(int delta);
    
    /**
     * sets entity to a location
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setLocation(double x, double y);
    
    /**
     * sets new x coordinate
     * @param x new x coordinate
     */
    public void setX(double x);
    
    /**
     * sets new y coordinate
     * @param y new y coordinate
     */
    public void setY(double y);
    
    /**
     * sets width of entity
     * @param width width of entity
     */
    public void setWidth(double width);
    
    /**
     * sets height of entity
     * @param height height of entity
     */
    public void setHeight(double height);
    
    /**
     * gets current x coordinate
     * @return current x coordinate
     */
    public double getX();
    
    /**
     * gets current y coordinate
     * @return current y coordinate
     */
    public double getY();
    
    /**
     * get height of entity
     * @return height
     */
    public double getHeight();
    
    /**
     * gets width of entity
     * @return width
     */
    public double getWidth();
    
    /**
     * checks if this entity collides with another entity
     * @param other another entity 
     * @return true if entity does collide with another entity; false otherwise
     */
    public boolean collidesWith(Entity other);
}

