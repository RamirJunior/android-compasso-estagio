package com.compasso.agenda.asynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import com.compasso.agenda.database.dao.TelefoneDAO;
import com.compasso.agenda.model.Telefone;

public class BuscaPrimeiroTelefoneDoContatoTask extends AsyncTask<Void, Void, Telefone> {

    private final TelefoneDAO dao;
    private final int contatoId;
    private final PrimeiroTelefoneEncontradoListener listener;

    public BuscaPrimeiroTelefoneDoContatoTask(TelefoneDAO dao, int contatoId, PrimeiroTelefoneEncontradoListener listener) {
        this.dao = dao;
        this.contatoId = contatoId;
        this.listener = listener;
    }

    @Override
    protected Telefone doInBackground(Void... voids) {
        return dao.buscaPrimeiroTelefoneDoContato(contatoId);
    }

    @Override
    protected void onPostExecute(Telefone primeiroTelefone) {
        super.onPostExecute(primeiroTelefone);
        listener.quandoEncontrado(primeiroTelefone);
    }

    public interface PrimeiroTelefoneEncontradoListener {
        void quandoEncontrado(Telefone telefoneEncontrado);
    }
}
