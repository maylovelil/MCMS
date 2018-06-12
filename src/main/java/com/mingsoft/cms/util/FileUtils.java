
package com.mingsoft.cms.util;

import java.io.*;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;

public class FileUtils {

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * <P>CSVUtils.deleteDir()<P>;
     * <P>Date : 2017年3月14日 </P>
     *
     * @param dir
     * @return
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 删除文件
     * <P>CSVUtils.deleteFile()<P>;
     * <P>Date : 2017年3月14日 </P>
     *
     * @param filePath
     * @param fileName
     */
    public static void deleteFile(String filePath, String fileName) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    if (files[i].getName().equals(fileName)) {
                        files[i].delete();
                        return;
                    }
                }
            }
        }
    }

    /**
     * 私有构造函数不能实例化
     */
    private FileUtils() {

    }

    /**
     * @param fileName
     * @return
     * @description 根据传入的文件名创建文件
     */
    public static File createFile(String fileName) {
        if (null == fileName || "".equals(fileName)) {
            return null;
        }
        File file = new File(fileName);
        if (file.exists()) {
            return file;
        }
        File parentFile = file.getParentFile();
        try {
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param path
     * @return
     * @description 根据path建立文件夹
     */
    public static File createPath(String path) {
        if (null == path || "".equals(path)) {
            return null;
        }
        File file = new File(path);
        /**
         * 如果传过来的路径是一个文件，则返回文件所在的文件夹
         */
        if (file.isFile()) {
            return file.getParentFile();
        }
        if (file.exists()) {
            return file;
        }
        if (file.mkdirs()) {
            return file;
        }
        return null;
    }

    public static void copyFileToFile(File src, File target) {
        //System.out.println(src.getAbsolutePath() + "\t" + target.getAbsolutePath());
        if (!src.exists()) {
            return;
        }
//	        String dir = target.getAbsolutePath();
        //
//	        int i = dir.lastIndexOf("/");
//	        if (-1 == i) {
//	            i = dir.lastIndexOf("\\");
//	        }
        //
//	        dir = dir.substring(0, i + 1);
//	        File dirDest = new File(dir);
        //
//	        if (!dirDest.exists()) {
//	            dirDest.mkdirs();
//	        }

        if (!target.exists()) {
            target.getParentFile().mkdirs();
            try {
                target.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int b;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new FileInputStream(src));

            out = new BufferedOutputStream(new FileOutputStream(target));
            while ((b = in.read()) != -1) {
                out.write(b);
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
        }
    }

    public static String formetFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static void copyFileToPath(String srcPath, String destPath) {
        //System.out.println(srcPath);
        File src = new File(srcPath);
        File dest = createPath(destPath);
        if (!src.exists() || dest == null || !dest.exists()) {
            throw new InvalidParameterException();
        }
        if (src.isFile()) {
            copyFileToFile(src, new File(destPath + File.separatorChar + src.getName()));
            return;
        }
        for (File file : src.listFiles()) {
            if (file.isFile()) {
                copyFileToFile(file, new File(destPath + File.separatorChar + file.getName()));
            } else {
                copyFileToPath(file.getAbsolutePath(), destPath + File.separatorChar + file.getName());
            }
        }
    }

    public static void deleteFile(String srcPath) {
        File src = new File(srcPath);
        if (!src.exists()) {
            return;
        }
        if (src.isFile()) {
            src.delete();
        } else {
            for (File file : src.listFiles()) {
                deleteFile(file.getAbsolutePath());
            }
            src.delete();
        }
    }

    public static Boolean isExists(String srcPath) {
        File src = new File(srcPath);
        return src.exists();
    }

    public static void main(String[] args) {
        copyFileToPath("e:\\111", "d:\\222\\");
    }
}
