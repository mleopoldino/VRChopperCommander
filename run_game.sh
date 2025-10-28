#!/bin/bash
# VR Chopper Commander - Run Script

echo "🚁 VR Chopper Commander - Starting..."
echo ""

# Check if compiled
if [ ! -d "bin" ] || [ -z "$(ls -A bin 2>/dev/null)" ]; then
    echo "📦 Compiling project..."
    javac -d bin -sourcepath src src/tcc/game/engine/core/Main.java
    if [ $? -ne 0 ]; then
        echo "❌ Compilation failed!"
        exit 1
    fi
    echo "✅ Compilation successful!"
    echo ""
fi

echo "🎮 Starting game..."
echo "Controls:"
echo "  - Arrow Keys: Move view/aim"
echo "  - SPACE: Fire weapon"
echo "  - ENTER: Start game"
echo "  - ESC: Exit game"
echo ""
java -cp bin tcc.game.engine.core.Main
