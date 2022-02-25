package net.bxx2004.pandalib.bukkit.plistener.event;

import net.bxx2004.pandalib.bukkit.pitem.PEnchantment;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 自定义附魔被注册时触发该事件
 * @since 1.4.2
 */
public class PEnchantRegisterEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private PEnchantment enchantment;
    public PEnchantRegisterEvent(PEnchantment enchantment){
        this.enchantment = enchantment;
    }

    /**
     * 获得注册的附魔附魔
     * @return 所有注册的附魔
     */
    public PEnchantment getEnchantments() {
        return enchantment;
    }

    /**
     * 事件是否被取消
     * @return 取消
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * 事件取消
     * @param b 取消
     */
    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
