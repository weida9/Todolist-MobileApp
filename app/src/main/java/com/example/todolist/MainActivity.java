package com.example.todolist;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity - Activity utama aplikasi TodoList
 * Seperti otak aplikasi yang mengatur semua interaksi user dan tampilan
 * Mengimplementasi interface OnTodoClickListener untuk menangani event dari adapter
 */
public class MainActivity extends AppCompatActivity implements TodoAdapter.OnTodoClickListener {
    
    // ========== KOMPONEN UI ==========
    private EditText editTextTask;    // Input field untuk mengetik tugas baru
    private ImageButton buttonAdd;    // Tombol untuk menambah tugas
    private RecyclerView recyclerView; // List untuk menampilkan semua tugas
    private LinearLayout layoutEmpty; // Layout yang muncul saat belum ada tugas
    
    // ========== KOMPONEN DATA ==========
    private DatabaseHelper databaseHelper; // Helper untuk operasi database
    private TodoAdapter adapter;           // Adapter untuk RecyclerView
    private List<Todo> todoList;           // List berisi semua data tugas

    /**
     * Method yang dipanggil saat Activity pertama kali dibuat
     * Seperti setup awal saat aplikasi baru dibuka
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable tampilan edge-to-edge (modern Android)
        setContentView(R.layout.activity_main); // Set layout XML sebagai tampilan
        
        // Setup window insets untuk tampilan yang rapi di semua device
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        // ========== PROSES INISIALISASI ==========
        initViews();           // 1. Inisialisasi semua view component
        setupDatabase();       // 2. Setup koneksi database
        setupRecyclerView();   // 3. Setup RecyclerView dan adapter
        setupClickListeners(); // 4. Setup event listener untuk tombol
        loadTodos();          // 5. Load data dari database dan tampilkan
    }
    
    /**
     * Menginisialisasi semua view component
     * Mencari dan menyimpan referensi view berdasarkan ID
     */
    private void initViews() {
        editTextTask = findViewById(R.id.editTextTask);   // Input field
        buttonAdd = findViewById(R.id.buttonAdd);         // Tombol tambah
        recyclerView = findViewById(R.id.recyclerView);   // List tugas
        layoutEmpty = findViewById(R.id.layoutEmpty);     // Empty state
    }
    
    /**
     * Setup koneksi database
     * Membuat instance DatabaseHelper untuk operasi database
     */
    private void setupDatabase() {
        databaseHelper = new DatabaseHelper(this);
    }
    
    /**
     * Setup RecyclerView dan adapter
     * Mengatur bagaimana list tugas akan ditampilkan
     */
    private void setupRecyclerView() {
        todoList = new ArrayList<>();                           // Buat list kosong
        adapter = new TodoAdapter(todoList, this);              // Buat adapter dengan listener = this
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set layout manager (vertical list)
        recyclerView.setAdapter(adapter);                       // Pasang adapter ke RecyclerView
    }
    
    /**
     * Setup event listener untuk tombol
     * Mengatur apa yang terjadi saat tombol diklik
     */
    private void setupClickListeners() {
        // Lambda expression: saat tombol add diklik, panggil method addTodo()
        buttonAdd.setOnClickListener(v -> addTodo());
    }
    
    /**
     * Menambahkan tugas baru
     * Dipanggil saat user klik tombol tambah
     */
    private void addTodo() {
        // Ambil text dari input field dan hapus spasi di awal/akhir
        String taskTitle = editTextTask.getText().toString().trim();
        
        // ========== VALIDASI INPUT ==========
        if (taskTitle.isEmpty()) {
            // Jika input kosong, tampilkan pesan error
            Toast.makeText(this, "Masukkan tugas terlebih dahulu", Toast.LENGTH_SHORT).show();
            return; // Keluar dari method, tidak lanjut eksekusi
        }
        
        // ========== PROSES PENYIMPANAN ==========
        Todo todo = new Todo(taskTitle);           // Buat objek Todo baru
        long id = databaseHelper.addTodo(todo);    // Simpan ke database, dapat ID
        
        if (id > 0) {
            // Jika berhasil disimpan (ID > 0)
            todo.setId(id);                        // Set ID ke objek Todo
            editTextTask.setText("");              // Kosongkan input field
            loadTodos();                           // Refresh tampilan list
            Toast.makeText(this, "Tugas berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        } else {
            // Jika gagal disimpan (ID = -1)
            Toast.makeText(this, "Gagal menambahkan tugas", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * Load semua tugas dari database dan tampilkan di RecyclerView
     * Dipanggil saat pertama kali buka app atau setelah ada perubahan data
     */
    private void loadTodos() {
        todoList = databaseHelper.getAllTodos(); // Ambil semua data dari database
        adapter.updateTodos(todoList);           // Update adapter dengan data baru
        
        // ========== MANAJEMEN TAMPILAN EMPTY STATE ==========
        if (todoList.isEmpty()) {
            // Jika belum ada tugas, tampilkan empty state
            layoutEmpty.setVisibility(View.VISIBLE);   // Tampilkan layout empty
            recyclerView.setVisibility(View.GONE);     // Sembunyikan RecyclerView
        } else {
            // Jika ada tugas, tampilkan list
            layoutEmpty.setVisibility(View.GONE);      // Sembunyikan layout empty
            recyclerView.setVisibility(View.VISIBLE);  // Tampilkan RecyclerView
        }
    }

    // ========== IMPLEMENTASI INTERFACE OnTodoClickListener ==========
    // Method-method ini dipanggil dari adapter saat user berinteraksi dengan item

    /**
     * Dipanggil saat user klik checkbox pada item tugas
     * @param todo = tugas yang di-check/uncheck
     */
    @Override
    public void onTodoChecked(Todo todo) {
        databaseHelper.updateTodo(todo); // Simpan perubahan status ke database
        
        // Tampilkan pesan sesuai status
        String message = todo.isCompleted() ? "Tugas selesai!" : "Tugas belum selesai";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Dipanggil saat user klik tombol delete pada item tugas
     * @param todo = tugas yang akan dihapus
     */
    @Override
    public void onTodoDelete(Todo todo) {
        // Tampilkan dialog konfirmasi sebelum hapus
        new AlertDialog.Builder(this)
                .setTitle("Hapus Tugas")                                    // Judul dialog
                .setMessage(getString(R.string.confirm_delete))             // Pesan konfirmasi
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    // Lambda: jika user klik "Ya"
                    databaseHelper.deleteTodo(todo.getId());                // Hapus dari database
                    loadTodos();                                            // Refresh tampilan
                    Toast.makeText(this, "Tugas berhasil dihapus", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(getString(R.string.no), null)            // Jika "Tidak", tidak ada aksi
                .show();                                                    // Tampilkan dialog
    }

    /**
     * Dipanggil saat user klik tombol edit pada item tugas
     * @param todo = tugas yang akan diedit
     */
    @Override
    public void onTodoEdit(Todo todo) {
        showEditDialog(todo); // Tampilkan dialog edit
    }
    
    /**
     * Menampilkan dialog untuk edit tugas
     * @param todo = tugas yang akan diedit
     */
    private void showEditDialog(Todo todo) {
        // ========== SETUP DIALOG ==========
        // Inflate layout dialog dari XML
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_task, null);
        EditText editTextTask = dialogView.findViewById(R.id.editTextTask); // Input field di dialog
        
        // ========== PRE-FILL DATA ==========
        editTextTask.setText(todo.getTitle());                              // Isi dengan judul sekarang
        editTextTask.setSelection(editTextTask.getText().length());         // Taruh cursor di akhir text
        
        // Buat dan tampilkan dialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)    // Set custom view
                .create();              // Buat dialog
        
        // ========== SETUP BUTTON LISTENERS ==========
        // Tombol Cancel - tutup dialog tanpa simpan
        dialogView.findViewById(R.id.buttonCancel).setOnClickListener(v -> dialog.dismiss());
        
        // Tombol Save - simpan perubahan
        dialogView.findViewById(R.id.buttonSave).setOnClickListener(v -> {
            String newTitle = editTextTask.getText().toString().trim(); // Ambil judul baru
            
            // ========== VALIDASI INPUT ==========
            if (newTitle.isEmpty()) {
                Toast.makeText(this, "Tugas tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return; // Jangan tutup dialog jika input kosong
            }
            
            // ========== PROSES UPDATE ==========
            if (!newTitle.equals(todo.getTitle())) {
                // Jika judul berubah, update ke database
                todo.setTitle(newTitle);                // Update objek
                databaseHelper.updateTodo(todo);        // Simpan ke database
                loadTodos();                            // Refresh tampilan
                Toast.makeText(this, "Tugas berhasil diperbarui", Toast.LENGTH_SHORT).show();
            }
            
            dialog.dismiss(); // Tutup dialog
        });
        
        dialog.show(); // Tampilkan dialog
    }
}