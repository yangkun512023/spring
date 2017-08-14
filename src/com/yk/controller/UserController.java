package com.yk.controller;

import com.yk.annotation.Controller;
import com.yk.annotation.RequestMapping;

@Controller
public class UserController {
	@RequestMapping("index")
	public void mm(){
		System.out.println("jjjjjj");
	}
}
