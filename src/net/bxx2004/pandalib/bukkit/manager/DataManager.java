package net.bxx2004.pandalib.bukkit.manager;

import net.bxx2004.pandalib.bukkit.PandaLib;
import org.bukkit.configuration.file.FileConfiguration;

public class DataManager {
    public static FileConfiguration getConfig(){
        PandaLib.getInstance().reloadConfig();
        return PandaLib.getInstance().getConfig();
    }
    public static boolean isINFO(){
        return PandaLib.getInstance().getConfig().getBoolean("LIBINFO.PRINTMESSAGE");
    }
}
