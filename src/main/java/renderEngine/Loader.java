package renderEngine;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import models.RawModel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Loader {

    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices) {
        int vaoID = createVAO();
        vaos.add(vaoID);
        bindIndicesBuffer(indices);
        storeDataInAttributes(0, 3, positions, 12);
        storeDataInAttributes(1, 2, textureCoords, 8);
        unbindVAO();
        return new RawModel(vaoID, positions.length / 3, indices.length);
    }

    private int createVAO() {
        int vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributes(int attributeNumber, int coordsSize, float[] data, int size) {
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, coordsSize, GL_FLOAT, false, size, 0);
        glEnableVertexAttribArray(attributeNumber);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private void unbindVAO() {
        glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public void cleanUp() {
        for (int vao : vaos) {
            glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos) {
            glDeleteBuffers(vbo);
        }
        for (int texture : textures) {
            glDeleteTextures(texture);
        }
    }

    public int loadTexture(String path) {
        ByteBuffer image;
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            stbi_set_flip_vertically_on_load(true);
            image = stbi_load(path, w, h, comp, 4);
            if (image == null) {
                throw new RuntimeException(
                        "Failed to load a texture file!" + System.lineSeparator() + stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        int textureID = glGenTextures();
        textures.add(textureID);
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        glGenerateMipmap(GL_TEXTURE_2D);
        stbi_image_free(image);

        return textureID;
    }
}
