package net.bxx2004.pandalibloader;

import java.net.URL;

public class SafetyPluginMessage<T> {
    private URL url;
    private T pandaLibPlugin;
    public SafetyPluginMessage(URL url, T plugin){
        this.url = url;
        this.pandaLibPlugin = plugin;
    }

    public URL getUrl() {
        return url;
    }

    public T getPandaLibPlugin() {
        return pandaLibPlugin;
    }
}
