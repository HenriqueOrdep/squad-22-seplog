-- =====================
-- SETORES
-- =====================
INSERT INTO setores (id, nome) VALUES
                                   (1, 'Saúde Pública'),
                                   (2, 'Previdência Social'),
                                   (3, 'Finanças')
ON CONFLICT (id) DO NOTHING;

-- =====================
-- SERVIÇOS
-- =====================
INSERT INTO servicos (id, nome, descricao, ativo, setor_id) VALUES
                                                                (1, 'Agendamento de Consulta', 'Permite ao cidadão agendar consultas médicas em unidades públicas.', true, 1),
                                                                (2, 'Vacinação', 'Consulta e registro de vacinas aplicadas.', true, 1),
                                                                (3, 'Solicitação de Aposentadoria', 'Inicia o processo de aposentadoria junto ao órgão previdenciário.', true, 2),
                                                                (4, 'Atualização Cadastral do Beneficiário', 'Permite atualizar os dados cadastrais no sistema de benefícios.', true, 2),
                                                                (5, 'Emissão de Guia de IPTU', 'Gera a guia de pagamento do IPTU com base no cadastro do imóvel.', true, 3),
                                                                (6, 'Negociação de Dívidas Municipais', 'Solicita condições para parcelamento de dívidas com o município.', false, 3)
ON CONFLICT (id) DO NOTHING;
