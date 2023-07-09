package dev.android.appbuses.database;

import java.util.List;

import dev.android.appbuses.models.Asiento;
import dev.android.appbuses.models.Compra;
import dev.android.appbuses.models.Contrasenia;
import dev.android.appbuses.models.FormaPago;
import dev.android.appbuses.models.Frecuencia;
import dev.android.appbuses.models.Usuario;
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
    Call<List<Asiento>> getSeats(@Query("id_bus")Integer id_bus, @Query("id_viaje")Integer id_viaje);

    @GET("listarViajesDiarios.php")
    Call<List<Frecuencia>> getFrequencies();

    @GET("listarFormasPago.php")
    Call<List<FormaPago>> getPayments();

    @GET("obtenerDatosUsuariosEmail.php")
    Call<Usuario> getUser(@Query("email_usuario")String email_usuario);

    @GET("obtenerClavePasajero.php")
    Call<Contrasenia> getPassword(@Query("email_usuario")String email_usuario);

    @GET("listarCompras.php")
    Call<List<Compra>> getPurchases(@Query("id_comprado")Integer id_comprado);

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

    @FormUrlEncoded
    @POST("editarPerfilPasajero.php")
    Call<Usuario> updateUser(@Field("id_usuario") Integer id_usuario,
                             @Field("email_usuario") String email_usuario,
                             @Field("nombre_usuario") String nombre_usuario,
                             @Field("apellido_usuario") String apellido_usuario,
                             @Field("telefono_usuario") String telefono_usuario,
                             @Field("foto_usuario") String foto_usuario);

    @FormUrlEncoded
    @POST("generarVenta.php")
    Call<Venta> insertData(@Field("id_comprador")Integer id_comprador,
                           @Field("id_viaje_pertenece")Integer id_viaje_pertenece,
                           @Field("id_parada_pertenece")Integer id_parada_pertenece,
                           @Field("fecha_venta")String fecha_venta,
                           @Field("id_forma_pago")Integer id_forma_pago,
                           @Field("total_venta")Float total_venta,
                           @Field("codigo_qr_venta")String codigo_qr_venta,
                           @Field("comprobante_venta")String comprobante_venta);

    @FormUrlEncoded
    @POST("editarClaveUsuario.php")
    Call<Usuario> updateUserPassword(@Field("id_usuario") Integer id_usuario,
                             @Field("clave_usuario") String clave_usuario);
}

