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
import com.example.anybooks.UpdateUsersActivity;

import java.util.ArrayList;

public class CustomUsersAdapter extends RecyclerView.Adapter<CustomUsersAdapter.MyViewHolder> {

    private final Context context;
    private ArrayList user_numb, user_id, user_name;

    Animation translate_animation;

    // Constructor
    public CustomUsersAdapter(Context context, ArrayList user_numb, ArrayList user_id, ArrayList user_name) {
        this.context = context;
        this.user_numb = user_numb;
        this.user_id = user_id;
        this.user_name = user_name;
    }

    // ==========================================
    // FUNCIONES PARA TRABAJAR CON RECYCLER VIEW
    // ==========================================
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_user_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Se imprimen los registros de la base de datos en recyclerview usando element_user_row.xml
        holder.user_id_txt.setText(String.valueOf(user_numb.get(position)));
        holder.user_name_txt.setText(String.valueOf(user_name.get(position)));

        // Se captura la información del registro y se manda a la sección de actualizar
        holder.user_main_layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateUsersActivity.class);
            intent.putExtra("id_user", String.valueOf(user_id.get(position)));
            intent.putExtra("user_name", String.valueOf(user_name.get(position)));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return user_id.size();
    }

    // Funcion para identificar los elementos de element_user_row.xml
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user_id_txt, user_name_txt;
        LinearLayout user_main_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_id_txt = itemView.findViewById(R.id.user_id_txt);
            user_name_txt = itemView.findViewById(R.id.user_name_txt);
            user_main_layout = itemView.findViewById(R.id.user_main_layout);

            // Se carga una pequeña animación
            translate_animation = AnimationUtils.loadAnimation(context, R.anim.translate_animation_1);
            user_main_layout.setAnimation(translate_animation);
        }
    }
}
