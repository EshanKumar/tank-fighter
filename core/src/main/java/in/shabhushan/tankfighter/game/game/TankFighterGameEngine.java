package in.shabhushan.tankfighter.game.game;

import in.shabhushan.tankfighter.game.engine.GameEngine;
import in.shabhushan.tankfighter.game.engine.Handler;
import in.shabhushan.tankfighter.game.enumeration.ObjectType;
import in.shabhushan.tankfighter.game.model.Bomb;
import in.shabhushan.tankfighter.game.model.Bullet;
import in.shabhushan.tankfighter.game.model.builder.EnemyTankBuilder;
import in.shabhushan.tankfighter.game.model.builder.PlayerTankBuilder;
import in.shabhushan.tankfighter.game.model.Tank;
import in.shabhushan.tankfighter.game.util.GameUtil;

import java.util.List;
import java.awt.*;
import java.util.ListIterator;

import static in.shabhushan.tankfighter.game.enumeration.Direction.DOWN;
import static in.shabhushan.tankfighter.game.enumeration.Direction.UP;
import static in.shabhushan.tankfighter.game.util.Defaults.*;

/**
 * @author Shashi Bhushan
 * @date 17/9/18
 */
public class TankFighterGameEngine extends GameEngine {

    private Handler<Tank> handler;

    private Handler<Tank> enemyTankHandler;

    private Handler<Bomb> bombsHandler;

    public TankFighterGameEngine(Dimension resolution) {
        super(resolution);
        handler = new Handler<>();
        enemyTankHandler = new Handler<>();
        bombsHandler = new Handler<>();

        Tank playerTank = new PlayerTankBuilder(
                (int) resolution.getWidth() / 2,(int) resolution.getHeight() / 2,
                        ObjectType.PLAYER_TANK, DEFAULT_TANK_OBJECT_SIZE, this)
                .setSpeed(DEFAULT_PLAYER_TANK_SPEED)
                .setColor(DEFAULT_PLAYER_TANK_COLOR)
                .setDirection(UP)
                .build();


        handler.addObject(playerTank);

        for(int index = 0; index < DEFAULT_AI_TANK_NUMBER; index++) {
            Tank enemyTank = new EnemyTankBuilder(
                        100 * (index + 1), (int) resolution.getHeight() / 4,
                        ObjectType.ENEMY_TANK, DEFAULT_TANK_OBJECT_SIZE, this)
                    .setDirection(DOWN)
                    .setSpeed(DEFAULT_AI_TANK_SPEED)
                    .setColor(DEFAULT_AI_TANK_COLOR)
                    .build();

            // Add to Handler
            enemyTankHandler.addObject(enemyTank);

            // Add to Executor Thread Pool
            executorService.execute(enemyTank);
        }
    }

    @Override
    public void update() {
        handler.update();
        enemyTankHandler.update();
        bombsHandler.update();
    }

    @Override
    public void draw() {
        super.draw();

        handler.draw(drawGraphics);

        enemyTankHandler.draw(drawGraphics);

        bombsHandler.draw(drawGraphics);
    }

    public Tank getPlayerTank() {
        return handler.getGameObjects().get(0);
    }

    public List<Tank> getEnemyTanks() {
        return enemyTankHandler.getGameObjects();
    }

    @Override
    public void checkForCollisions() {
        // Check if Player's Bullet has hit any enemy tank
        ListIterator<Bullet> playerBulletIterator = getPlayerTank().getBullets().listIterator();
        while(playerBulletIterator.hasNext()) {
            Bullet playerBullet = playerBulletIterator.next();

            ListIterator<Tank> enemyTanksIterator = enemyTankHandler.getGameObjects().listIterator();
            while(enemyTanksIterator.hasNext()) {
                Tank enemyTank = enemyTanksIterator.next();

                /*
                System.out.println("Is Tank Hit by Bullet : " + GameUtil.isTankHitByBullet(enemyTank, playerBullet));
                System.out.println("Tank : " + enemyTank.getVerticalPosition() + " Bullet : " + playerBullet.getVerticalPosition() + " | " +
                        objectWithinTankBoundary(enemyTank.getHorizontalPosition(), playerBullet.getHorizontalPosition()) + " | " +
                        objectWithinTankBoundary(enemyTank.getVerticalPosition(), playerBullet.getVerticalPosition()));
                */
                if(GameUtil.isTankHitByBullet(enemyTank, playerBullet)) {
                    // Create a Bomb Here
                    Bomb bomb = new Bomb(enemyTank.getHorizontalPosition(), enemyTank.getVerticalPosition(), this);
                    bombsHandler.addObject(bomb);
                    // Add to Executor Thread Pool
                    executorService.execute(bomb);

                    // Remove Player's Bullet
                    playerBulletIterator.remove();

                    enemyTank.destroy();
                    enemyTanksIterator.remove();

                    // enemyTank is x_x with the bullet
                    // Bullet is already removed, tank is destroyed no need to iterate for this bullet further
                    // check for next bullet
                    break;
                }
            }
        }

        // Check if Enemy Tank has hit player
        OUTER_LOOP:
        for(Tank enemyTank: enemyTankHandler.getGameObjects()) {
            ListIterator<Bullet> enemyBulletIterator = enemyTank.getBullets().listIterator();

            while(enemyBulletIterator.hasNext()) {
                Bullet enemyBullet = enemyBulletIterator.next();

                if(GameUtil.isTankHitByBullet(getPlayerTank(), enemyBullet)) {
                    enemyBulletIterator.remove();
                    // Remove Player Tank
                    // TODO: Uncomment when playing fairly :p
                    // handler.getGameObjects().remove(getPlayerTank());

                    // break out of all loops, Player is x_x
                    break OUTER_LOOP;
                }
            }
        }

        ListIterator<Bomb> bombsIterator = bombsHandler.getGameObjects().listIterator();
        while(bombsIterator.hasNext()) {
            Bomb bomb = bombsIterator.next();

            if(!bomb.isLive()) {
                bombsIterator.remove();
            }
        }

        if(getEnemyTanks().isEmpty()) {
            gameFinished = true;
        }
    }
}
