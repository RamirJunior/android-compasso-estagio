package com.compasso.agenda.asynctask;

import android.os.AsyncTask;

import com.compasso.agenda.database.dao.ContatoDAO;
import com.compasso.agenda.model.Contato;
import com.compasso.agenda.ui.adapter.ListaContatosAdapter;

import java.util.List;

public class BuscaContatosTask extends AsyncTask<Void, Void, List<Contato>> {

    private final ContatoDAO dao;
    private final ListaContatosAdapter adapter;

    public BuscaContatosTask(ContatoDAO dao, ListaContatosAdapter adapter) {
        this.dao = dao;
        this.adapter = adapter;
    }

    @Override
    protected List<Contato> doInBackground(Void[] objects) {
        return dao.todos();
    }

    @Override
    protected void onPostExecute(List<Contato> todosContatos) {
        super.onPostExecute(todosContatos);
        adapter.atualiza(todosContatos);

    }
}
