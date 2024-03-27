package com.example.ava2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ObjetosDB";
    public static final int DATABASE_VERSION = 1;

    // Definir os nomes das colunas da tabela
    public static final String TABLE_NAME = "objetos";
    public static final String COLUMN_ID = "_id"; // Nome da coluna de ID alterado para "_id" para compatibilidade com CursorAdapter
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_QUANTIDADE = "quantidade";
    public static final String COLUMN_PRECO = "preco";
    public static final String COLUMN_DISPONIVEL = "disponivel";

    // Construtor
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL para criar a tabela de objetos
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," // ID autoincrementado
                + COLUMN_NOME + " TEXT,"
                + COLUMN_QUANTIDADE + " INTEGER,"
                + COLUMN_PRECO + " REAL,"
                + COLUMN_DISPONIVEL + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Quando a versão do banco de dados é atualizada, podemos executar a lógica de atualização aqui
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
