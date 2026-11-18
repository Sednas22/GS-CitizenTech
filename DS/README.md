# PULSO · Data Science & Statistical Computing (GS 2026.2 · FIAP)

**Aluno:** Cainã Sandes Batista · **RM:** 568571 · **Turma:** 2ESPR

Análise descritiva do Censo Demográfico 2022 (IBGE) sobre dificuldade para ouvir e enxergar, para dimensionar e descrever o público do PULSO. É uma análise acadêmica complementar: não usa dados do dispositivo e **não mede chamadas perdidas**.

## Arquivos
- `PULSO-DS.ipynb`: notebook executado (contexto, preparação, análise, gráficos e conclusões);
- `dados/censo2022_deficiencia_ibge.csv`: base (361 linhas, formato longo); cada linha traz a tabela e a página de origem.

## Fonte dos dados
IBGE, *Censo Demográfico 2022: Pessoas com deficiência e pessoas diagnosticadas com TEA, resultados preliminares da amostra* (2025): https://biblioteca.ibge.gov.br/visualizacao/livros/liv102178.pdf
O CSV reúne as Tabelas 1, 3, 7, 8, 9 e 10 e números do texto da publicação, digitados a partir do PDF e conferidos no notebook (seção 4.4). Não há dados simulados nem pessoais.

## Como executar
1. Baixe `PULSO-DS.ipynb`;
2. Abra `PULSO-DS.ipynb` no [Google Colab](https://colab.research.google.com/).
3. Clique em **Ambiente de execução > Executar tudo**.
4. O notebook carrega automaticamente o CSV público do GitHub e exibe as análises, gráficos e conclusões. Não é necessário enviar arquivos adicionais nem clonar o repositório. É necessária conexão à internet.

**CSV utilizado:** https://raw.githubusercontent.com/Sednas22/GS-CitizenTech/main/DS/dados/censo2022_deficiencia_ibge.csv
