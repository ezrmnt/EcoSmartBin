package com.example.ecosmartbin.config

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbConnection(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "ecosmartbin.db"
        private const val DB_VERSION = 2

        const val TABLE_USUARIO = "usuario"
        const val COL_ID = "id"
        const val COL_NOMBRE = "nombre"
        const val COL_APELLIDO_PATERNO = "apellido_paterno"
        const val COL_APELLIDO_MATERNO = "apellido_materno"
        const val COL_TELEFONO = "telefono"
        const val COL_FECHA_NACIMIENTO = "fecha_nacimiento"
        const val COL_PAIS = "pais"
        const val COL_CORREO = "correo"
        const val COL_CONTRASENA = "contrasena"
        const val COL_CONTRASENA_CONFIRMACION = "contrasena_confirmacion"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableUsuario = """
            CREATE TABLE $TABLE_USUARIO (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOMBRE TEXT,
                $COL_APELLIDO_PATERNO TEXT,
                $COL_APELLIDO_MATERNO TEXT,
                $COL_TELEFONO INTEGER,
                $COL_FECHA_NACIMIENTO TEXT,
                $COL_PAIS TEXT,
                $COL_CORREO TEXT UNIQUE,
                $COL_CONTRASENA TEXT,
                $COL_CONTRASENA_CONFIRMACION TEXT
            )
        """.trimIndent()
        db.execSQL(createTableUsuario)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIO")
        onCreate(db)
    }
}
