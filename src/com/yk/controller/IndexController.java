package com.yk.controller;

import com.yk.annotation.Controller;
import com.yk.annotation.RequestMapping;




/**
 * 
 * @title IndexController
 * @todo TODO ¿ØÖÆÆ÷
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
			System.out.println("ÓîÖæµÚÒ»Ë§");
		}
		@RequestMapping("/cc")
		public void doSomeThingg(){
			System.out.println("ÓîÖæµÚerË§");
		}
		@RequestMapping("/dd")
		public void doSomeThingggg(){
			System.out.println("ÓîÖæµÚsanË§");
		}
}	
