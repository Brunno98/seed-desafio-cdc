# Bookstore

Este é um projeto feito para o desafio da jornada deveficiente.

## Anotações sobre correções após concluir desafio:

Delegar a responsabilidade de persistencia a um repository ao inves de um entityManager.  
Refletindo sobre, acredito que me acostumei a sempre usar um repository quando penso em persistencia.  
Penso que preciso conhecer mais sobre entityManager pra te-lo em minha caixa de ferramento e poder identificar
boas oportunidades de usa-lo

Fiquei em duvida se o 'instante do registro' deveria ser recebido no controller ou se deveria ser o momento da
persistencia. Apos ver a correção, descobri que era no momento da persistencia, então fiz a correção.