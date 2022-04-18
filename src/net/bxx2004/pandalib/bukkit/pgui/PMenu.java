package net.bxx2004.pandalib.bukkit.pgui;

import net.bxx2004.pandalib.PandaLib;
import net.bxx2004.pandalib.bukkit.pitem.PItemStack;
import net.bxx2004.pandalib.bukkit.plistener.PListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public abstract class PMenu implements HolderFactory{
    public PMenu(){
        new PListener(){
            @EventHandler
            public void onClick(InventoryClickEvent event){
                if (event.getInventory().getHolder().equals(holder())){
                    for (int i : notClickSlot()) {
                        if (event.getRawSlot() == i){
                            event.setCancelled(true);
                        }
                    }
                    for (String s : notClickString()){
                        if (event.getCurrentItem() != null){
                            if (event.getCurrentItem().getItemMeta().getDisplayName().contains(s)){
                                event.setCancelled(true);
                            }
                            if (event.getCurrentItem().getItemMeta().getLore().contains(s)){
                                event.setCancelled(true);
                            }
                        }
                    }
                    click(event);
                }
            }
            @EventHandler
            public void onOpen(InventoryOpenEvent event){
                if (event.getInventory().getHolder().equals(holder())){
                    layout().forEach((key,value) ->{
                        event.getInventory().setItem(key,value);
                    });
                }
            }
        }.hook(PandaLib.initPlugin.getName());
    }
    public abstract void click(InventoryClickEvent event);
    public abstract String[] notClickString();
    public abstract int[] notClickSlot();
    public abstract Inventory inventory();
    public abstract HashMap<Integer, PItemStack> layout();
    public void open(Player player){
        player.openInventory(inventory());
    }

}
