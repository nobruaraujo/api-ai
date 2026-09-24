# Deploy Seguro na VPS NetCup

Guia enxuto e focado em **segurança** para deploy do projeto api-ai na VPS NetCup.

---

## 📋 Pré-requisitos

- VPS NetCup com Ubuntu 22.04 LTS (mínimo: 2 vCPU, 4GB RAM, 50GB SSD)
- Acesso SSH configurado
- Domínio apontado para o IP da VPS
- Conta Cloudflare (proteção DDoS/WAF gratuito)

---

## 🔒 FASE 1: Segurança Básica da VPS

### 1.1. Atualizar sistema
```bash
sudo apt update && sudo apt upgrade -y
```

### 1.2. Configurar chave SSH (NO SEU COMPUTADOR LOCAL)

**⚠️ ATENÇÃO:** Faça isso ANTES de desabilitar senha!

```bash
# No seu computador local (não na VPS!)
ssh-keygen -t ed25519 -C "seu-email@exemplo.com"

# Pressione Enter 3x (aceitar padrão)
# Cria ~/.ssh/id_ed25519 (privada) e ~/.ssh/id_ed25519.pub (pública)

# Copiar chave pública para VPS
ssh-copy-id root@IP_DA_VPS
# Digite a senha do root
```

**Testar conexão com chave (em outro terminal):**
```bash
ssh root@IP_DA_VPS
# Deve conectar SEM pedir senha ✅
```

### 1.3. Criar usuário não-root
```bash
# Na VPS, como root
sudo adduser deploy
sudo usermod -aG sudo deploy

# Copiar chaves SSH para novo usuário
sudo rsync --archive --chown=deploy:deploy ~/.ssh /home/deploy
```

**Testar conexão com usuário deploy (em outro terminal):**
```bash
ssh deploy@IP_DA_VPS
# Deve conectar sem pedir senha ✅
```

### 1.4. Desabilitar login root via SSH

**⚠️ SÓ FAÇA ISSO APÓS CONFIRMAR QUE CONSEGUE LOGAR COM `deploy` SEM SENHA!**

```bash
# Na VPS
sudo nano /etc/ssh/sshd_config
```
Alterar:
```
PermitRootLogin no
PasswordAuthentication no
PubkeyAuthentication yes
```
Reiniciar SSH:
```bash
sudo systemctl restart sshd
```

**De agora em diante, sempre use:**
```bash
ssh deploy@IP_DA_VPS
```

### 1.5. Configurar Firewall (UFW)
```bash
sudo ufw default deny incoming
sudo ufw default allow outgoing
sudo ufw allow 22/tcp      # SSH
sudo ufw allow 80/tcp      # HTTP
sudo ufw allow 443/tcp     # HTTPS
sudo ufw enable
sudo ufw status
```

### 1.6. Fail2Ban (proteção contra brute-force)
```bash
sudo apt install fail2ban -y
sudo systemctl enable fail2ban
sudo systemctl start fail2ban
```

---

## 🐳 FASE 2: Instalação Docker

### 2.1. Instalar Docker Engine

**Detectar seu sistema:**
```bash
cat /etc/os-release | grep "^ID="
# Resultado: ID=ubuntu OU ID=debian
```

**Para DEBIAN (Trixie/Bookworm):**
```bash
# Remover versões antigas
sudo apt remove docker docker-engine docker.io containerd runc

# Instalar dependências
sudo apt install ca-certificates curl gnupg lsb-release -y

# Adicionar chave GPG oficial Docker
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/debian/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

# Adicionar repositório Docker para DEBIAN
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Atualizar e instalar Docker
sudo apt update
sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y
```

**Para UBUNTU (22.04/24.04):**
```bash
# Remover versões antigas
sudo apt remove docker docker-engine docker.io containerd runc

# Instalar dependências
sudo apt install ca-certificates curl gnupg lsb-release -y

# Adicionar chave GPG oficial Docker
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

# Adicionar repositório Docker para UBUNTU
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Atualizar e instalar Docker
sudo apt update
sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y
```

**Configuração final (ambos sistemas):**
```bash
# Adicionar usuário ao grupo docker
sudo usermod -aG docker deploy
newgrp docker

# Verificar instalação
docker --version
docker compose version

# Testar
docker run hello-world
docker rmi hello-world
```

---

## 🚀 FASE 3: Deploy da Aplicação

### 3.1. Clonar repositório
```bash
# Fazer login como usuário deploy
su - deploy

# Criar diretório para aplicações
mkdir -p ~/apps
cd ~/apps

# Clonar projeto (use HTTPS)
git clone https://github.com/nobruaraujo/api-ai.git
cd api-ai

# Verificar que está no branch main atualizado
git pull origin main
```

### 3.2. Configurar variáveis de ambiente (SEGURO)

**Primeiro, gere senhas fortes:**
```bash
# PostgreSQL User Password
openssl rand -base64 32

# PostgreSQL Root Password  
openssl rand -base64 32
```

**Criar arquivo `.env` (Docker Compose lê automaticamente):**
```bash
nano .env
```

Conteúdo do `.env` (⚠️ NUNCA COMMITAR - já está no .gitignore):
```env
# Database
SPRING_DATASOURCE_DATABASE=api_ai_db
SPRING_DATASOURCE_USERNAME=api_ai_user
SPRING_DATASOURCE_PASSWORD=<COLE_SENHA_USER_GERADA_ACIMA>
SPRING_DATASOURCE_ROOT_PASSWORD=<COLE_SENHA_ROOT_GERADA_ACIMA>
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/api_ai_db

# OpenAI
OPENAI_API_KEY=<SUA_CHAVE_OPENAI>

# Twilio WhatsApp
TWILIO_ACCOUNT_SID=<SEU_SID>
TWILIO_AUTH_TOKEN=<SEU_TOKEN>
TWILIO_WHATSAPP_NUMBER=whatsapp:+<NUMERO>

# Timezone
TZ=America/Sao_Paulo
```

### 3.3. Proteger arquivo .env
```bash
chmod 600 .env
```

**Verificar se variáveis serão carregadas:**
```bash
docker compose -f docker-compose.prod.yaml config | grep POSTGRES
# Deve mostrar os valores, não "<no value>" ou warnings
```

### 3.4. Criar docker-compose.prod.yaml
```bash
nano docker-compose.prod.yaml
```

```yaml
services:
  db:
    image: postgres:15-alpine
    container_name: api-ai-postgres
    restart: always
    environment:
      POSTGRES_DB: ${SPRING_DATASOURCE_DATABASE}
      POSTGRES_USER: ${SPRING_DATASOURCE_USERNAME}
      POSTGRES_PASSWORD: ${SPRING_DATASOURCE_PASSWORD}
      POSTGRES_INITDB_ARGS: "--encoding=UTF8"
    volumes:
      - pgdata:/var/lib/postgresql/data
    networks:
      - app-network
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ${SPRING_DATASOURCE_USERNAME} -d ${SPRING_DATASOURCE_DATABASE}"]
      interval: 10s
      timeout: 5s
      retries: 5
    # Não expor porta 5432 publicamente
    # ports:
    #   - "5432:5432"

  app:
    build: .
    container_name: api-ai-app
    restart: always
    ports:
      - "127.0.0.1:8080:8080"  # Bind apenas localhost
    environment:
      SPRING_DATASOURCE_URL: ${SPRING_DATASOURCE_URL}
      SPRING_DATASOURCE_USERNAME: ${SPRING_DATASOURCE_USERNAME}
      SPRING_DATASOURCE_PASSWORD: ${SPRING_DATASOURCE_PASSWORD}
      OPENAI_API_KEY: ${OPENAI_API_KEY}
      TWILIO_ACCOUNT_SID: ${TWILIO_ACCOUNT_SID}
      TWILIO_AUTH_TOKEN: ${TWILIO_AUTH_TOKEN}
      TWILIO_WHATSAPP_NUMBER: ${TWILIO_WHATSAPP_NUMBER}
    depends_on:
      db:
        condition: service_healthy
    networks:
      - app-network
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 60s

volumes:
  pgdata:
    driver: local

networks:
  app-network:
    driver: bridge
```

### 3.5. Build e deploy
```bash
# Build da aplicação (Docker Compose lê .env automaticamente)
docker compose -f docker-compose.prod.yaml build

# Subir serviços
docker compose -f docker-compose.prod.yaml up -d

# Verificar status (NÃO deve ter warnings sobre variáveis)
docker compose -f docker-compose.prod.yaml ps

# Deve mostrar ambos como "Up" ou "Up (healthy)"
```

**Monitorar logs:**
```bash
# Ver todos os logs
docker compose -f docker-compose.prod.yaml logs -f

# Ver apenas logs do banco
docker compose -f docker-compose.prod.yaml logs -f db

# Ver apenas logs da app
docker compose -f docker-compose.prod.yaml logs -f app
```

**Se aparecer "Up (unhealthy)", aguarde alguns segundos para o healthcheck passar.**

---

## 🌐 FASE 4: Nginx como Reverse Proxy

### 4.1. Instalar Nginx
```bash
sudo apt install nginx -y
sudo systemctl enable nginx
```

### 4.2. Configurar Nginx
```bash
sudo nano /etc/nginx/sites-available/api-ai
```

```nginx
# Limitar tamanho de upload
client_max_body_size 10M;

# Rate limiting
limit_req_zone $binary_remote_addr zone=api_limit:10m rate=10r/s;

upstream api_backend {
    server 127.0.0.1:8080;
}

server {
    listen 80;
    server_name seu-dominio.com www.seu-dominio.com;

    # Redirecionar para HTTPS
    return 301 https://$server_name$request_uri;
}

server {
    listen 443 ssl;
    http2 on;
    server_name seu-dominio.com www.seu-dominio.com;

    # Certificados SSL (Cloudflare Origin Certificates)
    ssl_certificate /etc/nginx/ssl/cloudflare-origin.pem;
    ssl_certificate_key /etc/nginx/ssl/cloudflare-origin.key;

    # Configurações SSL seguras
    ssl_protocols TLSv1.3;
    ssl_ciphers 'ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES128-GCM-SHA256:ECDHE-ECDSA-AES256-GCM-SHA384:ECDHE-RSA-AES256-GCM-SHA384';
    ssl_prefer_server_ciphers off;

    # Security headers
    add_header X-Frame-Options "SAMEORIGIN" always;
    add_header X-Content-Type-Options "nosniff" always;
    add_header X-XSS-Protection "1; mode=block" always;
    add_header Referrer-Policy "strict-origin-when-cross-origin" always;

    # Logs
    access_log /var/log/nginx/api-ai-access.log;
    error_log /var/log/nginx/api-ai-error.log;

    location / {
        # Rate limiting
        limit_req zone=api_limit burst=20 nodelay;

        proxy_pass http://api_backend;
        proxy_http_version 1.1;
        
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
        # Timeouts
        proxy_connect_timeout 60s;
        proxy_send_timeout 60s;
        proxy_read_timeout 60s;
    }

    # Health check endpoint (opcional)
    location /actuator/health {
        proxy_pass http://api_backend/actuator/health;
        access_log off;
    }
}
```

### 4.3. Ativar configuração (SEM RECARREGAR AINDA)
```bash
# Criar link simbólico
sudo ln -s /etc/nginx/sites-available/api-ai /etc/nginx/sites-enabled/

# Remover configuração default
sudo rm /etc/nginx/sites-enabled/default
```

**⚠️ NÃO teste/recarregue o nginx ainda! Os certificados SSL ainda não existem.**
**Continue para FASE 5 para configurar Cloudflare e criar os certificados.**

---

## 🔐 FASE 5: Cloudflare (SSL/TLS + WAF)

### 5.1. Adicionar domínio à Cloudflare
1. Acesse [Cloudflare Dashboard](https://dash.cloudflare.com)
2. Adicione seu domínio
3. Atualize os nameservers no provedor do domínio

### 5.2. Configurar DNS
No painel Cloudflare > DNS:
```
Type    Name              Content           Proxy Status
A       seu-dominio.com   <IP_VPS_NETCUP>   Proxied (laranja)
A       www               <IP_VPS_NETCUP>   Proxied (laranja)
```

### 5.3. Gerar Certificado Origin (Cloudflare ↔ VPS)
1. Cloudflare Dashboard > SSL/TLS > Origin Server
2. Create Certificate
3. **Escolher:**
   - Hostnames: `seu-dominio.com` e `*.seu-dominio.com`
   - Validade: 15 anos
   - Formato: PEM
4. Copiar certificado e chave privada

Na VPS:
```bash
# Criar diretório
sudo mkdir -p /etc/nginx/ssl

# Salvar certificado
sudo nano /etc/nginx/ssl/cloudflare-origin.pem
# Colar o CERTIFICADO aqui (começando com -----BEGIN CERTIFICATE-----)

# Salvar chave privada
sudo nano /etc/nginx/ssl/cloudflare-origin.key
# Colar a CHAVE PRIVADA aqui (começando com -----BEGIN PRIVATE KEY-----)

# Permissões restritas
sudo chmod 600 /etc/nginx/ssl/cloudflare-origin.key
sudo chmod 644 /etc/nginx/ssl/cloudflare-origin.pem

# AGORA SIM, testar configuração nginx
sudo nginx -t

# Se testar OK, recarregar Nginx
sudo systemctl reload nginx
```

**Verificar se Nginx está rodando:**
```bash
sudo systemctl status nginx
# Deve mostrar: active (running)
```

### 5.4. Configurar SSL/TLS Mode
Cloudflare > SSL/TLS > Overview:
- **Encryption mode:** `Full (strict)` ✅

### 5.5. Ativar segurança Cloudflare
- **SSL/TLS > Edge Certificates:**
  - ✅ Always Use HTTPS: ON
  - ✅ HTTP Strict Transport Security (HSTS): Enable
  - ✅ Minimum TLS Version: 1.3

- **Security > WAF:**
  - ✅ Cloudflare managed ruleset: ON

- **Security > Bots:**
  - ✅ Bot Fight Mode: ON

### 5.6. Configurar bypass para endpoints de API (IMPORTANTE)

**⚠️ Bot Fight Mode bloqueia curl e requisições de API!**

Se sua aplicação é uma API REST, configure bypass:

**Security > WAF > Custom Rules > Create rule:**

```
Rule name: Allow API and Actuator
When incoming requests match:
  - Field: URI Path
  - Operator: starts with  
  - Value: /api

OR

  - Field: URI Path
  - Operator: starts with
  - Value: /actuator

Then:
  - Action: Skip
  - Select: Bot Fight Mode
```

**Isso permite:**
- ✅ Requisições de API de aplicativos/scripts
- ✅ Monitoramento via curl
- ✅ Integrações externas
- 🛡️ Mantém proteção WAF e rate limiting

**Testar depois:**
```bash
curl https://novak.dev.br/actuator/health
# Deve retornar: {"status":"UP"}
```

---

## 🔄 FASE 6: Manutenção e Segurança Contínua

### 6.1. Backup automático PostgreSQL
```bash
# Criar script de backup
nano ~/backup-db.sh
```

```bash
#!/bin/bash
BACKUP_DIR="/home/deploy/backups"
DATE=$(date +%Y%m%d_%H%M%S)
CONTAINER="api-ai-postgres"
DB_NAME="api_ai_db"
DB_USER="api_ai_user"

mkdir -p $BACKUP_DIR

docker exec $CONTAINER pg_dump -U $DB_USER $DB_NAME | gzip > $BACKUP_DIR/backup_${DATE}.sql.gz

# Manter apenas últimos 7 backups
find $BACKUP_DIR -name "backup_*.sql.gz" -mtime +7 -delete

echo "Backup concluído: backup_${DATE}.sql.gz"
```

```bash
# Dar permissão
chmod +x ~/backup-db.sh

# Agendar backup diário (3:00 AM)
crontab -e
```
Adicionar:
```
0 3 * * * /home/deploy/backup-db.sh >> /home/deploy/backup.log 2>&1
```

### 6.2. Atualização automática de segurança
```bash
sudo apt install unattended-upgrades -y
sudo dpkg-reconfigure --priority=low unattended-upgrades
```

### 6.3. Monitoramento de logs
```bash
# Ver logs da aplicação
docker compose -f docker-compose.prod.yaml logs -f app

# Ver logs do PostgreSQL
docker compose -f docker-compose.prod.yaml logs -f db

# Logs do Nginx
sudo tail -f /var/log/nginx/api-ai-error.log
```

### 6.4. Restart automático em caso de falha
O `restart: always` no docker-compose.prod.yaml já garante isso.

---

## 🛡️ Checklist de Segurança

- [ ] SSH apenas com chave pública (sem senha)
- [ ] Login root SSH desabilitado
- [ ] Firewall (UFW) configurado
- [ ] Fail2Ban ativo
- [ ] PostgreSQL NÃO exposto publicamente (sem bind em 0.0.0.0:5432)
- [ ] Aplicação bind apenas em localhost (127.0.0.1:8080)
- [ ] Nginx como único ponto de entrada
- [ ] Cloudflare SSL/TLS Full (strict)
- [ ] Cloudflare WAF ativo
- [ ] Rate limiting configurado (Nginx)
- [ ] Security headers configurados
- [ ] Variáveis de ambiente protegidas (chmod 600)
- [ ] Senhas fortes geradas com openssl
- [ ] Backups automáticos configurados
- [ ] Atualizações de segurança automáticas
- [ ] Logs monitorados

---

## 🚀 Comandos Úteis

```bash
# Atualizar aplicação
cd ~/apps/api-ai
git pull
docker compose -f docker-compose.prod.yaml build
docker compose -f docker-compose.prod.yaml up -d

# Parar aplicação
docker compose -f docker-compose.prod.yaml down

# Ver status
docker compose -f docker-compose.prod.yaml ps

# Reiniciar apenas app
docker compose -f docker-compose.prod.yaml restart app

# Ver uso de recursos
docker stats

# Limpar containers/images não usados
docker system prune -a
```

---

## 🔍 Teste Final

```bash
# Testar HTTPS
curl -I https://seu-dominio.com

# Testar API
curl -X POST https://seu-dominio.com/api/chat/memory/new \
  -H "Content-Type: application/json" \
  -d '{"message": "Olá"}'
```

---

## 📞 Próximos Passos (Opcional)

- [ ] Configurar Redis para cache
- [ ] Implementar autenticação JWT
- [ ] Configurar observabilidade (Prometheus + Grafana)
- [ ] CI/CD com GitHub Actions
- [ ] Escalar para Kubernetes quando necessário

---

**Prioridade: SEGURANÇA ✅**

Este guia prioriza segurança desde o início. Todos os pontos críticos estão cobertos para um deploy seguro em produção.
