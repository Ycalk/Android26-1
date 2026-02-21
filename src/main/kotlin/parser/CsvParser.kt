package parser

import model.Player

object CsvParser {
    fun readPlayers(): List<Player> {
        val inputStream = CsvParser::class.java.getResourceAsStream("/fakePlayers.csv")
            ?: throw RuntimeException("File not found")
        return inputStream.bufferedReader().readLines().drop(1).map { line ->
            val fields = line.split(";")
            if (fields.size != 12)
            {
                throw RuntimeException("Invalid number of fields in line: $line")
            }
            Player(
                name = fields[0],
                team = fields[1],
                city = fields[2],
                position = fields[3],
                nationality = fields[4],
                agency = fields[5].ifEmpty { null },
                transferCost = fields[6].toInt(),
                participations = fields[7].toInt(),
                goals = fields[8].toInt(),
                assists = fields[9].toInt(),
                yellowCards = fields[10].toInt(),
                redCards = fields[11].toInt()
            )
        }
    }
}