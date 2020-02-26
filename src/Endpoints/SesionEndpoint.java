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

import Conexiones.JugadorActions;
import Conexiones.PartidaActions;
import Conexiones.SesionActions;
import Model.Jugador;
import Model.Partida;
import Model.Sesion;
/**
 * Esta es la clase con los endpoints de Sesion
 * @author Esteban
 *
 */
@Path("sesion")
public class SesionEndpoint {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getSaludo() {
		return "Este es el inicio de los EndPoints de la clase Sesion";
	}
	/**Invoca al metodo leerSesion de la clase SesionActions 
	 * tipo GET
	 * @param id - int 
	 * @return String con la sesion en formato json
	 */
	@Path("/{idsesion}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String leerSesion(@PathParam("idsesion")int idsesion) {
		Sesion sesion;
		sesion = SesionActions.leerSesion(idsesion);
		Gson gson = new Gson();
		return gson.toJson(sesion);
		
	}
	/**Invoca al metodo leerSesiones de la clase SesionActions
	 * tipo GET
	 * @return String con el array de sesiones en formato json
	 */
	@Path("/sesiones")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String leerSesiones() {
		ArrayList<Sesion> sesiones = new ArrayList<>();
		sesiones = SesionActions.leerSesiones();
		Gson gson = new Gson();
		return gson.toJson(sesiones);
	}
	
	/**Invoca al metodo ultimaSesionCreada de la clase SesionActions
	 * tipo GET
	 * @return String con la sesion en formato json
	 */
	@Path("/last")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String ultimaPartida() {
		Sesion sesion;
		sesion = SesionActions.ultimaSesionCreada();
		Gson gson = new Gson();
		return gson.toJson(sesion);
	}
	
	/**Invoca al metodo crearSesion de la clase SesionActions
	 * tipo POST
	 * @param sesion
	 * @return response con sesion en formato json
	 */
	@Path("/crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearSesion(Sesion sesion) { 		
		SesionActions.crearSesion(sesion);					
		String json = new Gson().toJson(sesion);		
		return Response.status(Response.Status.OK).entity(json).build();
	}

	
	/**Invoca al metodo editarSesion de la clase SesionActions
	 * tipo PUT 
	 * @param id - int
	 * @param sesion
	 * @return response con sesion en formato json
	 */
	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editarSesion(@PathParam("id")int id,Sesion sesion) {
		SesionActions.editarSesion( id,sesion);
		String json = new Gson().toJson(sesion);		
		return Response.status(Response.Status.OK).entity(json).build();
		
	}
	
	
	/**Invoca metodo borrarSesion de la clase SesionActions
	 * tipo DELETE
	 * @param idjugador -String
	 * @return response con mensaje
	 */
	@Path("/{idjugador}")
	@DELETE
	public Response borrarSesion(@PathParam("idjugador")String idjugador) {
		boolean deleted;
		String mensaje;
		deleted = SesionActions.borrarSesion(idjugador);
		if(deleted) {
			mensaje = ("Ha hecho deleteee..");
		}else {
			mensaje = ("salio mal..");
		}		
		return Response.status(Response.Status.OK).entity(mensaje).build();
	}
}
