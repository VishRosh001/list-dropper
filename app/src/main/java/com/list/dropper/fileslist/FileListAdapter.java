package com.list.dropper.fileslist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.list.dropper.R;
import com.list.dropper.fileslist.file.File;
import com.list.dropper.fileslist.file.FileType;

import java.util.ArrayList;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.MyViewHolder>{

    private final ArrayList<File> fileList;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    public FileListAdapter(ArrayList<File> fileList){
        this.fileList = fileList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtFilename;
        private final TextView txtFileDate;
        private ImageView imgFileIcon;

        public MyViewHolder(final View view, final OnItemClickListener clickListener){
            super(view);
            this.txtFilename = view.findViewById(R.id.txt_filename);
            this.txtFileDate = view.findViewById(R.id.txt_file_date);
            this.imgFileIcon = view.findViewById(R.id.img_file_icon);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
    public FileListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View fileView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_file, parent, false);
        return new MyViewHolder(fileView, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FileListAdapter.MyViewHolder holder, int position) {
        String filename = fileList.get(position).getFilename();
        String fileDate = fileList.get(position).getFileDate();
        FileType fileType = fileList.get(position).getFileType();

        holder.txtFilename.setText(filename);
        holder.txtFileDate.setText(fileDate);

        if(fileType == FileType.Dir){
            holder.imgFileIcon.setImageResource(R.drawable.ic_folder);
        }else{
            holder.imgFileIcon.setImageResource(R.drawable.ic_file);
        }
    }

    @Override
    public int getItemCount() {
        return this.fileList.size();
    }
}
