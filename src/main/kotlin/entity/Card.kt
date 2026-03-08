package entity

/**
 * Represents a card with a [suit] and [value].
 *
 * @property suit The suit of the card (clubs, spades, hearts, or diamonds)
 * @property value The value of the card
 */
class Card(val suit: CardSuit, val value: CardValue)