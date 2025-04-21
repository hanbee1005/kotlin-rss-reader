package coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val time =
            measureTimeMillis {
                val name = getName()
                val lastName = getLastName()
                println("Hello, $name $lastName")
            }
        println("Execution took $time ms")
    }
}

// 비동기로 호출
// fun main() {
//    runBlocking {
//        val time = measureTimeMillis {
//            val name = async { getName() }
//            val lastName = async { getLastName() }
//            println("Hello, ${name.await()} ${lastName.await()}")
//        }
//        println("Execution took $time ms")
//    }
// }

suspend fun getName(): String {
    delay(1000)
    return "Jason"
}

suspend fun getLastName(): String {
    delay(1000)
    return "Park"
}
