package study

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

data class UserInfo(val name: String, val lastName: String, val id: Int)

lateinit var user: UserInfo

fun main() {
    runBlocking {
        asyncGetUserInfo(1)
        delay(1000)
        println("User ${user.id} is ${user.name} ${user.lastName}")
    }
}

suspend fun asyncGetUserInfo(id: Int) {
    GlobalScope.async {
        delay(1100)
        user = UserInfo("Jason", "Park", id)
    }
}

/** 예외 발생! */
