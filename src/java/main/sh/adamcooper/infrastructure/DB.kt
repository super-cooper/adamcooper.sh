package sh.adamcooper.infrastructure

import org.jetbrains.exposed.v1.jdbc.Database


/** Manages network connection to the database server */
object DB {
    private val host = System.getenv("DB_HOST") ?: "localhost"
    private val port = System.getenv("DB_PORT")?.toUInt() ?: 3306u
    private const val driver = "com.mysql.cj.jdbc.Driver"
    private val user = System.getenv("DB_USER") ?: "web"
    private val password = System.getenv("DB_PASSWORD")!!

    fun connect(database: String): Database =
        Database.connect(
            "jdbc:mysql://${this.host}:${this.port}/$database",
            driver = this.driver,
            user = this.user,
            password = this.password,
        )
}
