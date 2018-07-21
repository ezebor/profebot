package ar.com.profebot.service;
import android.app.LauncherActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.profebot.activities.R;

import java.util.List;

import ar.com.profebot.Models.PendingExercise;

public class PendingExerciseAdapter extends RecyclerView.Adapter<PendingExerciseAdapter.ViewHolder>{

    private List<PendingExercise> listExercise;
    private Context context;

    public PendingExerciseAdapter(List<PendingExercise> pendingExercises, Context context){
        this.listExercise = pendingExercises;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pending_exercise,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PendingExercise listPendingExercises = listExercise.get(position);
        holder.textViewHead.setText(listPendingExercises.getHead());
        holder.textViewEquation.setText(listPendingExercises.getDesc());
    }

    @Override
    public int getItemCount() {
        return listExercise.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHead;
        public TextView textViewEquation;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewEquation = (TextView) itemView.findViewById(R.id.textViewEquation);
        }
    }
}
