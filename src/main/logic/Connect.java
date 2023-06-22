/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.logic;

/**
 *
 * @author Francisco Valente
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Connect {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/despesa";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "123";

     public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    public static void saveData(String text1, String text2, double text3, double text4) {
        String query = "INSERT INTO despesas (nif, categoria, valor, iva) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, text1);
            statement.setString(2, text2);
            statement.setDouble(3, text3);
            statement.setDouble(4, text4);
            statement.executeUpdate();
            System.out.println("Data saved successfully.");
        } catch (SQLException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    
    public static void getAllData(JTable jTable) {
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        model.setRowCount(0); // Clear existing data

        String query = "SELECT * FROM despesas";

        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String text1 = resultSet.getString("nif");
                String text2 = resultSet.getString("categoria");
                double text3 = resultSet.getDouble("valor");
                double text4 = resultSet.getDouble("iva");
                double iva = text3*text4;
                double irs = 0.0;
                
                
                if(!text1.equals(""))
                    for(Categoria cat: Categoria.values())
                        if(text2.equals(cat.getCategoria()))
                            irs = iva*cat.getIRS();
               
   

                model.addRow(new Object[]{ text2, text3, iva,String.format("%.2f",irs)});
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving data: " + e.getMessage());
        }
}
}