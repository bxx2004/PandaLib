package net.bxx2004.pandalib.bukkit.pgui.predefine;

import net.bxx2004.pandalib.bukkit.pgui.HolderFactory;
import net.bxx2004.pandalib.bukkit.pgui.PMenu;
import net.bxx2004.pandalib.bukkit.pitem.PItemStack;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;

public class CommonMenu extends PMenu {
    private String[] not;
    private int[] not1;
    private Inventory inventory;
    private HashMap<Integer, PItemStack> maps;
    public CommonMenu(String title,int size){
        super();
        inventory = Bukkit.createInventory(holder(),size,title);
        maps = new HashMap<Integer, PItemStack>();
    }
    @Override
    public InventoryHolder holder() {
        return HolderFactory.register(this.toString(),true);
    }

    @Override
    public void click(InventoryClickEvent event) {

    }
    public void setItem(PItemStack t,int... i){
        for (int a : i){
            maps.put(a,t);
        }
    }
    public void setNotSlot(String[] not) {
        this.not = not;
    }

    public void setNotString(int[] not1) {
        this.not1 = not1;
    }

    @Override
    public String[] notClickString() {
        return not;
    }

    @Override
    public int[] notClickSlot() {
        return not1;
    }

    @Override
    public Inventory inventory() {
        return inventory;
    }

    @Override
    public HashMap<Integer, PItemStack> layout() {
        return maps;
    }
}
