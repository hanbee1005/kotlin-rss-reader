package study

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val time =
            measureTimeMillis {
                val name = async { getName() }
                val lastName = async { getLastName() }
                println("Hello, $name $lastName")
            }
        println("Execution took $time ms")
    }
}

suspend fun getName(): String {
    delay(1000)
    return "Jason"
}

suspend fun getLastName(): String {
    delay(1000)
    return "Park"
}

/** await 호출을 해야 동작 수행 이후 값 받을 수 있다 */
