package net.bxx2004.pandalib;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.bxx2004.Metrics;
import net.bxx2004.java.reflect.PJVariable;
import net.bxx2004.java.reflect.ReflectUtils;
import net.bxx2004.pandalib.manager.DataManager;
import net.bxx2004.pandalib.manager.Lang;
import net.bxx2004.pandalib.otherplugin.PVault;
import net.bxx2004.pandalib.pcommands.PSimpleCommand;
import net.bxx2004.pandalib.pfile.PYml;
import net.bxx2004.pandalib.pitem.CustomItem;
import net.bxx2004.pandalib.pitem.PEnchantment;
import net.bxx2004.pandalib.planguage.PAction;
import net.bxx2004.pandalib.planguage.PActionBar;
import net.bxx2004.pandalib.planguage.PMessage;
import net.bxx2004.pandalib.planguage.PTitle;
import net.bxx2004.pandalib.plistener.PListener;
import net.bxx2004.pandalib.putil.PMath;
import net.bxx2004.pandalib.putil.PDownLoad;
import net.bxx2004.pandalib.putil.PPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static net.bxx2004.pandalib.pitem.PEnchantment.enchantments;
/**
 * 插件主类
 * @author bxx2004
 * @version 1.4.2
 */
public class PandaLib extends JavaPlugin{
    static {
        DataManager.setJar();
    }
    public static PYml pscriptdata = new PYml("plugins/PandaLib/scriptdata.yml",true);
    public static HashMap<String,Object> tscriptdata = new HashMap<String, Object>();
    public static PandaLib getInstance(){
        return PandaLib.getPlugin(PandaLib.class);
    }
    @Override
    public void onEnable() {
        registerOther();
        registerCommand();
        registerAction();
        Metrics metrics = new Metrics(this, 11609);
        saveDefaultConfig();
        if (DataManager.isINFO()){
            Date date= new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String a = format.format(date);
            Lang.print("&e┏━━━━━━━━━ PandaLib "+getDescription().getVersion()+" ━━━━━━━━━━━━━━━━━━┓");
            Lang.print("&e┃  &fPandaLib is Running..." + "                   &e┃");
            Lang.print("&e┃  &6最新版本: &f" + getLastVestion().get(0) + " &7| &6更新时间: &f"+ getLastVestion().get(1) +"   &e┃");
            Lang.print("&e┃  &b作者: &fbxx2004 &7| &fhttp://linyanmc.cn/" + "      &e┃");
            Lang.print("&e┃  &b交流群: &f1038503485  &7|  &b" + a +"  &e┃");
            Lang.print("&e┖━━━━━━━━━━ PandaLib ━━━━━━━━━━━━━━━━━━━━━━━┛");
        }
        if (DataManager.getConfig().getBoolean("LIBUPDATE.UPDATEMESSAGE")){
            if (getLastVestion().isEmpty()){
                if (getLastVestion().get(0).equals(getDescription().getVersion())){
                    System.out.println("PandaLib 当前无更新...");
                }else {
                    if (DataManager.getConfig().getBoolean("LIBUPDATE.UPDATEMESSAGE")){
                        if (getConfig().getString("LIBUPDATE.DOWNLOADPATH").equalsIgnoreCase("none")){
                        }else {
                            File file = new File("plugins/PandaLib/version");
                            if (!file.exists()){
                                file.mkdir();
                            }
                            System.out.println("正在为您下载最新版本的PandaLib到目录...");
                            if (getConfig().getString("LIBUPDATE.DOWNLOADPATH").contains("!")){
                                PDownLoad load = new PDownLoad("http://linyanmc.cn/PandaLib/PandaLib.jar", getConfig().getString("LIBUPDATE.DOWNLOADPATH").replaceAll("!","") + "/PandaLib-"+getLastVestion()+".jar", this);
                                load.start();
                            }else {
                                PDownLoad load = new PDownLoad("http://linyanmc.cn/PandaLib/PandaLib.jar", getDataFolder().getAbsolutePath() + "/" +getConfig().getString("LIBUPDATE.DOWNLOADPATH") + "/PandaLib-"+getLastVestion().get(0).replaceAll("/", "")+".jar", this);
                                load.start();
                            }
                        }
                    }
                }
            }else {
                System.out.println("网络无连接...");
            }
        }
        registerEnchantments();
    }

    @Override
    public void onDisable() {

    }

    public List<String> getLastVestion(){
        List<String> list = new ArrayList<>();
        try {
            URL url = new URL("http://linyanmc.cn/version.json");
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            JsonParser parser = new JsonParser();
            JsonArray version = (JsonArray) parser.parse(br);
            for (int i = 0; i < version.size(); i++) {
                JsonObject object = version.get(i).getAsJsonObject();
                if (object.get("PluginName").getAsString().equalsIgnoreCase(getDescription().getName())){
                    list.add(object.get("version").getAsString());
                    list.add(object.get("date").getAsString());
                    return list;
                }
            }
            br.close();
        }catch (Exception e){
            return list;
        }
        return list;
    }
    private void registerEnchantments(){
        new BukkitRunnable(){
            private boolean co(List<String> list, String s){
                for (String a : list){
                    if (a.contains(s)){
                        return true;
                    }else {
                        continue;
                    }
                }
                return false;
            }
            HashMap<String,List<PEnchantment>> canviews;
            @Override
            public void run() {
                new PListener(){
                    @EventHandler
                    public void onTable(PrepareItemEnchantEvent event){
                        for (PEnchantment enchantment : enchantments){
                            if (enchantment.canRegisterEnchantTable().isCan()){
                                if (event.getEnchantmentBonus() >= enchantment.canRegisterEnchantTable().getEnchantchance()){
                                    canviews = new HashMap<>();
                                    List<PEnchantment> list = new ArrayList<>();
                                    for (PEnchantment ment : enchantments){
                                        if (ment.getSlotType().equals(PEnchantment.SlotType.ALL_ITEM) || event.getItem().getType().toString().split("_")[1].equalsIgnoreCase(ment.getSlotType().toString())){
                                            int r = PMath.getRandomAsInt(0,100);
                                            if (ment.canRegisterEnchantTable().getChance() > r){
                                                list.add(ment);
                                            }
                                        }
                                        if (ment.getSlotType().equals(PEnchantment.SlotType.CUSTOM_ITEM)){
                                            for (CustomItem item : ment.getCustomItem()){
                                                if (event.getItem().getItemMeta().equals(item.getItemMeta())){
                                                    list.add(ment);
                                                }
                                            }
                                        }
                                    }
                                    canviews.put(event.getEnchanter().getName(), list);
                                }
                            }
                        }
                    }
                    @EventHandler
                    public void onTable_2(EnchantItemEvent event){
                        try {
                            List<PEnchantment> list = canviews.get(event.getEnchanter().getName());
                            int r = PMath.getRandomAsInt(0,list.size());
                            int l = PMath.getRandomAsInt(list.get(r).getMinLevel(), list.get(r).getMaxLevel());
                            ItemStack three = event.getItem();
                            if (three.hasItemMeta()){
                                ItemMeta meta = three.getItemMeta();
                                List<String> list1 = meta.getLore();
                                list1.add(list.get(r).getEnchantColor() + list.get(r).getEnchantName() + " " + l);
                                meta.setLore(list1);
                                three.setItemMeta(meta);
                            }else {
                                ItemMeta meta = three.getItemMeta();
                                List<String> list2 = new ArrayList<>();
                                list2.add(list.get(r).getEnchantColor() + list.get(r).getEnchantName() + " " + l);
                                meta.setLore(list2);
                                three.setItemMeta(meta);
                            }
                            canviews.remove(event.getEnchanter().getName());
                        }catch (Exception e){}
                    }
                    @EventHandler
                    public void onUseAnvil(PrepareAnvilEvent event){
                        try {
                            if (event.getInventory().getType().equals(InventoryType.ANVIL)){
                                for (PEnchantment ment : enchantments){
                                    if (ment.canUseOfAnvil()){
                                        ItemStack one = event.getInventory().getItem(0);
                                        ItemStack two = event.getInventory().getItem(1);

                                        if ((co(two.getLore(),ment.getEnchantName())) && (one.getAmount() == 1)){
                                            ItemStack three = one.clone();
                                            for (String s : two.getItemMeta().getLore()){
                                                if (s.contains(ment.getEnchantName())){
                                                    if (one.hasItemMeta()){
                                                        List<String> onel = new ArrayList<>();
                                                        for (String as : one.getItemMeta().getLore()){
                                                            onel.add(as.split(" ")[0]);
                                                        }
                                                        if (onel.contains(s.split(" ")[0])) {
                                                            three = null;
                                                        }else {
                                                            if (three.hasItemMeta()){
                                                                ItemMeta meta = three.getItemMeta();
                                                                List<String> list = meta.getLore();
                                                                list.add(ment.getEnchantColor() + ment.getEnchantName() + " " + s.split(" ")[1]);
                                                                meta.setLore(list);
                                                                three.setItemMeta(meta);
                                                            }else {
                                                                ItemMeta meta = three.getItemMeta();
                                                                List<String> list = new ArrayList<>();
                                                                list.add(ment.getEnchantColor() + ment.getEnchantName() + " " + s.split(" ")[1]);
                                                                meta.setLore(list);
                                                                three.setItemMeta(meta);
                                                            }
                                                        }
                                                    }else {
                                                        if (three.hasItemMeta()){
                                                            ItemMeta meta = three.getItemMeta();
                                                            List<String> list = meta.getLore();
                                                            list.add(ment.getEnchantColor() + ment.getEnchantName() + " " + s.split(" ")[1]);
                                                            meta.setLore(list);
                                                            three.setItemMeta(meta);
                                                        }else {
                                                            ItemMeta meta = three.getItemMeta();
                                                            List<String> list = new ArrayList<>();
                                                            list.add(ment.getEnchantColor() + ment.getEnchantName() + " " + s.split(" ")[1]);
                                                            meta.setLore(list);
                                                            three.setItemMeta(meta);
                                                        }
                                                    }
                                                    event.setResult(three);
                                                    break;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }catch (NullPointerException e){

                        }
                    }
                    @EventHandler
                    public void onClick(InventoryClickEvent event){
                        try {
                            if (event.getInventory().getType().equals(InventoryType.ANVIL)){
                                for (PEnchantment ment : enchantments){
                                    ItemStack one = event.getInventory().getItem(0);
                                    ItemStack two = event.getInventory().getItem(1);
                                    ItemStack three = event.getInventory().getItem(2);
                                    if ((three != null) && (co(two.getLore(),ment.getEnchantName())) && (one.getAmount() == 1)){
                                        if (event.getRawSlot() == 2){
                                            event.setCursor(event.getInventory().getItem(2));
                                            event.getInventory().setItem(0, new ItemStack(Material.AIR));
                                            event.getInventory().setItem(1, new ItemStack(Material.AIR));
                                        }
                                        break;
                                    }
                                }
                            }
                        }catch (Exception e){

                        }
                    }
                }.hook(PPlugin.getPlugin("PandaLib"));
            }
        }.runTaskAsynchronously(PPlugin.getPlugin("PandaLib"));
    }
    private void registerOther(){
        if (PPlugin.getPlugin("Vault") != null){
            PVault.register();
            Lang.print("检测到 Vault 插件,相关功能已经注册!");
        }
    }
    private void registerAction(){
        new PAction("tell"){
            @Override
            public Object run(Player player, String... args) {
                PMessage.to(player,args[0]);
                return null;
            }
        };
        new PAction("title"){
            @Override
            public Object run(Player player, String... args) {
                if (args.length > 1){
                    PTitle.To(player,args[0] + "&nbsp" + args[1]);
                }else {
                    PTitle.To(player,args[0]);
                }
                return null;
            }
        };
        new PAction("actionbar"){
            @Override
            public Object run(Player player, String... args) {
                PActionBar.To(player,args[0]);
                return null;
            }
        };
        new PAction("close"){
            @Override
            public Object run(Player player, String... args) {
                player.closeInventory();
                return null;
            }
        };
        new PAction("print"){
            @Override
            public Object run(Player player, String... args) {
                Bukkit.getConsoleSender().sendMessage(args[0].replaceAll("&", "§"));
                return null;
            }
        };
        new PAction("value"){
            @Override
            public Object run(Player player, String... args) {
                try {
                    String name = args[0];
                    String type = args[1];
                    if (type.equalsIgnoreCase("p")){
                        return pscriptdata.get(name);
                    }
                    if (type.equalsIgnoreCase("t")){
                        return tscriptdata.get(name);
                    }
                }catch (Exception e){
                    Lang.error("&4VALUE取出变量异常",args);
                }
                return null;
            }
        };
        new PAction("var"){
            @Override
            public Object run(Player player, String... args) {
                try {
                    String varname = args[0];
                    String type = args[2];
                    try {
                        boolean value = Boolean.parseBoolean(args[1]);
                        if (type.equalsIgnoreCase("p")){
                            pscriptdata.set(varname,value);
                        }
                        if (type.equalsIgnoreCase("t")){
                            tscriptdata.put(varname,value);
                        }
                    }catch (Exception e){}
                    try {
                        int value = Integer.parseInt(args[1]);
                        if (type.equalsIgnoreCase("p")){
                            pscriptdata.set(varname,value);
                        }
                        if (type.equalsIgnoreCase("t")){
                            tscriptdata.put(varname,value);
                        }
                    }catch (Exception e){}
                    try {
                        float value = Float.parseFloat(args[1]);
                        if (type.equalsIgnoreCase("p")){
                            pscriptdata.set(varname,value);
                        }
                        if (type.equalsIgnoreCase("t")){
                            tscriptdata.put(varname,value);
                        }
                    }catch (Exception e){}
                    try {
                        double value = Double.parseDouble(args[1]);
                        if (type.equalsIgnoreCase("p")){
                            pscriptdata.set(varname,value);
                        }
                        if (type.equalsIgnoreCase("t")){
                            tscriptdata.put(varname,value);
                        }
                    }catch (Exception e){}
                    try {
                        long value = Long.parseLong(args[1]);
                        if (type.equalsIgnoreCase("p")){
                            pscriptdata.set(varname,value);
                        }
                        if (type.equalsIgnoreCase("t")){
                            tscriptdata.put(varname,value);
                        }
                    }catch (Exception e){}
                }catch (Exception e){
                    Lang.error("&4VAR存入变量异常",args);
                }
                return null;
            }
        };
        new PAction("command"){
            @Override
            public Object run(Player player, String... args) {
                String way = args[0];
                String command = "";
                for (int a = 1; a< args.length ; a++){
                    command += (args[a] + " ");
                }
                if (way.equalsIgnoreCase("player")){
                    Bukkit.dispatchCommand(player,command.replaceAll("<PLAYER>",player.getName()));
                }
                if (way.equalsIgnoreCase("op")){
                    if (!player.isOp()){
                        player.setOp(true);
                        Bukkit.dispatchCommand(player,command.replaceAll("<PLAYER>",player.getName()));
                        player.setOp(false);
                    }else {
                        Bukkit.dispatchCommand(player,command.replaceAll("<PLAYER>",player.getName()));
                    }
                }
                if (way.equalsIgnoreCase("console")){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command.replaceAll("<PLAYER>",player.getName()));
                }
                return null;
            }
        };
    }
    private void registerCommand(){
        new PSimpleCommand(this,"PandaLib"){
            @Override
            public void run() {
                if (args.length < 1){
                    sender.sendMessage("[PandaLib] - 请输入参数...");
                    return;
                }
                if (sender.hasPermission("pandalib.admin")){
                    if (args[0].equalsIgnoreCase("action")){
                        PJVariable var = new PJVariable(ReflectUtils.getClass("net.bxx2004.pandalib.planguage.PAction"));
                        HashMap map = (HashMap) var.getValue("map");
                        sender.sendMessage("- PandaLib | 动作");
                        for (Object s : map.keySet()){
                            sender.sendMessage(" - " + s);
                        }
                    }
                    if (args[0].equalsIgnoreCase("enchants")){
                        PJVariable var = new PJVariable(ReflectUtils.getClass("net.bxx2004.pandalib.pitem.PEnchantment"));
                        List<PEnchantment> enchantments = (List<PEnchantment>) var.getValue("enchantments");
                        sender.sendMessage("- PandaLib | 附魔");
                        for (PEnchantment s : enchantments){
                            sender.sendMessage(s.getPluginName() + " - " + s.getEnchantName());
                        }
                    }
                    if (args[0].equalsIgnoreCase("gc")){
                        sender.sendMessage("Java内存回收...");
                        System.gc();
                    }
                }else {
                    sender.sendMessage("你没权限");
                }
            }
        };

    }
}

