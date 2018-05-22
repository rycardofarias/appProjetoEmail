package com.teste1.email;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {

    EditText txtNome, txtEmail;
    Button btnEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        txtNome = (EditText)findViewById(R.id.txtNome);
        txtEmail = (EditText)findViewById(R.id.txtEmail);

        btnEmail = (Button)findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                enviarEmail();
            }
        });
    }

    private void enviarEmail(){

        final String nome = txtNome.getText().toString();
        final String email = txtEmail.getText().toString();

        if(isOnline()) {
            new Thread(new Runnable(){
                @Override
                public void run() {
                    Mail m = new Mail("seunome@seuemail.com.br", "suaSenha");

                    String[] toArr = {email};
                    m.setTo(toArr);

                    m.setFrom("seunome@seuemail.com.br");
                    m.setSubject("Email de teste do seu app");
                    m.setBody(nome + " recebeu um email com sucesso!");

                    try {
                        //m.addAttachment("pathDoAnexo");//anexo opcional
                        m.send();
                    }
                    catch(RuntimeException rex){ }//erro ignorado
                    catch(Exception e) {
                        //tratar algum outro erro aqui
                    }

                    System.exit(0);
                }
            }).start();
        }
        else {
            Toast.makeText(getApplicationContext(), "Não estava online para enviar e-mail!", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }
    }

    public boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        catch(Exception ex){
            Toast.makeText(getApplicationContext(), "Erro ao verificar se estava online! ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
