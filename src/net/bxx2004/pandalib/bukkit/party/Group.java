package net.bxx2004.pandalib.bukkit.party;

import net.bxx2004.pandalib.bukkit.party.element.Lobby;
import net.bxx2004.pandalib.bukkit.party.element.Party;
import net.bxx2004.pandalib.bukkit.party.event.ExtendEvent;
import net.bxx2004.pandalib.bukkit.party.event.GroupAddPlayerEvent;
import net.bxx2004.pandalib.bukkit.party.event.GroupDistributeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 代表一个组
 */
public class Group {
    private String groupName;
    private int partySize;
    private GroupState state;
    private Lobby lobby;
    private HashMap<String,PlayerState> players;
    private List<Party> parties;
    public Group(String groupName,int partySize){
        this.groupName = groupName;
        this.partySize = partySize;
        this.state = GroupState.WAIT;
        this.players = new HashMap<String, PlayerState>();
        this.parties = new ArrayList<Party>();
    }
    public String getGroupName(){
        return this.groupName;
    }
    public void addPlayer(Player player){
        ExtendEvent.callPandaLibEvent(new GroupAddPlayerEvent(player,this));
        players.put(player.getName(), PlayerState.OFFHAND);
        getLobby().teleport(player);
    }
    public void removePlayer(Player player){
        players.remove(player.getName());
    }
    public List<Player> getAllPlayers(){
        List<Player> list = new ArrayList<>();
        for (String name : players.keySet()){
            list.add(Bukkit.getPlayer(name));
        }
        return list;
    }
    public GroupState getState(){
        return state;
    }
    public Player getPlayer(String name){
        if (hasPlayer(Bukkit.getPlayer(name))){
            return Bukkit.getPlayer(name);
        }
        return null;
    }
    public PlayerState getPlayerState(Player player){
        return players.get(player.getName());
    }
    public void setPlayerState(Player player,PlayerState state){
        players.put(player.getName(),state);
    }
    public Group setLobby(Location lobby){
        this.lobby = new Lobby(lobby);
        return this;
    }
    public void addParty(String partyName, int playerSize){
        parties.add(new Party(this,partyName,playerSize));
    }
    public void addParty(Party party){
        parties.add(party);
    }
    public void removeParty(String partyName){
        for (Party party : parties){
            if (party.getPartyName().equals(partyName)){
                parties.remove(party);
                return;
            }
        }
    }
    public Party getParty(String name){
        for (Party party : parties){
            if (party.getPartyName().equals(name)){
                return party;
            }
        }
        return null;
    }
    public void setState(GroupState state){
        this.state = state;
    }
    public int getGroupPlayersSize(){
        return partySize * parties.get(0).getPlayerSize();
    }
    public Lobby getLobby(){
        return lobby;
    }
    public boolean hasLobby(){
        return lobby == null ? false : true;
    }
    public boolean hasPlayer(Player player){
        return players.keySet().contains(player.getName());
    }
    public List<Party> getAllParties(){
        return parties;
    }
    public enum GroupState{
        WAIT,
        DISTRIBUTE
    }
    public enum PlayerState{
        READY,
        OFFHAND
    }
    public boolean isFull(){
        if (getGroupPlayersSize() >= getAllPlayers().size()){
            return true;
        }
        return false;
    }
    public void distributeParty(){
        ExtendEvent.callPandaLibEvent(new GroupDistributeEvent(this));
        this.state = GroupState.DISTRIBUTE;
        List<Player> playerss = getAllPlayers();
        for (Player player : playerss){
            for (Party party: parties){
                if (party.getPlayerSize() != party.getPartyPlayers().size()){
                    party.addPlayer(player.getName());
                    setPlayerState(player, PlayerState.READY);
                    party.getLobby().teleport(player);
                }
            }
        }
    }
}
