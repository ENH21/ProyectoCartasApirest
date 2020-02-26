package Endpoints;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import Conexiones.PartidaActions;
import Conexiones.SesionActions;
import Model.Partida;

/**
 * Esta es la clase con los endpoints de Partida
 * @author Esteban
 *
 */
@Path("partida")
public class PartidaEndpoint {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getSaludo() {
		return "Este es el inicio de los EndPoints de la clase Partida";
	}
	/**Invoca al metodo leerPartida de la clase PartidaActions 
	 * tipo GET
	 * @param id - int 
	 * @return String con la partida en formato json
	 */
	@Path("/{idpartida}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String leerPartida(@PathParam("idpartida")int idpartida) {
		Partida partida;
		partida = PartidaActions.leerPartida(idpartida);
		Gson gson = new Gson();
		return gson.toJson(partida);
		
	}
	/**Invoca al metodo leerPartidas de la clase PartidaActions
	 * tipo GET
	 * @return String con el array de partidas en formato json
	 */
	@Path("/partidas")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String leerPartidas() {
		ArrayList<Partida> partidas = new ArrayList<>();
		partidas = PartidaActions.leerPartidas();
		Gson gson = new Gson();
		return gson.toJson(partidas);
	}
	/**Invoca al metodo ultimaPartidaCreada de la clase PartidaActions
	 * tipo GET
	 * @return String con la partida en formato json
	 */
	@Path("/last")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String ultimaPartida() {
		Partida partida;
		partida = PartidaActions.ultimaPartidaCreada();
		Gson gson = new Gson();
		return gson.toJson(partida);
	}
	/**Invoca al metodo crearPartida de la clase PartidaActions
	 * tipo POST
	 * @param partida
	 * @return response con partida en formato json
	 */
	@Path("/crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearPartida(Partida partida) { 		
		PartidaActions.crearPartida(partida);					
		String json = new Gson().toJson(partida);		
		return Response.status(Response.Status.OK).entity(json).build();
	}

	/**Invoca al metodo EditarPartida de la clase PartidaActions
	 * tipo PUT
	 * @param id - int 
	 * @param partida
	 * @return response con partida en formato json
	 */
	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editarPartida(@PathParam("id")int id,Partida partida) {
		PartidaActions.editarPartida(id,partida);
		String json = new Gson().toJson(partida);		
		return Response.status(Response.Status.OK).entity(json).build();
		
	}
	
	
	/**Invoca metodo borrarPartida de la clase PartidaActions
	 * tipo DELETE
	 * @param id - int 
	 * @return response con mensaje
	 */
	@Path("/{id}")
	@DELETE
	public Response borrarPartida(@PathParam("id")int id) {
		boolean deleted;
		String mensaje;
		deleted = PartidaActions.borrarPartida(id);
		if(deleted) {
			mensaje = ("Ha hecho deleteee..");
		}else {
			mensaje = ("salio mal..");
		}		
		return Response.status(Response.Status.OK).entity(mensaje).build();
	}
}
