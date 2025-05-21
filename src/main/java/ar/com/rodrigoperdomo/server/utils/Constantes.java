package ar.com.rodrigoperdomo.server.utils;

public class Constantes {
  // Keys
  public static final String EMAIL_KEY = "email";
  public static final String AUTHORIZATION = "Authorization";
  public static final String BREARER_KEY = "Bearer ";
  public static final String EMAIL_ENV = "EMAIL";
  // Messages
  public static final String USER_CREATED_SUCCESS = "Usuario creado con exito!";
  public static final String LOGIN_SUCCESS = "Inicio de sesión exitoso!";
  public static final String PASSWORD_INCORRECT = "Contraseña Incorrecta!";
  public static final String USER_NOT_FOUND = "Usuario no encontrado!";
  public static final String SECRET_KEY_ENV = "SECRET_KEY";
  public static final String PRODUCT_CREATED_SUCCESS = "Publicacion creada con exito!";
  public static final String OK_MESSAGE = "OK";
  public static final String PUBLICACION_NOT_FOUND = "No se encontro la publicacion solicitada!";
  public static final String PUBLICACION_UPDATED_SUCCESS = "Publicacion actualizada con éxito!";
  public static final String IMAGEN_DELETED_SUCCES = "Imagen eliminada con éxito!";
  public static final String IMAGEN_UPLOAD_SUCCESS_ASSOCIATE_PUBLICACION =
      "Imagen subida correctamente y asociada a la publicacion solicitada!";
  public static final String PUBLICACION_DELETED_SUCCESS = "Publicacion eliminada con éxito!";
  public static final String COMPRA_VENTAS_ACTION_SUCCESS = "Compra-Venta realizada con éxito";
  // Cloudinary
  public static final String CLOUDINARY_CLOUD_NAME_ENV = "CLOUDINARY_CLOUD_NAME";
  public static final String CLOUDINARY_API_KEY_ENV = "CLOUDINARY_API_KEY";
  public static final String CLOUDINARY_SECRET_KEY_ENV = "CLOUDINARY_SECRET_KEY";
  public static final String CLOUD_NAME_KEY = "cloud_name";
  public static final String API_KEY_NAME_KEY = "api_key";
  public static final String API_SECRET_KEY = "api_secret";
  public static final String SECURE_URL_KEY = "secure_url";
  public static final String UPLOAD_PATH = "/upload/";
  public static final String URL_NO_VALIDA_PATH_FILE_MESSAGE = "URL no válida de Cloudinary";
  public static final String PUBLICACION_IMAGEN_NOT_FOUND =
      "La publicación no tiene imagen asociada.";
  public static final String ERROR_DELETE_IMAGEN_CLOUDINARY =
      "Error al eliminar imagen de Cloudinary";
  // General
  public static final String BARRA = "/";
  public static final String BARRA_REGEXP = "\\.[^\\.]+$";
  public static final String VACIO = "";
  public static final Long ONE_LONG = 1L;
  public static final Long ZERO_LONG = 0L;
  public static final String SPACE = " ";
  // Swagger
  public static final String TITLE_API = "Ventas Server";
  public static final String VERSION_API = "1.0";
  public static final String DESCRIPTION_API_SWAGGER = "Documentación del servidor, endpoints";
  public static final String BEARER_AUTH_STRING = "bearerAuth";
  public static final String BEARER_STRING = "bearer";
  public static final String JWT_STRING = "JWT";
  public static final String JSON_CREAR_PRODUCTO_DESCRIPCION_KEY =
      "JSON del producto como string, ejemplo:";
  public static final String JSON_CREAR_PRODUCTO_DESCRIPCION =
      "{ nombreProducto: Test, descripcion: desc, precio: 123, cantidadProducto: 1, idUsuario: 1 }";
  // EMAIL
  public static final String ASUNTO_MAIL = "Compra-Venta del producto ";
  public static final String CUERPO_MAIL =
      "Se ha vendido/comprado el producto con las siguientes caracteristicas: ";
  public static final String DATOS_CUERPO_MAIL_VENDEDOR = "Datos del vendedor ";
  public static final String DATOS_CUERPO_MAIL_COMPRADOR = "Datos del comprador";
}
