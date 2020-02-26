package Conexiones;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Carta;
import Model.Jugador;
import Model.Sesion;

/**
 * Esta clase es la encargada de hacer las operaciones de los jugadores, las que invoca los los endpoints de la clase Jugador
 * Mediante query en SQL 
 * Hace uso de la clase Conector
 * @author Esteban
 *
 */
public class JugadorActions {
	static Connection c  = Conector.getConectorSQL();	
	
	/**Obtiene el array de los jugadores
	 * @return devuelve el arraylist de los jugadores
	 */
	public static ArrayList leerJugadores() {
		PreparedStatement statement;
		ResultSet result ;
		ArrayList<Jugador> jugadores = new ArrayList<>();
		String consulta = "SELECT * from jugador";
		
		try {
			statement = c.prepareStatement(consulta);
			result = statement.executeQuery();
			
			
				while(result.next()) {
					jugadores.add(new Jugador(result.getString("nick"),result.getString("password"),
							result.getInt("partidas_ganadas"),result.getInt("partidas_perdidas"),result.getInt("partidas_empatadas")));
				}
			
			
			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return jugadores;
		
	}
	
	/**Obtiene un jugador en especifico , especificado por su nick
	 * @param nick - String
	 * @return devuelve el Jugador
	 */
	public static Jugador leerJugador(String nick) {
		Statement statement;
		ResultSet result ;
		Jugador jugador = null;
		String consulta = "SELECT * from jugador WHERE nick LIKE '"+nick+"';";
		
		try {
			statement = c.createStatement();
			result = statement.executeQuery(consulta);
			
			
				if(result.next()) {
					jugador = new Jugador(result.getString("nick"),result.getString("password"),
							result.getInt("partidas_ganadas"),result.getInt("partidas_perdidas"),result.getInt("partidas_empatadas"));
				}
									
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return jugador;
		
	}
	
	/**Crear un jugador en la base de datos
	 * @param jugador 
	 */
	public static Boolean crearJugador(Jugador jugador) {
		Boolean hecho = false;
		Statement statement;
		ResultSet result ;	
		String consulta = "INSERT into jugador (nick,password,partidas_ganadas,partidas_perdidas,partidas_empatadas)"
				+ "VALUES('"+jugador.getNick()+"','"+jugador.getPassword()+"',"+jugador.getPartidas_ganadas()+
				","+jugador.getPartidas_perdidas()+","+jugador.getPartidas_empatadas()+");";
		
		try {
			statement = c.createStatement();
			statement.executeUpdate(consulta);
			hecho  = true;
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return hecho;
		
	}
	/**Edita un jugador en especifico , especificado por nick 
	 * @param nick - String
	 * @param jugador - con los datos nuevos
	 */
	public static void editarJugador(String nick,Jugador jugador) {
		Statement statement;
		ResultSet result ;
		String consulta = "UPDATE jugador SET password='"+jugador.getPassword()+"',partidas_ganadas= "+jugador.getPartidas_ganadas()+","
				+ "partidas_perdidas="+jugador.getPartidas_perdidas()+",partidas_empatadas="+jugador.getPartidas_empatadas()+
				" WHERE nick='"+nick+"';";
		System.out.println(consulta);
		try {
			statement = c.createStatement();
			statement.executeUpdate(consulta);
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		
	}
	/**Elimina un jugador de la base de datos , jugador elegido mediante su nick
	 * @param nick - String
	 * @return devuelve un booleano con la respuesta en afirmativo si se borro corectamente , y negativo en caso contrario.
	 */
	public static boolean borrarJugador(String nick) {
		Statement statement;
		boolean deleted = false;
		String consulta = "DELETE from jugador WHERE nick Like '"+nick+"';";
		
		try {
			statement = c.createStatement();
			statement.executeUpdate(consulta);
			deleted = true;
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return deleted;
		
	}

	/**Obtiene el ultimo jugador creado
	 * @return devuelve el ultimo jugador creado
	 */
	public static Jugador ultimoJugadorCreado() {
		Jugador jugador = null;
		PreparedStatement statement;
		ResultSet result ;
		String consulta = "SELECT * from jugador ORDER BY nick DESC LIMIT 1 ";
		
		try {
			statement = c.prepareStatement(consulta);
			result = statement.executeQuery();
			
				while(result.next()) {
					jugador=new Jugador(result.getString("nick"),result.getString("password"),
							result.getInt("partidas_ganadas"),result.getInt("partidas_perdidas"),
							result.getInt("partidas_empatadas"));
				}			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return jugador;
	
	}
}
