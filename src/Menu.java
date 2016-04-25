import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class Menu {

    private static Texture menuImage;
    
    public static void run() {
    	try {
            Display.setDisplayMode(new DisplayMode(1250, 937));
            Display.setTitle("Texture Demo");

        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
        try {
            // Load the menuImage texture from "res/images/menuImage.png"
            menuImage = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/menuImage.png")));
        } catch (IOException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
//        glMatrixMode(GL_PROJECTION);
//        glOrtho(0, 640, 480, 0, 1, -1);
//        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        boolean spaceTapped = false;
        while (!spaceTapped) {
        	if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
     		   Display.destroy();
            	   System.exit(1);
     	    }
     	    if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
     	    	spaceTapped = true;
     	    }
            glClear(GL_COLOR_BUFFER_BIT);
            menuImage.bind();
//            glBegin(GL_TRIANGLES);
//            glTexCoord2f(1, 0);
//            glVertex2i(450, 10);
//            glTexCoord2f(0, 0);
//            glVertex2i(10, 10);
//            glTexCoord2f(0, 1);
//            glVertex2i(10, 450);
//            glTexCoord2f(0, 1);
//            glVertex2i(10, 450);
//            glTexCoord2f(1, 1);
//            glVertex2i(450, 450);
//            glTexCoord2f(1, 0);
//            glVertex2i(450, 10);
//            glEnd();
                        glBegin(GL_QUADS);
                        glTexCoord2f(0, 0);
                        glVertex2i(0, 0); // Upper-left
                        glTexCoord2f(1, 0);
                        glVertex2i(1850, 0); // Upper-right
                        glTexCoord2f(1, 1);
                        glVertex2i(1850, 1850); // Bottom-right
                        glTexCoord2f(0, 1);
                        glVertex2i(0,1850 ); // Bottom-left
                        glEnd();
            Display.update();
            Display.sync(60);
        }
        // Release the resources of the menuImage texture
        menuImage.release();
    }
}