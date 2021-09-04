package net.bxx2004.pandalib.pitem;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 构造一个自定义物品堆
 */
public class CustomItem extends ItemStack {
    /**
     * 无用
     */
    protected CustomItem() {

    }
    /**
     *  构造一个自定义物品堆
     * @param type 材质
     */
    public CustomItem(Material type) {
        super(new ItemStack(type));
    }
    /**
     *  构造一个自定义物品堆
     * @param type 材质
     * @param amount 数量
     */
    public CustomItem(Material type, int amount) {
        super(new ItemStack(type, amount));
    }

    /**
     * 构造一个自定义物品堆
     * @param stack ItemStack物品堆
     */
    public CustomItem(ItemStack stack){
        super(stack);
    }
    /**
     *  构造一个自定义物品堆
     * @param type 材质
     * @param amount 数量
     * @param name 物品堆显示名称
     * @param lore 物品堆的描述
     */
    public CustomItem(Material type, int amount, String name, String... lore){
        super(new ItemStack(type, amount));
        ItemMeta meta = super.getItemMeta();
        meta.setDisplayName(name.replaceAll("&", "§"));
        if (lore != null){
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < lore.length; i++){
                list.add(lore[i].replaceAll("&", "§"));
            }
            meta.setLore(list);
        }
        super.setItemMeta(meta);
    }
    /**
     *  构造一个自定义物品堆
     * @param type 材质
     * @param name 物品堆显示名称
     * @param lore 物品堆的描述
     */
    public CustomItem(Material type, String name, String... lore){
        super(new ItemStack(type, 1));
        ItemMeta meta = super.getItemMeta();
        meta.setDisplayName(name.replaceAll("&", "§"));
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < lore.length; i++){
            list.add(lore[i].replaceAll("&", "§"));
        }
        meta.setLore(list);
        super.setItemMeta(meta);
    }
    /**
     *  构造一个自定义物品堆
     * @param type 材质
     * @param name 物品堆显示名称
     * @param lore 物品堆的描述
     * @param enchants 物品堆的附魔(名称-级别)
     */
    public CustomItem(Material type, String name, String[] lore, String[] enchants){
        super(new ItemStack(type, 1));
        ItemMeta meta = super.getItemMeta();
        meta.setDisplayName(name.replaceAll("&", "§"));
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < lore.length; i++){
            list.add(lore[i].replaceAll("&", "§"));
        }
        meta.setLore(list);
        Map<Enchantment, Integer> map = new HashMap<>();
        for (String ench : enchants){
            String enchname = ench.split("-")[0];
            int level = Integer.parseInt(ench.split("-")[1]);
            map.put(Enchantment.getByName(enchname), level);
        }
        super.setItemMeta(meta);
        super.addUnsafeEnchantments(map);
    }

    /**
     * 为该物品增加Lore
     * @param lore 描述
     * @since 1.4.2
     */
    public void addLore(String... lore){
        ItemMeta meta = super.getItemMeta();
        if (meta.hasLore()){
            List<String> list = meta.getLore();
            for (int i = 0; i < lore.length; i++){
                list.add(lore[i].replaceAll("&", "§"));
            }
            meta.setLore(list);
            super.setItemMeta(meta);
        }else {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < lore.length; i++){
                list.add(lore[i].replaceAll("&", "§"));
            }
            meta.setLore(list);
            super.setItemMeta(meta);
        }
    }

    /**
     * 为物品移除某一行lore
     * @param index 行数
     * @since 1.5.2
     */
    public void removeLore(int index){
        ItemMeta meta = super.getItemMeta();
        List<String> list = meta.getLore();
        list.remove(index);
        meta.setLore(list);
        super.setItemMeta(meta);
    }

    /**
     * 根据正则表达式匹配Lore
     * @param regx 正则表达式
     * @return 所有行匹配结果
     */
    public String[] searchLore(String regx){
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(regx);
        for (String lore : super.getItemMeta().getLore()){
            String result = pattern.matcher(lore).replaceAll("");
            if (!result.equalsIgnoreCase("")){
                list.add(result);
            }
        }
        return list.toArray(new String[list.size()]);
    }
    /**
     * @since 1.5.3
     * 将这个物品对象转化为字符串形式(适用Yaml)
     * @return 字符串
     */
    public String To(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("DisPlayName: " + getItemMeta().getDisplayName() + "\n"+
                "Material: " + getType() + "\n"+
                "Amount: " + getAmount());
        if (getItemMeta().hasLore()){
            buffer.append("\nLore:");
            for (String s : getItemMeta().getLore()){
                buffer.append("\n  - \"" + s + "\"");
            }
        }
        if (getItemMeta().hasEnchants()){
            buffer.append("\nEnchant:");
            for (Enchantment s : getItemMeta().getEnchants().keySet()){
                buffer.append("\n  - \"" + s.getName() + ":" + getItemMeta().getEnchants().get(s) + "\"");
            }
        }
        if ((getItemMeta().getItemFlags() != null) && (getItemMeta().getItemFlags().size() != 0)){
            buffer.append("\nFlags:");
            for (ItemFlag flag : getItemMeta().getItemFlags()){
                buffer.append("\n  - \"" + flag + "\"");
            }
        }
        return buffer.toString();
    }
    /**
     * 从一个字符串特定格式读取一个CustomItem对象(适用Yaml)
     * @since 1.5.3
     * @param s 字符串
     * @return CustomItem
     */
    public static CustomItem From(String s){
        BufferedReader reader = new BufferedReader(new StringReader(s));
        FileConfiguration data = YamlConfiguration.loadConfiguration(reader);

        CustomItem item = new CustomItem(Material.getMaterial(data.getString("Material")),data.getInt("Amount"),data.getString("DisPlayName").replaceAll("&", "§"));
        if ((data.getStringList("Lore") != null) && (!data.getStringList("Lore").isEmpty())){
            List<String> lore = data.getStringList("Lore");
            for (String a : lore){
                item.addLore(a.replaceAll("&", "§"));
            }
        }
        if ((data.getStringList("Enchant") != null) && (!data.getStringList("Enchant").isEmpty())){
            HashMap<Enchantment,Integer> enchant = new HashMap<Enchantment, Integer>();
            for (String e : data.getStringList("Enchant")){
                enchant.put(Enchantment.getByName(e.split(":")[0]),Integer.parseInt(e.split(":")[1]));
            }
            item.addUnsafeEnchantments(enchant);
        }
        if ((data.getStringList("Flags") != null) && (!data.getStringList("Flags").isEmpty())){
            for (String b : data.getStringList("Flags")){
                item.addItemFlags(ItemFlag.valueOf(b));
            }
        }
        return item;
    }
}
