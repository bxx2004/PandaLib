package net.bxx2004.pandalib.bukkit.pitem.nbt;

import jdk.internal.org.jline.utils.InputStreamReader;
import net.bxx2004.java.reflect.PJMethod;
import net.bxx2004.java.reflect.PJVariable;
import net.bxx2004.java.reflect.ReflectUtils;
import net.bxx2004.pandalib.bukkit.pitem.nbt.type.*;
import net.bxx2004.pandalib.bukkit.putil.PNMS;
import net.minecraft.nbt.NBTReadLimiter;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * PItemMeta
 */
public class NBTMeta {
    private NBTNode node;
    private String nbt;
    private Object nmsItem;
    public NBTMeta(ItemStack stack){
        Object nms_item = PNMS.getNMSItemStack(stack);
        this.nmsItem = nms_item;
        if (!PNMS.is1_17UPServer()){
            Object tag = new PJMethod(nms_item.getClass()).InPutName("getTag").runIgnoreArgs(nms_item);
            this.nbt = tag.toString();
            Map map = (Map) new PJVariable(tag).getValue("map");
            node = new NBTNode(map);
        }else {
            try {
                Object tag = nms_item.getClass().getDeclaredField("u").get(nms_item);
                Map map = (Map) tag.getClass().getDeclaredField("x").get(tag);
                this.nbt = tag.toString();
                node = new NBTNode(map);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public String origin(){
        return this.nbt;
    }
    public NBTMeta(NBTNode nbt){
        node = nbt;
    }
    public NBTNode getNode(){
        return node;
    }
    public void setNode(NBTNode node){
        this.node = node;
    }
    public static String numberType(String number){
        if (number.endsWith("b")){
            return "byte";
        }
        if (number.endsWith("s")){
            return "short";
        }
        if (number.endsWith("L")){
            return "long";
        }
        if (number.endsWith("f")){
            return "float";
        }
        if (number.endsWith("d")){
            return "double";
        }
        return "int";
    }
    public static NBTType type(Object value){
        String v = value.toString();
        try {
            int result = Integer.parseInt(v);
            return NBTType.INTEGER;
        }catch (Exception e){
            if (v.endsWith("b") || v.endsWith("s") || v.endsWith("L")){
                return NBTType.INTEGER;
            }
        }
        if (v.startsWith("\"") && v.endsWith("\"")){
            return NBTType.STRING;
        }
        if (v.startsWith("'") && v.endsWith("'")){
            return NBTType.STRING;
        }
        if (v.endsWith("f") || v.endsWith("d")){
            return NBTType.DECIMAL;
        }
        if (v.startsWith("[") && v.endsWith("]")){
            if (v.startsWith("[B;") || v.startsWith("[I;") ||v.startsWith("[L;")){
                return NBTType.ARRAY;
            }else {
                return NBTType.LIST;
            }
        }
        if (v.startsWith("{") && v.endsWith("}")){
            return NBTType.NODE;
        }
        return null;
    }
}
