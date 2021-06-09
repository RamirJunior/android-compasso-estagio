package com.compasso.agenda.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.compasso.agenda.database.converter.ConversorCalendar;
import com.compasso.agenda.database.converter.ConversorTipoTelefone;
import com.compasso.agenda.database.dao.ContatoDAO;
import com.compasso.agenda.database.dao.TelefoneDAO;
import com.compasso.agenda.model.Contato;
import com.compasso.agenda.model.Telefone;

import static com.compasso.agenda.database.AgendaMigrations.TODAS_MIGRATIONS;

@Database(entities = {Contato.class, Telefone.class}, version = 6, exportSchema = false)
@TypeConverters({ConversorCalendar.class, ConversorTipoTelefone.class})
public abstract class AgendaDataBase extends RoomDatabase {

    private static final String NOME_BANCO_DE_DADOS = "agenda.db";
    public abstract ContatoDAO getRoomContatoDAO();
    public abstract TelefoneDAO getTelefoneDAO();

    public static AgendaDataBase getInstance(Context context) {
        return Room.databaseBuilder(context, AgendaDataBase.class, NOME_BANCO_DE_DADOS)
                .allowMainThreadQueries()
                .addMigrations(TODAS_MIGRATIONS)
                .build();
    }
}
