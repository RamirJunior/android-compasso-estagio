package com.compasso.agenda.asynctask;

import com.compasso.agenda.database.dao.ContatoDAO;
import com.compasso.agenda.database.dao.TelefoneDAO;
import com.compasso.agenda.model.Contato;
import com.compasso.agenda.model.Telefone;
import com.compasso.agenda.model.TipoTelefone;

import java.util.List;

public class EditaContatoTask extends BaseContatoComTelefoneTask {

    private final ContatoDAO contatoDAO;
    private final Contato contato;
    private final Telefone telefoneFixo;
    private final Telefone telefoneCelular;
    private final TelefoneDAO telefoneDAO;
    private final List<Telefone> telefonesDoContato;

    public EditaContatoTask(ContatoDAO contatoDAO,
                            Contato contato,
                            Telefone telefoneFixo,
                            Telefone telefoneCelular,
                            TelefoneDAO telefoneDAO,
                            List<Telefone> telefonesDoContato,
                            FinalizadaListener listener) {
        super(listener);
        this.contatoDAO = contatoDAO;
        this.contato = contato;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
        this.telefoneDAO = telefoneDAO;
        this.telefonesDoContato = telefonesDoContato;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        contatoDAO.edita(contato);
        vinculaContatoComTelefone(contato.getId(), telefoneFixo, telefoneCelular);
        atualizaIdsDosTelefones(telefoneFixo, telefoneCelular);
        telefoneDAO.atualiza(telefoneFixo, telefoneCelular);
        return null;
    }

    private void atualizaIdsDosTelefones(Telefone telefoneFixo, Telefone telefoneCelular) {
        for (Telefone telefone :
                telefonesDoContato) {
            if (telefone.getTipo() == TipoTelefone.FIXO){
                telefoneFixo.setId(telefone.getId());
            } else {
                telefoneCelular.setId(telefone.getId());
            }
        }
    }
}
