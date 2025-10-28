#!/bin/bash
# Script para executar VR Chopper Commander

# Cores para output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}🚁 VR Chopper Commander${NC}"
echo -e "${BLUE}======================================${NC}"
echo ""

# Navegar para o diretório do projeto
cd "$(dirname "$0")"

# Verificar se está compilado
if [ ! -d "bin/tcc" ]; then
    echo -e "${GREEN}📦 Compilando projeto...${NC}"
    mkdir -p bin
    javac -d bin -sourcepath src src/tcc/game/engine/core/*.java src/tcc/game/engine/*.java src/tcc/game/engine/communication/*.java

    if [ $? -ne 0 ]; then
        echo "❌ Erro na compilação!"
        exit 1
    fi
    echo -e "${GREEN}✅ Compilação concluída!${NC}"
    echo ""
fi

# Executar o jogo
echo -e "${GREEN}🎮 Iniciando o jogo...${NC}"
echo ""
echo "Controles:"
echo "  ↑ ↓ ← → : Mover mira"
echo "  ESPAÇO  : Atirar"
echo "  ENTER   : Iniciar jogo"
echo ""
java -cp bin tcc.game.engine.core.Main
