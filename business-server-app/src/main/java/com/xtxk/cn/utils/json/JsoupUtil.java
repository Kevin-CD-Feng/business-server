package com.xtxk.cn.utils.json;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * @author lost
 * @Description JsoupUtil
 * @Date create in 2022-10-9 9:10:34
 */
public class JsoupUtil {
    /**
     * 白名单
     */
    private static final Whitelist WHITELIST = Whitelist.basicWithImages();
    /**
     * 配置是否过滤后字符美化
     */
    private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);

    /**
     * 过滤
     *
     * @param content
     * @return
     */
    public static String clean(String content) {
        return Jsoup.clean(content, "", WHITELIST, OUTPUT_SETTINGS);
    }
}