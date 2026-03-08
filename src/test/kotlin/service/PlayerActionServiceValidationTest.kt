package service

import entity.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * Test class for validating preconditions and exception throwing in [PlayerActionService].
 */
class PlayerActionServiceValidationTest {

    private lateinit var rootService: RootService
    private lateinit var playerActionService: PlayerActionService
    private lateinit var gameService: GameService

    @BeforeEach
    fun setUp() {
        rootService = RootService()
        playerActionService = rootService.playerService
        gameService = rootService.gameService
        rootService.gameService.startGame(mutableListOf(Player("Alice"), Player("Bob")), 2)
    }

    /**
     * Test that swap throws IllegalStateException when player has no actions left.
     */
    @Test
    fun testSwapThrowsExceptionWhenNoActionsLeft() {
        gameService.startGame(listOf(Player("Alice"), Player("Bob")), 2)
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        currentPlayer.actionCount = 0

        val exception = assertThrows<IllegalStateException> {
            playerActionService.swap(0, 0)
        }
        assertEquals("Player has already performed 2 actions in this turn", exception.message)
    }

    /**
     * Test that swap throws IllegalArgumentException for invalid playerSelection.
     */
    @Test
    fun testSwapThrowsExceptionForInvalidPlayerSelection() {
        gameService.startGame(listOf(Player("Alice"), Player("Bob")), 2)

        val exception = assertThrows<IllegalArgumentException> {
            playerActionService.swap(5, 0)
        }
        assertEquals("playerSelection is not in the valid range (0-2)", exception.message)
    }

    /**
     * Test that swap throws IllegalArgumentException for negative playerSelection.
     */
    @Test
    fun testSwapThrowsExceptionForNegativePlayerSelection() {
        gameService.startGame(listOf(Player("Alice"), Player("Bob")), 2)

        val exception = assertThrows<IllegalArgumentException> {
            playerActionService.swap(-1, 0)
        }
        assertEquals("playerSelection is not in the valid range (0-2)", exception.message)
    }

    /**
     * Test that swap throws IllegalArgumentException for invalid middleSelection.
     */
    @Test
    fun testSwapThrowsExceptionForInvalidMiddleSelection() {
        gameService.startGame(listOf(Player("Alice"), Player("Bob")), 2)

        val exception = assertThrows<IllegalArgumentException> {
            playerActionService.swap(0, 10)
        }
        assertEquals("middleSelection is not in the valid range (0-2)", exception.message)
    }

    /**
     * Test that swap throws IllegalArgumentException for negative middleSelection.
     */
    @Test
    fun testSwapThrowsExceptionForNegativeMiddleSelection() {
        gameService.startGame(listOf(Player("Alice"), Player("Bob")), 2)

        val exception = assertThrows<IllegalArgumentException> {
            playerActionService.swap(0, -2)
        }
        assertEquals("middleSelection is not in the valid range (0-2)", exception.message)
    }


    /**
     * Test that swapAll throws IllegalStateException when player has no actions left.
     */
    @Test
    fun testSwapAllThrowsExceptionWhenNoActionsLeft() {
        gameService.startGame(listOf(Player("Alice"), Player("Bob")), 2)
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        currentPlayer.actionCount = 0

        val exception = assertThrows<IllegalStateException> {
            playerActionService.swapAll()
        }
        assertEquals("Player has already performed 2 actions in this turn", exception.message)
    }

    /**
     * Test that pushCards throws IllegalStateException when player has no actions left.
     */
    @Test
    fun testPushCardsThrowsExceptionWhenNoActionsLeft() {
        gameService.startGame(listOf(Player("Alice"), Player("Bob")), 2)
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        currentPlayer.actionCount = 0

        val exception = assertThrows<IllegalStateException> {
            playerActionService.pushCards(true)
        }
        assertEquals("Player has already performed his allowed two actions per turn", exception.message)
    }

    /**
     * Test that pushCards throws IllegalStateException when draw stack is empty.
     */
    @Test
    fun testPushCardsThrowsExceptionWhenDrawStackEmpty() {
        gameService.startGame(listOf(Player("Alice"), Player("Bob")), 2)
        rootService.mainGame.drawStack.clear()

        val exception = assertThrows<IllegalStateException> {
            playerActionService.pushCards(true)
        }
        assertEquals("Draw stack is empty", exception.message)
    }

    /**
     * Test that swapNone throws IllegalStateException when player has no actions left.
     */
    @Test
    fun testSwapNoneThrowsExceptionWhenNoActionsLeft() {
        gameService.startGame(listOf(Player("Alice"), Player("Bob")), 2)
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        currentPlayer.actionCount = 0

        val exception = assertThrows<IllegalStateException> {
            playerActionService.swapNone()
        }
        assertEquals("Player has no actions left", exception.message)
    }
}

