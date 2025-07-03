package com.example.todolist;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * TodoAdapter - Adapter untuk RecyclerView dengan TextView-based checkbox
 * Menggunakan TextView untuk checkbox yang 100% dijamin berfungsi
 * Seperti jembatan yang menghubungkan data (List<Todo>) dengan tampilan (RecyclerView)
 * Bertanggung jawab untuk mengubah data menjadi tampilan yang bisa dilihat user
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<Todo> todoList; // List berisi semua data tugas
    private OnTodoClickListener listener; // Interface untuk menangani klik user

    /**
     * Interface untuk menangani event klik dari user
     * Seperti kontrak yang harus diimplementasi oleh Activity
     */
    public interface OnTodoClickListener {
        void onTodoChecked(Todo todo);  // Ketika checkbox diklik
        void onTodoDelete(Todo todo);   // Ketika tombol hapus diklik
        void onTodoEdit(Todo todo);     // Ketika tombol edit diklik
    }

    /**
     * Constructor untuk membuat adapter
     * @param todoList = data tugas yang akan ditampilkan
     * @param listener = objek yang akan menangani event klik
     */
    public TodoAdapter(List<Todo> todoList, OnTodoClickListener listener) {
        this.todoList = todoList;
        this.listener = listener;
    }

    /**
     * Membuat ViewHolder baru ketika RecyclerView membutuhkannya
     * Seperti membuat cetakan kosong untuk menampilkan data
     */
    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_todo.xml menjadi View object
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view); // Bungkus View dalam ViewHolder
    }

    /**
     * Mengisi ViewHolder dengan data pada posisi tertentu
     * Seperti mengisi cetakan dengan data aktual
     */
    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.textTitle.setText(todo.getTitle());
        
        // Setup TextView-based checkbox
        updateCheckboxState(holder, todo);
        
        // Checkbox click listener
        holder.checkBox.setOnClickListener(v -> {
            todo.setCompleted(!todo.isCompleted());
            updateCheckboxState(holder, todo);
            if (listener != null) {
                listener.onTodoChecked(todo);
            }
        });

        // Edit button
        holder.buttonEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTodoEdit(todo);
            }
        });

        // Delete button
        holder.buttonDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTodoDelete(todo);
            }
        });
    }

    private void updateCheckboxState(TodoViewHolder holder, Todo todo) {
        holder.checkBox.setSelected(todo.isCompleted());
        
        if (todo.isCompleted()) {
            // Show checkmark and apply strikethrough
            holder.checkBox.setText("✓");
            holder.checkBox.setTextColor(holder.itemView.getContext().getColor(R.color.white));
            holder.textTitle.setPaintFlags(holder.textTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textTitle.setAlpha(0.5f);
        } else {
            // Hide checkmark and remove strikethrough
            holder.checkBox.setText("");
            holder.textTitle.setPaintFlags(holder.textTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textTitle.setAlpha(1.0f);
        }
    }

    /**
     * Mengembalikan jumlah total item dalam list
     * RecyclerView butuh info ini untuk tahu berapa item yang harus ditampilkan
     */
    @Override
    public int getItemCount() {
        return todoList.size();
    }

    /**
     * Update data adapter dengan list baru
     * Dipanggil ketika data berubah (ada penambahan, edit, atau hapus)
     */
    public void updateTodos(List<Todo> newTodoList) {
        this.todoList = newTodoList;        // Ganti dengan data baru
        notifyDataSetChanged();             // Beritahu RecyclerView untuk refresh tampilan
    }

    /**
     * ViewHolder - Kelas untuk menyimpan referensi view dalam satu item
     * Seperti wadah yang menyimpan semua komponen UI dalam satu item todo
     * Static class untuk menghemat memory
     */
    static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView checkBox;          // TextView acting as checkbox
        TextView textTitle;         // TextView untuk menampilkan judul tugas
        ImageButton buttonEdit;     // Tombol untuk edit tugas
        ImageButton buttonDelete;   // Tombol untuk hapus tugas

        /**
         * Constructor ViewHolder
         * Mencari dan menyimpan referensi semua view yang ada di item layout
         */
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Cari dan simpan referensi view berdasarkan ID
            checkBox = itemView.findViewById(R.id.checkBox);
            textTitle = itemView.findViewById(R.id.textTitle);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
} 