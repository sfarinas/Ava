package com.example.ava2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ObjetoDetailsActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextQuantidade;
    private EditText editTextPreco;
    private CheckBox checkBoxDisponivel;
    private Button buttonSalvar;
    private Button buttonExcluir;

    private ObjetoDataSource dataSource;
    private long objetoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto_details);

        editTextNome = findViewById(R.id.editTextNome);
        editTextQuantidade = findViewById(R.id.editTextQuantidade);
        editTextPreco = findViewById(R.id.editTextPreco);
        checkBoxDisponivel = findViewById(R.id.checkBoxDisponivel);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonExcluir = findViewById(R.id.buttonExcluir);

        dataSource = new ObjetoDataSource(this);

        Intent intent = getIntent();
        objetoId = intent.getLongExtra("objeto_id", -1);

        Objeto objeto;
        if (objetoId != -1) {
            // Carrega os dados do objeto existente para edição
            dataSource.open();
            objeto = dataSource.getObjeto(objetoId);
            dataSource.close();

            if (objeto != null) {
                editTextNome.setText(objeto.getNome());
                editTextQuantidade.setText(String.valueOf(objeto.getQuantidade()));
                editTextPreco.setText(String.valueOf(objeto.getPreco()));
                checkBoxDisponivel.setChecked(objeto.isDisponivel());
            }
        } else {
            // Cria um novo objeto
            objeto = new Objeto();
        }

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAlteracoes();
            }
        });

        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirObjeto();
            }
        });
    }

    private void salvarAlteracoes() {
        String nome = editTextNome.getText().toString();
        int quantidade = Integer.parseInt(editTextQuantidade.getText().toString());
        double preco = Double.parseDouble(editTextPreco.getText().toString());
        boolean disponivel = checkBoxDisponivel.isChecked();

        Objeto objeto = new Objeto(objetoId, nome, quantidade, preco, disponivel);

        dataSource.open();
        if (objetoId == -1) {
            dataSource.criarObjeto(objeto);
        } else {
            dataSource.atualizarObjeto(objeto);
        }
        dataSource.close();

        finish(); // Fecha a atividade após salvar as alterações
    }

    private void excluirObjeto() {
        dataSource.open();
        dataSource.deletarObjeto(objetoId);
        dataSource.close();

        finish(); // Fecha a atividade após excluir o objeto
    }
}
