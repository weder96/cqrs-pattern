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
