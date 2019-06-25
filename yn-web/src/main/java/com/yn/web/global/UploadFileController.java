package com.yn.web.global;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.yn.common.entity.BaseController;
import com.yn.common.entity.GlobalResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.yn.common.utils.ImageUtils;

import javax.servlet.ServletOutputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/upload")
@Api(value = "上传文件", tags = "上传文件", description = "上传文件接口")
public class UploadFileController extends BaseController {

    private static final Log log = LogFactory.getLog(UploadFileController.class);


    @Value("${upload.file.url}")
    private String uploadFileUrl;

    public static final List<String> ALLOW_IMAGE_TYPES = Arrays.asList(
            "image/jpg", "image/jpeg", "image/png", "image/gif"
    );
    public static final String SHOW_IMAGE_MAPPING = "/upload/showImg/";

	/*@RequestMapping(value = "uploadImage", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "上传文件", notes = "上传文件")
	public Object uploadImage(@RequestParam("imageFile") MultipartFile[] imageFile, GlobalResult result){
        System.out.println(imageFile.length);
		if(imageFile==null || imageFile.length==0){
			result.setMsg("请选择需要上传的文件");
			return result;
		}
		if(Arrays.stream(imageFile).com.yn.common.filter(file-> ALLOW_IMAGE_TYPES.contains(file.getContentType())).count()==0) {
			result.setMsg("只能上传图片文件类型");
			return result;
		}
		String[] strings=new String[imageFile.length];
		try {
			String fileDir = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + File.separator;
			for (MultipartFile file : imageFile) {
				String saveTempPath = uploadFileUrl + fileDir;// 记录文件的临时路径
				File dir = new File(saveTempPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				//生成一个新的文件名
				String createFileName = IdWorker.getId() + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
				file.transferTo(new File(dir, createFileName));
				Arrays.fill(strings, SHOW_IMAGE_MAPPING + fileDir +createFileName);
				result.setSuccess(true);
				result.setMsg("图片上传成功");
			}
		}catch (Exception e){
			log.error(e);
			result.setMsg("文件上传失败，服务器异常");
		}
		result.setData(strings);
		return result;
	}*/

    @RequestMapping(value = "base64upload", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "base64上传文件", notes = "base64上传文件")
    public Object base64uploadImage(@RequestParam("base64") String base64, GlobalResult result) {
        result.setData(ImageUtils.saveImg(super.getRequest(), base64));
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "showImg/{fileDir}/{fileName:.+}", method = RequestMethod.GET)
    public void showImg(@PathVariable String fileDir, @PathVariable String fileName) {
        if (StringUtils.isNotEmpty(fileName)) {
            BufferedInputStream is = null;
            ServletOutputStream out = null;
            try {
                //告诉浏览器以什么方式打开,这样设置，会自动判断下载文件类型
                getResponse().setContentType("multipart/form-data");
                //设置文件头：最后一个参数是设置下载文件名
                getResponse().setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                //获取响应对象流，并写入输出流中
                File f = new File(uploadFileUrl + fileDir + File.separator + fileName);//通过文件路径获得File对象
                FileInputStream fis = new FileInputStream(f);
                is = new BufferedInputStream(fis);
                out = getResponse().getOutputStream();
                int len;
                byte buf[] = new byte[1024 * 1024];//缓存
                while ((len = is.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (Exception e) {
                log.error(e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @PostMapping("/uploadImage")
    @ResponseBody
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public Object uploadImage(@RequestParam("file") MultipartFile[] imageFile, GlobalResult result) {
        if (imageFile == null || imageFile.length == 0) {
            result.setMsg("请选择需要上传的文件");
            return result;
        }
        if (Arrays.stream(imageFile).filter(file -> ALLOW_IMAGE_TYPES.contains(file.getContentType())).count() == 0) {
            result.setMsg("只能上传图片文件类型");
            return result;
        }
        String[] strings = new String[imageFile.length];
        String fileDir = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + File.separator;
        try {
            for (MultipartFile file : imageFile) {
                String saveTempPath = uploadFileUrl + fileDir;// 记录文件的临时路径
                File dir = new File(saveTempPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                //生成一个新的文件名
                String createFileName = IdWorker.getId() + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
                file.transferTo(new File(dir, createFileName));
                Arrays.fill(strings, SHOW_IMAGE_MAPPING + fileDir + createFileName);
                result.setSuccess(true);
                result.setMsg("图片上传成功");
            }
        } catch (Exception e) {
            log.error(e);
            result.setMsg("文件上传失败，服务器异常");
        }
        result.setData(strings);
        return result;
    }

}
