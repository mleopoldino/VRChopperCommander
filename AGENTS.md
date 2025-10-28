# Repository Guidelines

## Project Structure & Module Organization
- `src/tcc/game/engine/core` contains the entry point (`Main`), window lifecycle (`Application`), and main loop (`GameCore`). Supporting gameplay classes live under `src/tcc/game/engine`.
- `assets/images`, `assets/icon`, and `assets/sounds` store visual and audio resources; optimize PNGs and keep unused files out of version control.
- `bin` holds compiled bytecode. Do not commit it—run the build scripts to regenerate when needed.

## Build, Test, and Development Commands
```bash
javac -d bin -sourcepath src src/tcc/game/engine/core/Main.java
```
Compiles the minimum set of sources; useful for quick checks.

```bash
./run.sh
```
Full compile of engine packages (creates `bin` if missing) and launches the game with usage tips.

```bash
./run_game.sh
```
Runs the same entry point without rebuilding if classes already exist.

```bash
./test_fixes.sh
```
Smoke test suite that validates compilation, the timer-driven loop, ESC handler, scoring, collision visibility, and resource cleanup.

## Coding Style & Naming Conventions
- Target Java 8, keep packages rooted at `tcc.game.engine`.
- Preserve tab-based indentation and place braces on the same line as declarations.
- Use `PascalCase` for classes, `camelCase` for methods/fields, and `UPPER_SNAKE_CASE` for direction and state constants.
- Group imports by package and avoid unused or wildcard imports. Keep inline comments bilingual only when necessary for clarity.

## Testing Guidelines
- Run `./test_fixes.sh` before every pull request; add new assertions there when patching high-risk logic (input handling, scoring, timers).
- Perform a manual playthrough covering menu → gameplay → game over whenever you alter rendering or state transitions.
- Document any uncovered edge cases in the pull request so they can be scheduled for follow-up automation.

## Commit & Pull Request Guidelines
- Follow the existing history’s style: short, present-tense subjects (e.g., `Add ESC key handler`) with optional detail in the body. Keep subjects ≤72 characters.
- Reference related issues or documents, and include a concise summary of gameplay or UX changes.
- List all validation commands you ran (build, tests, manual steps) and attach screenshots or short clips when UI elements change.
