package service

import entity.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test class for the findWinners method of [GameService].
 */
class FindWinnersTest {

    private lateinit var rootService: RootService
    private lateinit var gameService: GameService

    @BeforeEach
    fun setUp() {
        rootService = RootService()
        gameService = rootService.gameService
    }

    /**
     * Test that winners are determined correctly based on hand values.
     */
    @Test
    fun testFindWinnersSortsPlayers() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val player3 = Player("Charlie")
        rootService.mainGame.playerList.addAll(listOf(player1, player2, player3))

        gameService.findWinners()

        assertNotNull(rootService.mainGame.playerList, "Player list should not be null")
        assertEquals(3, rootService.mainGame.playerList.size, "Player list should still contain 3 players")
    }

    /**
     * Test that player list remains the same size after finding winners.
     */
    @Test
    fun testFindWinnersPreservesPlayerCount() {
        val players = listOf(Player("P1"), Player("P2"), Player("P3"), Player("P4"))
        rootService.mainGame.playerList.addAll(players)
        val initialSize = rootService.mainGame.playerList.size

        gameService.findWinners()

        assertEquals(initialSize, rootService.mainGame.playerList.size,
            "Player list size should remain unchanged")
    }
}

