# API-AI

Esta aplicação é uma API backend construída com Spring Boot e Spring AI, que integra modelos de IA (OpenAI) e utiliza MySQL para persistência de sessões de chat com memória. O objetivo é fornecer endpoints para conversas inteligentes, mantendo o histórico das interações.

## Propósito

- Permitir conversas com IA via API REST.
- Persistir sessões de chat e histórico das mensagens.
- Exemplo de integração entre Spring Boot, Spring AI e MySQL.

## Como rodar localmente

1. **Clone o repositório**
   ```bash
   git clone https://github.com/seu-usuario/api-ai.git
   cd api-ai
   ```

2. **Configure as variáveis de ambiente**
   - Crie um arquivo `.env` ou exporte as variáveis necessárias:
     - `OPENAI_API_KEY`
     - `MYSQL_USER`
     - `MYSQL_PASSWORD`
     - `MYSQL_DATABASE`
     - `MYSQL_ROOT_PASSWORD`

3. **Suba o MySQL com Docker Compose**
   ```bash
   docker compose up -d
   ```

4. **Instale as dependências e rode a aplicação**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Acesse os endpoints**
   - Exemplo: `POST /api/chat/memory/new` para iniciar uma nova sessão de chat.

## Observações

- Certifique-se que o banco MySQL está rodando e configurado conforme `application.properties`.
- A aplicação utiliza o OpenAI, portanto é necessário uma chave de API válida.


