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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import Conexiones.CartaActions;
import Conexiones.JugadorActions;
import Conexiones.SesionActions;
import Model.Carta;
import Model.Jugador;
import Model.Sesion;
/**
 * Esta es la clase con los endpoints de Jugador
 * @author Esteban
 *
 */
@Path("jugador")
public class JugadorEndpoint {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getSaludo() {
		return "Este es el inicio de los EndPoints de la clase Jugador";
	}
	/**Invoca al metodo leerJugador de la clase JugadorActions 
	 * tipo GET
	 * @param nick - String 
	 * @return String con el jugador en formato json
	 */
	@Path("/{nick}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String leerJugador(@PathParam("nick")String nick) {
		Jugador jugador;
		jugador = JugadorActions.leerJugador(nick);
		Gson gson = new Gson();
		return gson.toJson(jugador);
		
	}
	/**Invoca al metodo leerJugadores de la clase JugadorActions
	 * tipo GET
	 * @return String con el array de jugadores en formato json
	 */
	@Path("/jugadores")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String leerJugadores() {
		ArrayList<Jugador> jugadores = new ArrayList<>();
		jugadores = JugadorActions.leerJugadores();
		Gson gson = new Gson();
		return gson.toJson(jugadores);
	}
	
	/**Invoca al metodo ultimoJugadorCreado de la clase JugadorActions
	 * tipo GET
	 * @return String con el jugdor en formato json
	 */
	@Path("/last")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String ultimoJugador() {
		Jugador jugador;
		jugador = JugadorActions.ultimoJugadorCreado();
		Gson gson = new Gson();
		return gson.toJson(jugador);
	}
	
	/**Invoca al metodo crearJugador de la clase JugadorActions
	 * tipo POST
	 * @param jugador
	 * @return response con jugador en formato json
	 */
	@Path("/crear")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearJugador(@FormParam("nick")String nick,@FormParam("password")String password) { 		
		Jugador jugador = new Jugador(nick,password);
		Boolean hecho = JugadorActions.crearJugador(jugador);		
		if(hecho) {
			String json = new Gson().toJson(jugador);		
			return Response.status(Response.Status.OK).entity(json).build();
		}else {
			return Response.status(400).build();
		}
		
	}

	
	/**Invoca al metodo editarJugador de la clase JugadorActions
	 * tipo PUT
	 * @param nick - String
	 * @param jugador
	 * @return response con jugador en formato json
	 */
	@Path("/{nick}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editarJugador(@PathParam("nick")String nick,Jugador jugador) {
		JugadorActions.editarJugador( nick,jugador);
		String json = new Gson().toJson(jugador);		
		return Response.status(Response.Status.OK).entity(json).build();
		
	}
	
	
	/**Invoca al metodo borrarJugador de la clase JugadorActions
	 * tipo DELETE
	 * @param nick - String
	 * @return response con mensaje
	 */
	@Path("/{nick}")
	@DELETE
	public Response borrarCarta(@PathParam("nick")String nick) {
		boolean deleted;
		String mensaje;
		deleted = JugadorActions.borrarJugador(nick);
		if(deleted) {
			mensaje = ("Ha hecho deleteee..");
		}else {
			mensaje = ("salio mal..");
		}		
		return Response.status(Response.Status.OK).entity(mensaje).build();
	}
}
