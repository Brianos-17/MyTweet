package org.wit.mytweet.db;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.wit.mytweet.db.DBDesigner;
import org.wit.mytweet.models.Tweet;


public class DBManager {

	private SQLiteDatabase database;
	private DBDesigner dbHelper;

	public DBManager(Context context) {
		dbHelper = new DBDesigner(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}

	public void insert(Tweet tweet) {
		ContentValues values = new ContentValues();
		values.put(DBDesigner.COLUMN_ID, tweet.tweetId);
		values.put(DBDesigner.COLUMN_USERID, tweet.userId);
		values.put(DBDesigner.COLUMN_MESSAGE, tweet.message);
		values.put(DBDesigner.COLUMN_DATE, tweet.date);

		long insertId = database.insert(DBDesigner.TABLE_TWEET, null,
				values);
	}

	public void delete(int id) {
		Log.v("DB", "Coffee deleted with id: " + id);
		database.delete(DBDesigner.TABLE_TWEET,
				DBDesigner.COLUMN_ID + " = " + id, null);
	}

	public void update(Tweet tweet) {
		ContentValues values = new ContentValues();
		values.put(DBDesigner.COLUMN_ID, tweet.tweetId);
		values.put(DBDesigner.COLUMN_USERID, tweet.userId);
		values.put(DBDesigner.COLUMN_MESSAGE, tweet.message);
		values.put(DBDesigner.COLUMN_DATE, tweet.date);

		long insertId = database
				.update(DBDesigner.TABLE_TWEET,
						values,
						DBDesigner.COLUMN_ID + " = "
								+ tweet.tweetId, null);

	}

	public List<Tweet> getAll() {
		List<Tweet> coffees = new ArrayList<Tweet>();
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ DBDesigner.TABLE_TWEET, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Tweet pojo = toTweet(cursor);
			coffees.add(pojo);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return coffees;
	}

	public Tweet get(int id) {
		Tweet pojo = null;

		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ DBDesigner.TABLE_TWEET + " WHERE "
				+ DBDesigner.COLUMN_ID + " = " + id, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Tweet temp = toTweet(cursor);
			pojo = temp;
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return pojo;
	}
	
	private Tweet toTweet(Cursor cursor) {
		Tweet pojo = new Tweet();
		pojo.tweetId = cursor.getInt(0);
		pojo.userId = cursor.getString(1);
		pojo.message = cursor.getString(2);
		pojo.date = cursor.getString(3);

		return pojo;
	}

//	public void setupList() {
//		Coffee c1 = new Coffee("Mocca Latte", "Ardkeen Stores", 4, 2.99, false);
//		Coffee c2 = new Coffee("Espresso", "Tescos Stores",3.5, 1.99, true);
//		Coffee c3 = new Coffee("Standard Black", "Ardkeen Stores",2.5, 1.99, true);
//		Coffee c4 = new Coffee("Cappuccino", "Spar Shop",2.5, 1.49, false);
//
//		insert(c1);
//		insert(c2);
//		insert(c3);
//		insert(c4);
//	}
}
