package com.compasso.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.compasso.agenda.model.Telefone;

import java.util.List;

@Dao
public interface TelefoneDAO {
    @Query("SELECT * FROM Telefone WHERE contatoId = :contatoId LIMIT 1")
    Telefone buscaPrimeiroTelefoneDoContato(int contatoId);

    @Insert
    void salva(Telefone... telefones);

    @Query("SELECT * FROM Telefone WHERE contatoId = :contatoId")

    List<Telefone> buscaTodosTelefonesDoContato(int contatoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void atualiza(Telefone... telefones);
}
