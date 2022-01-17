package renderEngine;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL11.*;

public class DisplayManager {

    private static long window;
    private static float xPos, yPos;
    private static boolean isClicked;
    private static int width, height;
    private static int oldMouseState = GLFW_RELEASE;

    public static void createDisplay(int inWidth, int inHeight, String title) {

        width = inWidth;
        height = inHeight;

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
                glfwSetWindowShouldClose(window, true);
        });

        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
            xPos = (float) xpos;
            yPos = (float) ypos;
        });

        glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_LEFT) {
                if (action == GLFW_PRESS && oldMouseState == GLFW_RELEASE) {
                    isClicked = true;
                }
                oldMouseState = action;
            }
        });

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        glfwShowWindow(window);
        GL.createCapabilities();
        glfwSetFramebufferSizeCallback(window, (window, w, h) -> {
            glViewport(0, 0, w, h);
            width = w;
            height = h;
        });
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void updateDisplay() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public static void closeDisplay() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static boolean isCloseRequested() {
        return glfwWindowShouldClose(window);
    }

    public static float getXPos() {
        return xPos;
    }

    public static float getYPos() {
        return yPos;
    }

    public static boolean isClicked() {
        return isClicked;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setClick(boolean b) {
        isClicked = b;
    }

    public static void requestClose() {
        glfwSetWindowShouldClose(window, true);
    }

}
