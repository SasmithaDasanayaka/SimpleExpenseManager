package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DB extends SQLiteOpenHelper {

    static DB instance;

    static final String DB_NAME = "170094E";
    static final int DB_VERSION = 2;

    public static final String ACCOUNTS ="accounts";
    public static final String ID = "id";
    public static final String ACC_NO = "acc_no";
    public static final String BANK_NAME = "bank_name";
    public static final String ACC_OWNER_NAME = "acc_owner_name";
    public static final String BALANCE = "balance";
    private static final String CREATE_TABLE_ACCOUNTS = "create table " + ACCOUNTS + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ACC_NO + " TEXT NOT NULL UNIQUE, "
            + BANK_NAME + " TEXT, "
            + ACC_OWNER_NAME + " TEXT, "
            + BALANCE + " INTEGER(15) );";

    public static final String TRANSACTIONS ="transactions";
    public static final String TRANSACTION_ID = "id";
    public static final String TRANSACTION_DATE = "date";
    public static final String TRANSACTION_ACC_NO = "acc_no";
    public static final String EXPENSE_TYPE = "expense_type";
    public static final String AMOUNT = "amount";
    private static final String CREATE_TABLE_TRANSACTIONS = "create table " + TRANSACTIONS + "(" + TRANSACTION_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TRANSACTION_DATE + " TEXT, "
            + TRANSACTION_ACC_NO + " TEXT NOT NULL, "
            + EXPENSE_TYPE + " TEXT CHECK( "+EXPENSE_TYPE+" IN ('INCOME','EXPENSE') ), "
            + AMOUNT + " REAL );";


    public DB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNTS);
        sqLiteDatabase.execSQL(CREATE_TABLE_TRANSACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRANSACTIONS);
        onCreate(sqLiteDatabase);
    }

    public synchronized static DB getInstance(Context context) {
        if(instance==null){
            instance = new DB(context);
        }
        return instance;
    }

    public static void closeDB(){
        if(instance!=null){
            instance.close();
        }
    }
}