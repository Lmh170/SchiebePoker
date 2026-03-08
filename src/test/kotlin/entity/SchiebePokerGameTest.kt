package entity

import kotlin.test.Test
import kotlin.test.assertEquals

/** Test class for the SchiebePokerGame class, which represents a game of Schiebe Poker. */
class SchiebePokerGameTest {

    /** Test to verify that a SchiebePokerGame object is correctly initialized with default values for maxVal,
     * playerList, middleCards, discardStack, and drawStack.
     */
    @Test
    fun correctlyInitialized() {
        val game = SchiebePokerGame()

        assertEquals(0, game.currentPlayerIndex)
        assertEquals(mutableListOf<String>(), game.logList)
        assertEquals(0, game.roundCount)
        assertEquals(mutableListOf(), game.playerList)
        assertEquals(mutableListOf(), game.middleCards)
        assertEquals(mutableListOf(), game.discardStack)
        assertEquals(mutableListOf(), game.drawStack)
    }
}