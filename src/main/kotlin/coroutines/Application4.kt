package coroutines

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 무한 반복
fun main() =
    runBlocking {
        val job =
            launch {
                while (true) {
                    println("While in ${Thread.currentThread().name}")
                }
            }
    }
