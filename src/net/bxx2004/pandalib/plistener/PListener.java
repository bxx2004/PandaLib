package net.bxx2004.pandalib.plistener;

import net.bxx2004.pandalib.putil.PPlugin;
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
    @Deprecated
    public PListener hook(Plugin plugin){
        if (!hook){
            Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
            hook = true;
        }
        return this;
    }
    /**
     * 挂在一个PListener监听器
     * @param pluginName 插件名称
     */
    public void hook(String pluginName){
        if (!hook){
            Bukkit.getServer().getPluginManager().registerEvents(this, PPlugin.getPlugin(pluginName));
            hook = true;
        }
    }
    /**
     * 注销这个监听器
     */
    public void unhook(){
        HandlerList.unregisterAll(this);
    }
}
