package net.bxx2004.pandalib.pparticle;

import java.util.HashMap;

/**
 * 粒子构造器
 */
public abstract class ParticleBuilder{
    private HashMap<MathUnit,Double> map;

    /**
     * 构造一个粒子构造器
     * @param type 单位
     * @param size 数值
     */
    public ParticleBuilder(MathUnit[] type, double[] size){
        this.map = new HashMap<MathUnit, Double>();
        for (int i = 0; i < type.length; i++){
            this.map.put(type[i],size[i]);
        }
    }

    public double value(MathUnit mathUnit){
        return map.get(mathUnit);
    }
    /**
     * 单位
     */
    public enum MathUnit{
        /**
         * 长
         */
        LONG,
        /**
         * 宽
         */
        WIDE,
        /**
         * 高
         */
        HEIGHT,
        /**
         * 半径
         */
        RIDIUS,
        /**
         * 步长
         */
        STEP,
    }
}
