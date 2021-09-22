package renderEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

import models.RawModel;
import models.TexturedModel;

public class Renderer {

    public void prepare() {
        glClearColor(1, 0, 0, 1);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void render(TexturedModel texturedModel) {
        RawModel model = texturedModel.getRawModel();
        glBindVertexArray(model.getVaoID());
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
        glDrawElements(GL_TRIANGLES, model.getIndexCount(), GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
}
