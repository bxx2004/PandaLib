package net.bxx2004.pandalib;

import net.bxx2004.pandalib.bukkit.manager.Lang;
import net.bxx2004.pandalib.bukkit.otherplugin.PVault;
import net.bxx2004.pandalib.bukkit.pfile.PYml;
import net.bxx2004.pandalib.bukkit.pitem.PItemStack;
import net.bxx2004.pandalib.bukkit.planguage.PAction;
import net.bxx2004.pandalib.bukkit.planguage.PActionBar;
import net.bxx2004.pandalib.bukkit.planguage.PMessage;
import net.bxx2004.pandalib.bukkit.planguage.PTitle;
import net.bxx2004.pandalib.bukkit.planguage.pactionextend.PlayerControl;
import net.bxx2004.pandalib.bukkit.plistener.PListener;
import net.bxx2004.pandalib.bukkit.putil.PMath;
import net.bxx2004.pandalib.bukkit.putil.PPlugin;
import net.bxx2004.pandalibloader.PandaLibPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    public static void connect(Player player,String server){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(initPlugin, "BungeeCord", b.toByteArray());
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

    private static void registerOther(){
        if (PPlugin.getPlugin("Vault") != null){
            PVault.register();
            Lang.print("检测到 Vault 插件,相关功能已经注册!");
        }
    }
    public  static HashMap<String,String> data = new HashMap<>();
    private static void registerAction(){
        new PlayerControl();
        //operate (+ - * /) 1 1 1 1 1
        new PAction("operate"){
            @Override
            public Object run(Player player, String... args) {
                String type = args[0];
                if (type.equals("+")){
                    double data = 0.00;
                    for (int i = 1; i < args.length; i++){
                        double d = Double.parseDouble(args[i]);
                        data = data + d;
                    }
                    return data;
                }
                if (type.equals("-")){
                    double data = Double.parseDouble(args[1]);;
                    for (int i = 2; i < args.length; i++){
                        double d = Double.parseDouble(args[i]);
                        data = data - d;
                    }
                    return data;
                }
                if (type.equals("*")){
                    double data = Double.parseDouble(args[1]);;
                    for (int i = 2; i < args.length; i++){
                        double d = Double.parseDouble(args[i]);
                        data = data * d;
                    }
                    return data;
                }
                if (type.equals("/")){
                    double data = Double.parseDouble(args[1]);;
                    for (int i = 2; i < args.length; i++){
                        double d = Double.parseDouble(args[i]);
                        data = data / d;
                    }
                    return data;
                }
                return 0.00;
            }
        };

        //while con t => t => t
        new PAction("while"){
            @Override
            public Object run(Player player, String... args) {
                try {
                    boolean c = Boolean.parseBoolean(args[0]);
                    while (c){
                        for (int i = 1; i < args.length; i = i + 2){
                            PAction.go(args[i],player);
                        }
                    }
                }catch (Exception e){
                    boolean c = Boolean.parseBoolean(PAction.go(args[0],player).toString());
                    while (c){
                        c = Boolean.parseBoolean(PAction.go(args[0],player).toString());
                        for (int i = 1; i < args.length; i = i + 2){
                            PAction.go(args[i],player);
                        }
                    }
                }
                return null;
            }
        };
        //for 5 t => t => t
        new PAction("for"){
            @Override
            public Object run(Player player, String... args) {
                try {
                    int c = Integer.parseInt(args[0]);
                    for (int a = 0;a<c;a++){
                        for (int i = 1; i < args.length; i = i + 2){
                            PAction.go(args[i],player);
                        }
                    }
                }catch (Exception e){
                    int c = Integer.parseInt(PAction.go(args[0],player).toString());
                    for (int a = 0;a<c;a++){
                        for (int i = 1; i < args.length; i = i + 2){
                            PAction.go(args[i],player);
                        }
                    }
                }
                return null;
            }
        };
        //value a
        new PAction("value"){
            @Override
            public Object run(Player player, String... args) {
                return data.get(args[0]);
            }
        };
        //var a = some
        new PAction("var"){
            @Override
            public Object run(Player player, String... args) {
                String cache = "";
                for (int i = 0 ; i< args.length; i++){
                    if (i==0){
                        cache +=args[i];
                    }else {
                        cache += " " + args[i];
                    }
                }
                String word = cache.split("=")[1].trim();
                try {
                    int a =Integer.parseInt(word);
                    data.put(args[0], String.valueOf(a));
                }catch (Exception e){
                    if (word.contains(" ")){
                        data.put(args[0],PAction.go(word,player).toString());
                    }else {
                        data.put(args[0],args[2]);
                    }
                }
                return null;
            }
        };
        //check a = b
        new PAction("check"){
            @Override
            public Object run(Player player, String... args) {
                try {
                    Double a =Double.parseDouble(args[0]);
                    Double b =Double.parseDouble(args[2]);
                    String type = args[1];
                    if (type.equalsIgnoreCase(">")){
                        return a>b;
                    }
                    if (type.equalsIgnoreCase(">=")){
                        return a>=b;
                    }
                    if (type.equalsIgnoreCase("==")){
                        return a==b;
                    }
                    if (type.equalsIgnoreCase("<")){
                        return a<b;
                    }
                    if (type.equalsIgnoreCase("<=")){
                        return a<b;
                    }
                }catch (Exception e){
                    String a = args[0];
                    String b = args[2];
                    try {
                        double a1 =Double.parseDouble(PAction.go(a,player).toString());
                        double b1=Double.parseDouble(PAction.go(b,player).toString());
                        String type1 = args[1];
                        if (type1.equalsIgnoreCase(">")){
                            return a1>b1;
                        }
                        if (type1.equalsIgnoreCase(">=")){
                            return a1>=b1;
                        }
                        if (type1.equalsIgnoreCase("==")){
                            return a1==b1;
                        }
                        if (type1.equalsIgnoreCase("<")){
                            return a1<b1;
                        }
                        if (type1.equalsIgnoreCase("<=")){
                            return a1<=b1;
                        }
                    }catch (Exception ex){
                        return a.equals(b);
                    }
                }
                return false;
            }
        };
        // if (condition) some0 => some1 => some2 : some0 => some1 => some2
        new PAction("if"){
            @Override
            public Object run(Player player, String... args) {
                String cache = "";
                for (int i = 0 ; i< args.length; i++){
                    if (i==0){
                        cache +=args[i];
                    } else {
                        cache += " " + args[i];
                    }
                }
                String pattern = "\\(.*\\)";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(cache);
                while (m.find()){
                    String condition = m.group().replaceAll("\\(","").replaceAll("\\)","");;
                    String newC = cache.substring(cache.indexOf(")") + 1);
                    boolean result = PMath.sum(player,condition);
                    String[] y = newC.split(":")[0].split("=>");
                    String[] n = newC.split(":")[1].split("=>");
                    if (result){
                        for (String s : y){
                            PAction.go(s.trim(),player);
                        }
                        return true;
                    }else {
                        for (String s : n){
                            PAction.go(s.trim(),player);
                        }
                        return false;
                    }
                }
                return false;
            }
        };
        new PAction("tell"){
            @Override
            public Object run(Player player, String... args) {
                if (args[0].contains("$")){
                    PMessage.to(player,PAction.go(args[0],player).toString());
                    return null;
                }
                PMessage.to(player,args[0]);
                return null;
            }
        };
        new PAction("title"){
            @Override
            public Object run(Player player, String... args) {
                if (args[0].contains("$")){
                    if (args.length > 1){
                        PTitle.To(player,PAction.go(args[0],player).toString() + "&nbsp" + PAction.go(args[1],player).toString());
                    }else {
                        PTitle.To(player,PAction.go(args[0],player).toString());
                    }
                    return null;
                }

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
                if (args[0].contains("$")){
                    PActionBar.To(player,PAction.go(args[0],player).toString());
                    return null;
                }
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
                if (args[0].contains("$")){
                    Bukkit.getConsoleSender().sendMessage(PAction.go(args[0],player).toString().replaceAll("&", "§"));
                    return null;
                }
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

