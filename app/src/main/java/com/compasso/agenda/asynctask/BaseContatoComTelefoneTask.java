package com.compasso.agenda.asynctask;

import android.os.AsyncTask;

import com.compasso.agenda.model.Telefone;

abstract class BaseContatoComTelefoneTask extends AsyncTask<Void, Void, Void> {

    private final FinalizadaListener listener;

    protected BaseContatoComTelefoneTask(FinalizadaListener listener) {
        this.listener = listener;
    }

    protected void vinculaContatoComTelefone(int contatoId, Telefone... telefones) {
        for (Telefone telefone :
                telefones) {
            telefone.setContatoId(contatoId);
        }
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        listener.quandoFinalizada();
    }

    public interface FinalizadaListener {
        void quandoFinalizada();
    }


}
