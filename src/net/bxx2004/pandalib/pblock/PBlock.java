package net.bxx2004.pandalib.pblock;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.IOException;
import java.util.Set;

/**
 * 快速构建一个没什么用的方块
 */
public class PBlock extends PBlockMeta{
    private Block block;
    private Location location;
    private PBlockMeta meta;
    /**
     * 构造一个没什么用的方块类
     * @param location 位置
     * @param meta 数据
     */
    public PBlock(Location location, PBlockMeta meta){
        this.block = location.getBlock();
        this.location = location;
        this.meta = meta;
    }

    /**
     * 构造一个没什么用的方块类
     * @param world 世界
     * @param x 坐标
     * @param y 坐标
     * @param z 坐标
     * @param meta 数据
     */
    public PBlock(String world, double x, double y, double z, PBlockMeta meta){
        this.location = new Location(Bukkit.getWorld(world), x,y,z);
        this.block = location.getBlock();
        this.meta = meta;
    }
    /**
     * 移除这个方块
     */
    public void removeBlock(){
        this.block.setType(Material.AIR);
    }
    /**
     * 更改这个方块材质
     * @param type 材质
     */
    public void setType(Material type){
        this.block.setType(type);
    }

    /**
     * 获取方块材质
     * @return 方块材质
     */
    public Material getType(){
        return this.block.getType();
    }

    @Override
    public void addMeta(String key, Object meta) {
        this.meta.data.set(toBlockKey(block) + "." + key, meta);
        reload();
    }

    @Override
    public void removeMeta(String key) {
        this.meta.data.set(toBlockKey(block) + "." + key, null);
        reload();
    }

    @Override
    public Object getMeta(String key) {
        return this.meta.data.get(toBlockKey(block) + "." + key);
    }

    @Override
    public String list(boolean b) {
        Set<String> keys = this.meta.data.getConfigurationSection(toBlockKey(block)).getKeys(b);
        StringBuilder meta = new StringBuilder();
        for (String a : keys){
            meta.append( a + ":" + this.meta.data.get(toBlockKey(block) + "." + a).toString() + ";");
        }
        return meta.toString();
    }

    @Override
    public void clear() {
        this.meta.data.set(toBlockKey(block), null);
        reload();
    }

    @Override
    public Set<String> keySet() {
        return this.meta.data.getConfigurationSection(toBlockKey(block)).getKeys(true);
    }

    /**
     * Block toString
     * @param block 方块
     * @return Block toString
     */
    public static String toBlockKey(Block block){
        return block.getWorld().getName() + ";" + block.getX()  + ";" + block.getY()  + ";" + block.getZ();
    }

    /**
     * 保存文件
     */
    private void reload(){
        try {
            this.meta.data.save(this.meta._data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
