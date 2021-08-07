package net.bxx2004.pandalib.manager;

import net.bxx2004.pandalib.PandaLib;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public static List<File> getCustomLibs(){
        List<File> list = new ArrayList<>();
        for (String string : getConfig().getStringList("LIBS")){
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
final class JarLoader {
    private static Method addURL = initAddMethod();

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
    public static final void loadJarFile(File file) {
        try {
            addURL.invoke(system, new Object[] { file.toURI().toURL() });
            Lang.print("正在加载依赖项-> " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static final void loadJarPath(String path) {
        List<File> files = new ArrayList<File>();
        File lib = new File(path);
        loopFiles(lib, files);
        for (File file : files) {
            loadJarFile(file);
        }
    }
}