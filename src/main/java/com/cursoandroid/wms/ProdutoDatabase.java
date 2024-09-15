package com.cursoandroid.wms;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Produto.class}, version = 1)
public abstract class ProdutoDatabase extends RoomDatabase {
    private static volatile ProdutoDatabase instance;

    public abstract ProdutoDao produtoDao();

    public static synchronized ProdutoDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (ProdutoDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    ProdutoDatabase.class,
                                    "produto_database"
                            ).fallbackToDestructiveMigration() // Em caso de alteração no schema
                            .build();
                }
            }
        }
        return instance;
    }
}
