import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class DslTest {
//    @Test
//    fun name() {
//        val person: Person = introduce {
//            this.name("홍길동") // 이 this는 introduce 함수에서 정의를 해줘야 한다!!
//        }
//        person.name shouldBe "홍길동"  // shouldBe = 중위함수 ?!
//    }

    @ValueSource(strings = ["홍길동", "김철수"])
    @ParameterizedTest
    fun name(name: String) {
        val person =
            introduce {
                name(name)
            }
        person.name shouldBe name
    }

    @Test
    fun company() {
        val person =
            introduce {
                name("홍길동")
                company("다음")
                skills {
                    soft("A passion for problem solving")
                    soft("Good communication skills")
                    hard("Kotlin")
                }
                languages {
                    "Korean" level 5
                    "English" level 3
                }
            }

        person.name shouldBe "홍길동" // kotest
        person.company shouldBe "다음"
        person.soft shouldContainAll listOf("A passion for problem solving", "Good communication skills")
        person.hard shouldContainAll listOf("Kotlin")
        person.language["Korean"] shouldBe 5
        person.language["English"] shouldBe 3
    }
}

private fun introduce(block: PersonBuilder.() -> Unit): Person { // Person.() -> Person 클래스 내부 함수만 받게 된다 ?!
    // return Person().apply { block() }
    return PersonBuilder().apply(block).build()
}

/** '() -> Unit' = 결국 아무 함수나 올 수 있다 의 뜻임 !!
 *  input은 아무거나 가능, Unit (Java의 Void)*/

class PersonBuilder(
    var name: String = "",
    var company: String = "",
    val soft: MutableList<String> = mutableListOf(),
    val hard: MutableList<String> = mutableListOf(),
    val language: MutableMap<String, Int> = mutableMapOf(),
) {
    fun name(name: String) {
        this.name = name
    }

    fun company(company: String) {
        this.company = company
    }

    fun skills(block: PersonBuilder.() -> Unit) {
        block()
    }

    fun soft(skill: String) {
        this.soft.add(skill)
    }

    fun hard(skill: String) {
        this.hard.add(skill)
    }

    fun languages(block: PersonBuilder.() -> Unit) {
        block()
    }

    fun build(): Person {
        return Person(name, company, soft, hard, language)
    }

    infix fun String.level(levelValue: Int) {
        language[this] = levelValue
    }
}

/** PeronBuilder를 사용하면서 기존의 Person은 아래와 같은 형태로 변경 가능 */
class Person(
    val name: String,
    val company: String,
    val soft: List<String>,
    val hard: List<String>,
    val language: Map<String, Int>,
)
