package net.bxx2004.pandalibloader;

import net.bxx2004.java.reflect.PJMethod;
import net.bxx2004.pandalib.PandaLib;
import net.bxx2004.pandalib.bukkit.manager.Lang;
import net.bxx2004.pandalib.bukkit.pcommands.BukkitCommand;
import net.bxx2004.pandalib.bukkit.pcommands.BukkitSubCommand;
import net.bxx2004.pandalib.bukkit.pcommands.PCommand;
import net.bxx2004.pandalib.bukkit.pcommands.PSubcommands;
import net.bxx2004.pandalib.bukkit.pfile.BukkitResource;
import net.bxx2004.pandalib.bukkit.pfile.PYml;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.reflections.Reflections;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

import static net.bxx2004.pandalibloader.ServerUtils.get256;

public abstract class BukkitPlugin extends JavaPlugin implements PandaLibPlugin<BukkitPlugin>,SafetyPlugin{
    {
        Reflections reflections = new Reflections(getPackage());
        Set<Field> resource = reflections.getFieldsAnnotatedWith(BukkitResource.class);
        for (Field field : resource){
            for (BukkitResource resource1 : field.getAnnotationsByType(BukkitResource.class)){
                try {
                    File file = new File(this.getPath() + resource1.path());
                    if (!file.exists()){
                        PandaLib.saveFileFormPlugin(this,resource1.path(),this.getPath() + resource1.path());
                    }
                    field.set(null,new PYml(this.getPath() + resource1.path(),false));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        Set<Class<?>> mainCommands = reflections.getTypesAnnotatedWith(BukkitCommand.class);
        for (Class c : mainCommands){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for (Annotation annoo : c.getAnnotationsByType(BukkitCommand.class)){
                        BukkitCommand anno = (BukkitCommand) annoo;
                        PCommand command = null;
                        try {
                            command = (PCommand) c.newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        PluginCommand command1 = Bukkit.getServer().getPluginCommand(anno.name());
                        command1.setExecutor(command);
                        if (anno.permissionMessage() != null){
                            command1.setPermissionMessage(anno.permissionMessage());
                        }
                        if (anno.permission() != null){
                            command1.setPermission(anno.permission());
                        }
                        if (anno.aliases() != null){
                            command1.setAliases(Arrays.asList(anno.aliases()));
                        }
                        PCommand.commandMap.put(anno.name(),new ArrayList<>());
                    }
                    Set<Class<?>> subCommandsT = reflections.getTypesAnnotatedWith(BukkitSubCommand.class);
                    Set<Method> subCommandsM = reflections.getMethodsAnnotatedWith(BukkitSubCommand.class);
                    for (Class s : subCommandsT){
                        try {
                            Object o = s.newInstance();
                            for (Annotation anno : s.getDeclaredAnnotations()){
                                BukkitSubCommand sub = (BukkitSubCommand) anno;
                                PSubcommands subcommands = new PSubcommands() {
                                    private String usage = sub.usage();
                                    private String description = sub.description();
                                    @Override
                                    public boolean performCommand(CommandSender sender, String[] strings) {
                                        if (sub.permission() != null){
                                            if (sender.hasPermission(sub.permission())){
                                                if (strings[0].equalsIgnoreCase(sub.usage().split(" ")[0])){
                                                    try {
                                                        s.getDeclaredMethod("performCommand",CommandSender.class,String[].class).invoke(o,sender,strings);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }else {
                                            if (strings[0].equalsIgnoreCase(sub.usage().split(" ")[0])){
                                                try {
                                                    s.getDeclaredMethod("performCommand",CommandSender.class,String[].class).invoke(o,sender,strings);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        return false;
                                    }
                                };
                                PCommand.commandMap.get(sub.mainCommand()).add(subcommands);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    for (Method m : subCommandsM){
                        for (Annotation anno : m.getDeclaredAnnotations()){
                            if (anno instanceof BukkitSubCommand){
                                BukkitSubCommand sub = (BukkitSubCommand) anno;
                                PSubcommands subcommands = new PSubcommands() {
                                    private String usage = sub.usage();
                                    private String description = sub.description();
                                    @Override
                                    public boolean performCommand(CommandSender sender, String[] strings) {
                                        if (sub.permission() != null){
                                            if (sender.hasPermission(sub.permission())){
                                                if (strings[0].equalsIgnoreCase(sub.usage().split(" ")[0])){
                                                    try {
                                                        m.invoke(null,sender,strings);
                                                    } catch (IllegalAccessException e) {
                                                        e.printStackTrace();
                                                    } catch (InvocationTargetException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }else {
                                            if (strings[0].equalsIgnoreCase(sub.usage().split(" ")[0])){
                                                try {
                                                    m.invoke(null,sender,strings);
                                                } catch (IllegalAccessException e) {
                                                    e.printStackTrace();
                                                } catch (InvocationTargetException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        return false;
                                    }
                                };
                                PCommand.commandMap.get(sub.mainCommand()).add(subcommands);
                            }
                        }
                    }
                }
            },5000);
        }
    }
    public abstract String getPackage();
    {
        PJMethod method = new PJMethod(PandaLib.class,"init",PandaLibPlugin.class);
        method.runMethod(null,this);
        if (this.verify() != null){
            SafetyPluginMessage<BukkitPlugin> message = this.verify();
            if (getAllUsers(message.getUrl()).contains(getCode())){
                Lang.print("§b[§f PandaLibLoader §b] §f- §7插件 §f" + this.getName() + " §7注册成功");
                super.onEnable();
                Lang.print("&e["+ message.getPandaLibPlugin().getPlugin().getName() +"&e] &a认证成功,欢迎使用, 机器码:" + getCode());
            }else {
                Lang.print("&e["+ message.getPandaLibPlugin().getPlugin().getName() +"&e] &c您尚未取得该插件的使用权, 机器码:" + getCode());
                Bukkit.shutdown();
            }
        }else {
            Lang.print("§b[§f PandaLibLoader §b] §f- §7插件 §f" + this.getName() + " §7注册成功");
            super.onEnable();
        }
    }
    public BukkitPlugin() {
        super();
    }

    @Override
    public InputStream getJarFile(String name) {
        return this.getResource(name);
    }

    @Override
    public String getPath() {
        return this.getDataFolder().getAbsolutePath();
    }

    @Override
    public SafetyPluginMessage verify() {
        return null;
    }

    @Override
    public BukkitPlugin getPlugin() {
        return this;
    }

    protected BukkitPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    protected File getFile() {
        return super.getFile();
    }

    @Override
    public FileConfiguration getConfig() {
        return super.getConfig();
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
    }

    @Override
    public void saveConfig() {
        super.saveConfig();
    }

    @Override
    public void saveDefaultConfig() {
        super.saveDefaultConfig();
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        super.saveResource(resourcePath, replace);
    }

    @Override
    public InputStream getResource(String filename) {
        return super.getResource(filename);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return super.onCommand(sender, command, label, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return super.onTabComplete(sender, command, alias, args);
    }

    @Override
    public PluginCommand getCommand(String name) {
        return super.getCommand(name);
    }
    @Override
    public void onEnable() {
        super.onEnable();
    }
    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return super.getDefaultWorldGenerator(worldName, id);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public List<String> getAllUsers(URL url){
        List<String> list = new ArrayList<>();
        try {
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            Iterator<String> i = br.lines().iterator();
            while (i.hasNext()){
                list.add(i.next());
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public String getCode(){
        try {
            Process process = Runtime.getRuntime().exec(
                    new String[] { "wmic", "cpu", "get", "ProcessorId" });
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            String property = sc.next();
            String serial = sc.next();
            return get256(serial);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}