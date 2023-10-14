insert into enderecos (numero, bairro, cep, cidade, complemento, logradouro, uf)
values
(2, 'Brotas', '43800441', 'Salvador', null, 'Quadra 4', 'BA'),
(8841, 'Lobato', '43801572', 'Salvador', null, 'Rua de Cristo', 'BA'),
(206, 'Comércio', '43986323', 'Salvador', null, 'Avenida Sete de Setembro', 'BA'),
(null, 'Valéria', '43800993', 'Salvador', null, 'Rua dos Perdões', 'BA');

insert into pacientes (email, nome, cpf, telefone, endereco_id)
values
('amadojorge@gmail.com', 'Jorge Amado', '91658744213', '2133122597', 2),
('vcaetano@gmail.com', 'Caetano Veloso', '06858744213', '9935728200', 3),
('amadojorge@gmail.com', 'Jorge Amado', '49958744213', '2133122597', 2),
('vcaetano@gmail.com', 'Caetano Veloso', '85658744213', '9935728200', 3),
('amadojorge@gmail.com', 'Jorge Amado', '00758744213', '2133122597', 2),
('vcaetano@gmail.com', 'Caetano Veloso', '15158744213','9935728200', 3),
('amadojorge@gmail.com', 'Jorge Amado', '12358744213', '2133122597', 2),
('vcaetano@gmail.com', 'Caetano Veloso', '45658744213', '9935728200', 3),
('amadojorge@gmail.com', 'Jorge Amado', '78958744213', '2133122597', 2),
('vcaetano@gmail.com', 'Caetano Veloso', '99858744213', '9935728200', 3),
('amadojorge@gmail.com', 'Jorge Amado', '77458744213', '2133122597', 2),
('vcaetano@gmail.com', 'Caetano Veloso', '11458744213', '9935728200', 3);

insert into medicos (crm, email, especialidade, nome, telefone, endereco_id)
values
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Doutor', '2133122597', 4),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','Medico', '9935728200', 2),
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Ana', '2133122597', 4),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','Medico', '9935728200', 1),
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Lucas', '2133122597', 3),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','Medico', '9935728200', 3),
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Marcia', '2133122597', 4),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','Medico', '9935728200', 1),
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Doutor', '2133122597', 2),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','Yuri', '9935728200', 1),
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Doutor', '2133122597', 4),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','Medico', '9935728200', 1),
('02010304', 'doutor@gmail.com', 'GINECOLOGIA', 'Flavia', '2133122597', 2),
('09008053', 'medico@gmail.com', 'DERMATOLOGIA','Medico II', '9935728200', 1);