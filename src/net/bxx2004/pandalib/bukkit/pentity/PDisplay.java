package net.bxx2004.pandalib.bukkit.pentity;

import net.bxx2004.pandalib.bukkit.pfile.PYml;
import net.bxx2004.pandalib.bukkit.plistener.PListener;
import net.bxx2004.pandalib.bukkit.plistener.event.PDisplayClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

/**
 * 有关于全息投影的类
 */
public class PDisplay {
    private HashMap<Integer,String> content;
    private Entity[] them;
    private int nowLine;
    private double linedistance = 0.25;;
    private Location location;
    private String id;
    private boolean listener;
    /**
     * 构造一个全息投影类
     */
    public PDisplay(String id){
        this.id = id;
        this.nowLine = 0;
        this.content = new HashMap<Integer,String>();
    }

    /**
     * 添加文本
     * @param content 文本内容
     * @return 当前构造器
     */
    public PDisplay inputContent(String... content){
        for (String s : content){
            this.nowLine += 1;
            this.content.put(nowLine,s);
        }
        return this;
    }

    /**
     * 为该全息图设置行距
     * @param distance 行距
     */
    public void setDistance(double distance){
        this.linedistance = distance;
    }

    /**
     * 在某个位置生成该全息图
     * @param location 位置
     * @return 当前构造器
     */
    public PDisplay spawn(Location location,boolean listener){
        this.listener = listener;
        this.them = new Entity[this.content.keySet().size() + 1];
        callEntity(location);
        if (listener){
            hookListener();
        }
        return this;
    }

    /**
     * 删除这个全息显示
     */
    public void remove(){
        for (Entity e : them){
            e.remove();
        }
    }

    /**
     * 传送该全息到某位置
     * @param location 位置
     */
    public void teleport(Location location){
        this.remove();
        spawn(location,listener);
    }
    private void callEntity(Location location){
        this.location = location;
        double o = 0;
        for (int i = 0; i < content.keySet().size(); i++) {
            ArmorStand entity = (ArmorStand) location.getWorld().spawnEntity(location.clone().add(0.00, o, 0.00), EntityType.ARMOR_STAND);
            entity.setCustomNameVisible(true);
            entity.setCustomName(content.get(i + 1).replaceAll("&", "§"));
            entity.setGravity(false);
            entity.setInvulnerable(true);
            entity.setVisible(false);
            entity.addScoreboardTag(id);
            this.them[i + 1] = entity;
            o -= linedistance;
        }
    }

    /**
     * 从一个YML文件获取构造器
     * @param name 名字
     * @return 构造器
     */
    public static PDisplay getDisPlayByYML(PYml yml,String name){
        String id = yml.getString(name + ".id");
        Location location = (Location) yml.get(name + ".location");
        boolean listener = (boolean) yml.get(name + ".listener");
        double distance = yml.getDouble(name + ".distance");
        List<String> list = new ArrayList<String>();
        Iterator<String> keys = yml.getYaml().getConfigurationSection(name + ".content").getKeys(false).iterator();
        while (keys.hasNext()){
            String a = keys.next();
            list.add(yml.getString(name + ".content." + a));
        }
        String[] aa = new String[list.size()];
        list.toArray(aa);
        PDisplay display = new PDisplay(id).inputContent(aa);
        display.setDistance(distance);
        display.spawn(location,listener);
        return display;
    }

    /**
     * 将此构造器保存到YML文件
     * @param yml 文件
     * @param name 名字
     */
    public void saveDisPlayByYML(PYml yml, String name){
        yml.set(name + ".id",this.id);
        yml.set(name + ".location",this.location);
        yml.set(name + ".content",this.content);
        yml.set(name + ".listener",this.listener);
        yml.set(name + ".distance",this.linedistance);
    }
    private PDisplay getInstance(){
        return this;
    }
    private void hookListener(){
        new PListener(){
            @EventHandler
            public void onClick(PlayerInteractEvent event){
                Collection<Entity> e = event.getPlayer().getWorld().getNearbyEntitiesByType(ArmorStand.class,location,2);
                for (int i = 0 ;i < content.keySet().size(); i++){
                    for (Entity s : e){
                        if (them[i + 1] == s){
                            Bukkit.getServer().getPluginManager().callEvent(new PDisplayClickEvent(getInstance(),event.getPlayer()));
                            return;
                        }
                    }
                }
            }
        }.hook("Pandalib");
    }
}
