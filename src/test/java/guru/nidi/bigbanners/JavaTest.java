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
package guru.nidi.bigbanners;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaTest {
    @Test
    @Disabled
    void demo() {
        for (String font : BigBanners.fonts()) {
            try {
                System.out.println(font);
                System.out.println(BigBanners.render(font, "Big Banners"));
            } catch (Exception e) {
            }
        }
    }

    @Test
    void render() {
        assertEquals("" +
                        "    _     ___    ___ \n" +
                        "   /_\\   | _ )  / __|\n" +
                        "  / _ \\  | _ \\ | (__ \n" +
                        " /_/ \\_\\ |___/  \\___|\n" +
                        "                     \n",
                BigBanners.render("small", "ABC"));
    }

    @Test
    void categories() {
        assertEquals(new HashSet<>(asList("Style", "Linecount", "Year", "Author")),
                BigBanners.categories());
    }

    @Test
    void entries() {
        assertEquals(new HashSet<>(asList("3D", "Banner", "Block", "Contrast", "Framed", "Line", "Script", "Outline",
                "Pattern", "Shadow", "Solid", "Turned", "Dingbats", "Codes", "Not sorted")),
                BigBanners.entries("Style"));
    }

    @Test
    void fonts() {
        assertEquals(new HashSet<>(asList("eftichess", "eftiwall", "hieroglyphs")),
                BigBanners.fonts("Style", "Dingbats"));
    }

    @Test
    void allFonts() {
        assertEquals(263, BigBanners.fonts().size());
    }

    @Test
    void proposals() {
        assertEquals(asList("basic", "big", "cosmic", "epic", "italic"),
                BigBanners.proposals("basic", BigBanners.fonts(), 5));
    }
}
