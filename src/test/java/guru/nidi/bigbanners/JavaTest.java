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
