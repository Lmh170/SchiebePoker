package service

import entity.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test class for the swapNone method of [PlayerActionService].
 */
class SwapNoneTest {

    private lateinit var rootService: RootService
    private lateinit var playerActionService: PlayerActionService
    private lateinit var gameService: GameService

    @BeforeEach
    fun setUp() {
        rootService = RootService()
        playerActionService = rootService.playerService
        gameService = rootService.gameService
    }

    /**
     * Helper method to initialize a game with default settings.
     */
    private fun initializeGame() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val playerList = listOf(player1, player2)
        gameService.startGame(playerList, 2)
    }

    /**
     * Test that action count decreases but no cards are changed.
     */
    @Test
    fun testSwapNoneDecreasesActionCount() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val initialActionCount = currentPlayer.actionCount

        playerActionService.swapNone()

        assertEquals(initialActionCount - 1, currentPlayer.actionCount,
            "Action count should decrease by 1")
    }

    /**
     * Test that player cards remain unchanged.
     */
    @Test
    fun testSwapNonePlayerCardsUnchanged() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val playerCardsBefore = currentPlayer.openCards.toList()

        playerActionService.swapNone()

        assertEquals(playerCardsBefore, currentPlayer.openCards,
            "Player's cards should remain unchanged")
    }

    /**
     * Test that middle cards remain unchanged.
     */
    @Test
    fun testSwapNoneMiddleCardsUnchanged() {
        initializeGame()
        val middleCardsBefore = rootService.mainGame.middleCards.toList()

        playerActionService.swapNone()

        assertEquals(middleCardsBefore, rootService.mainGame.middleCards,
            "Middle cards should remain unchanged")
    }

    /**
     * Test swapNone called multiple times.
     */
    @Test
    fun testSwapNoneMultipleTimes() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val initialActionCount = currentPlayer.actionCount

        playerActionService.swapNone()
        playerActionService.swapNone()

        assertEquals(initialActionCount - 2, currentPlayer.actionCount,
            "Action count should decrease by 2")
    }

    /**
     * Test that all game state remains the same except action count.
     */
    @Test
    fun testSwapNoneOnlyChangesActionCount() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val playerCardsBefore = currentPlayer.openCards.toList()
        val middleCardsBefore = rootService.mainGame.middleCards.toList()
        val drawStackSizeBefore = rootService.mainGame.drawStack.size

        playerActionService.swapNone()

        assertEquals(playerCardsBefore, currentPlayer.openCards, "Player cards should be unchanged")
        assertEquals(middleCardsBefore, rootService.mainGame.middleCards, "Middle cards should be unchanged")
        assertEquals(drawStackSizeBefore, rootService.mainGame.drawStack.size, "Draw stack size should be unchanged")
    }

    /**
     * Test that swapNone can deplete all actions.
     */
    @Test
    fun testSwapNoneDepletesAllActions() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]

        playerActionService.swapNone()
        playerActionService.swapNone()

        assertEquals(0, currentPlayer.actionCount,
            "Action count should be 0 after using swapNone twice")
    }
}

