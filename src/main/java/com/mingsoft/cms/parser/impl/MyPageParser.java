package com.mingsoft.cms.parser.impl;

import com.mingsoft.cms.util.MycLangUtils;
import com.mingsoft.parser.IParser;
import com.mingsoft.util.PageUtil;

import java.io.File;

/**
 * @Description:
 * @author: :MaYong
 * @Date: 2018/6/4 17:06
 */
public class MyPageParser  extends IParser {

    private final String PAGE_INDEX = "\\{ms:page.index/\\}";
    private final String PAGE_PRE = "\\{ms:page.pre/\\}";
    private final String PAGE_NEXT = "\\{ms:page.next/\\}";
    private final String PAGE_OVER = "\\{ms:page.last/\\}";
    private PageUtil page;

    public MyPageParser(String htmlContent, PageUtil page) {
        super.htmlCotent = htmlContent;
        this.page = page;
    }

    public String parse() {
        if(this.page != null) {
            super.newCotent = MycLangUtils.isZh()?this.page.getIndexUrl():replaceEn(this.page.getIndexUrl());
            String indexHtml = super.replaceAll("\\{ms:page.index/\\}");
            super.htmlCotent = indexHtml;
            super.newCotent = MycLangUtils.isZh()?this.page.getPreviousUrl():replaceEn(this.page.getPreviousUrl());
            String preHtml = super.replaceAll("\\{ms:page.pre/\\}");
            super.htmlCotent = preHtml;
            super.newCotent = MycLangUtils.isZh()?this.page.getNextUrl():replaceEn(this.page.getNextUrl());
            String nextHtml = super.replaceAll("\\{ms:page.next/\\}");
            super.htmlCotent = nextHtml;
            super.newCotent = MycLangUtils.isZh()?this.page.getLastUrl():replaceEn(this.page.getLastUrl());
            String traileHtml = super.replaceAll("\\{ms:page.last/\\}");
            return traileHtml;
        } else {
            return this.htmlCotent;
        }
    }

    private String replaceEn(String url){
        if(!url.contains(File.separator+"1"+File.separator+"en"+File.separator)){
            if(url.contains(File.separator+"1"+File.separator)){
                if(url.contains(File.separator+"1"+File.separator+File.separator)){
                    url = url.replace(File.separator+"1"+File.separator+File.separator,"/1/en/");
                }else{
                    url = url.replace(File.separator+"1"+File.separator,"/1/en/");
                }
            }
        }
        return  url;
    }
}
