package com.example.nagasaicharan.assignmentpeepstake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nagasaicharan.assignmentpeepstake.data.NoteItem;
import com.example.nagasaicharan.assignmentpeepstake.data.NotesDataSource;

import java.util.List;

public class NotesActivity extends AppCompatActivity {


    ListView lv;

    public static final int EDITOR_ACTIVITY_REQUEST = 1001;

    public static int currentNoteId;
    private NotesDataSource datasource;
    List<NoteItem> notesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*when the user touches and holds on an item for a moment, then releases it, it'll be seen as
        that context menu request. And it'll result in calling a method called on create context menu.
        Each time the method is called You can either load a menu that you define in XML, or you can do it all in Java code.*/
        lv = (ListView)findViewById(R.id.lv1);

        datasource = new NotesDataSource(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NoteItem note =notesList.get(i);
                Intent intent=new Intent(NotesActivity.this,NoteEditorActivity.class);
                intent.putExtra("key",note.getKey());
                intent.putExtra("text",note.getText());
                startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
            }
        });

        refreshDisplay();
    }

    private void refreshDisplay() {
        //The array adapter class will be used to wrap around the data, and feed that data to the list display
        notesList = datasource.findAll();
        ArrayAdapter<NoteItem> adapter = new ArrayAdapter<NoteItem>(this,R.layout.list_item_layout,notesList);

        lv.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //When the user selects an item in the Action Bar or in the Options menu, which we're not using right now,
    // it triggers a method called on options item selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_create) {
            createNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNote() {
        NoteItem note =NoteItem.getNew();
        Intent intent=new Intent(this,NoteEditorActivity.class);
        intent.putExtra("key",note.getKey());
        intent.putExtra("text",note.getText());
        startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
        //When you start the activity, you pass in the request code. When the activity is finished,
        // it passes back the request code. And when the first activity receives the request code it
        // can figure out which screen it came from. The request code integer can be anything
    }


    protected void onListItemClick(ListView l, View v, int position, long id) {
        NoteItem note =notesList.get(position);
        Intent intent=new Intent(this,NoteEditorActivity.class);
        intent.putExtra("key",note.getKey());
        intent.putExtra("text",note.getText());
        startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==EDITOR_ACTIVITY_REQUEST && resultCode==RESULT_OK){
            NoteItem note = new NoteItem();
            note.setKey(data.getStringExtra("key"));
            note.setText(data.getStringExtra("text"));
            datasource.update(note);
            refreshDisplay();
        }
    }


}
