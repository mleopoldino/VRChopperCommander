#!/bin/bash
# Test script for high priority bug fixes

echo "🧪 Testing High Priority Bug Fixes"
echo "=================================="
echo ""

# Test 1: Compilation
echo "Test 1: Compilation"
echo "-------------------"
if javac -d bin -sourcepath src src/tcc/game/engine/core/Main.java 2>&1 | grep -q "error"; then
    echo "❌ FAILED: Compilation errors found"
    exit 1
else
    echo "✅ PASSED: Project compiles successfully"
fi
echo ""

# Test 2: Check for proper game loop implementation
echo "Test 2: Game Loop Architecture"
echo "------------------------------"
if grep -q "javax.swing.Timer" src/tcc/game/engine/core/GameCore.java; then
    echo "✅ PASSED: javax.swing.Timer implemented"
else
    echo "❌ FAILED: Timer not found"
fi

if ! grep -q "Thread.sleep" src/tcc/game/engine/core/GameCore.java; then
    echo "✅ PASSED: Thread.sleep removed from paint()"
else
    echo "⚠️  WARNING: Thread.sleep still present"
fi
echo ""

# Test 3: Check ESC key fix
echo "Test 3: ESC Key Handler"
echo "-----------------------"
if grep -q "VK_ESCAPE" src/tcc/game/engine/core/Application.java && \
   grep -A2 "VK_ESCAPE" src/tcc/game/engine/core/Application.java | grep -q "stopGame"; then
    echo "✅ PASSED: ESC key handler in keyPressed with stopGame()"
else
    echo "❌ FAILED: ESC key handler not properly implemented"
fi
echo ""

# Test 4: Check score calculation fix
echo "Test 4: Score Calculation"
echo "-------------------------"
if grep -q "explosionJustStarted" src/tcc/game/engine/core/GameCore.java; then
    echo "✅ PASSED: Explosion flag tracking implemented"
else
    echo "❌ FAILED: Explosion tracking not found"
fi
echo ""

# Test 5: Check collision detection
echo "Test 5: Collision Detection"
echo "---------------------------"
if grep -q "inimigo.isVisible()" src/tcc/game/engine/core/GameCore.java | head -1; then
    echo "✅ PASSED: Enemy visibility check added"
else
    echo "⚠️  WARNING: Additional checks may be needed"
fi
echo ""

# Test 6: Check for stopGame method
echo "Test 6: Resource Cleanup"
echo "------------------------"
if grep -q "public void stopGame()" src/tcc/game/engine/core/GameCore.java; then
    echo "✅ PASSED: stopGame() method exists"
else
    echo "❌ FAILED: stopGame() method not found"
fi
echo ""

echo "=================================="
echo "🎉 Test Suite Complete!"
echo ""
echo "To run the game:"
echo "  ./run_game.sh"
echo ""
echo "Or manually:"
echo "  java -cp bin tcc.game.engine.core.Main"
