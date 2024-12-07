package com.example.eventosapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class Evento(
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val ubicacion: String
)

interface ApiService {
    @POST("eventos")
    fun crearEvento(@Body evento: Evento): Call<Evento>
}
