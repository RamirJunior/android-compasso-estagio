package com.compasso.agenda.asynctask;

import android.os.AsyncTask;

import com.compasso.agenda.database.dao.TelefoneDAO;
import com.compasso.agenda.model.Contato;
import com.compasso.agenda.model.Telefone;

import java.util.List;

public class BuscaTodosTelefonesDoContatoTask extends AsyncTask<Void, Void, List<Telefone>> {

    private final TelefoneDAO telefoneDAO;
    private final Contato contato;
    private final TelefonesDoContatoEncontradoListener listener;

    public BuscaTodosTelefonesDoContatoTask(TelefoneDAO telefoneDAO, Contato contato, TelefonesDoContatoEncontradoListener listener) {
        this.telefoneDAO = telefoneDAO;
        this.contato = contato;
        this.listener = listener;
    }

    @Override
    protected List<Telefone> doInBackground(Void... voids) {
        return telefoneDAO.buscaTodosTelefonesDoContato(contato.getId());
    }

    @Override
    protected void onPostExecute(List<Telefone> telefones) {
        super.onPostExecute(telefones);
        listener.quandoEncontrados(telefones);
    }

    public interface TelefonesDoContatoEncontradoListener {
        void quandoEncontrados(List<Telefone> telefones);
    }
}
