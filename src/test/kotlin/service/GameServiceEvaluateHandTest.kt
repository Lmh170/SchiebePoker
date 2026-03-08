package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.HandValue
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test class for the evaluateHand method of [GameService].
 */
class GameServiceEvaluateHandTest {

    private lateinit var rootService: RootService
    private lateinit var gameService: GameService

    @BeforeEach
    fun setUp() {
        rootService = RootService()
        gameService = rootService.gameService
    }

    /**
     * Test that evaluateHand correctly identifies a ROYAL_FLUSH.
     * Royal Flush: 10-J-Q-K-A of the same suit.
     */
    @Test
    fun testEvaluateHandRoyalFlush() {
        val cards = listOf(
            Card(CardSuit.HEARTS, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.KING),
            Card(CardSuit.HEARTS, CardValue.QUEEN),
            Card(CardSuit.HEARTS, CardValue.JACK),
            Card(CardSuit.HEARTS, CardValue.TEN)
        )

        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.ROYAL_FLUSH, result, "Should identify Royal Flush")
    }

    /**
     * Test that evaluateHand correctly identifies a STRAIGHT_FLUSH.
     * Straight Flush: Five consecutive cards of the same suit.
     */
    @Test
    fun testEvaluateHandStraightFlush() {
        val cards = listOf(
            Card(CardSuit.SPADES, CardValue.NINE),
            Card(CardSuit.SPADES, CardValue.EIGHT),
            Card(CardSuit.SPADES, CardValue.SEVEN),
            Card(CardSuit.SPADES, CardValue.SIX),
            Card(CardSuit.SPADES, CardValue.FIVE)
        )

        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.STRAIGHT_FLUSH, result, "Should identify Straight Flush")
    }

    /**
     * Test that evaluateHand correctly identifies THREE_OF_A_KIND.
     * Three of a Kind: Three cards with the same value.
     */
    @Test
    fun testEvaluateHandThreeOfAKind() {
        val cards = listOf(
            Card(CardSuit.HEARTS, CardValue.SEVEN),
            Card(CardSuit.SPADES, CardValue.SEVEN),
            Card(CardSuit.DIAMONDS, CardValue.SEVEN),
            Card(CardSuit.CLUBS, CardValue.KING),
            Card(CardSuit.HEARTS, CardValue.TWO)
        )

        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.THREE_OF_A_KIND, result, "Should identify Three of a Kind")
    }

    /**
     * Test that evaluateHand correctly identifies a FLUSH.
     * Flush: All five cards of the same suit but not consecutive.
     */
    @Test
    fun testEvaluateHandFlush() {
        val cards = listOf(
            Card(CardSuit.CLUBS, CardValue.ACE),
            Card(CardSuit.CLUBS, CardValue.JACK),
            Card(CardSuit.CLUBS, CardValue.FIVE),
            Card(CardSuit.CLUBS, CardValue.THREE),
            Card(CardSuit.CLUBS, CardValue.TWO)
        )

        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.FLUSH, result, "Should identify Flush")
    }

    /**
     * Test that evaluateHand correctly identifies a STRAIGHT.
     * Straight: Five consecutive cards of different suits.
     */
    @Test
    fun testEvaluateHandStraight() {

        val cards = listOf(
            Card(CardSuit.HEARTS, CardValue.NINE),
            Card(CardSuit.SPADES, CardValue.EIGHT),
            Card(CardSuit.DIAMONDS, CardValue.SEVEN),
            Card(CardSuit.CLUBS, CardValue.SIX),
            Card(CardSuit.HEARTS, CardValue.FIVE)
        )


        val result = gameService.evaluateHand(cards)


        assertEquals(HandValue.STRAIGHT, result, "Should identify Straight")
    }

    /**
     * Test that evaluateHand correctly identifies the special case STRAIGHT with A-2-3-4-5.
     * This is a special case where Ace is considered as low value.
     */
    @Test
    fun testEvaluateHandStraightAceLow() {
        val cards = listOf(
            Card(CardSuit.HEARTS, CardValue.ACE),
            Card(CardSuit.SPADES, CardValue.FIVE),
            Card(CardSuit.DIAMONDS, CardValue.FOUR),
            Card(CardSuit.CLUBS, CardValue.THREE),
            Card(CardSuit.HEARTS, CardValue.TWO)
        )


        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.STRAIGHT, result, "Should identify Straight with Ace as low card (A-2-3-4-5)")
    }

    /**
     * Test that evaluateHand correctly identifies HIGH_CARD.
     * High Card: Five unrelated cards with no matching combination.
     */
    @Test
    fun testEvaluateHandHighCard() {

        val cards = listOf(
            Card(CardSuit.HEARTS, CardValue.ACE),
            Card(CardSuit.SPADES, CardValue.KING),
            Card(CardSuit.DIAMONDS, CardValue.FIVE),
            Card(CardSuit.CLUBS, CardValue.THREE),
            Card(CardSuit.HEARTS, CardValue.TWO)
        )

        val result = gameService.evaluateHand(cards)


        assertEquals(HandValue.HIGH_CARD, result, "Should identify High Card")
    }

    /**
     * Test that evaluateHand throws IllegalArgumentException when given an empty card list.
     */
    @Test
    fun testEvaluateHandEmptyListThrowsException() {
        val cards = emptyList<Card>()

        val exception = assertThrows(IllegalArgumentException::class.java) {
            gameService.evaluateHand(cards)
        }
        assertEquals("Card list cannot be empty", exception.message, "Should throw exception for empty card list")
    }

    /**
     * Test that evaluateHand returns HIGH_CARD for less than 5 cards.
     */
    @Test
    fun testEvaluateHandSingleCard() {

        val cards = listOf(Card(CardSuit.HEARTS, CardValue.ACE))

        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.HIGH_CARD, result, "Should return HIGH_CARD for single card")
    }

    /**
     * Test that evaluateHand returns HIGH_CARD for two cards.
     */
    @Test
    fun testEvaluateHandTwoCards() {
        val cards = listOf(
            Card(CardSuit.HEARTS, CardValue.ACE),
            Card(CardSuit.SPADES, CardValue.KING)
        )

        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.HIGH_CARD, result, "Should return HIGH_CARD for two cards")
    }

    /**
     * Test that evaluateHand correctly prioritizes ROYAL_FLUSH over other hand types.
     */
    @Test
    fun testEvaluateHandRoyalFlushPriority() {

        val cards = listOf(
            Card(CardSuit.DIAMONDS, CardValue.ACE),
            Card(CardSuit.DIAMONDS, CardValue.KING),
            Card(CardSuit.DIAMONDS, CardValue.QUEEN),
            Card(CardSuit.DIAMONDS, CardValue.JACK),
            Card(CardSuit.DIAMONDS, CardValue.TEN)
        )

        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.ROYAL_FLUSH, result, "ROYAL_FLUSH should have priority over STRAIGHT_FLUSH and FLUSH")
    }

    /**
     * Test that evaluateHand correctly prioritizes STRAIGHT_FLUSH over FLUSH or STRAIGHT.
     */
    @Test
    fun testEvaluateHandStraightFlushPriority() {
        val cards = listOf(
            Card(CardSuit.CLUBS, CardValue.NINE),
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.CLUBS, CardValue.SEVEN),
            Card(CardSuit.CLUBS, CardValue.SIX),
            Card(CardSuit.CLUBS, CardValue.FIVE)
        )

        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.STRAIGHT_FLUSH, result, "STRAIGHT_FLUSH should have priority over FLUSH or STRAIGHT")
    }

    /**
     * Test that evaluateHand correctly prioritizes THREE_OF_A_KIND over lower hands.
     */
    @Test
    fun testEvaluateHandThreeOfAKindPriority() {
        val cards = listOf(
            Card(CardSuit.HEARTS, CardValue.JACK),
            Card(CardSuit.SPADES, CardValue.JACK),
            Card(CardSuit.CLUBS, CardValue.JACK),
            Card(CardSuit.DIAMONDS, CardValue.FIVE),
            Card(CardSuit.HEARTS, CardValue.TWO)
        )


        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.THREE_OF_A_KIND, result, "THREE_OF_A_KIND should have priority over PAIR or HIGH_CARD")
    }

    /**
     * Test that evaluateHand correctly prioritizes FLUSH over STRAIGHT.
     */
    @Test
    fun testEvaluateHandFlushPriority() {
        val cards = listOf(
            Card(CardSuit.HEARTS, CardValue.TWO),
            Card(CardSuit.HEARTS, CardValue.FIVE),
            Card(CardSuit.HEARTS, CardValue.NINE),
            Card(CardSuit.HEARTS, CardValue.JACK),
            Card(CardSuit.HEARTS, CardValue.KING)
        )

        val result = gameService.evaluateHand(cards)


        assertEquals(HandValue.FLUSH, result, "FLUSH should be identified correctly")
    }

    /**
     * Test that evaluateHand handles various card orders correctly (order should not matter).
     */
    @Test
    fun testEvaluateHandOrderIndependent() {

        val cardsOrdered = listOf(
            Card(CardSuit.HEARTS, CardValue.NINE),
            Card(CardSuit.HEARTS, CardValue.EIGHT),
            Card(CardSuit.HEARTS, CardValue.SEVEN),
            Card(CardSuit.HEARTS, CardValue.SIX),
            Card(CardSuit.HEARTS, CardValue.FIVE)
        )
        val cardsUnordered = listOf(
            Card(CardSuit.HEARTS, CardValue.FIVE),
            Card(CardSuit.HEARTS, CardValue.NINE),
            Card(CardSuit.HEARTS, CardValue.SEVEN),
            Card(CardSuit.HEARTS, CardValue.SIX),
            Card(CardSuit.HEARTS, CardValue.EIGHT)
        )

        val resultOrdered = gameService.evaluateHand(cardsOrdered)
        val resultUnordered = gameService.evaluateHand(cardsUnordered)

        assertEquals(resultOrdered, resultUnordered, "Hand evaluation should be independent of card order")
    }

    /**
     * Test that evaluateHand correctly identifies different straights.
     * Tests for middle range straights to ensure proper consecutive checking.
     */
    @Test
    fun testEvaluateHandVariousStraights() {
        val cards678910 = listOf(
            Card(CardSuit.HEARTS, CardValue.TEN),
            Card(CardSuit.SPADES, CardValue.NINE),
            Card(CardSuit.DIAMONDS, CardValue.EIGHT),
            Card(CardSuit.CLUBS, CardValue.SEVEN),
            Card(CardSuit.HEARTS, CardValue.SIX)
        )

        val result678910 = gameService.evaluateHand(cards678910)

        assertEquals(HandValue.STRAIGHT, result678910, "Should identify 6-7-8-9-10 Straight")

        val cards23456 = listOf(
            Card(CardSuit.HEARTS, CardValue.SIX),
            Card(CardSuit.SPADES, CardValue.FIVE),
            Card(CardSuit.DIAMONDS, CardValue.FOUR),
            Card(CardSuit.CLUBS, CardValue.THREE),
            Card(CardSuit.HEARTS, CardValue.TWO)
        )

        val result23456 = gameService.evaluateHand(cards23456)

        assertEquals(HandValue.STRAIGHT, result23456, "Should identify 2-3-4-5-6 Straight")
    }

    /**
     * Test that evaluateHand correctly identifies different flushes with various values.
     */
    @Test
    fun testEvaluateHandVariousFlushes() {

        val spadeFlush = listOf(
            Card(CardSuit.SPADES, CardValue.TWO),
            Card(CardSuit.SPADES, CardValue.SEVEN),
            Card(CardSuit.SPADES, CardValue.KING),
            Card(CardSuit.SPADES, CardValue.FOUR),
            Card(CardSuit.SPADES, CardValue.NINE)
        )

        val resultSpades = gameService.evaluateHand(spadeFlush)


        assertEquals(HandValue.FLUSH, resultSpades, "Should identify Spades Flush")

        val diamondFlush = listOf(
            Card(CardSuit.DIAMONDS, CardValue.THREE),
            Card(CardSuit.DIAMONDS, CardValue.NINE),
            Card(CardSuit.DIAMONDS, CardValue.ACE),
            Card(CardSuit.DIAMONDS, CardValue.FIVE),
            Card(CardSuit.DIAMONDS, CardValue.JACK)
        )

        val resultDiamonds = gameService.evaluateHand(diamondFlush)

        assertEquals(HandValue.FLUSH, resultDiamonds, "Should identify Diamonds Flush")
    }

    /**
     * Test that evaluateHand correctly identifies high card hands with various combinations.
     */
    @Test
    fun testEvaluateHandVariousHighCards() {
        val highCard1 = listOf(
            Card(CardSuit.HEARTS, CardValue.KING),
            Card(CardSuit.SPADES, CardValue.JACK),
            Card(CardSuit.DIAMONDS, CardValue.FIVE),
            Card(CardSuit.CLUBS, CardValue.THREE),
            Card(CardSuit.HEARTS, CardValue.TWO)
        )

        val result1 = gameService.evaluateHand(highCard1)

        assertEquals(HandValue.HIGH_CARD, result1, "Should identify High Card")

        val highCard2 = listOf(
            Card(CardSuit.HEARTS, CardValue.TWO),
            Card(CardSuit.SPADES, CardValue.FOUR),
            Card(CardSuit.DIAMONDS, CardValue.NINE),
            Card(CardSuit.CLUBS, CardValue.JACK),
            Card(CardSuit.HEARTS, CardValue.KING)
        )

        val result2 = gameService.evaluateHand(highCard2)

        assertEquals(HandValue.HIGH_CARD, result2, "Should identify High Card with low values")
    }

    /**
     * Test that evaluateHand correctly identifies a STRAIGHT_FLUSH with A-2-3-4-5.
     * This hand should be identified as STRAIGHT_FLUSH and not ROYAL_FLUSH.
     */
    @Test
    fun testEvaluateHandStraightFlushAceLowIsNotRoyalFlush() {
        val cards = listOf(
            Card(CardSuit.HEARTS, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.FIVE),
            Card(CardSuit.HEARTS, CardValue.FOUR),
            Card(CardSuit.HEARTS, CardValue.THREE),
            Card(CardSuit.HEARTS, CardValue.TWO)
        )

        val result = gameService.evaluateHand(cards)

        assertEquals(
            HandValue.STRAIGHT_FLUSH,
            result,
            "A-2-3-4-5 derselben Farbe muss STRAIGHT_FLUSH sein, nicht ROYAL_FLUSH"
        )
    }

    /**
     * Test that evaluateHand correctly identifies ONE_PAIR.
     * One Pair: Two cards with the same value.
     */
    @Test
    fun testEvaluateHandOnePair() {
        val cards = listOf(
            Card(CardSuit.HEARTS, CardValue.NINE),
            Card(CardSuit.SPADES, CardValue.NINE),
            Card(CardSuit.DIAMONDS, CardValue.THREE),
            Card(CardSuit.CLUBS, CardValue.KING),
            Card(CardSuit.HEARTS, CardValue.FIVE)
        )

        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.ONE_PAIR, result, "Should identify One Pair")
    }

    /**
     * Test that evaluateHand correctly identifies TWO_PAIR.
     * Two Pair: Two distinct pairs.
     */
    @Test
    fun testEvaluateHandTwoPair() {
        val cards = listOf(
            Card(CardSuit.HEARTS, CardValue.NINE),
            Card(CardSuit.SPADES, CardValue.NINE),
            Card(CardSuit.DIAMONDS, CardValue.KING),
            Card(CardSuit.CLUBS, CardValue.KING),
            Card(CardSuit.HEARTS, CardValue.FIVE)
        )

        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.TWO_PAIR, result, "Should identify Two Pair")
    }

    /**
     * Test that evaluateHand correctly identifies FOUR_OF_A_KIND.
     * Four of a Kind: Four cards with the same value.
     */
    @Test
    fun testEvaluateHandFourOfAKind() {
        val cards = listOf(
            Card(CardSuit.HEARTS, CardValue.JACK),
            Card(CardSuit.SPADES, CardValue.JACK),
            Card(CardSuit.DIAMONDS, CardValue.JACK),
            Card(CardSuit.CLUBS, CardValue.JACK),
            Card(CardSuit.HEARTS, CardValue.FIVE)
        )

        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.FOUR_OF_A_KIND, result, "Should identify Four of a Kind")
    }

    /**
     * Test that evaluateHand correctly identifies FULL_HOUSE.
     * Full House: Three of a kind + one pair.
     */
    @Test
    fun testEvaluateHandFullHouse() {
        val cards = listOf(
            Card(CardSuit.HEARTS, CardValue.JACK),
            Card(CardSuit.SPADES, CardValue.JACK),
            Card(CardSuit.DIAMONDS, CardValue.JACK),
            Card(CardSuit.CLUBS, CardValue.FIVE),
            Card(CardSuit.HEARTS, CardValue.FIVE)
        )

        val result = gameService.evaluateHand(cards)

        assertEquals(HandValue.FULL_HOUSE, result, "Should identify Full House")
    }
}
