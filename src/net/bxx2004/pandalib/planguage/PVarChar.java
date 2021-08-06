package net.bxx2004.pandalib.planguage;

import net.bxx2004.java.reflect.PJMethod;
import net.bxx2004.java.reflect.ReflectUtils;

import java.util.HashMap;

/**
 * 类似于变量
 * @since 1.5.5
 */
public class PVarChar {
    private HashMap<String,PVarCharObject> map;
    private HashMap<String,PVarCharValue> newmap;
    public PVarChar(boolean old){
        if (old){
            map = new HashMap<String,PVarCharObject>();
        }else {
            newmap = new HashMap<String, PVarCharValue>();
        }
    }
    /**
     * 放入一个变量
     * @param key 对应值
     * @param value 变量值
     */
    public void put(String key, PVarCharValue value){
        newmap.put(key,value);
    }
    /**
     * 取出一个变量
     * @param key 对应值
     * @return 取出的变量
     */
    public Object get(String key){
        return newmap.get(key).vaule();
    }
    /**
     * 取出一个变量
     * @param key 对应值
     * @return 取出的变量
     */
    public String getAsString(String key){
        return (String) newmap.get(key).vaule();
    }
    public Integer getAsInt(String key){
        return (int) newmap.get(key).vaule();
    }
    public Boolean getAsBoolean(String key){
        return (boolean) newmap.get(key).vaule();
    }
    public Byte getAsByte(String key){
        return (byte) newmap.get(key).vaule();
    }
    public Short getAsShort(String key){
        return (short) newmap.get(key).vaule();
    }
    public Long getAsLong(String key){
        return (long) newmap.get(key).vaule();
    }
    public Float getAsFloat(String key){
        return (float) newmap.get(key).vaule();
    }
    public Double getAsDouble(String key){
        return (double) newmap.get(key).vaule();
    }
    public Character getAsChar(String key){
        return (char) newmap.get(key).vaule();
    }
    /**
     * 获取一个PVarCharObject对象
     * @param inClass 在哪个类
     * @param methodName 方法名字
     * @param args 参数
     * @return PVarCharObject
     */
    @Deprecated
    public static PVarCharObject getObject(String inClass,String methodName,Object... args){
        Class[] classes = new Class[args.length];
        for (int i = 0 ; i < classes.length; i++){
            classes[i] = args[i].getClass();
        }
        return new PVarCharObject(ReflectUtils.getClass(inClass),methodName,classes,args);
    }

    /**
     * 取出一个变量
     * @param key 对应值
     * @param ob 执行对象
     * @return 取出的变量
     */
    @Deprecated
    public Object get(String key,Object ob){
        PJMethod method = new PJMethod(map.get(key).getIn());
        return method.InPutName(map.get(key).getMe()).InPutArg(map.get(key).getObjects()).run(ob);
    }
    /**
     * 获取该对象
     * @return 该对象
     */
    @Deprecated
    public static PVarChar create(){
        return new PVarChar(true) {};
    }
    /**
     * 放入一个变量
     * @param key 对应值
     * @param new_ 新的
     */
    @Deprecated
    public void put(String key, PVarCharObject new_){
        map.put(key,new_);
    }
}
@Deprecated
class PVarCharObject{
    private Class in;
    private String me;
    private Class[] classes;
    private Object[] objects;
    public PVarCharObject(Class in,String methodname,Class[] classes,Object[] objects){
        this.in = in;
        this.me = methodname;
        this.classes = classes;
        this.objects = objects;
    }
    public Class getIn() {
        return in;
    }
    public String getMe() {
        return me;
    }
    public Class[] getClasses() {
        return classes;
    }
    public Object[] getObjects() {
        return objects;
    }
}
abstract class PVarCharValue{
    abstract public Object vaule();
}
