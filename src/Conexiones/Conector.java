package Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Esta clase es la que se encarga de la conexion con la base de datos 
 * @author Esteban
 *
 */
public class Conector {	
	
	/**Hace uso de jdbc para realizar la conexion
	 * Se le suele llamar en los metodos que realizan las operaciones de cada clase
	 * @return devuelve la conexion 
	 */
	public static Connection getConectorSQL() {
		Connection connection = null ;
		String user = "root";
		String password = "";
		String urlConnection = "jdbc:mysql://localhost:3306/cartasdb?serverTimezone=UTC";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(urlConnection,user,password);			
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
}
