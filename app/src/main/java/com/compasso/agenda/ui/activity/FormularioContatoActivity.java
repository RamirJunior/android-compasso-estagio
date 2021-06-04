package com.compasso.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.compasso.agenda.R;
import com.compasso.agenda.database.AgendaDataBase;
import com.compasso.agenda.database.dao.ContatoDAO;
import com.compasso.agenda.model.Contato;

import static com.compasso.agenda.ui.activity.ConstantesActivities.CHAVE_CONTATO;

public class FormularioContatoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVO_CONTATO = "Novo contato";
    private static final String TITULO_APPBAR_EDITA_CONTATO = "Edita contato";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private ContatoDAO dao;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_contato);
        AgendaDataBase database = AgendaDataBase.getInstance(this);
        dao = database.getRoomContatoDAO();
        inicializacaoDosCampos();
        carregaContato();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.activity_formulario_contato_menu_salvar){
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_contato_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void carregaContato() {
        Intent dados = getIntent();
        if(dados.hasExtra(CHAVE_CONTATO)){
            setTitle(TITULO_APPBAR_EDITA_CONTATO);
            contato = (Contato) dados.getSerializableExtra(CHAVE_CONTATO);
            preencheCampos();
        }else{
            setTitle(TITULO_APPBAR_NOVO_CONTATO);
            contato = new Contato();
        }
    }

    private void preencheCampos() {
        campoNome.setText(contato.getNome());
        campoTelefone.setText(contato.getTelefone());
        campoEmail.setText(contato.getEmail());
    }

    private void finalizaFormulario() {
        preencheContato();
        if(contato.temIdValido()){
            dao.edita(contato);
        } else {
            dao.salva(contato);
        }
        finish();
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_contato_nome);
        campoTelefone = findViewById(R.id.activity_formulario_contato_telefone);
        campoEmail = findViewById(R.id.activity_formulario_contato_email);
    }



    private void preencheContato() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        contato.setNome(nome);
        contato.setTelefone(telefone);
        contato.setEmail(email);
    }
}