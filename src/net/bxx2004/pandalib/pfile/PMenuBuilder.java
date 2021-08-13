package net.bxx2004.pandalib.pfile;

import me.clip.placeholderapi.PlaceholderAPI;
import net.bxx2004.pandalib.pgui.CustomGui;
import net.bxx2004.pandalib.pitem.CustomItem;
import net.bxx2004.pandalib.planguage.PAction;
import net.bxx2004.pandalib.plistener.PListener;
import net.bxx2004.pandalib.putil.PPlugin;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 按照特定的Yaml文件格式构建一个菜单
 * 格式请查阅doc教程
 */
public class PMenuBuilder {
    private PYml data;
    private CustomGui gui;
    private boolean keep;

    /**
     * 构造
     * @param pluginname 插件名
     * @param menuYml 文件
     */
    public PMenuBuilder(String pluginname, PYml menuYml){
        this.data = menuYml;
        this.gui = CustomGui.createGUI(data.getString("Title").replaceAll("&","§"),data.getList("Index").size()*9);
        StringBuffer buffer = new StringBuffer();
        for (String s : getIndex()){
            buffer.append(s);
        }
        String index = buffer.toString();
        char[] index2 = index.toCharArray();
        for (int i = 0; i < index2.length; i++) {
            if (index2[i] == ' '){

            }else {
                gui.getInventory().setItem(i,getButton(String.valueOf(index2[i])));
            }
        }
        gui.addListener(new PListener(){
            @EventHandler
            public void on(InventoryClickEvent event){
                if (event.getView().getTitle().equals(data.getString("Title").replaceAll("&","§")) && event.getInventory().getSize() == data.getList("Index").size()*9){
                    event.setCancelled(true);
                }
            }
            @EventHandler
            public void onclose(InventoryCloseEvent event) {
                if (event.getView().getTitle().equals(data.getString("Title").replaceAll("&","§")) && event.getInventory().getSize() == data.getList("Index").size()*9) {
                    this.unhook();
                }
            }
            @EventHandler
            public void onClick(InventoryClickEvent event){
                try {
                    if (event.getView().getTitle().equals(data.getString("Title").replaceAll("&","§")) && event.getInventory().getSize() == data.getList("Index").size()*9){
                        if (event.getCurrentItem() != null || event.getCurrentItem().getType() != Material.AIR){
                            char key = index2[event.getRawSlot()];
                            for (String s : getAction(String.valueOf(key))){
                                PAction.go(s, (Player) event.getWhoClicked());
                            }
                        }
                        if (!keep){
                            event.getWhoClicked().closeInventory();
                        }
                    }
                }catch (Exception e){}
            }
        }.hook(PPlugin.getPlugin(pluginname)));
    }

    /**
     * 获取该菜单里的一个按钮
     * @param key 代表符号
     * @return 代表符号
     */
    public CustomItem getButton(String key){
        StringBuffer buffer = new StringBuffer();
        buffer.append("DisPlayName: " + data.getString("Button." + key + ".DisPlayName").replaceAll("&", "§") + "\n");
        buffer.append("Material: " + data.getString("Button." + key + ".Material") + "\n");
        buffer.append("Amount: " + data.getInt("Button." + key + ".Amount"));
        if ((data.getList("Button." + key + ".Lore") != null) && (!data.getList("Button." + key + ".Lore").isEmpty())){
            buffer.append("\nLore: ");
            List<String> lore = data.getList("Button." + key + ".Lore");
            for (String a : lore){
                buffer.append("\n  - \"" + a.replaceAll("&", "§") + "\"");
            }
        }
        if ((data.getList("Button." + key + ".Enchant") != null) && (!data.getList("Button." + key + ".Enchant").isEmpty())){
            buffer.append("\nEnchant: ");
            HashMap<Enchantment,Integer> enchant = new HashMap<Enchantment, Integer>();
            for (Object e : data.getList("Button." + key + ".Enchant")){
                buffer.append("\n  - \"" + (String) e + "\"");
            }
        }
        if ((data.getList("Button." + key + ".Flags") != null)  && (!data.getList("Button." + key + ".Flags").isEmpty())){
            buffer.append("\nFlags: ");
            for (Object b : data.getList("Button." + key + ".Flags")){
                buffer.append("\n  - \"" + (String) b + "\"");
            }
        }
        return CustomItem.From(buffer.toString());
    }

    /**
     * 返回一个按钮的动作
     * @param key 代表符号
     * @return 动作
     */
    public String[] getAction(String key){
        String[] sa = new String[data.getList("Button." + key + ".Action").size()];
        for (int i = 0; i < data.getList("Button." + key + ".Action").size(); i++) {
            sa[i] = (String) data.getList("Button." + key + ".Action").get(i);
        }
        return sa;
    }

    /**
     * 返回该菜单的布局
     * @return 布局
     */
    public String[] getIndex(){
        String[] a = new String[data.getList("Index").size()];
        for (int i = 0; i < data.getList("Index").size(); i++) {
            a[i] = (String) data.getList("Index").get(i);
        }
        return a;
    }

    /**
     * 获取背包对象
     * @return 背包
     */
    public Inventory getInventory(){
        return this.gui.getInventory();
    }

    /**
     * 给玩家打开gui
     * @param player 玩家
     */
    public void open(Player player,boolean plcaeholderapi){
        if (plcaeholderapi){
            ItemStack[] stack = this.gui.getInventory().getContents();
            for (ItemStack stack1 : stack){
                if (stack1.hasItemMeta()){
                    ItemMeta meta = stack1.getItemMeta();
                    if (meta.hasDisplayName()){
                        meta.setDisplayName(PlaceholderAPI.setPlaceholders(player,stack1.getItemMeta().getDisplayName()));
                    }
                    if (meta.hasLore()){
                        List<String> lore = new ArrayList<>();
                        for (String list : meta.getLore()){
                            lore.add(PlaceholderAPI.setPlaceholders(player,list));
                        }
                        meta.setLore(lore);
                    }
                    stack1.setItemMeta(meta);
                }
            }
        }
        this.gui.openGui(player);
    }

    /**
     * 忽略文件设置按钮
     * @param key 代表符号
     * @param item 按钮
     */
    public void setButton(char key,CustomItem item){
        StringBuffer buffer = new StringBuffer();
        for (String s : getIndex()){
            buffer.append(s);
        }
        String index = buffer.toString();
        char[] index2 = index.toCharArray();
        for (int i = 0; i < index2.length; i++) {
            if (index2[i] == key){
                this.getInventory().setItem(i, item);
                continue;
            }
        }
    }

    /**
     * 是否保持打开状态
     * @param b 状态
     */
    public void setKeep(boolean b){
        this.keep = b;
    }
}
