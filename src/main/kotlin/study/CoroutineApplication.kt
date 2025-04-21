package study

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
                createCoroutines(10)
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

/**
 * result
 * >>
 *
 * Started 0 in Thread[main,5,main]
 * Started 1 in Thread[main,5,main]
 * Started 2 in Thread[main,5,main]
 * Finished 0 in main
 * Finished 1 in main
 * Finished 2 in main
 * 2 threads active at the end
 * Took 1019 ms
 *
 * >>
 * 인텔리제이 스레드 제외 하나의 스레드에서 수행
 * 하나의 스레드로 최대한 자원을 활용하는 것이 핵심
 *
 * */
