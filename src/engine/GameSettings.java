package engine;

import org.joml.Vector2i;
import org.joml.Vector3f;

/**
 * Represents the current setting of a game
 */
public class GameSettings {

    public Vector2i windowDimensions;
    public Vector3f backgroundColor;

    public int resolutionX;
    public int resolutionY;
    public long targetFps;
}
