package net.bxx2004.pandalib.bukkit.plistener.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * PandaLib拓展事件接口
 */
public abstract class PandaLibExtendEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public abstract boolean isCancelled();

    @Override
    public abstract void setCancelled(boolean bl);

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    /**
     * 通信一个事件
     */
    public static void callPandaLibEvent(PandaLibExtendEvent event){
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }
}
