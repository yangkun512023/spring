package com.yk.spring;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @title ScannerUtil
 * @todo TODO 扫描类
 * @param
 * @return 
 * @date Aug 11, 2017
 * @author yangk
 */
public class ScannerUtil {
	public static void main(String[] args) {
		scannerClass("com.yk.controller");
	}
	public static Map<String,Class<?>> scannerClass(String packagePath){
		//把包名换成路径
		String filePath =packagePath.replace(".", "/");
		//System.out.println(filePath);
		Map<String,Class<?>> classes =new HashMap<String,Class<?>>();
		//取得路径的枚举类型
		
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(filePath);
			//获取根路径
			String root = Thread.currentThread().getContextClassLoader().getResource(filePath).getPath();
			if(null!=root){
				root =root.substring(root.lastIndexOf(filePath));
			}
			System.out.println(root);
			//迭代url
			while(dirs.hasMoreElements()){
				URL url=dirs.nextElement();//拿到当前迭代的元素
				//file:/D:/workspace/spring/WebRoot/WEB-INF/classes/com/yk/controller
				//System.out.println(url.getPath());
				if(url.getProtocol().equals("file")){
					//通过url 对象产生实际的文件对象
					File folder =new File(url.getPath().substring(1));
					//开始扫描文件夹（子文件夹）的所有类
					scannerFile(folder,root,classes);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(dirs);
		
		return classes;
	}
	/**
	 * 
	 * @param file
	 * @param root 扫描所有的类
	 * @param classes
	 */
	public static void scannerFile(File folder,String root,Map<String,Class<?>> classes){
		//先找到所有文件对象
		File[] files = folder.listFiles();
		for(File file : files){
			if(file.isDirectory()){
				scannerFile(file, root+file.getName()+"/", classes);
			}else{//如果是文件有可能是class文件
				String path=file.getAbsolutePath();
				if(path.endsWith(".class")){
					path =path.replace("\\", "/");
					String className=root+path.substring(path.lastIndexOf("/")+1, path.lastIndexOf(".class"));
					className =className.replace("/", ".");
					//com.yk.controller.IndexController
							//com.yk.controller.sub.SubController
							//com.yk.controller.UserController
					//System.out.println(className);
					try {
						classes.put(className, Class.forName(className));
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
