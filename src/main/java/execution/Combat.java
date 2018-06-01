package execution;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entities.Entity;
import entities.HostileEntity;
import entities.Player;

import java.util.ArrayList;
import java.util.Comparator;

public class Combat {

    private Map map;
    private Player player;
    private HostileEntity enemy;
    private ArrayList<Entity> turnOrder;

    public Combat(Map map, Player player, HostileEntity enemy) {
        this.map = map;
        this.player = player;
        this.enemy = enemy;

        this.player.setInCombat(true);
        this.enemy.setInCombat(true);

        //TODO: Add Player's party to combat
        //TODO: Generate enemy party

        turnOrder.add(player);
        turnOrder.add(enemy);
        turnOrder.sort(Comparator.comparingInt(Entity::getMovement));
    }

    public void render(SpriteBatch sb) {
        this.map.render(sb);
        this.player.setPosition(0, 0);
        this.enemy.setPosition(map.getNumberOfTilesX() * Tile.WIDTH, map.getNumberOfTilesY() * Tile.WIDTH);
    }

    public void combat() {
        // while player alive and enemies alive
        turnOrder.forEach(Entity::executeTurn);
        // end while

        // rewards on success or reload if failed
    }
}
