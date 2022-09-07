package com.example.anybooks.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anybooks.R;
import com.example.anybooks.UpdateBooksActivity;

import java.util.ArrayList;

public class CustomBooksAdapter extends RecyclerView.Adapter<CustomBooksAdapter.MyViewHolder> {

    private final Context context;
    private ArrayList book_numb, book_id, book_title, book_author, book_year, book_language, book_editorial, book_pages;

    Animation translate_animation;

    // Constructor
    public CustomBooksAdapter(Context context, ArrayList book_numb, ArrayList book_id, ArrayList book_title, ArrayList book_author, ArrayList book_year, ArrayList book_language, ArrayList book_editorial, ArrayList book_pages) {
        this.context = context;
        this.book_numb = book_numb;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_year = book_year;
        this.book_language = book_language;
        this.book_editorial = book_editorial;
        this.book_pages = book_pages;
    }

    // ==========================================
    // FUNCIONES PARA TRABAJAR CON RECYCLER VIEW
    // ==========================================
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_book_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String full_book_year = "Año: " + book_year.get(position);
        String full_book_langage = "Idioma: " + book_language.get(position);

        // Se imprimen los registros de la base de datos en recyclerview usando element_book_row.xml
        holder.book_id_txt.setText(String.valueOf(book_numb.get(position)));
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_year_txt.setText(full_book_year);
        holder.book_language_txt.setText(full_book_langage);
        holder.book_editorial_txt.setText(String.valueOf(book_editorial.get(position)));

        // Se captura la información del registro y se manda a la sección de actualizar
        holder.book_main_layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateBooksActivity.class);
            intent.putExtra("id_book", String.valueOf(book_id.get(position)));
            intent.putExtra("title", String.valueOf(book_title.get(position)));
            intent.putExtra("author", String.valueOf(book_author.get(position)));
            intent.putExtra("year", String.valueOf(book_year.get(position)));
            intent.putExtra("language", String.valueOf(book_language.get(position)));
            intent.putExtra("editorial", String.valueOf(book_editorial.get(position)));
            intent.putExtra("pages", String.valueOf(book_pages.get(position)));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    // Funcion para identificar los elementos de element_book_row.xml
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView book_id_txt, book_title_txt, book_author_txt, book_year_txt, book_language_txt, book_editorial_txt;
        LinearLayout book_main_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.book_id_txt);
            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            book_author_txt = itemView.findViewById(R.id.book_author_txt);
            book_year_txt = itemView.findViewById(R.id.book_year_txt);
            book_language_txt = itemView.findViewById(R.id.book_language_txt);
            book_editorial_txt = itemView.findViewById(R.id.book_editorial_txt);
            book_main_layout = itemView.findViewById(R.id.book_main_layout);

            // Se carga una pequeña animación
            translate_animation = AnimationUtils.loadAnimation(context, R.anim.translate_animation_1);
            book_main_layout.setAnimation(translate_animation);
        }
    }
}
