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
 * Esta clase es la encargada de hacer las operaciones de las sesiones , las que invoca los los endpoints de la clase Sesion
 * Mediante query en SQL 
 * Hace uso de la clase Conector
 * @author Esteban
 *
 */
public class SesionActions {
static Connection c  = Conector.getConectorSQL();	
	
	/**Obtiene todas las sesiones
	 * @return arraylist con las sesiones
	 */
	public static ArrayList leerSesiones() {
		PreparedStatement statement;
		ResultSet result ;
		ArrayList<Sesion> sesiones = new ArrayList<>();
		String consulta = "SELECT * from sesion";
		
		try {
			statement = c.prepareStatement(consulta);
			result = statement.executeQuery();
			
			
				while(result.next()) {
					sesiones.add(new Sesion(result.getInt("idsesion"),result.getInt("idpartida"),
							result.getString("idjugador")));
				}			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return sesiones;
		
	}
	
	/**Obtiene la sesion elegida mediante su id
	 * @param idsesion - int
	 * @return sesion
	 */
	public static Sesion leerSesion(int idsesion) {
		Statement statement;
		ResultSet result ;
		Sesion sesion = null;
		String consulta = "SELECT * from sesion WHERE idsesion = "+idsesion+";";
		
		try {
			statement = c.createStatement();
			result = statement.executeQuery(consulta);
			
			
				if(result.next()) {
					sesion = new Sesion(result.getInt("idsesion"),result.getInt("idpartida"),
							result.getString("idjugador"));
				}
									
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return sesion;
		
	}
	public static Sesion leerSesionJugador(String idjugador) {
		Statement statement;
		ResultSet result ;
		Sesion sesion = null;
		String consulta = "SELECT * from sesion WHERE idjugador LIKE '"+idjugador+"';";
		
		try {
			statement = c.createStatement();
			result = statement.executeQuery(consulta);
			
			
				if(result.next()) {
					sesion = new Sesion(result.getInt("idsesion"),result.getInt("idpartida"),
							result.getString("idjugador"));
				}
									
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return sesion;
		
	}
	
	/**Crea una sesion en la base de datos
	 * @param sesion -
	 */
	public static void crearSesion(Sesion sesion) {
		Statement statement;
		ResultSet result ;	
		String consulta = "INSERT into sesion (idjugador,idpartida)"
				+ "VALUES('"+sesion.getIdjugador()+"',"+sesion.getIdpartida()+");";
		System.out.println(consulta);
		try {
			statement = c.createStatement();
			statement.executeUpdate(consulta);
			System.out.println("sssssssssssssssssssssssssssssssssssssssssssss");			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		
	}
	/**Edita la sesion elegida mediante su id
	 * @param idsesion - int
	 * @param sesion -
	 */
	public static void editarSesion(int idsesion,Sesion sesion) {
		Statement statement;
		ResultSet result ;
		String consulta = "UPDATE sesion SET idpartida="+sesion.getIdpartida()+",idjugador='"+sesion.getIdjugador()+
				"' WHERE idsesion="+idsesion+";";
		System.out.println(consulta);
		try {
			statement = c.createStatement();
			statement.executeUpdate(consulta);
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		
	}
	/**Elimina la sesion elegida mediante el nick del jugador
	 * @param idjugador - String
	 * @return boolean con mensaje afirmativo si se elimino correctamente y negativo en caso contrario
	 */
	public static boolean borrarSesion(String idjugador) {
		Statement statement;
		boolean deleted = false;
		String consulta = "DELETE from sesion WHERE idjugador LIKE '"+idjugador+"';";
		
		try {
			statement = c.createStatement();
			statement.executeUpdate(consulta);
			deleted = true;
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return deleted;
		
	}

	/**Obtiene la ultima sesion creada
	 * @return sesion
	 */
	public static Sesion ultimaSesionCreada() {
		Sesion sesion = null;
		PreparedStatement statement;
		ResultSet result ;
		String consulta = "SELECT * from sesion ORDER BY idsesion DESC LIMIT 1 ";
		
		try {
			statement = c.prepareStatement(consulta);
			result = statement.executeQuery();
			
				while(result.next()) {
					sesion=new Sesion(result.getInt("idsesion"),result.getInt("idpartida"),result.getString("idjugador"));
				}			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return sesion;
	
	}
}


