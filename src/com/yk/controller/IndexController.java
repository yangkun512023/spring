package com.yk.controller;

import com.yk.annotation.Controller;
import com.yk.annotation.RequestMapping;




/**
 * 
 * @title IndexController
 * @todo TODO ������
 * @param
 * @return 
 * @date Aug 11, 2017
 * @author yangk
 */
@Controller
@RequestMapping("/index")
public class IndexController {
		@RequestMapping("/doSomeThing")
		public void doSomeThing(){
			System.out.println("�����һ˧");
		}
		@RequestMapping("/cc")
		public void doSomeThingg(){
			System.out.println("�����er˧");
		}
		@RequestMapping("/dd")
		public void doSomeThingggg(){
			System.out.println("�����san˧");
		}
}	
