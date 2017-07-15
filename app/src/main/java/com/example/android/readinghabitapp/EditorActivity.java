package com.example.android.readinghabitapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.readinghabitapp.Data.BooksContract;
import com.example.android.readinghabitapp.Data.BooksSQLite;

/**
 * Created by Cristi on 7/14/2017.
 */

public class EditorActivity extends AppCompatActivity{

    private EditText mAuthorEditText;
    private EditText mTitleEditText;
    private EditText mPublisherText;
    private EditText mYearEditText;
    private Spinner mStatusSpinner;

    private int mStatus = 0;

    private BooksSQLite mDbHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.editor_activity);
        mAuthorEditText = (EditText) findViewById(R.id.edit_book_author);
        mTitleEditText = (EditText) findViewById(R.id.edit_book_title);
        mPublisherText = (EditText) findViewById(R.id.edit_book_publisher);
        mYearEditText = (EditText) findViewById(R.id.edit_book_year);
        mStatusSpinner = (Spinner) findViewById(R.id.spinner_status);

        setupSpinner();
        mDbHelper = new BooksSQLite(this);
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter statusSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_status_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mStatusSpinner.setAdapter(statusSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.status_not_read))) {
                        mStatus = BooksContract.BooksEntry.STATUS_NOT_READ;
                    } else if (selection.equals(getString(R.string.status_reading_now))) {
                        mStatus = BooksContract.BooksEntry.STATUS_READING_NOW;
                    } else {
                        mStatus = BooksContract.BooksEntry.STATUS_READ;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mStatus = 0;
            }
        });
    }

    private void insertBook () {
        String authorName = mAuthorEditText.getText().toString().trim();
        String bookTitle = mTitleEditText.getText().toString().trim();
        String bookPublisher = mPublisherText.getText().toString().trim();
        String bookYear = mYearEditText.getText().toString().trim();
        int bookStatus = mStatus;

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BooksContract.BooksEntry.COLUMN_AUTHOR, authorName);
        values.put(BooksContract.BooksEntry.COLUMN_TITLE, bookTitle);
        values.put(BooksContract.BooksEntry.COLUMN_PUBLISHER, bookPublisher);
        values.put(BooksContract.BooksEntry.COLUMN_YEAR, bookYear);
        values.put(BooksContract.BooksEntry.COLUMN_STATUS, bookStatus);

        long newRowId = db.insert(BooksContract.BooksEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            String error = getString(R.string.error_saving);
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            String saved = getString(R.string.book_saved);
            Toast.makeText(this, saved + ": " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
private void clearText(){
    mAuthorEditText.setText("");
    mTitleEditText.setText("");
    mPublisherText.setText("");
    mYearEditText.setText("");
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                //Save pets to database;
                insertBook();
                // Finish editor activity;
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                clearText();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
