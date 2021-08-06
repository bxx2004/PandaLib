package net.bxx2004.java.reflect;

import java.lang.reflect.Constructor;

/**
 * @since 1.5.2
 * 关于一个类的反射
 */
public class PJObject {
    private Class clazz;
    private String c;
    private Class[] args;
    private Object[] objects;
    /**
     * 构造一个反射类
     * @param className 类名
     */
    public PJObject(String className){
        try {
            this.clazz = Class.forName(className);
            this.c = className;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造方法获取对象
     * @param args 参数
     * @param objects 参数
     * @return 对象
     */
    public Object newObject(Class[] args,Object[] objects){
        try {
            this.args = args;
            this.objects = objects;
            Constructor c = this.clazz.getConstructor(args);
            c.setAccessible(true);
            return c.newInstance(objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取PJ变量对象
     * @param isobject 是否为对象形式
     * @return 变量对象
     */
    public PJVariable getPJVariable(boolean isobject){
        if (isobject){
            return new PJVariable(newObject(this.args,this.objects));
        }else {
            return new PJVariable(c);
        }
    }
    /**
     * 获取PJ方法对象
     * @param methodName 方法名
     * @param c 参数
     * @return 方法对象
     */
    public PJMethod getPJMthod(String methodName,Class... c){
        return new PJMethod(clazz,methodName,c);
    }
}
