package gui

import entity.Player
import service.Refreshable
import service.RootService
import tools.aqua.bgw.components.layoutviews.Pane
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.ComboBox
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.components.uicomponents.UIComponent
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.Color
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.ImageVisual

/**
 * The main menu scene of the game.
 *
 * @constructor Creates a new Main Menu Scene with the specified rootService.
 *
 * @param rootService The [RootService] that manages the game state.
 */
class MenuScene(private val rootService: RootService) : MenuScene(1920, 1080), Refreshable {

    // This pane is used to hold all components of the scene and easily center them on the screen
    private val contentPane = Pane<UIComponent>(
        width = 700,
        height = 900,
        posX = 1920 / 2 - 700 / 2,
        posY = 1080 / 2 - 900 / 2,
        visual = ColorVisual(Color(0x0C2027))
    )

    // This label is used to display the title of the scene
    private val titleLabel = Label(
        text = "NAMEN EINGEBEN",
        width = 700,
        height = 100,
        posX = 0,
        posY = 30,
        alignment = Alignment.CENTER,
        font = Font(30, Color(0xFFFFFFF), "JetBrains Mono ExtraBold")
    )

    // This text field is used to enter the name of the first player (default)
    private val playerDefaultInput = TextField(
        prompt = "Name",
        width = 600,
        height = 75,
        posX = 50,
        posY = 150,
        font = Font(26, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D)),
    )

    // This button is used to remove the first player (default)
    private val playerRemove = Button(
        width = 75,
        height = 75,
        posX = 575,
        posY = 150,
        font = Font(35, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = CompoundVisual(
            ColorVisual(Color(0x49585D)),
            ImageVisual("remove.png")
        )
    ).apply {
        // When the button is clicked, the first player is removed
        onMouseClicked = {
            removePlayer(0)
        }
    }

    // This button is used to add a new player
    private val playerAdd = Button(
        width = 75,
        height = 75,
        posX = 700 / 2 - 75 / 2,
        posY = 275,
        font = Font(35, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = CompoundVisual(
            ColorVisual(Color(0x49585D)),
            ImageVisual("add.png")
        )
    ).apply {
        // When the button is clicked, a new player is added
        onMouseClicked = {
            addPlayer()
        }
    }

    // Group all player inputs and remove buttons in lists to easily manage them
    private val playerInputs = mutableListOf(playerDefaultInput)
    private val playerRemoves = mutableListOf(playerRemove)

    val roundLabel = Label(
        posX = 125,
        posY = 700,
        width = 60,
        height = 30,
        text = "Runden:",
        font = Font(
            size = 14,
            color = Color(0xFFFFFF),
            family = "Arial",
            fontWeight = Font.FontWeight.NORMAL,
            fontStyle = Font.FontStyle.NORMAL
        ),
        alignment = Alignment.CENTER_RIGHT
    )

    val comboBox = ComboBox(
        posX = 200,
        posY = 700,
        width = 120,
        height = 30,
        font = Font(
            size = 14,
            color = Color(0x000000),
            family = "Arial",
            fontWeight = Font.FontWeight.NORMAL,
            fontStyle = Font.FontStyle.NORMAL
        ),
        visual = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        items = listOf(
            2,
            3,
            4,
            5,
            6,
            7
        )
    )

    // This button is used to start the game
    private val startButton = Button(
        text = "START",
        width = 280,
        height = 60,
        posX = 370,
        posY = 790,
        font = Font(22, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        // When the button is clicked, the game is started with the entered player names
        onMouseClicked = {
            // Filter out all player names that are not empty
            val playerNames = playerInputs.filter { it.text.isNotBlank() }.map { Player(it.text) }
            // If there are at least two and at most four player names, start the game
            if(playerNames.size in 2..4 && comboBox.selectedItem != null) {
                // Call the startNewGame method of the game service with the player names
                rootService.gameService.startGame(playerNames, comboBox.selectedItem ?: 2)
            }
        }
    }

    // Initialize the scene by setting the background color and adding all components to the content pane
    init {
        background = ColorVisual(Color(12, 32, 39, 240))
        contentPane.addAll(titleLabel, playerDefaultInput, playerRemove, playerAdd, startButton,
            roundLabel, comboBox)
        addComponents(contentPane)
    }

    /**
     * Adds a new player input field and remove button to the scene.
     */
    private fun addPlayer() {
        // Disallow adding more than four players
        if(playerInputs.size >= 4) return

        // Get the current index of the player input field to be created
        val currentI = playerInputs.size

        // Create a new player input field
        val newPlayerInput = TextField(
            prompt = "Name",
            width = 600,
            height = 75,
            posX = 50,
            posY = 150 + 100 * playerInputs.size,
            font = Font(26, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
            visual = ColorVisual(Color(0x49585D)),
        )

        // Create a new remove button for the player input field
        val newPlayerRemove = Button(
            width = 75,
            height = 75,
            posX = 575,
            posY = 150 + 100 * playerInputs.size,
            font = Font(35, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
            visual = CompoundVisual(
                ColorVisual(Color(0x49585D)),
                ImageVisual("remove.png")
            )
        ).apply {
            // When the button is clicked, the player input field is removed
            onMouseClicked = {
                removePlayer(currentI)
            }
        }

        // Add the new player input field and remove button to the scene and the lists
        contentPane.add(newPlayerInput)
        contentPane.add(newPlayerRemove)
        playerInputs.add(newPlayerInput)
        playerRemoves.add(newPlayerRemove)

        // Move the add player button down by 100 pixels
        playerAdd.posY += 100
    }

    /**
     * Removes the player input field and remove button at the given index.
     *
     * @param index The index of the player input field and remove button to be removed
     */
    private fun removePlayer(index : Int) {
        // Disallow removing the last player remaining
        if (playerInputs.size > 1) {
            // Remove the player input field and remove button at the given index
            contentPane.remove(playerInputs[index])
            contentPane.remove(playerRemoves[index])
            playerInputs.removeAt(index)
            playerRemoves.removeAt(index)

            // Iterate over all player input fields and remove buttons after the removed index
            // Move them up by 100 pixels to fill the gap
            // Update the on click event of the remove buttons to remove the correct player
            for (i in index until playerInputs.size) {
                playerInputs[i].posY -= 100
                playerRemoves[i].posY -= 100
                playerRemoves[i].onMouseClicked = {
                    removePlayer(i)
                }
            }

            // Move the add player button up by 100 pixels
            playerAdd.posY -= 100
        }
    }
}