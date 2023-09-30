INSERT INTO ENDERECOS (NUMERO, BAIRRO, CEP, CIDADE, COMPLEMENTO, LOGRADOURO, UF)
VALUES
('', 'PARQUE RESIDENCIAL BURITI', '78734226', 'RONDONÓPOLIS', '', 'RUA JOSÉ PINTO', 'MT'),
('756', 'SANTA ETELVINA', '69059605', 'MANAUS', '', 'RUA MATUSALÉM', 'AM'),
('42', 'LOBATO', '40470250', 'SALVADOR', '', 'AVENIDA TENENTE ALMIR', 'BA'),
('', 'CAJAZEIRAS', '41342355', 'SALVADOR', '', 'RUA JACOBINA', 'BA'),
('14', 'VILA CABRAL', '58408303', 'CAMPINA GRANDE', '', 'RUA NARCISO COSTA FIGUEIREDO', 'PB');

INSERT INTO PACIENTES (EMAIL, NOME, CPF, TELEFONE, ENDERECO_ID)
VALUES
('LAURA.ROCHA@GMAIL.COM', 'LAURA ISABELLA ANTUNES ROCHA', '01813908680', '83994984199', 2),
('CARLOSRAULDAMOTA@GMAIL.COM', 'CARLOS RAUL DA MOTA', '28257552658', '98982843056', 3);

INSERT INTO MEDICOS (CRM, NOME, EMAIL, TELEFONE, ENDERECO_ID, ESPECIALIDADE_ID)
VALUES
('3064-MT', 'LUCIANA ASSIS DA SILVA', 'LUCIANA_ASSIS@GMAIL.COM', '84993797620', 1, 3),
('14792-PB', 'DÉBORA DA COSTA ROCHA', 'DEBORA.COSTA@GMAIL.COM', '13983232179', 5, 4),
('8588-BA', 'MARCOS VINICIUS ZARDO DA CRUZ', 'VINICIUS_DACRUZ@GMAIL.COM', '4198396-3000', 4, 2);