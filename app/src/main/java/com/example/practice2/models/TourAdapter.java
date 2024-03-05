package com.example.practice2.models;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice2.R;

import java.util.ArrayList;
import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> {
    private Context context;
    private List<Tour> listTours, backupList;
    private TourListener tourListener;

    public TourAdapter(Context context) {
        this.context = context;
        listTours = new ArrayList<>();
        backupList = new ArrayList<>();
    }

    public void setTourListener(TourListener listener) {
        this.tourListener=listener;
    }

    public void setFilter(List<Tour> listFilter) {
        listTours = listFilter;
        notifyDataSetChanged();
    }

    public List<Tour> getBackupList() {
        return backupList;
    }

    public void addTour(Tour tour) {
        backupList.add(tour);
        listTours.add(tour);
        notifyDataSetChanged();
    }

    public void update(int curPosition,Tour tour) {
        backupList.set(curPosition,tour);
        listTours.set(curPosition,tour);
        notifyDataSetChanged();
    }

    public void removeTour(int position) {
        backupList.remove(position);
        listTours.remove(position);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        Tour tour = listTours.get(position);
        if(tour == null) {
            return;
        }
        holder.imageView.setImageResource(tour.imgResource);
        holder.textView.setText(tour.getDescription());

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc muốn xóa Tour "+tour.name);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeTour(holder.getAdapterPosition());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng hộp thoại mà không làm gì cả
                        dialog.cancel();
                    }
                });
                AlertDialog dialog =  builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTours.size();
    }

    public Tour getItem(int position) {
        return listTours.get(position);
    }
    public class TourViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView textView;
        private Button btnRemove;
        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgTour);
            textView = itemView.findViewById(R.id.textTour);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(tourListener != null) {
                tourListener.onItemClick(view,getAdapterPosition());
            }
        }
    }

    public interface TourListener {
        void onItemClick(View view, int position);
    }
}
