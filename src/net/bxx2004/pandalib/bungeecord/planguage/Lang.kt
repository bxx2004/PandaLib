package net.bxx2004.pandalib.bungeecord.planguage

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent

object Lang {
    fun print(message:String){
        ProxyServer.getInstance().console.sendMessage(TextComponent(message.replace("&","§")))
    }
}