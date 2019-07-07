/*
 * Copyright © 2017 Stefan Niederhauser (nidin@gmx.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:JvmName(name = "BigBanners")

package guru.nidi.bigbanners

import com.github.lalyos.jfiglet.FigletFont
import java.io.IOException
import kotlin.math.min

private val cl = Thread.currentThread().contextClassLoader!!

private val categories = readCategories()

fun categories() = categories.keys

fun entries(category: String) = categories[category]?.keys ?: emptySet()

fun fonts(category: String, entry: String) = categories[category]?.get(entry) ?: emptySet()

fun fonts() = categories().flatMap { c -> entries(c).flatMap { e -> fonts(c, e) } }.toSortedSet()

fun proposals(name: String, list: Set<String>, count: Int) = list.sortedBy { levenshtein(it, name) }.take(count)

@Throws(IOException::class)
fun render(font: String, text: String) =
        cl.getResourceAsStream("jave/$font.flf").use {
            if (it == null) {
                throw IOException("font $font not found.")
            }
            FigletFont.convertOneLine(it, text)!!
        }

private fun readCategories(): Map<String, Map<String, Set<String>>> =
        cl.getResourceAsStream("jave/categoriestree.txt")!!.use { categories ->
            val res = mutableMapOf<String, MutableMap<String, MutableSet<String>>>()
            var category: MutableMap<String, MutableSet<String>>?
            var entries: MutableSet<String>? = null
            categories.reader().readLines()
                    .filter { it.isNotBlank() && !it.startsWith("#") }
                    .forEach { line ->
                        val catMatch = Regex("""\[(.*?)]\{.*?}""").matchEntire(line)
                        if (catMatch != null) {
                            val name = catMatch.groupValues[1]
                            val pos = name.indexOf('|')
                            if (pos >= 0) {
                                category = res.getOrPut(name.substring(0, pos)) { mutableMapOf() }
                                entries = category!!.getOrPut(name.substring(pos + 1)) { mutableSetOf() }
                            }
                        } else {
                            entries!!.add(line)
                        }
                    }
            res
        }

private fun levenshtein(s1: String, s2: String): Int {
    val edits = Array(s1.length + 1) { IntArray(s2.length + 1) }
    for (i in 0..s1.length) edits[i][0] = i
    for (i in 1..s2.length) edits[0][i] = i
    for (i in 1..s1.length) {
        for (j in 1..s2.length) {
            val u = if (s1[i - 1] == s2[j - 1]) 0 else 1
            edits[i][j] = min(edits[i - 1][j] + 1, min(edits[i][j - 1] + 1, edits[i - 1][j - 1] + u))
        }
    }
    return edits[s1.length][s2.length]
}

