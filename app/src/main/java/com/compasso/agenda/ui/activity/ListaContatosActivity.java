package com.compasso.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.compasso.agenda.R;
import com.compasso.agenda.model.Contato;
import com.compasso.agenda.ui.ListaContatosView;

import static com.compasso.agenda.ui.activity.ConstantesActivities.CHAVE_CONTATO;

public class ListaContatosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Contatos";
    private final ListaContatosView listaContatosView = new ListaContatosView(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);
        setTitle(TITULO_APPBAR);
        configuraFabNovoContato();
        configuraLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_contatos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_contatos_menu_remover) {
            listaContatosView.confirmaRemocao(item);
        }
        return super.onContextItemSelected(item);
    }

    private void configuraFabNovoContato() {
        FloatingActionButton botaoNovoContato = findViewById(R.id.activity_lista_contatos_fab_novo_contato);
        botaoNovoContato.setOnClickListener(view -> abreFormularioModoInsereContato());
    }

    private void abreFormularioModoInsereContato() {
        startActivity(new Intent(this, FormularioContatoActivity.class));
    }

    //onResume é o estado da activity antes de estar em execução
    @Override
    protected void onResume() {
        super.onResume();
        listaContatosView.atualizaContatos();
    }

    private void configuraLista() {
        ListView listaDeContatos = findViewById(R.id.activity_lista_contatos_listview);
        listaContatosView.configuraAdapter(listaDeContatos);
        configuraListenerDeCliquePorItem(listaDeContatos);
        registerForContextMenu(listaDeContatos);
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeContatos) {
        listaDeContatos.setOnItemClickListener((adapterView, view, posicao, id) -> {
            Contato contatoEscolhido = (Contato) adapterView.getItemAtPosition(posicao);
            abreFormularioModoEditaAluno(contatoEscolhido);
        });
    }

    private void abreFormularioModoEditaAluno(Contato contato) {
        Intent vaiParaFormularioActivity = new Intent(ListaContatosActivity.this, FormularioContatoActivity.class);
        vaiParaFormularioActivity.putExtra(CHAVE_CONTATO, contato);
        startActivity(vaiParaFormularioActivity);
    }

}
