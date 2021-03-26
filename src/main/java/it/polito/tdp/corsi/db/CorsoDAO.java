package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {

	public List<Corso> getCorsiByPeriodo(Integer periodo) {
		
		String sql = "SELECT * "
			+ "FROM corso "
			+ "WHERE pd = ?";
	
		List<Corso> Result = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				Result.add(c);
			}
			
			rs.close();
			st.close();
			conn.close(); //importante
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return Result;
		
	}
	
	public Map<Corso, Integer> getIscrittiByPeriodo(Integer periodo){
		String sql = "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins = i.codins AND c.pd = ? "
				+ "GROUP BY c.codins, c.nome, c.crediti, c.pd";
		
			Map<Corso, Integer> Result = new HashMap<>();
			
			try {
				
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, periodo);
				ResultSet rs = st.executeQuery();
				
				while(rs.next()) {
					
					Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
					Integer n = rs.getInt("tot");
					
					Result.put(c, n);
					
				}
				
				rs.close();
				st.close();
				conn.close(); //importante
				
			} catch(SQLException e) {
				throw new RuntimeException(e);
			}
			
			return Result;
			
	}
	
}