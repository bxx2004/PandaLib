package net.bxx2004.pandalib.bungeecord.planguage

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent

/**
 * 消息工具
 */
object PMessage {
    /**
     * @param message 要发送的文本
     */
    fun print(message:String){
        ProxyServer.getInstance().console.sendMessage(TextComponent(message.replace("&","§")))
    }
}