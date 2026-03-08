package entity
/**
 * Represents a Schiebe Poker game.
 * Manages the game state including players,
 * cards on the table, and the draw/discard stacks.
 *
 * @property currentPlayerIndex The index of the current player in the player list
 * @property logList A mutable list of log messages for game events
 * @property roundCount The current round number in the game
 * @property playerList A mutable list of all players
 * @property middleCards A mutable list of cards placed in the middle of the table
 * @property discardStack A mutable list of cards in the discard stack
 * @property drawStack A mutable list of cards in the draw stack
 */
class SchiebePokerGame {

    var currentPlayerIndex: Int = 0
    var logList: MutableList<String> = mutableListOf()
    var roundCount: Int = 0

    val playerList: MutableList<Player> = mutableListOf()

    var middleCards: MutableList<Card> = mutableListOf()

    var discardStack = mutableListOf<Card>()

    var drawStack = mutableListOf<Card>()



}