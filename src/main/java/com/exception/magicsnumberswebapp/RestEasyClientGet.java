package com.exception.magicsnumberswebapp;

import com.exception.magicsnumberswebapp.entities.User;
import com.exception.magicsnumberswebapp.entities.UserConteiner;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

public class RestEasyClientGet {
 
	public static void main(String[] args) {
            Gson gSon = new Gson();
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
 
		BufferedReader br = new BufferedReader(new InputStreamReader(
			new ByteArrayInputStream(response.getEntity().getBytes())));
                User[] users = (User[])gSon.fromJson(response.getEntity(), User[].class);
 
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
 
	  } catch (ClientProtocolException e) {
 
		e.printStackTrace();
 
	  } catch (IOException e) {
 
		e.printStackTrace();
 
	  } catch (Exception e) {
 
		e.printStackTrace();
 
	  }
 
	}
 
}