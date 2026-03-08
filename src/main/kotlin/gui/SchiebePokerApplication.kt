package gui

import entity.Player
import service.Refreshable
import service.RootService
import tools.aqua.bgw.core.BoardGameApplication

/**
 * Main class of the MauMau application.
 */
class SchiebePokerApplication : BoardGameApplication("SchiebePokerGame"), Refreshable {

    // Create a new instance of the root service
    private val rootService = RootService()

    // Create the game and menu scenes and pass them the root service
    private val gameScene = GameScene(rootService)
    private val menuScene = MenuScene(rootService)
    private val endScene = EndSceneScene(rootService)
    private val nextPlayerScene  = NextPlayerScene(rootService)

    // Initialize the application by loading the fonts, adding refreshables and setting the initial scene
    init {

        // Register refreshables for the application and every scene
        rootService.addRefreshables(
            gameScene,
            menuScene,
            endScene,
            nextPlayerScene,
            this
        )

        // Set the initial scene to the main menu
        this.showGameScene(gameScene)
        this.showMenuScene(menuScene)

    }

    /**
     * The refreshAfterGameStart method is called by the service layer after a game has started.
     * It hides the menu scene after a short delay.
     */
    override fun refreshAfterStartGame() {
        hideMenuScene(500)
    }

    override fun refreshAfterConfirmation() {
        super.refreshAfterConfirmation()
        hideMenuScene(500)
    }

    override fun refreshAfterNextTurn() {
        super.refreshAfterNextTurn()
        showMenuScene(nextPlayerScene,0)
    }

    override fun refreshAfterFinishGame(winnerList: List<Player>) {
        super.refreshAfterFinishGame(winnerList)
        showMenuScene(endScene)
    }

    override fun refreshAfterRestart() {
        super.refreshAfterRestart()
        hideMenuScene(500)
        showMenuScene(menuScene)
    }

    companion object Application{

    }
}