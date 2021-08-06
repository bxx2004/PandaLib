package net.bxx2004.pandalib.bungeecord

import com.google.common.io.ByteStreams
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import net.bxx2004.pandalib.bungeecord.planguage.Lang
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.*
import java.lang.Exception
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class PandaLib : Plugin(){
    var config : Configuration? = null

    override fun onEnable() {
        var file : File = File(dataFolder, "config.yml")
        if (file.exists()){
            config = ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(file)
        }else{
            var file2 : File = File("plugins/PandaLib")
            file2.mkdirs()
            file.createNewFile()
            var ins = this.getResourceAsStream("config.yml")
            var out = FileOutputStream(file)
            ByteStreams.copy(ins, out)
        }
        if (config!!.getBoolean("LIBUPDATE.UPDATEMESSAGE")) {
            val date = Date()
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val a = format.format(date)
            Lang.print("&e┏━━━━━━━━━ PandaLib " + description.version + " ━━━━━━━━━━━━━━━━━━┓")
            Lang.print("&e┃  &fPandaLib is Running..." + "                   &e┃")
            Lang.print("&e┃  &6最新版本: &f" + getLastVestion()!!.get(0) + " &7| &6更新时间: &f" + getLastVestion()!!.get(1) + "   &e┃")
            Lang.print("&e┃  &b作者: &fbxx2004 &7| &fhttp://linyanmc.cn/" + "      &e┃")
            Lang.print("&e┃  &b交流群: &f1038503485  &7|  &b$a  &e┃")
            Lang.print("&e┖━━━━━━━━━━ PandaLib ━━━━━━━━━━━━━━━━━━━━━━━┛")
        }
    }
    fun getLastVestion(): List<String>? {
        val list: MutableList<String> = ArrayList()
        try {
            val url = URL("http://linyanmc.cn/version.json")
            val `is` = url.openStream()
            val br = BufferedReader(InputStreamReader(`is`, "UTF-8"))
            val parser = JsonParser()
            val version = parser.parse(br) as JsonArray
            for (i in 0 until version.size()) {
                val `object` = version[i].asJsonObject
                if (`object`["PluginName"].asString.equals(description.name, ignoreCase = true)) {
                    list.add(`object`["version"].asString)
                    list.add(`object`["date"].asString)
                    return list
                }
            }
            br.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }
    override fun onDisable() {

    }
}