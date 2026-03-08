package service

import entity.Player

/**
 * [Refreshable] implementation that refreshes nothing, but remembers
 * if a refresh method has been called (since last [reset])
 *
 * @constructor Creates a new [TestRefreshable] with the given [rootService]
 *
 * @param rootService The root service to which this service belongs
 */
class TestRefreshable(val rootService: RootService): Refreshable {
    var refeshAfterStartGameV = false
    var refeshAfterFinishGameV = false
    var refreshAfterNextTurnV = false
    var refreshAfterSwapV = false
    var refreshAfterSwapAllV = false
    var refreshAfterPushCardsV = false
    var refreshAfterRestartV = false

    /**
     * Resets all called properties to false
     */
    fun reset() {
        refeshAfterStartGameV = false
        refeshAfterFinishGameV = false
        refreshAfterNextTurnV = false
        refreshAfterSwapV = false
        refreshAfterSwapAllV = false
        refreshAfterPushCardsV = false
        refreshAfterRestartV = false
    }

    /* The refreshAfterStartGame method is called when the game starts, and it sets the
    refeshAfterStartGameV property to true. */
    override fun refreshAfterStartGame() {
        refeshAfterStartGameV = true
    }

    /* The refreshAfterFinishGame method is called when the game finishes, and it sets the
    refeshAfterFinishGameV property to true. */
    override fun refreshAfterFinishGame(winnerList: List<Player>) {
        refeshAfterFinishGameV = true
    }

/* The refreshAfterNextTurn method is called when the next turn starts, and it sets the
refreshAfterNextTurnV property to true. */
    override fun refreshAfterNextTurn() {
        refreshAfterNextTurnV = true
    }

/* The refreshAfterSwap method is called when the player swaps cards, and it sets the
refreshAfterSwapV property to true. */
    override fun refreshAfterSwap() {
        refreshAfterSwapV = true
    }

/* The refreshAfterSwapAll method is called when the player swaps all cards, and it sets the
 refreshAfterSwapAllV property to true. */
    override fun refreshAfterSwapAll() {
        refreshAfterSwapAllV = true
    }

/* The refreshAfterPushCards method is called when the player pushes cards, and it sets the
refreshAfterPushCardsV property to true. */
    override fun refreshAfterPushCards() {
        refreshAfterPushCardsV = true
    }

/* The refreshAfterRestart method is called when the game restarts, and it sets the
refreshAfterRestartV property to true. */
    override fun refreshAfterRestart() {
        refreshAfterRestartV = true
    }


}
