package net.bxx2004.pandalib.bukkit.party.element;

import net.bxx2004.pandalib.bukkit.party.PartyLib;
import org.bukkit.entity.Player;

/**
 * 代表一个队里面的玩家
 */
public class PartyPlayer {
    private Party party;
    private Player player;
    public PartyPlayer(Player player){
        this.party = PartyLib.playersData.get(player);
        this.player = player;
    }
    public PartyPlayer(Party party,Player player){
        this.party = party;
        this.player = player;
    }
    public Player cast(){
        return player;
    }
    public String getPlayerName(){
        return player.getName();
    }

    public Party getParty() {
        return party;
    }
}
