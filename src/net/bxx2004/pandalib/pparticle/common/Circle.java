package net.bxx2004.pandalib.pparticle.common;

import net.bxx2004.pandalib.pparticle.ImpParticle;
import net.bxx2004.pandalib.pparticle.PParticle;
import net.bxx2004.pandalib.pparticle.ParticleBuilder;
import org.bukkit.Location;
import java.lang.Math;
/**
 * 代表一个圆
 */
public class Circle extends ParticleBuilder implements ImpParticle {
    private PParticle p;
    public Circle(PParticle particle,double ridius, int step){
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
            location.getWorld().spawnParticle(p.particle,location, p.i);
            location.subtract(x, 0, y);
        }
    }

    @Override
    public String getType() {
        return "Circle";
    }

    @Override
    public void showPacket(Location location) {

    }
}
