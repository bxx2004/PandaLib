package net.bxx2004.pandalib.bukkit.party;

import net.bxx2004.pandalib.bukkit.party.element.Party;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class PartyLib {
    public static HashMap<Player, Party> playersData = new HashMap<Player, Party>();;
    private Group group;
    private Party[] parties;
    public PartyLib(){

    }

    /**
     * 第一步
     * @return
     */
    public static PartyLib register(){
        return new PartyLib();
    }
    public PartyLib inputGroup(String groupName, int partySize, Location lobby){
        this.group = new Group(groupName,partySize);
        if (lobby != null){
            group.setLobby(lobby);
        }
        return this;
    }

    /**
     * 第二步
     * @param eachPlayerSize 每个队伍的玩家数量
     * @param lobby 每个队伍的大厅
     * @param partyName 每个队伍的名字
     * @return
     */
    public PartyLib inputParty(int eachPlayerSize, Location[] lobby ,String... partyName){
        parties = new Party[partyName.length];
        for (int i = 0; i< partyName.length; i++){
            parties[i] = new Party(this.group,partyName[i],eachPlayerSize);
            if (lobby != null){
                parties[i].setLobby(lobby[i]);
            }
        }
        return this;
    }

    /**
     * 第三步
     * @param auto 组满员后是否自动分配队伍
     * @return 一个组
     */
    public Group endSetting(boolean auto){
        for (Party party : parties){
            group.addParty(party);
        }
        if (auto){
            new BukkitRunnable(){
                @Override
                public void run() {
                    if (group.isFull()){
                        for (Player p  : group.getAllPlayers()){
                            if (group.getPlayerState(p) != Group.PlayerState.READY){
                                return;
                            }
                        }
                        group.distributeParty();
                        cancel();
                    }
                }
            }.runTaskTimerAsynchronously(Bukkit.getPluginManager().getPlugin("Vault"), 0,20);
        }
        return group;
    }
}
