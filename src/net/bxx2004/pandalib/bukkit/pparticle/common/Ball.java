package net.bxx2004.pandalib.bukkit.pparticle.common;


import net.bxx2004.pandalib.bukkit.pparticle.ImpParticle;
import net.bxx2004.pandalib.bukkit.pparticle.PParticle;
import org.bukkit.Location;
import xyz.xenondevs.particle.ParticleBuilder;

/**
 * 代表一个球
 */
public class Ball extends PParticle implements ImpParticle {
    private ParticleBuilder p;

    /**
     * 构造器
     * @param particle 粒子
     * @param radius 半径
     * @param step 步长
     */
    public Ball(ParticleBuilder particle, double radius, int step){
        super(new MathUnit[]{MathUnit.RIDIUS,MathUnit.STEP},new double[]{radius,step});
        this.p = particle;
    }
    @Override
    public void show(Location location) {
        for (double i = 0; i < 180; i += value(MathUnit.STEP)) {
            double radians = Math.toRadians(i);
            double radius = Math.sin(radians) * value(MathUnit.RIDIUS);
            double y = Math.cos(radians)* value(MathUnit.RIDIUS);
            for (double j = 0; j < 360; j += value(MathUnit.STEP)) {
                double radiansCircle = Math.toRadians(j);
                double x = Math.cos(radiansCircle) * radius;
                double z = Math.sin(radiansCircle) * radius;
                location.add(x, y, z);
                p.setLocation(location);
                p.display();
                location.subtract(x, y, z);
            }
        }
    }

    @Override
    public String getType() {
        return "Ball";
    }
}
