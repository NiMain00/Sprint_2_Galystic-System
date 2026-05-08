// src/dao/ProdukDAO.java
package dao;

import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Barang;
import model.Jasa;
import model.Produk;

public class ProdukDAO {

    // ===== BARANG =====
    public List<Barang> getAllBarang() {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT p.*, b.kategori_id, b.deskripsi, k.nama_kategori " +
                     "FROM produk p JOIN barang b ON p.id = b.id " +
                     "LEFT JOIN kategori_barang k ON b.kategori_id = k.id ORDER BY p.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Barang br = new Barang();
                br.setId(rs.getInt("id"));
                br.setKode(rs.getString("kode"));
                br.setNama(rs.getString("nama"));
                br.setHarga(rs.getDouble("harga"));
                br.setKategoriId(rs.getInt("kategori_id"));
                br.setNamaKategori(rs.getString("nama_kategori"));
                br.setDeskripsi(rs.getString("deskripsi"));
                br.setCreatedAt(rs.getString("created_at"));
                list.add(br);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertBarang(Barang b) {
        String sqlProduk = "INSERT INTO produk (kode, nama, harga) VALUES (?, ?, ?)";
        String sqlBarang = "INSERT INTO barang (id, kategori_id, deskripsi) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                PreparedStatement ps1 = conn.prepareStatement(sqlProduk, Statement.RETURN_GENERATED_KEYS);
                ps1.setString(1, b.getKode());
                ps1.setString(2, b.getNama());
                ps1.setDouble(3, b.getHarga());
                ps1.executeUpdate();

                ResultSet keys = ps1.getGeneratedKeys();
                if (keys.next()) {
                    int produkId = keys.getInt(1);
                    PreparedStatement ps2 = conn.prepareStatement(sqlBarang);
                    ps2.setInt(1, produkId);
                    ps2.setInt(2, b.getKategoriId());
                    ps2.setString(3, b.getDeskripsi());
                    ps2.executeUpdate();
                }
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBarang(Barang b) {
        String sqlProduk = "UPDATE produk SET kode=?, nama=?, harga=? WHERE id=?";
        String sqlBarang = "UPDATE barang SET kategori_id=?, deskripsi=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                PreparedStatement ps1 = conn.prepareStatement(sqlProduk);
                ps1.setString(1, b.getKode());
                ps1.setString(2, b.getNama());
                ps1.setDouble(3, b.getHarga());
                ps1.setInt(4, b.getId());
                ps1.executeUpdate();

                PreparedStatement ps2 = conn.prepareStatement(sqlBarang);
                ps2.setInt(1, b.getKategoriId());
                ps2.setString(2, b.getDeskripsi());
                ps2.setInt(3, b.getId());
                ps2.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBarang(int id) {
        String sql = "DELETE FROM produk WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== JASA =====
    public List<Jasa> getAllJasa() {
        List<Jasa> list = new ArrayList<>();
        String sql = "SELECT p.*, j.deskripsi FROM produk p JOIN jasa j ON p.id = j.id ORDER BY p.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Jasa j = new Jasa();
                j.setId(rs.getInt("id"));
                j.setKode(rs.getString("kode"));
                j.setNama(rs.getString("nama"));
                j.setHarga(rs.getDouble("harga"));
                j.setDeskripsi(rs.getString("deskripsi"));
                j.setCreatedAt(rs.getString("created_at"));
                list.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertJasa(Jasa j) {
        String sqlProduk = "INSERT INTO produk (kode, nama, harga) VALUES (?, ?, ?)";
        String sqlJasa = "INSERT INTO jasa (id, deskripsi) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                PreparedStatement ps1 = conn.prepareStatement(sqlProduk, Statement.RETURN_GENERATED_KEYS);
                ps1.setString(1, j.getKode());
                ps1.setString(2, j.getNama());
                ps1.setDouble(3, j.getHarga());
                ps1.executeUpdate();

                ResultSet keys = ps1.getGeneratedKeys();
                if (keys.next()) {
                    int produkId = keys.getInt(1);
                    PreparedStatement ps2 = conn.prepareStatement(sqlJasa);
                    ps2.setInt(1, produkId);
                    ps2.setString(2, j.getDeskripsi());
                    ps2.executeUpdate();
                }
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateJasa(Jasa j) {
        String sqlProduk = "UPDATE produk SET kode=?, nama=?, harga=? WHERE id=?";
        String sqlJasa = "UPDATE jasa SET deskripsi=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                PreparedStatement ps1 = conn.prepareStatement(sqlProduk);
                ps1.setString(1, j.getKode());
                ps1.setString(2, j.getNama());
                ps1.setDouble(3, j.getHarga());
                ps1.setInt(4, j.getId());
                ps1.executeUpdate();

                PreparedStatement ps2 = conn.prepareStatement(sqlJasa);
                ps2.setString(1, j.getDeskripsi());
                ps2.setInt(2, j.getId());
                ps2.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteJasa(int id) {
        String sql = "DELETE FROM produk WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== PRODUK KHUSUS BARANG (untuk transaksi) =====
    public List<Produk> getAllProduk() {
        List<Produk> list = new ArrayList<>();
        String sql = "SELECT p.* FROM produk p " +
                    "JOIN barang b ON p.id = b.id " +
                    "ORDER BY p.kode";

        try (Connection conn = DatabaseConnection.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Produk p = new Produk();
                p.setId(rs.getInt("id"));
                p.setKode(rs.getString("kode"));
                p.setNama(rs.getString("nama"));
                p.setHarga(rs.getDouble("harga"));
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public Produk getById(int id) {
        String sql = "SELECT * FROM produk WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Produk p = new Produk();
                p.setId(rs.getInt("id"));
                p.setKode(rs.getString("kode"));
                p.setNama(rs.getString("nama"));
                p.setHarga(rs.getDouble("harga"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}