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
 * @todo TODO ɨ����
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
		//�Ѱ�������·��
		String filePath =packagePath.replace(".", "/");
		//System.out.println(filePath);
		Map<String,Class<?>> classes =new HashMap<String,Class<?>>();
		//ȡ��·����ö������
		
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(filePath);
			//��ȡ��·��
			String root = Thread.currentThread().getContextClassLoader().getResource(filePath).getPath();
			if(null!=root){
				root =root.substring(root.lastIndexOf(filePath));
			}
			System.out.println(root);
			//����url
			while(dirs.hasMoreElements()){
				URL url=dirs.nextElement();//�õ���ǰ������Ԫ��
				//file:/D:/workspace/spring/WebRoot/WEB-INF/classes/com/yk/controller
				//System.out.println(url.getPath());
				if(url.getProtocol().equals("file")){
					//ͨ��url �������ʵ�ʵ��ļ�����
					File folder =new File(url.getPath().substring(1));
					//��ʼɨ���ļ��У����ļ��У���������
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
	 * @param root ɨ�����е���
	 * @param classes
	 */
	public static void scannerFile(File folder,String root,Map<String,Class<?>> classes){
		//���ҵ������ļ�����
		File[] files = folder.listFiles();
		for(File file : files){
			if(file.isDirectory()){
				scannerFile(file, root+file.getName()+"/", classes);
			}else{//������ļ��п�����class�ļ�
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
