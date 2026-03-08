package entity

import kotlin.test.Test

/**
 * Test class for the Card class, which represents a card in a Schiebe Poker game.
 */
class CardTest {

    /** Test to verify that a Card object is correctly initialized with the provided suit and value. */
    @Test
    fun correctlyInitialized() {
        val card = Card(CardSuit.HEARTS, CardValue.ACE)

        assert(card.suit == CardSuit.HEARTS)
        assert(card.value == CardValue.ACE)
    }
}