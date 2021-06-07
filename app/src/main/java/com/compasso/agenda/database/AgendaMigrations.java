package com.compasso.agenda.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

class AgendaMigrations {

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Contato ADD COLUMN sobrenome TEXT");
        }
    };
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Cria nova tabela com as informações desejadas
            database.execSQL("CREATE TABLE IF NOT EXISTS `Contato_novo` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`nome` TEXT, " +
                    "`telefone` TEXT, " +
                    "`email` TEXT)");

            // Copiar dados da tabela antiga para a nova
            database.execSQL("INSERT INTO Contato_novo (id, nome, telefone, email) " +
                    "SELECT id, nome, telefone, email FROM Contato");

            // Remove tabela antiga
            database.execSQL("DROP TABLE Contato");

            // Renomear tabela nova com nome da tabela antiga
            database.execSQL("ALTER TABLE Contato_novo RENAME TO Contato");
        }
    };
    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Contato ADD COLUMN momentoDeCadastro INTEGER");
        }
    };
    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate (SupportSQLiteDatabase database){
            database.execSQL("CREATE TABLE IF NOT EXISTS `Contato_novo` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`nome` TEXT, " +
                    "`telefoneFixo` TEXT, " +
                    "`telefoneCelular` TEXT, " +
                    "`email` TEXT, " +
                    "`momentoDeCadastro` INTEGER)");

            database.execSQL("INSERT INTO Contato_novo (id, nome, telefoneFixo, email, momentoDeCadastro) " +
                    "SELECT id, nome, telefone, email, momentoDeCadastro FROM Contato");

            database.execSQL("DROP TABLE Contato");

            database.execSQL("ALTER TABLE Contato_novo RENAME TO Contato");
        }
    };
    static final Migration[] TODAS_MIGRATIONS = {MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5};

}
