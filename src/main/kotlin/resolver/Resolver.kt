package resolver

import model.Player
import model.Team

class Resolver(private val players: List<Player>) : IResolver {
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency == null }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val player = players.filter { it.position == "DEFENDER" }.maxBy { it.goals }
        return Pair(player.name, player.goals)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val player = players.filter { it.nationality == "Germany" }.maxBy { it.transferCost }
        return when (player.position) {
            "DEFENDER" -> "Защитник"
            "MIDFIELD" -> "Полузащитник"
            "FORWARD" -> "Нападающий"
            "GOALKEEPER" -> "Вратарь"
            else -> throw RuntimeException("Invalid position ${player.position}")
        }
    }

    override fun getTheRudestTeam(): Team {
        val teamEntry = players.groupBy { it.team }
            .maxBy { entry ->
                entry.value.map { player -> player.redCards }.average()
            }
        return Team(teamEntry.key, teamEntry.value)
    }
}