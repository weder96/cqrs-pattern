# cqrs-pattern

# Visão geral:
O código se baseia em demonstrar o CQRS Pattern com Spring Boot, que é um dos padrões de projeto de microsserviços para dimensionar de forma independente as cargas de trabalho de leitura e gravação de um aplicativo e ter um esquema de dados bem otimizado.<br/>

# Padrão CQRS:
1. Modelos de leitura versus gravação:<br/>
A maioria dos aplicativos são de natureza CRUD. Quando projetamos esses aplicativos, criamos classes de entidade e classes de repositório correspondentes para operações CRUD. Usamos as mesmas classes de modelo para todas as operações CRUD. No entanto, esses aplicativos podem ter requisitos de LEITURA e GRAVAÇÃO completamente diferentes!<br/>

Por exemplo: vamos considerar um aplicativo em que temos 3 tabelas, conforme mostrado aqui:<br/>
<br/>
user<br/>
product<br/>
purchase_order<br/>

Todas essas tabelas foram normalizadas. Criação de um novo usuário ou produto ou pedido vá para as tabelas apropriadas de forma rápida e direta.<br/> 
Mas se considerarmos os requisitos de LEITURA, não desejaríamos simplesmente todos os usuários, produtos, apenas pedidos. <br/>
Em vez disso, estaríamos interessados ​​em saber todos os detalhes dos pedidos de um usuário, estado de venda total, estado e venda de produto, etc. 
<br/>
Muitas informações agregadas que envolvem a junção de várias tabelas. Todas essas operações READ de junção também podem exigir o mapeamento DTO correspondente.

<br/>
Mais normalização que fazemos, é mais fácil de escrever e mais difícil de ler, o que por sua vez afeta o desempenho geral de leitura.

Mesmo que digamos que ESCREVER é fácil, antes de inserirmos o registro no banco de dados, podemos fazer a validação do negócio. <br/>
Todas essas lógicas podem estar presentes na classe do modelo. Portanto, podemos acabar criando um modelo muito complexo que está tentando oferecer suporte a READ e WRITE.<br/>

Um aplicativo pode ter requisitos de LEITURA e GRAVAÇÃO completamente diferentes. Portanto, um modelo criado para WRITE pode não funcionar para READ. <br/>
Para resolver este problema, podemos ter modelos separados para READ e WRITE.<br/>

# 2. Tráfego de leitura versus gravação:
A maioria das aplicações baseadas na web são lidas pesadamente. Vejamos o Facebook / Twitter, por exemplo. Quer postemos novas atualizações ou não, todos nós verificamos esses aplicativos com frequência em um dia. Claro, continuamos recebendo atualizações nesses aplicativos que são devido a algumas inserções no banco de dados. Mas é muito LER do que ESCREVER. Além disso, considere um aplicativo de reserva de voo. Provavelmente, menos de 5% dos usuários poderiam reservar uma passagem, enquanto a maioria dos usuários do aplicativo continuaria procurando o melhor voo que atendesse às suas necessidades.

Os aplicativos têm mais solicitações de operações READ em comparação com as operações WRITE. Para resolver este problema, podemos até ter microsserviços separados para READ e WRITE. Para que possam ser escalados dentro / fora de forma independente, depende de suas necessidades.

É disso que se trata o Padrão CQRS, que significa Padrão de Segregação de Responsabilidade de Consulta de Comando.

Comando: modifica os dados e não retorna nada (WRITE)
Consulta: não modifica, mas retorna dados (READ)
Isso é separar os modelos de Comando (gravação) e Consulta (leitura) de um aplicativo para dimensionar as operações de leitura e gravação de um aplicativo independentemente. Podemos resolver os 2 problemas acima usando o padrão CQRS. Vamos ver como isso pode ser implementado.

# Aplicativo de amostra:
Vamos considerar um aplicativo simples no qual temos 3 serviços, conforme mostrado a seguir. (O ideal é que todos esses serviços tenham bancos de dados diferentes. Aqui, apenas para este código, estou usando o mesmo banco de dados). <br/>
Estamos interessados ​​apenas nas funcionalidades relacionadas ao serviço de pedidos.

# Adicionados
Openapi 3.0 Swagger


# Artigo 
O texto acima e uma tradução livre do Artigo abaixo e foi usado para entender como o Spring Boot pode ser usado junto  cqrs-pattern:<br/>
C - Command <br/>
Q - Query <br/>
R - Responsibility  <br/>
S - Segregation <br/>

Entendendo melhor o CQRS <br/>
A ideia básica é segregar as responsabilidades da aplicação em: <br/>

Command – Operações que modificam o estado dos dados na aplicação.<br/>
Query – Operações que recuperam informações dos dados na aplicação.<br/>

<a href="https://www.vinsguru.com/cqrs-pattern-microservice-design-patterns/">  cqrs-pattern </a>

Mitos sobre o CQRS
# 1 Mito – CQRS e Event Sourcing devem ser implementados juntos.
O Event Sourcing é um outro pattern assim como o CQRS.<br/>
É uma abordagem que nos permite guardar todos os estados assumidos por uma uma entidade desde sua criação. <br/>
O Event Sourcing tem uma forte ligação com o CQRS e é facilmente implementado uma vez que temos também o CQRS, <br/>
porém é possível implementar Event Sourcing independente do CQRS e vice-versa.<br/>


# 2 Mito – CQRS requer consistência eventual
Negativo. Como abordado anteriormente o CQRS pode trabalhar com uma consistência imediata e síncrona.<br/>

# 3 Mito – CQRS depende de Fila/Bus/Queues
CQRS é dividir as responsabilidades de Queries e Commands, a necessidade de enfileiramento vai surgir dependendo de sua implementação, principalmente se for utilizar a estratégia de consistência eventual.<br/>

# 4 Mito – CQRS é fácil
Não é fácil. O CQRS também não é uma ciência de foguetes. A implementação vai exigir uma complexidade extra em sua aplicação além de um claro entendimento do domínio e da linguagem ubíqua.<br/>

# 5 Mito – CQRS é arquitetura
Não é! Conforme foi abordado o CQRS é um pattern arquitetural e pode ser implementado em uma parte específica da sua aplicação para um determinado conjunto de dados apenas.<br/>

# Referências externas
<a href="https://cqrs.files.wordpress.com/2010/11/cqrs_documents.pdf">  Greg Young (criador do pattern CQRS) </a>
