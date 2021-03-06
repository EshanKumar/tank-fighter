package in.shabhushan.tankfighter.game.model;

import in.shabhushan.tankfighter.game.engine.GameEngine;
import in.shabhushan.tankfighter.game.enumeration.Direction;
import in.shabhushan.tankfighter.game.enumeration.ObjectType;

import java.awt.Graphics2D;
import java.awt.Color;

/**
 * This is a Game Object, which all the model in the Game that are going to be placed, inherits from.
 * It provides some common methods across different types of GameObjects viz position, speed, direction's Getter
 * and Setters, {@link #update()} and {@link #draw(Graphics2D)}
 *
 * @author Shashi Bhushan
 * @date 16/9/18
 */
public interface GameObject {

    /**
     * Sets the vertical position of Game Object in Playing Field
     * @param verticalPosition the vertical position to set to
     */
    public void setVerticalPosition(int verticalPosition);

    /**
     * Returns the vertical position of the game object in Playing Field
     * @return the vertical position of game object in playing field
     */
    public int getVerticalPosition();

    /**
     * Sets the horizontal position of Game Object in Playing Field
     * @param horizontalPosition the horizontal position to set to
     */
    public void setHorizontalPosition(int horizontalPosition);

    /**
     * Returns the vertical position of the Game object in Playing Field
     * @return the vertical position of the game object in playing field
     */
    public int getHorizontalPosition();

    /**
     * Returns {@link Direction} of the Game Object w.r.t Playing Field
     * @return {@link Direction} of the game object
     */
    public Direction getDirection();

    /**
     * Sets the {@link Direction} of the Game object w.r.t Playing Field
     * @param direction the {@link Direction} of the game object
     */
    public void setDirection(Direction direction);

    /**
     * Sets {@link GameEngine} for this Game Object.
     * @param game The {@link GameEngine} for this Game Object
     */
    public void setGame(GameEngine game);

    /**
     * Returns the {@link GameEngine} for this Game Object.
     * It can be used to fetch properties pertaining to the Game viz. window dimensions etc
     * @return {@link GameEngine} for this Game Object
     */
    public GameEngine getGame();

    /**
     * Sets the {@link Color} for this Game Object
     * @param color {@link Color} of this Game Object
     */
    public void setColor(Color color);

    /**
     * Returns the {@link Color} of this Game Object
     * @return {@link Color} of this game object
     */
    public Color getColor();

    /**
     * Sets {@link ObjectType} for this Game Object
     * @param objectType the {@link ObjectType} for this Game Object
     */
    public void setObjectType(ObjectType objectType);

    /**
     * Returns the {@link ObjectType} for this Game Object
     * @return the {@link ObjectType} for this Game Object
     */
    public ObjectType getObjectType();

    /**
     * Sets speed for this game object
     * @param speed the speed of this game object
     */
    public void setSpeed(int speed);

    /**
     * Returns the speed of this Game Object
     * @return the speed of this game object
     */
    public int getSpeed();

    /**
     * Update Position, Speed, Direction etc of Game Object
     */
    public void update();

    /**
     * Draw Object based on it's position and Direction
     * @param graphics
     */
    public void draw(Graphics2D graphics);

    /**
     * TODO: Add Support for Non Square objects
     * Updates the objectSize
     */
    public void setObjectSize(int objectSize);

    /**
     * Returns the Object Size in Game Grid
     * @return
     */
    public int getObjectSize();

    /**
     * Occupied the Space in Game Grid
     */
    public void occupySpace();

    /**
     * Vacants the Space in Game Grid
     */
    public void vacantSpace();
}
