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
import com.example.anybooks.UpdatePapersActivity;

import java.util.ArrayList;

public class CustomPapersAdapter extends RecyclerView.Adapter<CustomPapersAdapter.MyViewHolder> {

    private final Context context;
    private ArrayList paper_numb, paper_id, paper_title, paper_author, paper_year, paper_language;

    Animation translate_animation;

    // Constructor
    public CustomPapersAdapter(Context context, ArrayList paper_numb, ArrayList paper_id, ArrayList paper_title, ArrayList paper_author, ArrayList paper_year, ArrayList paper_language) {
        this.context = context;
        this.paper_numb = paper_numb;
        this.paper_id = paper_id;
        this.paper_title = paper_title;
        this.paper_author = paper_author;
        this.paper_year = paper_year;
        this.paper_language = paper_language;
    }

    // ==========================================
    // FUNCIONES PARA TRABAJAR CON RECYCLER VIEW
    // ==========================================
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_paper_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String full_paper_year = "Año: " + paper_year.get(position);
        String full_paper_langage = "Idioma: " + paper_language.get(position);

        // Se imprimen los registros de la base de datos en recyclerview usando element_paper_row.xml
        holder.paper_id_txt.setText(String.valueOf(paper_numb.get(position)));
        holder.paper_title_txt.setText(String.valueOf(paper_title.get(position)));
        holder.paper_author_txt.setText(String.valueOf(paper_author.get(position)));
        holder.paper_year_txt.setText(full_paper_year);
        holder.paper_language_txt.setText(full_paper_langage);

        // Se captura la información del registro y se manda a la sección de actualizar
        holder.paper_main_layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdatePapersActivity.class);
            intent.putExtra("id_paper", String.valueOf(paper_id.get(position)));
            intent.putExtra("title", String.valueOf(paper_title.get(position)));
            intent.putExtra("author", String.valueOf(paper_author.get(position)));
            intent.putExtra("year", String.valueOf(paper_year.get(position)));
            intent.putExtra("language", String.valueOf(paper_language.get(position)));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return paper_id.size();
    }

    // Funcion para identificar los elementos de element_paper_row.xml
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView paper_id_txt, paper_title_txt, paper_author_txt, paper_year_txt, paper_language_txt;
        LinearLayout paper_main_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            paper_id_txt = itemView.findViewById(R.id.paper_id_txt);
            paper_title_txt = itemView.findViewById(R.id.paper_title_txt);
            paper_author_txt = itemView.findViewById(R.id.paper_author_txt);
            paper_year_txt = itemView.findViewById(R.id.paper_year_txt);
            paper_language_txt = itemView.findViewById(R.id.paper_language_txt);
            paper_main_layout = itemView.findViewById(R.id.paper_main_layout);

            // Se carga una pequeña animación
            translate_animation = AnimationUtils.loadAnimation(context, R.anim.translate_animation_1);
            paper_main_layout.setAnimation(translate_animation);
        }
    }
}
