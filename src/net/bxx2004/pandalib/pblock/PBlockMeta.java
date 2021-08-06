package net.bxx2004.pandalib.pblock;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

/**
 * 方块数据
 */
public abstract class PBlockMeta {
    /**
     * The Meta.
     */
    public HashMap<String, Object> meta = new HashMap<String,Object>();
    /**
     * The Data.
     */
    public FileConfiguration data;
    /**
     * The Data.
     */
    public File _data;

    /**
     * Instantiates a new P block meta.
     */
    public PBlockMeta(){};

    /**
     * 创建该对象
     *
     * @param path 路径
     * @return 对象 p block meta
     */
    public static PBlockMeta create(String path){
        return new PBlockMeta(path) {
            @Override
            public void addMeta(String key, Object meta) {

            }

            @Override
            public void removeMeta(String key) {

            }

            @Override
            public Object getMeta(String key) {
                return null;
            }

            @Override
            public String list(boolean b) {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public Set<String> keySet() {
                return null;
            }
        };
    }

    /**
     * 构造物品数据存放位置(yml文件)
     *
     * @param path 位置
     */
    public PBlockMeta(String path){
        this._data = new File(path);
        this.data = YamlConfiguration.loadConfiguration(_data);
    }

    /**
     * 为方块添加数据
     *
     * @param key  唯一对应标识
     * @param meta 数据
     */
    public abstract void addMeta(String key, Object meta);

    /**
     * 为方块删除数据
     *
     * @param key 唯一对应标识
     */
    public abstract void removeMeta(String key);

    /**
     * 获取这个方块数据
     *
     * @param key 唯一对应标识
     * @return 方块数据 meta
     */
    public abstract Object getMeta(String key);

    /**
     * toString
     *
     * @param b getKeys(boolean b)
     * @return 全部数据toString
     */
    public abstract String list(boolean b);

    /**
     * 删除全部数据
     */
    public abstract void clear();

    /**
     * 该方块内所有唯一对应值
     *
     * @return 数据 set
     */
    public abstract Set<String> keySet();
}
