package net.bxx2004.pandalib.planguage;

import net.bxx2004.pandalib.manager.Lang;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 执行特殊的动作
 * @since 1.5.3
 */
public abstract class PAction{
    private static HashMap<String,PActionObject> map = new HashMap<String, PActionObject>();
    static {
        map.put("if",null);
        map.put("for",null);
        map.put("check",null);
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
    public static Object go(String word,Player player){
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
        if (!map.keySet().contains(words)){
            Lang.print("&c[&fPAaction&c] &c执行失败,该动作 &f" + words +" &c无效...");
        }else {
            if (words.equalsIgnoreCase("if")) {
                String term = word.substring(4, word.indexOf("then") - 2).trim();
                String trueAction = word.substring(word.indexOf("then") + 6, word.indexOf("else") - 2).trim();
                String falseAction = word.substring(word.indexOf("else") + 6, word.length() - 1).trim();
                if (sum(player, term)) {
                    String[] t = trueAction.split(",");
                    for (String tr : t) {
                        go(tr, player);
                    }
                } else {
                    String[] f = falseAction.split(",");
                    for (String fa : f) {
                        go(fa, player);
                    }
                }
                return null;
            }else if (words.equalsIgnoreCase("for")){
                try {
                    int next = Integer.parseInt(word.split(" ")[1]);
                    String lang = word.substring(word.indexOf("[") + 1,word.indexOf("]"));
                    String[] lang1 = lang.split(",");
                    for (int i = 0; i< next; i++){
                        for (String s : lang1){
                            go(s,player);
                        }
                    }
                }catch (Exception e){
                    Lang.error("&4FOR循环异常",word);
                }
                return null;
            }else if (words.equalsIgnoreCase("check")){
                try {
                    String checklang1 = word.substring(7,word.indexOf("]"));
                    String sign = word.substring(word.indexOf("]") + 2,word.indexOf("]")+3);
                    String checklang2 = word.substring(
                            word.indexOf("[",word.indexOf(sign)) + 1, word.indexOf("]",word.indexOf(sign))
                    );
                    if (sign.equals("=")){
                        try {
                            int o1 = (int) go(checklang1,player);
                            int o2 = (int) go(checklang2,player);
                            return o1 == o2;
                        }catch (Exception e){}
                        try {
                            long o1 = (long) go(checklang1,player);
                            long o2 = (long) go(checklang2,player);
                            return o1 == o2;
                        }catch (Exception e){}
                        try {
                            float o1 = (float) go(checklang1,player);
                            float o2 = (float) go(checklang2,player);
                            return o1 == o2;
                        }catch (Exception e){}
                        try {
                            double o1 = (double) go(checklang1,player);
                            double o2 = (double) go(checklang2,player);
                            return o1 == o2;
                        }catch (Exception e){}
                        try {
                            String o1 = (String) go(checklang1,player);
                            String o2 = (String) go(checklang2,player);
                            return o1.equals(o2);
                        }catch (Exception e){}
                    }
                    if (sign.equals(">")){
                        try {
                            int o1 = (int) go(checklang1,player);
                            int o2 = (int) go(checklang2,player);
                            return o1 > o2;
                        }catch (Exception e){}
                        try {
                            long o1 = (long) go(checklang1,player);
                            long o2 = (long) go(checklang2,player);
                            return o1 > o2;
                        }catch (Exception e){}
                        try {
                            float o1 = (float) go(checklang1,player);
                            float o2 = (float) go(checklang2,player);
                            return o1 > o2;
                        }catch (Exception e){}
                        try {
                            double o1 = (double) go(checklang1,player);
                            double o2 = (double) go(checklang2,player);
                            return o1 > o2;
                        }catch (Exception e){}
                    }
                    if (sign.equals(">=")){
                        try {
                            int o1 = (int) go(checklang1,player);
                            int o2 = (int) go(checklang2,player);
                            return o1 >= o2;
                        }catch (Exception e){}
                        try {
                            long o1 = (long) go(checklang1,player);
                            long o2 = (long) go(checklang2,player);
                            return o1 >= o2;
                        }catch (Exception e){}
                        try {
                            float o1 = (float) go(checklang1,player);
                            float o2 = (float) go(checklang2,player);
                            return o1 >= o2;
                        }catch (Exception e){}
                        try {
                            double o1 = (double) go(checklang1,player);
                            double o2 = (double) go(checklang2,player);
                            return o1 >= o2;
                        }catch (Exception e){}
                    }
                    if (sign.equals("<")){
                        try {
                            int o1 = (int) go(checklang1,player);
                            int o2 = (int) go(checklang2,player);
                            return o1 < o2;
                        }catch (Exception e){}
                        try {
                            long o1 = (long) go(checklang1,player);
                            long o2 = (long) go(checklang2,player);
                            return o1 < o2;
                        }catch (Exception e){}
                        try {
                            float o1 = (float) go(checklang1,player);
                            float o2 = (float) go(checklang2,player);
                            return o1 < o2;
                        }catch (Exception e){}
                        try {
                            double o1 = (double) go(checklang1,player);
                            double o2 = (double) go(checklang2,player);
                            return o1 < o2;
                        }catch (Exception e){}
                    }
                    if (sign.equals("<=")){
                        try {
                            int o1 = (int) go(checklang1,player);
                            int o2 = (int) go(checklang2,player);
                            return o1 <= o2;
                        }catch (Exception e){}
                        try {
                            long o1 = (long) go(checklang1,player);
                            long o2 = (long) go(checklang2,player);
                            return o1 <= o2;
                        }catch (Exception e){}
                        try {
                            float o1 = (float) go(checklang1,player);
                            float o2 = (float) go(checklang2,player);
                            return o1 <= o2;
                        }catch (Exception e){}
                        try {
                            double o1 = (double) go(checklang1,player);
                            double o2 = (double) go(checklang2,player);
                            return o1 <= o2;
                        }catch (Exception e){}
                    }
                }catch (Exception e){
                    Lang.error("&4CHECK检查异常",word);
                }
                return null;
            } else {
                return map.get(words).vaule(player,args1);
            }
        }
        return "[Null]";
    }
    private static boolean sum(Player player,String term){
        try {
            if (term.contains("|")){
                String[] a = term.split("\\|");
                for (String b : a){
                    boolean result = (boolean) go(b,player);
                    if (result){
                        return true;
                    }
                }
                return false;
            }
            if (term.contains("&")){
                String[] a = term.split("&");
                for (String b : a){
                    boolean result = (boolean) go(b,player);
                    if (!result){
                        return false;
                    }
                }
                return true;
            }
            boolean result = (boolean) go(term,player);
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
}
abstract class PActionObject{
    abstract Object vaule(Player player,String... args);
}