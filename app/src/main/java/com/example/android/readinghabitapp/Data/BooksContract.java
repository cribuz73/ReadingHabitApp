package com.example.android.readinghabitapp.Data;

import android.provider.BaseColumns;

/**
 * Created by Cristi on 7/14/2017.
 */

public final class BooksContract {
    private BooksContract () {}

    public static final class BooksEntry implements BaseColumns {

        public static final String TABLE_NAME = "books";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PUBLISHER = "publisher";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_STATUS = "status";

// Status constants

        public static final int STATUS_READ = 1;
        public static final int STATUS_READING_NOW = 2;
        public static final int STATUS_NOT_READ = 0;

    }

}
