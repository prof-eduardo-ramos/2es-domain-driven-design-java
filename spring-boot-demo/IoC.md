**O que é Inversão de Controle (IoC)?**

  * **Um Princípio de Design:** IoC é um princípio de programação onde o controle sobre a criação e gerenciamento de objetos (dependências) é "invertido".
  * **Fluxo Tradicional:** Normalmente, um objeto é responsável por criar as dependências de que precisa.
      * *Exemplo:* A classe `PedidoService` precisaria criar sua própria dependência: `ClienteRepository repository = new ClienteRepository()`. A classe `PedidoService` *controla* a criação do `ClienteRepository`.
  * **Fluxo com IoC:** Com IoC, o objeto (ex: `PedidoService`) não cria mais suas dependências. Ele apenas "avisa" que precisa de uma (ex: de um `ClienteRepository`), e uma entidade externa (um "Contêiner") fica responsável por criar e *fornecer* (injetar) essa dependência para ele.
  * **Injeção de Dependência (DI):** A Injeção de Dependência é a forma mais comum de *implementar* o princípio da Inversão de Controle.

**Para que serve o IoC?**

  * **Desacoplamento:** É o principal benefício. As classes não ficam fortemente "amarradas" umas às outras. O `PedidoService` não precisa saber *como* construir um `ClienteRepository`, ele só precisa saber usar um.
  * **Facilitar Testes (Testabilidade):** Torna os testes unitários muito mais fáceis. Durante um teste, você pode facilmente "injetar" uma versão falsa (*mock*) do `ClienteRepository` para simular comportamentos, sem precisar de um banco de dados real.
  * **Flexibilidade e Manutenção:** Se você precisar trocar a implementação (ex: de um `ClienteRepositoryMySQL` para um `ClienteRepositoryPostgres`), você só precisa alterar a configuração no "Contêiner". As classes que usam o repositório (como o `PedidoService`) não precisam ser modificadas.
  * **Centralização:** O ciclo de vida dos objetos (criação, configuração, destruição) é gerenciado em um único lugar (o Contêiner), e não espalhado por todo o código.

**Como é usado dentro do Spring Boot?**

  * **O Contêiner IoC (ApplicationContext):** O Spring Boot gerencia um "Contêiner IoC" (chamado de `ApplicationContext`). É ele quem vai criar, configurar e gerenciar seus objetos.

  * **"Beans":** Os objetos que são gerenciados pelo Contêiner IoC do Spring são chamados de **Beans**.

  * **Declarando Beans (Avisando o Spring):** Você "diz" ao Spring para gerenciar um objeto (transformá-lo em um Bean) usando anotações. As mais comuns são:

      * `@Component`: Anotação genérica para qualquer bean.
      * `@Service`: Usada para classes de lógica de negócio (serviços).
      * `@Repository`: Usada para classes que acessam dados (repositórios).
      * `@Controller` / `@RestController`: Usada para classes que recebem requisições web.

  * **Injetando Dependências (Pedindo ao Spring):** Para *usar* um bean dentro de outra classe, você pede ao Spring para "injetá-lo". A forma mais comum é usando a anotação `@Autowired`:

    ```java
    // 1. Você "avisa" ao Spring que esta classe é um Bean
    @Service
    public class PedidoService {

        // 2. Você "pede" ao Spring para injetar o bean ClienteRepository aqui
        @Autowired
        private ClienteRepository clienteRepository;

        public void criarPedido(long clienteId) {
            // 3. Você usa o repositório que o Spring "injetou"
            Cliente cliente = clienteRepository.findById(clienteId);
            // ... lógica para criar o pedido ...
        }
    }

    // O Spring também gerencia este Bean
    @Repository
    public class ClienteRepository {
        // ... lógica de banco de dados ...
        public Cliente findById(long id) { ... }
    }
    ```

  * **Resumo do Fluxo no Spring:** Quando o Spring vai criar o `PedidoService` (passo 1), ele vê o `@Autowired` (passo 2), procura no seu Contêiner IoC por um bean do tipo `ClienteRepository`, encontra-o e o "injeta" automaticamente no atributo `clienteRepository`. O `PedidoService` nunca precisou usar a palavra `new` para criar o repositório.