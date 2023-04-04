package com.smartcontact.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.smartcontact.dao_repository.UserRepository;
import com.smartcontact.entity.User;
import com.smartcontact.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	

	@RequestMapping("/")
	public String home(Model model){
		model.addAttribute("title","Home-smart Contact Manager");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model){
		model.addAttribute("title","About-smart Contact Manager");
		return "about";
	}
	@RequestMapping("/signup")
	public String signup(Model model){
		model.addAttribute("title","Register-smart Contact Manager");
		model.addAttribute("user",new User());
		return "signup";
	}
	
	//handler for registering user
	@RequestMapping(value = "/do_register",method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1,@RequestParam(value = "agreement",defaultValue = "false")
	boolean agreement, Model model, HttpSession sesssion) {
		try {
			if(!agreement) {
				System.out.println("you have not agreed terms and condition");
				throw new Exception("you have not agreed terms and condition");

			}
			
			if(result1.hasErrors()) {
				System.out.println("ERROR"+result1.toString());
				model.addAttribute("user",user);
				return "signup";
			}
			
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImgUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			System.out.println("Agreement "+agreement);
			System.out.println("User"+user);
			
			User result=this.userRepository.save(user);
			model.addAttribute("user",new User());
			
			sesssion.setAttribute("message", new Message("successfully register","alert-success"));
			return "signup";
		
			
		} catch (Exception e) {
			
			e.printStackTrace();
			model.addAttribute("user",user);
			sesssion.setAttribute("message",new Message("Something went wrong!!", "alert-danger"));
			return "signup";
		}
		
		
	}

	//handler for custom login
	@GetMapping("/signin")
		public String customLogin(Model model) {
			model.addAttribute("title","Login page");
			return "login";
		}
}
