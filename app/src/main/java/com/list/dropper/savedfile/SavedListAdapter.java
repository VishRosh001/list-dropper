package com.list.dropper.savedfile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.list.dropper.R;

import java.util.ArrayList;

public class SavedListAdapter extends RecyclerView.Adapter<SavedListAdapter.MyViewHolder> {

    private final ArrayList<SavedFile> savedFiles;
    private SavedListAdapter.OnItemClickListener itemClickListener;

    public SavedListAdapter(ArrayList<SavedFile> savedFiles){
        this.savedFiles = savedFiles;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setItemClickListener(SavedListAdapter.OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtFilename;
        private final TextView txtFilePath;

        public MyViewHolder(final View view, final SavedListAdapter.OnItemClickListener clickListener){
            super(view);
            this.txtFilename = view.findViewById(R.id.txt_saved_filename);
            this.txtFilePath = view.findViewById(R.id.txt_saved_file_path);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    if(clickListener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)clickListener.onItemClick(pos);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public SavedListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View fileView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_saved, parent, false);
        return new MyViewHolder(fileView, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedListAdapter.MyViewHolder holder, int position) {
        String filename = savedFiles.get(position).getFile().getDisplayName();
        String filePath = savedFiles.get(position).getFilePath();

        holder.txtFilename.setText(filename);
        holder.txtFilePath.setText(filePath);
    }

    @Override
    public int getItemCount() {
        return this.savedFiles.size();
    }
}
