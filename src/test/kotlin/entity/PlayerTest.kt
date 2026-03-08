package entity

import kotlin.test.Test
import kotlin.test.assertEquals

/** Test class for the Player class, which represents a player in a Schiebe Poker game. */
class PlayerTest {
    @Test

    /** Test to verify that a Player object is correctly initialized
     * with the provided name and default values for secondTurn,
     * hiddenCards, and openCards.
     */
    fun correctlyInitialized() {
        val player = Player("Alice")

        assertEquals("Alice", player.name)
        assertEquals(2, player.actionCount)
        assertEquals(mutableListOf(),player.hiddenCards)
        assertEquals(mutableListOf(), player.openCards)
    }
}