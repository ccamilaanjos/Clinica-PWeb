insert into enderecos (numero, bairro, cep, cidade, complemento, logradouro, uf)
values
(2, 'Brotas', '43800441', 'Salvador', null, 'Quadra 4', 'BA'),
(8841, 'Lobato', '43801572', 'Salvador', null, 'Rua de Cristo', 'BA'),
(206, 'Comércio', '43986323', 'Salvador', null, 'Avenida Sete de Setembro', 'BA'),
(null, 'Valéria', '43800993', 'Salvador', null, 'Rua dos Perdões', 'BA');

insert into pacientes (email, nome, cpf, telefone, endereco_id)
values
('amadojorge@gmail.com', 'Jorge Amado', '91658744213', '2133122597', 1),
('vcaetano@gmail.com', 'Caetano Veloso', '06858744213', '9935728200', 3),
('amadojorge@gmail.com', 'Rosângela Silva', '49958744213', '2133122597', 2),
('vcaetano@gmail.com', 'Matheus de Lima', '85658744213', '9935728200', 3),
('amadojorge@gmail.com', 'Lindomar Miranda', '00758744213', '2133122597', 1),
('vcaetano@gmail.com', 'Ana Moreira', '15158744213','9935728200', 4),
('amadojorge@gmail.com', 'Stephanie Wong', '12358744213', '2133122597', 1),
('vcaetano@gmail.com', 'Luan Soledade', '45658744213', '9935728200', 4),
('amadojorge@gmail.com', 'Yuri Cesar', '78958744213', '2133122597', 2),
('vcaetano@gmail.com', 'Renato Novais', '99858744213', '9935728200', 1),
('amadojorge@gmail.com', 'Ronaldo Fenômeno', '77458744213', '2133122597', 2),
('vcaetano@gmail.com', 'Michael Jackson', '11458744213', '9935728200', 3);

insert into medicos (crm, email, especialidade, nome, telefone, endereco_id)
values
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Ulisses Argus', '2133122597', 4),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','Xena Guerrera', '9935728200', 2),
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Ana Mendes', '2133122597', 4),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','Gabielle Podedia', '9935728200', 1),
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Joxer Joker', '2133122597', 3),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','Chicó Santos', '9935728200', 3),
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Márcia Sensitiva', '2133122597', 4),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','Gretchen Maria', '9935728200', 1),
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Xuxa Meneghel', '2133122597', 2),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','Antônio Ferreira', '9935728200', 1),
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Luis Phellip', '2133122597', 4),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','William Bonner', '9935728200', 1),
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Jéssica Senra', '2133122597', 2),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','Palmirinha F. de Jesus', '9935728200', 1);