package br.com.nettooe;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

@OpenAPIDefinition(
	    info = @Info(
	        title="Cliente API",
	        version = "1.0.0",
	        contact = @Contact(
	            name = "Oliver Netto",
	            url = "https://www.linkedin.com/in/olivernetto",
	            email = "netto.oe@gmail.com"),
	        license = @License(
	            name = "Apache 2.0",
	            url = "http://www.apache.org/licenses/LICENSE-2.0.html"))    		
	)
public class ClienteApi extends Application {

}
