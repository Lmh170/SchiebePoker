package gui
import entity.Player
import service.Refreshable
import service.RootService
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.gamecomponentviews.GameComponentView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.Orientation
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.core.Color
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * The GameScene class is a BoardGameScene that displays the game board and all game components.
 *
 * @constructor Creates a new Game Scene with the specified rootService.
 *
 * @param rootService The [RootService] that manages the game state.
 */
class GameScene(private val rootService: RootService) :
    BoardGameScene(1920, 1080, background = ColorVisual(108, 168, 59)), Refreshable {

    // CardImageLoader to load card visuals
    private val cardImageLoader = CardImageLoader()
    private var selectedPlayerCard = -1
    private var selectedMiddleCard = -1

    var cardList = mutableListOf(mutableListOf<CardView>())

    val linearLayout1 = LinearLayout<GameComponentView>(
        posX = 500,
        posY = 50,
        width = 800,
        height = 250,
        spacing = 10,
        visual = ColorVisual(
            color = Color(0xFFFFFF)
        ).apply {
        },
        orientation = Orientation.HORIZONTAL,
        alignment = Alignment.TOP_LEFT
    )

    val card11 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedPlayerCard = 1
        }
    }

    val card12 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedPlayerCard = 2
        }
    }

    val card13 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
            onMouseClicked = {
                selectedPlayerCard = 3
            }
        }

    val card14 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    )

    val card15 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    )

    val linearLayout3 = LinearLayout<GameComponentView>(
        posX = 500,
        posY = 850,
        width = 800,
        height = 250,
        spacing = 10,
        visual = ColorVisual(
            color = Color(0xFFFFFF)
        ).apply {
            transparency = 0.0
        },
        orientation = Orientation.HORIZONTAL,
        alignment = Alignment.TOP_LEFT
    )

    val card31 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedPlayerCard = 1
        }
    }

    val card32 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedPlayerCard = 2
        }
    }

    val card33 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedPlayerCard = 3
        }
    }

    val card34 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    )

    val card35 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    )

    val linearLayout4 = LinearLayout<GameComponentView>(
        posX = 47,
        posY = 30,
        width = 250,
        height = 1500,
        spacing = 10,
        visual = ColorVisual(
            color = Color(0xFFFFFF)
        ).apply {
            transparency = 0.0
        },
        orientation = Orientation.VERTICAL,
        alignment = Alignment.TOP_LEFT
    )

    val card41 = CardView(
        posX = 8,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedPlayerCard = 1
        }
    }

    val card42 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedPlayerCard = 2
        }
    }

    val card43 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedPlayerCard = 3
        }
    }

    val card44 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    )

    val card45 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    )

    val linearLayout2 = LinearLayout<GameComponentView>(
        posX = 1700,
        posY = 10,
        width = 250,
        height = 1500,
        spacing = 10,
        visual = ColorVisual(
            color = Color(0xFFFFFF)
        ).apply {
            transparency = 0.0
        },
        orientation = Orientation.VERTICAL,
        alignment = Alignment.TOP_LEFT
    )

    val card21 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedPlayerCard = 1
        }
    }

    val card22 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedPlayerCard = 2
        }
    }

    val card23 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedPlayerCard = 3
        }
    }

    val card24 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    )

    val card25 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    )

    val linearLayoutMid = LinearLayout<GameComponentView>(
        posX = 600,
        posY = 400,
        width = 700,
        height = 250,
        spacing = 5,
        visual = ColorVisual(
            color = Color(0xFFFFFF)
        ).apply {
            transparency = 0.0
        },
        orientation = Orientation.HORIZONTAL,
        alignment = Alignment.TOP_LEFT
    )

    val cardDrawStack = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    )

    val cardm1 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedMiddleCard= 1
        }
    }

    val cardm2 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedMiddleCard= 2
        }
    }

    val cardm3 = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    ).apply {
        onMouseClicked = {
            selectedMiddleCard= 3
        }
    }

    val cardDiscardStack = CardView(
        posX = 0,
        posY = 0,
        width = 130,
        height = 200,
        front = ColorVisual(
            color = Color(0xB4B4B4)
        ),
        back = ColorVisual(
            color = Color(0xB4B4B4)
        )
    )

    val btnSwap = Button(
        posX = 600,
        posY = 650,
        width = 130,
        height = 50,
        text = "Swap",
        font = Font(16, Color.WHITE),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            if (selectedPlayerCard > 0 && selectedMiddleCard > 0) {
                rootService.playerService.swap(selectedPlayerCard - 1, selectedMiddleCard - 1)
                selectedPlayerCard = -1
                selectedMiddleCard = -1
                rootService.gameService.onAllRefreshables { refreshAfterSwap() }
            }
        }
    }

    val btnSwapAll = Button(
        posX = 740,
        posY = 650,
        width = 130,
        height = 50,
        text = "Swap All",
        font = Font(16, Color.WHITE),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked =  {
            rootService.playerService.swapAll()
            rootService.gameService.onAllRefreshables { refreshAfterSwapAll() }
        }
    }

    val btnPushLeft = Button(
        posX = 880,
        posY = 650,
        width = 130,
        height = 50,
        text = "Push Left",
        font = Font(16, Color.WHITE),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            rootService.playerService.pushCards(false)
            rootService.playerService.onAllRefreshables { refreshAfterPushCards() }
        }
    }

    val btnPushRight = Button(
        posX = 1020,
        posY = 650,
        width = 130,
        height = 50,
        text = "Push Right",
        font = Font(16, Color.WHITE),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            rootService.playerService.pushCards(true)
            rootService.playerService.onAllRefreshables { refreshAfterPushCards() }
        }
    }

    val btnSwapNone = Button(
        posX = 1160,
        posY = 650,
        width = 130,
        height = 50,
        text = "Swap None",
        font = Font(16, Color.WHITE),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            rootService.playerService.swapNone()

            roundLabel.text = "Aktuelle Runde: ${rootService.mainGame.roundCount}"
            actionLabel.text = "Verbleibende Züge: ${rootService.mainGame
                .playerList[rootService.mainGame.currentPlayerIndex].actionCount}"
            logLabel.text = rootService.mainGame.logList.reversed().joinToString("\n")
        }
    }

    val logLabel = Label(
        posX = 880,
        posY = 710,
        width = 270,
        height = 100,
        text = "Logs",
        font = Font(
            size = 12,
            color = Color(0x000000),
            family = "Arial",
            fontWeight = Font.FontWeight.NORMAL,
            fontStyle = Font.FontStyle.NORMAL
        ),
        alignment = Alignment.TOP_LEFT,
        isWrapText = true,
        visual = ColorVisual(
            color = Color(0xFFFFFF)
        ).apply {
            transparency = 0.3
        }
    )

    val roundLabel = Label(
        posX = 600,
        posY = 710,
        width = 260,
        height = 50,
        text = "Aktuelle Runde: 0",
        font = Font(
            size = 14,
            color = Color(0xFFFFFF),
            family = "Arial",
            fontWeight = Font.FontWeight.NORMAL,
            fontStyle = Font.FontStyle.NORMAL
        ),
        alignment = Alignment.CENTER_LEFT,
        isWrapText = false,
        visual = ColorVisual(
            color = Color(0x49585D)
        )
    )

    val actionLabel = Label(
        posX = 600,
        posY = 760,
        width = 260,
        height = 50,
        text = "Verbleibende Züge: 2",
        font = Font(
            size = 14,
            color = Color(0xFFFFFF),
            family = "Arial",
            fontWeight = Font.FontWeight.NORMAL,
            fontStyle = Font.FontStyle.NORMAL
        ),
        alignment = Alignment.CENTER_LEFT,
        isWrapText = false,
        visual = ColorVisual(
            color = Color(0x49585D)
        )
    )


    val label1 = Label(
        posX = 699,
        posY = 0,
        width = 120,
        height = 30,
        text = "Player1",
        font = Font(
            size = 14,
            color = Color(0x000000),
            family = "Arial",
            fontWeight = Font.FontWeight.NORMAL,
            fontStyle = Font.FontStyle.NORMAL
        ),
        alignment = Alignment.CENTER,
        isWrapText = false,
        visual = ColorVisual(
            color = Color(0xFF0000)
        ).apply {
            transparency = 0.0
        }
    )

    val label2 = Label(
        posX = 1700,
        posY = 1050,
        width = 120,
        height = 30,
        text = "Player3",
        font = Font(
            size = 14,
            color = Color(0x000000),
            family = "Arial",
            fontWeight = Font.FontWeight.NORMAL,
            fontStyle = Font.FontStyle.NORMAL
        ),
        alignment = Alignment.CENTER,
        isWrapText = false,
        visual = ColorVisual(
            color = Color(0xFF0000)
        ).apply {
            transparency = 0.0
        }
    )

    val label3 = Label(
        posX = 697,
        posY = 1050,
        width = 120,
        height = 30,
        text = "Player3",
        font = Font(
            size = 14,
            color = Color(0x000000),
            family = "Arial",
            fontWeight = Font.FontWeight.NORMAL,
            fontStyle = Font.FontStyle.NORMAL
        ),
        alignment = Alignment.CENTER,
        isWrapText = false,
        visual = ColorVisual(
            color = Color(0xFF0000)
        ).apply {
            transparency = 0.0
        }
    )

    val label4 = Label(
        posX = 50,
        posY = 0,
        width = 120,
        height = 30,
        text = "Player4",
        font = Font(
            size = 14,
            color = Color(0x000000),
            family = "Arial",
            fontWeight = Font.FontWeight.NORMAL,
            fontStyle = Font.FontStyle.NORMAL
        ),
        alignment = Alignment.CENTER,
        isWrapText = false,
        visual = ColorVisual(
            color = Color(0xFF0000)
        ).apply {
            transparency = 0.0
        }
    )


   init {
       linearLayoutMid.addAll(cardDrawStack, cardm1, cardm2, cardm3, cardDiscardStack)
       addComponents(linearLayoutMid)
       addComponents(label1, label2, label3, label4, logLabel, roundLabel, actionLabel)

       // Add buttons directly (Buttons cannot be in LinearLayout)
       addComponents(btnSwap, btnSwapAll, btnSwapNone, btnPushLeft, btnPushRight)

       linearLayout1.addAll(card11, card12, card13, card14, card15)
       linearLayout2.addAll(card21, card22, card23, card24, card25)

       addComponents(linearLayout1, linearLayout2)

       linearLayout3.addAll(card31, card32, card33, card34, card35)
       addComponents(linearLayout3)

       linearLayout4.addAll(card41, card42, card43, card44, card45)
       addComponents(linearLayout4)
   }

    override fun refreshAfterStartGame() {
        linearLayout3.isVisible = true
        linearLayout4.isVisible = true
        label3.isVisible = true
        label4.isVisible = true

        cardList.clear()
        cardList.addAll(listOf(
            mutableListOf(card11, card12, card13, card14, card15),
            mutableListOf(card21, card22, card23, card24, card25),
            mutableListOf(card31, card32, card33, card34, card35),
            mutableListOf(card41, card42, card43, card44, card45)
        ))

        label1.text = rootService.mainGame.playerList[0].name
        label2.text = rootService.mainGame.playerList[1].name
        roundLabel.text = "Aktuelle Runde: ${rootService.mainGame.roundCount}"
        actionLabel.text = "Verbleibende Züge: ${rootService.mainGame
            .playerList[rootService.mainGame.currentPlayerIndex].actionCount}"


        if (rootService.mainGame.playerList.size < 4) {
            linearLayout4.isVisible = false
            cardList.removeAt(3)
            label4.isVisible = false
        } else {
            label4.text = rootService.mainGame.playerList[3].name
        }
        if (rootService.mainGame.playerList.size < 3) {
            linearLayout3.isVisible = false
            cardList.removeAt(2)
            label3.isVisible = false
        } else {
            label3.text = rootService.mainGame.playerList[2].name
        }

        loadAllCards()

        cardm1.showFront()
        cardm2.showFront()
        cardm3.showFront()
    }

    private fun hideLastPlayerHiddenCards() {
        val lastPlayerIndex = if (rootService.mainGame.currentPlayerIndex - 1 < 0) {
            rootService.mainGame.playerList.size - 1
        } else {
            rootService.mainGame.currentPlayerIndex - 1
        }

        cardList[lastPlayerIndex][3].showBack()
        cardList[lastPlayerIndex][4].showBack()
    }

    private fun updateCurrentPlayerCards() {
        cardList[rootService.mainGame.currentPlayerIndex][3].showFront()
        cardList[rootService.mainGame.currentPlayerIndex][4].showFront()

        for (j in 0 until 3) {
            val card = rootService.mainGame.playerList[rootService.mainGame.currentPlayerIndex].openCards[j]
            val cardView = cardList[rootService.mainGame.currentPlayerIndex][j]
            cardView.frontVisual = cardImageLoader.frontImageFor(card.suit, card.value)
            cardView.showFront()
        }
    }

    private fun reloadMiddleCards() {
        cardm1.frontVisual = cardImageLoader.frontImageFor(rootService.mainGame.middleCards[0].suit,
            rootService.mainGame.middleCards[0].value)
        cardm2.frontVisual = cardImageLoader.frontImageFor(rootService.mainGame.middleCards[1].suit,
            rootService.mainGame.middleCards[1].value)
        cardm3.frontVisual = cardImageLoader.frontImageFor(rootService.mainGame.middleCards[2].suit,
            rootService.mainGame.middleCards[2].value)
        try {
            cardDiscardStack.frontVisual = cardImageLoader.frontImageFor(rootService.mainGame.discardStack.last().suit,
                rootService.mainGame.discardStack.last().value)

        } catch (_: NoSuchElementException) {
            cardDiscardStack.frontVisual = cardImageLoader.blankImage
        }

        cardDiscardStack.showFront()

    }

    private fun loadAllCards() {
        for (i in 0 until rootService.mainGame.playerList.size) {
            for (j in 0 until 3) {
                val card = rootService.mainGame.playerList[i].openCards[j]
                val cardView = cardList[i][j]
                cardView.frontVisual = cardImageLoader.frontImageFor(card.suit, card.value)
                cardView.showFront()
            }

            for (j in 0 until 2) {
                val card = rootService.mainGame.playerList[i].hiddenCards[j]
                val cardView = cardList[i][j+3]
                cardView.frontVisual = cardImageLoader.frontImageFor(card.suit, card.value)
                cardView.showBack()
            }
        }

        cardList[rootService.mainGame.currentPlayerIndex][3].showFront()
        cardList[rootService.mainGame.currentPlayerIndex][4].showFront()


        cardm1.frontVisual = cardImageLoader.frontImageFor(rootService.mainGame.middleCards[0].suit,
            rootService.mainGame.middleCards[0].value)
        cardm2.frontVisual = cardImageLoader.frontImageFor(rootService.mainGame.middleCards[1].suit,
            rootService.mainGame.middleCards[1].value)
        cardm3.frontVisual = cardImageLoader.frontImageFor(rootService.mainGame.middleCards[2].suit,
            rootService.mainGame.middleCards[2].value)
    }

    override fun refreshAfterFinishGame(winnerList: List<Player>) {
        // Clear the display when game finishes
        //    currentPlayerLabel.text = "Gewinner: ${winnerList.joinToString(", ") { it.name }}"
    }

    override fun refreshAfterSwap() {
        reloadMiddleCards()
       // reloadLastPlayerCards()
        updateCurrentPlayerCards()

        roundLabel.text = "Aktuelle Runde: ${rootService.mainGame.roundCount}"
        actionLabel.text = "Verbleibende Züge: ${rootService.mainGame
            .playerList[rootService.mainGame.currentPlayerIndex].actionCount}"
        logLabel.text = rootService.mainGame.logList.reversed().joinToString("\n")
    }

    override fun refreshAfterSwapAll() {
        reloadMiddleCards()
      //  reloadLastPlayerCards()
        updateCurrentPlayerCards()

        roundLabel.text = "Aktuelle Runde: ${rootService.mainGame.roundCount}"
        actionLabel.text = "Verbleibende Züge: ${rootService.mainGame
            .playerList[rootService.mainGame.currentPlayerIndex].actionCount}"
        logLabel.text = rootService.mainGame.logList.reversed().joinToString("\n")
    }

    override fun refreshAfterPushCards() {
        super.refreshAfterPushCards()
        reloadMiddleCards()

        roundLabel.text = "Aktuelle Runde: ${rootService.mainGame.roundCount}"
        actionLabel.text = "Verbleibende Züge: ${rootService.mainGame
            .playerList[rootService.mainGame.currentPlayerIndex].actionCount}"
        logLabel.text = rootService.mainGame.logList.reversed().joinToString("\n")
    }

    override fun refreshAfterNextTurn() {
        hideLastPlayerHiddenCards()
        updateCurrentPlayerCards()
        /*
                for (i in 0 until rootService.mainGame.playerList.size) {
                   cardList[i][3].showBack()
                    cardList[i][4].showBack()
                }

         */
        roundLabel.text = "Aktuelle Runde: ${rootService.mainGame.roundCount}"
        actionLabel.text = "Verbleibende Züge: ${rootService.mainGame
            .playerList[rootService.mainGame.currentPlayerIndex].actionCount}"
        logLabel.text = rootService.mainGame.logList.reversed().joinToString("\n")
    }
}