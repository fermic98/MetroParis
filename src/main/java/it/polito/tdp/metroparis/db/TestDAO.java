package it.polito.tdp.metroparis.db;

import java.sql.Connection;
import java.util.List;

import it.polito.tdp.metroparis.model.Fermata;

public class TestDAO {

	public static void main(String[] args) {
		
		try {
			Connection connection = DBConnect.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");
			
			MetroDAO dao = new MetroDAO() ;
			List<Fermata> fermate = dao.getAllFermate();
			System.out.println(dao.getAllFermate()) ;
			System.out.println(dao.getAllLinee()) ;

			System.out.println(dao.getConnessioniTraFermate(fermate.get(90),fermate.get(92)));
		} catch (Exception e) {
			throw new RuntimeException("Test FAILED", e);
		}
	}

}
