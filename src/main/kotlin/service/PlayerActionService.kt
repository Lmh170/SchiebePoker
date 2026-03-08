package service

/**
 * The [PlayerActionService] class is responsible for handling player actions such as
 * swapping cards and pushing new cards from the draw stack.
 * It interacts with the main game state through the [RootService] and
 * provides methods to perform specific actions related to the player's open cards and the middle cards.
 */
class PlayerActionService(private val rootService: RootService): AbstractRefreshingService() {

    /**
     * General Description: The method swap(playerSelection : Int, middleSelection : Int) allows
     * the player to swap exactly one card from his hand with exactly one card in the center.
     *
     *
     * Preconditions:
     * - Game needs to be started.
     * - Cards need to be distributed.
     * - It should be the player's turn.
     * - Player has not exceeded the allowed number of actions for the current turn.
     *
     *
     * Postconditions:
     * - The selected cards have been exchanged.
     * - The order of the other cards remains unchanged.
     *
     *
     * Parameter:
     * @param playerSelection The card selected from the player's hand.
     * @param middleSelection The card selected from the center.
     *
     *
     * Result:
     * @return none This method does not have a return value (Unit).
     *
     * Exceptions:
     * @throws IllegalStateException:
     * - If the game is not running.
     * - If the cards have not been distributed yet.
     * - If it is not the current player's turn.
     * - If the player has already performed 2 actions in this turn.
     * @throws IllegalArgumentException: If playerSelection or middleSelection is not in the valid range.
     * @throws IndexOutOfBoundsException: If the index of the cards do not exist in the card lists.
     **/
    fun swap(playerSelection: Int, middleSelection: Int){
        // Validate preconditions
        check(rootService.mainGame.playerList.isNotEmpty()) { "Game is not running" }
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        check(currentPlayer.openCards.isNotEmpty()) { "Cards have not been distributed yet" }
        check(rootService.mainGame.middleCards.isNotEmpty()) { "Cards have not been distributed yet" }
        check(currentPlayer.actionCount > 0) { "Player has already performed 2 actions in this turn" }
        require(playerSelection in 0..2) { "playerSelection is not in the valid range (0-2)" }
        require(middleSelection in 0..2) { "middleSelection is not in the valid range (0-2)" }

        val speicher =
            rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].openCards[playerSelection]
        rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].openCards[playerSelection] =
            rootService.mainGame.middleCards[middleSelection]

        rootService.mainGame.middleCards[middleSelection] = speicher

        rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount -= 1

        rootService.mainGame.logList.add("Player swapped card $playerSelection with middle card $middleSelection")

        if(rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount == 0){
            rootService.gameService.nextTurn()
        }

        onAllRefreshables { refreshAfterSwap() }
    }


    /**
     * General Description: The method swapAll() allows the player to swap all cards
     * from his hand with all cards in the center.
     *
     *
     * Preconditions:
     * Game needs to be started.
     * Cards need to be distributed.
     * It should be the player's turn.
     * Player has not exceeded the allowed number of actions for the current turn.
     *
     *
     * Postconditions:
     * All cards from the player's hand and all cards from the center have been exchanged.
     * The order of the cards inside each swapped group remains unchanged.
     *
     *
     * Parameter:
     * none
     *
     *
     * Result:
     * @return none This method does not have a return value (Unit).
     *
     *
     * Exceptions:
     * @throws IllegalStateException:
     * If the game is not running.
     * If the cards have not been distributed yet.
     * If it is not the current player's turn.
     * If the player has already performed 2 actions in this turn.
     *
     **/
    fun swapAll() {
        // Validate preconditions
        check(rootService.mainGame.playerList.isNotEmpty()) { "Game is not running" }
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        check(currentPlayer.openCards.isNotEmpty()) { "Cards have not been distributed yet" }
        check(rootService.mainGame.middleCards.isNotEmpty()) { "Cards have not been distributed yet" }
        check(currentPlayer.actionCount > 0) { "Player has already performed 2 actions in this turn" }

        val speicher = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].openCards
        rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].openCards =
            rootService.mainGame.middleCards

        rootService.mainGame.middleCards = speicher

        rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount -= 1

        rootService.mainGame.logList.add("Player swapped all cards")
        onAllRefreshables { refreshAfterSwapAll() }

        if(rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount == 0){
            rootService.gameService.nextTurn()
        }
    }

    /**
     *General Description: The method pushCards(left:Boolean) shifta the
     *three open cards in the middle either to the left or to the right.
     *If left is true, the cards are shifted to the left otherwise they are
     *shifted to the right. The card that leaves the middle is placed on
     *Discardstack and the empty position will be filled with a new Card
     *from DrawStack.
     *
     *
     *Preconditions:
     *-Game needs to be started.
     *-Cards need to be distributed.
     *-It should be Current Player's turn
     *-Player must not exceed allowed number of actions for his current turn.
     *
     *
     *Postconditions:
     *-One middle Card is moved to discard stack.
     *-Remaining middle cards are shifted one position in chosen direction.
     *-One new Card id drawn from draw stack and put in the middle.
     *
     *
     *Parameter:
     *@param left direction of the shift (if left=true cards pushed left,
     * if left=false cards pushed right)
     *
     *
     *Result:
     *@return None (Unit) no return value
     *
     *
     *Exceptions:
     *@throws IllegalStateException:
     *-If game not running
     *-If not the current Player's turn
     *-If player has already performed his allowed two actions per turn
     *
     */
    fun pushCards(left: Boolean){
        // Validate preconditions
        check(rootService.mainGame.playerList.isNotEmpty()) { "Game is not running" }
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        check(currentPlayer.actionCount > 0) { "Player has already performed his allowed two actions per turn" }
        if (rootService.mainGame.drawStack.isEmpty()) {
            rootService.mainGame.logList.add("Draw stack is empty, reshuffling discard stack into draw stack")
            rootService.gameService.reshuffleDrawStack()
        }

        val card = rootService.mainGame.drawStack.removeAt(0)

        if (left) {
            rootService.mainGame.middleCards.add(0, card)
            rootService.mainGame.discardStack.add(rootService.mainGame.middleCards.removeAt(3))
        } else {
            rootService.mainGame.discardStack.add(rootService.mainGame.middleCards.removeAt(0))
            rootService.mainGame.middleCards.add(card)
        }
        rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount -= 1


        rootService.mainGame.logList.add("Player pushed cards ${if (left) "left" else "right"}")

        onAllRefreshables { refreshAfterPushCards() }

        if(rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount == 0){
            rootService.gameService.nextTurn()
        }
    }

    fun swapNone() {
        // Validate preconditions
        check(rootService.mainGame.playerList.isNotEmpty()) { "Game is not running" }
        val currentPlayer = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex]
        check(currentPlayer.actionCount > 0) { "Player has no actions left" }

        //empty function, just for refreshing
        rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount -= 1

        rootService.mainGame.logList.add("Player swapped no cards")

        if(rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].actionCount == 0){
            rootService.gameService.nextTurn()
        }

    }
}