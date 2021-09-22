package engineTester;

import chess.Board;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.createDisplay(1000, 1000, "Dice Chess");

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = { -0.5f, 0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f };

        int[] indices = { 0, 1, 3, 3, 1, 2 };

        float[] textureCoords = { 3.0f / 8, 0.5f, 3.0f / 8, 1, 4f / 8, 1, 4f / 8, 0.5f };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("res/chess_texture_atlas.png"));
        TexturedModel texturedModel = new TexturedModel(model, texture);
        Board board = new Board(texture);

        while (!DisplayManager.isCloseRequested()) {
            renderer.prepare();
            shader.start();
            for (TexturedModel square : board.getSquares()) {
                renderer.render(square);
            }
            // renderer.render(texturedModel);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}