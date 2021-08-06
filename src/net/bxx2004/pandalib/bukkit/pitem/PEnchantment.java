package net.bxx2004.pandalib.bukkit.pitem;

import net.bxx2004.pandalib.bukkit.plistener.PEnchantRegisterEvent;
import net.bxx2004.pandalib.bukkit.plistener.PListener;
import net.bxx2004.pandalib.bukkit.putil.PPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义附魔抽象类(直接继承就好)
 * @since 1.4.2
 */
public abstract class PEnchantment{
    public static List<PEnchantment> enchantments = new ArrayList<>();
    public PEnchantment(){
        super();
    }

    /**
     * 获得注册该附魔的插件名称
     * @return 附魔
     */
    public abstract String getPluginName();
    /**
     * 获取该附魔最小等级
     * @return 最小等级
     */
    public abstract int getMinLevel();

    /**
     * 获取该附魔最大等级
     * @return 最大等级
     */
    public abstract int getMaxLevel();

    /**
     * 获取该附魔的附魔书
     * @return 附魔书物品堆
     */
    public abstract CustomItem getEnchantBook();

    /**
     * 为该附魔添加实现方法
     * @return 实现方法
     */
    public abstract PListener getPListener();

    /**
     * 获取该附魔名
     * @return 附魔名
     */
    public abstract String getEnchantName();

    /**
     * 该附魔是否可以出现在附魔台
     * @return 是否
     */
    public abstract PEnchantTable canRegisterEnchantTable();

    /**
     * 该附魔是否可以应用于铁砧
     * @return 是否
     */
    public abstract boolean canUseOfAnvil();

    /**
     * 应用类型
     * @return 应用类型
     */
    public abstract SlotType getSlotType();

    /**
     * 返回附魔颜色
     * @return 附魔文本颜色
     */
    public abstract ChatColor getEnchantColor();
    /**
     * 返回附魔描述
     * @return 附魔描述
     */
    public abstract String getText();

    /**
     * 如果是自定义应用类型 请返回自定义应用的物品
     * @return 应用物品
     */
    public CustomItem[] getCustomItem(){
        return null;
    };
    /**
     * 注册该附魔
     * @return 注册是否成功
     */
    public boolean register(){
        try {
            enchantments.add(this);
            this.getPListener().hook(PPlugin.getPlugin(this.getPluginName()));
            PEnchantRegisterEvent e = new PEnchantRegisterEvent(this);
            Bukkit.getServer().getPluginManager().callEvent(e);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 应用类型枚举
     */
    public static enum SlotType{
        /**
         * 头部
         */
        HELMET,
        /**
         * 胸部
         */
        CHESTPLATE,
        /**
         * 腿部
         */
        LEGGINGS,
        /**
         * 脚部
         */
        BOOTS,
        /**
         * 剑
         */
        SWORD,
        /**
         * 斧头
         */
        AXE,
        /**
         * 镐头
         */
        PICKAXE,
        /**
         * 锄头
         */
        HOE,
        /**
         * 铲子
         */
        SPADE,
        /**
         * 所有物品
         */
        ALL_ITEM,
        /**
         * 自定义物品
         */
        CUSTOM_ITEM
    }

    /**
     * 通过附魔名称获得附魔
     * @param enchantName 附魔名称
     * @return 附魔
     */
    public static PEnchantment getPEnchantByEnchantName(String enchantName){
        for (PEnchantment enchantment : enchantments){
            if (enchantment.getEnchantName().equals(enchantName)){
                return enchantment;
            }
        }
        return null;
    }

    /**
     * 通过某玩家某位置获取是否持有该附魔
     * @param player 玩家
     * @param slot 位置
     * @param name 附魔名称
     * @return 是否有
     */
    public static boolean hasEnchant(Player player, EquipmentSlot slot, String name){
        if (player.getInventory().getItem(slot) != null){
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.hasItemMeta() && stack.getItemMeta().hasLore()){
                for (String s : stack.getItemMeta().getLore()){
                    if (s.contains(name)){
                        return true;
                    }
                }
            }else {
                return false;
            }
        }
        return false;
    }
    /**
     * 通过某玩家某位置获取附魔等级
     * @param player 玩家
     * @param slot 位置
     * @param name 附魔名称
     * @return 是否有
     */
    public static int getLevel(Player player, EquipmentSlot slot, String name){
        if (hasEnchant(player,slot,name)){
            ItemStack stack = player.getInventory().getItem(slot);
            for (String s : stack.getItemMeta().getLore()){
                if (s.contains(name)){
                    return Integer.parseInt(s.split(" ")[1]);
                }
            }
        }
        return 0;
    }
}
