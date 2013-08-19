package com.exception.magicsnumberswebapp;

import com.exception.magicsnumberswebapp.entities.User;
import com.exception.magicsnumberswebapp.entities.UserContainer;
import com.exception.magicsnumberswebapp.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class RestEasyClientGet {
 
    @Autowired
    private UserService userService;
	public static void main(String[] args) {
            Gson gSon = new Gson();
            String json = "{firtName: 'fausto'}";
            
            
                
	  try { 
		ClientRequest request;
              request = new ClientRequest(
                "http://localhost:8084/MagicsNumbersWS/security/user");                 
		request.accept("application/json");
		ClientResponse<String> response = request.get(String.class);
 
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
		}
                UserContainer[] users = (UserContainer[])gSon.fromJson(response.getEntity(), UserContainer[].class);                 
                users= users;
 	 
	  } catch (ClientProtocolException e) {
 
		e.printStackTrace();
 
	  } catch (IOException e) {
 
		e.printStackTrace();
 
	  } catch (Exception e) {
 
		e.printStackTrace();
 
	  }
 
	}
 
}