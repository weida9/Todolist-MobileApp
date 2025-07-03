package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHelper - Kelas untuk mengelola database SQLite
 * Seperti asisten yang mengurus semua operasi database (simpan, ambil, edit, hapus data)
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // ========== KONSTANTA DATABASE ==========
    // Informasi dasar database (nama, versi, tabel)
    private static final String DATABASE_NAME = "todo_database";  // Nama file database
    private static final int DATABASE_VERSION = 2;               // Versi database
    
    // Nama tabel dan kolom-kolom di database
    private static final String TABLE_TODOS = "todos";           // Nama tabel
    private static final String COLUMN_ID = "id";                // Kolom ID (primary key)
    private static final String COLUMN_TITLE = "title";          // Kolom judul tugas
    private static final String COLUMN_COMPLETED = "completed";  // Kolom status selesai
    private static final String COLUMN_CREATED_AT = "created_at"; // Kolom waktu pembuatan

    /**
     * Constructor - Membuat objek DatabaseHelper
     * @param context = konteks aplikasi (biasanya dari Activity)
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Method yang dipanggil saat database pertama kali dibuat
     * Membuat struktur tabel database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL command untuk membuat tabel todos
        String createTable = "CREATE TABLE " + TABLE_TODOS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"  // ID otomatis bertambah
                + COLUMN_TITLE + " TEXT NOT NULL,"                   // Judul wajib diisi
                + COLUMN_COMPLETED + " INTEGER DEFAULT 0,"           // Status default 0 (belum selesai)
                + COLUMN_CREATED_AT + " INTEGER DEFAULT 0"           // Waktu default 0
                + ")";
        db.execSQL(createTable); // Jalankan perintah SQL
    }

    /**
     * Method yang dipanggil saat database perlu diupgrade
     * Hapus tabel lama dan buat yang baru
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS); // Hapus tabel lama
        onCreate(db); // Buat tabel baru
    }

    /**
     * Menambahkan tugas baru ke database
     * @param todo = objek tugas yang akan disimpan
     * @return ID tugas yang baru ditambahkan (atau -1 jika gagal)
     */
    public long addTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase(); // Buka database untuk menulis
        
        // ContentValues = wadah untuk data yang akan disimpan
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, todo.getTitle());                      // Masukkan judul
        values.put(COLUMN_COMPLETED, todo.isCompleted() ? 1 : 0);       // Konversi boolean ke integer
        values.put(COLUMN_CREATED_AT, todo.getCreatedAt());             // Masukkan waktu pembuatan
        
        long id = db.insert(TABLE_TODOS, null, values); // Simpan ke database
        db.close(); // Tutup koneksi database
        return id;  // Kembalikan ID tugas baru
    }

    /**
     * Mengambil semua tugas dari database
     * @return List berisi semua tugas, diurutkan dari terbaru
     */
    public List<Todo> getAllTodos() {
        List<Todo> todoList = new ArrayList<>(); // List kosong untuk menampung hasil
        
        // SQL query untuk ambil semua data, urutkan dari terbaru
        String selectQuery = "SELECT * FROM " + TABLE_TODOS + " ORDER BY " + COLUMN_CREATED_AT + " DESC";
        
        SQLiteDatabase db = this.getReadableDatabase(); // Buka database untuk membaca
        Cursor cursor = db.rawQuery(selectQuery, null);  // Jalankan query, dapat hasil dalam Cursor
        
        // Loop untuk membaca setiap baris data
        if (cursor.moveToFirst()) { // Pindah ke baris pertama
            do {
                // Buat objek Todo baru dan isi dengan data dari database
                Todo todo = new Todo();
                todo.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                todo.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                todo.setCompleted(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COMPLETED)) == 1); // Konversi 1/0 ke true/false
                todo.setCreatedAt(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)));
                
                todoList.add(todo); // Tambahkan ke list
            } while (cursor.moveToNext()); // Lanjut ke baris berikutnya
        }
        
        cursor.close(); // Tutup cursor
        db.close();     // Tutup database
        return todoList; // Kembalikan list semua tugas
    }

    /**
     * Memperbarui data tugas yang sudah ada
     * @param todo = tugas dengan data baru
     * @return jumlah baris yang berhasil diupdate
     */
    public int updateTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase(); // Buka database untuk menulis
        
        // Data baru yang akan diupdate
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, todo.getTitle());                // Judul baru
        values.put(COLUMN_COMPLETED, todo.isCompleted() ? 1 : 0); // Status baru
        
        // Update data di database berdasarkan ID
        // WHERE id = ? (? akan diganti dengan ID tugas)
        int result = db.update(TABLE_TODOS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(todo.getId())});
        
        db.close(); // Tutup database
        return result; // Kembalikan jumlah baris yang terupdate
    }

    /**
     * Menghapus tugas dari database
     * @param id = ID tugas yang akan dihapus
     */
    public void deleteTodo(long id) {
        SQLiteDatabase db = this.getWritableDatabase(); // Buka database untuk menulis
        
        // Hapus baris dengan ID tertentu
        // WHERE id = ? (? akan diganti dengan ID tugas)
        db.delete(TABLE_TODOS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        
        db.close(); // Tutup database
    }
} 