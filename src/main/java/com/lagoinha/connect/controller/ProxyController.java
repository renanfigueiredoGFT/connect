package com.lagoinha.connect.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("proxy")
public class ProxyController {
	
	
	@GetMapping("map")
	public String getMap(String myURL) {
		URL url;
		try {
			url = new URL(myURL);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			conexao.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(
					  new InputStreamReader(conexao.getInputStream()));
					String inputLine;
					StringBuffer content = new StringBuffer();
					while ((inputLine = in.readLine()) != null) {
					    content.append(inputLine);
					}
					in.close();
					conexao.disconnect();
					return content.toString();
		} catch (Exception e) {
			return "[]";
		}
		
	}

}
