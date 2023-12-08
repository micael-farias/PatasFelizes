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
SELECT P.*, A.Nome as NomeAnimal, A.Id as IdAnimal, v.Nome as NomeVoluntario, D.valor as valorDespesa
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

DELETE FROM Voluntarios;
INSERT INTO Voluntarios VALUES ('2', 'Fernanda', X'', 'fernandaoliveira@gmaill.com', '(88)998647125', '1700620348575');
INSERT INTO Voluntarios VALUES ('3', 'Graziella Lima', X'', 'graziellarodrigues461@gmail.com', '8282989328', '1700671728435');
INSERT INTO Voluntarios VALUES ('4', 'Sabrina Santos', X'', 'sabrinadsd@alu.ufc.brr', '8289392289', '1700679638768');
INSERT INTO Voluntarios VALUES ('6', 'Alexandre Toledo', X'', 'alexandrehtoledoh@gmail.com', '11988345674', '1701894004372');
INSERT INTO Voluntarios VALUES ('7', 'Dinah Toledo', X'', 'dinatoled@gmail.com', '11983649202', '1701896470109');
DELETE FROM Adotantes;
INSERT INTO Adotantes VALUES ('1', 'Wania Kelly', '88996581247', '', '', '', '', '', '', '1701260510162');
INSERT INTO Adotantes VALUES ('2', 'Fernanda Oliveira', '88998756214', '', '', '', '', '', '', '1701260810080');
INSERT INTO Adotantes VALUES ('9', 'Joâo Victor', '85985670680', '', '', '', '', '', '', '1701798762968');
INSERT INTO Adotantes VALUES ('10', 'Milena Duarte', '2839238982', '', '', '', '', '', '', '1701824924664');
INSERT INTO Adotantes VALUES ('11', 'Maria Arruda do Nascimento', '85998743562', '', '', '', '', '', '', '1701896677581');
INSERT INTO Adotantes VALUES ('12', 'Mika', '85998293216', '', '', '', '', '', '', '1701912061943');
DELETE FROM Adocoes;
INSERT INTO Adocoes VALUES ('9', '14', '1', '1701260772232');
INSERT INTO Adocoes VALUES ('10', '13', '2', '1701260810080');
INSERT INTO Adocoes VALUES ('18', '16', '9', '1701798762974');
INSERT INTO Adocoes VALUES ('20', '15', '10', '1701824978606');
INSERT INTO Adocoes VALUES ('22', '18', '10', '1702059032326');
DELETE FROM Animais;
INSERT INTO Animais VALUES ('10', 'Belinha', '1462071600000', X'', '', 'F', '0', 'D', '1700618884849');
INSERT INTO Animais VALUES ('13', 'Ravena', '1604199600000', X'', '', 'F', '0', 'A', '1701257431359');
INSERT INTO Animais VALUES ('14', 'Tangerino', '1675220400000', X'', '', 'M', '0', 'A', '1701258630197');
INSERT INTO Animais VALUES ('15', 'Fred', '1572577200000', X'', 'Achado no meio da estrada.', 'M', '0', 'A', '1701259000078');
INSERT INTO Animais VALUES ('16', 'Júpiter', '1654052400000', X'', '', 'M', '1', 'A', '1701259219773');
INSERT INTO Animais VALUES ('17', 'Caramelo', '1541041200000', X'', '', 'M', '0', 'PA', '1701259326147');
INSERT INTO Animais VALUES ('18', 'Bentinho', '1682910000000', X'', 'O pessoal estava falando sobre um gatinho que tinha acabado de entrar no prédio e estavam pensando em dar leite. Imediatamente decidimos descer para ver o gatinho e pronto ✨ Ele estava simplesmente na portaria amassando pãozinho no sapato do porteiro, cheeeeeio de fungo, não aguentamos! Olhamos uma para a outra e rimos de nervoso, mas é claro que assumimos o compromisso, né? Foi assim que o Bento chegou ao projeto! Bentinho, de aprox. 1 mês e pouquinhos dias já foi consultado, testado para fiv/felv (negativo) e já foi confirmado que ele está com esporotricose! Ele também estava com uma ferida fedida na ponta do rabo, foi preciso fazer uma limpeza para olhar melhor pois estava bem feia e poderia ter miíase.', 'M', '0', 'A', '1701260967524');
INSERT INTO Animais VALUES ('19', 'Jurandinho', '1664593200000', X'', 'Não estávamos planejando um resgate nem tão cedo, na verdade, estávamos tão desmotivadas ultimamente, que ponderamos seriamente se era hora de encerrar essa história. A rede social anda boicotando tudo o que construímos e sonhamos. Vídeos engraçados ganharam o espaço que nos mal tínhamos e lutávamos incansavelmente para conquistar e dar voz a vocês. Mas nós fomos esquecidos. Vocês foram esquecidos. Abandonados nas ruas, com fome, sentindo medo, dor, sendo rejeitados e esquecidos em meio a tantos vídeos rápidos sobre os assuntos mais aleatórios possíveis. Quase desistimos. O desânimo nos pegou em cheio. Pensamos: Será que as pessoas ainda querem acompanhar essa luta? Será que isso ainda importa? E se não conseguirmos ajuda? Ta tão difícil para todo mundo… Foi ai que você apareceu. Não vou mentir. Até tomarmos a decisão de que ajudaríamos você, uma ansiedade tomou conta do meu coração imediatamente. Principalmente quando soube que estava em nosso bairro. Eu senti que era a hora de voltar, de ignorar esse medo e ajudar você. Não foi fácil. Eu suei frio até abrir a caixa de transporte hoje e te olhar.', 'M', '0', 'D', '1701261634955');
INSERT INTO Animais VALUES ('20', 'Nego', '1696129200000', X'', 'M', '1', 'PA', '1701827573490');
INSERT INTO Animais VALUES ('21', 'Negresca', '1630465200000', X'', 'Descrição', 'M', '1', 'PA', '1702058552337');
DELETE FROM Despesas;
INSERT INTO Despesas VALUES ('20', 'Internação durante 4 dias', '1200.0', '1701054000000', 'Internação', NULL, '1', '1701826724971');
INSERT INTO Despesas VALUES ('21', 'Homograma', '300.1', '1698980400000', 'Consulta', NULL, '1', '1701826754922');
INSERT INTO Despesas VALUES ('22', 'Sporonax para dor', '50.0', '1701831600000', 'Medicação', NULL, '1', '1701826773950');
INSERT INTO Despesas VALUES ('23', 'Castração', '200.0', '1701831600000', 'Castração', NULL, '1', '1701826870799');
INSERT INTO Despesas VALUES ('24', 'Vacina contra pulgas', '100.0', '1701831600000', 'Medicação', X'', '1', '1701893344946');
INSERT INTO Despesas VALUES ('25', 'Castração', '250.0', '1701831600000', 'Castração', NULL, '1', '1701894065752');
INSERT INTO Despesas VALUES ('26', 'Coleira', '5.5', '1701831600000', 'Acessório', NULL, '1', '1701894280979');
INSERT INTO Despesas VALUES ('27', 'Keravit', '35.0', '1701831600000', 'Medicação', NULL, '1', '1701894339013');
INSERT INTO Despesas VALUES ('28', 'Vitamina B12', '20.0', '1701831600000', 'Medicação', NULL, '1', '1701894378122');
INSERT INTO Despesas VALUES ('29', 'Vacinação do cachorro', '20.0', '1702004400000', 'Medicação', X'', '1', '1702058874336');
INSERT INTO Despesas VALUES ('30', 'Vacina contra alguma doença', '200.0', '1702004400000', 'Medicação', NULL, '1', '1702059015034');
DELETE FROM Doacoes;
INSERT INTO Doacoes VALUES ('4', 'Sebastião Mesquista', '15.0', '1700708400000', NULL, '1700700265699');
INSERT INTO Doacoes VALUES ('5', 'Romario Freitas', '30.0', '1698980400000', NULL, '1700700284191');
INSERT INTO Doacoes VALUES ('7', 'Clara Alves', '45.0', '1701226800000', X'', '1701260052547');
INSERT INTO Doacoes VALUES ('8', 'Regis Mesquita', '100.0', '1700535600000', X'', '1701260167949');
INSERT INTO Doacoes VALUES ('9', 'Marcela Gomes', '120.0', '1698807600000', X'', '1701260613096');
INSERT INTO Doacoes VALUES ('10', 'Mendonça', '40.0', '1702004400000', X'', '1702059068808');
DELETE FROM Procedimentos;
INSERT INTO Procedimentos VALUES ('14', 'Internação durante 4 dias', '18', '1701054000000', 'Internação', '3', '20', '1', '1701826724976');
INSERT INTO Procedimentos VALUES ('15', 'Homograma', '18', '1698980400000', 'Consulta', '2', '21', '1', '1701826754923');
INSERT INTO Procedimentos VALUES ('16', 'Sporonax para dor', '18', '1701831600000', 'Medicação', '3', '22', '1', '1701826773952');
INSERT INTO Procedimentos VALUES ('17', 'Castração', '16', '1701831600000', 'Castração', '4', '23', '1', '1701826870803');
INSERT INTO Procedimentos VALUES ('18', 'Limpar o espaço', '', '1701745200000', 'Limpeza', '6', '', '1', '1701826934924');
INSERT INTO Procedimentos VALUES ('19', 'Vacina contra pulgas', '19', '1701831600000', 'Medicação', '2', '24', '1', '1701893344959');
INSERT INTO Procedimentos VALUES ('20', 'Castração', '17', '1701831600000', 'Castração', '4', '25', '1', '1701894044230');
INSERT INTO Procedimentos VALUES ('21', 'Coleira', '13', '1701831600000', 'Acessório', '2', '26', '1', '1701894280981');
INSERT INTO Procedimentos VALUES ('22', 'Keravit', '14', '1701831600000', 'Medicação', '3', '27', '1', '1701894339015');
INSERT INTO Procedimentos VALUES ('23', 'Vitamina B12', '14', '1701831600000', 'Medicação', '4', '28', '1', '1701894378124');
INSERT INTO Procedimentos VALUES ('24', 'Vacinação do cachorro', '17', '1702004400000', 'Medicação', '7', '29', '1', '1701896630882');
INSERT INTO Procedimentos VALUES ('25', 'Limpar o espaço', '', '1702004400000', 'Limpeza', '6', '', '0', '1702058788151');
INSERT INTO Procedimentos VALUES ('26', 'Vacina contra alguma doença', '18', '1702004400000', 'Medicação', '7', '30', '1', '1702059015036');
