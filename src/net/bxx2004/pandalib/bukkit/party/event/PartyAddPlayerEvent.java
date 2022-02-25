package net.bxx2004.pandalib.bukkit.party.event;

import net.bxx2004.pandalib.bukkit.party.element.Party;
import org.bukkit.entity.Player;

public class PartyAddPlayerEvent extends ExtendEvent{
    private boolean c;
    private Player player;
    private Party party;
    @Override
    public void setCancelled(boolean bl) {
        this.c =bl;
    }

    @Override
    public boolean isCancelled() {
        return c;
    }
    public PartyAddPlayerEvent(Party party,Player player){
        this.party = party;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Party getParty() {
        return party;
    }
}
