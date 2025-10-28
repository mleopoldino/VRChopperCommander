# VR Chopper Commander 🚁

[![Java](https://img.shields.io/badge/Java-SE_8-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/license-Educational-blue.svg)]()

**[Português](#português)** | **[English](#english)**

---

# Português

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

# English

A first-person shooter game developed in Java using Swing, where you control a helicopter machine gun to shoot down approaching enemies.

![Game Banner](assets/icon/GameIcon.png)

## 📋 About The Project

VR Chopper Commander is an action game where the player takes control of a machine gun mounted on a helicopter. The objective is to shoot down enemy helicopters approaching before they escape. The game features:

- First-person cockpit view
- Score system based on hits
- Explosion animations
- Dynamic scenery with parallax scrolling
- User interface with scoreboard and error counter

## 🎮 How To Play

### Controls

| Key | Action |
|-----|--------|
| **↑ ↓ ← →** | Move crosshair / camera |
| **SPACE** | Shoot |
| **ENTER** | Start game (title screen) |
| **ESC** | Exit game |

### Objective

- Destroy as many enemy helicopters as possible
- Each hit increases your score
- You can miss a maximum of **3 times**
- Enemies appear at random positions and grow as they approach
- Game Over when reaching 3 misses

### Scoring System

- Each destroyed helicopter = **10 points**
- Helicopters that escape = **1 miss**
- Total allowed misses = **3**

## 🚀 How To Run

### Prerequisites

- Java SE Development Kit (JDK) 8 or higher
- Operating System: Windows, macOS or Linux

### Compile and Run

#### Using command line:

```bash
# Compile the project
javac -d bin -sourcepath src src/tcc/game/engine/core/Main.java

# Run the game
java -cp bin tcc.game.engine.core.Main
```

#### Using Eclipse:

1. Import the project into Eclipse (File → Import → Existing Projects)
2. Right-click on `Main.java`
3. Select **Run As → Java Application**

#### Using IntelliJ IDEA:

1. Open the project in IntelliJ
2. Configure JDK if necessary (File → Project Structure)
3. Run the class `tcc.game.engine.core.Main`

## 🏗️ Project Architecture

```
TCC_Game/
├── src/
│   └── tcc/game/engine/
│       ├── core/
│       │   ├── Main.java           # Entry point
│       │   ├── Application.java    # Window and input handling
│       │   └── GameCore.java       # Main loop and logic
│       ├── GameObject.java         # Base class for objects
│       ├── Cenario.java           # Animated scenery
│       ├── Inimigo.java           # Enemy helicopters
│       ├── Mira.java              # Crosshair
│       ├── Tiro.java              # Shot visual effect
│       ├── Explosao.java          # Explosion animation
│       ├── GameOver.java          # Game over screen
│       ├── SpriteSheet.java       # Animation system
│       ├── Point.java             # 2D position representation
│       └── Vector2D.java          # Movement vectors
├── assets/
│   ├── images/                    # Sprites and textures
│   ├── sounds/                    # Sound effects (not implemented)
│   └── icon/                      # Application icon
└── bin/                           # Compiled files
```

### Main Components

#### **GameObject**
Base class for all game objects. Provides:
- Positioning system (`Point`)
- Animation system (`SpriteSheet`)
- Scale and dimensions
- `update()` and `draw()` methods

#### **GameCore**
Game core that manages:
- Rendering loop (80ms per frame ~12.5 FPS)
- Collision detection
- Scoring system
- Game states (menu, playing, game over)

#### **Direction System**
Uses integer constants for directions:
- `CENTRO = 0` (stopped)
- `SOBE = 1` (up)
- `DESCE = 2` (down)
- `DIREITA = 3` (right)
- `ESQUERDA = 4` (left)

## 🎨 Assets

The game uses PNG sprites for all visual entities:

- **Scenery**: 6 animation frames (parallax scrolling)
- **Helicopters**: 2 animation frames
- **Explosions**: 6 animation frames
- **UI**: Cockpit panel, title screen, game over
- **Effects**: Crosshair, shots

### Audio (Planned)
The following sound files are present but not implemented:
- `BackgroundTheme.mp3`
- `HelicopterSoundEffect.mp3`
- `MachineGunSoundEffect.mp3`
- `ExplosionSoundEffect.mp3`

## 🛠️ Technologies Used

- **Java SE 8**
- **Java Swing** - Graphical interface
- **Java AWT** - 2D graphics and events
- **javax.imageio** - Image loading

## 🐛 Known Issues

1. ⚠️ ESC key is not functional to exit the game
2. ⚠️ Sound system is not implemented
3. ⚠️ Explosions don't move with the scenery
4. ⚠️ Scoring system counts multiple times per hit
5. ⚠️ Fixed frame rate may cause speed variations on different systems

## 🔮 Future Improvements

- [ ] Implement complete sound system
- [ ] Fix known bugs
- [ ] Add difficulty levels
- [ ] High score system with persistence
- [ ] Multiple enemy types
- [ ] Power-ups and special weapons
- [ ] Improve game loop with delta time
- [ ] Add visual effects (particles, screen shake)
- [ ] Settings menu (volume, controls)
- [ ] Fullscreen mode

## 📝 Development

### Class Structure

```
GameObject (base)
    ├── Cenario (inherits)
    ├── Inimigo (inherits)
    ├── Mira (inherits)
    ├── Tiro (inherits)
    ├── Explosao (inherits)
    └── GameOver (inherits)
```

### Game Flow

1. **Main** → Creates **Application**
2. **Application** → Creates **JFrame** and **GameCore**
3. **GameCore.paint()** → Recursive loop:
   - Calls `update()` for logic
   - Draws all objects
   - Waits 80ms
   - Calls `repaint()`

### Collision Detection

Uses simple **bounding box**:
- Checks if the crosshair center is within enemy bounds
- Collision only counts when player is shooting
- Hit → Explosion at enemy position + respawn

## 👥 Contributing

This is an educational project developed as a TCC (Course Completion Project). Suggestions and improvements are welcome!

### To contribute:

1. Fork the project
2. Create a feature branch (`git checkout -b feature/MyFeature`)
3. Commit your changes (`git commit -m 'Add MyFeature'`)
4. Push to the branch (`git push origin feature/MyFeature`)
5. Open a Pull Request

## 📄 License

This project was developed for educational purposes.

## 🙏 Acknowledgments

Developed as part of a Course Completion Project (TCC).

---

**Developed with ☕ and Java**

For questions or suggestions about the code, check the [CLAUDE.md](CLAUDE.md) file for detailed technical guidance.
