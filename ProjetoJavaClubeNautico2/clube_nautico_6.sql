-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 10-Jun-2021 às 01:34
-- Versão do servidor: 10.4.17-MariaDB
-- versão do PHP: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `clube_nautico`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `aula`
--

CREATE TABLE `aula` (
  `id_aula` int(11) NOT NULL,
  `preco_aula` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `aula`
--

INSERT INTO `aula` (`id_aula`, `preco_aula`) VALUES
(1, 25),
(2, 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `desconto`
--

CREATE TABLE `desconto` (
  `id_desconto` int(11) NOT NULL,
  `percentagem_desconto` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `desconto`
--

INSERT INTO `desconto` (`id_desconto`, `percentagem_desconto`) VALUES
(1, 0.2),
(2, 0),
(3, 0.1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `dirigente`
--

CREATE TABLE `dirigente` (
  `id_dirigente` int(11) NOT NULL,
  `socio_dirigente` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `dirigente`
--

INSERT INTO `dirigente` (`id_dirigente`, `socio_dirigente`) VALUES
(1, 0),
(2, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `encarregado_educacao`
--

CREATE TABLE `encarregado_educacao` (
  `id_encarregado_educacao` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `telefone` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `encarregado_educacao`
--

INSERT INTO `encarregado_educacao` (`id_encarregado_educacao`, `nome`, `telefone`) VALUES
(1, 'Sofia', 910910910),
(2, 'Tiago', 911911911),
(4, 'Fatima', 911888434),
(5, 'Joana', 989979762);

-- --------------------------------------------------------

--
-- Estrutura da tabela `mensalidade`
--

CREATE TABLE `mensalidade` (
  `id_mensalidade` int(11) NOT NULL,
  `desconto_id` int(11) NOT NULL,
  `aula_id` int(11) NOT NULL,
  `valor_referencia` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `mensalidade`
--

INSERT INTO `mensalidade` (`id_mensalidade`, `desconto_id`, `aula_id`, `valor_referencia`) VALUES
(1, 1, 1, 100),
(2, 2, 1, 100),
(3, 3, 2, 150);

-- --------------------------------------------------------

--
-- Estrutura da tabela `socio`
--

CREATE TABLE `socio` (
  `id_socio` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `numero_contribuinte` int(11) NOT NULL,
  `ano_nascimento` int(11) NOT NULL,
  `numero_aulas` int(11) NOT NULL,
  `mensalidade_id` int(11) NOT NULL,
  `dirigente_id` int(11) NOT NULL,
  `encarregado_educacao_id` int(11) DEFAULT NULL,
  `tipo_socio_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `socio`
--

INSERT INTO `socio` (`id_socio`, `nome`, `numero_contribuinte`, `ano_nascimento`, `numero_aulas`, `mensalidade_id`, `dirigente_id`, `encarregado_educacao_id`, `tipo_socio_id`) VALUES
(1, 'Beatriz', 256345736, 2015, 8, 1, 1, 1, 1),
(2, 'Diana', 253444212, 2015, 8, 1, 1, 1, 1),
(4, 'Joaquim', 142367846, 1946, 4, 3, 1, NULL, 3),
(5, 'Lara', 255789098, 1956, 8, 3, 1, NULL, 3),
(6, 'Manuel', 177872645, 1940, 4, 3, 1, NULL, 3),
(7, 'Rodrigo', 266727453, 1987, 14, 2, 2, NULL, 2),
(8, 'Gustavo', 230948756, 2012, 15, 1, 1, 2, 1),
(10, 'Martim Oliveira ', 546546546, 2007, 12, 1, 1, 4, 1),
(11, 'Ana', 123456789, 2010, 16, 1, 1, 5, 1),
(12, 'Francisco', 134135136, 1999, 15, 2, 1, NULL, 2),
(13, 'Marta Ramos', 422433411, 1988, 8, 2, 1, NULL, 2),
(16, 'Maria', 111222333, 1977, 7, 2, 1, NULL, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tipo_socio`
--

CREATE TABLE `tipo_socio` (
  `id_tipo_socio` int(11) NOT NULL,
  `tipo_socio` enum('SMenor','SAdulto','SSenior') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `tipo_socio`
--

INSERT INTO `tipo_socio` (`id_tipo_socio`, `tipo_socio`) VALUES
(1, 'SMenor'),
(2, 'SAdulto'),
(3, 'SSenior');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `aula`
--
ALTER TABLE `aula`
  ADD PRIMARY KEY (`id_aula`);

--
-- Índices para tabela `desconto`
--
ALTER TABLE `desconto`
  ADD PRIMARY KEY (`id_desconto`);

--
-- Índices para tabela `dirigente`
--
ALTER TABLE `dirigente`
  ADD PRIMARY KEY (`id_dirigente`);

--
-- Índices para tabela `encarregado_educacao`
--
ALTER TABLE `encarregado_educacao`
  ADD PRIMARY KEY (`id_encarregado_educacao`),
  ADD UNIQUE KEY `telefone` (`telefone`);

--
-- Índices para tabela `mensalidade`
--
ALTER TABLE `mensalidade`
  ADD PRIMARY KEY (`id_mensalidade`),
  ADD KEY `desconto_id` (`desconto_id`),
  ADD KEY `aula_id` (`aula_id`);

--
-- Índices para tabela `socio`
--
ALTER TABLE `socio`
  ADD PRIMARY KEY (`id_socio`),
  ADD KEY `tipo_socio_id` (`tipo_socio_id`),
  ADD KEY `mensalidade_id` (`mensalidade_id`),
  ADD KEY `encarregado_educacao_id` (`encarregado_educacao_id`),
  ADD KEY `dirigente_id` (`dirigente_id`);

--
-- Índices para tabela `tipo_socio`
--
ALTER TABLE `tipo_socio`
  ADD PRIMARY KEY (`id_tipo_socio`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `aula`
--
ALTER TABLE `aula`
  MODIFY `id_aula` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `desconto`
--
ALTER TABLE `desconto`
  MODIFY `id_desconto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `dirigente`
--
ALTER TABLE `dirigente`
  MODIFY `id_dirigente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `encarregado_educacao`
--
ALTER TABLE `encarregado_educacao`
  MODIFY `id_encarregado_educacao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `mensalidade`
--
ALTER TABLE `mensalidade`
  MODIFY `id_mensalidade` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `socio`
--
ALTER TABLE `socio`
  MODIFY `id_socio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de tabela `tipo_socio`
--
ALTER TABLE `tipo_socio`
  MODIFY `id_tipo_socio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `mensalidade`
--
ALTER TABLE `mensalidade`
  ADD CONSTRAINT `mensalidade_ibfk_1` FOREIGN KEY (`desconto_id`) REFERENCES `desconto` (`id_desconto`),
  ADD CONSTRAINT `mensalidade_ibfk_2` FOREIGN KEY (`aula_id`) REFERENCES `aula` (`id_aula`);

--
-- Limitadores para a tabela `socio`
--
ALTER TABLE `socio`
  ADD CONSTRAINT `socio_ibfk_1` FOREIGN KEY (`tipo_socio_id`) REFERENCES `tipo_socio` (`id_tipo_socio`),
  ADD CONSTRAINT `socio_ibfk_2` FOREIGN KEY (`mensalidade_id`) REFERENCES `mensalidade` (`id_mensalidade`),
  ADD CONSTRAINT `socio_ibfk_3` FOREIGN KEY (`encarregado_educacao_id`) REFERENCES `encarregado_educacao` (`id_encarregado_educacao`),
  ADD CONSTRAINT `socio_ibfk_4` FOREIGN KEY (`dirigente_id`) REFERENCES `dirigente` (`id_dirigente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
