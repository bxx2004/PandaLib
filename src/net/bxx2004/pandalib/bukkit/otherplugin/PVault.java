package net.bxx2004.pandalib.bukkit.otherplugin;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

/**
 * 如果服务器装有Vault插件则可以使用!
 */
public class PVault {
    private static Economy economy = null;
    private static Chat chat = null;
    private static Permission permission = null;

    /**
     * 注册Vault
     */
    public static void register(){
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        RegisteredServiceProvider<Chat> ch = getServer().getServicesManager().getRegistration(Chat.class);
        if (ch != null) {
            chat = ch.getProvider();
        }
        RegisteredServiceProvider<Permission> per = getServer().getServicesManager().getRegistration(Permission.class);
        if (per != null) {
            permission = per.getProvider();
        }
    }

    /**
     * 返回Vault的Economy对象
     * @return Economy
     */
    public static Economy getEconomy(){
        return economy;
    }
    /**
     * 返回Vault的Chat对象
     * @return Chat
     */
    public static Chat getChat(){
        return chat;
    }
    /**
     * 返回Vault的Permission对象
     * @return Permission
     */
    public static Permission getPermission(){
        return permission;
    }

    /**
     * 给予某玩家经济
     * @param player 玩家
     * @param value 数值
     */
    public static void addEconomy(OfflinePlayer player, double value){
        economy.depositPlayer(player, value);
    }
    /**
     * 删除某玩家经济
     * @param player 玩家
     * @param value 数值
     */
    public static void removeEconomy(OfflinePlayer player, double value){
        economy.withdrawPlayer(player, value);
    }

    /**
     * 查询某玩家经济
     * @param player 玩家
     * @return 余额
     */
    public static double checkEconomy(OfflinePlayer player){
        return economy.getBalance(player);
    }

    /**
     * 查询玩家银行余额
     * @param player 玩家
     * @return 余额
     */
    public static double checkBank(OfflinePlayer player){
        return economy.bankBalance(player.getName()).balance;
    }

    /**
     * 删除玩家银行余额
     * @param player 玩家
     * @param value 数值
     */
    public static void removeBank(OfflinePlayer player, double value){
        economy.bankWithdraw(player.getName(), value);
    }
    /**
     * 增加玩家银行余额
     * @param player 玩家
     * @param value 数值
     */
    public static void addBank(OfflinePlayer player, double value){
        economy.bankDeposit(player.getName(), value);
    }

    /**
     * 查询玩家前缀
     * @param player 玩家
     * @return 前缀
     */
    public static String getPlayerPrefix(Player player){
        return chat.getPlayerPrefix(player);
    }
    /**
     * 设置玩家前缀
     * @param player 玩家
     * @param prefix 前缀
     */
    public static void getPlayerPrefix(Player player,String prefix){
        chat.setPlayerPrefix(player, prefix);
    }

    /**
     * 查询玩家后缀
     * @param player 玩家
     * @return 后缀
     */
    public static String getPlayerSuffix(Player player){
        return chat.getPlayerSuffix(player);
    }
    /**
     * 设置玩家后缀
     * @param player 玩家
     * @param prefix 后缀
     */
    public static void getPlayerSuffix(Player player,String prefix){
        chat.setPlayerSuffix(player, prefix);
    }

    /**
     * 添加权限给玩家
     * @param player 玩家
     * @param value 值
     */
    public static void addPermission(Player player, String value){
        permission.playerAdd(player,value);
    }
    /**
     * 删除权限给玩家
     * @param player 玩家
     * @param value 值
     */
    public static void removePermission(Player player, String value){
        permission.playerRemove(player,value);
    }

    /**
     * 玩家是否拥有权限
     * @param player 玩家
     * @param value 权限
     * @return 是否拥有
     */
    public static boolean hasPermission(Player player, String value){
        return permission.has(player, value);
    }

    /**
     * 获取玩家所在组
     * @param player 玩家
     * @return 组
     */
    public static String getGroup(Player player){
        return permission.getPrimaryGroup(player);
    }
    /**
     * 添加权限给组
     * @param group 组
     * @param value 值
     */
    public static void addGroupPermisson(String group, String value){
        permission.groupAdd((World) null,group, value);
    }
    /**
     * 删除权限给组
     * @param group 组
     * @param value 值
     */
    public static void removeGroupPermisson(String group, String value){
        permission.groupRemove((World) null,group, value);
    }

    /**
     * 检测组是否拥有权限
     * @param group 组
     * @param value 值
     * @return 是否拥有
     */
    public static boolean hasGroupPermission(String group, String value){
        return permission.groupHas((World) null, group, value);
    }

    /**
     * 转移玩家到某个组
     * @param player 玩家
     * @param group 组
     */
    public static void playerOfGroup(Player player,String group){
        permission.playerAddGroup(player,group);
    }
}
