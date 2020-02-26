package Conexiones;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Carta;
import Model.Sesion;

/**
 * Esta clase es la encargada de hacer las operaciones de las cartas , las que invoca los los endpoints de la clase Carta
 * Mediante query en SQL 
 * Hace uso de la clase Conector
 * @author Esteban
 *
 */
public class CartaActions {
	static Connection c  = Conector.getConectorSQL();	
	
	
	/**Obtiene el array de cartas
	 * @return Devuelve el arraylist de las cartas
	 */
	public static ArrayList leerCartas() {
		PreparedStatement statement;
		ResultSet result ;
		ArrayList<Carta> cartas = new ArrayList<>();
		String consulta = "SELECT * from carta";
		
		try {
			statement = c.prepareStatement(consulta);
			result = statement.executeQuery();
			
			
				while(result.next()) {
					cartas.add(new Carta(result.getInt("idcarta"),result.getString("marca"),result.getString("modelo"),
							result.getInt("motor"),result.getInt("cilindros"),result.getInt("potencia"),
							result.getInt("revoluciones"),result.getInt("velocidad"),result.getDouble("consumo")));
				}
			
			
			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return cartas;
		
	}
	
	/**Obtiene la carta que coincide con el id pasado como parametro
	 * @param idcarta
	 * @return devuelve la carta correspondiente
	 */
	public static Carta leerCarta(int idcarta) {
		PreparedStatement statement;
		ResultSet result ;
		Carta carta = null;
		String consulta = "SELECT * from carta WHERE idcarta ="+idcarta+";";
		
		try {
			statement = c.prepareStatement(consulta);
			result = statement.executeQuery();
			
			
				if(result.next()) {
					carta = new Carta(result.getInt("idcarta"),result.getString("marca"),result.getString("modelo"),
							result.getInt("motor"),result.getInt("cilindros"),result.getInt("potencia"),
							result.getInt("revoluciones"),result.getInt("velocidad"),result.getDouble("consumo"));
				}
									
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return carta;
		
	}
	
	/**Crea una carta en la base de datos 
	 * @param carta
	 */
	public static void crearCarta(Carta carta) {
		Statement statement;
		ResultSet result ;	
		String consulta = "INSERT into carta (marca,modelo,motor,cilindros,potencia,revoluciones,velocidad,consumo) "
				+ "VALUES('"+carta.getMarca()+"','"+carta.getModelo()+"',"+carta.getMotor()+","+carta.getCilindros()+","+carta.getPotencia()+","+carta.getRevoluciones()+
				","+carta.getVelocidad()+","+carta.getConsumo()+");";
		System.out.println(consulta);
		try {
			statement = c.createStatement();
			statement.executeUpdate(consulta);
			System.out.println("sssssssssssssssssssssssssssssssssssssssssssss");			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		
	}
	
	/**Edita la carta que corresponde a la id que se le pasa como parametro , ademas de la carta con los datos nuevos
	 * @param idcarta
	 * @param carta
	 */
	public static void editarCarta(int idcarta,Carta carta) {
		Statement statement;
		ResultSet result ;
		String consulta = "UPDATE carta SET marca='"+carta.getMarca()+"',modelo='"+carta.getModelo()+"',motor= "+carta.getMotor()+","
				+ "cilindros="+carta.getCilindros()+",potencia="+carta.getPotencia()+",revoluciones="+carta.getRevoluciones()+
				",velocidad="+carta.getVelocidad()+",consumo="+ carta.getConsumo()+" WHERE idcarta="+idcarta+";";
		System.out.println(consulta);
		try {
			statement = c.createStatement();
			statement.executeUpdate(consulta);
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		
	}
	/**Elimina la carta que corresponde pasandole el id como parametro
	 * @param idcarta
	 * @return
	 */
	public static boolean borrarCarta(int idcarta) {
		Statement statement;
		boolean deleted = false;
		String consulta = "DELETE from carta WHERE idcarta ="+idcarta+";";
		
		try {
			statement = c.createStatement();
			statement.executeUpdate(consulta);
			deleted = true;
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return deleted;
		
	}

	/**Obtiene la ultima carta creada
	 * @return devuelve la ultima carta creada
	 */
	public static Carta ultimaCartaCreada() {
		Carta carta = null;
		PreparedStatement statement;
		ResultSet result ;
		String consulta = "SELECT * from carta ORDER BY idcarta DESC LIMIT 1 ";
		
		try {
			statement = c.prepareStatement(consulta);
			result = statement.executeQuery();
			
				while(result.next()) {
					carta=new Carta(result.getInt("idcarta"),result.getString("marca"),result.getString("modelo"),
							result.getInt("motor"),result.getInt("cilindros"),result.getInt("potencia"),
							result.getInt("revoluciones"),result.getInt("velocidad"),result.getDouble("consumo"));
				}			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return carta;
	}
}
