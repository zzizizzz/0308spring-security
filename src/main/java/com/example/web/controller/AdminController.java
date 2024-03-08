package com.example.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
// ADMIN 권한이 있어야지만 요청할수 있다.
public class AdminController {

	@GetMapping("/home")
	public String home() {
		return "admin/home";
	}

}
