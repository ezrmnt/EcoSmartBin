import com.example.ecosmartbin.models.LoginRequest
import com.example.ecosmartbin.models.LoginResponse
import com.example.ecosmartbin.models.Usuario
import com.example.ecosmartbin.response.RegistroResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.GET


interface ApiService {

    @POST("Usuarios/registro")
    suspend fun registrarUsuario(@Body usuario: Usuario): Response<RegistroResponse>

    @POST("Usuarios/login")
    suspend fun loginUsuario(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("Usuarios/{id}")
    suspend fun obtenerUsuarioPorId(@Path("id") id: Int): Response<Usuario>

    @GET("Usuarios/username/{username}")
    suspend fun obtenerUsuarioPorUsername(@Path("username") username: String): Response<Usuario>

    @PUT("Usuarios/actualizar/{id}")
    suspend fun actualizarUsuario(@Path("id") id: Int, @Body usuario: Usuario): Response<Usuario>
}
