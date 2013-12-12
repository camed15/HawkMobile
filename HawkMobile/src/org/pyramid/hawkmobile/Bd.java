/**
 * Provee acceso y modificación a la base de datos SQLite, asi como la creación de la misma.
 * 
 * @author Pablo Gonzalez
 */

package org.pyramid.hawkmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Bd {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "mats_name";
	public static final String KEY_UN1 = "un_1";
	public static final String KEY_UN2 = "un_2";
	public static final String KEY_UN3 = "un_3";
	public static final String KEY_UN4 = "un_4";
	public static final String KEY_UN5 = "un_5";
	public static final String KEY_UN6 = "un_6";
	public static final String KEY_UN7 = "un_7";
	public static final String KEY_UN8 = "un_8";
	public static final String KEY_UN9 = "un_9";

	public static final String KEY_HORA = "hora";
	public static final String KEY_SALON = "salon";
	public static final String KEY_PROM = "prom";
	public static final String KEY_SEM = "sem";

	private static final String DATABASE_NAME = "Materias";
	private static final String DATABASE_TABLE = "Boleta";
	public static final String TABLE_LUNES = "Lunes";
	public static final String TABLE_MARTES = "Martes";
	public static final String TABLE_MIERCOLES = "Miercoles";
	public static final String TABLE_JUEVES = "Jueves";
	public static final String TABLE_VIERNES = "Viernes";
	public static final String TABLE_KARDEX = "proms";
	private static final int DATABASE_VERSION = 1;

	private DbHelper dbHelper;
	private final Context cont;
	private SQLiteDatabase mibase;
	public static Integer c = 0;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
					+ " TEXT UNIQUE NOT NULL, " + KEY_UN1 + " TEXT NOT NULL, "
					+ KEY_UN2 + " TEXT NOT NULL, " + KEY_UN3
					+ " TEXT NOT NULL, " + KEY_UN4 + " TEXT NOT NULL, "
					+ KEY_UN5 + " TEXT NOT NULL, " + KEY_UN6
					+ " TEXT NOT NULL, " + KEY_UN7 + " TEXT NOT NULL, "
					+ KEY_UN8 + " TEXT NOT NULL, " + KEY_UN9
					+ " TEXT NOT NULL);");
			
			db.execSQL("CREATE TABLE " + TABLE_LUNES + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
					+ " TEXT UNIQUE NOT NULL, " + KEY_SALON + " TEXT NOT NULL, "
					+ KEY_HORA + " TEXT NOT NULL);");

			db.execSQL("CREATE TABLE " + TABLE_MARTES + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
					+ " TEXT UNIQUE NOT NULL, " + KEY_SALON + " TEXT NOT NULL, "
					+ KEY_HORA + " TEXT NOT NULL);");

			db.execSQL("CREATE TABLE " + TABLE_MIERCOLES + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
					+ " TEXT UNIQUE NOT NULL, " + KEY_SALON + " TEXT NOT NULL, "
					+ KEY_HORA + " TEXT NOT NULL);");

			db.execSQL("CREATE TABLE " + TABLE_JUEVES + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
					+ " TEXT UNIQUE NOT NULL, " + KEY_SALON + " TEXT NOT NULL, "
					+ KEY_HORA + " TEXT NOT NULL);");

			db.execSQL("CREATE TABLE " + TABLE_VIERNES + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
					+ " TEXT UNIQUE NOT NULL, " + KEY_SALON + " TEXT NOT NULL, "
					+ KEY_HORA + " TEXT NOT NULL);");

			db.execSQL("CREATE TABLE " + TABLE_KARDEX + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
					+ " TEXT UNIQUE NOT NULL, " + KEY_PROM + " TEXT NOT NULL, "
					+ KEY_SEM + " TEXT NOT NULL);");
		

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public Bd(Context c) {
		cont = c;

	}

	public Bd open() throws SQLException {
		dbHelper = new DbHelper(cont);
		mibase = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long califsEntry(String nombre, String un1, String un2, String un3,
			String un4, String un5, String un6, String un7, String un8,
			String un9) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, nombre);
		cv.put(KEY_UN1, un1);
		cv.put(KEY_UN2, un2);
		cv.put(KEY_UN3, un3);
		cv.put(KEY_UN4, un4);
		cv.put(KEY_UN5, un5);
		cv.put(KEY_UN6, un6);
		cv.put(KEY_UN7, un7);
		cv.put(KEY_UN8, un8);
		cv.put(KEY_UN9, un9);
		return mibase.insert(DATABASE_TABLE, null, cv);
	}

	public void getLast() {
		c = 0;
		String[] columns = new String[] { KEY_ROWID };
		Cursor cur = mibase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		Mats y;
		int irow = cur.getColumnIndex(KEY_ROWID);
		int p;
		cur.moveToLast();
		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
			c = cur.getInt(irow);
		}
	}

	public void getLastH(String cual) {
		this.c = 0;
		String[] columns = new String[] { KEY_ROWID };
		Cursor c = mibase.query(cual, columns, null, null, null, null, null);
		Mats y;
		int irow = c.getColumnIndex(KEY_ROWID);
		int p;
		// c.moveToLast();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			this.c = c.getInt(irow);
		}

	}

	public void getLastK(String sem) {
		this.c = 0;
		String[] columns = new String[] { KEY_ROWID };
		Cursor c = mibase.query(TABLE_KARDEX, columns, "sem= " + sem, null,
				null, null, null);
		Mats y;
		int irow = c.getColumnIndex(KEY_ROWID);
		int p;
		// c.moveToLast();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			this.c += 1;
		}

	}

	public Mats getBol(int pos) {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_UN1,
				KEY_UN2, KEY_UN3, KEY_UN4, KEY_UN5, KEY_UN6, KEY_UN7, KEY_UN8,
				KEY_UN9 };
		Cursor c = mibase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		Mats y;
		int irow = c.getColumnIndex(KEY_ROWID);
		int iname = c.getColumnIndex(KEY_NAME);
		int iun1 = c.getColumnIndex(KEY_UN1);
		int iun2 = c.getColumnIndex(KEY_UN2);
		int iun3 = c.getColumnIndex(KEY_UN3);
		int iun4 = c.getColumnIndex(KEY_UN4);
		int iun5 = c.getColumnIndex(KEY_UN5);
		int iun6 = c.getColumnIndex(KEY_UN6);
		int iun7 = c.getColumnIndex(KEY_UN7);
		int iun8 = c.getColumnIndex(KEY_UN8);
		int iun9 = c.getColumnIndex(KEY_UN9);
		c.moveToPosition(pos);
		y = new Mats(c.getString(iname), c.getString(iun1), c.getString(iun2),
				c.getString(iun3), c.getString(iun4), c.getString(iun5),
				c.getString(iun6), c.getString(iun7), c.getString(iun8),
				c.getString(iun9));

		return y;
	}

	public long horaEntry(String nombre, String salon, String hora, String dia) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, nombre);
		cv.put(KEY_SALON, salon);
		cv.put(KEY_HORA, hora);

		return mibase.insert(dia, null, cv);
	}

	public long promEntry(String nombre, String prom, String sem) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, nombre);
		cv.put(KEY_PROM, prom);
		cv.put(KEY_SEM, sem);

		return mibase.insert(TABLE_KARDEX, null, cv);

	}

	public Mats getHor(int pos, String cual) {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_SALON,
				KEY_HORA };
		Cursor c = mibase.query(cual, columns, null, null, null, null, null);
		//public Cursor query (boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
		Mats y;
		int irow = c.getColumnIndex(KEY_ROWID);
		int iname = c.getColumnIndex(KEY_NAME);
		int isalon = c.getColumnIndex(KEY_SALON);
		int ihora = c.getColumnIndex(KEY_HORA);

		c.moveToPosition(pos);
		y = new Mats(c.getString(iname), c.getString(isalon),
				c.getString(ihora));

		return y;
	}

	public Mats getProm(int pos, String sem) {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_PROM,
				KEY_SEM };
		Cursor c = mibase.query(TABLE_KARDEX, columns, "sem= " + sem, null,
				null, null, null);
		Mats y;
		int irow = c.getColumnIndex(KEY_ROWID);
		int iname = c.getColumnIndex(KEY_NAME);
		int iprom = c.getColumnIndex(KEY_PROM);
		int isem = c.getColumnIndex(KEY_SEM);

		c.moveToPosition(pos);
		y = new Mats(c.getString(iname), c.getString(iprom));

		return y;
	}

	public String[] getSem() {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_SEM };
		String[] sems;
		Cursor c = mibase.query(true, TABLE_KARDEX, columns, null, null, null, null, null,
				null);
		//true, cual, columns, null, null, null, null, null,null
		Mats y;

		int isem = c.getColumnIndex(KEY_SEM);

		int d = 0;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			d += 1;
		}
		sems = new String[d];
		int x = 0;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			sems[x] = c.getString(isem);
			x++;
		}

		return sems;
	}

}
