package com.compasso.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.compasso.agenda.R;
import com.compasso.agenda.asynctask.BuscaTodosTelefonesDoContatoTask;
import com.compasso.agenda.asynctask.EditaContatoTask;
import com.compasso.agenda.asynctask.SalvaContatoTask;
import com.compasso.agenda.database.AgendaDataBase;
import com.compasso.agenda.database.dao.ContatoDAO;
import com.compasso.agenda.database.dao.TelefoneDAO;
import com.compasso.agenda.model.Contato;
import com.compasso.agenda.model.Telefone;
import com.compasso.agenda.model.TipoTelefone;

import java.util.List;

import static com.compasso.agenda.ui.activity.ConstantesActivities.CHAVE_CONTATO;

public class FormularioContatoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVO_CONTATO = "Novo contato";
    private static final String TITULO_APPBAR_EDITA_CONTATO = "Edita contato";
    private EditText campoNome;
    private EditText campoTelefoneFixo;
    private EditText campoTelefoneCelular;
    private EditText campoEmail;
    private ContatoDAO contatoDAO;
    private Contato contato;
    private TelefoneDAO telefoneDAO;
    private List<Telefone> telefonesDoContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_contato);
        AgendaDataBase database = AgendaDataBase.getInstance(this);
        contatoDAO = database.getRoomContatoDAO();
        telefoneDAO = database.getTelefoneDAO();
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
        campoEmail.setText(contato.getEmail());
        preencheCamposDeTelefone();
    }

    private void preencheCamposDeTelefone() {
        new BuscaTodosTelefonesDoContatoTask(telefoneDAO, contato, telefones -> {
            this.telefonesDoContato = telefones;
            for (Telefone telefone :
                    telefonesDoContato) {
                if (telefone.getTipo() == TipoTelefone.FIXO ){
                    campoTelefoneFixo.setText(telefone.getNumero());
                } else {
                    campoTelefoneCelular.setText(telefone.getNumero());
                }
            }
        }).execute();
    }

    private void finalizaFormulario() {
        preencheContato();

        Telefone telefoneFixo = criaTelefone(campoTelefoneFixo, TipoTelefone.FIXO);
        Telefone telefoneCelular = criaTelefone(campoTelefoneCelular, TipoTelefone.CELULAR);

        if(contato.temIdValido()){
            editaContato(telefoneFixo, telefoneCelular);
        } else {
            salvaContato(telefoneFixo, telefoneCelular);
        }
    }

    private Telefone criaTelefone(EditText campoTelefoneFixo, TipoTelefone fixo) {
        String numeroFixo = campoTelefoneFixo.getText().toString();
        return new Telefone(numeroFixo, fixo);
    }

    private void salvaContato(Telefone telefoneFixo, Telefone telefoneCelular) {
        new SalvaContatoTask(contatoDAO, contato, telefoneFixo, telefoneCelular, telefoneDAO, this::finish).execute();
    }

    private void editaContato(Telefone telefoneFixo, Telefone telefoneCelular) {
        new EditaContatoTask(contatoDAO, contato, telefoneFixo, telefoneCelular, telefoneDAO, telefonesDoContato, this::finish).execute();
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_contato_nome);
        campoTelefoneFixo = findViewById(R.id.activity_formulario_contato_telefone_fixo);
        campoTelefoneCelular = findViewById(R.id.activity_formulario_contato_telefone_celular);
        campoEmail = findViewById(R.id.activity_formulario_contato_email);
    }

    private void preencheContato() {
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();

        contato.setNome(nome);
        contato.setEmail(email);
    }
}