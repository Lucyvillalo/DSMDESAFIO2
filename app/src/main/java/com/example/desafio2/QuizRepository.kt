package com.example.desafio2

data class Question(
    val text: String,
    val options: List<String>,
    val correctIndex: Int
)

object QuizRepository {
    const val TIPO_CULTURA = "cultura"
    const val TIPO_CIENCIA = "ciencia"
    const val TIPO_DEPORTES = "deportes"
    const val TIPO_HISTORIA = "historia"

    const val DIFICULTAD_FACIL = "facil"
    const val DIFICULTAD_DIFICIL = "dificil"

    fun nombreVisible(tipo: String): String {
        return when (tipo) {
            TIPO_CULTURA -> "Cultura general"
            TIPO_CIENCIA -> "Ciencia"
            TIPO_DEPORTES -> "Deportes"
            TIPO_HISTORIA -> "Historia"
            else -> "Quiz"
        }
    }

    fun dificultadVisible(dificultad: String): String {
        return when (dificultad) {
            DIFICULTAD_DIFICIL -> "Difícil"
            else -> "Fácil"
        }
    }

    fun obtenerPreguntas(tipo: String, dificultad: String): List<Question> {
        return preguntas.getValue(tipo).getValue(dificultad)
    }

    private val preguntas: Map<String, Map<String, List<Question>>> = mapOf(
        TIPO_CULTURA to mapOf(
            DIFICULTAD_FACIL to listOf(
                Question("¿Cuál es la capital de El Salvador?", listOf("San Salvador", "Santa Ana", "San Miguel"), 0),
                Question("¿Cuántos días tiene una semana?", listOf("5", "7", "10"), 1),
                Question("¿Qué planeta es conocido como el planeta rojo?", listOf("Venus", "Marte", "Júpiter"), 1),
                Question("¿Cuál es el idioma oficial de Brasil?", listOf("Español", "Portugués", "Francés"), 1),
                Question("¿Cuántos colores tiene un semáforo común?", listOf("2", "3", "4"), 1)
            ),
            DIFICULTAD_DIFICIL to listOf(
                Question("¿Qué país tiene como capital a Reikiavik?", listOf("Islandia", "Noruega", "Finlandia"), 0),
                Question("¿Cuál es el elemento químico con símbolo W?", listOf("Wolframio", "Wilsonio", "Westerio"), 0),
                Question("¿Qué obra fue escrita por Miguel de Cervantes?", listOf("La Odisea", "Don Quijote de la Mancha", "La Divina Comedia"), 1),
                Question("¿En qué continente se encuentra el desierto de Gobi?", listOf("África", "Asia", "Oceanía"), 1),
                Question("¿Cuál es la moneda oficial de Japón?", listOf("Yen", "Won", "Yuan"), 0)
            )
        ),
        TIPO_CIENCIA to mapOf(
            DIFICULTAD_FACIL to listOf(
                Question("¿Qué órgano bombea la sangre?", listOf("Pulmón", "Corazón", "Hígado"), 1),
                Question("¿Cuál es el estado sólido del agua?", listOf("Vapor", "Hielo", "Lluvia"), 1),
                Question("¿Qué gas respiramos principalmente para vivir?", listOf("Oxígeno", "Helio", "Neón"), 0),
                Question("¿Cuántos sentidos se mencionan tradicionalmente?", listOf("3", "5", "8"), 1),
                Question("¿Qué estrella está en el centro del sistema solar?", listOf("La Luna", "El Sol", "Sirio"), 1)
            ),
            DIFICULTAD_DIFICIL to listOf(
                Question("¿Qué partícula tiene carga negativa?", listOf("Protón", "Electrón", "Neutrón"), 1),
                Question("¿Cuál es la unidad básica de la herencia?", listOf("Gen", "Átomo", "Tejido"), 0),
                Question("¿Qué científico propuso la teoría de la relatividad?", listOf("Newton", "Einstein", "Darwin"), 1),
                Question("¿Qué proceso usan las plantas para producir alimento?", listOf("Fotosíntesis", "Fermentación", "Evaporación"), 0),
                Question("¿Cuál es el símbolo químico del sodio?", listOf("S", "Na", "So"), 1)
            )
        ),
        TIPO_DEPORTES to mapOf(
            DIFICULTAD_FACIL to listOf(
                Question("¿Cuántos jugadores tiene un equipo de fútbol en cancha?", listOf("9", "10", "11"), 2),
                Question("¿En qué deporte se usa una raqueta?", listOf("Tenis", "Natación", "Boxeo"), 0),
                Question("¿Cuántos aros hay en una cancha de baloncesto?", listOf("1", "2", "4"), 1),
                Question("¿Qué deporte se juega en una piscina?", listOf("Natación", "Béisbol", "Ciclismo"), 0),
                Question("En el fútbol, ¿qué tarjeta indica expulsión?", listOf("Amarilla", "Roja", "Azul"), 1)
            ),
            DIFICULTAD_DIFICIL to listOf(
                Question("¿Cada cuántos años se celebran normalmente los Juegos Olímpicos?", listOf("2", "4", "6"), 1),
                Question("En béisbol, ¿cuántos strikes eliminan al bateador?", listOf("2", "3", "4"), 1),
                Question("¿Qué país ganó el Mundial de Fútbol 2018?", listOf("Francia", "Croacia", "Alemania"), 0),
                Question("¿Cuántos sets se juegan como máximo en muchos partidos masculinos de Grand Slam?", listOf("3", "5", "7"), 1),
                Question("¿Qué disciplina combina natación, ciclismo y carrera?", listOf("Triatlón", "Pentatlón", "Decatlón"), 0)
            )
        ),
        TIPO_HISTORIA to mapOf(
            DIFICULTAD_FACIL to listOf(
                Question("¿Quién descubrió América según la versión histórica tradicional?", listOf("Cristóbal Colón", "Marco Polo", "Simón Bolívar"), 0),
                Question("¿En qué país se construyeron las pirámides de Giza?", listOf("Perú", "Egipto", "Grecia"), 1),
                Question("¿Cómo se llamó el primer presidente de Estados Unidos?", listOf("Abraham Lincoln", "George Washington", "Thomas Jefferson"), 1),
                Question("¿Qué civilización construyó Machu Picchu?", listOf("Inca", "Maya", "Azteca"), 0),
                Question("La independencia de El Salvador se conmemora el:", listOf("15 de septiembre", "5 de noviembre", "24 de diciembre"), 0)
            ),
            DIFICULTAD_DIFICIL to listOf(
                Question("¿En qué año inició la Primera Guerra Mundial?", listOf("1914", "1918", "1939"), 0),
                Question("¿Qué tratado puso fin oficialmente a la Primera Guerra Mundial?", listOf("Versalles", "Tordesillas", "París"), 0),
                Question("¿Quién fue el líder de la independencia de Haití?", listOf("Toussaint Louverture", "José Martí", "Benito Juárez"), 0),
                Question("¿En qué año cayó el muro de Berlín?", listOf("1987", "1989", "1991"), 1),
                Question("¿Qué imperio tuvo como capital a Constantinopla?", listOf("Bizantino", "Persa", "Mongol"), 0)
            )
        )
    )
}
