package net.bxx2004.pandalib;

import net.bxx2004.java.reflect.PJMethod;
import net.bxx2004.java.reflect.PJObject;
import net.bxx2004.java.reflect.ReflectUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

/**
 * PandaLibAPI 一些工具
 * @since 2.0.1
 */
public class PandaLibAPI {
    private PandaLib libMain;

    /**
     * 构造API
     * @param lib PandaLib主类
     */
    public PandaLibAPI(PandaLib lib){
        this.libMain = lib;
    }

    /**
     * 获取实例对象
     * @return PandaLibAPI对象
     */
    public static PandaLibAPI getInstance(){
        return new PandaLibAPI(PandaLib.getInstance());
    }

    /**
     * 从一个目录下加载所有Jar包
     * @param path 目录
     */
    public void loadJarFromPath(String path){
        PJObject object = new PJObject(ReflectUtils.getClass("net.bxx2004.pandalib.manager.JarLoader"));
        object.getPJMthod().InPutName("loadJarPath").InPutArg(path).run(null);
    }
    /**
     * 加载一个jar包
     * @param file 包
     */
    public void loadJarFromFile(File file){
        PJObject object = new PJObject(ReflectUtils.getClass("net.bxx2004.pandalib.manager.JarLoader"));
        object.getPJMthod().InPutName("loadJarFile").InPutArg(file).run(null);

    }

    /**
     * 获取前置主类
     * @return 前置主类
     */
    public PandaLib getPandaLib(){
        return libMain;
    }

    /**
     * 获取某个插件的plugin.yml文件
     * @param plugin 插件
     * @return plugin.yml
     */
    public FileConfiguration getLoadYmlFromPlugin(Plugin plugin){
        return YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource("plugin.yml")));
    }

    /**
     * 从某个插件的Jar中释放文件
     * @param plugin 插件
     * @param filePath 文件路径
     * @param outPath 释放路径
     */
    public void saveFileFormPlugin(Plugin plugin,String filePath,String outPath){
        InputStreamReader input = new InputStreamReader(plugin.getResource(filePath));
        File file = new File(outPath);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new PrintStream(outPath));
            while (input.read() != -1){
                writer.write(input.read());
            }
            input.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
