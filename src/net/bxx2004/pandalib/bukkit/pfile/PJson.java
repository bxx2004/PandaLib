package net.bxx2004.pandalib.bukkit.pfile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;

/**
 * 目前无法使用
 */
public class PJson implements CustomFile{
    private File file;
    private Gson gson;
    private JsonObject jsonObject;
    private JsonArray jsonArray;
    @Override
    public FileType getType() {
        return FileType.JSON;
    }
    /**
     * 无法使用!!!!!!!!!!
     * 快速构建一个json文件类型(使用谷歌的 Gson 进行包装)
     * @param url 文件地址
     */
    public PJson(String url){
        this.file = new File(url);
        this.gson = new Gson();
        try {
            this.jsonObject = gson.fromJson(new FileReader(file), JsonObject.class);
            this.jsonArray = gson.fromJson(new FileReader(file), JsonArray.class);
        }catch (Exception e){}
    }
    /**
     * 获取一个Json对象
     * @param key 键
     * @return 一个Json对象
     */
    public JsonObject getJsonObject(String key){
        return jsonObject.get(key).getAsJsonObject();
    }
    /**
     * 获取一个Json数组
     * @param key 键
     * @return 一个Json数组
     */
    public JsonArray getJsonArray(String key){
        return jsonObject.get(key).getAsJsonArray();
    }
}
