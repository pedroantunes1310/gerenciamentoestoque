package com.cursoandroid.wms;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private EditText etNomeProduto, etDescricao, etPreco, etQuantidade;
    private Button btnSalvar;
    private ProdutoDao produtoDao;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa o banco de dados e o DAO
        ProdutoDatabase db = ProdutoDatabase.getInstance(this);
        produtoDao = db.produtoDao();

        // Ligação entre os elementos da interface e o código Java
        etNomeProduto = findViewById(R.id.etNomeProduto);
        etDescricao = findViewById(R.id.etDescricao);
        etPreco = findViewById(R.id.etPreco);
        etQuantidade = findViewById(R.id.etQuantidade);
        btnSalvar = findViewById(R.id.btnSalvar);

        // Configurando o clique no botão de salvar
        btnSalvar.setOnClickListener(v -> {
            String nomeProduto = etNomeProduto.getText().toString().trim();
            String descricao = etDescricao.getText().toString().trim();
            String preco = etPreco.getText().toString().trim();
            String quantidade = etQuantidade.getText().toString().trim();

            // Validação dos campos
            if (nomeProduto.isEmpty() || preco.isEmpty() || quantidade.isEmpty()) {
                Toast.makeText(MainActivity.this, "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Criação do objeto Produto
            Produto produto = new Produto(nomeProduto, descricao, Double.parseDouble(preco), Integer.parseInt(quantidade));

            // Salvando o produto no banco de dados usando Room
            saveProduto(produto);
        });
    }

    // Método para salvar o produto no banco de dados
    private void saveProduto(Produto produto) {
        if (produtoDao != null) {
            executorService.execute(() -> {
                produtoDao.insert(produto);
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Produto salvo com sucesso!", Toast.LENGTH_SHORT).show());
            });
        } else {
            Toast.makeText(MainActivity.this, "ProdutoDao não está inicializado.", Toast.LENGTH_SHORT).show();
        }
    }
}
