package com.example.kaizentranspo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kaizentranspo.classes.TicketList;

import java.util.ArrayList;

public class Ticket_RecyclerViewAdapter extends RecyclerView.Adapter<Ticket_RecyclerViewAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<TicketList> ticket = new ArrayList<>();

    public Ticket_RecyclerViewAdapter(Context context, ArrayList<TicketList> ticket, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.ticket = ticket;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    @NonNull
    @Override
    public Ticket_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bus_recyclerview, parent, false);
        return new Ticket_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Ticket_RecyclerViewAdapter.MyViewHolder holder,
                                 int position) {
        holder.destination.setText(ticket.get(position).getDestination());
        holder.price.setText(ticket.get(position).getSeatNumber());
        holder.busNumber.setText(ticket.get(position).getBusNumber());
        holder.departureTime.setText(ticket.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return ticket.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView destination, price, departureTime, busNumber;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            destination = itemView.findViewById(R.id.destination_recycleView);
            price = itemView.findViewById(R.id.price_recycleView);
            busNumber = itemView.findViewById(R.id.busNumber_recycleView);
            departureTime = itemView.findViewById(R.id.departureTime_recycleView);

            itemView.setOnClickListener(v -> {
                if(recyclerViewInterface != null){
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onClick(position);

                    }
                }
            });
        }
    }
}
