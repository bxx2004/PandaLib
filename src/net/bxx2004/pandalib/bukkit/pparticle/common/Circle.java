package net.bxx2004.pandalib.bukkit.pparticle.common;

import net.bxx2004.pandalib.bukkit.pparticle.ImpParticle;
import net.bxx2004.pandalib.bukkit.pparticle.PParticle;
import org.bukkit.Location;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.lang.Math;
/**
 * 代表一个圆
 */
public class Circle extends PParticle implements ImpParticle {
    private ParticleBuilder p;

    /**
     * 代表一个圆
     * @param particle 粒子
     * @param ridius 半径
     * @param step 步长
     */
    public Circle(ParticleBuilder particle, double ridius, int step){
        super(new MathUnit[]{MathUnit.RIDIUS,MathUnit.STEP},new double[]{ridius,step});
        this.p = particle;
    }
    @Override
    public double value(MathUnit mathUnit) {
        return super.value(mathUnit);
    }

    @Override
    public void show(Location location) {
        for (int i = 0; i < 360; i+= value(MathUnit.STEP)) {
            double radians = Math.toRadians(i);
            double x = Math.cos(radians) * value(MathUnit.RIDIUS);
            double y = Math.sin(radians) * value(MathUnit.RIDIUS);
            location.add(x, 0, y);
            p.setLocation(location);
            p.display();
            location.subtract(x, 0, y);
        }
    }

    @Override
    public String getType() {
        return "Circle";
    }

}
