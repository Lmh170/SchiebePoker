package service

import entity.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * Test class for validating preconditions and exception throwing in [GameService].
 */
class GameServiceValidationTest {

    private lateinit var rootService: RootService
    private lateinit var gameService: GameService

    @BeforeEach
    fun setUp() {
        rootService = RootService()
        gameService = rootService.gameService
    }

    /**
     * Test that startGame throws IllegalArgumentException when rounds is 0.
     */
    @Test
    fun testStartGameThrowsExceptionForZeroRounds() {
        val playerList = mutableListOf(Player("Alice"))

        val exception = assertThrows<IllegalArgumentException> {
            gameService.startGame(playerList, 0)
        }
        assertEquals("Number of rounds must be positive", exception.message)
    }

    /**
     * Test that startGame throws IllegalArgumentException when rounds is negative.
     */
    @Test
    fun testStartGameThrowsExceptionForNegativeRounds() {
        val playerList = mutableListOf(Player("Alice"))

        val exception = assertThrows<IllegalArgumentException> {
            gameService.startGame(playerList, -5)
        }
        assertEquals("Number of rounds must be positive", exception.message)
    }

    /**
     * Test that finishGame throws IllegalStateException when game is not running.
     */
    @Test
    fun testFinishGameThrowsExceptionWhenNotRunning() {
        val exception = assertThrows<IllegalStateException> {
            gameService.finishGame()
        }
        assertEquals("Game is not running", exception.message)
    }

    /**
     * Test that findWinners throws IllegalStateException when game is not finished.
     */
    @Test
    fun testFindWinnersThrowsExceptionWhenNotFinished() {
        val exception = assertThrows<IllegalStateException> {
            gameService.findWinners()
        }
        assertEquals("Game is not finished", exception.message)
    }

    /**
     * Test that reshuffleDrawStack throws IllegalStateException when game is not running.
     */
    @Test
    fun testReshuffleDrawStackThrowsExceptionWhenNotRunning() {
        val exception = assertThrows<IllegalStateException> {
            gameService.reshuffleDrawStack()
        }
        assertEquals("Game is not running", exception.message)
    }

    /**
     * Test that reshuffleDrawStack throws IllegalStateException when drawstack is not empty.
     */
    @Test
    fun testReshuffleDrawStackThrowsExceptionWhenDrawStackNotEmpty() {
        gameService.startGame(mutableListOf(Player("Alice"), Player("Bob")), 2)

        val exception = assertThrows<IllegalStateException> {
            gameService.reshuffleDrawStack()
        }
        assertEquals("Drawstack is not empty", exception.message)
    }

    /**
     * Test that reshuffleDrawStack throws IllegalStateException when discardstack is empty.
     */
    @Test
    fun testReshuffleDrawStackThrowsExceptionWhenDiscardStackEmpty() {
        gameService.startGame(mutableListOf(Player("Alice"), Player("Bob")), 2)
        rootService.mainGame.drawStack.clear()

        val exception = assertThrows<IllegalStateException> {
            gameService.reshuffleDrawStack()
        }
        assertEquals("Discardstack is empty", exception.message)
    }

    /**
     * Test that nextTurn throws IllegalStateException when game is not running.
     */
    @Test
    fun testNextTurnThrowsExceptionWhenNotRunning() {
        val exception = assertThrows<IllegalStateException> {
            gameService.nextTurn()
        }
        assertEquals("Game is not currently running", exception.message)
    }

    /**
     * Test that nextTurn throws IllegalStateException when game has ended.
     */
    @Test
    fun testNextTurnThrowsExceptionWhenGameEnded() {
        gameService.startGame(mutableListOf(Player("Alice"), Player("Bob")), 1)
        rootService.mainGame.roundCount = 0

        val exception = assertThrows<IllegalStateException> {
            gameService.nextTurn()
        }
        assertEquals("Game has already ended", exception.message)
    }

    /**
     * Test that nextTurn throws IllegalStateException when player has not completed actions.
     */
    @Test
    fun testNextTurnThrowsExceptionWhenActionsNotCompleted() {
        gameService.startGame(mutableListOf(Player("Alice"), Player("Bob")), 2)
        val exception = assertThrows<IllegalStateException> {
            gameService.nextTurn()
        }
        assertEquals("Current player has not completed both actions", exception.message)
    }
}

