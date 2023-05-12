package com.example.agendaprincipal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class Banco_dados extends SQLiteOpenHelper {
    public static final int VERSAO_BANCO = 1;
    public static final String BancoAgenda= "db_agenda";

    public Banco_dados(Context context){
        super(context,BancoAgenda, null, VERSAO_BANCO);
    }

    public static final String TABELA_PESSOA= "tb_pessoa";

    public static final String COLUNA_CODIGO= "codigo";
    public static final String COLUNA_NOME= "nome";
    public static final String COLUNA_EMAIL= "email";
    public static final String COLUNA_TELEFONE= "telefone";
    public static final String COLUNA_ENDERECO= "endereço";

    @Override
        public void onCreate(SQLiteDatabase db) { //Para criar a tabela

        String CRIAR_TABELA = " CREATE TABLE " + TABELA_PESSOA + "(" + COLUNA_CODIGO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUNA_NOME + "TEXT," + COLUNA_EMAIL + "TEXT," + COLUNA_TELEFONE + "TEXT," + COLUNA_ENDERECO + "TEXT)";
        // Foi usado o codigo para o MYSQL

        db.execSQL(CRIAR_TABELA); // usando um metodo para criando a tabela

    }

    @Override

        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

    }

     void addPessoa(Pessoa pessoa){ // Criando o metodo pessoa que vai inserir os valores do aplicativo para o banco

            SQLiteDatabase db = this.getWritableDatabase(); // Varial local

            ContentValues valor = new ContentValues(); // Instanciando as propriedades, assim ela tem as caractristicas do metodos e atributos da classe

            // Objetos valores, pegando dados do front
            valor.put(COLUNA_NOME, pessoa.getNome());
            valor.put(COLUNA_EMAIL, pessoa.getEmail());
            valor.put(COLUNA_TELEFONE, pessoa.getTelefone());
            valor.put(COLUNA_ENDERECO, pessoa.getEndereco());

            db.insert(TABELA_PESSOA, null, valor); // objeto db usando um metodo, para inserir os dados
            db.close(); // objeto para fechar o banco

     }

     void apagarPessoa(Pessoa pessoa) { // Metódo para apagar algum dado da tabela

        SQLiteDatabase db =this.getWritableDatabase(); // Criando uma variavel local para ter acesso ao banco
         db.delete(TABELA_PESSOA, COLUNA_CODIGO + " =?", new String[]{ // usando metodo SQLite
            String.valueOf(pessoa.getCodigo()) //  usando metodo e  objeto de outro metodo
         });

         db.close();

     }

     Pessoa selecionarPessoa(int codigo) { // Criando metodo selecionar pessoa

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_PESSOA, new String[]{COLUNA_CODIGO, COLUNA_NOME, COLUNA_EMAIL, COLUNA_TELEFONE, COLUNA_ENDERECO},
                // instanciamos o cursor em uma variavel de mesmo nome, usamo um metodo dentro do objeto db que faz uma busca no banco de dados onde ele vai precisar apenas do id da pessoa para mostrar os outros valores
                COLUNA_CODIGO + " ?=", new String[]{String.valueOf(codigo)}, null, null, null, null);

            if(cursor != null) {
                cursor.moveToFirst(); // se o cursor tiver algum valor ele vai retornar pro inicip
            }

            Pessoa pessoa = new Pessoa(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)); // instanciando a classe pessoa no objeto pessoa

            return pessoa; // retorna o valor de pessoa

     }

     void atualizar(Pessoa pessoa){ // Criação do método atualizarPessoa, que irá atualizar os dados da pessoa pelo front-end e depois atualizar no banco de dados

         SQLiteDatabase db = this.getWritableDatabase();

         ContentValues valor = new ContentValues();

         // objeto valor usando um metodo do ContentValues para pegar dados do front-end

         valor.put(COLUNA_CODIGO, pessoa.getCodigo());
         valor.put(COLUNA_NOME, pessoa.getNome());
         valor.put(COLUNA_EMAIL, pessoa.getEmail());
         valor.put(COLUNA_TELEFONE, pessoa.getTelefone());
         valor.put(COLUNA_ENDERECO, pessoa.getEndereco());

        db.update(TABELA_PESSOA, valor, COLUNA_CODIGO + " =?", new String[]{String.valueOf(pessoa.getCodigo())}); // objeto db usando um método do SQLitedatabase para atualizar a tabela
        db.close();

     }
}
