# PULSO - Dynamic Programming

**Global Solution 2026.2 · FIAP · Engenharia de Software**  
**Aluno:** Cainã Sandes Batista · **RM:** 568571 · **Turma:** 2ESPR  
**Repositório:** https://github.com/Sednas22/GS-CitizenTech

## Sobre a entrega

O **PULSO** é uma solução de acessibilidade que utiliza uma pulseira e um transmissor para alertar pessoas surdas, cegas e surdocegas durante a espera por atendimento presencial.

Nesta disciplina, o algoritmo em Python **prioriza kits PULSO para verificação e manutenção**, considerando falhas nos canais de alerta, falhas de comunicação e nível de bateria. É uma análise complementar: **não faz parte do funcionamento físico dos dispositivos**.

## Como executar

1. Abra o notebook `.ipynb` desta pasta no [Google Colab](https://colab.research.google.com/).
2. Selecione **Ambiente de execução > Executar tudo**.
3. Confira o ranking, as justificativas de prioridade e o resultado dos testes nas saídas das células.

**Não é necessário instalar bibliotecas, enviar arquivos CSV ou importar scripts `.py`**. O notebook contém o algoritmo, o gerador de dados, os exemplos e os testes automatizados, usando apenas a biblioteca padrão do Python.

## Dados e resultados

Os dados são **inteiramente simulados**, gerados no próprio notebook com semente fixa (2026): **30 kits, 368 chamadas e 90 ensaios**. Não representam medições reais do PULSO nem contêm dados pessoais.

A execução cria automaticamente os CSVs de entrada na pasta `dados/` e o resultado `saida/ranking_manutencao.csv`, dentro do ambiente do Colab. **Esses arquivos são saídas geradas, não entregáveis separados.**

O ranking ordena os kits por gravidade, pontuação e critérios de desempate. Chamadas enviadas sem confirmação **não são consideradas defeitos automaticamente**. O notebook inclui **30 testes automatizados** e uma explicação de eficiência; a complexidade total é **O(C + T + K log K)**, em que C representa chamadas, T ensaios e K kits.

## Arquivos para entrega

- **Notebook `.ipynb`**: código-fonte, demonstração, entradas e saídas, testes e análise de complexidade;
- **Este `README.md`**: instruções de execução e descrição da atividade;
- **Relatório em PDF**: documentação entregue separadamente, conforme a organização da GS.
