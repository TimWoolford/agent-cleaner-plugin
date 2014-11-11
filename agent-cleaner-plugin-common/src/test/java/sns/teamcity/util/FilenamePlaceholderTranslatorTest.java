package sns.teamcity.util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FilenamePlaceholderTranslatorTest {
    private FilenamePlaceholderTranslator translator;

    @Before
    public void setUp() throws Exception {
        translator = new FilenamePlaceholderTranslator();
    }

    @Test
    public void doesNotTranslateWhenNoPlaceholders() throws Exception {
        String original = "I am Groot";

        String translated = translator.apply(original);

        assertThat(translated, is(original) );
    }

    @Test
    public void replaceSinglePlaceholder() throws Exception {
        System.setProperty("foo", "bar");

        String translate = translator.apply("Sheep goes ${foo}");

        assertThat(translate, is("Sheep goes bar"));
    }

    @Test
    public void replaceMultiplePlaceholders() throws Exception {
        System.setProperty("foo", "drugs");
        System.setProperty("bar", "worse");

        String translate = translator.apply("The ${foo} don't work, they just make me ${bar}");

        assertThat(translate, is("The drugs don't work, they just make me worse"));
    }

    @Test
    public void replaceRepeatedPlaceholders() throws Exception {
        System.setProperty("foo", "round");

        String translate = translator.apply("The wheels on the bus go ${foo} and ${foo}");

        assertThat(translate, is("The wheels on the bus go round and round"));
    }
}