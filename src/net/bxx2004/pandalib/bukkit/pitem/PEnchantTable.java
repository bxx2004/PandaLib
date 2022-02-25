package net.bxx2004.pandalib.bukkit.pitem;

/**
 * 附魔台
 * @since 1.4.2
 */
public class PEnchantTable {
    private int chance;
    private boolean can;
    private int enchantchance;

    /**
     * 附魔台
     * @param chance 出现在附魔台的几率
     * @param can 是否可以出现在附魔台
     * @param enchantchance 附魔台达到多少等级时可以出现
     */
    public PEnchantTable(boolean can, int chance, int enchantchance) {
        this.chance = chance;
        this.can = can;
        this.enchantchance = enchantchance;
    }

    /**
     * 出现在附魔台的几率
     * @return 出现在附魔台的几率
     */
    public int getChance() {
        return chance;
    }

    /**
     * 是否可以出现在附魔台
     * @return 是否可以出现在附魔台
     */
    public boolean isCan() {
        return can;
    }

    /**
     * 附魔台达到多少等级时可以出现
     * @return 附魔台达到多少等级时可以出现
     */
    public int getEnchantchance() {
        return enchantchance;
    }
}
