package net.bxx2004.pandalib.bukkit.party.element;

import net.bxx2004.pandalib.bukkit.plistener.PListener;
import net.bxx2004.pandalib.bukkit.party.Group;
import net.bxx2004.pandalib.bukkit.party.PartyLib;
import net.bxx2004.pandalib.bukkit.party.event.ExtendEvent;
import net.bxx2004.pandalib.bukkit.party.event.PartyAddPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 代表一个队伍
 */
public class Party {
    private Group group;
    private String partyName;
    private int playerSize;
    private Lobby lobby;
    private PListener listener;
    private List<PartyPlayer> players;
    public Party(Group group,String partyName, int playerSize){
        this.partyName = partyName;
        this.playerSize = playerSize;
        this.group = group;
        this.players = new ArrayList<>();
    }
    public String getPartyName(){
        return this.partyName;
    }
    public int getPlayerSize(){
        return playerSize;
    }
    public void addPlayer(String playerName){
        ExtendEvent.callPandaLibEvent(new PartyAddPlayerEvent(this,Bukkit.getPlayer(playerName)));
        players.add(new PartyPlayer(this, Bukkit.getPlayer(playerName)));
        PartyLib.playersData.put(Bukkit.getPlayer(playerName),this);
    }
    public void removePlayer(PartyPlayer player){
        players.remove(player);
        PartyLib.playersData.remove(player);
    }
    public Group getGroup(){
        return this.group;
    }
    public List<PartyPlayer> getPartyPlayers(){
        return players;
    }
    public PartyPlayer getPartyPlayer(String playerName){
       for (PartyPlayer player : players){
           if (player.getPlayerName().equals(playerName)){
               return player;
           }
       }
       return null;
    }
    public Lobby getLobby(){
        return lobby;
    }
    public boolean hasLobby(){
        return lobby == null ? false : true;
    }
    public void setLobby(Location lobby) {
        this.lobby = new Lobby(lobby);
    }
    public void setDamage(boolean damage){
        if (damage){
            this.listener = new PListener(){
              @EventHandler
              public void onDamage(EntityDamageByEntityEvent event){
                  if (event.getDamager() instanceof Player){
                      Player damager = (Player) event.getDamager();
                      if (event.getEntity() instanceof Player){
                          Player entity = (Player) event.getEntity();
                          List<String> names = new ArrayList<>();
                          for (PartyPlayer player : players){
                              names.add(player.getPlayerName());
                          }
                          if (names.contains(damager.getName()) && names.contains(entity.getName())){
                              event.setCancelled(true);
                          }
                      }
                  }
              }
            };
            this.listener.hook("Vault");
        }else {
            this.listener.unhook();
        }
    }
}
