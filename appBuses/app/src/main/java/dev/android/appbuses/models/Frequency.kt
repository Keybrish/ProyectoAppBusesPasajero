package dev.android.appbuses.models
import java.io.Serializable
import java.time.LocalTime

data class Frecuencia (
    val id_frecuencia: Int,
    val id_cooperativa: Int,
    val nombre_cooperativa: String,
    val fotografia: String,
    val origen_frecuencia: String,
    val destino_frecuencia: String,
    val destino_provincia_frecuencia: String,
    val duracion_frecuencia: LocalTime,
    val tipo_frecuencia: Int,
    val estado_frecuencia: Int,
    val fecha_viaje: String,
    val hora_salida: String,
    val hora_llegada: String,
    val id_bus: Int,
    val numero_bus: String,
    val placa_bus: String,
    val chasis_bus: String,
    val carroceria_bus: String,
    val costo_frecuencia: Float
): Serializable