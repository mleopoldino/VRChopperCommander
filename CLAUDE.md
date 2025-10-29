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
- **ESC**: Quit game (handled via KeyEventDispatcher)

## Architecture

### Core Game Loop

The application uses a Swing Timer-based game loop running at 60 FPS:

1. **Main.java** - Entry point that instantiates Application
2. **Application.java** - Creates JFrame window and handles all keyboard input via KeyEventDispatcher
3. **GameCore.java** - Component that handles game logic and rendering:
   - **Swing Timer** (16ms interval) calls `updateGameLogic()` for game state updates
   - **updateGameLogic()** updates all game objects and handles sound transitions
   - **paintComponent()** handles rendering only (separates logic from rendering)

The architecture follows proper Swing EDT practices with separated update and render phases.

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
- `assets/sounds/` - WAV audio files (fully implemented via SoundManager)
- `assets/icon/` - Application icon

SpriteSheet handles animation by cycling through multiple images loaded for each GameObject.

### Sound System

The game uses a thread-safe sound system implemented via **SoundManager.java**:
- **Background music** (BackgroundTheme.wav) plays on title screen
- **Helicopter sound** (HelicopterSoundEffect.wav) loops during gameplay
- **Machine gun sound** (MachineGunSoundEffect.wav) plays when firing
- **Explosion sound** (ExplosionSoundEffect.wav) plays on enemy destruction

All sound operations execute off the Event Dispatch Thread using an ExecutorService to prevent EDT blocking.

## Code Conventions

- Comments are in Portuguese
- Instance variables use camelCase
- Constants use UPPER_SNAKE_CASE
- Classes use PascalCase
- Package structure: tcc.game.engine (core classes in .core subpackage)

## Recent Improvements (v2.0+)

All previously known issues have been resolved:

1. ✅ **Sound system fully implemented** - SoundManager with thread-safe ExecutorService
2. ✅ **ESC key functional** - Fixed via KeyEventDispatcher in Application.java
3. ✅ **Game loop optimized** - Now runs at 60 FPS using Swing Timer (16ms intervals)
4. ✅ **Proper EDT handling** - Separated game logic (updateGameLogic) from rendering (paintComponent)
5. ✅ **Explosion counting fixed** - Explosions now count only once per hit
6. ✅ **Code quality improvements** - Serialization warnings fixed, magic numbers extracted to GameConfig.java, immutable Vector2D pattern
