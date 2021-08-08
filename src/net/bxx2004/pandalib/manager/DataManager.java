package net.bxx2004.pandalib.manager;

import net.bxx2004.pandalib.PandaLib;
import net.bxx2004.pandalib.pfile.PYml;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DataManager {
    public static void setJar(){
        JarLoader loader = new JarLoader();
        File file = new File("plugins/PandaLib/libs");
        if (!file.exists()){
            file.mkdirs();
        }
        if (loopJar("kotlin-reflect") != null){
            System.out.println(loopJar("kotlin-reflect"));
            loader.withFile(new File(loopJar("kotlin-reflect")));
        }
        if (loopJar("kotlin-stdlib") != null){
            loader.withFile(new File(loopJar("kotlin-stdlib")));
        }
        if (loopJar("kotlin-stdlib-jdk7") != null){
            loader.withFile(new File(loopJar("kotlin-stdlib-jdk7")));
        }
        if (loopJar("kotlin-stdlib-jdk8") != null){
            loader.withFile(new File(loopJar("kotlin-stdlib-jdk8")));
        }
        if (loopJar("kotlin-test") != null){
            loader.withFile(new File(loopJar("kotlin-test")));
        }
        for (File file1 : getCustomLibs()){
            loader.withFile(file1);
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
final class JarLoader extends URLClassLoader{

    public JarLoader() {
        super(new URL[]{});
    }
    public JarLoader withFile(String jarFile) {
        return withFile(new File(jarFile));
    }

    public JarLoader withFile(File jarFile) {
        try {
            if (jarFile.exists()){
                addURL(jarFile.toURI().toURL());
            }
            Lang.print("正在加载依赖项-> " + jarFile.getAbsolutePath());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return this;
    }

    public JarLoader withLibDir(String path) {
        Stream.of(new File(path).listFiles(f -> f.getName().endsWith(".jar"))).forEach(this::withFile);
        return this;
    }
}