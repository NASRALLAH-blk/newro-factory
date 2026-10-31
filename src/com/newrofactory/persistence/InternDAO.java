package com.newrofactory.persistence;

import com.newrofactory.model.Intern;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InternDAO {
    private static InternDAO instance;

    private InternDAO() {}

    public static synchronized InternDAO getInstance() {
        if (instance == null) {
            instance = new InternDAO();
        }
        return instance;
    }

    public List<Intern> getAll(int limit, int offset) {
        List<Intern> list = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, arrival, formation_over, promotion_id FROM intern LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               
                Date sqlArrival = rs.getDate("arrival");
                Date sqlFin = rs.getDate("formation_over");
                
                
                list.add(new Intern(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    sqlArrival != null ? sqlArrival.toLocalDate() : null, // Sécurisé
                    sqlFin != null ? sqlFin.toLocalDate() : null,         // Sécurisé
                    rs.getLong("promotion_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la lecture: " + e.getMessage());
        }
        return list;
    }

    public void add(Intern intern) {
        String sql = "INSERT INTO intern (first_name, last_name, arrival, formation_over, promotion_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, intern.getFirstName());
            ps.setString(2, intern.getLastName());
            
            
            ps.setDate(3, intern.getArrival() != null ? Date.valueOf(intern.getArrival()) : null);
            ps.setDate(4, intern.getFormationOver() != null ? Date.valueOf(intern.getFormationOver()) : null);
            
            ps.setLong(5, intern.getPromotionId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de l'ajout: " + e.getMessage());
        }
    }
 
    public void delete(long id) {
        String sql = "DELETE FROM intern WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la suppression: " + e.getMessage());
        }
    }
}