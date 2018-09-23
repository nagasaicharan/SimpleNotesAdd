package com.example.nagasaicharan.assignmentpeepstake;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


import com.example.nagasaicharan.assignmentpeepstake.data.NoteItem;



public class NoteEditorActivity extends AppCompatActivity {
    MenuItem save;
    private NoteItem note;
    private EditText et;
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);



        Intent intent=this.getIntent();
        note=new NoteItem();
        note.setKey(intent.getStringExtra("key"));
        note.setText(intent.getStringExtra("text"));

        et = (EditText) findViewById(R.id.noteText);
        if(note.getText().length()!=0) {
            et.setText(note.getText());
            et.setFocusable(false);
           flag=1;
            this.invalidateOptionsMenu();
        }
    }

    private void saveAndFinish(){
        String noteText=et.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("key",note.getKey());
        intent.putExtra("text",noteText);
        setResult(RESULT_OK,intent); //everything is ok
        finish();
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.note_editor, menu);
        save= menu.findItem(R.id.action_save);
        if(flag==1) {
            save.setTitle("BACK");
        }

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_save) {
            saveAndFinish();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        saveAndFinish();
    }
}
