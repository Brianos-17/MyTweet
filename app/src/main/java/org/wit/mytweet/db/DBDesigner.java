package org.wit.mytweet.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBDesigner extends SQLiteOpenHelper
{
	public static final String TABLE_TWEET = "table_tweet";
	public static final String COLUMN_ID = "tweetid";
	public static final String COLUMN_USERID = "userid";
	public static final String COLUMN_MESSAGE = "message";
	public static final String COLUMN_DATE = "date";
	
	private static final String DATABASE_NAME = "mytweet.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE_TABLE_TWEET = "create table "
			+ TABLE_TWEET + "( " + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_USERID + " integer not null,"
			+ COLUMN_MESSAGE + " text not null,"
			+ COLUMN_DATE + " text not null);";
		
	public DBDesigner(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_TABLE_TWEET);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBDesigner.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWEET);
		onCreate(db);
	}

}