package com.example.hwandroid11;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.VH> {
    private final RecyclerViewInterface recyclerViewInterface;
    private List<Student> data = new ArrayList<>();

    StudentAdapter(RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
    }

    void setStudents(List<Student> list) {
        data = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
        android.view.View v = android.view.LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new VH(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int pos) {
        holder.tv.setText(data.get(pos).toString());
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        android.widget.TextView tv;
        VH(android.view.View v, RecyclerViewInterface recyclerViewInterface)
        {
            super(v);
            tv = v.findViewById(android.R.id.text1);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null)
                    {
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION)
                        {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }


    }
}