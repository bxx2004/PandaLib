package net.bxx2004.pandalib.bukkit.plistener;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Set;

/**
 * OP发生变动事件
 * @since 1.5.4
 */
public class PAdministratorChangEvent extends Event{
    private static final HandlerList handlers = new HandlerList();
    private Set<OfflinePlayer> old;
    private Set<OfflinePlayer> newops;
    private OfflinePlayer player;
    public PAdministratorChangEvent(Set<OfflinePlayer> old, Set<OfflinePlayer> ne,OfflinePlayer newop){
        this.old = old;
        this.newops = ne;
        this.player = newop;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * 获取没发生变动之前的OP列表
     * @return 旧的OP列表
     */
    public Set<OfflinePlayer> getOldAdmins() {
        return old;
    }
    /**
     * 获取发生变后的OP列表
     * @return 新的OP列表
     */
    public Set<OfflinePlayer> getNewAdmins() {
        return newops;
    }

    /**
     * 获取涉及此次变化成为OP的玩家
     * @return 此次事件成为OP的玩家
     */
    public OfflinePlayer getThisPlayer() {
        return player;
    }
}
