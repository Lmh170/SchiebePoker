package gui

import entity.Player
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

/**
 * The result menu scene of the game.
 *
 * @constructor Creates a new Result Menu Scene with the specified rootService.
 *
 * @param rootService The [RootService] that manages the game state.
 */
class EndSceneScene(private val rootService: RootService) : MenuScene(1920, 1080), Refreshable {

    // This pane is used to hold all components of the scene and easily center them on the screen
    private val contentPane = Pane<UIComponent>(
        width = 700,
        height = 620,
        posX = 1920 / 2 - 700 / 2,
        posY = 1080 / 2 - 620 / 2,
        visual = ColorVisual(Color(0x0C2027))
    )

    // This label is used to display the title of the scene
    private val titleLabel = Label(
        text = "GEWINNER",
        width = 700,
        height = 100,
        posX = 0,
        posY = 30,
        alignment = Alignment.CENTER,
        font = Font(30, Color(0xFFFFFFF), "JetBrains Mono ExtraBold")
    )

    // This label is used to display the name of the winner
    private val winnerLabel = Label(
        text = "",
        width = 600,
        height = 100,
        posX = 50,
        posY = 140,
        alignment = Alignment.CENTER,
        font = Font(38, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    )

    private val labelRankTitle = Label(
        text = "Ranking",
        width = 600,
        height = 40,
        posX = 50,
        posY = 260,
        alignment = Alignment.CENTER_LEFT,
        font = Font(26, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x32434A))
    )

    private val labelRank1 = Label(
        text = "",
        width = 600,
        height = 40,
        posX = 50,
        posY = 310,
        alignment = Alignment.CENTER_LEFT,
        font = Font(20, Color(0xFFFFFFF), "JetBrains Mono"),
        visual = ColorVisual(Color(0x32434A))
    )

    private val labelRank2 = Label(
        text = "",
        width = 600,
        height = 40,
        posX = 50,
        posY = 350,
        alignment = Alignment.CENTER_LEFT,
        font = Font(20, Color(0xFFFFFFF), "JetBrains Mono"),
        visual = ColorVisual(Color(0x32434A))
    )

    private val labelRank3 = Label(
        text = "",
        width = 600,
        height = 40,
        posX = 50,
        posY = 390,
        alignment = Alignment.CENTER_LEFT,
        font = Font(20, Color(0xFFFFFFF), "JetBrains Mono"),
        visual = ColorVisual(Color(0x32434A))
    )

    private val labelRank4 = Label(
        text = "",
        width = 600,
        height = 40,
        posX = 50,
        posY = 430,
        alignment = Alignment.CENTER_LEFT,
        font = Font(20, Color(0xFFFFFFF), "JetBrains Mono"),
        visual = ColorVisual(Color(0x32434A))
    )


    // This button is used to restart the game
    private val restartButton = Button(
        text = "NEUSTART",
        width = 280,
        height = 60,
        posX = 700 / 2 - 280 / 2,
        posY = 530,
        font = Font(22, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        // When the button is clicked, restart the game
        onMouseClicked = {
            // Access the onAllRefreshables method of the game service to call the refreshAfterGameRestart method
            rootService.gameService.onAllRefreshables { refreshAfterRestart() }
        }
    }

    // Initialize the scene by setting the background color and adding all components to the content pane
    init {
        background = ColorVisual(Color(12, 32, 39, 240))
        contentPane.addAll(
            titleLabel,
            winnerLabel,
            labelRankTitle,
            labelRank1,
            labelRank2,
            labelRank3,
            labelRank4,
            restartButton
        )
        addComponents(contentPane)
    }

    override fun refreshAfterFinishGame(winnerList: List<Player>) {
        super.refreshAfterFinishGame(winnerList)

        winnerLabel.text = if (winnerList.size > 1 && winnerList[0].playerHandValue == winnerList[1].playerHandValue) {
            "Unentschieden"
        } else {
            "${winnerList[0].name} hat gewonnen!"
        }

        // Clear all rank labels first
        labelRank1.text = ""
        labelRank2.text = ""
        labelRank3.text = ""
        labelRank4.text = ""

        // Fill rank labels with player data
        val rankLabels = listOf(labelRank1, labelRank2, labelRank3, labelRank4)
        winnerList.forEachIndexed { index, player ->
            if (index < rankLabels.size) {
                rankLabels[index].text = "${index + 1}. ${player.name.padEnd(15)} | ${player.playerHandValue}"
            }
        }
    }
}