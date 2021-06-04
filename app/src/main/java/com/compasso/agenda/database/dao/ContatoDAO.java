package com.compasso.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.compasso.agenda.model.Contato;

import java.util.List;

@Dao
public interface ContatoDAO {
    @Insert
    void salva(Contato contato);

    @Query("SELECT * from contato")
    List<Contato> todos();

    @Delete
    void remove(Contato contato);

    @Update
    void edita(Contato contato);
}
