package com.gitzzp.ecode.baselib.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static android.content.ContentValues.TAG;

/**
 * 创建人：gitzzp
 * 创建日期:17/3/29 12:54
 * 类描述: 文件压缩/解压 图片本地缓存的保存与获取
 */
public class FileUtil {

    /**
     * 压缩数据
     */

    public static byte[] gzip(byte[] content) throws IOException {
        GZIPOutputStream zos = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            zos = new GZIPOutputStream(baos);
            zos.write(content);
            zos.finish();
            return baos.toByteArray();
        } finally {
            if (zos != null)
            {
                zos.close();
            }
        }
    }

    /**
     * 解压缩数据
     */

    public static byte[] ungzip(byte[] content) throws IOException {
        GZIPInputStream zis = null;
        try {
            zis = new GZIPInputStream(new ByteArrayInputStream(content));
            return IOUtil.readStream(zis);
        } finally {
            if (zis != null)
            {
                zis.close();
            }
        }
    }

    /**
     * 批量压缩文件（夹）
     *
     * @param zipFile 生成的压缩文件
     * @param files 需要压缩的文件（夹）列表
     */

    public static void zip(File zipFile, File... files) throws Exception {
        createFileIfNecessary(zipFile);
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for (File file : files)
            {
                if (file.exists())
                {
                    zip(file, zos, "");
                }
            }
        } finally {
            if (zos != null)
            {
                zos.close();
            }
        }
    }

    /**
     * 压缩单个文件（夹）
     *
     * @param file 需要压缩的文件（夹）
     * @param zos 压缩后的目标文件流
     * @param dir 压缩后的文件目录
     */

    private static void zip(File file, ZipOutputStream zos, String dir)
            throws Exception {
        dir += (dir.length() == 0 ? dir : File.separator) + file.getName();
        if (file.isDirectory())
        {
            for (File f : file.listFiles())
            {
                zip(f, zos, dir);
            }
        }
        else
        {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                zos.putNextEntry(new ZipEntry(dir));
                IOUtil.writeStream(fis, zos);
                zos.closeEntry();
            } finally {
                if (fis != null)
                {
                    fis.close();
                }
            }
        }
    }

    /**
     * ZIP解压
     * @param file 文件完整了路径
     * @param targetDir 文件解压之后的路径
     */
    public static void Unzip(File file, String targetDir) {
        int BUFFER = 4096; //这里缓冲区我们使用4KB，
        String strEntry; //保存每个zip的条目名称

        try {
            BufferedOutputStream dest = null; //缓冲输出流
            FileInputStream fis = new FileInputStream(file);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry; //每个zip条目的实例


            while ((entry = zis.getNextEntry()) != null) {


                try {
                    LogUtil.i("Unzip: ","="+ entry);
                    int count;
                    byte data[] = new byte[BUFFER];
                    strEntry = entry.getName();

                    LogUtil.d(TAG,strEntry);

                    File entryFile = new File(targetDir + strEntry);
                    File entryDir = new File(entryFile.getParent());
                    if (!entryDir.exists()) {
                        entryDir.mkdirs();
                    }


                    FileOutputStream fos = new FileOutputStream(entryFile);
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            zis.close();
        } catch (Exception cwj) {
            cwj.printStackTrace();
        }
    }

    /**
     * 根据url去图片缓存目录下寻找对应的图片
     * @param key
     * @return
     */
    public static Bitmap getLoacalBitmap(String key) {
        String name = SecurityUtil.MD5(key);
        String path = PathUtil.getInstance().getImagePath().getAbsolutePath()+name;
        try{
            FileInputStream fis = new FileInputStream(path);
            return BitmapFactory.decodeStream(fis);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 将bitmap保存到本地缓存中
     * @param key
     * @param bitmap
     */
    public static void saveLoacalBitmap(String key, Bitmap bitmap){
        try{
            File f = new File(PathUtil.getInstance().getImagePath(), SecurityUtil.MD5(key));
            if (f.exists()) {
                f.delete();
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    /**
     * 根据文件的完整路径去寻找该文件是否存在
     * @param filepath
     * @return
     */
    public static boolean isFileExists(String filepath) {
        File file = new File(filepath);
        file.isFile();
        return file.exists();
    }

    /**
     * 删除某个目录下的所有文件
     * @param root
     */
    public static void deleteAllFiles(File root){
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    /**
     * 获取文件夹大小
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(java.io.File file){

        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++)
            {
                if (fileList[i].isDirectory())
                {
                    size = size + getFolderSize(fileList[i]);

                }else{
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return size/1048576;
//        return size;
    }

    /**
     * 获取文件夹下文件个数
     * @param string
     * @return
     */
    public static int getFiles(String string) {
        return getFiles(new File(string));
    }

    public static int getFiles(File file) {
        int i = 0;
        File[] files = file.listFiles();
        for (int j = 0; j < files.length; j++) {
            String name = files[j].getName();
            if (files[j].isDirectory()) {
                String dirPath = files[j].toString().toLowerCase();
                System.out.println(dirPath);
                i += getFiles(dirPath + "/");
            } else {
                System.out.println("FileName===" + files[j].getName());
                i++;
            }
        }

        return i;
    }

    /**
     * 创建文件以便写入
     */

    public static void createFileIfNecessary(File file) {
        if (file.isDirectory())
        {
            throw new RuntimeException(String.format("%s is directory.", file));
        }

        File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs())
        {
            throw new RuntimeException(String.format("Cannot create parent dir[%s]", parent));
        }

        if (!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(String.format("Cannot create file[%s]", file), e);
            }
        }
    }
}
