package in.shabhushan.tankfighter.game.model.impl;


import in.shabhushan.tankfighter.game.engine.GameEngine;
import in.shabhushan.tankfighter.game.enumeration.Direction;
import in.shabhushan.tankfighter.game.enumeration.ObjectType;
import in.shabhushan.tankfighter.game.model.Bullet;
import in.shabhushan.tankfighter.game.model.Tank;
import in.shabhushan.tankfighter.game.model.builder.GenericTankBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static in.shabhushan.tankfighter.game.util.Defaults.DEFAULT_BULLET_COUNT;
import static in.shabhushan.tankfighter.game.util.Defaults.DEFAULT_TANK_BLOCK_DISTANCE;
import static in.shabhushan.tankfighter.game.util.Defaults.DEFAULT_TANK_BLOCK_WIDTH;
import static in.shabhushan.tankfighter.game.util.TankUtil.updateBulletsPosition;

/**
 * This is a Generic Implementation for Tank, which gives default functionality to the implementation Tank class
 *
 * TODO: Move in 8 Directions instead of 4
 */
public abstract class GenericTank extends GenericGameObject implements Tank {

    final protected List<Bullet> bullets = new ArrayList<>();

    protected boolean interrupted = false;

    protected int timeToSleep = 1000;

    protected boolean dead = false;

    public GenericTank(int positionX, int positionY, ObjectType objectType, GameEngine game) {
        super(positionX, positionY, objectType, game, DEFAULT_TANK_BLOCK_DISTANCE * 3);
    }

    public GenericTank(int positionX, int positionY, ObjectType objectType, GameEngine game, int speed, Color color) {
        this(positionX, positionY, objectType, game);

        setColor(color);
        setSpeed(speed);
        setObjectSize(DEFAULT_TANK_BLOCK_DISTANCE * 3);
    }


    public GenericTank(int positionX, int positionY, ObjectType objectType, GameEngine game, Direction direction) {
        this(positionX, positionY, objectType, game);

        setDirection(direction);
        setObjectSize(DEFAULT_TANK_BLOCK_DISTANCE * 3);
    }

    public GenericTank(GenericTankBuilder genericTankBuilder) {
        super(genericTankBuilder);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public void addBullet(Bullet bullet) {
        if(bullets.size() < DEFAULT_BULLET_COUNT) {
            bullets.add(bullet);
        }
    }

    @Override
    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    @Override
    public List<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public void update() {
        // bullets.removeIf(bullet -> !GameUtil.objectWithinBoundary(bullet, game));

        updateBulletsPosition(bullets, game);
    }

    /**
     * Draw Tank based on it's Direction
     *
     * use a Box Model to Build Tank on 9 Boxes
     * First ROW
     * graphics.fill3DRect(horizontalPosition, verticalPosition, width, width, false);
     * graphics.fill3DRect(horizontalPosition + size, verticalPosition, width, width, false);
     * graphics.fill3DRect(horizontalPosition + 2 * size, verticalPosition, width, width, false);
     *
     * Second ROW
     * graphics.fill3DRect(horizontalPosition, verticalPosition + size, width, width, false);
     * graphics.fill3DRect(horizontalPosition + size, verticalPosition + size, width, width, false);
     * graphics.fill3DRect(horizontalPosition + 2 * size, verticalPosition + size, width, width, false);
     *
     * Third ROW
     * graphics.fill3DRect(horizontalPosition, verticalPosition + 2 * size, width, width, false);
     * graphics.fill3DRect(horizontalPosition + size, verticalPosition + 2 * size, width, width, false);
     * graphics.fill3DRect(horizontalPosition + 2 * size, verticalPosition + 2 * size, width, width, false);
     *
     * @param graphics
     */
    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);

        int width = DEFAULT_TANK_BLOCK_WIDTH;
        int size = DEFAULT_TANK_BLOCK_DISTANCE;

        switch (direction) {
            case UP:
                graphics.fill3DRect(horizontalPosition + size, verticalPosition, width, width, false);

                graphics.fill3DRect(horizontalPosition, verticalPosition + size, width, width, false);
                graphics.fill3DRect(horizontalPosition + size, verticalPosition + size, width, width, false);
                graphics.fill3DRect(horizontalPosition + 2 * size, verticalPosition + size, width, width, false);

                graphics.fill3DRect(horizontalPosition, verticalPosition + 2 * size, width, width, false);
                graphics.fill3DRect(horizontalPosition + size, verticalPosition + 2 * size, width, width, false);
                graphics.fill3DRect(horizontalPosition + 2 * size, verticalPosition + 2 * size, width, width, false);
                break;

            case DOWN:
                graphics.fill3DRect(horizontalPosition, verticalPosition, width, width, false);
                graphics.fill3DRect(horizontalPosition + size, verticalPosition, width, width, false);
                graphics.fill3DRect(horizontalPosition + 2 * size, verticalPosition, width, width, false);

                graphics.fill3DRect(horizontalPosition, verticalPosition + size, width, width, false);
                graphics.fill3DRect(horizontalPosition + size, verticalPosition + size, width, width, false);
                graphics.fill3DRect(horizontalPosition + 2 * size, verticalPosition + size, width, width, false);

                graphics.fill3DRect(horizontalPosition + size, verticalPosition + 2 * size, width, width, false);
                break;

            case LEFT:
                graphics.fill3DRect(horizontalPosition + size, verticalPosition, width, width, false);

                graphics.fill3DRect(horizontalPosition + 2 * size, verticalPosition, width, width, false);
                graphics.fill3DRect(horizontalPosition, verticalPosition + size, width, width, false);
                graphics.fill3DRect(horizontalPosition + size, verticalPosition + size, width, width, false);

                graphics.fill3DRect(horizontalPosition + 2 * size, verticalPosition + size, width, width, false);
                graphics.fill3DRect(horizontalPosition + size, verticalPosition + 2 * size, width, width, false);
                graphics.fill3DRect(horizontalPosition + 2 * size, verticalPosition + 2 * size, width, width, false);
                break;

            case RIGHT:
                graphics.fill3DRect(horizontalPosition, verticalPosition, width, width, false);
                graphics.fill3DRect(horizontalPosition + size, verticalPosition, width, width, false);
                graphics.fill3DRect(horizontalPosition, verticalPosition + size, width, width, false);

                graphics.fill3DRect(horizontalPosition + size, verticalPosition + size, width, width, false);

                graphics.fill3DRect(horizontalPosition + 2 * size, verticalPosition + size, width, width, false);
                graphics.fill3DRect(horizontalPosition, verticalPosition + 2 * size, width, width, false);
                graphics.fill3DRect(horizontalPosition + size, verticalPosition + 2 * size, width, width, false);
                break;
        }
        for(Bullet bullet: bullets) {
            bullet.draw(graphics);
        }
    }

    /**
     * Do Nothing By Default in the Thread.
     */
    @Override
    public void run() {}

    /**
     * Vacate Space when Dying
     */
    @Override
    public void destroy() {
        vacantSpace();
    }
}
