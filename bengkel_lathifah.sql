-- SQLite Schema for Bengkel Lathifah
-- Run: sqlite3 bengkel_lathifah.db < bengkel_lathifah.sql

CREATE TABLE IF NOT EXISTS pengguna (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TEXT DEFAULT (datetime('now','localtime'))
);

CREATE TABLE IF NOT EXISTS produk (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    kode VARCHAR(50) NOT NULL UNIQUE,
    nama VARCHAR(100) NOT NULL,
    harga DECIMAL(12,2) NOT NULL,
    created_at TEXT DEFAULT (datetime('now','localtime'))
);

CREATE TABLE IF NOT EXISTS kategori_barang (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nama_kategori VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS barang (
    id INTEGER PRIMARY KEY,
    kategori_id INTEGER NOT NULL,
    deskripsi TEXT,
    harga_beli DECIMAL(12,2),
    FOREIGN KEY (id) REFERENCES produk(id) ON DELETE CASCADE,
    FOREIGN KEY (kategori_id) REFERENCES kategori_barang(id)
);

CREATE TABLE IF NOT EXISTS jasa (
    id INTEGER PRIMARY KEY,
    deskripsi TEXT,
    FOREIGN KEY (id) REFERENCES produk(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS pelanggan (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nama VARCHAR(100) NOT NULL,
    no_telp VARCHAR(20),
    alamat TEXT,
    no_plat VARCHAR(20),
    created_at TEXT DEFAULT (datetime('now','localtime'))
);

CREATE TABLE IF NOT EXISTS transaksi (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    no_transaksi VARCHAR(50) NOT NULL UNIQUE,
    tanggal TEXT NOT NULL,  -- 'YYYY-MM-DD'
    tipe_transaksi VARCHAR(20) NOT NULL,
    total_harga DECIMAL(14,2) DEFAULT 0,
    created_at TEXT DEFAULT (datetime('now','localtime')),
    pelanggan_id INTEGER,
    pengguna_id INTEGER,
    FOREIGN KEY (pelanggan_id) REFERENCES pelanggan(id),
    FOREIGN KEY (pengguna_id) REFERENCES pengguna(id)
);

CREATE TABLE IF NOT EXISTS detail_transaksi (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    transaksi_id INTEGER NOT NULL,
    produk_id INTEGER NOT NULL,
    qty INTEGER NOT NULL,
    harga_satuan DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(14,2) NOT NULL,
    FOREIGN KEY (transaksi_id) REFERENCES transaksi(id) ON DELETE CASCADE,
    FOREIGN KEY (produk_id) REFERENCES produk(id)
);

CREATE TABLE IF NOT EXISTS laporan (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    periode_awal TEXT NOT NULL,
    periode_akhir TEXT NOT NULL,
    jenis_laporan VARCHAR(50),
    created_at TEXT DEFAULT (datetime('now','localtime'))
);

CREATE TABLE IF NOT EXISTS statistik (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    jenis_statistik VARCHAR(50),
    periode_awal TEXT,
    periode_akhir TEXT,
    created_at TEXT DEFAULT (datetime('now','localtime'))
);

-- Indexes for performance
CREATE INDEX IF NOT EXISTS idx_transaksi_tanggal ON transaksi(tanggal);
CREATE INDEX IF NOT EXISTS idx_detail_transaksi_transaksi ON detail_transaksi(transaksi_id);

-- Insert default admin
INSERT OR IGNORE INTO pengguna (username, password, nama_lengkap, role)
VALUES ('admin', 'admin123', 'Marwan Aidit', 'Admin');

-- Insert sample kategori
INSERT OR IGNORE INTO kategori_barang (nama_kategori) VALUES
('Oli'), ('Filter'), ('Rem'), ('Ban'), ('Aki'), ('Sparepart Lainnya');
