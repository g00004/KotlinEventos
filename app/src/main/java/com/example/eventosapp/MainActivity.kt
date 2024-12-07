package com.example.eventosapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.eventosapp.ui.theme.EventosAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Modelo de datos para el evento
data class Evento(val titulo: String, val descripcion: String, val fecha: String)

// Función para hacer la llamada a la API
fun crearEvento(evento: Evento, onSuccess: () -> Unit, onError: () -> Unit) {
    RetrofitClient.apiService.crearEvento(evento).enqueue(object : Callback<Evento> {
        override fun onResponse(call: Call<Evento>, response: Response<Evento>) {
            if (response.isSuccessful) {
                onSuccess() // Llama a onSuccess si la respuesta es exitosa
            } else {
                onError() // Llama a onError si hubo un error con la respuesta
            }
        }

        override fun onFailure(call: Call<Evento>, t: Throwable) {
            onError() // Llama a onError si la conexión a la API falla
        }
    })
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventosAppTheme {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    // Crear un formulario con campos de texto
                    var titulo by remember { mutableStateOf("") }
                    var descripcion by remember { mutableStateOf("") }
                    var fecha by remember { mutableStateOf("") }

                    // Mostrar los campos del formulario
                    Text("Título del evento:")
                    TextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)
                    )

                    Text("Descripción del evento:")
                    TextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)
                    )

                    Text("Fecha del evento:")
                    TextField(
                        value = fecha,
                        onValueChange = { fecha = it },
                        modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)
                    )

                    // Botón para crear un evento
                    Button(onClick = {
                        // Verificar que los campos no estén vacíos
                        if (titulo.isNotEmpty() && descripcion.isNotEmpty() && fecha.isNotEmpty()) {
                            val evento = Evento(titulo = titulo, descripcion = descripcion, fecha = fecha)

                            // Llamada a crearEvento con los callbacks onSuccess y onError
                            crearEvento(evento,
                                onSuccess = {
                                    mostrarToast("Evento creado exitosamente")
                                },
                                onError = {
                                    mostrarToast("Error al crear evento")
                                })
                        } else {
                            mostrarToast("Todos los campos son obligatorios")
                        }
                    }) {
                        Text("Crear Evento")
                    }
                }
            }
        }
    }

    // Función composable para mostrar un Toast
    @Composable
    fun mostrarToast(mensaje: String) {
        val context = LocalContext.current
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EventosAppTheme {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            var titulo by remember { mutableStateOf("") }
            var descripcion by remember { mutableStateOf("") }
            var fecha by remember { mutableStateOf("") }

            Text("Título del evento:")
            TextField(
                value = titulo,
                onValueChange = { titulo = it },
                modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)
            )

            Text("Descripción del evento:")
            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)
            )

            Text("Fecha del evento:")
            TextField(
                value = fecha,
                onValueChange = { fecha = it },
                modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)
            )

            Button(onClick = {
                if (titulo.isNotEmpty() && descripcion.isNotEmpty() && fecha.isNotEmpty()) {
                    // Acción de creación del evento
                }
            }) {
                Text("Crear Evento")
            }
        }
    }
}
