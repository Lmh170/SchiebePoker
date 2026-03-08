package service

import entity.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test class for the swapAll method of [PlayerActionService].
 */
class SwapAllTest {

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
     * Test that all player cards are swapped with all middle cards.
     */
    @Test
    fun testSwapAllExchangesAllCards() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val playerCardsBefore = currentPlayer.openCards.toList()
        val middleCardsBefore = rootService.mainGame.middleCards.toList()

        playerActionService.swapAll()

        assertEquals(middleCardsBefore, currentPlayer.openCards,
            "Player's open cards should be the middle cards")
        assertEquals(playerCardsBefore, rootService.mainGame.middleCards,
            "Middle cards should be the player's cards")
    }

    /**
     * Test that action count decreases by 1.
     */
    @Test
    fun testSwapAllDecreasesActionCount() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val initialActionCount = currentPlayer.actionCount

        playerActionService.swapAll()

        assertEquals(initialActionCount - 1, currentPlayer.actionCount,
            "Action count should decrease by 1")
    }

    /**
     * Test that card counts remain the same.
     */
    @Test
    fun testSwapAllPreservesCardCounts() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]

        playerActionService.swapAll()

        assertEquals(3, currentPlayer.openCards.size,
            "Player should still have 3 open cards")
        assertEquals(3, rootService.mainGame.middleCards.size,
            "Middle should still have 3 cards")
    }

    /**
     * Test that swapping twice returns cards to original positions.
     */
    @Test
    fun testSwapAllTwiceReturnsToOriginal() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val playerCardsInitial = currentPlayer.openCards.toList()
        val middleCardsInitial = rootService.mainGame.middleCards.toList()


        playerActionService.swapAll()
        playerActionService.swapAll()

        assertEquals(playerCardsInitial, currentPlayer.openCards,
            "Player's cards should return to initial state after swapping twice")
        assertEquals(middleCardsInitial, rootService.mainGame.middleCards,
            "Middle cards should return to initial state after swapping twice")
    }

    /**
     * Test that order is preserved during swap.
     */
    @Test
    fun testSwapAllPreservesOrder() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val middleCard0 = rootService.mainGame.middleCards[0]
        val middleCard1 = rootService.mainGame.middleCards[1]
        val middleCard2 = rootService.mainGame.middleCards[2]


        playerActionService.swapAll()

        assertEquals(middleCard0, currentPlayer.openCards[0], "Order should be preserved at index 0")
        assertEquals(middleCard1, currentPlayer.openCards[1], "Order should be preserved at index 1")
        assertEquals(middleCard2, currentPlayer.openCards[2], "Order should be preserved at index 2")
    }
}

