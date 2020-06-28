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


public class userAdapter extends  RecyclerView.Adapter<userAdapter.ArtistViewHolder> {
    private Context mCtx;
    private List<user> artistList;
    private OnItemClickListener mlisterner;
    public  interface OnItemClickListener{
        void  onDeleteClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mlisterner  = listener;
    }
    public userAdapter(Context mCtx, List<user> artistList) {
        this.mCtx = mCtx;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_artists, parent, false);
        return new ArtistViewHolder(view,mlisterner);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        user artist = artistList.get(position);
        holder.textViewName.setText(artist.getName());
        holder.textViewGenre.setText("Surname: " + artist.getSurname());
        holder.textViewCountry.setText("Type: " + artist.getType());
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewGenre,  textViewCountry;
        Button remove ;

        public ArtistViewHolder(@NonNull View itemView , final OnItemClickListener listener) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewGenre = itemView.findViewById(R.id.text_view_genre);

            textViewCountry = itemView.findViewById(R.id.text_view_country);
            remove = itemView.findViewById(R.id.buttdelete);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int pos = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            listener.onDeleteClick(pos);
                        }
                    }
                }
            });
        }
    }
}
