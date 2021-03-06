package GUI3D.GUI;

import org.lwjglx.util.vector.Matrix4f;
import GUI3D.shaders.ShaderProgram3D;

public class GUIShader extends ShaderProgram3D {

    private static final String VERTEX_FILE = "src/main/java/GUI3D/GUI/guiVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/GUI3D/GUI/guiFragmentShader.txt";
    private int location_transformationMatrix;

    public GUIShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocation() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

}
