package com.compasso.agenda;

import android.app.Application;

import com.compasso.agenda.dao.ContatoDAO;
import com.compasso.agenda.model.Contato;

public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        criaContatosDeTeste();
    }

    private void criaContatosDeTeste() {
        ContatoDAO dao = new ContatoDAO();
        dao.salva(new Contato("roberto", "151516", "jkfjdskjfkjdskf"));
        dao.salva(new Contato("clara", "151516", "jkfjdskjfkjdskf"));
    }
}
