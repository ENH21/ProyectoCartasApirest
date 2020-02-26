package Endpoints;
import java.util.ArrayList;

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

import com.google.gson.Gson;

import Conexiones.CartaActions;
import Conexiones.SesionActions;
import Model.Carta;
import Model.Sesion;
/**
 * Esta es la clase con los endpoints de Carta
 * @author Esteban
 *
 */
@Path("/carta")
public class CartaEndpoint {
	

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getSaludo() {
		return "Este es el inicio de los EndPoints de la clase Carta";
	}

	/**Invoca al metodo leerCarta de la clase CartaActions 
	 * tipo GET
	 * @param id - int 
	 * @return response con la carta en formato json
	 */
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response leerCarta(@PathParam("id")int id) {
		Carta carta;
		carta = CartaActions.leerCarta(id);
		Gson gson = new Gson();
		 String json = gson.toJson(carta);
		return Response.status(Response.Status.OK).entity(json).build();
		
	}
	
	/**Invoca al metodo leerCartas de la clase CartaActions
	 * tipo GET
	 * @return String con el array de cartas en formato json
	 */
	@Path("/cartas")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String leerCartas() {
		ArrayList<Carta> cartas = new ArrayList<>();
		cartas = CartaActions.leerCartas();
		Gson gson = new Gson();
		return gson.toJson(cartas);
	}
	
	
	/**Invoca al metodo ultimaCartaCreada de la clase CartaActions
	 * tipo GET
	 * @return String con la carta en formato json
	 */
	@Path("/last")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String ultimaCarta() {
		Carta carta;
		carta = CartaActions.ultimaCartaCreada();
		Gson gson = new Gson();
		return gson.toJson(carta);
	}
	
	/**Invoca al metodo crearCarta de la clase CartaActions
	 * tipo POST
	 * @param carta
	 * @return response con carta en formato json
	 */
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearCarta(Carta  carta) { 		
		CartaActions.crearCarta(carta);					
		String json = new Gson().toJson(carta);		
		return Response.status(Response.Status.OK).entity(json).build();
	}
	
	
	/**Invoca al metodo editarCarta de la clase CartaActions
	 * tipo PUT
	 * @param idcarta - int
	 * @param carta
	 * @return response de carta en formato json
	 */
	@Path("/{idcarta}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editarCarta(@PathParam("idcarta")int idcarta,Carta carta) {
		CartaActions.editarCarta( idcarta,carta);
		String json = new Gson().toJson(carta);		
		return Response.status(Response.Status.OK).entity(json).build();
		
	}
	
	/**Invoca el metodo borrarCarta de la clsae CartaActions
	 * tipo DELETE
	 * @param id - int
	 * @return reponse con mensaje 
	 */
	@Path("/{idx}")
	@DELETE
	public Response borrarCarta(@PathParam("idx")int id) {
		boolean deleted;
		String mensaje;
		deleted = CartaActions.borrarCarta(id);
		if(deleted) {
			mensaje = ("Ha hecho deleteee..");
		}else {
			mensaje = ("salio mal..");
		}		
		return Response.status(Response.Status.OK).entity(mensaje).build();
	}
}
