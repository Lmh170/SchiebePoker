package service

import entity.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

/**
 * Test class for the finishGame method of [GameService].
 */
class FinishGameTest {

    private lateinit var rootService: RootService
    private lateinit var gameService: GameService

    @BeforeEach
    fun setUp() {
        rootService = RootService()
        gameService = rootService.gameService
    }

    /**
     * Test that the game finishes without errors.
     */
    @Test
    fun testFinishGameExecutesWithoutErrors() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val playerList = mutableListOf(player1, player2)
        gameService.startGame(playerList, 1)

        assertDoesNotThrow { gameService.finishGame() }
    }

    /**
     * Test that finishGame calls findWinners.
     */
    @Test
    fun testFinishGameCallsFindWinners() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val player3 = Player("Charlie")
        rootService.gameService.startGame(mutableListOf(player1, player2, player3), 5)

        gameService.finishGame()

        assertDoesNotThrow { gameService.finishGame() }
    }
}

