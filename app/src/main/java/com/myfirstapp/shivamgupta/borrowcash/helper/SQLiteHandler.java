/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.myfirstapp.shivamgupta.borrowcash.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "borrow_cash";

	// Login table name
	private static final String TABLE_LOGIN = "users";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_UID = "uid";
	private static final String KEY_BORROWER = "borrower";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_PHONE = "phone";
	private static final String KEY_AMOUNT = "prin_amount";
	private static final String KEY_INTEREST = "interest";
	private static final String KEY_TOTAL = "total";
	private static final String KEY_TIME = "time";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_CHARITY = "charity";
	private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_MORTGAGE = "mortgage";

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_UID + " TEXT,"
				+ KEY_BORROWER + " TEXT,"
				+ KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT,"
				+ KEY_PHONE + " TEXT," + KEY_AMOUNT + " TEXT,"
				+ KEY_INTEREST + " TEXT," + KEY_TOTAL + " TEXT,"
				+ KEY_TIME + " TEXT,"
				+ KEY_ADDRESS + " TEXT," + KEY_CHARITY + " TEXT,"
				+ KEY_CREATED_AT + " TEXT,"
				+ KEY_MORTGAGE + " TEXT"
				+ ")";
		db.execSQL(CREATE_LOGIN_TABLE);

		Log.d(TAG, "Database tables created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(String uid,String borrower,String name, String email,String phone,String amount,String interest,String total_amount,String time, String add,String charity, String created_at,String mortgage) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_UID, uid);
		values.put(KEY_BORROWER, borrower); // Name
		values.put(KEY_NAME, name);
		values.put(KEY_EMAIL, email);
		values.put(KEY_PHONE, phone);
		values.put(KEY_AMOUNT, amount);
		values.put(KEY_INTEREST, interest);
		values.put(KEY_TOTAL, total_amount);
		values.put(KEY_TIME, time);
		values.put(KEY_ADDRESS, add);
		values.put(KEY_CHARITY, charity);
		values.put(KEY_CREATED_AT, created_at);
		values.put(KEY_MORTGAGE, mortgage);
		// Inserting Row
		long id = db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("uid", cursor.getString(1));
			user.put("borrower", cursor.getString(2));
			user.put("name", cursor.getString(3));
			user.put("email", cursor.getString(4));
			user.put("phone", cursor.getString(5));
			user.put("prin_amount", cursor.getString(6));
			user.put("interest", cursor.getString(7));
			user.put("total", cursor.getString(8));
			user.put("time", cursor.getString(9));
			user.put("address", cursor.getString(10));
			user.put("charity", cursor.getString(11));
			user.put("created_at", cursor.getString(12));
			user.put("mortgage", cursor.getString(13));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

		return user;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_LOGIN, null, null);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

}
