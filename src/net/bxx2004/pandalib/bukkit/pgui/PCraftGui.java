package net.bxx2004.pandalib.bukkit.pgui;

import net.bxx2004.pandalib.PandaLib;
import net.bxx2004.pandalib.bukkit.plistener.PListener;
import net.bxx2004.pandalib.bukkit.pitem.PItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 自定义合成界面GUI
 */
public class PCraftGui extends CustomGui{
    private String title;
    private PItemStack by;
    private PItemStack result;
    private PItemStack[] craftitem;
    private Inventory craftgui;
    private PItemStack esc;
    private Animation animation;
    private int speed;
    /**
     * <p>构造一个类似于Slimefun的合成表GUI</p>
     * @param title GUI标题
     * @param by 合成方式
     * @param result 合成结果
     * @param craftitem 合成材料(限制9个以内)
     */
    public PCraftGui(String title, PItemStack by, PItemStack result, PItemStack... craftitem){
        this.title = title;
        this.by = by;
        this.result = result;
        this.craftitem = craftitem;
        this.craftgui = Bukkit.createInventory(null, 27 , title.replaceAll("&", "§"));
        update();
    }
    private void update(){
        int[] craftslot = new int[]{3,4,5,12,13,14,21,22,23};
        for (int i = 0; i < 9; i++){
            this.craftgui.setItem(craftslot[i], this.craftitem[i]);
        }
        this.craftgui.setItem(0, this.esc);
        this.craftgui.setItem(10, this.by);
        this.craftgui.setItem(16, this.result);
    }
    @Override
    public void openGui(Player player) {
        if (animation == null){
            player.openInventory(craftgui);
        }else {
            openAnimation(player,this.animation, speed);
        }
    }
    @Override
    public CustomGuiType getCustomGuiType() {
        return CustomGuiType.CRAFT_GUI;
    }
    @Override
    public Inventory getInventory() {
        return craftgui;
    }

    @Override
    public void addListener(PListener event) {

    }

    /**
     * 返回GUI标题
     * @return 返回GUI标题
     */
    public String getTitle() {
        return title;
    }
    /**
     * 设置GUI标题
     * @param title GUI标题
     */
    public void setTitle(String title) {
        this.title = title;
        update();
    }
    /**
     * 返回合成方式
     * @return 返回合成方式
     */
    public PItemStack getBy() {
        return by;
    }
    /**
     * 设置合成方式
     * @param by 合成方式
     */
    public void setBy(PItemStack by) {
        this.by = by;
        update();
    }
    /**
     * 返回合成结果
     * @return 返回合成结果
     */
    public PItemStack getResult() {
        return result;
    }
    /**
     * 设置合成结果
     * @param result 合成结果
     */
    public void setResult(PItemStack result) {
        this.result = result;
        update();
    }
    /**
     * 返回合成材料
     * @return 返回合成材料
     */
    public PItemStack[] getCraftitem() {
        return craftitem;
    }
    /**
     * 设置合成材料
     * @param craftitem 合成材料
     */
    public void setCraftitem(PItemStack[] craftitem) {
        this.craftitem = craftitem;
        update();
    }
    /**
     * 返回按钮材质
     * @return 返回按钮材质
     */
    public PItemStack getESC() {
        return esc;
    }
    /**
     * 设置按钮材质
     * @param esc 材质
     */
    public void setESC(PItemStack esc) {
        this.esc = esc;
        update();
    }

    @Override
    public void setAnimation(Animation animation,int speed) {
        this.animation = animation;
        this.speed = speed;
    }
    private void openAnimation(Player player, Animation animation, int speed){
        ItemStack[] backs = craftgui.getContents();
        this.craftgui.clear();
        player.openInventory(craftgui);
        if (animation == Animation.BAR){
            new BukkitRunnable(){
                int i = 9;
                @Override
                public void run() {
                    craftgui.setItem(i,new PItemStack(Material.GREEN_STAINED_GLASS_PANE,"&e加载中···"));
                    i= i+1;
                    if (i > 17){
                        craftgui.clear();
                        for (int o = 0 ; o < backs.length; o++){
                            craftgui.setItem(o,backs[o]);
                        }
                        cancel();
                    }
                }
            }.runTaskTimerAsynchronously(PandaLib.initPlugin,0,speed);
        }
        if (animation == Animation.BRUSH){
            craftgui.setItem(0,new PItemStack(Material.GREEN_STAINED_GLASS_PANE,"&e加载中···"));
            craftgui.setItem(1,new PItemStack(Material.GREEN_STAINED_GLASS_PANE,"&e加载中···"));
            craftgui.setItem(9,new PItemStack(Material.GREEN_STAINED_GLASS_PANE,"&e加载中···"));
            new BukkitRunnable(){
                int s1 = 2;
                int s2 = 10;
                int s3 = 18;
                @Override
                public void run() {
                    try {
                        craftgui.setItem(s1,new PItemStack(Material.GREEN_STAINED_GLASS_PANE,"&e加载中···"));
                        craftgui.setItem(s2,new PItemStack(Material.GREEN_STAINED_GLASS_PANE,"&e加载中···"));
                        craftgui.setItem(s3,new PItemStack(Material.GREEN_STAINED_GLASS_PANE,"&e加载中···"));
                        s1= s1+1;
                        s2= s2+1;
                        s3= s3+1;
                    }catch (Exception e){
                        craftgui.clear();
                        for (int o = 0 ; o < backs.length; o++){
                            craftgui.setItem(o,backs[o]);
                        }
                        this.cancel();
                    }
                }
            }.runTaskTimerAsynchronously(PandaLib.initPlugin,0,speed);
        }
        if (animation == Animation.LOAD){
            new BukkitRunnable(){
                int who = 0;
                int s1 = 12;
                int s2 = 13;
                int s3 = 14;
                @Override
                public void run() {
                    if (who == 0 || who == 3 || who == 6){
                        craftgui.setItem(s3, new PItemStack(Material.AIR));
                        craftgui.setItem(s1,new PItemStack(Material.GREEN_STAINED_GLASS_PANE,"&e加载中···"));
                    }
                    if (who == 1 || who == 4 || who == 7) {
                        craftgui.setItem(s1, new PItemStack(Material.AIR));
                        craftgui.setItem(s2,new PItemStack(Material.GREEN_STAINED_GLASS_PANE,"&e加载中···"));
                    }
                    if (who == 2 || who == 5 || who == 8){
                        craftgui.setItem(s2, new PItemStack(Material.AIR));
                        craftgui.setItem(s3,new PItemStack(Material.GREEN_STAINED_GLASS_PANE,"&e加载中···"));
                    }
                    who = who + 1;
                    if (who >= 9){
                        craftgui.clear();
                        for (int o = 0 ; o < backs.length; o++){
                            craftgui.setItem(o,backs[o]);
                        }
                        this.cancel();
                    }
                }
            }.runTaskTimerAsynchronously(PandaLib.initPlugin,0,speed);
        }
    }
}
