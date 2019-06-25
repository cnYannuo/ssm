package com.yn.common.utils;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.zip.*;

/**
 * Created by 15c on 2018/8/7.
 */
@Component
public class ImageUtils {

    private static String uploadFileUrl;

    private static String SHOW_IMAGE_MAPPING = "/upload/showImg/";

    public static String getUploadFileUrl() {
        return uploadFileUrl;
    }

    @Value("${upload.file.url}")
    public void setUploadFileUrl(String uploadFileUrl) {
        ImageUtils.uploadFileUrl = uploadFileUrl;
    }

    /**
     * TODO(将一张网络图片转化成Base64字符串)
     *
     * @param imgURL 网络资源位置
     * @return Base64字符串
     */
    public static String GetImageStrFromUrl(String imgURL) {
        byte[] data = null;
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();
//            data = new byte[inStream.available()];
            data = ImageUtils.readInputStream(inStream);
//            inStream.read(data);
//            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    /**
     * 图片转成base64
     *
     * @param imgURL
     * @return
     */
    public static String imgToBase64(String imgURL) {
        /* String url="/upload/showImg/2018-10-24\\e59584af-7972-4c43-bfbf-14816d55b480.jpg";*/
        if (imgURL.contains("/")) {
            imgURL = imgURL.substring(imgURL.lastIndexOf("/"));
        }
        /*System.out.println();*/
        String imgFile = uploadFileUrl + imgURL;//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    //测试
    public static void main(String[] args) {

//        System.out.println(ImageUtils.GetImageStrFromUrl("http://192.168.0.51:80/faceDatabase/20180807/3974bd6af7354778a6ede442f0da74ba.jpg"));
        System.out.println(ImageUtils.zip(ImageUtils.GetImageStrFromUrl("http://192.168.0.51:80/faceDatabase/20180810/7445f14f847842efa3bd7b8ea47a7f2e.jpg")).length());
        Random r = new Random();
        int n1 = r.nextInt(10);
        System.out.println("d1:" + n1);
    }

    //读取图片流
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }


    /**
     * 使用gzip进行压缩
     */
    public static String gzip(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return new BASE64Encoder().encode(out.toByteArray());
    }

    /**
     * <p>Description:使用gzip进行解压缩</p>
     *
     * @param compressedStr
     * @return
     */
    public static String gunzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try {
            compressed = new BASE64Decoder().decodeBuffer(compressedStr);
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);

            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }

        return decompressed;
    }

    /**
     * 使用zip进行压缩
     *
     * @param str 压缩前的文本
     * @return 返回压缩后的文本
     */
    public static final String zip(String str) {
        if (str == null)
            return null;
        byte[] compressed;
        ByteArrayOutputStream out = null;
        ZipOutputStream zout = null;
        String compressedStr = null;
        try {
            out = new ByteArrayOutputStream();
            zout = new ZipOutputStream(out);
            zout.putNextEntry(new ZipEntry("0"));
            zout.write(str.getBytes());
            zout.closeEntry();
            compressed = out.toByteArray();
            compressedStr = new BASE64Encoder().encodeBuffer(compressed);
        } catch (IOException e) {
            compressed = null;
        } finally {
            if (zout != null) {
                try {
                    zout.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        return compressedStr;
    }

    /**
     * 使用zip进行解压缩
     *
     * @param compressedStr 压缩后的文本
     * @return 解压后的字符串
     */
    public static final String unzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        ZipInputStream zin = null;
        String decompressed = null;
        try {
            byte[] compressed = new BASE64Decoder().decodeBuffer(compressedStr);
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(compressed);
            zin = new ZipInputStream(in);
            zin.getNextEntry();
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = zin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            decompressed = null;
        } finally {
            if (zin != null) {
                try {
                    zin.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        return decompressed;
    }

    /**
     * 保存图片在本地
     *
     * @param base64 图片64位编码
     * @return
     */
    public static String saveImg(HttpServletRequest request, String base64) {
        if (null != base64 && !"".equals(base64)) {
//            //获取服务器的路径
            //   String path = request.getSession().getServletContext().getRealPath(File.separator +"resources");
            String dateStr = DateUtil.format(new Date(), "yyyy-MM-dd");
            String saveTempPath = uploadFileUrl + dateStr;
            File dir = new File(saveTempPath);
            String realPath = ImageUtils.judeDirExists(request, dir);
            //声明图片地址
            String imgAddress = null;
            //获取扩展名
            String extensionName = ".jpg";
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                //设置图片名为人员编号+随机数
                imgAddress = IdWorker.get32UUID() + extensionName;
                //将文件上传到服务器
                byte[] decodedBytes = decoder.decodeBuffer(base64);

                FileOutputStream out = new FileOutputStream(realPath + File.separator + imgAddress);

                out.write(decodedBytes);

                out.close();
                return SHOW_IMAGE_MAPPING + dateStr + File.separator + imgAddress;
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }
    }


    public static boolean deleteImg(HttpServletRequest request, String fileName) {
        if (null != fileName && !"".equals(fileName)) {
            File file = new File(request.getSession().getServletContext().getRealPath(fileName));
            if (file.isFile() && file.exists()) {
                Boolean succeedDelete = file.delete();
                if (succeedDelete) {
                    System.out.println("删除单个文件" + fileName + "成功！");
                    return true;
                } else {
                    System.out.println("删除单个文件" + fileName + "失败！");
                    return false;
                }
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        }
        return false;
    }

    // 判断文件夹是否存在,不存在则新建
    public static String judeDirExists(HttpServletRequest request, File file) {
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
            File file1 = new File(file.getPath());
            if (!file1.exists() && !file1.isDirectory()) {
                file1.mkdir();
            }
            return file1.getPath();
        } else {
            // return request.getSession().getServletContext().getRealPath(File.separator +"resources"+File.separator +"personImg");
            return file.getPath();
        }
    }
}
