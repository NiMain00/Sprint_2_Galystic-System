-- Jalankan di phpMyAdmin / MySQL

CREATE DATABASE IF NOT EXISTS bengkel_lathifah;
USE bengkel_lathifah;

CREATE TABLE pengguna (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE produk (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kode VARCHAR(50) NOT NULL UNIQUE,
    nama VARCHAR(100) NOT NULL,
    harga DECIMAL(12,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE kategori_barang (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_kategori VARCHAR(100) NOT NULL
);

CREATE TABLE barang (
    id INT PRIMARY KEY,
    kategori_id INT NOT NULL,
    deskripsi TEXT,
    FOREIGN KEY (id) REFERENCES produk(id) ON DELETE CASCADE,
    FOREIGN KEY (kategori_id) REFERENCES kategori_barang(id)
);

CREATE TABLE jasa (
    id INT PRIMARY KEY,
    deskripsi TEXT,
    FOREIGN KEY (id) REFERENCES produk(id) ON DELETE CASCADE
);

CREATE TABLE pelanggan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    no_telp VARCHAR(20),
    alamat TEXT,
    no_plat VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transaksi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    no_transaksi VARCHAR(50) NOT NULL UNIQUE,
    tanggal DATE NOT NULL,
    tipe_transaksi VARCHAR(20) NOT NULL,
    total_harga DECIMAL(14,2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    pelanggan_id INT,
    pengguna_id INT,
    FOREIGN KEY (pelanggan_id) REFERENCES pelanggan(id),
    FOREIGN KEY (pengguna_id) REFERENCES pengguna(id)
);

CREATE TABLE detail_transaksi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    transaksi_id INT NOT NULL,
    produk_id INT NOT NULL,
    qty INT NOT NULL,
    harga_satuan DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(14,2) NOT NULL,
    FOREIGN KEY (transaksi_id) REFERENCES transaksi(id) ON DELETE CASCADE,
    FOREIGN KEY (produk_id) REFERENCES produk(id)
);

CREATE TABLE laporan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    periode_awal DATE NOT NULL,
    periode_akhir DATE NOT NULL,
    jenis_laporan VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE statistik (
    id INT AUTO_INCREMENT PRIMARY KEY,
    jenis_statistik VARCHAR(50),
    periode_awal DATE,
    periode_akhir DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert default admin
INSERT INTO pengguna (username, password, nama_lengkap, role)
VALUES ('admin', 'admin123', 'Marwan Aidit', 'Admin');

-- Insert sample kategori
INSERT INTO kategori_barang (nama_kategori) VALUES
('Oli'), ('Filter'), ('Rem'), ('Ban'), ('Aki'), ('Sparepart Lainnya');