fun main() {
    val n = readLine()!!.toInt()
    print(when(n) {
        1 -> "No!"
        2 -> "Yes!"
        3 -> "No!"
        4 -> "No!"
        else -> "Unknown number"
    })
}
