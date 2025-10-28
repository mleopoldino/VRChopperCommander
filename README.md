# VR Chopper Commander 🚁

[![Java](https://img.shields.io/badge/Java-SE_8-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/license-Educational-blue.svg)]()

Um jogo de tiro em primeira pessoa desenvolvido em Java usando Swing, onde você controla uma metralhadora de helicóptero para abater inimigos que se aproximam.

![Game Banner](assets/icon/GameIcon.png)

## 📋 Sobre o Projeto

VR Chopper Commander é um jogo de ação onde o jogador assume o controle de uma metralhadora montada em um helicóptero. O objetivo é abater helicópteros inimigos que se aproximam antes que eles escapem. O jogo apresenta:

- Visão em primeira pessoa do cockpit
- Sistema de pontuação baseado em acertos
- Animações de explosão
- Cenário dinâmico com movimento paralaxe
- Interface de usuário com placar e contador de erros

## 🎮 Como Jogar

### Controles

| Tecla | Ação |
|-------|------|
| **↑ ↓ ← →** | Movimentar a mira / câmera |
| **ESPAÇO** | Atirar |
| **ENTER** | Iniciar jogo (tela inicial) |
| **ESC** | Sair do jogo |

### Objetivo

- Destrua o máximo de helicópteros inimigos possível
- Cada acerto aumenta sua pontuação
- Você pode errar no máximo **3 vezes**
- Os inimigos aparecem em posições aleatórias e crescem conforme se aproximam
- Game Over quando atingir 3 erros

### Sistema de Pontuação

- Cada helicóptero destruído = **10 pontos**
- Helicópteros que escapam = **1 erro**
- Total de erros permitidos = **3**

## 🚀 Como Executar

### Pré-requisitos

- Java SE Development Kit (JDK) 8 ou superior
- Sistema operacional: Windows, macOS ou Linux

### Compilar e Executar

#### Usando linha de comando:

```bash
# Compilar o projeto
javac -d bin -sourcepath src src/tcc/game/engine/core/Main.java

# Executar o jogo
java -cp bin tcc.game.engine.core.Main
```

#### Usando Eclipse:

1. Importe o projeto no Eclipse (File → Import → Existing Projects)
2. Clique com botão direito em `Main.java`
3. Selecione **Run As → Java Application**

#### Usando IntelliJ IDEA:

1. Abra o projeto no IntelliJ
2. Configure o JDK se necessário (File → Project Structure)
3. Execute a classe `tcc.game.engine.core.Main`

## 🏗️ Arquitetura do Projeto

```
TCC_Game/
├── src/
│   └── tcc/game/engine/
│       ├── core/
│       │   ├── Main.java           # Ponto de entrada
│       │   ├── Application.java    # Janela e input handling
│       │   └── GameCore.java       # Loop principal e lógica
│       ├── GameObject.java         # Classe base para objetos
│       ├── Cenario.java           # Cenário animado
│       ├── Inimigo.java           # Helicópteros inimigos
│       ├── Mira.java              # Mira/crosshair
│       ├── Tiro.java              # Efeito visual do disparo
│       ├── Explosao.java          # Animação de explosão
│       ├── GameOver.java          # Tela de game over
│       ├── SpriteSheet.java       # Sistema de animação
│       ├── Point.java             # Representação de posição 2D
│       └── Vector2D.java          # Vetores de movimento
├── assets/
│   ├── images/                    # Sprites e texturas
│   ├── sounds/                    # Efeitos sonoros (não implementados)
│   └── icon/                      # Ícone da aplicação
└── bin/                           # Arquivos compilados
```

### Componentes Principais

#### **GameObject**
Classe base para todos os objetos do jogo. Fornece:
- Sistema de posicionamento (`Point`)
- Sistema de animação (`SpriteSheet`)
- Escala e dimensões
- Métodos `update()` e `draw()`

#### **GameCore**
Núcleo do jogo que gerencia:
- Loop de renderização (80ms por frame ~12.5 FPS)
- Detecção de colisão
- Sistema de pontuação
- Estados do jogo (menu, jogando, game over)

#### **Sistema de Direção**
Usa constantes inteiras para direções:
- `CENTRO = 0` (parado)
- `SOBE = 1` (cima)
- `DESCE = 2` (baixo)
- `DIREITA = 3`
- `ESQUERDA = 4`

## 🎨 Assets

O jogo utiliza sprites PNG para todas as entidades visuais:

- **Cenário**: 6 frames de animação (parallax scrolling)
- **Helicópteros**: 2 frames de animação
- **Explosões**: 6 frames de animação
- **UI**: Painel do cockpit, tela inicial, game over
- **Efeitos**: Mira, tiros

### Áudio (Planejado)
Os seguintes arquivos de som estão presentes mas não implementados:
- `BackgroundTheme.mp3`
- `HelicopterSoundEffect.mp3`
- `MachineGunSoundEffect.mp3`
- `ExplosionSoundEffect.mp3`

## 🛠️ Tecnologias Utilizadas

- **Java SE 8**
- **Java Swing** - Interface gráfica
- **Java AWT** - Gráficos 2D e eventos
- **javax.imageio** - Carregamento de imagens

## 🐛 Problemas Conhecidos

1. ⚠️ Tecla ESC não está funcional para sair do jogo
2. ⚠️ Sistema de som não está implementado
3. ⚠️ Explosões não se movem com o cenário
4. ⚠️ Sistema de pontuação conta múltiplas vezes por acerto
5. ⚠️ Frame rate fixo pode causar variações de velocidade em diferentes sistemas

## 🔮 Melhorias Futuras

- [ ] Implementar sistema de som completo
- [ ] Corrigir bugs conhecidos
- [ ] Adicionar níveis de dificuldade
- [ ] Sistema de high scores com persistência
- [ ] Múltiplos tipos de inimigos
- [ ] Power-ups e armas especiais
- [ ] Melhorar game loop com delta time
- [ ] Adicionar efeitos visuais (partículas, screen shake)
- [ ] Menu de configurações (volume, controles)
- [ ] Modo fullscreen

## 📝 Desenvolvimento

### Estrutura de Classes

```
GameObject (base)
    ├── Cenario (herda)
    ├── Inimigo (herda)
    ├── Mira (herda)
    ├── Tiro (herda)
    ├── Explosao (herda)
    └── GameOver (herda)
```

### Fluxo do Jogo

1. **Main** → Cria **Application**
2. **Application** → Cria **JFrame** e **GameCore**
3. **GameCore.paint()** → Loop recursivo:
   - Chama `update()` para lógica
   - Desenha todos os objetos
   - Aguarda 80ms
   - Chama `repaint()`

### Detecção de Colisão

Usa **bounding box** simples:
- Verifica se o centro da mira está dentro dos limites do inimigo
- Colisão só conta quando o jogador está atirando
- Acerto → Explosão na posição do inimigo + respawn

## 👥 Contribuindo

Este é um projeto educacional desenvolvido como TCC (Trabalho de Conclusão de Curso). Sugestões e melhorias são bem-vindas!

### Para contribuir:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais.

## 🙏 Agradecimentos

Desenvolvido como parte do Trabalho de Conclusão de Curso (TCC).

---

**Desenvolvido com ☕ e Java**

Para dúvidas ou sugestões sobre o código, consulte o arquivo [CLAUDE.md](CLAUDE.md) para orientações técnicas detalhadas.
