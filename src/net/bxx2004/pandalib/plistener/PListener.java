package net.bxx2004.pandalib.plistener;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 * 简单的监听器
 */
public class PListener implements Listener {
    private boolean hook = false;
    /**
     * 挂在一个PListener监听器
     * @param plugin 插件
     * @return PListener
     */
    public PListener hook(Plugin plugin){
        if (!hook){
            Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
            hook = true;
        }
        return this;
    }

    /**
     * 注销这个监听器
     */
    public void unhook(){
        HandlerList.unregisterAll(this);
    }
}
