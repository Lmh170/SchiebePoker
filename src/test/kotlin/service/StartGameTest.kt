package service

import entity.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test class for the startGame method of [GameService].
 */
class StartGameTest {

    private lateinit var rootService: RootService
    private lateinit var gameService: GameService

    @BeforeEach
    fun setUp() {
        rootService = RootService()
        gameService = rootService.gameService
    }

    /**
     * Test that the game is initialized correctly with players, cards, and rounds.
     */
    @Test
    fun testStartGameInitializesCorrectly() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val playerList = listOf(player1, player2)
        val rounds = 3

        gameService.startGame(playerList, rounds)

        assertEquals(2, rootService.mainGame.playerList.size, "Player list should contain 2 players")
        assertEquals(3, rootService.mainGame.roundCount, "Round count should be 3")
        assertTrue(rootService.mainGame.currentPlayerIndex in 0..1, "Current player index should be 0 or 1")
    }

    /**
     * Test that each player receives the correct number of cards.
     */
    @Test
    fun testStartGameDistributesCardsToPlayers() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val playerList = listOf(player1, player2)

        gameService.startGame(playerList, 2)

        for (player in rootService.mainGame.playerList) {
            assertEquals(3, player.openCards.size, "Each player should have 3 open cards")
            assertEquals(2, player.hiddenCards.size, "Each player should have 2 hidden cards")
        }
    }

    /**
     * Test that middle cards are set correctly.
     */
    @Test
    fun testStartGameSetsMiddleCards() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val playerList = listOf(player1, player2)

        gameService.startGame(playerList, 2)

        assertEquals(3, rootService.mainGame.middleCards.size, "Middle should have 3 cards")
    }

    /**
     * Test that draw stack is populated correctly.
     */
    @Test
    fun testStartGameCreatesDrawStack() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val playerList = listOf(player1, player2)

        gameService.startGame(playerList, 2)

        assertTrue(rootService.mainGame.drawStack.isNotEmpty(), "Draw stack should not be empty")
    }

    /**
     * Test that total card count is 52.
     */
    @Test
    fun testStartGameHasAllCards() {
        val player1 = Player("Alice")
        val player2 = Player("Bob")
        val playerList = listOf(player1, player2)

        gameService.startGame(playerList, 2)

        val totalCards = rootService.mainGame.playerList.sumOf { it.openCards.size + it.hiddenCards.size } +
                rootService.mainGame.middleCards.size + rootService.mainGame.drawStack.size
        assertEquals(52, totalCards, "Total cards should be 52")
    }

    /**
     * Test with a single player.
     */
    @Test
    fun testStartGameWithSinglePlayer() {
        val player = Player("SinglePlayer")
        val playerList = listOf(player)
        val rounds = 2

        gameService.startGame(playerList, rounds)

        assertEquals(1, rootService.mainGame.playerList.size, "Player list should contain 1 player")
        assertEquals(2, rootService.mainGame.roundCount, "Round count should be 2")
        assertEquals(0, rootService.mainGame.currentPlayerIndex, "Current player index should be 0")
    }

    /**
     * Test with multiple players.
     */
    @Test
    fun testStartGameWithMultiplePlayers() {

        val players = listOf(Player("P1"), Player("P2"), Player("P3"), Player("P4"))
        val rounds = 5

        gameService.startGame(players, rounds)

        assertEquals(4, rootService.mainGame.playerList.size, "Player list should contain 4 players")
        assertEquals(5, rootService.mainGame.roundCount, "Round count should be 5")
    }
}

