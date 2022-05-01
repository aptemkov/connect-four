package connectfour
class Score(var score1:Int = 0, var score2:Int = 0){}

fun gamesNumber():Int {
    while(true) {
        println("Do you want to play single or multiple games? \n" +
                "For a single game, input 1 or press Enter \n" +
                "Input a number of games: ")
        val a = readLine()!!
        if(a.isEmpty()) return 1
        else {
            try {
                when (a.toInt()) {
                    0 -> println("Invalid input")
                    in 1..100 -> return a.toInt()
                    in 101..Int.MAX_VALUE -> println("The number of games is too big")
                    else -> println("Invalid input")
                }
            } catch(e:java.lang.NumberFormatException) {println("Invalid input")}
        }
    }
}

fun printField(rows: Int, cols: Int, field: MutableList<MutableList<Char>>){
    for (i in 1..cols) print(" $i")
    println()
    for (i in 0 until rows) {
        for (j in 0 until cols)
            print("║${field[i][j]}")
        println('║')
    }
    println("╚" + "═╩".repeat(cols - 1) + "═╝")
}

fun winingCondition(rows: Int, cols: Int, field: MutableList<MutableList<Char>>):Int{
    var counter = 0
    for (i in 0 until rows) {
        for (j in 0 until cols) {
           try  {
                if (field[i][j] == 'o' && field[i + 1][j] == 'o' && field[i + 2][j] == 'o' && field[i + 3][j] == 'o') return 1
                if (field[i][j] == '*' && field[i + 1][j] == '*' && field[i + 2][j] == '*' && field[i + 3][j] == '*') return 2

                if (field[i][j] == 'o' && field[i][j + 1] == 'o' && field[i][j + 2] == 'o' && field[i][j + 3] == 'o') return 1
                if (field[i][j] == '*' && field[i][j + 1] == '*' && field[i][j + 2] == '*' && field[i][j + 3] == '*') return 2

                if (field[i][j] == 'o' && field[i + 1][j + 1] == 'o' && field[i + 2][j + 2] == 'o' && field[i + 3][j + 3] == 'o') return 1
                if (field[i][j] == '*' && field[i + 1][j + 1] == '*' && field[i + 2][j + 2] == '*' && field[i + 3][j + 3] == '*') return 2
                if (field[i][j] == 'o' && field[i - 1][j + 1] == 'o' && field[i - 2][j + 2] == 'o' && field[i - 3][j + 3] == 'o') return 1
                if (field[i][j] == '*' && field[i - 1][j + 1] == '*' && field[i - 2][j + 2] == '*' && field[i - 3][j + 3] == '*') return 2

                if (field[i][j] != ' ') counter++
                if (counter == rows * cols) return 3
            } catch (e:java.lang.IndexOutOfBoundsException) {continue}
        }

    }
    return 0
}

fun clearField(rows: Int, cols: Int, field: MutableList<MutableList<Char>>): MutableList<MutableList<Char>> {
    for (i in 0 until rows)
        for (j in 0 until cols)
            field[i][j] = ' '
    return field
}

fun drawCheck(rows: Int, cols: Int, field: MutableList<MutableList<Char>>): Boolean {
    var drawCounter = 0
    for (i in 0 until rows) {
        for (j in 0 until cols)
            if (field[i][j] == ' ') drawCounter++
    }
    return drawCounter == 0
}

fun main() {
    val gameScore = Score(0,0)

    println("Connect Four")
    println("First player's name: ")
    val player1 = readLine()!!
    println("Second player's name: ")
    val player2 = readLine()!!

    val pattern = """^\s*\d+\s*[xX]\s*\d+\s*$""".toRegex()
    var rows: Int
    var cols: Int
    while (true) {
        println(
            "Set the board dimensions (Rows x Columns)\n" +
                    "Press Enter for default (6 x 7) "
        )
        val input = readLine()!!
        if (input.matches(pattern) || input.isEmpty()) {
            when (input) {
                "" -> {
                    rows = 6; cols = 7;break
                }

                else -> {
                    val (rowsStr, colsStr) = input.split('x', 'X')
                    cols = colsStr.trim().toInt()
                    rows = rowsStr.trim().toInt()
                    if (cols !in 5..9) {
                        println("Board columns should be from 5 to 9")
                        continue
                    } else if (rows !in 5..9) {
                        println("Board rows should be from 5 to 9")
                        continue
                    } else (break)
                }
            }
        } else println("Invalid input")
    }
    val numberOfGames = gamesNumber()
    println(
        "$player1 VS $player2\n"
                + "$rows X $cols board"
    )
    when (numberOfGames){
        1 -> println("Single game")
        else -> println("Total $numberOfGames games")
    }

    // ║, ╚, ═, ╩, ╝
    val field = mutableListOf(
        mutableListOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        mutableListOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        mutableListOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        mutableListOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        mutableListOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        mutableListOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        mutableListOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        mutableListOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        mutableListOf('-', '-', '-', '-', '-', '-', '-', '-', '-')
    )
    clearField(rows,cols,field)

    var turn = 1
    for (i in 0 until numberOfGames){
        if (numberOfGames > 1)
            println("Game #${i+1}")
        printField(rows,cols,field)
        while (true) {
            when (turn % 2) {
                1 -> {
                    while (true) {
                        println("$player1's turn:")
                        val playerTurn = readLine()!!
                        if (playerTurn == "end") {
                            println("Game over!")
                            return
                        } else {
                            var playerChoice = 0
                            try {
                                playerChoice = playerTurn.toInt()
                            } catch (e: NumberFormatException) {
                                println("Incorrect column number")
                                continue
                            }
                            var rowsChoice = rows - 1

                            when (playerChoice) {
                                in 1..cols -> {
                                    while (true) {
                                        if (field[rowsChoice][playerChoice - 1] == ' ') {
                                            field[rowsChoice][playerChoice - 1] = 'o'
                                            printField(rows, cols, field)
                                            turn++
                                            break
                                        } else {
                                            if (rowsChoice < 1) {
                                                println("Column $playerChoice is full")
                                                break
                                            }
                                            rowsChoice--
                                        }

                                    }
                                }
                                in Int.MIN_VALUE until 1, in cols..Int.MAX_VALUE -> {
                                    println("The column number is out of range (1 - $cols)")
                                }
                                else -> {
                                    println("Incorrect column number")
                                }
                            }
                        }
                        break
                    }
                }
                0 -> {
                    while (true) {
                        println("$player2's turn:")
                        val playerTurn = readLine()!!
                        if (playerTurn == "end") {
                            println("Game over!")
                            return
                        } else {
                            var playerChoice = 0
                            try {
                                playerChoice = playerTurn.toInt()
                            } catch (e: NumberFormatException) {
                                println("Incorrect column number")
                                continue
                            }
                            var rowsChoice = rows - 1

                            when (playerChoice) {
                                in 1..cols -> {
                                    while (true) {
                                        if (field[rowsChoice][playerChoice - 1] == ' ') {
                                            field[rowsChoice][playerChoice - 1] = '*'
                                            printField(rows, cols, field)
                                            turn++
                                            break
                                        } else {
                                            if (rowsChoice < 1) {
                                                println("Column $playerChoice is full")
                                                break
                                            }
                                            rowsChoice--
                                        }
                                    }
                                }
                                in Int.MIN_VALUE until 1, in cols..Int.MAX_VALUE -> {
                                    println("The column number is out of range (1 - $cols)")
                                }
                                else -> {
                                    println("Incorrect column number")
                                }
                            }
                        }
                        break
                    }
                }
                else -> println("Game over!")
            }
            if (drawCheck(rows,cols, field)) {
                println("It is a draw ")
                println("Score")
                gameScore.score1++
                gameScore.score2++
                println("$player1: ${gameScore.score1} $player2: ${gameScore.score2}")
                clearField(rows, cols, field)
                break
            }

            when (winingCondition(rows, cols, field)) {
                0 -> continue
                1 -> {
                    println("Player $player1 won \nScore")
                    gameScore.score1+=2
                    println("$player1: ${gameScore.score1} $player2: ${gameScore.score2}")
                    clearField(rows, cols, field)
                    break
                }
                2 -> {
                    println("Player $player2 won \nScore")
                    gameScore.score2+=2
                    println("$player1: ${gameScore.score1} $player2: ${gameScore.score2}")
                    clearField(rows, cols, field)
                    break
                }
                else -> continue
            }
        }
    }
    println("Game over!")
}
