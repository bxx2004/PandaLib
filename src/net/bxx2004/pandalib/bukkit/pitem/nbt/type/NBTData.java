package net.bxx2004.pandalib.bukkit.pitem.nbt.type;

public interface NBTData {
    public String asString();
    public NBTType type();
    public Object value();
    public String key();
}
