package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test class for the reshuffleDrawStack method of [GameService].
 */
class ReshuffleDrawStackTest {

    private lateinit var rootService: RootService
    private lateinit var gameService: GameService

    @BeforeEach
    fun setUp() {
        rootService = RootService()
        gameService = rootService.gameService
        rootService.gameService.startGame(mutableListOf(Player("test1"), Player("test2")), 3)
    }

    /**
     * Test that discard stack is moved to draw stack and shuffled.
     */
    @Test
    fun testReshuffleDrawStackMovesCards() {
        val card1 = Card(CardSuit.HEARTS, CardValue.ACE)
        val card2 = Card(CardSuit.SPADES, CardValue.KING)
        val card3 = Card(CardSuit.DIAMONDS, CardValue.QUEEN)

        rootService.mainGame.discardStack.addAll(listOf(card1, card2, card3))
        rootService.mainGame.drawStack.clear()

        gameService.reshuffleDrawStack()

        assertEquals(3, rootService.mainGame.drawStack.size, "Draw stack should have 3 cards")
        assertEquals(0, rootService.mainGame.discardStack.size, "Discard stack should be empty")
    }

    /**
     * Test that all cards from discard stack are in draw stack.
     */
    @Test
    fun testReshuffleDrawStackContainsAllCards() {
        val card1 = Card(CardSuit.HEARTS, CardValue.ACE)
        val card2 = Card(CardSuit.SPADES, CardValue.KING)
        val card3 = Card(CardSuit.DIAMONDS, CardValue.QUEEN)

        rootService.mainGame.discardStack.addAll(listOf(card1, card2, card3))
        rootService.mainGame.drawStack.clear()

        gameService.reshuffleDrawStack()

        assertTrue(rootService.mainGame.drawStack.contains(card1), "Draw stack should contain card1")
        assertTrue(rootService.mainGame.drawStack.contains(card2), "Draw stack should contain card2")
        assertTrue(rootService.mainGame.drawStack.contains(card3), "Draw stack should contain card3")
    }

    /**
     * Test that discard stack is cleared after reshuffling.
     */
    @Test
    fun testReshuffleDrawStackClearsDiscardStack() {
        val cards = listOf(
            Card(CardSuit.CLUBS, CardValue.TWO),
            Card(CardSuit.HEARTS, CardValue.THREE)
        )
        rootService.mainGame.discardStack.addAll(cards)
        rootService.mainGame.drawStack.clear()

        gameService.reshuffleDrawStack()


        assertTrue(rootService.mainGame.discardStack.isEmpty(), "Discard stack should be empty after reshuffle")
    }
}

