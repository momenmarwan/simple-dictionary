package com.example.finalexam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements WordListAdapter.OnItemActionListener {

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_EDIT = 2;

    private static final String STATE_WORDS = "state_words";

    private ArrayList<Word> wordList;

    private WordListAdapter adapter;
    private RecyclerView recyclerView;
    private TextView textEmpty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            @SuppressWarnings("unchecked")
            ArrayList<Word> restored =
                    (ArrayList<Word>) savedInstanceState.getSerializable(STATE_WORDS);
            wordList = (restored != null) ? restored : new ArrayList<Word>();
        } else {
            wordList = new ArrayList<>();
            seedSampleData();
        }

        recyclerView = findViewById(R.id.recyclerViewWords);
        textEmpty = findViewById(R.id.textEmpty);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new WordListAdapter(wordList, this);
        recyclerView.setAdapter(adapter);

        updateEmptyView();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                startActivityForResult(intent, REQUEST_ADD);
            }
        });
    }

    private void seedSampleData() {
        wordList.add(new Word("Ephemeral", "Lasting for a very short time."));
        wordList.add(new Word("Serendipity",
                "Finding something good without looking for it."));
        wordList.add(new Word("Android", "A mobile operating system by Google."));
        wordList.add(new Word("Lucid", "Clear and easy to understand."));
        wordList.add(new Word("Quixotic", "Extremely idealistic and unrealistic."));
        wordList.add(new Word("Eloquent", "Fluent and persuasive in speaking."));
        wordList.add(new Word("Resilient", "Able to recover quickly from difficulties."));
        wordList.add(new Word("Meticulous", "Showing great attention to detail."));
        wordList.add(new Word("Benevolent", "Kind and generous towards others."));
        wordList.add(new Word("Candid", "Truthful and straightforward."));
        wordList.add(new Word("Diligent", "Careful and hardworking."));
        wordList.add(new Word("Empathy", "The ability to understand others' feelings."));
        wordList.add(new Word("Frugal", "Careful about spending money."));
        wordList.add(new Word("Gregarious", "Fond of company; sociable."));
        wordList.add(new Word("Humble", "Modest; not proud or arrogant."));
        wordList.add(new Word("Innovate", "To introduce something new."));
        wordList.add(new Word("Jovial", "Cheerful and friendly."));
        wordList.add(new Word("Keen", "Eager and enthusiastic."));
        wordList.add(new Word("Luminous", "Giving off light; bright."));
        wordList.add(new Word("Nostalgia", "A longing for the past."));
        wordList.add(new Word("Optimistic", "Hopeful about the future."));
        wordList.add(new Word("Pragmatic", "Dealing with things practically."));
        wordList.add(new Word("Vivid", "Bright, clear, and full of life."));
    }

    @Override
    public void onItemClick(int position) {
        Word word = wordList.get(position);
        Intent intent = new Intent(this, AddEditActivity.class);
        intent.putExtra(AddEditActivity.EXTRA_WORD, word.getWord());
        intent.putExtra(AddEditActivity.EXTRA_MEANING, word.getMeaning());
        intent.putExtra(AddEditActivity.EXTRA_POSITION, position);
        startActivityForResult(intent, REQUEST_EDIT);
    }

    @Override
    public void onItemLongClick(int position) {
        if (position < 0 || position >= wordList.size()) {
            return;
        }
        wordList.remove(position);
        adapter.notifyItemRemoved(position);
        updateEmptyView();
        Toast.makeText(this, R.string.toast_deleted, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) {
            return;
        }

        String word = data.getStringExtra(AddEditActivity.EXTRA_WORD);
        String meaning = data.getStringExtra(AddEditActivity.EXTRA_MEANING);
        int position = data.getIntExtra(
                AddEditActivity.EXTRA_POSITION, AddEditActivity.POSITION_NONE);

        if (word == null || meaning == null) {
            return;
        }

        if (requestCode == REQUEST_ADD) {
            wordList.add(new Word(word, meaning));
            adapter.notifyItemInserted(wordList.size() - 1);
            recyclerView.scrollToPosition(wordList.size() - 1);
            updateEmptyView();
            Toast.makeText(this, R.string.toast_added, Toast.LENGTH_SHORT).show();

        } else if (requestCode == REQUEST_EDIT) {
            if (position >= 0 && position < wordList.size()) {
                Word existing = wordList.get(position);
                existing.setWord(word);
                existing.setMeaning(meaning);
                adapter.notifyItemChanged(position);
                Toast.makeText(this, R.string.toast_updated, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@androidx.annotation.NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_WORDS, wordList);
    }

    private void updateEmptyView() {
        if (wordList.isEmpty()) {
            textEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            textEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
