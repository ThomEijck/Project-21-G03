package GUI3D.entities;

import GUI3D.engine.graphics.models.TexturedModel;
import org.jetbrains.annotations.NotNull;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import GUI3D.terrain.Terrain;


public class Player extends Entity {

    /**
     * create Player Entity
     * @param model
     * @param position
     * @param rotX
     * @param rotY
     * @param rotZ
     * @param scale
     */
    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }


    /**
     * move player to new position.
     * @param terrain Terrain, play field of game
     * @param newPos Vector2f, new position of chess pieces
     */
    public void move(@NotNull Terrain terrain, @NotNull Vector2f newPos){
        Vector3f newPosition = new Vector3f();
        newPosition.x = newPos.x;
        newPosition.z = newPos.y;
        newPosition.y = (float)1.75;

        super.setPosition(newPosition);
    }

}
