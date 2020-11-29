package com.iesribera.tarea2_elena_ortiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iesribera.tarea2_elena_ortiz.nivel.Nivel;
import com.iesribera.tarea2_elena_ortiz.nivel.NivelAvanzado;
import com.iesribera.tarea2_elena_ortiz.nivel.NivelFacil;
import com.iesribera.tarea2_elena_ortiz.nivel.NivelIntermedio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final Set<String> descubiertas = new HashSet<>();
    private GridLayout grid;
    private Nivel nivel = new NivelFacil();
    private Casilla[][] casillas;
    private final int dificultad = 0;
    int personaje = 0;
    static ArrayList<Button> personajes = new ArrayList<>();
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nuevaPartida();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.instrucciones:
                mostrarInstrucciones();
                return true;
            case R.id.nuevo_juego:
                nuevaPartida();
                return true;
            case R.id.configurar:
                seleccionarNivel();
                return true;
            case R.id.selec_personaje:
                mostrarPersonajes();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void seleccionarNivel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] items = new CharSequence[3];
        items[0] = Valores.NivelFacil.TEXTO;
        items[1] = Valores.NivelMedio.TEXTO;
        items[2] = Valores.NivelAvanzado.TEXTO;
        builder.setTitle(R.string.titulo_nivel)
                .setSingleChoiceItems(items, dificultad, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nivel = crearNivel(i);
                    }
                })
                .setPositiveButton(R.string.volver, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nuevaPartida();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private Nivel crearNivel(int i) {
        switch (i) {
            case Valores.NivelFacil.OPCION_MENU:
                nivel = new NivelFacil();
                break;
            case Valores.NivelMedio.OPCION_MENU:
                nivel = new NivelIntermedio();
                break;
            case Valores.NivelAvanzado.OPCION_MENU:
                nivel = new NivelAvanzado();
                break;
        }
        if (nivel == null) {
            throw new IllegalArgumentException("Ha ocurrido un error al detectar el nivel");
        }
        return nivel;
    }

    public void mostrarPersonajes() {
        Personaje selecPersonaje = new Personaje();
        selecPersonaje.show(getFragmentManager(), null);
    }

    public void setPersonaje(int i) {
        personaje = i;
        for (Button boton : personajes) {
            seleccionarPersonaje(boton);
        }
    }

    public void seleccionarPersonaje(Button boton) {
        switch (personaje) {
            case 0:
                boton.setBackgroundResource(R.drawable.android_oreo);
                break;
            case 1:
                boton.setBackgroundResource(R.drawable.android_kitkat);
                break;
            case 2:
                boton.setBackgroundResource(R.drawable.android_gingerbread);
                break;
        }
        boton.setText("");
    }

    public void nuevaPartida() {
        grid = findViewById(R.id.grid);
        grid.removeAllViews();
        casillas = crearPartida(nivel);
        //TODO: borrar
        Tablero tablero = new Tablero(casillas);
        System.out.println(tablero);
    }

    public Casilla[][] crearPartida(Nivel nivel) {
        grid = findViewById(R.id.grid);
        grid.setColumnCount(nivel.getColumnas());
        grid.setRowCount(nivel.getFilas());
        Tablero tablero = new Tablero(nivel);
        casillas = tablero.getCasillas();
        toma();
        Button boton;
        for (int i = 0; i < nivel.getColumnas(); i++) {
            for (int j = 0; j < nivel.getFilas(); j++) {
                boton = new Button(this);
                boton.setLayoutParams(new ViewGroup.LayoutParams(width / nivel.getColumnas(),
                        ((height) / nivel.getFilas())));
                boton.setText(String.valueOf(casillas[i][j].getTieneHipotenocha()));
                boton.setPadding(0, 0, 0, 0);
                boton.setBackgroundResource(R.drawable.my_button_bg);
                boton.setTextColor(Color.BLACK);
                boton.setTag(i + "," + j);
                boton.setOnClickListener(this);
//                boton.setOnLongClickListener(this);
                grid.addView(boton);
            }
        }
        return casillas;
    }

    //TODO: modificar
    public void toma() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        float escala = getResources().getDisplayMetrics().density;
        float dpPx = (int) (80 * escala + 0.5f);
        height -= dpPx;
    }

    public void mostrarInstrucciones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.instrucciones_texto)
                .setTitle(R.string.instrucciones)
                .setPositiveButton(R.string.volver, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClick(View view) {
        Button boton = (Button) view;
        boton.setEnabled(false);
        boton.setTextColor(Color.BLACK);
        if (boton.getText().equals("0")) {
            descubrirCelda(boton);
            String coordenada = (String) boton.getTag();
            descubiertas.add(coordenada);
            int[] posicion = obtenerPosicion(coordenada);
            int hipotenochas = Nivel.contarHipotenochasAlrededor(casillas, posicion[0], posicion[1]);
            if (hipotenochas == 0) {
                boton.setText("");
                boton.setBackgroundColor(Color.RED);
                for (int i = 0; i < grid.getChildCount(); i++) {
                    ArrayList<String> hecho = new ArrayList<>();
                    despejar(boton, hecho);
                }
            } else {
                boton.setText(String.valueOf(hipotenochas));
            }
        } else {
//            inabilitar();
            boton.setText("");
            boton.setBackgroundResource(R.mipmap.oreo_foreground);
            Toast.makeText(MainActivity.this,
                    R.string.derrota, Toast.LENGTH_LONG).show();
        }
    }

    public void despejar(Button boton, ArrayList<String> despejadas) {
        for (String coordenada : despejadas) {
            if (coordenada.equals(boton.getTag())) return;
        }
        despejadas.add((String) boton.getTag());
        boton.setEnabled(false);
        int[] posicion = obtenerPosicion((String) boton.getTag());
        for (int i = 0; i < grid.getChildCount(); i++) {
            Button botonHijo = (Button) grid.getChildAt(i);
            int[] posicionHijo = obtenerPosicion((String) grid.getChildAt(i).getTag());
            int hipotenochas = Nivel.contarHipotenochasAlrededor(casillas, posicionHijo[0], posicionHijo[1]);
            if (hipotenochas == 0) {
                if (posicion[0] == posicionHijo[0] & (posicion[1] == (1 + posicionHijo[1])
                        || posicion[1] == (posicionHijo[1] - 1))) {
                    botonHijo.setBackgroundColor(Color.RED);
                    botonHijo.setText("");
                    despejar(botonHijo, despejadas);
                }
                if (posicion[1] == posicionHijo[1] & (posicion[0] == (1 + posicionHijo[0]) || posicion[0] == (posicionHijo[0] - 1))) {
                    botonHijo.setBackgroundColor(Color.RED);
                    botonHijo.setText("");
                    despejar(botonHijo, despejadas);
                }
            }
        }
    }

    int[] obtenerPosicion(String coordenada) {
        int[] posicion = new int[2];
        String coma = ",";
        String[] coordenadas = coordenada.split(coma);
        for (int i = 0; i < coordenadas.length; i++) {
            posicion[i] = Integer.parseInt(coordenadas[i]);
        }
        return posicion;
    }

    public void descubrirCelda(Button boton) {
        boton.setBackgroundColor(Color.WHITE);
    }
}