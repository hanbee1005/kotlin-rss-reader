package coroutines

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        println("${Thread.activeCount()} threads active at the start")

        val time =
            measureTimeMillis {
                createCoroutines(3)
            }
        println("${Thread.activeCount()} threads active at the end")
        println("Took $time ms")
    }
}

suspend fun createCoroutines(amount: Int) {
    coroutineScope {
        val jobs = mutableListOf<Job>()
        repeat(amount) {
            jobs +=
                launch {
                    println("Started $it in ${Thread.currentThread()}")
                    delay(1000)
                    println("Finished $it in ${Thread.currentThread().name}")
                }
        }
    }
}
