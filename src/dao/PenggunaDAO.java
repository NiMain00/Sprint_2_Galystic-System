// src/dao/PenggunaDAO.java
package dao;

import config.DatabaseConnection;
import model.Pengguna;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PenggunaDAO {

    public Pengguna login(String username, String password) {
        String sql = "SELECT * FROM pengguna WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pengguna p = new Pengguna();
                p.setId(rs.getInt("id"));
                p.setUsername(rs.getString("username"));
                p.setPassword(rs.getString("password"));
                p.setNamaLengkap(rs.getString("nama_lengkap"));
                p.setRole(rs.getString("role"));
                p.setCreatedAt(rs.getString("created_at"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Pengguna> getAll() {
        List<Pengguna> list = new ArrayList<>();
        String sql = "SELECT * FROM pengguna ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Pengguna p = new Pengguna();
                p.setId(rs.getInt("id"));
                p.setUsername(rs.getString("username"));
                p.setPassword(rs.getString("password"));
                p.setNamaLengkap(rs.getString("nama_lengkap"));
                p.setRole(rs.getString("role"));
                p.setCreatedAt(rs.getString("created_at"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Pengguna p) {
        String sql = "INSERT INTO pengguna (username, password, nama_lengkap, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getUsername());
            ps.setString(2, p.getPassword());
            ps.setString(3, p.getNamaLengkap());
            ps.setString(4, p.getRole());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Pengguna p) {
        String sql = "UPDATE pengguna SET username=?, password=?, nama_lengkap=?, role=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getUsername());
            ps.setString(2, p.getPassword());
            ps.setString(3, p.getNamaLengkap());
            ps.setString(4, p.getRole());
            ps.setInt(5, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM pengguna WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}