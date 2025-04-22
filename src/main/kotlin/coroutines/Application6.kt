package coroutines

import kotlinx.coroutines.delay
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
        delay(1000L)
        job.cancel() // 여기서 명령을 보낼 방법이 없음...
    }
