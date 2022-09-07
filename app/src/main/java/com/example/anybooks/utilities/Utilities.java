package com.example.anybooks.utilities;

public class Utilities {

    // ==================
    // TABLA LIBRO
    // ==================
    public static final String TABLA_LIBRO = "libro";

    public static final String CAMPO_ID_LIBRO = "id_libro";
    public static final String CAMPO_TITULO_LIBRO = "titulo";
    public static final String CAMPO_AUTOR_LIBRO = "autor";
    public static final String CAMPO_ANIO_LIBRO = "anio";
    public static final String CAMPO_IDIOMA_LIBRO = "idioma";
    public static final String CAMPO_EDITORIAL_LIBRO = "editorial";
    public static final String CAMPO_PAGINAS_LIBRO = "paginas";

    public static final String CREAR_TABLA_LIBRO = "CREATE TABLE " + TABLA_LIBRO + " ("
            + CAMPO_ID_LIBRO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_TITULO_LIBRO + " TEXT, "
            + CAMPO_AUTOR_LIBRO + " TEXT, "
            + CAMPO_ANIO_LIBRO + " INTEGER, "
            + CAMPO_IDIOMA_LIBRO + " TEXT, "
            + CAMPO_EDITORIAL_LIBRO + " TEXT, "
            + CAMPO_PAGINAS_LIBRO + " INTEGER"
            + ");";

    // ==================
    // TABLA ARTICULO
    // ==================
    public static final String TABLA_ARTICULO = "articulo";

    public static final String CAMPO_ID_ARTICULO = "id_articulo";
    public static final String CAMPO_TITULO_ARTICULO = "titulo";
    public static final String CAMPO_AUTOR_ARTICULO = "autor";
    public static final String CAMPO_ANIO_ARTICULO = "anio";
    public static final String CAMPO_IDIOMA_ARTICULO = "idioma";

    public static final String CREAR_TABLA_ARTICULO = "CREATE TABLE " + TABLA_ARTICULO + " ("
            + CAMPO_ID_ARTICULO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_TITULO_ARTICULO + " TEXT, "
            + CAMPO_AUTOR_ARTICULO + " TEXT, "
            + CAMPO_ANIO_ARTICULO + " INTEGER, "
            + CAMPO_IDIOMA_ARTICULO + " TEXT"
            + ");";

    // ==================
    // TABLA MULTIMEDIA
    // ==================
    /*
        Valores para el formato de multimedia
        1 = Diapositiva
        2 = Grabación sonora
        3 = Microforma
        4 = Música
        5 = Película
        6 = Transparencia
        7 = Recurso electrónico
        8 = Videograbación
    */
//    public static final String TABLA_MULTIMEDIA = "multimedia";
//
//    public static final String CAMPO_ID_MULTIMEDIA = "id_multimedia";
//    public static final String CAMPO_TITULO_MULTIMEDIA = "titulo";
//    public static final String CAMPO_AUTOR_MULTIMEDIA = "autor";
//    public static final String CAMPO_ANIO_MULTIMEDIA = "anio";
//    public static final String CAMPO_IDIOMA_MULTIMEDIA = "idioma";
//    public static final String CAMPO_FORMATO_MULTIMEDIA = "formato";
//
//    public static final String CREAR_TABLA_MULTIMEDIA = "CREATE TABLE " + TABLA_MULTIMEDIA + " ("
//            + CAMPO_ID_MULTIMEDIA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + CAMPO_TITULO_MULTIMEDIA + " TEXT, "
//            + CAMPO_AUTOR_MULTIMEDIA + " TEXT, "
//            + CAMPO_ANIO_MULTIMEDIA + " INTEGER, "
//            + CAMPO_IDIOMA_MULTIMEDIA + " TEXT, "
//            + CAMPO_FORMATO_MULTIMEDIA + " INTEGER"
//            + ");";

    // ==================
    // TABLA PRESTAMO
    // ==================
    /*
        Si un id de un recurso es -1, quiere decir que no es de ese tipo
    */
//    public static final String TABLA_PRESTAMO = "prestamo";
//
//    public static final String CAMPO_ID_PRESTAMO = "id_prestamo";
//    public static final String CAMPO_NOMBRE_PERSONA_PRESTAMO = "nombre_persona";
//    public static final String CAMPO_CONTACTO_PERSONA_PRESTAMO = "contacto_persona";
//    public static final String CAMPO_ID_LIBRO_PRESTAMO = "id_libro";
//    public static final String CAMPO_ID_ARTICULO_PRESTAMO = "id_articulo";
//    public static final String CAMPO_ID_MULTIMEDIA_PRESTAMO = "id_multimedia";
//    public static final String CAMPO_FECHA_INICIO_PRESTAMO = "fecha_inicio";
//    public static final String CAMPO_FECHA_FIN_PRESTAMO = "fecha_fin";
//
//    public static final String CREAR_TABLA_PRESTAMO = "CREATE TABLE " + TABLA_PRESTAMO + " ("
//            + CAMPO_ID_PRESTAMO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + CAMPO_NOMBRE_PERSONA_PRESTAMO + " TEXT, "
//            + CAMPO_CONTACTO_PERSONA_PRESTAMO + " TEXT, "
//            + CAMPO_ID_LIBRO_PRESTAMO + " INTEGER, " // FK
//            + CAMPO_ID_ARTICULO_PRESTAMO + " INTEGER, " // FK
//            + CAMPO_ID_MULTIMEDIA_PRESTAMO + " INTEGER, " // FK
//            + CAMPO_FECHA_INICIO_PRESTAMO + " TEXT, "
//            + CAMPO_FECHA_FIN_PRESTAMO + " TEXT"
//            + ");";

    // ==================
    // TABLA USUARIO
    // ==================
    public static final String TABLA_USUARIO = "usuario";

    public static final String CAMPO_ID_USUARIO = "id_usuario";
    public static final String CAMPO_NOMBRE_USUARIO = "nombre";
    public static final String CAMPO_CONTRASENIA_USUARIO = "contrasenia";

    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + " ("
            + CAMPO_ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_NOMBRE_USUARIO + " TEXT, "
            + CAMPO_CONTRASENIA_USUARIO + " TEXT"
            + ");";
}
