fun main() {
    val n = readLine()!!.toInt()
    print(when (n){
        in 1000..9999 -> "4"
        in 100..999 -> "3"
        in 10..99 -> "2"
        else -> "1"
    })
}
