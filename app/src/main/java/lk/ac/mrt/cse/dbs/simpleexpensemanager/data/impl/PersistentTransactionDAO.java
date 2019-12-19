package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO {

    private DB db;
    private Context context;
    private SQLiteDatabase sqliteDatabase;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public PersistentTransactionDAO(Context context) {
        this.context = context;
        db = DB.getInstance(context);
        sqliteDatabase = db.getWritableDatabase();
    }


    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DB.TRANSACTION_DATE, dateFormat.format(date));
        contentValue.put(DB.TRANSACTION_ACC_NO, accountNo);
        contentValue.put(DB.EXPENSE_TYPE, expenseType.name());
        contentValue.put(DB.AMOUNT,amount);
        sqliteDatabase.insert(DB.TRANSACTIONS, null, contentValue);

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        String[] columns = new String[]{DB.TRANSACTION_ID,
                DB.TRANSACTION_DATE,
                DB.TRANSACTION_ACC_NO,
                DB.EXPENSE_TYPE,
                DB.AMOUNT,};
        Cursor cursor = sqliteDatabase.query(DB.TRANSACTIONS, columns, null, null, null, null, null);
        ArrayList<Transaction> transactions = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    transactions.add(new Transaction(dateFormat.parse(cursor.getString(1)),cursor.getString(2), ExpenseType.valueOf(cursor.getString(3)),cursor.getDouble(4)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        String[] columns = new String[]{DB.TRANSACTION_ID,
                DB.TRANSACTION_DATE,
                DB.TRANSACTION_ACC_NO,
                DB.EXPENSE_TYPE,
                DB.AMOUNT,};
        Cursor cursor = sqliteDatabase.query(DB.TRANSACTIONS, columns, null, null, null, null, null,String.valueOf(limit));
        ArrayList<Transaction> transactions = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext() ) {
                try {
                    transactions.add(new Transaction(dateFormat.parse(cursor.getString(1)),cursor.getString(2), ExpenseType.valueOf(cursor.getString(3)),cursor.getDouble(4)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return transactions;
    }
}