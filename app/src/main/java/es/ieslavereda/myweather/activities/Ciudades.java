package es.ieslavereda.myweather.activities;

import java.io.Serializable;

public class Ciudades implements Serializable {
    private String nombre;
    private String imagen;
    private float lat;
    private float lon;

    public Ciudades(String nombre, String imagen, float lat, float lon) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.lat = lat;
        this.lon = lon;
    }

    public String getNombre() {
        return nombre;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getImagen() {
        return imagen;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
