package utils;

public class Flecha 
{
    private Punto punto;
    private char color; //v o r
    private char direccion; //i o d

    public Flecha(Punto punto, char color, char direccion) {
        this.punto = punto;
        this.color = color;
        this.direccion = direccion;
    }

    public Flecha() {
    }
     

    public Punto getPunto() {
        return punto;
    }

    public void setPunto(Punto punto) {
        this.punto = punto;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public char getDireccion() {
        return direccion;
    }

    public void setDireccion(char direccion) {
        this.direccion = direccion;
    }
}
