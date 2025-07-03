package com.example.todolist;

/**
 * Kelas Todo - Model data untuk menyimpan informasi tugas
 * Seperti blueprint atau cetakan untuk membuat objek tugas
 */
public class Todo {
    // Variable untuk menyimpan data tugas
    private long id;           // ID unik setiap tugas (seperti nomor urut)
    private String title;      // Judul atau isi tugas
    private boolean completed; // Status: true = selesai, false = belum selesai
    private long createdAt;    // Waktu kapan tugas dibuat (dalam format timestamp)

    /**
     * Constructor kosong - untuk membuat tugas baru tanpa parameter
     * Otomatis mengatur waktu pembuatan dan status belum selesai
     */
    public Todo() {
        this.createdAt = System.currentTimeMillis(); // Ambil waktu sekarang
        this.completed = false;                       // Set status belum selesai
    }

    /**
     * Constructor dengan judul - untuk membuat tugas baru dengan judul
     * @param title = judul tugas yang akan dibuat
     */
    public Todo(String title) {
        this(); // Panggil constructor kosong dulu
        this.title = title; // Kemudian set judulnya
    }

    /**
     * Constructor lengkap - untuk membuat tugas dengan semua data
     * Biasanya dipakai saat mengambil data dari database
     */
    public Todo(long id, String title, boolean completed, long createdAt) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    // ========== GETTER DAN SETTER ==========
    // Fungsi untuk mengambil dan mengubah data (karena variable private)
    
    /**
     * Mengambil ID tugas
     */
    public long getId() {
        return id;
    }

    /**
     * Mengubah ID tugas
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Mengambil judul tugas
     */
    public String getTitle() {
        return title;
    }

    /**
     * Mengubah judul tugas
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Mengecek apakah tugas sudah selesai
     * @return true jika selesai, false jika belum
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Mengubah status tugas (selesai/belum selesai)
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Mengambil waktu pembuatan tugas
     */
    public long getCreatedAt() {
        return createdAt;
    }

    /**
     * Mengubah waktu pembuatan tugas
     */
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
} 