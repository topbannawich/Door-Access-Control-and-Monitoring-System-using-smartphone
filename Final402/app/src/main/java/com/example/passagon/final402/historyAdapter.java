package com.example.passagon.final402;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;


public class historyAdapter extends  RecyclerView.Adapter<historyAdapter.ArtistViewHolder> {
    private Context mCtx;
    private List<Information> artistList;
    private OnItemClickListener mlisterner;
    public  interface OnItemClickListener{
        void  onDeleteClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mlisterner  = listener;
    }
    public historyAdapter(Context mCtx, List<Information> artistList) {
        this.mCtx = mCtx;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.history, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Information artist = artistList.get(position);
        holder.textViewName.setText(artist.name);
        holder.textViewGenre.setText("Date: " + artist.date);
        holder.textViewAge.setText("note: " + artist.type);
        holder.textViewCountry.setText("type: " + artist.note);
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewGenre, textViewAge, textViewCountry;
        Button remove ;

        public ArtistViewHolder(@NonNull View itemView ) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name1);
            textViewGenre = itemView.findViewById(R.id.text_view_genre1);
            textViewAge = itemView.findViewById(R.id.text_view_age1);
            textViewCountry = itemView.findViewById(R.id.text_view_country1);


        }
    }
}
