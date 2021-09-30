package chess;

public enum Color {
    White(0), Black(0.5f);

    private float colorValue;

    private Color(float colorValue) {
        this.colorValue = colorValue;
    }

    public float getColorValue() {
        return colorValue;
    }
}