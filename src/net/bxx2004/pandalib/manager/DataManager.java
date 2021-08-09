package net.bxx2004.pandalib.manager;

import net.bxx2004.pandalib.PandaLib;
import net.bxx2004.pandalib.pfile.PYml;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class DataManager {
    public static void setJar(){
        String i = System.getProperty("java.version").split(".")[0];
        if (Integer.parseInt(i) >= 16){
            Lang.print("§b[§f PandaLib §b] §f- §7由于你使用了 Java16 所以您无法加载更多服务".replaceAll("§", "§"));
        }else {
            File file = new File("plugins/PandaLib/libs");
            if (!file.exists()){
                file.mkdirs();
            }
            if (loopJar("kotlin-reflect") != null){
                JarLoader.loadJarFile(new File(loopJar("kotlin-reflect")));
            }
            if (loopJar("kotlin-stdlib") != null){
                JarLoader.loadJarFile(new File(loopJar("kotlin-stdlib")));
            }
            if (loopJar("kotlin-stdlib-jdk7") != null){
                JarLoader.loadJarFile(new File(loopJar("kotlin-stdlib-jdk7")));
            }
            if (loopJar("kotlin-stdlib-jdk8") != null){
                JarLoader.loadJarFile(new File(loopJar("kotlin-stdlib-jdk8")));
            }
            if (loopJar("kotlin-test") != null){
                JarLoader.loadJarFile(new File(loopJar("kotlin-test")));
            }
        }
    }
    private static String loopJar(String name){
        File file = new File("plugins/PandaLib/libs/" + name + ".jar");
        if (file.exists()){
            return file.getAbsolutePath();
        }else {
            return null;
        }
    }
    public static List<File> getCustomLibs(){
        List<File> list = new ArrayList<>();
        PYml yml = new PYml("plugins/PandaLib/config.yml",false);
        for (String string : yml.getYaml().getStringList("LIBS")){
            try {
                list.add(new File("plugins/PandaLib/libs/" + string + ".jar"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }
    public static FileConfiguration getConfig(){
        PandaLib.getInstance().reloadConfig();
        return PandaLib.getInstance().getConfig();
    }
    public static boolean isINFO(){
        return PandaLib.getInstance().getConfig().getBoolean("LIBINFO.PRINTMESSAGE");
    }
}
final class JarLoader{
    private static Method addURL = initAddMethod();

    /** 初始化方法 */
    private static final Method initAddMethod() {
        try {
            Method add = URLClassLoader.class
                    .getDeclaredMethod("addURL", new Class[] { URL.class });
            add.setAccessible(true);
            return add;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static URLClassLoader system = (URLClassLoader) ClassLoader.getSystemClassLoader();

    /**
     * 循环遍历目录，找出所有的JAR包
     */
    private static final void loopFiles(File file, List<File> files) {
        if (file.isDirectory()) {
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                loopFiles(tmp, files);
            }
        } else {
            if (file.getAbsolutePath().endsWith(".jar") || file.getAbsolutePath().endsWith(".zip")) {
                files.add(file);
            }
        }
    }

    /**
     * <pre>
     * 加载JAR文件
     * </pre>
     *
     * @param file
     */
    public static final void loadJarFile(File file) {
        try {
            addURL.invoke(system, new Object[] { file.toURI().toURL() });
            Lang.print("§b[§f PandaLib §b] §f- §7正在加载依赖项-> " + file.getAbsolutePath().replaceAll("§", "§"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <pre>
     * 从一个目录加载所有JAR文件
     * </pre>
     *
     * @param path
     */
    public static final void loadJarPath(String path) {
        List<File> files = new ArrayList<File>();
        File lib = new File(path);
        loopFiles(lib, files);
        for (File file : files) {
            loadJarFile(file);
        }
    }
}