package net.bxx2004.pandalib.bukkit.party.element;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * 代表一个大厅
 */
public class Lobby {
    private Location location;
    public Lobby(Location location){
        this.location = location;
    }
    public void teleport(Player player){
        player.teleport(location);
    }
}
