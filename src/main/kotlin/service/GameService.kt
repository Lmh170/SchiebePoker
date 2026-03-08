package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.HandValue
import entity.Player
import kotlin.random.Random

/**
 * Service class responsible for managing the game logic of a Schiebe Poker game.
 *
 * @property rootService The RootService instance that provides access to the main game state and other services.
 */
class GameService(private val rootService: RootService): AbstractRefreshingService() {

    /**
     * Allgemeine Beschreibung: Die Methode startGame(playerList : List<player>, rounds : int)
     * ermöglicht es das Spiel zu Starten
     * und die Spieler sowie die Runden festzulegen.
     *
     *
     * Vorbedingungen:
     * - Das Spiel muss gestartet sein und sich in einem laufenden Zustand befinden.
     * - Die Spieler müssen sich konfigurieren.
     * - Die Anzahl der Runden muss festgelegt werden.
     *
     *
     * Nachbedingungen:
     * - Die Karten werden geschuffelt.
     * - Jeder Spieler hat drei offene Karten im Hand.
     * - Zwei Karten sind auf dem Spielfeld verdeckt.
     * - Drei offne Karten in der Mitte des Spielfelds geöffnet.
     * - Nachziehstabel und Ablagestabel werden gestellt.
     *
     *
     * Parameter:
     * @param playerList eine Liste, die alle Spieler enthält.
     * @param rounds Die Anzahl der Runden, die ein Spieler festgelegt schon hatte.
     *
     *
     * Ergebnis:
     * @return none Diese Methode hat keinen Rückgabewert (Unit).
     *
     * Fehlerfälle:
     * @throws IllegalStateException: Wird geworfen, wenn das Spiel nicht läuft.
     * @throws IllegalArgumentException: falsche Anzahl für die Runden festgelegt.

     **/
    fun startGame(playerList: List<Player>, rounds: Int) {
        // Validate preconditions
        require(playerList.isNotEmpty()) { "Player list cannot be empty" }
        require(rounds > 0) { "Number of rounds must be positive" }

        val randomPlayer = Random.nextInt(playerList.size)
        rootService.mainGame.playerList.clear()
        rootService.mainGame.playerList.addAll(playerList)
        val player = rootService.mainGame.playerList.removeAt(randomPlayer)
        rootService.mainGame.playerList.add(0, player)
        rootService.mainGame.logList.clear()

        // Reset game state that may still contain data from a previous run
        rootService.mainGame.middleCards.clear()
        rootService.mainGame.drawStack.clear()
        rootService.mainGame.discardStack.clear()
        rootService.mainGame.currentPlayerIndex = 0

        rootService.mainGame.roundCount = rounds
        val cards = createCardDeck()

        for (player in rootService.mainGame.playerList) {
            // Reusing Player instances across restarts is valid; clear old hand first.
            player.openCards.clear()
            player.hiddenCards.clear()
            player.playerHandValue = HandValue.HIGH_CARD
            player.actionCount = 2

            repeat(3) {
                player.openCards.add(cards.removeLast())
            }
            repeat(2) {
                player.hiddenCards.add(cards.removeLast())
            }
        }

        rootService.mainGame.middleCards.add(cards.removeLast())
        rootService.mainGame.middleCards.add(cards.removeLast())
        rootService.mainGame.middleCards.add(cards.removeLast())

        rootService.mainGame.drawStack.addAll(cards)

        onAllRefreshables { refreshAfterStartGame() }
        println("Game started with ${rootService.mainGame.playerList.size} players and " +
                "${rootService.mainGame.roundCount} rounds.")
    }

    /*
     * Creates a standard deck of 52 playing cards,
     * shuffles it, and returns the shuffled deck as a mutable list.
     */
    private fun createCardDeck(): MutableList<Card> {
        val cardDeck = mutableListOf<Card>()
        for (suit in CardSuit.entries) {
            for (rank in CardValue.entries) {
                cardDeck.add(Card(suit, rank))
            }
        }
        cardDeck.shuffle()
        return cardDeck
    }

    /**
     *
     * Allgemeine Beschreibung:
     *  - Diese Methode dient dazu, die mittels findWinners() ermittelten Gewinner auszugeben und
     *  die Spielrunde zu beenden.
     *  Dazu wird findWinners() aufgerufen und die Gewinner werden angezeigt.
     * Vorbedingungen:
     *  - Das Spiel muss gestartet sein und sich im abgeschlossenen Zustand befinden.
     *  - Es darf kein Spieler mehr einen Zug ausführen können.
     *  Die Interaktion mit den Spielkarten muss über findWinners() eingestellt worden sein.
     * Nachbedingungen:
     *  - Es muss der Gewinner auf der UI zu sehen sein.
     *  - Es müssen die Knöpfe "Restart" und "Exit" zu sehen und bedienbar sein.
     * Gültige Werte for the parameter:
     *  None
     * Rückgabewert:
     *  @return None
     * Fehlerfälle:
     *  @throws IllegalStateException: Wird geworfen, wenn das Spiel nicht läuft.
     *
     */
    fun finishGame(){
        check(rootService.mainGame.playerList.isNotEmpty()) { "Game is not running" }

        for (player in rootService.mainGame.playerList) {
            player.playerHandValue = evaluatePlayerHand(player)
        }

        findWinners()
        onAllRefreshables { refreshAfterFinishGame(rootService.mainGame.playerList) }
    }

    /**
     * * General Description: Extract Player(s) with highest scores calculated from HandValue
     *
     * **Preconditions:**
     * - The game needs to be finished correctly.
     *
     * **Postconditions:**
     * - The best Player(s) were calculated.
     *
     * @throws IllegalStateException If the game is not finished
     *
     * @return Returns a list which contains the best player.
     * If multiple players habe the best score, the list contains all players with the best score.
     */
    fun findWinners() {
        // Validate preconditions
        check(rootService.mainGame.playerList.isNotEmpty()) { "Game is not finished" }

        rootService.mainGame.playerList.sortBy { it.playerHandValue.ordinal }
    }

    /**
     * General Description:
     * Refills the drawstack with the cards from the discardstack.
     *
     * Precondition:
     * Game needs to be started and be in a running state.
     * Drawstack needs to have < 1 cards.
     * Discardstack can not be empty.
     *
     * Postcondition:
     * Drawstack gets all cards from the discards stack.
     * Drawstack gets shuffled.
     * Discardstack is empty.
     *
     * Paramter:
     * @param none This method does not have any parameter.
     *
     * Valid values range for parameters:
     * None
     *
     * Result:
     * @return Unit This Method does not have a return value (Unit).
     *
     * Exceptions:
     * @throws IllegalStateException: It gets thrown when the game is not running.
     * @throws IllegalStateException: Drawstack not empty
     * @throws IllegalStateException: Discardstack is empty.
     */
    fun reshuffleDrawStack(){
        // Validate preconditions
        check(rootService.mainGame.playerList.isNotEmpty()) { "Game is not running" }


        rootService.mainGame.drawStack = rootService.mainGame.discardStack
        rootService.mainGame.discardStack = mutableListOf()
        rootService.mainGame.drawStack.shuffle()
    }

    /**
     * General Description:
     * The method nextTurn() ends the current player's turn and passes control to the next player.
     * It checks whether all players have already taken their turn. If so, the round counter is increased.
     * Additionally, it verifies whether the maximum number of rounds has been reached in order to
     * end the game if necessary.
     *
     * Preconditions:
     * - The game must have been started and must be in a running state.
     * - The number of rounds must be defined.
     * - The current player must have completed both of their actions.
     *
     *
     * Postconditions:
     * - currentPlayerIdx points to the next player in the turn order.
     * - If the last player has finished their turn, roundCount is increased by 1.
     * - If the maximum number of rounds is reached, finishGame() is triggered.
     *
     *
     * Parameters:
     * none
     *
     *
     * Result:
     * @return none This method has no return value (Unit).
     *
     * Error cases:
     * @throws IllegalStateException: Thrown if the game is not currently running.
     * @throws IllegalArgumentException: Thrown if a turn change is attempted even though the game has already ended.
     *
     **/
    fun nextTurn() {
        // Validate preconditions
        check(rootService.mainGame.playerList.isNotEmpty()) { "Game is not currently running" }
        check(rootService.mainGame.roundCount > 0) { "Game has already ended" }
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        check(currentPlayer.actionCount == 0) { "Current player has not completed both actions" }

        rootService.mainGame.currentPlayerIndex =
            (rootService.mainGame.currentPlayerIndex + 1) % rootService.mainGame.playerList.size

        if (rootService.mainGame.currentPlayerIndex == 0) rootService.mainGame.roundCount -= 1

        rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount = 2
        if (rootService.mainGame.roundCount == 0) {
            finishGame()
            return
        }

        rootService.mainGame.logList.add("\n Player ${rootService.mainGame
            .playerList[rootService.mainGame.currentPlayerIndex].name} " +
                "is now playing. \n")

        onAllRefreshables { refreshAfterNextTurn() }
    }

    /**
     * Evaluates a list of cards and returns the best hand value according to poker rules.
     *
     * @param cards The list of cards to evaluate (should contain 5 cards for Schiebe Poker)
     * @return The [HandValue] representing the best hand that can be made with these cards
     */
    fun evaluateHand(cards: List<Card>): HandValue {
        require(cards.isNotEmpty()) { "Card list cannot be empty" }

        if (cards.size < 5) {
            return HandValue.HIGH_CARD
        }

        val sortedCards = cards.sortedByDescending { it.value.ordinal }

        // Check for flush (all same suit)
        val isFlush = cards.all { it.suit == cards.first().suit }

        // Check for straight (consecutive values)
        val isStraight = checkStraight(sortedCards)

        // Count card values
        val valueCounts = cards.groupBy { it.value }.mapValues { it.value.size }
        val counts = valueCounts.values.sortedDescending()

        // Royal Flush: 10-J-Q-K-A of same suit
        val isRoyalFlush = isFlush &&
            sortedCards.take(5).map { it.value }.toSet() == setOf(
                CardValue.ACE,
                CardValue.KING,
                CardValue.QUEEN,
                CardValue.JACK,
                CardValue.TEN
            )

        // Evaluate hand value
        return when {
            // Royal Flush: 10-J-Q-K-A of same suit
            isRoyalFlush -> HandValue.ROYAL_FLUSH

            // Straight Flush: Straight and Flush
            isFlush && isStraight -> HandValue.STRAIGHT_FLUSH

            // Four of a Kind: Four cards of same value
            counts.first() == 4 -> HandValue.FOUR_OF_A_KIND

            // Full House: Three of a kind + one pair
            counts.size == 2 && counts[0] == 3 && counts[1] == 2 -> HandValue.FULL_HOUSE

            // Flush: All same suit
            isFlush -> HandValue.FLUSH

            // Straight: Consecutive values
            isStraight -> HandValue.STRAIGHT

            // Three of a Kind: Three cards of same value
            counts.first() == 3 -> HandValue.THREE_OF_A_KIND

            // Two Pair: Two distinct pairs
            counts.size >= 2 && counts[0] == 2 && counts[1] == 2 -> HandValue.TWO_PAIR

            // One Pair: Two cards of same value
            counts.first() == 2 -> HandValue.ONE_PAIR

            // High Card: No matching combination
            else -> HandValue.HIGH_CARD
        }
    }

    /**
     * Checks if the given sorted cards form a straight (consecutive values).
     *
     * @param sortedCards The cards sorted by value in descending order
     * @return true if the cards form a straight, false otherwise
     */
    private fun checkStraight(sortedCards: List<Card>): Boolean {
        if (sortedCards.size < 5) return false

        // Check for standard straight (5 consecutive cards)
        val isStandardStraight = (0 until 4).all { i ->
            sortedCards[i].value.ordinal - sortedCards[i + 1].value.ordinal == 1
        }

        if (isStandardStraight) return true

        // Check for special case: A-2-3-4-5 (Ace can be low)
        if (sortedCards.size == 5 &&
            sortedCards[0].value == CardValue.ACE &&
            sortedCards[1].value == CardValue.FIVE &&
            sortedCards[2].value == CardValue.FOUR &&
            sortedCards[3].value == CardValue.THREE &&
            sortedCards[4].value == CardValue.TWO) {
            return true
        }

        return false
    }

    /**
     * Evaluates and updates the hand value for a player based on their open and hidden cards.
     *
     * @param player The player whose hand value should be calculated
     * @return The calculated [HandValue]
     */
    fun evaluatePlayerHand(player: Player): HandValue {
        val allCards = player.openCards + player.hiddenCards
        require(allCards.size == 5) {
            "Player ${player.name} has invalid hand size: ${allCards.size} " +
                "(open=${player.openCards.size}, hidden=${player.hiddenCards.size}). Expected 5 cards."
        }
        return evaluateHand(allCards)
    }
}