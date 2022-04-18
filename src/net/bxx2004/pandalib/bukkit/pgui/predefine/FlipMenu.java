package net.bxx2004.pandalib.bukkit.pgui.predefine;

import net.bxx2004.pandalib.bukkit.pgui.HolderFactory;
import net.bxx2004.pandalib.bukkit.pgui.PMenu;
import net.bxx2004.pandalib.bukkit.pitem.PItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;

public class FlipMenu extends PMenu {
    private HashMap<Integer,PItemStack[]> map;
    private boolean canClick;
    private int[] use;
    private int page;
    private Inventory inventory;
    public FlipMenu(String title,boolean canClick) {
        super();
        this.inventory = Bukkit.createInventory(holder(),54,title);
        this.map = new HashMap<>();
        this.canClick = canClick;
        this.page = 0;
        this.use = new int[]{1,2,3,4,5,6,7,10,11,12,13,14,15,16,19,20,21,22,23,24,25,26,29,30,31,32,33,34,35,38,39,40,41,42,43,44};
    }
    @Override
    public InventoryHolder holder() {
        return HolderFactory.register(this.toString(),true);
    }

    @Override
    public void click(InventoryClickEvent event) {
        if (event.getInventory().getHolder().equals(holder())){
            if (!canClick){
                event.setCancelled(true);
            }
            if (event.getRawSlot() == 47){
                if (page > 0){
                    page = page - 1;
                    layout(page);
                }
            }
            if (event.getRawSlot() == 51){
                if (page < (map.keySet().size() -1)){
                    page = page + 1;
                    layout(page);
                }
            }
        }
    }
    public void setItem(int index,PItemStack... itemStacks){
        map.put(index,itemStacks);
    }
    public int page(){
        return page;
    }
    private void layout(int page){
        inventory.clear();
        PItemStack[] item = map.get(page);
        int i = 0;
        for (int slot : use){
            if (item.length <= i){
                break;
            }
            if (item[i] == null){
                continue;
            }
            inventory().setItem(slot,item[i]);
            i = i+1;
        }
        inventory().setItem(47,new PItemStack(Material.ARROW,"&9⇦ 上一页"));
        inventory().setItem(51,new PItemStack(Material.ARROW,"&9⇨ 下一页"));
    }
    @Override
    public HashMap<Integer, PItemStack> layout() {
        HashMap<Integer,PItemStack> re = new HashMap<>();
        int i = 0;
        for (int slot : use){
            if (map.get(0).length <= i){
                break;
            }
            re.put(slot,map.get(0)[i]);
            i = i+1;
        }
        re.put(47,new PItemStack(Material.ARROW,"&9⇦ 上一页"));
        re.put(51,new PItemStack(Material.ARROW,"&9⇨ 下一页"));
        return re;
    }

    @Override
    public int[] notClickSlot() {
        return new int[]{47,51};
    }

    @Override
    public Inventory inventory() {
        return inventory;
    }

    @Override
    public String[] notClickString() {
        return new String[0];
    }
}
