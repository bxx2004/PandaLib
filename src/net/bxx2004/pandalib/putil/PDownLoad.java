package net.bxx2004.pandalib.putil;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.swing.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * 快速下载文件的工具
 */
public class PDownLoad {
    private Plugin plugin;
    private String url;
    private String url2;
    private long size;
    private int speed;

    /**
     * 构建一个下载工具类
     * @param url 下载地址
     * @param url2 本地保存地址
     * @param plugin 插件
     */
    public PDownLoad(String url, String url2, Plugin plugin){
        this.url = url;
        this.url2 = url2;
        this.plugin = plugin;
    }

    /**
     * 开始下载
     */
    public void start(){
        download();
    }

    /**
     * 返回下载地址
     * @return 下载地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 返回保存地址
     * @return 保存地址
     */
    public String getUrl2() {
        return url2;
    }

    /**
     * 返回下载文件大小
     * @return 文件大小
     */
    public long getSize() {
        return size;
    }

    /**
     * 返回下载进度
     * @return 下载进度
     */
    public int getSpeed() {
        return speed;
    }
    private void download(){
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    File file = new File(url2);
                    URL loadurl = new URL(url);
                    HttpURLConnection httpConnection = (HttpURLConnection) (loadurl.openConnection());
                    long completeFileSize = httpConnection.getContentLength();
                    java.io.BufferedInputStream in = new java.io.BufferedInputStream(httpConnection.getInputStream());
                    java.io.FileOutputStream fos = new java.io.FileOutputStream(file);
                    java.io.BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
                    byte[] data = new byte[1024];
                    long downloadedFileSize = 0;
                    int x = 0;
                    while ((x = in.read(data, 0, 1024)) >= 0) {
                        downloadedFileSize += x;
                        final int currentProgress = (int) ((((double)downloadedFileSize) / ((double)completeFileSize)) * 100d);
                        long finalDownloadedFileSize = downloadedFileSize;
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                size = finalDownloadedFileSize;
                                speed = currentProgress;
                                Bukkit.getConsoleSender().sendMessage("§b[§f "+ plugin.getName() +" §b] §f- §7下载进度: §f"+ currentProgress +"%§7...");
                                if (currentProgress == 100){
                                    Bukkit.getConsoleSender().sendMessage("§b[§f "+ plugin.getName() +" §b] §f- §7下载完成...");
                                }
                            }
                        });

                        bout.write(data, 0, x);
                    }
                    bout.close();
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }
}
