package com.example.projectapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, Constants.DATABASENAME, null, Constants.DATABASEVERSION) {

    private var CREATE_TABLE_USERS = ("CREATE TABLE " + Constants.TBL_USERS + "("
            + Constants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Constants.TBL_USERS_NAME + " TEXT  UNIQUE, "
            + Constants.TBL_USERS_EMAIL + " TEXT, "
            + Constants.TBL_USERS_PASSWORD + " TEXT);")

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_TABLE_USERS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    companion object {
        private val DATABASENAME = Constants.DATABASENAME
        private val DATABASEVERSION = Constants.DATABASEVERSION

        @Volatile private var INSTANCE: DatabaseHelper? = null
        fun getInstance(context: Context): DatabaseHelper {
            return INSTANCE ?: synchronized(this){
                INSTANCE?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }
        private fun buildDatabase(context: Context): DatabaseHelper{
            return DatabaseHelper(context)

        }
    }

    fun registerUser(user: User): Boolean{
        return try {
            val db = this.writableDatabase
            val values = ContentValues()
            values.put(Constants.TBL_USERS_NAME, user.name)
            values.put(Constants.TBL_USERS_EMAIL, user.email)
            values.put(Constants.TBL_USERS_PASSWORD, user.password)
            return db.insert(Constants.TBL_USERS, null, values) > -1
        }catch (e: Exception){
            false
        }
    }
    @SuppressLint("Range")
    fun getUsers(): List<User> {
        val db = this.writableDatabase
        val list = ArrayList<User>()
        val cursor: Cursor
        cursor = db.rawQuery("SELECT * FROM ${Constants.TBL_USERS}", null)
        if (cursor != null) {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                do {
                    val user = User()
                    user.name = cursor.getString(cursor.getColumnIndex(Constants.TBL_USERS_NAME))
                    user.password = cursor.getString(cursor.getColumnIndex(Constants.TBL_USERS_PASSWORD))
                    user.email = cursor.getString(cursor.getColumnIndex(Constants.TBL_USERS_EMAIL))
                    list.add(user)
                } while (cursor.moveToNext())
            }
        }
        return list
    }


    @SuppressLint("Range")
    fun getUser(email: String): User {
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM ${Constants.TBL_USERS} WHERE  ${Constants.TBL_USERS_EMAIL } = '$email';"
        val cursor = db.rawQuery(selectQuery, null)
        val user = User()
        if (cursor != null) {
            if (cursor.count == 1) {
                cursor.moveToFirst()
                user.name = cursor.getString(cursor.getColumnIndex(Constants.TBL_USERS_NAME))
                user.password = cursor.getString(cursor.getColumnIndex(Constants.TBL_USERS_PASSWORD))
                user.email = cursor.getString(cursor.getColumnIndex(Constants.TBL_USERS_EMAIL))
            }
        }
        cursor.close()
        db.close()
        return user
    }
}