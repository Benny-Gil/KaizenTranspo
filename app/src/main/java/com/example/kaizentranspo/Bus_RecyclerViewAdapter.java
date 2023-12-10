package com.example.kaizentranspo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Bus_RecyclerViewAdapter extends RecyclerView.Adapter<Bus_RecyclerViewAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<BusList> bus = new ArrayList<>();

    public Bus_RecyclerViewAdapter(Context context, ArrayList<BusList> bus, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.bus = bus;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Bus_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bus_recyclerview, parent, false);
        return new Bus_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Bus_RecyclerViewAdapter.MyViewHolder holder,
                                 int position) {
        holder.destination.setText(bus.get(position).getDestination());
        holder.price.setText(bus.get(position).getPrice());
        holder.busNumber.setText(bus.get(position).getBusNumber());
        holder.departureTime.setText(bus.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return bus.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView destination, price, departureTime, busNumber;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            destination = itemView.findViewById(R.id.destination_recycleView);
            price = itemView.findViewById(R.id.price_recycleView);
            busNumber = itemView.findViewById(R.id.busNumber_recycleView);
            departureTime = itemView.findViewById(R.id.departureTime_recycleView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onClick(position);

                        }
                    }
                }
            });
        }
    }
}
