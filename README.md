# App Bengkel Lathifah  
Aplikasi Manajemen Bengkel berbasis Java Desktop untuk membantu pengelolaan data bengkel seperti pengguna, layanan, dan database secara terintegrasi.

---

# Sprint 2 - Implementasi Koneksi Database & Login

Pada Sprint 2, aplikasi telah mengimplementasikan:

- Koneksi database menggunakan JDBC dan SQLite
- Sistem login pengguna
- Pengujian koneksi database
- Integrasi autentikasi dengan database

---

# Fitur yang Diimplementasikan

## 1. Koneksi Database (JDBC)

Aplikasi menggunakan JDBC untuk menghubungkan aplikasi Java dengan database SQLite.

### File Terkait
```bash
src/config/DatabaseConnection.java
```

### Konfigurasi Database
```java
jdbc:sqlite:bengkel_lathifah.db
```

### Fitur Tambahan
- Foreign key diaktifkan menggunakan:
```sql
PRAGMA foreign_keys = ON;
```

---

## 2. Sistem Login Pengguna

Fitur login digunakan untuk autentikasi pengguna sebelum masuk ke dashboard aplikasi.

### File Terkait
#### Tampilan Login
```bash
src/gui/LoginFrame.java
```

#### Proses Autentikasi
```bash
src/dao/PenggunaDAO.java
```

### Query Login
```sql
SELECT * FROM pengguna 
WHERE username = ? AND password = ?
```

### Alur Login
1. Pengguna memasukkan username dan password
2. Sistem memeriksa data pada tabel `pengguna`
3. Jika data valid:
   - Login berhasil
   - Aplikasi membuka `DashboardFrame`
4. Jika gagal:
   - Sistem menampilkan pesan error login

---

# Struktur Database

File database dan SQL yang digunakan:

```bash
bengkel_lathifah.db
Database.sql
bengkel_lathifah.sql
```

Keterangan:
- `bengkel_lathifah.db` → database SQLite utama
- `Database.sql` → script pembuatan database
- `bengkel_lathifah.sql` → script tambahan/alternatif database

---

# Cara Menjalankan Aplikasi

## Persyaratan
Pastikan:
- Java JDK sudah terinstall
- SQLite JDBC Driver tersedia
- File database berada di root project

---

## Menjalankan via IDE (Disarankan)

1. Buka project pada IDE:
   - NetBeans
   - IntelliJ IDEA
   - Eclipse

2. Jalankan file:
```bash
src/Main.java
```

3. Aplikasi akan membuka halaman login terlebih dahulu.

---

## Menjalankan via Terminal

Masuk ke direktori root project:

```bash
cd App-Bengkel-Lathifah
```

Compile program:

```bash
javac -cp ".;lib/sqlite-jdbc.jar" src/Main.java
```

Jalankan program:

```bash
java -cp ".;lib/sqlite-jdbc.jar" src.Main
```

---

# Informasi Login

Gunakan username dan password yang tersedia pada tabel:

```sql
pengguna
```

Jika data pengguna belum tersedia:
- Import database menggunakan:
```bash
Database.sql
```

atau

```bash
bengkel_lathifah.sql
```

---

# Pengujian Koneksi Database

Jika aplikasi gagal login atau tidak dapat mengakses data, lakukan pengecekan berikut:

## Checklist
- Pastikan file:
```bash
bengkel_lathifah.db
```
berada di root project.

- Pastikan tabel:
```sql
pengguna
```
sudah tersedia pada database.

- Pastikan struktur tabel sesuai dengan query pada:
```bash
src/dao/PenggunaDAO.java
```

- Periksa error pada terminal atau console aplikasi.

---

# Teknologi yang Digunakan

- Java
- Java Swing
- JDBC
- SQLite

---

# Struktur Folder Utama

```bash
src/
├── config/
├── dao/
├── gui/
├── model/
└── Main.java
```

---

# Author

App Bengkel Lathifah - Sprint 2