package com.boney.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

	@PreAuthorize("hasAnyRole('STUDENT')")
	@RequestMapping("/dashboard")
	public String showStudent(Model model) {
		System.out.println("Calle comes in DashBoard");
		return "dashboard";
	}
	@PreAuthorize("hasAnyRole('SCHOOL_ADMIN')")
	@GetMapping("/admindashboard")
	public String showSchoolAdmin(Model model) {
		System.out.println("Calle comes in AdminDashBoard");
		return "admindashboard";
	}
	@PreAuthorize("hasAnyRole('SUPER_ADMIN')")
	@GetMapping("/collegeSetup")
	public String showSuperAdmin(Model model) {
		System.out.println("Calle comes in SuperAdminDashBoard");
		return "collegeSetup";
	}

}
