package com.spring.common.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.base.controller.BaseController;
import com.spring.base.utils.PropertiesUtil;
import com.spring.base.utils.StringUtil;

/**
 * 公共下载 上传功能
 * 
 * @author huajian
 * 
 */
@Controller
@RequestMapping(value = "/publicUploadTwo")
public class PublicUploadTwoController extends BaseController {
	/**
	 * 
	 * @Title: fileUpload
	 * @Description: 图片上传
	 * @param @param myfiles
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws IOException 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/fileUpload")
	public String fileUpload(@RequestParam MultipartFile[] myfiles,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
        System.out.println("进入上传图片2方法------------");
		String folder = request.getParameter("folder");

		String realPath = request.getRealPath("/upload/" + folder + "");
//		String realPath = PropertiesUtil.getProperty("upload_path", "config") 
//				+ request.getContextPath() + "/upload/" + folder;
		String url = imgPath+"" + folder + "/";
		
		// 如果文件夹不存在就新建一个文件夹
		File dirPath = new File(realPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		// 设置响应给前台内容的数据格式
		response.setContentType("text/plain; charset=UTF-8");
		// 设置响应给前台内容的PrintWriter对象
		PrintWriter out = response.getWriter();
		// 上传文件的原名(即上传前的文件名字)
		String originalFilename = null;

		// 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
		// 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
		// 上传多个文件时,前台表单中的所有<input
		// type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
		for (MultipartFile myfile : myfiles) {
			if (myfile.isEmpty()) {
				out.print("1`请选择文件后上传!");
				out.flush();
				return null;
			} else if (myfile.getSize() > 2 * 1024 * 1024) {
				out.print("1`您上传的文件太大,系统允许最大文件2M");
				out.flush();
				return null;
			} else {
				// 获取文件名
				originalFilename = myfile.getOriginalFilename();
				// 文件名后缀处理
				String suffix = originalFilename.substring(
						originalFilename.lastIndexOf("."),
						originalFilename.length());

				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Timestamp(System
						.currentTimeMillis()))
						+ "_"
						+ (int) (Math.random() * 900000 + 100000) + suffix;
				url += newFileName;

				File file = new File(realPath, newFileName);
				try {
					// 判断文件名是否是图片
					if (".jpg".equals(suffix) || ".gif".equals(suffix)
							|| ".png".equals(suffix)) {
						// 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
						// 此处也可以使用Spring提供的MultipartFile.transferTo(File
						// dest)方法实现文件的上传
						FileUtils.copyInputStreamToFile(
								myfile.getInputStream(), file);// 上传文件到磁盘

						// 文件上传成功返回文件路径跟文件名
						out.print("0`" + url);
						out.flush();
						return null;
					} else {
						out.print("1`文件上传格式错误!!!");
						out.flush();
						return null;
					}

				} catch (IOException e) {
					System.out.println("文件[" + newFileName + "]上传失败,堆栈轨迹如下");
					e.printStackTrace();
					out.print("1`文件上传失败，请重试！！");

				}
			}
		}
		out.flush();
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addImage")
	public String addImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("进入了文件上传");
		// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
		// 这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("uploadFile");
		// 获取上传路径
		String realPath = request.getRealPath("/upload/image/");
		//String realPath = saveImageUrl + "/upload/image/";
		String url = imgPath+"/upload/image/";

		// 如果文件夹不存在就新建一个文件夹
		File dirPath = new File(realPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		// 设置响应给前台内容的数据格式
		response.setContentType("text/plain; charset=UTF-8");
		// 设置响应给前台内容的PrintWriter对象
		// 上传文件的原名(即上传前的文件名字)
		String originalFilename = null;

		// 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
		// 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
		// 上传多个文件时,前台表单中的所有<input
		// type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
		if (multipartFile.isEmpty()) {
			return "{\"error\":\"1\",\"message\":\"请选择文件后上传!\"}";
		} else if (multipartFile.getSize() > 1 * 1024 * 1024) {
			return "{'error':'1','message':'您上传的文件太大,系统允许最大文件1M!'}";
		} else {
			// 获取文件名
			originalFilename = multipartFile.getOriginalFilename();

			// 文件名后缀处理---start---
			String suffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Timestamp(System.currentTimeMillis())) + "_" + new Random().nextInt(5) + suffix;

			url += newFileName;
			String filePath = "/upload/image/"+newFileName;
			File file = new File(realPath, newFileName);

			try {
				// 判断文件名是否是图片
				if (".jpg".equals(suffix) || ".gif".equals(suffix) || ".png".equals(suffix)) {
					// 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
					// 此处也可以使用Spring提供的MultipartFile.transferTo(File
					// dest)方法实现文件的上传
					FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);// 上传文件

					// ImageUtil.compressImage(realPath + "\\" + newFileName,
					// realPath +"\\" + strSmallFileName, 120, 120);
					// Util.deleteFile(realPath + "\\" + newFileName);

					// 文件上传成功返回文件路径跟文件名
					return "{\"error\":\"0\",\"url\":\""+url+"\",\"filePath\":\""+filePath+"\"}";
				} else {
					return "{'error':'1','message':'文件上传格式错误!!!'}";
				}

			} catch (IOException e) {
				System.out.println("文件[" + newFileName + "]上传失败,堆栈轨迹如下");
				e.printStackTrace();

			}
		}
		return "{\"error\":\"1\",\"message\":\"文件上传失败，请重试！！\"}";
	}
}
