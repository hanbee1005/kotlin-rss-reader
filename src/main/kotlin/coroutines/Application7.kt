package coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() =
    runBlocking {
        val job =
            launch {
                while (true) {
                    delay(100L) // 여기서 기다리면서 다른 작업이 들어올 수 있게 함
                    println("While in ${Thread.currentThread().name}")
                }
            }
        delay(1000L)
        job.cancel()
    }
