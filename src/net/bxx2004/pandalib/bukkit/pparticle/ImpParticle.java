package net.bxx2004.pandalib.bukkit.pparticle;

import org.bukkit.Location;

/**
 * 一个粒子特效的接口
 */
public interface ImpParticle {
    /**
     * 以该坐标为中心绘画粒子特效
     * @param location 位置
     */
    public void show(Location location);

    /**
     * 获取该粒子特效的类型
     * @return 类型
     */
    public String getType();

    /**
     * 以该坐标为中心绘画粒子特效(发包)
     * @param location 位置
     */
    public void showPacket(Location location);
}
