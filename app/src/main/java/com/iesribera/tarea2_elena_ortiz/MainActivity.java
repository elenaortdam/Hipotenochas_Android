package com.iesribera.tarea2_elena_ortiz;

import androidx.appcompat.app.AppCompatActivity;

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

import com.iesribera.tarea2_elena_ortiz.nivel.Nivel;
import com.iesribera.tarea2_elena_ortiz.nivel.NivelAvanzado;
import com.iesribera.tarea2_elena_ortiz.nivel.NivelFacil;
import com.iesribera.tarea2_elena_ortiz.nivel.NivelIntermedio;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Button> hipotenochas;
    private GridLayout grid;
    private Nivel nivel = new NivelFacil();
    private Casilla[][] casillas;
    private int dificultad = 0;
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crearPartida(nivel);
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
//                showPer();
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
        ;
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

    public void nuevaPartida() {
        grid = findViewById(R.id.grid);
        grid.removeAllViews();
        casillas = crearPartida(nivel);
        Tablero tablero = new Tablero(casillas);
        System.out.println(tablero);
    }

    public Casilla[][] crearPartida(Nivel nivel) {
        hipotenochas = new ArrayList<Button>();
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

    public void onClick(View v) {
        Button boton = (Button) v;
        boton.setEnabled(false);
        boton.setTextColor(Color.BLACK);
        if (boton.getText().equals("0")) {
            descubrirCelda(boton);
            int[] posicion = obtenerPosicion((String) boton.getTag());
            int hipotenochas = Nivel.contarHipotenochasAlrededor(casillas, posicion[0], posicion[1]);
            if (hipotenochas == 0) {
                boton.setText("");
                boton.setBackgroundColor(Color.RED);
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