package Conexiones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Partida;
import Model.Sesion;
/**
 * Esta clase es la encargada de hacer las operaciones del juego en si , las que invoca los los endpoints de la clase Game
 * Mediante query en SQL 
 * Hace uso de la clase Conector
 * @author Esteban
 *
 */
public class GameActions {

static Connection c  = Conector.getConectorSQL();	
	
	/**Crea la sesion del juego al haberse logeado el usuario 
	 * @param idjugador - String
	 * @param password - String
	 * @return devuelve la sesion 
	 */
	public static Sesion login(String idjugador,String password) {
		PreparedStatement statement = null;
		ResultSet result ;
		Sesion sesion=null;
		String consulta = "SELECT nick from jugador WHERE nick LIKE '"+idjugador+"' "
				+ "AND password LIKE '"+password+"';";
		String nick = null;
		
		try {
			statement = c.prepareStatement(consulta);
			result = statement.executeQuery();
					
				while(result.next()) {
						nick = 	result.getString("nick");
				}			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		try {
			Statement s = c.createStatement();
			if(nick!=null) {
				sesion= new Sesion(nick);
				statement.executeUpdate("INSERT into sesion (idjugador,idpartida)"
						+ "VALUES('"+nick+"',"+sesion.getIdpartida()+");");
				
			}else {
				System.out.println("No es correcto usuario y contraseña");
			}
			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return sesion;
		
	}
	

	
	/**Crea la partida que va en conjunto con el usuario y sesion
	 * @param nick - String
	 * @param idsesion - int
	 * @return devuelve la partida
	 */
	public static Partida startGame(String nick,int idsesion) {
		PreparedStatement statement = null;
		ResultSet result ;
		Partida partida=null;
		String consulta = "SELECT idjugador,idpartida from sesion WHERE idsesion = "+idsesion+";";		
		String jugador = null;
		
		try {
			statement = c.prepareStatement(consulta);
			result = statement.executeQuery();
					
				while(result.next()) {
					if(nick.equals(result.getString("idjugador"))) {
						if(result.getInt("idpartida")==0) {
							jugador = result.getString("idjugador");
						}else {
							Statement s;
							String query = "DELETE from partida WHERE idpartida = "+result.getInt("idpartida")+";";						
							try {
								s = c.createStatement();
								s.executeUpdate(query);
								jugador = result.getString("idjugador");
							}catch(SQLException sqle) {
								sqle.printStackTrace();
							}
						}
						
					}
						
				}			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		try {
			Statement s = c.createStatement();
				if(jugador!=null) {
					s.executeUpdate("INSERT into partida (idsesion)"
						+ "VALUES("+idsesion+");");
					partida= PartidaActions.ultimaPartidaCreada();		
					
					Statement st = c.createStatement();	
					String consulta2 = "UPDATE sesion SET idpartida="+partida.getIdpartida()+",idjugador='"+jugador+
							"' WHERE idsesion="+idsesion+";";
					st.executeUpdate(consulta2);
					
					System.out.println(consulta2);
								
				}else {
				System.out.println("Hay error con el usuario");
				}
				
			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}
				
		
		return partida;
	
		}
	
	
		
	
	
		
	}
