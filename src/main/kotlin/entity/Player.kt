package entity
/**
 * Represents a player in a Schiebe Poker game.
 *
 * @property name The name of the player
 * @property playerHandValue The current hand value of the player, initialized to HIGH_CARD
 * @property hiddenCards A mutable list of cards that are hidden from other players
 * @property openCards A mutable list of cards that are visible to all players
 */
class Player(val name: String, var hiddenCards: MutableList<Card> = mutableListOf(),
             var openCards: MutableList<Card> = mutableListOf()) {

    var playerHandValue: HandValue = HandValue.HIGH_CARD
    var actionCount = 2
}