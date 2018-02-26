package com.add.service;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("/captcha")
public class AddService {
	
	private static String QUESTION = "Please sum the numbers ";
	
	//This object is used to serialize Java objects to JSON & deserialize JSON to Java objects
	ObjectMapper mapper = new ObjectMapper();
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendQuestion() {
		//Build the response question
		StringBuilder question = new StringBuilder(AddService.QUESTION);
		/*Assumptions: For the captcha to be simple, yet efficient, I have made the assumption that we will be providing
		 * 2-4 numbers to add up and each number will range from 1-9.
		 */
		//This is to generate the number count - minimum is 2 and maximum excluded is 5
		int numCount = ((int)(Math.random()*(5 - 2))) +2;
		/*Storing it in an array so that it can be used to mark if the question was asked. Extra scoring point 1.
		 * The array can be stored in a hashmap marked as true. This can be used to confirm if the question was asked to a client.
		 * 
		 */
		int[] randomNumbers = new int[numCount];
		//Now run a for loop numCount times
		for(int i=0; i<numCount; i++) {
			//Generate random number to sum up here
			randomNumbers[i] = ((int)(Math.random()*(10 - 1))) +1;
		}
		question.append(Arrays.toString(randomNumbers).replace("[", "").replace("]", ""));
		return Response.status(200).entity(question.toString()).build();
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{key}")
	public Response sendResult(String question, @PathParam("key") int key) {
		//TODO: Parse question and get the sum - using regex. Using 3 for now
		//if parsing gives an error : return 400 response
		if(key == 4) {
			return Response.status(200).entity("That's great").build();
		}
		else {
			return Response.status(400).entity("That's wrong. Try again!").build();
		}
	}
	
	
}
