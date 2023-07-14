package dev.android.appbuses.database;

import java.util.List;

import dev.android.appbuses.models.Asiento;
import dev.android.appbuses.models.Asiento_Numero;
import dev.android.appbuses.models.Compra;
import dev.android.appbuses.models.Compra_Detalle;
import dev.android.appbuses.models.Contrasenia;
import dev.android.appbuses.models.Disponibilidad;
import dev.android.appbuses.models.FormaPago;
import dev.android.appbuses.models.Frecuencia;
import dev.android.appbuses.models.Usuario;
import dev.android.appbuses.models.Usuario_Registro;
import dev.android.appbuses.models.Venta;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface api {
    @GET("listarTipoAsientosBus.php")
    Call<List<Asiento>> getSeats(@Query("id_bus")Integer id_bus,
                                 @Query("id_viaje")Integer id_viaje,
                                 @Query("id_parada")Integer id_parada);

    @GET("listarViajesDiarios.php")
    Call<List<Frecuencia>> getFrequencies();

    @GET("listarFormasPago.php")
    Call<List<FormaPago>> getPayments();

    @GET("obtenerDatosUsuariosEmail.php")
    Call<Usuario> getUser(@Query("email_usuario")String email_usuario);

    @GET("obtenerClavePasajero.php")
    Call<Contrasenia> getPassword(@Query("email_usuario")String email_usuario);

    @GET("listarCompras.php")
    Call<List<Compra>> getPurchases(@Query("id_comprador")Integer id_comprador);

    @GET("buscarVCoop.php")
    Call<List<Frecuencia>> getFrequenciesCooperative(@Query("origen") String origen, @Query("destino") String destino, @Query("cooperativa") String cooperativa);

    @GET("buscarVAsiento.php")
    Call<List<Frecuencia>> getFrequenciesSeat(@Query("origen") String origen, @Query("destino") String destino, @Query("asiento") String asiento);

    @GET("buscarVChasis.php")
    Call<List<Frecuencia>> getFrequenciesChassis(@Query("origen") String origen, @Query("destino") String destino, @Query("chasis") String chasis);

    @GET("buscarVCarroceria.php")
    Call<List<Frecuencia>> getFrequenciesBodywork(@Query("origen") String origen, @Query("destino") String destino, @Query("carroceria") String carroceria);

    @GET("buscarVTipo.php")
    Call<List<Frecuencia>> getFrequenciesType(@Query("origen") String origen, @Query("destino") String destino, @Query("tipo_viaje") Integer tipo_viaje);

    @GET("buscarViaje.php")
    Call<List<Frecuencia>> getFrequenciesOD(@Query("origen") String origen, @Query("destino") String destino);

    @GET("obtenerUltimaCompra.php")
    Call<Compra> getLastPurchase(@Query("id_comprador") Integer id_comprador);

    @FormUrlEncoded
    @POST("editarPerfilPasajero.php")
    Call<Usuario> updateUser(@Field("id_usuario") Integer id_usuario,
                             @Field("email_usuario") String email_usuario,
//                             @Field("tipo_usuario") String tipo_usuario,
                             @Field("nombre_usuario") String nombre_usuario,
                             @Field("apellido_usuario") String apellido_usuario,
                             @Field("telefono_usuario") String telefono_usuario,
                             @Field("foto_usuario") String foto_usuario);

    @FormUrlEncoded
    @POST("generarVenta.php")
    Call<Venta> insertData(@Field("id_comprador") Integer id_comprador,
                           @Field("id_viaje_pertenece")Integer id_viaje_pertenece,
                           @Field("id_parada_pertenece")Integer id_parada_pertenece,
                           @Field("fecha_venta")String fecha_venta,
                           @Field("id_forma_pago")Integer id_forma_pago,
                           @Field("estado_venta") Integer estado_venta,
                           @Field("total_venta")Float total_venta,
                           @Field("codigo_qr_venta")String codigo_qr_venta,
                           @Field("comprobante_venta")String comprobante_venta);

    @FormUrlEncoded
    @POST("editarClaveUsuario.php")
    Call<Usuario> updateUserPassword(@Field("id_usuario") Integer id_usuario,
                             @Field("clave_usuario") String clave_usuario);

    @FormUrlEncoded
    @POST("ingresarDetalleVenta.php")
    Call<Compra_Detalle> insertDataDetail(@Field("id_venta_pertenece") Integer id_venta_pertenece,
                                          @Field("id_asiento") Integer id_asiento,
                                          @Field("costo_asiento") Float costo_asiento,
                                          @Field("costo_parada") Float costo_parada,
                                          @Field("cedula_pasajero") String cedula_pasajero);

    @FormUrlEncoded
    @POST("editarCompra.php")
    Call<Venta> updatePurchase(@Field("id_venta") Integer id_venta,
                               @Field("codigo_qr_venta") String codigo_qr_venta);

    @FormUrlEncoded
    @POST("listarAsientosDisponibles.php")
    Call<Asiento_Numero> getSeat(@Field("id_bus_pertenece") Integer id_bus_pertenece,
                                 @Field("descripcion_asiento") String descripcion_asiento);

    @FormUrlEncoded
    @POST("editarAsiento.php")
    Call<Asiento_Numero> updateStateSeat(@Field("id_asiento") Integer id_asiento,
                                         @Field("estado") Integer estado);

    @GET("cantidadAsientosDisponibles.php")
    Call<Disponibilidad> getAmountSeats(@Query("id_bus") Integer id_bus,
                                        @Query("tipo_asiento") String tipo_asiento);

    @FormUrlEncoded
    @POST("registroPasajero.php")
    Call<Usuario_Registro> createUser(@Field("cedula_usuario") String cedula_usuario,
                                      @Field("email_usuario") String email_usuario,
                                      @Field("clave_usuario") String clave_usuario,
                                      @Field("nombre_usuario") String nombre_usuario,
                                      @Field("apellido_usuario") String apellido_usuario,
                                      @Field("telefono_usuario") String telefono_usuario);
}