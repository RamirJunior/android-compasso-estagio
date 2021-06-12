package com.compasso.agenda.asynctask;

import android.os.AsyncTask;

import com.compasso.agenda.database.dao.ContatoDAO;
import com.compasso.agenda.model.Contato;
import com.compasso.agenda.ui.adapter.ListaContatosAdapter;

public class RemoveContatoTask extends AsyncTask<Void, Void, Void> {

    private final ContatoDAO dao;
    private final ListaContatosAdapter adapter;
    private final Contato contato;

    public RemoveContatoTask(ContatoDAO dao, ListaContatosAdapter adapter, Contato contato) {
        this.dao = dao;
        this.adapter = adapter;
        this.contato = contato;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        dao.remove(contato);
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        adapter.remove(contato);

    }
}
