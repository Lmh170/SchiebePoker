package service

import entity.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test class for the nextTurn method of [GameService].
 */
class NextTurnTest {

    private lateinit var rootService: RootService
    private lateinit var gameService: GameService

    @BeforeEach
    fun setUp() {
        rootService = RootService()
        gameService = rootService.gameService
    }

    /**
     * Test that the turn switches to the next player correctly.
     */
    @Test
    fun testNextTurnSwitchesPlayer() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val playerList = listOf(player1, player2)
        gameService.startGame(playerList, 2)

        val initialPlayerIndex = rootService.mainGame.currentPlayerIndex
        rootService.mainGame.playerList[initialPlayerIndex].actionCount = 0

        gameService.nextTurn()

        val expectedNextIndex = (initialPlayerIndex + 1) % 2
        assertEquals(expectedNextIndex, rootService.mainGame.currentPlayerIndex,
            "Current player index should be the next player")
    }

    /**
     * Test that round count decreases.
     */
    @Test
    fun testNextTurnDecreasesRoundCount() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val playerList = listOf(player1, player2)
        gameService.startGame(playerList, 1)

        val initialRoundCount = rootService.mainGame.roundCount
        rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount = 0

        gameService.nextTurn()
        rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount = 0
        gameService.nextTurn()

        assertEquals(initialRoundCount - 1, rootService.mainGame.roundCount,
            "Round count should decrease by 1")
    }

    /**
     * Test that action count is reset to 2.
     */
    @Test
    fun testNextTurnResetsActionCount() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val playerList = listOf(player1, player2)
        gameService.startGame(playerList, 2)

        rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount = 0

        gameService.nextTurn()

        assertEquals(2, rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount,
            "Action count should be reset to 2")
    }

    /**
     * Test that turn wraps around to the first player.
     */
    @Test
    fun testNextTurnWrapsAround() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val player3 = Player("Charlie")
        val playerList = listOf(player1, player2, player3)
        gameService.startGame(playerList, 2)

        rootService.mainGame.currentPlayerIndex = 2
        rootService.mainGame.playerList[2].actionCount = 0

        gameService.nextTurn()

        assertEquals(0, rootService.mainGame.currentPlayerIndex,
            "Current player index should wrap around to 0")
    }

    /**
     * Test that game finishes when round count reaches 0.
     */
    @Test
    fun testNextTurnTriggersFinishGame() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val playerList = listOf(player1, player2)
        gameService.startGame(playerList, 1)

        rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount = 0

        gameService.nextTurn()
        rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount = 0
        gameService.nextTurn()

        assertEquals(0, rootService.mainGame.roundCount, "Round count should be 0 after finishing")
    }

    /**
     * Test that a log entry is added.
     */
    @Test
    fun testNextTurnAddsLogEntry() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val playerList = listOf(player1, player2)
        gameService.startGame(playerList, 2)

        val initialLogSize = rootService.mainGame.logList.size
        rootService.mainGame.roundCount = 2 // Ensure game doesn't finish
        rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount = 0

        gameService.nextTurn()

        assertTrue(rootService.mainGame.logList.size > initialLogSize,
            "Log list should have a new entry")
    }
}

