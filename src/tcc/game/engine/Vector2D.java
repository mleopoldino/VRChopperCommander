package tcc.game.engine;

/**
 * FIX: Immutable 2D vector class
 * Thread-safe by design - no setters, all fields are final
 * Shared instances (like in Cenario, Inimigo, Explosao) are now safe to reuse
 */
public final class Vector2D {

	//Definicao dos Atributos (now final - immutable)
    private final float x;
    private final float y;

	//Contrutores
    public Vector2D(){
        this(0f, 0f);
    }

    public Vector2D(int x, int y){
        this((float)x, (float)y);
    }

    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
    }

	//Getters (no setters - immutable)
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     * Creates a new Vector2D by adding another vector to this one
     * @param other The vector to add
     * @return A new Vector2D representing the sum
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * Creates a new Vector2D by subtracting another vector from this one
     * @param other The vector to subtract
     * @return A new Vector2D representing the difference
     */
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * Creates a new Vector2D by scaling this vector
     * @param scalar The scale factor
     * @return A new scaled Vector2D
     */
    public Vector2D scale(float scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    @Override
    public String toString() {
        return "Vector2D(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector2D)) return false;
        Vector2D other = (Vector2D) obj;
        return Float.compare(x, other.x) == 0 && Float.compare(y, other.y) == 0;
    }

    @Override
    public int hashCode() {
        int result = Float.floatToIntBits(x);
        result = 31 * result + Float.floatToIntBits(y);
        return result;
    }
}
