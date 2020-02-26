package Conexiones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Jugador;
import Model.Partida;
import Model.Sesion;
/**
 * Esta clase es la encargada de hacer las operaciones de las partidas , las que invoca los los endpoints de la clase Partida
 * Mediante query en SQL 
 * Hace uso de la clase Conector
 * @author Esteban
 *
 */
public class PartidaActions {
static Connection c  = Conector.getConectorSQL();	
	
	/**Obtiene todas las partidas
	 * @return devuelve arraylist con las partidas
	 */
	public static ArrayList leerPartidas() {
		PreparedStatement statement;
		ResultSet result ;
		ArrayList<Partida> partidas = new ArrayList<>();
		String consulta = "SELECT * from partida";
		
		try {
			statement = c.prepareStatement(consulta);
			result = statement.executeQuery();
			
			
				while(result.next()) {
					partidas.add(new Partida(result.getInt("idpartida"),result.getInt("idsesion")));
				}			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return partidas;
		
	}
	
	/**Obtiene una partida en especifico , indicada en parametro mediante su id
	 * @param idpartida - int
	 * @return
	 */
	public static Partida leerPartida(int idpartida) {
		Statement statement;
		ResultSet result ;
		Partida partida = null;
		String consulta = "SELECT * from partida WHERE idpartida = "+idpartida+";";
		
		try {
			statement = c.createStatement();
			result = statement.executeQuery(consulta);
			
			
				if(result.next()) {
					partida = new Partida(result.getInt("idpartida"),result.getInt("idsesion"));
				}
									
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return partida;
		
	}
	
	/**Crea una partida en la base de datos
	 * @param partida
	 */
	public static void crearPartida(Partida partida) {
		Statement statement;
		ResultSet result ;	
		String consulta = "INSERT into partida (idsesion)"
				+ "VALUES("+partida.getIdsesion()+");";
		System.out.println(consulta);
		try {
			statement = c.createStatement();
			statement.executeUpdate(consulta);
			System.out.println("sssssssssssssssssssssssssssssssssssssssssssss");			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		
	}
	
	/**Edita una partida en concrecto , elegida mediante su id
	 * @param idpartida - int
	 * @param partida 
	 */
	public static void editarPartida(int idpartida,Partida partida) {
		Statement statement;
		ResultSet result ;
		String consulta = "UPDATE partida SET idsesion="+partida.getIdsesion()+
				" WHERE idpartida="+idpartida+";";
		System.out.println(consulta);
		try {
			statement = c.createStatement();
			statement.executeUpdate(consulta);
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		
	}
	/**Elimina la partida elegida mediante su id
	 * @param idpartida - int
	 * @return
	 */
	public static boolean borrarPartida(int idpartida) {
		Statement statement;
		boolean deleted = false;
		String consulta = "DELETE from partida WHERE idpartida = "+idpartida+";";
		
		try {
			statement = c.createStatement();
			statement.executeUpdate(consulta);
			deleted = true;
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return deleted;
		
	}
	
	/**Obtiene la ultima partida creada
	 * @return devuelve la ultima partida
	 */
	public static Partida ultimaPartidaCreada() {
		Partida partida = null;
		PreparedStatement statement;
		ResultSet result ;
		String consulta = "SELECT * from partida ORDER BY idpartida DESC LIMIT 1 ";
		
		try {
			statement = c.prepareStatement(consulta);
			result = statement.executeQuery();
			
				while(result.next()) {
					partida=new Partida(result.getInt("idpartida"),result.getInt("idsesion"));
				}			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return partida;
	}
	
}


