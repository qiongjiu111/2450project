package com.example.a2450project.ui;

import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2450project.R;
import com.example.a2450project.model.CrewMember;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.ViewHolder> {

    private final List<CrewMember> crewList;
    private final Set<CrewMember> selected;

    public CrewAdapter(List<CrewMember> crewList) {
        this.crewList = crewList;
        this.selected = new LinkedHashSet<>();
    }

    public List<CrewMember> getSelected() {
        return new ArrayList<>(selected);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_crew_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CrewMember crew = crewList.get(position);
        String type = crew.getClass().getSimpleName();

        holder.title.setText(crew.getName() + " (" + type + ")");
        holder.hpText.setText("HP " + crew.getEnergy() + "/" + crew.getMaxEnergy());
        holder.stats.setText(
                "ATK " + crew.getSkill()
                        + " DEF " + crew.getResilience()
                        + " XP " + crew.getExperience()
        );

        holder.hpBar.setMax(crew.getMaxEnergy());
        holder.hpBar.setProgress(crew.getEnergy());

        int color = getColor(type);
        holder.title.setBackgroundColor(color);
        holder.hpBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        holder.image.setImageResource(getImage(type));

        if (selected.contains(crew)) { //select

            GradientDrawable border = new GradientDrawable();

            holder.itemView.setBackground(border);

            holder.itemView.setScaleX(0.95f);
            holder.itemView.setScaleY(0.95f);
            holder.itemView.setElevation(2f);

        } else {

            GradientDrawable normal = new GradientDrawable();
            normal.setColor(0xFFFFFFFF);
            normal.setStroke(2, 0xFFCCCCCC);

            holder.itemView.setBackground(normal);

            holder.itemView.setScaleX(1f);
            holder.itemView.setScaleY(1f);
            holder.itemView.setElevation(8f);
        }

        holder.itemView.setOnClickListener(v -> {  //show
            if (selected.contains(crew)) {
                selected.remove(crew);
            } else {
                selected.add(crew);
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    private int getImage(String type) {
        switch (type) {
            case "Engineer":
                return R.drawable.engineer;
            case "Medic":
                return R.drawable.medic;
            case "Pilot":
                return R.drawable.pilot;
            case "Scientist":
                return R.drawable.scientist;
            case "Soldier":
                return R.drawable.soldier;
            default:
                return R.drawable.threat;
        }
    }

    private int getColor(String type) {
        switch (type) {
            case "Pilot":
                return 0xFF2196F3;
            case "Engineer":
                return 0xFFFFEB3B;
            case "Medic":
                return 0xFF4CAF50;
            case "Scientist":
                return 0xFF9C27B0;
            case "Soldier":
                return 0xFFF44336;
            default:
                return 0xFF333333;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView hpText;
        TextView stats;
        ProgressBar hpBar;
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.cardName);
            hpText = view.findViewById(R.id.hpText);
            stats = view.findViewById(R.id.statsText);
            hpBar = view.findViewById(R.id.hpBar);
            image = view.findViewById(R.id.cardImage);
        }
    }
}