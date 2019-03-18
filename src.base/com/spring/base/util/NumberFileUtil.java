package com.spring.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberFileUtil {

	protected static Logger log = LoggerFactory.getLogger(NumberFileUtil.class);

	/**
	 * 读取配置文件里的信息
	 * @param fileName
	 * @param key
	 * @return
	 */
	public String getProperties(String fileName, String key) {
		InputStream inputStream = null;
		if (fileName.startsWith("/")) {
			inputStream = NumberFileUtil.class.getResourceAsStream(fileName);
		} else {
			inputStream = NumberFileUtil.class.getResourceAsStream("/"
					+ fileName);
		}
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return p.getProperty(key);
	}

	/**
	 * 
	 * 压缩文件
	 * 
	 * @param inputFileName
	 *            要压缩的文件或文件夹路径，例如：c:\\a.txt,c:\\a\
	 * 
	 * @param outputFileName
	 *            输出zip文件的路径，例如：c:\\a.zip
	 * 
	 */

	public void zip(String inputFileName, String outputFileName)
			throws Exception {

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				outputFileName));

		zip(out, new File(inputFileName), "");

		log.debug("压缩完成！");

		out.closeEntry();

		out.close();

	}

	/**

	 * 压缩文件

	 * @param out org.apache.tools.zip.ZipOutputStream

	 * @param file 待压缩的文件

	 * @param base 压缩的根目录

	 */

	private void zip(ZipOutputStream out, File file, String base)
			throws Exception {

		if (file.isDirectory()) {

			File[] fl = file.listFiles();

			base = base.length() == 0 ? "" : base + File.separator;

			for (int i = 0; i < fl.length; i++) {

				zip(out, fl[i], base + fl[i].getName());

			}

		} else {

			out.putNextEntry(new ZipEntry(base));

			log.debug("添加压缩文件：" + base);

			FileInputStream in = new FileInputStream(file);

			int b;

			while ((b = in.read()) != -1) {

				out.write(b);

			}

			in.close();

		}

	}

	/**

	 * 解压zip文件

	 * @param zipFileName 待解压的zip文件路径，例如：c:\\a.zip

	 * @param outputDirectory 解压目标文件夹,例如：c:\\a\
	 * @throws IOException 

	 */

	public void unzip(String targetPath, String zipFilePath) {
		try {
			File zipFile = new File(zipFilePath);
			InputStream is = new FileInputStream(zipFile);
			ZipInputStream zis = new ZipInputStream(is);
			ZipEntry entry = null;
			while ((entry = (ZipEntry) zis.getNextEntry()) != null) {
				String zipPath = entry.getName();
				try {
					if (entry.isDirectory()) {
						File zipFolder = new File(targetPath + File.separator
								+ zipPath);
						if (!zipFolder.exists()) {
							zipFolder.mkdirs();

						} else {
							File file = new File(targetPath + File.separator
									+ zipPath);
							if (!file.exists()) {
								File pathDir = file.getParentFile();
								pathDir.mkdirs();
								file.createNewFile();
							}
							FileOutputStream fos = new FileOutputStream(file);
							int bread;
							while ((bread = zis.read()) != -1) {
								fos.write(bread);
							}
							fos.close();
						}
					}
				} catch (Exception e) {
					System.out.println("解压" + zipPath + "失败");
					continue;
				}
			}
			zis.close();
			is.close();
			System.out.println("解压结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 解压方法
	 * 
	 * @param rarFilePath
	 *            rar压缩文件的路径
	 * 
	 * @param unFilePath
	 *            要解压到指定的路径
	 * 
	 * @throws IOException
	 *             IO异常
	 * 
	 */

	public static void unRarFile(String WINRAR_PATH, String rarFilePath,
			String unFilePath) throws IOException {
		File f = new File(unFilePath);
		if (!f.exists()) { // 如果发现指定解压的路径不存在，创建目录
			f.mkdirs();
		}

		String cmd = WINRAR_PATH + " x -r -p -o " + rarFilePath + " "
				+ unFilePath; // 需要执行的命令

		Runtime runtime = Runtime.getRuntime(); // 得到命令对象
		Process process = runtime.exec(cmd); // 获取执行命令过程中返回的流

		/**
		 * 
		 * 下面是打印出流的内容，查看是否有异常
		 * 
		 */
		//		InputStreamReader isr = new InputStreamReader(process.getInputStream());
		//
		//		BufferedReader br = new BufferedReader(isr);
		//
		//		String str = null;
		//
		//		while ((str = br.readLine()) != null) {
		//
		//			if (!"".equals(str.trim()) && str != null) { // 如果当前行不为空
		//				System.out.println(str);
		//			}
		//		}
		//		br.close();
	}

	private void createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);
		try {

			if (subDirectory == "" && fl.exists() != true) {

				fl.mkdir();

			} else if (subDirectory != "") {

				dir = subDirectory.replace('\\', '/').split("/");

				for (int i = 0; i < dir.length; i++) {

					File subFile = new File(directory + File.separator + dir[i]);

					if (subFile.exists() == false)

						subFile.mkdir();

					directory += File.separator + dir[i];

				}

			}

		} catch (Exception ex) {

			System.out.println(ex.getMessage());

		}

	}

	/**

	 * 拷贝文件夹中的所有文件到另外一个文件夹

	 * @param srcDirector 源文件夹

	 * @param desDirector 目标文件夹

	 */

	public void copyFileWithDirector(String srcDirector, String desDirector)
			throws IOException {

		(new File(desDirector)).mkdirs();

		File[] file = (new File(srcDirector)).listFiles();

		for (int i = 0; i < file.length; i++) {

			if (file[i].isFile()) {

				log.debug("拷贝：" + file[i].getAbsolutePath() + "-->"
						+ desDirector + "/" + file[i].getName());

				FileInputStream input = new FileInputStream(file[i]);

				FileOutputStream output = new FileOutputStream(desDirector
						+ "/" + file[i].getName());

				byte[] b = new byte[1024 * 5];

				int len;

				while ((len = input.read(b)) != -1) {

					output.write(b, 0, len);

				}

				output.flush();

				output.close();

				input.close();

			}

			if (file[i].isDirectory()) {

				log.debug("拷贝：" + file[i].getAbsolutePath() + "-->"
						+ desDirector + "/" + file[i].getName());

				copyFileWithDirector(srcDirector + "/" + file[i].getName(),
						desDirector + "/" + file[i].getName());

			}

		}

	}

	/**

	 * 删除文件夹

	 * @param folderPath folderPath 文件夹完整绝对路径

	 */

	public void delFolder(String folderPath) throws Exception {

		//删除完里面所有内容

		delAllFile(folderPath);

		String filePath = folderPath;

		filePath = filePath.toString();

		File myFilePath = new File(filePath);

		//删除空文件夹

		myFilePath.delete();

	}

	/**

	 * 删除指定文件夹下所有文件

	 * @param path 文件夹完整绝对路径

	 */

	public boolean delAllFile(String path) throws Exception {

		boolean flag = false;

		File file = new File(path);

		if (!file.exists()) {

			return flag;

		}

		if (!file.isDirectory()) {

			return flag;

		}

		String[] tempList = file.list();

		File temp = null;

		for (int i = 0; i < tempList.length; i++) {

			if (path.endsWith(File.separator)) {

				temp = new File(path + tempList[i]);

			} else {

				temp = new File(path + File.separator + tempList[i]);

			}

			if (temp.isFile()) {

				temp.delete();

			}

			if (temp.isDirectory()) {

				//先删除文件夹里面的文件

				delAllFile(path + "/" + tempList[i]);

				//再删除空文件夹

				delFolder(path + "/" + tempList[i]);

				flag = true;

			}

		}

		return flag;

	}

	/**
	 * 文本文件追加
	 * @param filename  文件名
	 * @param string 追加内容 
	 * @throws FoundException 
	 * @throws Exception
	 */
	public static void writeTxt(File file, String string) {
		BufferedWriter bw = null;
		try {
			FileOutputStream fils = new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(fils, "UTF-8");
			bw = new BufferedWriter(writer);
			bw.write(string);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * @param inFile
	 * @param outFile
	 * @throws IOException
	 * @说明 拷贝文件 
	 */
	public void copy(File inFile, File outFile) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(inFile));
			out = new BufferedOutputStream(new FileOutputStream(outFile));
			try {
				for (int c = in.read(); c != -1; c = in.read()) {
					out.write(c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取文件内容
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String readTxt(String fileName) throws Exception {
		StringBuilder str = new StringBuilder();
//		FileReader reader = new FileReader(fileName);
		FileInputStream fins = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fins, "UTF-8"));

		String s1 = null;
		while ((s1 = br.readLine()) != null) {
			str.append(s1 + "\r\n");
		}
		br.close();
		fins.close();

		return str.toString();
	}
	
	
	/**
	 * 统计文本文件有多少行
	 */
	public static Integer readTxtRows(String fileName) {
		try{
			FileReader reader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(reader);
			Integer count = 0;
			while (br.readLine() != null) {
				count++;
			}
			br.close();
			reader.close();
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	public void unZip(String fileName , String filePath) {
		try {
			ZipFile zipFile = new ZipFile(fileName);
			Enumeration emu = zipFile.entries();
			int i = 0;
			while (emu.hasMoreElements()) {
				java.util.zip.ZipEntry entry = (java.util.zip.ZipEntry) emu.nextElement();
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				if (entry.isDirectory()) {
					new File(filePath + entry.getName()).mkdirs();
					continue;
				}
				BufferedInputStream bis = new BufferedInputStream(zipFile
						.getInputStream(entry));
				File file = new File(filePath + entry.getName());
				// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
				// 而这个文件所在的目录还没有出现过，所以要建出目录来。
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos, 2048);

				int count;
				byte data[] = new byte[2048];
				while ((count = bis.read(data, 0, 2048)) != -1) {
					bos.write(data, 0, count);
				}
				bos.flush();
				bos.close();
				bis.close();
			}
			zipFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		NumberFileUtil f = new NumberFileUtil();
		try {
			
			//System.out.println(f.readTxtRows("D:/number/20120331/20120331150211756.txt"));
			File file = new File("D:/number/1.txt");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
