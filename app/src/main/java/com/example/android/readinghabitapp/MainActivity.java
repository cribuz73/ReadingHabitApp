package com.example.android.readinghabitapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.readinghabitapp.Data.BooksContract;
import com.example.android.readinghabitapp.Data.BooksSQLite;

import static com.example.android.readinghabitapp.Data.BooksContract.BooksEntry.TABLE_NAME;

public class MainActivity extends AppCompatActivity {
    private BooksSQLite mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new BooksSQLite(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private Cursor readData (){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {BooksContract.BooksEntry._ID,
                BooksContract.BooksEntry.COLUMN_AUTHOR,
                BooksContract.BooksEntry.COLUMN_TITLE,
                BooksContract.BooksEntry.COLUMN_PUBLISHER,
                BooksContract.BooksEntry.COLUMN_YEAR,
                BooksContract.BooksEntry.COLUMN_STATUS};
        //String selection = PetContract.PetsEntry.COLUMN_PET_GENDER + " = ?";
        // String selectionArgs = new String[] {PetContract.PetsEntry.GENDER_FEMALE};
        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }

    private void displayDatabaseInfo() {
       Cursor cursor = readData();
       TextView displayView = (TextView) findViewById(R.id.text_view_books);

        try {
            displayView.setText("The books table contains " + cursor.getCount() + " books.\n\n");

            displayView.append(BooksContract.BooksEntry._ID + " | " +
                    BooksContract.BooksEntry.COLUMN_AUTHOR + " | " +
                    BooksContract.BooksEntry.COLUMN_TITLE + " | " +
                    BooksContract.BooksEntry.COLUMN_PUBLISHER + " | " +
                    BooksContract.BooksEntry.COLUMN_YEAR + " | " +
                    BooksContract.BooksEntry.COLUMN_STATUS + "\n");

            int idColumnIndex = cursor.getColumnIndex(BooksContract.BooksEntry._ID);
            int authorColumnIndex = cursor.getColumnIndex(BooksContract.BooksEntry.COLUMN_AUTHOR);
            int titleColumnIndex = cursor.getColumnIndex(BooksContract.BooksEntry.COLUMN_TITLE);
            int publisherColumnIndex = cursor.getColumnIndex(BooksContract.BooksEntry.COLUMN_PUBLISHER);
            int yearColumnIndex = cursor.getColumnIndex(BooksContract.BooksEntry.COLUMN_YEAR);
            int stausColumnIndex = cursor.getColumnIndex(BooksContract.BooksEntry.COLUMN_STATUS);

            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentAuthor = cursor.getString(authorColumnIndex);
                String currentTitle = cursor.getString(titleColumnIndex);
                String currentPublisher = cursor.getString(publisherColumnIndex);
                String currentYear = cursor.getString(yearColumnIndex);
                int currentStatus = cursor.getInt(stausColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + ". " +
                        currentAuthor + " | " +
                        currentTitle + " | " +
                        currentPublisher + " | " +
                        currentYear + " | " +
                        currentStatus + "\n" ));
            }
        } finally {
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(BooksContract.BooksEntry.TABLE_NAME, null, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            deleteAll();
            displayDatabaseInfo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
