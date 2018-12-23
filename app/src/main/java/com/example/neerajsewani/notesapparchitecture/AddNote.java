package com.example.neerajsewani.notesapparchitecture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {
    public static final String TITLE = "TITLE";
    public static final String DESC = "DESC";
    public static final String PRIORITY = "PRIORITY";

    private EditText title;
    private EditText description;
    private NumberPicker priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        //  setting up the action bar
        getActionBar().setHomeAsUpIndicator(R.drawable.close);
        setTitle("Add Note");

        title = findViewById(R.id.title_et_add_note);
        description = findViewById(R.id.desc_et_add_note);
        priority = findViewById(R.id.number_picker_add_note);

        //  setting the min and max value of the number picker
        priority.setMaxValue(10);
        priority.setMinValue(1);
    }

    private void saveNote() {
        String titleValue = title.getText().toString();
        String descriptionValue = description.getText().toString();
        int priorityValue = priority.getValue();

        if (titleValue.trim().isEmpty() || descriptionValue.trim().isEmpty()) {
            Toast.makeText(this, "Enter Title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(TITLE, titleValue);
        intent.putExtra(DESC, descriptionValue);
        intent.putExtra(PRIORITY, priorityValue);

        //  sending the data back to the "MainActivity"
        setResult(RESULT_OK, intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_note_item:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}