package net.bxx2004.pandalib.putil;

import net.bxx2004.java.reflect.PJMethod;
import net.bxx2004.java.reflect.PJVariable;
import net.bxx2004.java.reflect.ReflectUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * 关于NMS有点小用的工具
 */
public class PNMS {
    /**
     * 获取当前版本NMS的包名
     * @return 包名
     */
    @Deprecated
    public static String getVersionForNMS(){
        String[] versions = Bukkit.getBukkitVersion().split("\\.");
        String major = versions[0];
        String minor = versions[1];
        String NMSBaseHead = "net.minecraft.server.v" + major + "_" + minor + "_R";
        String lastv = "";
        for (int i = 1; i <= 9; i++) {
            String versionTest = NMSBaseHead + i;
            try {
                Class.forName(versionTest + ".ItemStack");
                lastv = NMSBaseHead + i;
                break;
            } catch (ClassNotFoundException ignored) {}
        }
        return lastv;
    }
    /**
     * 获取当前版本OBC的包名
     * @return 包名
     */
    public static String getVersionForOBC(){
        String[] versions = Bukkit.getBukkitVersion().split("\\.");
        String major = versions[0];
        String minor = versions[1];
        String NMSBaseHead = "org.bukkit.craftbukkit.v" + major + "_" + minor.split("[-]")[0] + "_R";
        String lastv = "";
        for (int i = 1; i <= 9; i++) {
            String versionTest = NMSBaseHead + i;
            try {
                Class.forName(versionTest + ".entity.CraftChicken");
                lastv = NMSBaseHead + i;
                break;
            } catch (ClassNotFoundException ignored) {}
        }
        return lastv;
    }

    /**
     * 获取CraftPlayer对象
     * @param player Player对象
     * @return CraftPlayer
     */
    public static Object getCraftPlayer(Player player){
        Object cplayer = ReflectUtils.getClass(getVersionForOBC() + ".entity.CraftPlayer").cast(player);
        return cplayer;
    }

    /**
     * 获取NMS的ItemStack对象
     * @param item Bukkit的物品堆
     * @return NMS物品堆
     */
    @Deprecated
    public static Object getNMSItemStack(ItemStack item){
        PJMethod method = new PJMethod(ReflectUtils.getClass(getVersionForOBC()+".inventory.CraftItemStack"));
        Object o = method.InPutName("asNMSCopy").InPutArg(item).run(null);
        return o;
    }

    /**
     * 发送数据包
     * @param player 玩家
     * @param packet 数据包
     */
    @Deprecated
    public static void sendPacket(Player player, Object packet){
        PJMethod method = new PJMethod(ReflectUtils.getClass(getVersionForOBC() + ".entity.CraftPlayer"),"getHandle");
        Object hand = method.runMethod(getCraftPlayer(player));
        PJVariable var = new PJVariable(hand);
        PJMethod method1 = new PJMethod(ReflectUtils.getClass(getVersionForNMS() + ".PlayerConnection"),"sendPacket",ReflectUtils.getClass(getVersionForNMS() + ".Packet"));
        method1.runMethod(var.getValue("playerConnection"),packet);
    }
}