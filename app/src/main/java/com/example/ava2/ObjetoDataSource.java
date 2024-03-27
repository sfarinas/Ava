package com.example.ava2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class ObjetoDataSource {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ObjetoDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void criarObjeto(Objeto objeto) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_NOME, objeto.getNome());
        values.put(SQLiteHelper.COLUMN_QUANTIDADE, objeto.getQuantidade());
        values.put(SQLiteHelper.COLUMN_PRECO, objeto.getPreco());
        values.put(SQLiteHelper.COLUMN_DISPONIVEL, objeto.isDisponivel() ? 1 : 0);

        database.insert(SQLiteHelper.TABLE_NAME, null, values);
    }

    public void atualizarObjeto(Objeto objeto) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_NOME, objeto.getNome());
        values.put(SQLiteHelper.COLUMN_QUANTIDADE, objeto.getQuantidade());
        values.put(SQLiteHelper.COLUMN_PRECO, objeto.getPreco());
        values.put(SQLiteHelper.COLUMN_DISPONIVEL, objeto.isDisponivel() ? 1 : 0);

        database.update(SQLiteHelper.TABLE_NAME, values,
                SQLiteHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(objeto.getId())});
    }

    public void deletarObjeto(long objetoId) {
        database.delete(SQLiteHelper.TABLE_NAME, SQLiteHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(objetoId)});
    }

    public List<Objeto> getAllObjetos() {
        List<Objeto> objetos = new ArrayList<>();
        Cursor cursor = database.query(SQLiteHelper.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Objeto objeto = cursorToObjeto(cursor);
            objetos.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return objetos;
    }
    public Objeto getObjeto(long objetoId) {
        Objeto objeto = null;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(SQLiteHelper.TABLE_NAME, null,
                SQLiteHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(objetoId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            objeto = cursorToObjeto(cursor);
            cursor.close();
        }
        return objeto;
    }
    private Objeto cursorToObjeto(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            return null; // Retorna null se o cursor estiver vazio
        }

        int idIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_ID);
        if (idIndex == -1) {
            return null; // Retorna null se a coluna ID não existir
        }
        long id = cursor.getLong(idIndex);

        String nome = null;
        int nomeIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_NOME);
        if (nomeIndex != -1) {
            nome = cursor.getString(nomeIndex);
        }

        int quantidadeIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_QUANTIDADE);
        int quantidade = 0;
        if (quantidadeIndex != -1) {
            quantidade = cursor.getInt(quantidadeIndex);
        }

        int precoIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_PRECO);
        double preco = 0.0;
        if (precoIndex != -1) {
            preco = cursor.getDouble(precoIndex);
        }

        int disponivelIndex = cursor.getColumnIndex(SQLiteHelper.COLUMN_DISPONIVEL);
        boolean disponivel = false;
        if (disponivelIndex != -1) {
            disponivel = cursor.getInt(disponivelIndex) == 1;
        }

        return new Objeto(id, nome, quantidade, preco, disponivel);
    }

}

