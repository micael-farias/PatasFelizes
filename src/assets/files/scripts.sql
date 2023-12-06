CREATE TABLE IF NOT EXISTS Animais (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    Nome VARCHAR(50) UNIQUE NOT NULL,
    DataNascimento TIMESTAMP,
    Foto BLOB,
    Descricao TEXT,
    Sexo CHAR CHECK (Sexo IN ('M', 'F', 'N')),
    Castrado BOOLEAN NOT NULL,
    Status TEXT CHECK (Status IN ('PA', 'A', 'F', 'D')) NOT NULL,
    DataCadastro TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS Doacoes (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    Doador VARCHAR(255) NOT NULL,
    Valor REAL NOT NULL,
    Data TIMESTAMP NOT NULL,
    FotoComprovante BLOB,
    DataCadastro TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS Voluntarios (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    Nome VARCHAR(255) UNIQUE NOT NULL,
    Foto BLOB,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Telefone VARCHAR(11) UNIQUE NOT NULL,
    DataCadastro TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS Despesas (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    Descricao TEXT NOT NULL,
    Valor REAL NOT NULL,
    Data TIMESTAMP NOT NULL,
    Tipo VARCHAR(255) NOT NULL,
    FotoComprovante BLOB,
    Realizada BOOLEAN NOT NULL,
    DataCadastro TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS Procedimentos (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    Descricao TEXT NOT NULL,
    IdAnimal INTEGER,
    Data TIMESTAMP NOT NULL,
    Tipo VARCHAR(255) NOT NULL,
    IdVoluntario INTEGER,
    IdDespesa INTEGER,
    Realizado BOOLEAN NOT NULL,
    DataCadastro TIMESTAMP NOT NULL,
    FOREIGN KEY (IdAnimal) REFERENCES Animais(Id) ON DELETE CASCADE,
    FOREIGN KEY (IdDespesa) REFERENCES Despesas(Id),
    FOREIGN KEY (IdVoluntario) REFERENCES Voluntarios(Id)
);

CREATE TABLE IF NOT EXISTS Adotantes (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    Nome VARCHAR(255) UNIQUE NOT NULL,
    Contato VARCHAR(255) UNIQUE NOT NULL,
    CEP VARCHAR(10), 
    Cidade VARCHAR(255),
    Rua VARCHAR(255),
    Bairro VARCHAR(255),
    Complemento VARCHAR(255),
    Numero INT NOT NULL,
    DataCadastro TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS Adocoes (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    IdAnimal INTEGER NOT NULL,
    IdAdotante INTEGER NOT NULL,
    DataCadastro TIMESTAMP NOT NULL,
    FOREIGN KEY (IdAnimal) REFERENCES Animais(Id) ON DELETE CASCADE,
    FOREIGN KEY (IdAdotante) REFERENCES Adotantes(Id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Alteracoes (
    Id INTEGER PRIMARY KEY AUTOINCREMENT,
    TabelaAfetada TEXT NOT NULL,
    Descritor VARCHAR(255),
    IdRegistroAfetado INTEGER NOT NULL,
    ColunaAlterada TEXT NOT NULL,
    ValorAntigo TEXT,
    ValorNovo TEXT,
    DataAlteracao TIMESTAMP NOT NULL
);

CREATE VIEW IF NOT EXISTS ViewDespesas AS
SELECT D.*, P.IDANIMAL as IdAnimal FROM DESPESAS D
LEFT JOIN PROCEDIMENTOS P ON D.ID = P.IDDESPESA
WHERE 1 = 1;

CREATE VIEW IF NOT EXISTS ViewProcedimentos AS
SELECT P.*, A.Nome as NomeAnimal, v.Nome as NomeVoluntario
FROM PROCEDIMENTOS P
LEFT JOIN ANIMAIS A ON P.IDANIMAL = A.ID
LEFT JOIN VOLUNTARIOS V ON P.IDVOLUNTARIO = V.ID
LEFT JOIN DESPESAS D ON P.IDDESPESA = D.ID
WHERE 1 = 1;

CREATE TRIGGER IF NOT EXISTS AnimaisAposAtualizar_Nome
AFTER UPDATE OF Nome ON Animais
FOR EACH ROW
WHEN NEW.Nome != OLD.Nome
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Animais', NEW.Id, OLD.Nome,'Nome', OLD.Nome, NEW.Nome, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS AnimaisAposAtualizar_Sexo
AFTER UPDATE OF Sexo ON Animais
FOR EACH ROW
WHEN NEW.Sexo != OLD.Sexo
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Animais', NEW.Id, OLD.Nome,'Sexo', OLD.Sexo, NEW.Sexo, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS AnimaisAposAtualizar_Castrado
AFTER UPDATE OF Castrado ON Animais
FOR EACH ROW
WHEN NEW.Castrado != OLD.Castrado
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Animais', NEW.Id, OLD.Nome,'Castrado', OLD.Castrado, NEW.Castrado, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS AnimaisAposAtualizar_Status
AFTER UPDATE OF Status ON Animais
FOR EACH ROW
WHEN NEW.Status != OLD.Status
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Animais', NEW.Id, OLD.Nome,'Status', OLD.Status, NEW.Status, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS DoacoesAposAtualizar_Doador
AFTER UPDATE OF Doador ON Doacoes
FOR EACH ROW
WHEN NEW.Doador != OLD.Doador
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Doacoes', NEW.Id, OLD.Doador,'Doador', OLD.Doador, NEW.Doador, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS DoacoesAposAtualizar_Valor
AFTER UPDATE OF Valor ON Doacoes
FOR EACH ROW
WHEN NEW.Valor != OLD.Valor
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Doacoes', NEW.Id, OLD.Doador,'Valor', OLD.Valor, NEW.Valor, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS DoacoesAposAtualizar_Data
AFTER UPDATE OF Data ON Doacoes
FOR EACH ROW
WHEN NEW.Data != OLD.Data
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Doacoes', NEW.Id, OLD.Doador,'Data', OLD.Data, NEW.Data, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS VoluntariosAposAtualizar_Email
AFTER UPDATE OF Email ON Voluntarios
FOR EACH ROW
WHEN NEW.Email != OLD.Email
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Voluntarios', NEW.Id, OLD.Nome,'Email', OLD.Email, NEW.Email, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS VoluntariosAposAtualizar_Telefone
AFTER UPDATE OF Telefone ON Voluntarios
FOR EACH ROW
WHEN NEW.Telefone != OLD.Telefone
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Voluntarios', NEW.Id, OLD.Nome,'Telefone', OLD.Telefone, NEW.Telefone, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS DespesasAposAtualizar_Valor
AFTER UPDATE OF Valor ON Despesas
FOR EACH ROW
WHEN NEW.Valor != OLD.Valor
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Despesas', NEW.Id, OLD.Descricao,'Valor', OLD.Valor, NEW.Valor, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS DespesasAposAtualizar_Data
AFTER UPDATE OF Data ON Despesas
FOR EACH ROW
WHEN NEW.Data != OLD.Data
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Despesas', NEW.Id, OLD.Descricao,'Data', OLD.Data, NEW.Data, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS DespesasAposAtualizar_Tipo
AFTER UPDATE OF Tipo ON Despesas
FOR EACH ROW
WHEN NEW.Tipo != OLD.Tipo
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Despesas', NEW.Id, OLD.Descricao,'Tipo', OLD.Tipo, NEW.Tipo, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS ProcedimentosAposAtualizar_IdVoluntario
AFTER UPDATE OF IdVoluntario ON Procedimentos
FOR EACH ROW
WHEN NEW.IdVoluntario != OLD.IdVoluntario
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Procedimentos', NEW.Id, OLD.Descricao,'IdVoluntario', OLD.IdVoluntario, NEW.IdVoluntario, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS ProcedimentosAposAtualizar_IdAnimal
AFTER UPDATE OF IdAnimal ON Procedimentos
FOR EACH ROW
WHEN NEW.IdAnimal != OLD.IdAnimal
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Procedimentos', NEW.Id, OLD.Descricao,'IdAnimal', OLD.IdAnimal, NEW.IdAnimal, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS ProcedimentosAposAtualizar_Data
AFTER UPDATE OF Data ON Procedimentos
FOR EACH ROW
WHEN NEW.Data != OLD.Data
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Procedimentos', NEW.Id, OLD.Descricao,'Data', OLD.Data, NEW.Data, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS ProcedimentosAposAtualizar_Tipo
AFTER UPDATE OF Tipo ON Procedimentos
FOR EACH ROW
WHEN NEW.Tipo != OLD.Tipo
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Procedimentos', NEW.Id, OLD.Descricao,'Tipo', OLD.Tipo, NEW.Tipo, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS AdotantesAposAtualizar_Nome
AFTER UPDATE OF Nome ON Adotantes
FOR EACH ROW
WHEN NEW.Nome != OLD.Nome
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Adotantes', NEW.Id, OLD.Nome,'Nome', OLD.Nome, NEW.Nome, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS AdotantesAposAtualizar_Contato
AFTER UPDATE OF Contato ON Adotantes
FOR EACH ROW
WHEN NEW.Contato != OLD.Contato
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Adotantes', NEW.Id, OLD.Nome,'Contato', OLD.Contato, NEW.Contato, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS AdotantesAposAtualizar_CEP
AFTER UPDATE OF CEP ON Adotantes
FOR EACH ROW
WHEN NEW.CEP != OLD.CEP
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Adotantes', NEW.Id, OLD.Nome,'CEP', OLD.CEP, NEW.CEP, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS AdotantesAposAtualizar_Cidade
AFTER UPDATE OF Cidade ON Adotantes
FOR EACH ROW
WHEN NEW.Cidade != OLD.Cidade
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Adotantes', NEW.Id, OLD.Nome,'Cidade', OLD.Cidade, NEW.Cidade, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS AdotantesAposAtualizar_Rua
AFTER UPDATE OF Rua ON Adotantes
FOR EACH ROW
WHEN NEW.Rua != OLD.Rua
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Adotantes', NEW.Id, OLD.Nome,'Rua', OLD.Rua, NEW.Rua, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS AdotantesAposAtualizar_Bairro
AFTER UPDATE OF Bairro ON Adotantes
FOR EACH ROW
WHEN NEW.Bairro != OLD.Bairro
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Adotantes', NEW.Id, OLD.Nome,'Bairro', OLD.Bairro, NEW.Bairro, datetime('now', 'localtime') );
END;

CREATE TRIGGER IF NOT EXISTS AdotantesAposAtualizar_Numero
AFTER UPDATE OF Numero ON Adotantes
FOR EACH ROW
WHEN NEW.Numero != OLD.Numero
BEGIN
        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)
        VALUES ('Adotantes', NEW.Id, OLD.Nome,'Numero', OLD.Numero, NEW.Numero, datetime('now', 'localtime') );
END;
