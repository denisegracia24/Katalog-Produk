# Katalog Produk App

Aplikasi desktop berbasis **JavaFX** yang menerapkan pola arsitektur **MVC (Model-View-Controller)** untuk mengelola dan menampilkan data katalog produk secara interaktif.

## Fitur Utama
* **Form Input Produk**: Memungkinkan pengguna untuk menambahkan data produk baru yang mencakup:
  * **Nama Produk** (contoh: *Roti Coklat*, *Kopi Gula Aren*, *Susu Caramel*).
  * **Harga Produk** dalam format Rupiah (Rp).
  * **Pilihan Foto/Gambar Produk** (dropdown opsi aset gambar seperti `roti.png`, dll.).
* **Katalog & Kartu Produk**: Menampilkan daftar produk yang tersimpan dalam bentuk *card view* visual yang dilengkapi dengan gambar, nama, harga, serta ID produk.
* **Manajemen Data (CRUD)**: Tombol **Update** dan **Delete** interaktif pada setiap kartu produk untuk memperbarui atau menghapus data produk dari daftar.
* **Indikator Jumlah Produk**: Menampilkan informasi total produk yang sedang tersimpan secara *real-time* (contoh: *Jumlah produk: 3*).

## Teknologi yang Digunakan
* **Language**: Java
* **UI Framework**: JavaFX (FXML & CSS)
* **Architecture**: Model-View-Controller (MVC)
* **Build Tool**: Maven

## Cara Menjalankan
1. Clone repository ini atau buka folder project menggunakan IDE yang mendukung JavaFX (seperti NetBeans atau IntelliJ IDEA).
2. Pastikan JDK dan konfigurasi Maven sudah terpasang dengan benar.
3. Lakukan **Clean and Build** pada project.
4. Jalankan file utama (`MainApp.java`) untuk mulai menggunakan aplikasi.

## Dokumentasi Tampilan Aplikasi

Berikut adalah beberapa tangkapan layar dari fitur *Katalog Produk App*:

### 1. Tampilan Utama & Form Input
<img width="1006" height="739" alt="Screenshot 2026-11-17 194802" src="https://github.com/user-attachments/assets/a1e3e193-2628-4f0e-bab4-ba6b63522a0b" />

### 2. Fitur Update
<img width="1007" height="737" alt="Screenshot 2026-11-17 194911" src="https://github.com/user-attachments/assets/b9b51668-ceb5-4ad4-ac26-5de5fd5d91df" />

### 2. Fitur Delete
<img width="1005" height="737" alt="Screenshot 2026-11-17 194947" src="https://github.com/user-attachments/assets/0f1ffb8e-ec4e-4590-afc0-af958ed09685" />
