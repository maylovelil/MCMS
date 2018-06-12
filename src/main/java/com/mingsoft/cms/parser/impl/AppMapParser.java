package com.mingsoft.cms.parser.impl;

import com.mingsoft.parser.IParser;

/**
 * @Description:
 * @author: :MaYong
 * @Date: 2018/6/11 19:01
 */
public class AppMapParser  extends IParser {
    private static final String GLOBAL_HOST = "\\{ms:global.appMap/\\}";

    public AppMapParser(String htmlContent, String newContent) {
        super.htmlCotent = htmlContent;
        super.newCotent = newContent;
    }

    public String parse() {
        return super.replaceAll("\\{ms:global.appMap/\\}");
    }
}
