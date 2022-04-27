package es.ieslavereda.myweather;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import es.ieslavereda.myweather.activities.Ciudades;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class Parameters {

    public final static String URL = "https://api.openweathermap.org/data/2.5/";
    public final static String API = "571de3fad30f1b0f6c30413b1f3385fd";
    public final static String ICON_URL_PRE = "http://openweathermap.org/img/wn/";
    public static final String ICON_URL_POST = "@2x.png";

    public static List<Ciudades> ciudadesList = new ArrayList<>();

    static{

        ciudadesList.add(new Ciudades("Valencia","https://phantom-elmundo.unidadeditorial.es/2959d66274f76ba77c5fd314344e7d66/crop/2x0/3072x2047/resize/473/f/webp/assets/multimedia/imagenes/2022/02/22/16455200722788.jpg",39.4077643f,-0.4315509f));
        ciudadesList.add(new Ciudades("Barcelona","https://fotos.hoteles.net/articulos/guia-barcelona-ciudad-2400-1.jpg",41.3926467f,2.0701496f));
        ciudadesList.add(new Ciudades("Madrid","https://s03.s3c.es/imag/_v0/770x420/f/7/a/madrid-centro-coches-dreamstime.jpg",40.4378698f,-3.8196191f));
    }


}
