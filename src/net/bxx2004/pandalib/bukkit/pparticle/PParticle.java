package net.bxx2004.pandalib.bukkit.pparticle;

import org.bukkit.Location;
import org.bukkit.Particle;

/**
 * 代表一个简单的粒子
 */
public class PParticle implements ImpParticle{
    public int i;
    public Particle particle;

    /**
     * 构造粒子对象
     * @param particle 粒子
     * @param i 次数
     */
    public PParticle(Particle particle, int i){
        this.particle = particle;
        this.i = i;
    }

    @Override
    public void show(Location location) {
        location.getWorld().spawnParticle(particle,location,i);
    }
    @Override
    public void showPacket(Location location) {

    }

    @Override
    public String getType() {
        return "PParticle";
    }
}
