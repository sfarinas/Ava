package com.example.ava2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewObjetos;
    private Button buttonAdicionar;
    private List<Objeto> listaObjetos;
    private ObjetoDataSource dataSource;
    private TextView textViewSomaPrecos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar os componentes da interface
        listViewObjetos = findViewById(R.id.listViewObjetos);
        buttonAdicionar = findViewById(R.id.buttonAdicionar);
        textViewSomaPrecos = findViewById(R.id.textViewSomaPrecos);

        // Inicializar o dataSource
        dataSource = new ObjetoDataSource(this);
        dataSource.open();

        // Carregar a lista de objetos do banco de dados
        listaObjetos = dataSource.getAllObjetos();

        // Adaptador para a ListView
        ArrayAdapter<Objeto> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listaObjetos);
        listViewObjetos.setAdapter(adapter);

        // Listener para o clique em um item da lista
        listViewObjetos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aqui você pode implementar o código para exibir os detalhes do objeto clicado
                Objeto objetoSelecionado = (Objeto) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, ObjetoDetailsActivity.class);
                intent.putExtra("objeto_id", objetoSelecionado.getId());
                startActivity(intent);
            }
        });

        // Listener para o botão "Adicionar Objeto"
        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir a tela de detalhes para adicionar um novo objeto
                Intent intent = new Intent(MainActivity.this, ObjetoDetailsActivity.class);
                startActivity(intent);
            }
        });

        // Calcular e exibir a soma dos preços
        exibirSomaPrecos();
    }

    // Método para calcular e exibir a soma dos preços dos objetos disponíveis
    private void exibirSomaPrecos() {
        // Calcular a soma dos preços dos objetos disponíveis
        double somaPrecos = calcularSomaPrecos(listaObjetos);

        // Atualizar o texto da textViewSomaPrecos com o valor calculado
        textViewSomaPrecos.setText("Soma dos Preços: " + somaPrecos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Atualizar a lista de objetos ao retornar para a atividade principal
        listaObjetos.clear();
        listaObjetos.addAll(dataSource.getAllObjetos());
        ((ArrayAdapter<Objeto>)listViewObjetos.getAdapter()).notifyDataSetChanged();

        // Atualizar a soma dos preços
        exibirSomaPrecos();
    }

    // Método para calcular a soma dos preços dos objetos disponíveis
    private double calcularSomaPrecos(List<Objeto> objetos) {
        double somaPrecos = 0.0;
        for (Objeto objeto : objetos) {
            if (objeto.isDisponivel()) {
                somaPrecos += objeto.getPreco() * objeto.getQuantidade();
            }
        }
        return somaPrecos;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Fechar o dataSource ao destruir a atividade
        dataSource.close();
    }
}
