package com.xisra.arch.sampleclient.controller;

import com.xisra.arch.sampleclient.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class MainController {

	@Autowired
	private ApiService apiService;

	@GetMapping
	public String getGreetings() {
		return apiService.getGreetingMessage();
	}
}