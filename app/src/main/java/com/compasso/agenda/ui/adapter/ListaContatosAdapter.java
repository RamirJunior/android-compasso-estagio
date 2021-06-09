package com.compasso.agenda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.compasso.agenda.R;
import com.compasso.agenda.database.AgendaDataBase;
import com.compasso.agenda.database.dao.TelefoneDAO;
import com.compasso.agenda.model.Contato;
import com.compasso.agenda.model.Telefone;

import java.util.ArrayList;
import java.util.List;

public class ListaContatosAdapter extends BaseAdapter {

    private final List<Contato> contatos = new ArrayList<>();
    private final Context context;
    private final TelefoneDAO dao;

    public ListaContatosAdapter(Context context) {
        this.context = context;
        dao = AgendaDataBase.getInstance(context).getTelefoneDAO();
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Contato getItem(int posicao) {
        return contatos.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return contatos.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        View viewCriada = criaView(viewGroup);
        Contato contatoDevolvido = contatos.get(posicao);
        vincula(viewCriada, contatoDevolvido);
        return viewCriada;
    }

    private void vincula(View viewCriada, Contato contato) {
        TextView nome = viewCriada.findViewById(R.id.item_contato_nome);
        nome.setText(contato.getNome());
        TextView telefone = viewCriada.findViewById(R.id.item_contato_telefone);
        Telefone primeiroTelefone = dao.buscaPrimeiroTelefoneDoContato(contato.getId());
        telefone.setText(primeiroTelefone.getNumero());
    }

    private View criaView(ViewGroup viewGroup) {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_contato, viewGroup, false);
    }

    public void atualiza(List<Contato> contatos){
        this.contatos.clear();
        this.contatos.addAll(contatos);
        notifyDataSetChanged();
    }

    public void remove(Contato contato) {
        contatos.remove(contato);
        notifyDataSetChanged();
    }
}
