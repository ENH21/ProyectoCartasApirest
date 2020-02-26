package Endpoints;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.google.gson.Gson;

import Conexiones.CartaActions;
import Conexiones.GameActions;
import Conexiones.PartidaActions;
import Conexiones.SesionActions;
import Model.Carta;
import Model.Game;
import Model.Partida;
import Model.Sesion;
import Model.Game.Cartas;
/**
 * Esta es la clase con los endpoints del juego o clase Game
 * @author Esteban
 *
 */
@Path("game")
public class Main {
	public static ArrayList<Carta> cartasUsuario1 = new ArrayList<>(); 
	public static ArrayList<Carta> cartasCPU1 = new  ArrayList<>();
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getSaludo() {
		return "Este es el inicio de los EndPoints de la clase principal";
	}
	
	
	/**Invoca al metodo login de la clase GameActions
	 * @param idjugador
	 * @param password
	 * @return Response con la ultima Sesion creada
	 */
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("idjugador")String idjugador,@FormParam("password")String password) {
		Sesion sesionExistente = SesionActions.leerSesionJugador(idjugador);
		if(sesionExistente!=null) {
			Gson gson = new Gson();
			String json =  gson.toJson(sesionExistente);
			return Response.status(Response.Status.OK).entity(json).build();
		}else {
			Sesion sesion;
			sesion = GameActions.login(idjugador, password);
			if(sesion==null) {
				Gson gson = new Gson();
				String json =  gson.toJson(sesion);
				return Response.status(Response.Status.OK).entity(json).build();
			}else {
				Gson gson = new Gson();
				String json =  gson.toJson(SesionActions.ultimaSesionCreada());
				return Response.status(Response.Status.OK).entity(json).build();
			}
		
		}
		
		
	}
	
	
	/**Invoca al metodo startGame de la clase GameActions
	 * @param nick -String
	 * @param idsesion - int
	 * @return
	 */
	@Path("start")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response startGame(@FormParam("nick")String nick ,@FormParam("idsesion")int idsesion) {
		Partida partida ;
		partida = GameActions.startGame(nick,idsesion);
		Game game = new Game(partida.getIdsesion());
		game.repartirCartas(CartaActions.leerCartas());
		ArrayList<Carta> cartasUsuario = game.getCartasUsuario(); 
		ArrayList<Carta> cartasCPU = game.getCartasCPU();
		Cartas cartas = game.new Cartas(cartasUsuario,cartasCPU);
		game.raffle();
		String json = new Gson().toJson(game);
		cartasUsuario1=game.getCartasUsuario();
		cartasCPU1=game.getCartasCPU();
		return Response.status(Response.Status.OK).entity(json).build();
	}
	
	
	
	/**Endpoint para jugar carta
	 * @param idsesion
	 * @param turno
	 * @param idcartaCPU
	 * @param featureCPU
	 * @param idcartaJugador
	 * @param featureJugador
	 * @param mano
	 * @param puntosJugador
	 * @param puntosCPU
	 * @param resultFinal
	 * @return
	 */
	@Path("play")
	@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response jugarCarta(@FormParam("idsesion")int idsesion,
			@FormParam("turno")int turno, 
			@FormParam("idcartaCPU")int idcartaCPU,
			@FormParam("featureCPU")String featureCPU,
			@FormParam("idcartaJugador") int idcartaJugador,
			@FormParam("featureJugador") String featureJugador,
			@FormParam("mano")int mano,
			@FormParam("puntosJugador")int puntosJugador, 
			@FormParam("puntosCPU")int puntosCPU, 
			@FormParam("resultFinal")int resultFinal) {
		Game game1 = new Game(idsesion,turno, idcartaCPU, featureCPU, idcartaJugador,  featureJugador,  mano,
			 puntosJugador,  puntosCPU,  resultFinal);
		game1.setCartasCPU(cartasCPU1);
		game1.setCartasUsuario(cartasUsuario1);
		game1.jugada();
		String json = new Gson().toJson(game1);
		return Response.status(Response.Status.OK).entity(json).build();
		
		
	}	

	

}

