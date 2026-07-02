package com.example.finalexam;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    public static final String EXTRA_WORD =
            "com.example.finalexam.EXTRA_WORD";
    public static final String EXTRA_MEANING =
            "com.example.finalexam.EXTRA_MEANING";
    public static final String EXTRA_POSITION =
            "com.example.finalexam.EXTRA_POSITION";

    public static final int POSITION_NONE = -1;

    private EditText editWord;
    private EditText editMeaning;

    private int editPosition = POSITION_NONE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        editWord = findViewById(R.id.editWord);
        editMeaning = findViewById(R.id.editMeaning);
        Button buttonSave = findViewById(R.id.buttonSave);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        editPosition = intent.getIntExtra(EXTRA_POSITION, POSITION_NONE);

        if (savedInstanceState != null) {
            editPosition = savedInstanceState.getInt(EXTRA_POSITION, editPosition);
        } else if (editPosition != POSITION_NONE) {
            editWord.setText(intent.getStringExtra(EXTRA_WORD));
            editMeaning.setText(intent.getStringExtra(EXTRA_MEANING));
        }

        setTitle(editPosition == POSITION_NONE
                ? R.string.title_add
                : R.string.title_edit);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWord();
            }
        });
    }

    private void saveWord() {
        String word = editWord.getText().toString().trim();
        String meaning = editMeaning.getText().toString().trim();

        if (TextUtils.isEmpty(word)) {
            editWord.setError(getString(R.string.error_word_required));
            editWord.requestFocus();
            Toast.makeText(this, R.string.error_word_required, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(meaning)) {
            editMeaning.setError(getString(R.string.error_meaning_required));
            editMeaning.requestFocus();
            Toast.makeText(this, R.string.error_meaning_required, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent result = new Intent();
        result.putExtra(EXTRA_WORD, word);
        result.putExtra(EXTRA_MEANING, meaning);
        result.putExtra(EXTRA_POSITION, editPosition);

        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    protected void onSaveInstanceState(@androidx.annotation.NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_POSITION, editPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
