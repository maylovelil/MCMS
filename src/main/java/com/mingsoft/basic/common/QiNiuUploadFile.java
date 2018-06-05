package com.mingsoft.basic.common;

import com.google.gson.Gson;
import com.mingsoft.base.constant.Const;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.io.File;

/**
 * @Description:
 * @author: :MaYong
 * @Date: 2018/5/31 13:32
 */
@Component
public class QiNiuUploadFile {
    private static String qiNiuAk = "Ciwl0XpqMR2K29Z6i2uSxXo9TOaRxXDZWyU4BaN0";
    private static String qiNiuSk = "CV9wybrFwaONlgfdw9fsdL6tzETcl1Pt20M1D5eY";
    private static String qiNiuBucket = "my-mingxun";
    private static String qiNiuHost = "p9f59h2gx.bkt.clouddn.com";
    private static String serverPath= "myc";

    public static   String key(String img) {
        //...生成上传凭证，然后准备上传
        String imgUrl = "";

        //构造一个带指定Zone对象的配置类


         /*华东 Zone.zone0()
         华北 Zone.zone1()
         华南 Zone.zone2()
         北美 Zone.zoneNa0()*/

        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//        String zxc = "E:\\手机备份\\华为V8\\DCIM\\Camera\\1_2_1.png";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = img;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(qiNiuAk, qiNiuSk);
        String upToken = auth.uploadToken(qiNiuBucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            System.out.println(response.bodyString());
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            imgUrl = putRet.key;
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            //System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return "http://"+qiNiuHost+ Const.SEPARATOR+imgUrl;
    }

    public  static String replaceAllImages(String context,String rootPath){
        if(StringUtils.isNullOrEmpty(context)){
            return "";
        }
        Document doc = Jsoup.parse(context);//拿到编辑器里面的所有内容
        Elements elements = doc.select("img[src]");//获取到的值为所有的<img src="...">
        String fileRoute ="";
        for (Element element : elements) {
            String src = element.attr("src");//获取到src的值,开始进行上传七牛云的操作
            if(src.contains(qiNiuHost)){
                continue;
            }
            System.out.println("rootPath路径："+rootPath);
            System.out.println("src路径："+src);
            System.out.println("图片的路径1："+rootPath.replace("\\"+serverPath+"\\","")+src);
            System.out.println("图片的路径2："+rootPath.replace(File.separator+serverPath+File.separator,"")+src);

            //找到百度编辑器文件储存的路径

            System.out.println(fileRoute);
            String imgUrl = key(rootPath.replace("\\"+serverPath+"\\","")+src);
            //接下来进行字符串的替换工作
            element.attr("src",imgUrl);
            fileRoute =rootPath.replace("\\"+serverPath+"\\","")+src;
            //System.out.println("替换后的src地址:"+element.attr("src"));

        }
        //System.out.println("替换后的编辑器的内容"+doc.body());
        //System.out.println("替换后的编辑器的内容"+map.get("goods_des"));
        //通过io流进行文件的删除操作

        //+System.out.println("??????"+doc.body().toString().substring(6, doc.body().toString().length()-7));
        context = doc.body().toString().substring(7, doc.body().toString().length()-7);
        File file11 = new File(fileRoute);
        if (file11.isDirectory()){//判断file是否是文件目录 若是返回TRUE
            String name[]=file11.list();//name存储file文件夹中的文件名
            for (int i=0; i<name.length; i++){
                File f=new File(fileRoute, name[i]);//此时就可得到文件夹中的文件

                f.delete();//删除文件
            }
        }//删除完毕
        return context;
    }
}
