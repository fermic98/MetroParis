package it.polito.tdp.metroparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Linea;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"),
						new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	public List<Linea> getAllLinee() {
		final String sql = "SELECT id_linea, nome, velocita, intervallo FROM linea ORDER BY nome ASC";

		List<Linea> linee = new ArrayList<Linea>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Linea f = new Linea(rs.getInt("id_linea"), rs.getString("nome"), rs.getDouble("velocita"),
						rs.getDouble("intervallo"));
				linee.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return linee;
	}
    
	
	public boolean getConnessioniTraFermate(Fermata f1, Fermata f2) {
		
		final String sql="SELECT COUNT(*) AS C FROM connessione WHERE id_stazP=? AND id_stazA=?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, f1.getIdFermata());
			ps.setInt(2, f2.getIdFermata());

			ResultSet rs = ps.executeQuery();
			
			rs.next() ;
				if(rs.getInt("C")>=1) {
					return true;
				}
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		    throw new RuntimeException();
		}
		
		return false;
		
		
	}

	
	public List<Integer> getAllAdiacenti(Fermata fermata){
		List<Integer> adiacenti= new ArrayList<>();
		final String sql = "SELECT DISTINCT id_stazA FROM connessione " + 
				"WHERE id_stazP=?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, fermata.getIdFermata());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				adiacenti.add(rs.getInt("id_stazA"));
			}
			
		} catch (SQLException e) {
			 e.printStackTrace();
		}
		
		
		
		return adiacenti;
	}
}
