package it.polito.tdp.gosales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.gosales.model.Arco;
import it.polito.tdp.gosales.model.DailySale;
import it.polito.tdp.gosales.model.Products;
import it.polito.tdp.gosales.model.Retailers;

public class GOsalesDAO {
	
	/**
	 * ARCHI
	 */
	public List<Arco> getArchi(String country, Integer anno, Integer N, Map<Integer, Retailers> idMap){
		String sql = "select gr1.Retailer_code, gr2.Retailer_code, Count(distinct gds1.Product_number) as peso "
				+ "from go_retailers gr1, go_retailers gr2, go_daily_sales gds1, go_daily_sales gds2 "
				+ "where gr1.Country = ? and "
				+ "Year(gds1.Date) = ? and "
				+ "Year(gds1.Date) = Year (gds2.Date) and "
				+ "gr1.Country = gr2.Country and "
				+ "gr1.Retailer_code < gr2.Retailer_code and "
				+ "gr1.Retailer_code = gds1.Retailer_code and "
				+ "gr2.Retailer_code = gds2.Retailer_code and  "
				+ "gds1.Product_number = gds2.Product_number "
				+ "group by gr1.Retailer_code, gr2.Retailer_code "
				+ "having peso >= ? "
				+ "order by peso asc " ;
		
		List<Arco> archi = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, country);
			st.setInt(2, anno);
			st.setInt(3, N);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
					archi.add(new Arco(idMap.get(rs.getInt("gr1.Retailer_code")),
							idMap.get(rs.getInt("gr2.Retailer_code")),
							rs.getInt("peso")));
				
			}
			conn.close();
			return archi;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	/**
	 * VERTICI
	 */
	public List<Retailers> getVertici(String country){
		String sql = "select distinct * "
				+ "from go_retailers gr "
				+ "where gr.Country = ? "
				+ "order by Retailer_name asc ";
		List<Retailers> vertici = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, country);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				vertici.add(new Retailers(rs.getInt("Retailer_code"), 
						rs.getString("Retailer_name"),
						rs.getString("Type"), 
						rs.getString("Country")));		
			}
			conn.close();
			return vertici;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	/**
	 * MENU' A TENDINA PAESI
	 */
	public List<String> getCountry(){
		String sql = "select distinct Country "
				+ "from go_retailers "
				+ "order by Country asc ";
		List<String> result = new ArrayList<String>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(rs.getString("Country"));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	/**
	 * MENU' A TENDINA ANNO
	 */
	public List<Integer> getAnno(){
		String sql = "select distinct year(Date) "
				+ "from go_daily_sales "
				+ "order by year(Date) asc ";
		List<Integer> result = new ArrayList<Integer>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(rs.getInt("year(Date)"));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	/**
	 * Metodo per leggere la lista di tutti i rivenditori dal database
	 * @return
	 */

	public Map<Integer, Retailers> getAllRetailers(){
		String query = "SELECT * from go_retailers";
		Map<Integer, Retailers> result = new HashMap<Integer, Retailers>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Retailers r = new Retailers(rs.getInt("Retailer_code"), 
						rs.getString("Retailer_name"),
						rs.getString("Type"), 
						rs.getString("Country"));
				result.put(rs.getInt("Retailer_code"), r);
				
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	
	/**
	 * Metodo per leggere la lista di tutti i prodotti dal database
	 * @return
	 */
	public List<Products> getAllProducts(){
		String query = "SELECT * from go_products";
		List<Products> result = new ArrayList<Products>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Products(rs.getInt("Product_number"), 
						rs.getString("Product_line"), 
						rs.getString("Product_type"), 
						rs.getString("Product"), 
						rs.getString("Product_brand"), 
						rs.getString("Product_color"),
						rs.getDouble("Unit_cost"), 
						rs.getDouble("Unit_price")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}

	
	/**
	 * Metodo per leggere la lista di tutte le vendite nel database
	 * @return
	 */
	public List<DailySale> getAllSales(){
		String query = "SELECT * from go_daily_sales";
		List<DailySale> result = new ArrayList<DailySale>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new DailySale(rs.getInt("retailer_code"),
				rs.getInt("product_number"),
				rs.getInt("order_method_code"),
				rs.getTimestamp("date").toLocalDateTime().toLocalDate(),
				rs.getInt("quantity"),
				rs.getDouble("unit_price"),
				rs.getDouble("unit_sale_price")  ));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Retailers> getRetailersName() {
		String sql = "Select distinct * from go_retailers order by Retailer_name asc ";
		List<Retailers> result = new ArrayList<Retailers>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Retailers(rs.getInt("Retailer_code"), 
						rs.getString("Retailer_name"),
						rs.getString("Type"), 
						rs.getString("Country")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
}