package study

import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class DslTest {
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
            }
        person.name shouldBe "홍길동"
        person.company shouldBe "다음"
    }

    @Test
    fun skills() {
        val person =
            introduce {
                name("홍길동")
                company("다음")
                skills {
                    soft("A passion for problem solving")
                    soft("Good communication skills")
                    hard("Kotlin")
                }
            }
        person.name shouldBe "홍길동"
        person.company shouldBe "다음"
        person.soft shouldContainAll listOf("A passion for problem solving", "Good communication skills")
        person.hard shouldContainAll listOf("Kotlin")
    }

    @Test
    fun languages() {
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
        person.name shouldBe "홍길동"
        person.company shouldBe "다음"
        person.soft shouldContainAll listOf("A passion for problem solving", "Good communication skills")
        person.hard shouldContainAll listOf("Kotlin")
        person.languages["Korean"] shouldBe 5
        person.languages["English"] shouldBe 3
    }
}

private fun introduce(block: PersonBuilder.() -> Unit): Person {
    return PersonBuilder().apply { block() }.build()
}

class PersonBuilder(
    var name: String = "",
    var company: String = "",
    var soft: MutableList<String> = mutableListOf(),
    var hard: MutableList<String> = mutableListOf(),
    var languages: MutableMap<String, Int> = mutableMapOf(),
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

    infix fun String.level(levelValue: Int) {
        languages[this] = levelValue
    }

    fun build(): Person {
        return Person(name, company, soft, hard, languages)
    }
}

class Person(
    val name: String,
    val company: String,
    val soft: List<String>,
    val hard: List<String>,
    val languages: Map<String, Int>,
)
