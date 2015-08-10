package org.restful.books;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;



@Path("/register")
public class RegistrationService {

	FormDAO dao = new FormDAO();
	
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ResponseBuilder create(Form form) {
		System.out.println(form.getEmail());
		System.out.println("creating user account");
		dao.create(form);
		return Response.status(Response.Status.OK);
		
	}
	
	
	
		
 
}
