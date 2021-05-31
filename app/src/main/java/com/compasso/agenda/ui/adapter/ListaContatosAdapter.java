package com.compasso.agenda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.compasso.agenda.R;
import com.compasso.agenda.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ListaContatosAdapter extends BaseAdapter {

    private final List<Contato> contatos = new ArrayList<>();
    private final Context context;

    public ListaContatosAdapter(Context context) {
        this.context = context;
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

    private void vincula(View viewCriada, Contato contatoDevolvido) {
        TextView nome = viewCriada.findViewById(R.id.item_contato_nome);
        nome.setText(contatoDevolvido.getNome());
        TextView telefone = viewCriada.findViewById(R.id.item_contato_telefone);
        telefone.setText(contatoDevolvido.getTelefone());
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
