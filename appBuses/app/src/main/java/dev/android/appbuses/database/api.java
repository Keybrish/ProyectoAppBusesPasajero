package dev.android.appbuses.database;

import java.util.List;

import dev.android.appbuses.models.Asiento;
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

