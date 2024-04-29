package com.example.ava2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ObjetoDetailsActivity extends AppCompatActivity {
    private SQLiteHelper dbHelper; // Declaração da variável dbHelper

    private EditText editTextNome;
    private EditText editTextQuantidade;
    private EditText editTextPreco;
    private CheckBox checkBoxDisponivel;
    private Button buttonSalvar;
    private Button buttonExcluir;
    private Button buttonOrganizar; // Nova referência para o botão

    private ObjetoDataSource dataSource;
    private long objetoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto_details);
        dbHelper = new SQLiteHelper(this);

        editTextNome = findViewById(R.id.editTextNome);
        editTextQuantidade = findViewById(R.id.editTextQuantidade);
        editTextPreco = findViewById(R.id.editTextPreco);
        checkBoxDisponivel = findViewById(R.id.checkBoxDisponivel);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonExcluir = findViewById(R.id.buttonExcluir);
        buttonOrganizar = findViewById(R.id.buttonOrganizar); // Referência para o novo botão

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

        buttonOrganizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                organizarBancoDados();
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

    // Método para organizar o banco de dados (atualizado para usar ObjetoDataSource)
    private void organizarBancoDados() {
        dataSource.open(); // Abrir a conexão com o banco de dados
        List<Objeto> objetos = dataSource.getAllObjetos(); // Obter todos os objetos do banco de dados
        StringBuilder dataBuilder = new StringBuilder(); // StringBuilder para armazenar os dados formatados

        // Verificar se a lista de objetos não está vazia
        if (!objetos.isEmpty()) {
            // Loop através dos objetos
            for (Objeto objeto : objetos) {
                // Adicionar os dados do objeto ao StringBuilder
                dataBuilder.append("ID: ").append(objeto.getId())
                        .append(", Nome: ").append(objeto.getNome())
                        .append(", Quantidade: ").append(objeto.getQuantidade())
                        .append(", Preço: ").append(objeto.getPreco())
                        .append(", Disponível: ").append(objeto.isDisponivel() ? "Sim" : "Não")
                        .append("\n");
            }

            // Exibir um Toast com os dados da base
            String databaseData = dataBuilder.toString();
            Toast.makeText(this, "Dados da base:\n" + databaseData, Toast.LENGTH_LONG).show();
        } else {
            // Se a lista de objetos estiver vazia, exibir um Toast indicando que não há dados
            Toast.makeText(this, "Não há dados na base de dados.", Toast.LENGTH_SHORT).show();
        }

        // Fechar a conexão com o banco de dados
        dataSource.close();
    }

}
