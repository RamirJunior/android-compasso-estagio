package com.compasso.agenda.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.compasso.agenda.asynctask.BuscaContatosTask;
import com.compasso.agenda.asynctask.RemoveContatoTask;
import com.compasso.agenda.database.AgendaDataBase;
import com.compasso.agenda.database.dao.ContatoDAO;
import com.compasso.agenda.model.Contato;
import com.compasso.agenda.ui.adapter.ListaContatosAdapter;

public class ListaContatosView {

    private final ListaContatosAdapter adapter;
    private final ContatoDAO dao;
    private final Context context;

    public ListaContatosView(Context context) {
        this.context = context;
        this.adapter = new ListaContatosAdapter(this.context);
        dao = AgendaDataBase.getInstance(context)
                .getRoomContatoDAO();
    }

    public void confirmaRemocao(final MenuItem item) {
        new AlertDialog
                .Builder(context)
                .setTitle("Removendo contato")
                .setMessage("Tem certeza que deseja remover o contato?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    AdapterView.AdapterContextMenuInfo menuInfor =
                            (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    Contato contatoEscolhido = adapter.getItem(menuInfor.position);
                    remove(contatoEscolhido);
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void atualizaContatos() {
        new BuscaContatosTask(dao, adapter).execute();
    }

    private void remove(Contato contato) {
        new RemoveContatoTask(dao, adapter, contato).execute();
    }

    public void configuraAdapter(ListView listaDeContatos) {

        listaDeContatos.setAdapter(adapter);
    }

}
