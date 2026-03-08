package entity
/**
 * Enum representing the possible hand values in poker, ordered from best (ROYAL_FLUSH) to worst (HIGH_CARD).
 * Each enum value represents a specific poker hand combination.
 */
enum class HandValue {
    ROYAL_FLUSH,
    STRAIGHT_FLUSH,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    FLUSH,
    STRAIGHT,
    THREE_OF_A_KIND,
    TWO_PAIR,
    ONE_PAIR,
    HIGH_CARD,
    ;

}