package com.iesribera.tarea2_elena_ortiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.iesribera.tarea2_elena_ortiz.nivel.Nivel;
import com.iesribera.tarea2_elena_ortiz.nivel.NivelAvanzado;
import com.iesribera.tarea2_elena_ortiz.nivel.NivelFacil;
import com.iesribera.tarea2_elena_ortiz.nivel.NivelIntermedio;
import com.iesribera.tarea2_elena_ortiz.personajes.Personaje;
import com.iesribera.tarea2_elena_ortiz.personajes.PersonajeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private final ArrayList<Personaje> personajes = new ArrayList<>();
    private Personaje personajeSeleccionado;
    private GridLayout grid;
    private Tablero tablero;
    private Nivel nivel = new NivelFacil();
    private Casilla[][] casillas;
    private final int dificultad = 0;
    private int hipotenochasRestantes;
    private final List<Casilla> despejadas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Personaje oreo = new Personaje("Oreo", ContextCompat.getDrawable(this, R.drawable.android_oreo));
        Personaje kitkat = new Personaje("Kitkat", ContextCompat.getDrawable(this, R.drawable.android_kitkat));
        Personaje ginger = new Personaje("GingerBread", ContextCompat.getDrawable(this, R.drawable.android_gingerbread));
        personajes.add(oreo);
        personajes.add(kitkat);
        personajes.add(ginger);
        personajeSeleccionado = oreo;
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
                elegirPersonaje();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void elegirPersonaje() {
        Dialog personaje = new Dialog(this);
        personaje.setContentView(R.layout.spinner_personaje);
        personaje.setTitle("Personajes");

        Spinner spinner = personaje.findViewById(R.id.spinner_personaje);
        PersonajeAdapter personajeAdapter = new PersonajeAdapter(this, personajes);
        spinner.setAdapter(personajeAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                personajeSeleccionado = personajes.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                personaje.dismiss();
            }
        });
        personaje.show();
    }

    public void seleccionarNivel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] items = new CharSequence[3];
        items[0] = getString(R.string.nivel_facil);
        items[1] = getString(R.string.nivel_medio);
        items[2] = getString(R.string.nivel_avanzado);
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
            case Constantes.NivelFacil.OPCION_MENU:
                nivel = new NivelFacil();
                break;
            case Constantes.NivelMedio.OPCION_MENU:
                nivel = new NivelIntermedio();
                break;
            case Constantes.NivelAvanzado.OPCION_MENU:
                nivel = new NivelAvanzado();
                break;
        }
        if (nivel == null) {
            throw new IllegalArgumentException("Ha ocurrido un error al detectar el nivel");
        }
        hipotenochasRestantes = nivel.getHipotenochasOcultas();
        return nivel;
    }

    public void nuevaPartida() {
        tablero = new Tablero(nivel);
        casillas = crearPartida(nivel);
        this.hipotenochasRestantes = nivel.getHipotenochasOcultas();
    }

    public Casilla[][] crearPartida(Nivel nivel) {
        grid = findViewById(R.id.grid);
        grid.removeAllViews();
        grid.setColumnCount(nivel.getColumnas());
        grid.setRowCount(nivel.getFilas());
        casillas = new Casilla[nivel.getColumnas()][nivel.getFilas()];

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.setMargins(0, 0, 0, 10);
        param.height = ViewGroup.LayoutParams.MATCH_PARENT;
        param.width = ViewGroup.LayoutParams.MATCH_PARENT;
        float escala = getResources().getDisplayMetrics().density;
        float dpPx = (int) (50 * escala + 0.6f);
        height -= dpPx;
        LinearLayout.LayoutParams layoutParams = new
                LinearLayout.LayoutParams(width / nivel.getColumnas(), height / nivel.getFilas());
        layoutParams.setMargins(0, 0, 0, 0);
        for (int i = 0; i < nivel.getColumnas(); i++) {
            for (int j = 0; j < nivel.getFilas(); j++) {
                casillas[i][j] = new Casilla(this, j, i, (byte) tablero.getCasillas()[i][j]);
                Casilla casilla = casillas[i][j];
                casilla.setLayoutParams(layoutParams);
                casilla.setPulsada(false);
                casilla.setText(String.valueOf(tablero.getCasillas()[i][j]));
                casilla.setPadding(0, 0, 0, 0);
                //TODO: elena descomentar
                //   casillas[i][j].setBackgroundResource(R.drawable.my_button_bg);
                casilla.setTextColor(Color.BLACK);
                casilla.setTag(i + ";" + j);

                casilla.setOnClickListener(this);
                casilla.setOnLongClickListener(this);
                grid.addView(casilla);
            }
        }

        return casillas;
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
        Casilla casilla = (Casilla) view;
        casilla.setTextColor(Color.BLACK);
        if (casilla.getText().equals(String.valueOf(Constantes.TIENE_HIPOTENOCHA))) {
            casilla.setText("");
            casilla.setBackground(personajeSeleccionado.getImagen());
            Toast.makeText(MainActivity.this, R.string.derrota, Toast.LENGTH_LONG).show();
            terminarJuego();
        } else if (casilla.getText().equals(String.valueOf(Constantes.SIN_HIPOTENOCHAS_ALREDEDOR))) {
            casilla.setText("");
            casilla.setBackgroundColor(Color.RED);
            despejar(casilla, despejadas);
        } else {
            casilla.setText(String.valueOf(casilla.getText()));
        }
        casilla.setEnabled(false);
        casilla.setPulsada(true);
    }

    public void despejar(Casilla casilla, List<Casilla> despejadas) {
        for (Casilla despejada : despejadas) {
            if (despejada.getTag().equals(casilla.getTag())) {
                return;
            }
        }
        for (int i = casilla.getFila() - 1; i <= casilla.getFila() + 1; i++) {
            for (int j = casilla.getColumna() - 1; j <= casilla.getColumna() + 1; j++) {
                try {
                    Casilla casillaActual = casillas[i][j];
                    if (!casillaActual.isPulsada()) {
                        //  despejadas.add(casillaActual);
                        casillaActual.setTextColor(Color.BLACK);
                        if (casillaActual.getText().equals(String.valueOf(Constantes.SIN_HIPOTENOCHAS_ALREDEDOR))) {
                            casillaActual.setText("");
                            casillaActual.setBackgroundColor(Color.RED);
                            casillaActual.setEnabled(false);
                            casillaActual.setClickable(false);
                            casillaActual.setPulsada(true);
                            despejar(casillaActual, despejadas);
                        } else if (casillaActual.getText() != "" && Integer.parseInt(String.valueOf(casillaActual.getText()))
                                > Constantes.SIN_HIPOTENOCHAS_ALREDEDOR) {
                            casillaActual.setEnabled(false);
                            casillaActual.setPulsada(true);
                            casillaActual.setClickable(false);
                            casillaActual.setText(casillaActual.getText());
                        }

                    }

                } catch (ArrayIndexOutOfBoundsException ignored) {
                }


            }
        }
    }

    private void terminarJuego() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j].setEnabled(false);
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        Casilla pulsada = (Casilla) view;

        int valor = casillas[pulsada.getFila()][pulsada.getColumna()].getTieneHipotenocha();
        pulsada.setClickable(false);
        pulsada.setPulsada(true);
        pulsada.setEnabled(false);
        if (valor == Constantes.TIENE_HIPOTENOCHA) {
            pulsada.setText("");
            pulsada.setBackground(personajeSeleccionado.getImagen());
            hipotenochasRestantes--;

            if (hipotenochasRestantes == 0) {
                Toast.makeText(this, getString(R.string.victoria), Toast.LENGTH_LONG).show();
                terminarJuego();
            } else {
                Toast.makeText(this, String.format(getString(R.string.hipotenochas_restantes), hipotenochasRestantes),
                        Toast.LENGTH_LONG).show();
            }
        } else {
            pulsada.setBackgroundColor(Color.WHITE);
            pulsada.setText(String.valueOf(pulsada.getText()));
            Toast.makeText(this, getString(R.string.derrota_long_click), Toast.LENGTH_LONG).show();
            terminarJuego();
        }

        return true;
    }
}