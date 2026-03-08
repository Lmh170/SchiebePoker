package gui

import service.Refreshable
import service.RootService
import tools.aqua.bgw.components.layoutviews.Pane
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.UIComponent
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.Color
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

class NextPlayerScene(private val rootService: RootService) : MenuScene(1920, 1080), Refreshable {

    // This pane is used to hold all components of the scene and easily center them on the screen
    private val contentPane = Pane<UIComponent>(
        width = 1000,
        height = 1200,
        posX = 1920 / 2 - 700 / 2,
        posY = 1080 / 2 - 900 / 2,
        visual = ColorVisual(Color(0x0C2027))
    )

    val labelNextPlayer = Label(
        posX = 800,
        posY = 500,
        width = 200,
        height = 50,
        text = "",
        font = Font(
            size = 30,
            color = Color(0xffffff),
            family = "Arial",
            fontWeight = Font.FontWeight.NORMAL,
            fontStyle = Font.FontStyle.NORMAL
        ),
        alignment = Alignment.CENTER,
        isWrapText = false,
        visual = ColorVisual(
            color = Color(0x000000)
        ).apply {
            transparency = 0.0
        }
    )

    val buttonConfirmNextTurn = Button(
        posX = 800,
        posY = 700,
        width = 120,
        height = 45,
        text = "Weiter",
        font = Font(
            size = 30,
            color = Color(0x000000),
            family = "Arial",
            fontWeight = Font.FontWeight.NORMAL,
            fontStyle = Font.FontStyle.NORMAL
        ),
        alignment = Alignment.CENTER,
        isWrapText = false,
        visual = ColorVisual(
            color = Color(0xFFFFFF)
        )
    ).apply {
        onMouseClicked = {
            rootService.gameService.onAllRefreshables { refreshAfterConfirmation() }
        }
    }

    init {
        contentPane.addAll(buttonConfirmNextTurn, labelNextPlayer)
        addComponents(contentPane)
    }

    override fun refreshAfterNextTurn() {
        super.refreshAfterNextTurn()
        labelNextPlayer.text = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].name
    }
}