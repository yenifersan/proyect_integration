package com.santiago.proyect_integration.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santiago.proyect_integration.Models.Publicaciones;
import com.santiago.proyect_integration.R;

import java.util.ArrayList;
import java.util.List;

public class PublicacionesAdapters extends RecyclerView.Adapter<PublicacionesAdapters.ViewHolder> {

    private List<Publicaciones> publicacionesList;
    private Activity activity;

    public PublicacionesAdapters() {
        this.publicacionesList = new ArrayList<>();
    }

    public void setPostsList(List<Publicaciones> postsList) {
        this.publicacionesList = postsList;
    }

    @NonNull
    @Override
    public PublicacionesAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_publicacion, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionesAdapters.ViewHolder viewHolder, int i) {
        Publicaciones publicacion=publicacionesList.get(i);
        viewHolder.dateText.setText(publicacion.getFecha());
        viewHolder.descriptionText.setText(publicacion.getDescripcion());
        viewHolder.titleText.setText(publicacion.getTitulo());
        viewHolder.estadoText.setText(publicacion.getEstado());
        viewHolder.horario.setText(publicacion.getHorario().getHoraInicio()+" - "+publicacion.getHorario().getHoraFin());
        viewHolder.curso.setText(publicacion.getCurso().getNombre());
    }

    @Override
    public int getItemCount() {
        return publicacionesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dateText;
        private TextView descriptionText;
        private TextView titleText;
        private TextView estadoText;
        private TextView horario;
        private TextView curso;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateText = itemView.findViewById(R.id.date_card);
            descriptionText = itemView.findViewById(R.id.motivo_card);
            titleText = itemView.findViewById(R.id.usuario_card);
            estadoText = itemView.findViewById(R.id.estado_card);
            horario = itemView.findViewById(R.id.horario_card);
            curso = itemView.findViewById(R.id.curso_card);
        }
    }
}
