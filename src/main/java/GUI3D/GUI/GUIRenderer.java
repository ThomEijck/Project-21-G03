package GUI3D.GUI;

import GUI3D.engine.graphics.Loader;
import GUI3D.engine.graphics.models.RawModel;
import GUI3D.maths.Maths;
import org.lwjgl.opengl.*;
import org.lwjglx.util.vector.Matrix4f;

import java.util.List;


public class GUIRenderer {

    private final RawModel quad;
    private GUIShader shader;


    public GUIRenderer(Loader loader){
        float[] positions = {-1,1,-1,-1,1,1,1,-1};
        quad = loader.loadToVAO(positions, 2);
        shader = new GUIShader();
    }


    public void render(List<GUITexture> guis){
        shader.start();
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL46.glEnable(GL46.GL_BLEND);
        GL46.glBlendFunc(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA);
        GL46.glDisable(GL46.GL_DEPTH_TEST);

        for(GUITexture gui: guis){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
            Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
            shader.loadTransformation(matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }
        GL46.glEnable(GL46.GL_DEPTH_TEST);
        GL46.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

    public void cleanUp (){
        shader.cleanUp();
    }


}
