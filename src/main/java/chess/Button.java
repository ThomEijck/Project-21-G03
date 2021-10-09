package chess;

import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import textures.ModelTexture;

public class Button {

    Loader loader = new Loader();
    TexturedModel tm;
    float x, y, width, height;
    boolean enabled = true;

    public Button(float x, float y, float width, float height, ModelTexture mt) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        int[] indices = { 0, 1, 3, 1, 2, 3 };
        float[] textureCoords = { 1, 1, 1, 0, 0, 0, 0, 1 };
        float xCoord = x * 2 / DisplayManager.getWidth() - 1;
        float yCoord = y * 2 / DisplayManager.getHeight() - 1;
        float vWidth = width * 2 / DisplayManager.getWidth();
        float vHeight = height * 2 / DisplayManager.getHeight();
        float[] vertices = { xCoord + vWidth, yCoord, 0, xCoord + vWidth, yCoord - vHeight, 0, xCoord, yCoord - vHeight,
                0, xCoord, yCoord, 0 };
        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        tm = new TexturedModel(model, mt);
    }

    public TexturedModel getTexturedModel() {
        return tm;
    }

    public boolean isClicked(float mouseX, float mouseY) {
        if (!enabled)
            return false;
        if (mouseX <= x + width && mouseX >= x) {
            if (mouseY >= y - height && mouseY <= y) {
                return true;
            }
        }
        return false;
    }

    public void setEnabled(boolean b) {
        enabled = b;
    }

    public boolean getEnabled() {
        return enabled;
    }
}
