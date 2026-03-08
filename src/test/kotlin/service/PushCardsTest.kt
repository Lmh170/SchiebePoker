package service

import entity.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test class for the pushCards method of [PlayerActionService].
 */
class PushCardsTest {

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
     * Test that cards are pushed left correctly.
     */
    @Test
    fun testPushCardsRightShiftsCards() {
        initializeGame()
        val drawStackSizeBefore = rootService.mainGame.drawStack.size
        val middleCard1Before = rootService.mainGame.middleCards[1]
        val middleCard2Before = rootService.mainGame.middleCards[2]

        playerActionService.pushCards(false)

        assertEquals(drawStackSizeBefore - 1, rootService.mainGame.drawStack.size,
            "Draw stack should have one less card")
        assertEquals(3, rootService.mainGame.middleCards.size,
            "Middle should still have 3 cards")
        assertEquals(middleCard1Before, rootService.mainGame.middleCards[0],
            "Middle card at index 1 should now be at index 0")
        assertEquals(middleCard2Before, rootService.mainGame.middleCards[1],
            "Middle card at index 2 should now be at index 1")
    }

    /**
     * Test that cards are pushed right correctly.
     */
    @Test
    fun testPushCardsLeftShiftsCards() {
        initializeGame()
        val drawStackSizeBefore = rootService.mainGame.drawStack.size
        val middleCard0Before = rootService.mainGame.middleCards[0]
        val middleCard1Before = rootService.mainGame.middleCards[1]

        playerActionService.pushCards(true)

        assertEquals(drawStackSizeBefore - 1, rootService.mainGame.drawStack.size,
            "Draw stack should have one less card")
        assertEquals(3, rootService.mainGame.middleCards.size,
            "Middle should still have 3 cards")
        assertEquals(middleCard0Before, rootService.mainGame.middleCards[1],
            "Middle card at index 0 should now be at index 1")
        assertEquals(middleCard1Before, rootService.mainGame.middleCards[2],
            "Middle card at index 1 should now be at index 2")
    }

    /**
     * Test that action count decreases by 1.
     */
    @Test
    fun testPushCardsDecreasesActionCount() {
        initializeGame()
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        val initialActionCount = currentPlayer.actionCount

        playerActionService.pushCards(true)

        assertEquals(initialActionCount - 1, currentPlayer.actionCount,
            "Action count should decrease by 1")
    }

    /**
     * Test that new card from draw stack is added to middle.
     */
    @Test
    fun testPushCardsAddsNewCard() {
        initializeGame()
        val topDrawCard = rootService.mainGame.drawStack[0]

        playerActionService.pushCards(true)

        assertTrue(rootService.mainGame.middleCards.contains(topDrawCard),
            "Middle cards should contain the card from the top of draw stack")
    }

    /**
     * Test alternating left and right pushes.
     */
    @Test
    fun testPushCardsAlternatingDirections() {
        initializeGame()
        val initialDrawStackSize = rootService.mainGame.drawStack.size

        playerActionService.pushCards(true)
        playerActionService.pushCards(false)

        assertEquals(initialDrawStackSize - 2, rootService.mainGame.drawStack.size,
            "Draw stack should have two less cards")
        assertEquals(3, rootService.mainGame.middleCards.size,
            "Middle should still have 3 cards")
    }

    /**
     * Test that pushing left adds new card at the beginning.
     */
    @Test
    fun testPushCardsLeftAddsAtBeginning() {
        initializeGame()
        val topDrawCard = rootService.mainGame.drawStack[0]

        playerActionService.pushCards(true)

        assertEquals(topDrawCard, rootService.mainGame.middleCards[0],
            "New card should be at index 0 after pushing left")
    }

    /**
     * Test that pushing right adds new card at the end.
     */
    @Test
    fun testPushCardsRightAddsAtEnd() {
        initializeGame()
        val topDrawCard = rootService.mainGame.drawStack[0]

        playerActionService.pushCards(false)

        assertEquals(topDrawCard, rootService.mainGame.middleCards[2],
            "New card should be at index 2 after pushing right")
    }
}

