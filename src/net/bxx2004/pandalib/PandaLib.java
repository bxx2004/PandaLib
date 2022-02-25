package net.bxx2004.pandalib;

import net.bxx2004.pandalib.bukkit.manager.Lang;
import net.bxx2004.pandalib.bukkit.otherplugin.PVault;
import net.bxx2004.pandalib.bukkit.pfile.PYml;
import net.bxx2004.pandalib.bukkit.pitem.CustomItem;
import net.bxx2004.pandalib.bukkit.pitem.PEnchantment;
import net.bxx2004.pandalib.bukkit.planguage.PAction;
import net.bxx2004.pandalib.bukkit.planguage.PActionBar;
import net.bxx2004.pandalib.bukkit.planguage.PMessage;
import net.bxx2004.pandalib.bukkit.planguage.PTitle;
import net.bxx2004.pandalib.bukkit.plistener.PListener;
import net.bxx2004.pandalib.bukkit.putil.PMath;
import net.bxx2004.pandalib.bukkit.putil.PPlugin;
import net.bxx2004.pandalibloader.PandaLibPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.*;

import static net.bxx2004.pandalib.bukkit.pitem.PEnchantment.enchantments;
public class PandaLib{
    private static PYml option;
    private static boolean isInit = false;
    public static Plugin initPlugin;
    private static void init(PandaLibPlugin plugin) {
        if (!isInit){
            initPlugin = (Plugin) plugin.getPlugin();
            isInit = true;
            File file = new File(plugin.getPath().split("plugins")[0] + "server");
            if (!file.exists()){
                file.mkdirs();
            }
            option = new PYml(file.getAbsolutePath() + "/option.yml",true);
            new BukkitRunnable(){
                @Override
                public void run() {
                    registerAction();
                }
            }.run();
        }
    }

    public static String getServerLanguage(){
        if (option.getString("__option__.language") == null){
            option.set("__option__.language","zh_CN");
            option.reloadYaml();
            return "zh_CN";
        }else {
            return option.getString("__option__.language");
        }
    }
    /**
     * 获取某个插件的plugin.yml文件
     * @param plugin 插件
     * @return plugin.yml
     */
    public static FileConfiguration getLoadYmlFromPlugin(PandaLibPlugin plugin){
        return YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getJarFile("plugin.yml")));
    }

    /**
     * 从某个插件的Jar中释放文件
     * @param plugin 插件
     * @param filePath 文件路径
     * @param outPath 释放路径
     */
    public static void saveFileFormPlugin(PandaLibPlugin plugin,String filePath,String outPath){
        try {
            InputStreamReader input = new InputStreamReader(plugin.getJarFile(filePath),"UTF-8");
            File file = new File(outPath);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"), true);
            BufferedReader reder = new BufferedReader(input);

            if (!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Iterator<String> i = reder.lines().iterator();
            while (i.hasNext()){
                writer.println(i.next());
            }
            input.close();
            writer.close();;
            reder.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerEnchantments(){
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
                }.hook(initPlugin);
            }
        }.runTaskLaterAsynchronously(initPlugin,100);
    }
    private static void registerOther(){
        if (PPlugin.getPlugin("Vault") != null){
            PVault.register();
            Lang.print("检测到 Vault 插件,相关功能已经注册!");
        }
    }
    private static void registerAction(){
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
}

