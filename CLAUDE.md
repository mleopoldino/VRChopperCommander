# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

VR Chopper Commander is a Java Swing-based helicopter shooting game. The player controls a targeting reticle from a first-person cockpit view to shoot down incoming enemy helicopters that grow larger as they approach. The game tracks points and errors, ending when the player misses too many targets.

## Build and Run

This is an Eclipse project configured for Java SE 8.

**Compile the project:**
```bash
javac -d bin -sourcepath src src/tcc/game/engine/core/Main.java
```

**Run the game:**
```bash
java -cp bin tcc.game.engine.core.Main
```

The game window will open at 756x530 pixels with the title "VR Chopper Commander".

## Game Controls

- **Arrow Keys (UP/DOWN/LEFT/RIGHT)**: Move the view/targeting reticle
- **SPACE**: Fire weapon
- **ENTER**: Start game from title screen
- **ESC**: Quit game (keyTyped handler)

## Architecture

### Core Game Loop

The application uses a custom rendering loop rather than a standard game loop:

1. **Main.java** - Entry point that instantiates Application
2. **Application.java** - Creates JFrame window and runs the main loop, handles all keyboard input via KeyListener
3. **GameCore.java** - Component that handles game logic and rendering via paint() method, which calls itself recursively using repaint() after an 80ms sleep

The paint method (GameCore.java:78) serves as both the render and update loop, calling update() internally before drawing all game objects.

### GameObject Hierarchy

All visual game elements inherit from **GameObject**, which provides:
- Position management via Point class
- Sprite animation via SpriteSheet class
- Scaling support
- Basic draw() and update() methods

Key subclasses:
- **Cenario** - Scrolling background with boundary limits
- **Inimigo** (Enemy) - Helicopters that spawn randomly, scale up over time, and respawn when reaching full scale or being destroyed
- **Mira** (Crosshair) - Player's targeting reticle
- **Tiro** (Shot) - Weapon fire visualization
- **Explosao** (Explosion) - Animated explosion effect on hit

### Direction System

Both Cenario and Inimigo use a directional constant system (SOBE=up, DESCE=down, ESQUERDA=left, DIREITA=right, CENTRO=stopped) combined with Vector2D objects for movement. When the player moves in a direction, both the background scrolls AND enemies move in the opposite direction to simulate camera movement.

### Collision Detection

The verificaColisao() method (GameCore.java:132) performs bounding box collision between the center point of the crosshair (Mira) and the enemy helicopter's rectangular bounds. This is checked each frame when a shot (Tiro) is visible.

### Game State Management

The game has three states controlled by the `count` variable:
- count=0: Title screen showing capa_sprite.png
- count=1+: Gameplay with cockpit UI, scoring, and game over detection
- Game over triggered when countError >= 3 (validaGameOver at GameCore.java:188)

Scoring: Points = (countDestroy/4) * 10, where countDestroy increments by 4 frames per explosion animation.

### Asset Structure

All assets are in the `assets/` directory:
- `assets/images/` - PNG sprites for scenery, helicopters, explosions, UI elements
- `assets/sounds/` - MP3 audio files (currently commented out in code)
- `assets/icon/` - Application icon

SpriteSheet handles animation by cycling through multiple images loaded for each GameObject.

## Code Conventions

- Comments are in Portuguese
- Instance variables use camelCase
- Constants use UPPER_SNAKE_CASE
- Classes use PascalCase
- Package structure: tcc.game.engine (core classes in .core subpackage)

## Known Issues

- Sound system is commented out (GameCore.java:26, 55-56)
- ESC key handler in Application.keyTyped never triggers (KeyEvent.getKeyCode() returns 0 for keyTyped events)
- Thread.sleep in paint method causes fixed 80ms frame time regardless of actual render time
- No vsync or proper frame rate limiting
- Collision detection happens only when Tiro is visible, not just when checking the aim
