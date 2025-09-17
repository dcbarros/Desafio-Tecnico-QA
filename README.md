# Desafio-Tecnico-QA

## 1) Tecnologias utilizadas

- **Linguagem:** Java 8
- **Build:** Maven
- **Testes (API & E2E):** JUnit 5 (Jupiter) + JUnit Platform Suite (para suite E2E)
- **API:** Rest Assured (+ Jackson para (de)serialização)
- **UI (E2E):** Selenium WebDriver 4 + WebDriverManager (Chrome headless)
- **Relatórios:** Allure (allure-junit5 + allure-maven)
- **Utilitários:** Jackson `ObjectMapper`, fábrica de usernames únicos

Versões principais (definidas no `pom.xml`):
- JUnit 5: **5.10.5**; Platform Suite: **1.10.5**
- Rest Assured: **5.3.2**
- Selenium: **4.11.0**
- WebDriverManager: **5.6.0**
- Allure: **2.29.0** / allure-maven **2.12.0**

## 2) Estrutura do projeto (arquivos importantes)

```
desafioqa/
  pom.xml
  src/
    main/java/com/desafioqa/Main.java                 # placeholder
    test/java/
      org/desafioqa/utils/
        DataLoader.java                               # carrega JSON de resources
        UsernameFactory.java                          # gera username único p/ API
      org/desafioqa/integrationTest/                  # —— TESTES DE API ——
        api/DemoQaClient.java                         # cliente Rest Assured
        config/ApiConfig.java                         # baseURI, JSON, logs
        dto/*.java                                    # modelos de request/response
        factory/*.java                                # fábricas de payloads
        test/CreateUserTest.java                      # criação de usuário
        test/GenerateTokenAndAuthorizedTest.java      # token + authorized
        test/BookStoreFlowTest.java                   # fluxo completo (2 livros)
      org/desafioqa/e2e/                              # —— TESTES DE E2E ——
        SuiteTest.java                                # suíte JUnit Platform
        core/BaseTest.java                            # setup/teardown + driver
        core/DriverFactory.java                       # Chrome headless
        core/DSL.java                                 # ações utilitárias (scroll, waits, drag&drop...)
        core/BasePO.java                              # navegação + botões comuns
        models/*.java                                 # modelos de dados (form/table)
        pageObjects/*.java                            # POs (Forms, WebTables, Widgets, Interactions, Alerts...)
        tests/FormTest.java                           # preenche Practice Form
        tests/WebTablesTest.java                      # CRUD na Web Tables
        tests/WidgetTest.java                         # Progress Bar (start/stop/reset)
        tests/AlertsFrameEWindowsTest.java            # nova janela e validação
        tests/InteractionsTest.java                   # ordenar lista (drag & drop)
    test/resources/massaDados/e2e/
      formTest.json                                   # massa de dados do formulário
      webTablesTest.json                              # massa de dados da tabela
  target/allure-results/                              # resultados JUnit p/ Allure
  target/allure-report/index.html                     # relatório estático (pós build)
```

## 3) Como executar

Pré‑requisitos:
- **JDK 8**, **Maven 3+**
- **Chrome** instalado (E2E usa Chrome headless)

Comandos:

```bash
# tudo (API + E2E suite) + gera relatório Allure na fase verify
mvn clean test

# abrir o relatório Allure gerado pelo plugin
# (saída: target/allure-report/index.html)
mvn allure:report

# executar só testes de API (exemplo, apenas a classe de fluxo)
mvn -Dtest=org.desafioqa.integrationTest.test.BookStoreFlowTest test

# executar apenas a suíte E2E
mvn -Dtest=org.desafioqa.e2e.SuiteTest test
```

## 4) API — Casos de teste

### 4.1) Cliente e Configuração
- `ApiConfig` seta `baseURI = https://demoqa.com`, `Content-Type: application/json` e logs de URI/body quando falhar.
- `DemoQaClient` implementa chamadas:
  - `POST /Account/v1/User`
  - `POST /Account/v1/GenerateToken`
  - `POST /Account/v1/Authorized`
  - `GET  /BookStore/v1/Books`
  - `POST /BookStore/v1/Books` (adiciona livros ao usuário)
  - `GET  /Account/v1/User/{userID}`

### 4.2) Criação de usuário
Arquivo: `integrationTest/test/CreateUserTest.java`  
Payload (gerado por `CreateUserRequestFactory.valid("qa")` — senha forte fixa + username único):

```json
// POST /Account/v1/User
{
  "userName": "qa_lyk6w8e_abc123",
  "password": "SenhaForte123!"
}
```

Respostas esperadas:
- **201 Created** com corpo contendo `userID`, `username` e `books: []` (vazio na criação).
- Cenário negativo coberto: reutilizar o **mesmo username** → **406** com `message = "User exists!"`.

### 4.3) Geração de token + autorização
Arquivo: `integrationTest/test/GenerateTokenAndAuthorizedTest.java`  

```json
// POST /Account/v1/GenerateToken
{
  "userName": "qa_lyk6w8e_abc123",
  "password": "SenhaForte123!"
}
```
Saída típica (campos principais mapeados por `GenerateTokenResponse`):
```json
{
  "token": "<jwt>",
  "expires": "2025-...",
  "status": "Success",
  "result": "User authorized successfully."
}
```

Verificação de autorização:
```json
// POST /Account/v1/Authorized
{
  "userName": "qa_lyk6w8e_abc123",
  "password": "SenhaForte123!"
}
```
Saída esperada: `true` (body textual ou JSON simples; o teste tolera `true` em string).

### 4.4) Listagem de livros e inclusão de 2 livros
Arquivo: `integrationTest/test/BookStoreFlowTest.java`

- Lista livros:
```http
GET /BookStore/v1/Books
```
- Coleta 2 `isbn` quaisquer da lista (mapeados por `ListBooksResponse` / `Book`).
- Monta request:
```json
// POST /BookStore/v1/Books   (Authorization: Bearer <token>)
{
  "userId": "<userID>",
  "collectionOfIsbns": [
    { "isbn": "9781449325862" },
    { "isbn": "9781449331818" }
  ]
}
```
- Esperado: **201 Created**.

### 4.5) Detalhes do usuário devem conter os 2 livros
```http
GET /Account/v1/User/{userID}    (Authorization: Bearer <token>)
```
Asserções:
- **200 OK**
- A lista `books[*].isbn` contém **exatamente** os 2 ISBNs escolhidos.

**Observações de design:**
- `UsernameFactory.unique(prefix)` evita colisão de usuários entre execuções.
- Os DTOs (`dto/*.java`) permitem serialização limpa e assertiva tipada.
- Logs de requisição/resposta são habilitados apenas em falhas (sinal claro, sem “log poluído”).

---

## 5) E2E — Casos de teste e Page Objects

### 5.1) FormTest — preencher e validar “Practice Form”
Arquivos:
- `e2e/tests/FormTest.java`
- `e2e/pageObjects/FormPO.java`
- Massa: `test/resources/massaDados/e2e/formTest.json`

Fluxo:
1. Navega: Home → **Forms** → **Practice Form** (via `BasePO`).
2. Preenche **First/Last Name**, **Email**, **Mobile**, **Date of Birth**, **Subject**, **Address**, **State/City**.
3. Faz **upload** de arquivo dummy.
4. **Submit** e valida a **modal** final confrontando a tabela com os dados de entrada.
5. Fecha modal.

Asserções principais:
- Modal visível após submit.
- Todos os campos apresentados na modal batem com a massa JSON.

### 5.2) WebTablesTest — CRUD básico
Arquivos:
- `e2e/tests/WebTablesTest.java`
- `e2e/pageObjects/WebTablesPO.java`
- Massa: `test/resources/massaDados/e2e/webTablesTest.json`

Fluxos cobertos:
- **Criar** registro (Add New Record).
- **Editar** registro: altera email e valida que o antigo desapareceu.
- **Excluir** registro: remove por email e valida ausência.

### 5.3) WidgetTest — Progress Bar
Arquivos:
- `e2e/tests/WidgetTest.java`
- `e2e/pageObjects/WidgetPO.java`

Fluxos:
- **Start/Stop perto de 20%** e validar `< 25%`.
- **Reset** e validar `0%`.

Detalhe técnico: o `WidgetPO` usa esperas para **estabilizar** o atributo `aria-valuenow` e tolerar a variação do progresso (evita flaky).

### 5.4) AlertsFrameEWindowsTest — nova janela
Arquivos:
- `e2e/tests/AlertsFrameEWindowsTest.java`
- `e2e/pageObjects/AlertsFrameEWindowsPO.java`

Fluxo:
- Clica em **New Window**.
- Alterna para a nova janela e valida o texto `"This is a sample page"`.
- Fecha a nova janela e retorna à original.

### 5.5) InteractionsTest — ordenar lista
Arquivos:
- `e2e/tests/InteractionsTest.java`
- `e2e/pageObjects/InteractionsPO.java`

Fluxo:
- Reordena a lista de **Six → One** via **drag & drop** para atingir ordem decrescente.
- Valida a ordem final via comparação posicional.

**Infra de E2E (core):**
- `DriverFactory`: Chrome headless com `--window-size=1920,1080` e `WebDriverManager` (baixa o driver automaticamente).
- `DSL`: rolagem até elemento, cliques, waits, troca de janelas, drag&drop com `Actions`, upload, etc.
- `BasePO`: centraliza navegação entre cards/menus.

---

## 6) Relatórios (Allure)

- Dependências: `io.qameta.allure:allure-junit5` e `allure-rest-assured`.
- Plugin Maven: `io.qameta.allure:allure-maven`.
- Saída:
  - Resultados: `target/allure-results/`

  - HTML estático: `target/allure-report/index.html`

