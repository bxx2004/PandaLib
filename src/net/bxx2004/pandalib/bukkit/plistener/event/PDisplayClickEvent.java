package net.bxx2004.pandalib.bukkit.plistener.event;

import net.bxx2004.java.reflect.PJVariable;
import net.bxx2004.pandalib.bukkit.pentity.PDisplay;
import org.bukkit.entity.Player;

public class PDisplayClickEvent extends PandaLibExtendEvent{
    private boolean c;
    private PDisplay display;
    private Player player;
    public PDisplayClickEvent(PDisplay display, Player player){
        this.player = player;
        this.display = display;
    }
    @Override
    public void setCancelled(boolean bl) {
        this.c = bl;
    }

    @Override
    public boolean isCancelled() {
        return c;
    }

    /**
     * 获取当前发生点击的Display
     * @return Display
     */
    public PDisplay getDisplay(){
        return display;
    }

    /**
     * 获取当前点击的Display的ID
     * @return Display的ID
     */
    public String getDisPlayID(){
        return (String) new PJVariable(display).getValue("id");
    }
    public Player getPlayer(){
        return this.player;
    }
}
