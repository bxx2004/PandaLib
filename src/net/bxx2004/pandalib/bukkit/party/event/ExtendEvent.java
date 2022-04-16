package net.bxx2004.pandalib.bukkit.party.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 拓展事件
 */
public abstract class ExtendEvent extends Event implements Cancellable {
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
    public static void callPandaLibEvent(ExtendEvent event){
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }
}