package net.bxx2004.pandalib.bukkit.party.event;


import net.bxx2004.pandalib.bukkit.party.Group;
import org.bukkit.entity.Player;

public class GroupAddPlayerEvent extends ExtendEvent{
    private Player player;
    private Group group;
    private boolean cancel;
    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean bl) {
        this.cancel = cancel;
    }
    public GroupAddPlayerEvent(Player player, Group group){
        this.group = group;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Group getGroup() {
        return group;
    }
}
