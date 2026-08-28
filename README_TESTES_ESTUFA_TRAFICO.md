# Guia de Testes — Estufa e Tráfico de Drogas

Este documento explica como funcionam e como testar os sistemas **Estufa** e **Tráfico de Drogas**.

## 1. Preparação

Antes dos testes, confirme que os seguintes addons estão ativos no mundo:

- Comandos V3 — Cidade Baixa Roleplay.
- Addon que contém os blocos `md3`.
- Addon de NPCs que contém `npc:npc_humans`.
- Addon que contém os itens de drogas.

É recomendado realizar os testes com pelo menos três organizações:

- Uma organização do tipo **Máfia**, para testar a Estufa.
- Uma organização do tipo **Facção**, para testar as rotas de tráfico.
- Uma organização do tipo **Corporação**, para testar a pacificação.

Os jogadores usados nos testes precisam estar corretamente cadastrados como membros dessas organizações no sistema de Times.

---

# 2. Sistema da Estufa

## Como funciona

A Estufa é uma atividade ilegal vinculada ao território chamado exatamente:

```text
Estufa
```

O território deve ser cadastrado como tipo **Mafioso**.

Quando o território pertence a uma organização do tipo **Máfia**, o sistema produz automaticamente, a cada 30 minutos:

- 15x `cdb:semente_maconha`.
- 15x `cdb:semente_coca`.

As sementes ficam acumuladas no estoque mesmo que ninguém abra o painel. O tempo e o estoque também são preservados após uma reinicialização do servidor.

O painel fica no bloco:

```text
md3:pot_stand_a_4_dark_md3
```

Coordenada exata:

```text
-1632 -57 -854
```

Somente membros da Máfia proprietária conseguem abrir o painel e retirar sementes.

## Teste 1 — Configuração do território

1. Abra o painel administrativo.
2. Cadastre um território com o nome exato `Estufa`.
3. Defina o tipo do território como `Mafioso`.
4. Configure a área do território normalmente.
5. Defina uma Máfia como proprietária por dominação ou pelo painel administrativo.
6. Coloque o bloco `md3:pot_stand_a_4_dark_md3` em `-1632 -57 -854`.

Resultado esperado:

- Um membro da Máfia proprietária consegue abrir o painel.
- Jogadores de outras organizações recebem uma mensagem de acesso negado.

## Teste 2 — Produção automática

1. Abra o painel da Estufa.
2. Confirme que o contador mostra aproximadamente 30 minutos para a próxima produção.
3. Aguarde o ciclo terminar.
4. Abra ou atualize o painel novamente.

Resultado esperado:

- O estoque recebe 15 sementes de maconha e 15 sementes de coca.
- Depois da produção, um novo ciclo de 30 minutos começa automaticamente.
- Caso dois ciclos completos tenham passado, o sistema adiciona dois lotes.

### Teste acelerado para desenvolvimento

Para não esperar 30 minutos durante um teste local, altere temporariamente em `sistemaTrafico.js`:

```js
const INTERVALO_GERACAO_MS = 30 * 60 * 1000;
```

Para, por exemplo:

```js
const INTERVALO_GERACAO_MS = 60 * 1000;
```

Isso reduz o ciclo para um minuto. Depois do teste, restaure obrigatoriamente o valor original de 30 minutos.

## Teste 3 — Retirada de sementes

1. Aguarde existir estoque.
2. Abra o painel com um membro da Máfia proprietária.
3. Selecione maconha ou coca.
4. Digite uma quantidade menor ou igual ao estoque.
5. Confirme a retirada.

Resultado esperado:

- Os itens são adicionados ao inventário.
- A quantidade retirada é descontada do estoque.
- Não é possível retirar zero, números negativos, texto ou uma quantidade superior à disponível.
- A retirada é recusada caso o inventário não tenha espaço.

## Teste 4 — Persistência

1. Deixe algumas sementes no estoque.
2. Anote o estoque e o tempo restante.
3. Reinicie o servidor.
4. Abra novamente o painel.

Resultado esperado:

- O estoque anterior continua salvo.
- O tempo transcorrido durante a reinicialização é contabilizado.
- Lotes completos ocorridos enquanto o servidor estava reiniciando são adicionados ao estoque.

## Teste 5 — Pacificação por Corporação

1. Deixe sementes acumuladas no estoque.
2. Faça uma Corporação dominar o território `Estufa`.
3. Aguarde aproximadamente um segundo.
4. Tente abrir o painel com um policial e com um criminoso.

Resultado esperado:

- Todo o estoque acumulado é apagado.
- A geração de novas sementes é interrompida.
- O território passa a ser considerado **PACIFICADO**.
- Policiais não conseguem executar a atividade ilegal.
- Outras organizações também não conseguem usar o sistema, pois não são proprietárias.

Se uma Máfia dominar o território posteriormente, um novo contador de 30 minutos será iniciado com o estoque zerado.

---

# 3. Tráfico de Drogas pela Deep Web

## Como funciona

A Deep Web possui a opção **Tráfico de Drogas**, mas ela aparece exclusivamente para jogadores pertencentes a uma organização do tipo **Facção**.

- Membros de Máfia não visualizam o botão.
- Membros de Corporação não visualizam o botão.
- Jogadores sem organização não visualizam o botão.

Para iniciar uma rota, o jogador precisa possuir pelo menos um destes itens:

- `cdb:cocaina` — vendido por R$ 500 por unidade.
- `cdb:cigarro_maconha` — vendido por R$ 800 por unidade.

Quando a rota começa:

1. O sistema escolhe uma das drogas que o jogador possui.
2. O cliente solicita aleatoriamente entre 1 e 5 unidades.
3. Um dos cinco locais de entrega é sorteado.
4. Uma rota aparece no GPS.
5. Um NPC `npc:npc_humans` é criado no local com uma skin aleatória.

O jogador pode possuir apenas uma rota ativa por vez.

## Locais possíveis do cliente

| Local | Coordenadas |
|---|---:|
| Igreja | `-406.67 -60.00 376.91` |
| Praça | `-354.23 -60.50 281.56` |
| Comunidade Vila Paris | `-234.00 -54.00 213.60` |
| Morro do Gavião | `-635.20 -60.00 -203.06` |
| Área Industrial | `-641.47 -60.00 157.33` |

## Teste 1 — Visibilidade do botão

1. Abra a Deep Web com um membro de Facção.
2. Repita com um membro de Máfia.
3. Repita com um policial de uma Corporação.

Resultado esperado:

- Apenas o membro de Facção visualiza `Tráfico de Drogas`.
- Os demais continuam visualizando somente as opções da Deep Web permitidas para o tipo de organização deles.

## Teste 2 — Tentativa sem drogas

1. Use um membro de Facção.
2. Remova toda a cocaína e todos os cigarros de maconha do inventário.
3. Abra a Deep Web e selecione `Tráfico de Drogas`.

Resultado esperado:

- Nenhuma rota é iniciada.
- O sistema informa que é necessário possuir ao menos uma droga válida.

## Teste 3 — Iniciar uma rota

Para preparar o inventário durante um teste administrativo, podem ser usados comandos equivalentes a:

```text
/give @s cdb:cocaina 5
/give @s cdb:cigarro_maconha 5
```

Depois:

1. Abra a Deep Web.
2. Selecione `Tráfico de Drogas`.
3. Observe a droga e a quantidade solicitadas.
4. Siga a rota criada no GPS.

Resultado esperado:

- Um dos cinco locais é escolhido aleatoriamente.
- A rota aponta para o local correto.
- A chunk é carregada temporariamente caso esteja descarregada.
- O NPC aparece como `Cliente da Deep Web`.
- A skin do cliente é escolhida aleatoriamente entre as skins disponíveis no addon de NPCs.

## Teste 4 — Venda concluída

1. Tenha no inventário a droga e a quantidade solicitadas.
2. Interaja com o NPC.

Resultado esperado:

- A quantidade solicitada é removida do inventário.
- O pagamento é depositado diretamente no placar `banco`.
- Cocaína paga R$ 500 por unidade.
- Cigarro de maconha paga R$ 800 por unidade.
- O NPC desaparece.
- A rota é removida do GPS.
- O jogador fica livre para iniciar outra rota.

## Teste 5 — Cliente irritado

1. Inicie uma rota normalmente.
2. Antes de interagir com o cliente, guarde ou descarte os itens solicitados.
3. Interaja com o NPC sem possuir a quantidade completa.

Resultado esperado:

- Nenhum dinheiro é recebido.
- O NPC fica identificado como irritado, começa a ir embora e desaparece.
- A rota é encerrada.
- O jogador precisa iniciar uma nova rota pela Deep Web.

## Teste 6 — Cliente de outro jogador

1. Inicie uma rota com o jogador A.
2. Vá até o cliente com o jogador B.
3. Faça o jogador B interagir com o NPC.

Resultado esperado:

- O jogador B recebe a mensagem `Não tenho negócios com você`.
- Nenhuma droga é removida.
- Nenhum pagamento é realizado.
- A rota do jogador A continua ativa.

## Teste 7 — Uma rota por jogador

1. Inicie uma rota.
2. Abra novamente a Deep Web e selecione `Tráfico de Drogas`.

Resultado esperado:

- Um segundo NPC não é criado.
- O sistema informa que já existe um cliente aguardando.
- A rota existente é marcada novamente no GPS.

## Teste 8 — Persistência e chunks

1. Inicie uma rota.
2. Reinicie o servidor antes de concluir a venda.
3. Entre novamente com o mesmo jogador.
4. Aguarde alguns segundos.

Resultado esperado:

- A rota continua registrada.
- O NPC existente é reutilizado ou recriado caso tenha desaparecido.
- Não são criados clientes duplicados.
- A venda ainda pode ser concluída normalmente.

## Teste 9 — Proteção contra interação duplicada

1. Tenha exatamente a quantidade solicitada.
2. Interaja rapidamente várias vezes com o NPC.

Resultado esperado:

- A droga é removida somente uma vez.
- O pagamento acontece somente uma vez.
- A rota é encerrada antes do pagamento, impedindo duplicação.

---

# 4. Problemas comuns

## O painel da Estufa não abre

Confira:

- O nome do território deve ser exatamente `Estufa`.
- O território deve ser do tipo `Mafioso`.
- O bloco e a coordenada precisam estar corretos.
- O jogador precisa pertencer à Máfia proprietária.
- O território não pode estar pacificado por uma Corporação.

## O botão Tráfico de Drogas não aparece

Confira:

- O jogador precisa pertencer a uma organização do tipo exatamente `Facção`.
- Máfia e Corporação não recebem esse botão.
- O cadastro do jogador no sistema de Times precisa estar atualizado.

## O NPC não aparece

Confira:

- O addon que registra `npc:npc_humans` está ativo.
- Não houve erro de `tickingarea` no console.
- A rota realmente foi criada e aparece no GPS.
- Aguarde alguns segundos para o carregamento da chunk.

## O dinheiro não entrou

Confira:

- O objetivo de scoreboard `banco` existe.
- O jogador possuía a quantidade completa solicitada.
- A interação foi feita pelo dono da rota.
- A rota ainda estava ativa.

---

# 5. Checklist final

- [ ] Território `Estufa` cadastrado como `Mafioso`.
- [ ] Painel colocado em `-1632 -57 -854`.
- [ ] Produção de 15 + 15 validada.
- [ ] Retirada parcial e total validada.
- [ ] Persistência após reinício validada.
- [ ] Pacificação e perda do estoque validadas.
- [ ] Botão de tráfico visível somente para Facção.
- [ ] Validação dos dois itens de droga concluída.
- [ ] Cinco pontos aleatórios testados.
- [ ] Skin aleatória do NPC validada.
- [ ] Venda e pagamento bancário validados.
- [ ] Cliente irritado validado.
- [ ] Bloqueio de outro jogador validado.
- [ ] Proteção contra pagamento duplicado validada.
