package coroutines

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() =
    runBlocking {
        val job =
            launch {
                while (true) {
                    println("While in ${Thread.currentThread().name}")
                }
            }
        // job.join() 으로 강제 실행되지가 않아서
        job.cancel() // 실행 전 바로 종료
    }
