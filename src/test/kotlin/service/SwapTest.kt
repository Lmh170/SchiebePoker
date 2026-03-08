package service

import entity.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test class for the swap method of [PlayerActionService].
 */
class SwapTest {

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
     * Test that a player card is correctly swapped with a middle card.
     */
    @Test
    fun testSwapExchangesCards() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val playerCardBefore = currentPlayer.openCards[0]
        val middleCardBefore = rootService.mainGame.middleCards[1]

        playerActionService.swap(0, 1)

        assertEquals(middleCardBefore, currentPlayer.openCards[0],
            "Player's card at index 0 should be the middle card")
        assertEquals(playerCardBefore, rootService.mainGame.middleCards[1],
            "Middle card at index 1 should be the player's card")
    }

    /**
     * Test that action count decreases by 1.
     */
    @Test
    fun testSwapDecreasesActionCount() {

        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val initialActionCount = currentPlayer.actionCount


        playerActionService.swap(0, 1)


        assertEquals(initialActionCount - 1, currentPlayer.actionCount,
            "Action count should decrease by 1")
    }

    /**
     * Test swap with different indices.
     */
    @Test
    fun testSwapWithDifferentIndices() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val playerCardBefore = currentPlayer.openCards[2]
        val middleCardBefore = rootService.mainGame.middleCards[0]

        playerActionService.swap(2, 0)

        assertEquals(middleCardBefore, currentPlayer.openCards[2],
            "Player's card at index 2 should be the middle card")
        assertEquals(playerCardBefore, rootService.mainGame.middleCards[0],
            "Middle card at index 0 should be the player's card")
    }

    /**
     * Test that other cards remain unchanged.
     */
    @Test
    fun testSwapOtherCardsUnchanged() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val playerCard1Before = currentPlayer.openCards[1]
        val playerCard2Before = currentPlayer.openCards[2]
        val middleCard0Before = rootService.mainGame.middleCards[0]
        val middleCard2Before = rootService.mainGame.middleCards[2]

        playerActionService.swap(0, 1)

        assertEquals(playerCard1Before, currentPlayer.openCards[1],
            "Player's card at index 1 should remain unchanged")
        assertEquals(playerCard2Before, currentPlayer.openCards[2],
            "Player's card at index 2 should remain unchanged")
        assertEquals(middleCard0Before, rootService.mainGame.middleCards[0],
            "Middle card at index 0 should remain unchanged")
        assertEquals(middleCard2Before, rootService.mainGame.middleCards[2],
            "Middle card at index 2 should remain unchanged")
    }

    /**
     * Test swap with first indices (0, 0).
     */
    @Test
    fun testSwapFirstIndices() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val playerCardBefore = currentPlayer.openCards[0]
        val middleCardBefore = rootService.mainGame.middleCards[0]

        playerActionService.swap(0, 0)

        assertEquals(middleCardBefore, currentPlayer.openCards[0])
        assertEquals(playerCardBefore, rootService.mainGame.middleCards[0])
    }
}

