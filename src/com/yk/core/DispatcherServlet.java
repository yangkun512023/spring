package com.yk.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yk.annotation.Controller;
import com.yk.annotation.RequestMapping;
import com.yk.spring.ScannerUtil;


@SuppressWarnings("serial")
@WebServlet(urlPatterns={"*.do"},loadOnStartup=0)
public class DispatcherServlet extends HttpServlet{
	private final static String BASE_PACKAGE ="com.yk.controller";
	//����2�����ϣ��ֱ�洢 controller ʵ�� �Լ�ÿ��ӳ��·����Ӧ�� method
	@SuppressWarnings("unused")
	private Map<String,Object> controllers =new HashMap<String,Object>();
	@SuppressWarnings("unused")
	private Map<String,Method> methods =new HashMap<String,Method>();
	public void init(){
		System.out.println("-------------��ʼ--------------------");
		Map<String, Class<?>> classes = ScannerUtil.scannerClass(BASE_PACKAGE);
		System.out.println("ɨ�����Ϊ"+classes.size());
		System.out.println("-------------����--------------------");
		Iterator<String> ito =classes.keySet().iterator();
		while(ito.hasNext()){
			String className =ito.next();
			Class c=classes.get(className);
			//�жϵ�ǰ�����ǹ���controllerע��
			if(c.isAnnotationPresent(Controller.class)){
				String path="";//����·��
				if(c.isAnnotationPresent(RequestMapping.class)){
					path=((RequestMapping)c.getAnnotation(RequestMapping.class)).value();
				}
				try {
					controllers.put(className, c.newInstance());
					Method[] ms=c.getMethods();//ֻ��public
					for(Method method : ms){
						if(method.isAnnotationPresent(RequestMapping.class)){
							methods.put(path+((RequestMapping)method.getAnnotation(RequestMapping.class)).value(), method);
						}
					}
 				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	public void service(HttpServletRequest request,HttpServletResponse response){
		String contextpath =request.getContextPath();
		String uri=request.getRequestURI();
		if(uri.equals(contextpath+"/")){
			uri =uri +"index.do";
		}
		String mappingPath =uri.substring(uri.indexOf(contextpath)+contextpath.length(),uri.indexOf(".do"));
		Method method=methods.get(mappingPath);
		try {
			method.invoke(controllers.get(method.getDeclaringClass().getName()));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
}
