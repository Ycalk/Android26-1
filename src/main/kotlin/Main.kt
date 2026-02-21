import parser.CsvParser
import resolver.Resolver
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.pie
import java.awt.Desktop
import java.io.File
import java.io.PrintStream

fun main(args: Array<String>) {
    System.setOut(PrintStream(System.out, true, "UTF-8"))

    val players = CsvParser.readPlayers()
    val resolver = Resolver(players)
    println("1. Игроков без агенства: ${resolver.getCountWithoutAgency()}")

    val bestScoreDefender = resolver.getBestScorerDefender()
    println("2. Автор наибольшего числа голов из числа защитников: " +
            "${bestScoreDefender.first} (${bestScoreDefender.second} голов)"
    )
    println("3. Позиция самого дорогого немецкого игрока: " +
            resolver.getTheExpensiveGermanPlayerPosition()
    )
    println("4. Команда с наибольшим числом красных карточек на игрок: " +
            resolver.getTheRudestTeam().name
    )

    val grouped = players.groupingBy { it.position }.eachCount()

    val positionTranslations = mapOf(
        "DEFENDER" to "Защитник",
        "MIDFIELD" to "Полузащитник",
        "FORWARD" to "Нападающий",
        "GOALKEEPER" to "Вратарь"
    )

    val chart = plot(mapOf(
        "Позиция" to grouped.keys.map { positionTranslations[it] ?: it }.toList(),
        "Количество" to grouped.values.toList()
    )) {
        pie {
            slice("Количество")
            fillColor("Позиция")
        }
        layout {
            title = "Доли игроков по позициям на поле"
        }
    }
    val savedFilePath = chart.save("chart.html")
    val htmlFile = File(savedFilePath)

    if (Desktop.isDesktopSupported())
    {
        Desktop.getDesktop().browse(htmlFile.toURI())
    }
    println("График сохранен в файл: ${htmlFile.absolutePath}")
}