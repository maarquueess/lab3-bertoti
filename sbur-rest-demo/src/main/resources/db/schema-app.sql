CREATE TABLE IF NOT EXISTS cafe (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(150) NOT NULL
) ENGINE=InnoDB;

INSERT INTO cafe (nome)
SELECT 'Café Cereza'
WHERE NOT EXISTS (SELECT 1 FROM cafe WHERE nome='Café Cereza');

INSERT INTO cafe (nome)
SELECT 'Café Ganador'
WHERE NOT EXISTS (SELECT 1 FROM cafe WHERE nome='Café Ganador');

INSERT INTO cafe (nome)
SELECT 'Café Lareño'
WHERE NOT EXISTS (SELECT 1 FROM cafe WHERE nome='Café Lareño');

INSERT INTO cafe (nome)
SELECT 'Café Três Pontas'
WHERE NOT EXISTS (SELECT 1 FROM cafe WHERE nome='Café Três Pontas');
