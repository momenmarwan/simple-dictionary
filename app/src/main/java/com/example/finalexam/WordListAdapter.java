package com.example.finalexam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    public interface OnItemActionListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    private final List<Word> words;
    private final OnItemActionListener listener;

    public WordListAdapter(List<Word> words, OnItemActionListener listener) {
        this.words = words;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word current = words.get(position);
        holder.textWord.setText(current.getWord());
        holder.textMeaning.setText(current.getMeaning());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = holder.getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onItemLongClick(pos);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {
        final TextView textWord;
        final TextView textMeaning;

        WordViewHolder(@NonNull View itemView) {
            super(itemView);
            textWord = itemView.findViewById(R.id.textWord);
            textMeaning = itemView.findViewById(R.id.textMeaning);
        }
    }
}
