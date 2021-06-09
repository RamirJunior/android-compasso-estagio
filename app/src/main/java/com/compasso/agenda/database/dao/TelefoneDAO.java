package com.compasso.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.compasso.agenda.model.Telefone;

@Dao
public interface TelefoneDAO {
    @Query("SELECT * FROM Telefone WHERE contatoId = :contatoId LIMIT 1")
    Telefone buscaPrimeiroTelefoneDoContato(int contatoId);

}
