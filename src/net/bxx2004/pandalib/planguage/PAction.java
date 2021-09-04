package net.bxx2004.pandalib.planguage;

import net.bxx2004.pandalib.PandaLib;
import net.bxx2004.pandalib.manager.Lang;
import net.bxx2004.pandalib.pfile.PYml;
import net.bxx2004.pandalib.putil.PMath;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 执行特殊的动作
 * @since 1.5.3
 */
public abstract class PAction{
    public static PYml pscriptdata = new PYml("plugins/PandaLib/scriptdata.yml",true);
    public static HashMap<String,Object> tscriptdata = new HashMap<String, Object>();
    private static HashMap<String,PActionObject> map = new HashMap<String, PActionObject>();
    static {
        map.put("if",null);
        map.put("for",null);
        map.put("check",null);
        map.put("_",null);
    }
    public PAction(String word){
       String key = word.toLowerCase();
        if (!map.keySet().contains(key)){
            map.put(key, new PActionObject() {
                @Override
                Object vaule(Player player1,String... args) {
                   return run(player1, args);
                }
            });
        }else {
            Lang.print("&c[&fPAction&c] &c无法为该动作 &f" + key +" &c注册,因为一个存在了一个名称相同的动作...");
        }
    }
    public abstract Object run(Player player,String... args);
    /**
     * 执行语法
     * @param word 语法
     * @param player 玩家
     */
    public static Object go(List<String> word,Player player){
        try {
            List<String> actionlist = new ArrayList<String>();
            boolean b = false;
            for (String s : word) {
                if (s.contains("{")) {
                    b = true;
                }
                if (b) {
                    actionlist.add(s.trim());
                }else {
                    return goWord(s,player);
                }
                if (s.contains("}")){
                    if (!s.contains("} else {")){
                        b = false;
                        PAction.goBlock(actionlist, player);
                    }
                }
            }
        }catch (Exception e){
            Lang.error("&4PandaScript运行时错误",word);
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 执行语法
     * @param word 语法
     * @param player 玩家
     */
    @Deprecated
    public static Object go(String word,Player player){
        try {
            goWord(word,player);
        }catch (Exception e){
            Lang.error("&4PandaScript运行时错误",word);
            e.printStackTrace();
        }
        return null;
    }
    private static boolean sum(Player player,String term){
        try {
            if (term.contains("|")){
                String[] a = term.split("\\|");
                for (String b : a){
                    boolean result = (boolean) goWord(b.trim(),player);
                    if (result){
                        return true;
                    }
                }
                return false;
            }
            if (term.contains("&")){
                String[] a = term.split("&");
                for (String b : a){
                    boolean result = (boolean) goWord(b.trim(),player);
                    if (!result){
                        return false;
                    }
                }
                return true;
            }
            boolean result = (boolean) goWord(term.trim(),player);
            return result;
        }catch (Exception e){
            Lang.error("&4异常: IF条件异常",term);
        }
        return false;
    }
    /**
     * 该语句是否存在
     * @param word 语句
     * @return 是否存在
     */
    public static boolean has(String word){
        return map.keySet().contains(word.toLowerCase());
    }

    /**
     * 用于执行单条语句
     * @param word 语句
     * @param player 玩家
     * @return 执行结果
     */
    public static Object goWord(String word,Player player){
        String words = word.split(" ")[0].toLowerCase();
        String[] args = word.split(" ");
        String[] args1 = new String[args.length - 1];
        for (int i = 0; i < args.length; i++){
            if (i >= args1.length){
                continue;
            }else {
                args1[i] = args[i+1];
            }
        }
        if (!map.keySet().contains(words) && (!word.trim().startsWith("_"))){
            Lang.print("&c[&fPAction&c] &c执行失败,该动作 &f" + words +" &c无效...");
        }else {
            if (word.trim().startsWith("_")){
                return word.replaceAll("_","");
            } else if (word.trim().startsWith("^")){
                if (word.trim().startsWith("^^")){
                    return pscriptdata.get(word.replaceAll("\\^",""));
                }
                return tscriptdata.get(word.replaceAll("\\^",""));
            }else if (words.equalsIgnoreCase("check")){
                try {
                    String checklang1 = word.substring(7,word.indexOf("]"));
                    String sign = word.substring(word.indexOf("]") + 2,word.indexOf("]")+3);
                    String checklang2 = word.substring(
                            word.indexOf("[",word.indexOf(sign)) + 1, word.indexOf("]",word.indexOf(sign))
                    );
                    if (sign.equals("=")){
                        try {
                            int o1 =  Integer.parseInt(goWord(checklang1,player).toString());
                            int o2 =  Integer.parseInt(goWord(checklang2,player).toString());
                            return o1 == o2;
                        }catch (Exception e){ }
                        try {
                            long o1 = Long.parseLong(goWord(checklang1,player).toString());
                            long o2 = Long.parseLong(goWord(checklang2,player).toString());
                            return o1 == o2;
                        }catch (Exception e){

                        }
                        try {
                            float o1 = Float.parseFloat(goWord(checklang1,player).toString());
                            float o2 = Float.parseFloat(goWord(checklang2,player).toString());
                            return o1 == o2;
                        }catch (Exception e){}
                        try {
                            double o1 = Double.parseDouble(goWord(checklang1,player).toString());
                            double o2 = Double.parseDouble(goWord(checklang2,player).toString());
                            return o1 == o2;
                        }catch (Exception e){}
                        try {
                            String o1 = (String) goWord(checklang1,player);
                            String o2 = (String) goWord(checklang2,player);
                            return o1.equals(o2);
                        }catch (Exception e){}
                    }
                    if (sign.equals(">")){
                        try {
                            int o1 =  Integer.parseInt(goWord(checklang1,player).toString());
                            int o2 =  Integer.parseInt(goWord(checklang2,player).toString());
                            return o1 > o2;
                        }catch (Exception e){ }
                        try {
                            long o1 = Long.parseLong(goWord(checklang1,player).toString());
                            long o2 = Long.parseLong(goWord(checklang2,player).toString());
                            return o1 > o2;
                        }catch (Exception e){

                        }
                        try {
                            float o1 = Float.parseFloat(goWord(checklang1,player).toString());
                            float o2 = Float.parseFloat(goWord(checklang2,player).toString());
                            return o1 > o2;
                        }catch (Exception e){}
                        try {
                            double o1 = Double.parseDouble(goWord(checklang1,player).toString());
                            double o2 = Double.parseDouble(goWord(checklang2,player).toString());
                            return o1 > o2;
                        }catch (Exception e){}
                    }
                    if (sign.equals(">=")){
                        try {
                            int o1 =  Integer.parseInt(goWord(checklang1,player).toString());
                            int o2 =  Integer.parseInt(goWord(checklang2,player).toString());
                            return o1 >= o2;
                        }catch (Exception e){ }
                        try {
                            long o1 = Long.parseLong(goWord(checklang1,player).toString());
                            long o2 = Long.parseLong(goWord(checklang2,player).toString());
                            return o1 >= o2;
                        }catch (Exception e){

                        }
                        try {
                            float o1 = Float.parseFloat(goWord(checklang1,player).toString());
                            float o2 = Float.parseFloat(goWord(checklang2,player).toString());
                            return o1 >= o2;
                        }catch (Exception e){}
                        try {
                            double o1 = Double.parseDouble(goWord(checklang1,player).toString());
                            double o2 = Double.parseDouble(goWord(checklang2,player).toString());
                            return o1 >= o2;
                        }catch (Exception e){}
                    }
                    if (sign.equals("<")){
                        try {
                            int o1 =  Integer.parseInt(goWord(checklang1,player).toString());
                            int o2 =  Integer.parseInt(goWord(checklang2,player).toString());
                            return o1 < o2;
                        }catch (Exception e){ }
                        try {
                            long o1 = Long.parseLong(goWord(checklang1,player).toString());
                            long o2 = Long.parseLong(goWord(checklang2,player).toString());
                            return o1 < o2;
                        }catch (Exception e){

                        }
                        try {
                            float o1 = Float.parseFloat(goWord(checklang1,player).toString());
                            float o2 = Float.parseFloat(goWord(checklang2,player).toString());
                            return o1 < o2;
                        }catch (Exception e){}
                        try {
                            double o1 = Double.parseDouble(goWord(checklang1,player).toString());
                            double o2 = Double.parseDouble(goWord(checklang2,player).toString());
                            return o1 < o2;
                        }catch (Exception e){}
                    }
                    if (sign.equals("<=")){
                        try {
                            int o1 =  Integer.parseInt(goWord(checklang1,player).toString());
                            int o2 =  Integer.parseInt(goWord(checklang2,player).toString());
                            return o1 <= o2;
                        }catch (Exception e){ }
                        try {
                            long o1 = Long.parseLong(goWord(checklang1,player).toString());
                            long o2 = Long.parseLong(goWord(checklang2,player).toString());
                            return o1 <= o2;
                        }catch (Exception e){

                        }
                        try {
                            float o1 = Float.parseFloat(goWord(checklang1,player).toString());
                            float o2 = Float.parseFloat(goWord(checklang2,player).toString());
                            return o1 <= o2;
                        }catch (Exception e){}
                        try {
                            double o1 = Double.parseDouble(goWord(checklang1,player).toString());
                            double o2 = Double.parseDouble(goWord(checklang2,player).toString());
                            return o1 <= o2;
                        }catch (Exception e){}
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Lang.error("&4CHECK检查异常",word);
                }
                return null;
            } else {
                return map.get(words).vaule(player,args1);
            }
        }
        return "[Null]";
    }
    /**
     * 用于执行{}模块的代码
     * @param word 代码
     * @param player 玩家
     * @return 返回值
     */
    public static Object goBlock(List<String> word, Player player){
        if (word.get(0).trim().contains("if")){
            try {
                String term = word.get(0).trim().substring(4, word.get(0).indexOf("then") - 2).trim();
                List<String> trueAction = new ArrayList<String>();
                List<String> falseAction = new ArrayList<String>();
                for (int p = 1; p < word.indexOf("} else {") ; p++){
                    trueAction.add(word.get(p));
                }
                for (int p = word.indexOf("} else {") + 1; p < word.size() ; p++){
                    falseAction.add(word.get(p));
                }
                if (sum(player, term)) {
                    go(trueAction,player);
                } else {
                    go(falseAction,player);
                }
            }catch (Exception e){
                e.printStackTrace();
                Lang.error("&4IF异常", word);
            }
        }
        if (word.get(0).trim().contains("for")){
            try {
                String term = word.get(0).trim().substring(5,word.get(0).indexOf("]")).trim();
                int next = Integer.parseInt(goWord(term.trim(),player).toString());
                List<String> action = new ArrayList<String>();
                action.addAll(word);
                action.remove(0);
                action.remove(action.size()-1);
                for (int i = 0; i< next; i++){
                    go(action,player);
                }
            }catch (Exception e){
                e.printStackTrace();
                Lang.error("&4FOR循环异常", word);
            }
        }
        return null;
    }
}
abstract class PActionObject{
    abstract Object vaule(Player player,String... args);
}