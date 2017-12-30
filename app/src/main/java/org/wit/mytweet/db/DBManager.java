package ie.cm.db;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ie.cm.models.Coffee;

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

	public void insert(Coffee c) {
		ContentValues values = new ContentValues();
		values.put(DBDesigner.COLUMN_NAME, c.name);
		values.put(DBDesigner.COLUMN_SHOP, c.shop);
		values.put(DBDesigner.COLUMN_PRICE, c.price);
		values.put(DBDesigner.COLUMN_RATING, c.rating);
		values.put(DBDesigner.COLUMN_FAV, c.favourite == true ? 1 : 0);

		long insertId = database.insert(DBDesigner.TABLE_COFFEE, null,
				values);
	}

	public void delete(int id) {
		Log.v("DB", "Coffee deleted with id: " + id);
		database.delete(DBDesigner.TABLE_COFFEE,
				DBDesigner.COLUMN_ID + " = " + id, null);
	}

	public void update(Coffee c) {
		ContentValues values = new ContentValues();
		values.put(DBDesigner.COLUMN_NAME, c.name);
		values.put(DBDesigner.COLUMN_SHOP, c.shop);
		values.put(DBDesigner.COLUMN_PRICE, c.price);
		values.put(DBDesigner.COLUMN_RATING, c.rating);
		values.put(DBDesigner.COLUMN_FAV, c.favourite == true ? 1 : 0);

		long insertId = database
				.update(DBDesigner.TABLE_COFFEE,
						values,
						DBDesigner.COLUMN_ID + " = "
								+ c.coffeeId, null);

	}

	public List<Coffee> getAll() {
		List<Coffee> coffees = new ArrayList<Coffee>();
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ DBDesigner.TABLE_COFFEE, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Coffee pojo = toCoffee(cursor);
			coffees.add(pojo);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return coffees;
	}

	public Coffee get(int id) {
		Coffee pojo = null;

		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ DBDesigner.TABLE_COFFEE + " WHERE "
				+ DBDesigner.COLUMN_ID + " = " + id, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Coffee temp = toCoffee(cursor);
			pojo = temp;
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return pojo;
	}

	public List<Coffee> getFavourites() {
		List<Coffee> coffees = new ArrayList<Coffee>();
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ DBDesigner.TABLE_COFFEE + " WHERE "
				+ DBDesigner.COLUMN_FAV + " = 1", null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Coffee pojo = toCoffee(cursor);
			coffees.add(pojo);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return coffees;
	}
	
	private Coffee toCoffee(Cursor cursor) {
		Coffee pojo = new Coffee();
		pojo.coffeeId = cursor.getInt(0);
		pojo.name = cursor.getString(1);
		pojo.shop = cursor.getString(2);
		pojo.price = cursor.getDouble(3);
		pojo.rating = cursor.getDouble(4);
		pojo.favourite = cursor.getInt(5) == 1 ? true : false;

		return pojo;
	}

	public void setupList() {
		Coffee c1 = new Coffee("Mocca Latte", "Ardkeen Stores", 4, 2.99, false);
		Coffee c2 = new Coffee("Espresso", "Tescos Stores",3.5, 1.99, true);
		Coffee c3 = new Coffee("Standard Black", "Ardkeen Stores",2.5, 1.99, true);
		Coffee c4 = new Coffee("Cappuccino", "Spar Shop",2.5, 1.49, false);

		insert(c1);
		insert(c2);
		insert(c3);
		insert(c4);
	}
}
