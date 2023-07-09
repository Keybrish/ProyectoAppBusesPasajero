package dev.android.appbuses.models
import java.io.Serializable

data class Frecuencia (
    val id_parada: Int,
    val id_viaje: Int,
//    val id_cooperativa: Int,
    val nombre_cooperativa: String,
    val fotografia: String,
    val origen: String,
    val destino: String,
    val destinoProvincia: String,
    val duracion_parada: String,
//    val tipo_frecuencia: Int,
    val estado: Int,
    val fecha_viaje: String,
    val hora_salida_viaje: String,
    val hora_llegada_viaje: String,
    val id_bus: Int,
    val numero_bus: String,
    val placa_bus: String,
    val chasis_bus: String,
    val carroceria_bus: String,
    val costo_parada: Float
): Serializable

data class Venta(
    val id_comprador: Int,
    val id_viaje_pertenece: Int,
    val id_parada_pertenece: Int,
    val fecha_venta: String,
    val id_forma_pago: Int,
//    val estado_venta: Int,
    val total_venta: Float?,
    val codigo_qr_venta: String,
    val comprobante: String
): Serializable

data class Asiento (
    val descripcion_asiento: String,
    val costo_adicional: Float,
    val costo_parada: Float
): Serializable

data class FormaPago (
    val id_forma_pago: Int,
    val forma_pago: String
): Serializable

data class Usuario (
    val id_usuario: Int,
    val cedula_usuario: String,
    val tipo_usuario: String,
    val email_usuario: String,
    val clave_usuario: String,
    val nombre_usuario: String,
    val apellido_usuario: String,
    val telefono_usuario: String
): Serializable