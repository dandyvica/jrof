/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.dandyvica.jrof.Atom;

@DisplayName("Atom class tests")
class AtomTest {

    @Test
    @DisplayName("constructor")
    void cons() {
        var atom = new Atom("ELEM1", "Element description", "HYDROGEN", "H", "gas");

        assertThat(atom.getName()).isEqualTo("ELEM1");
        assertThat(atom.getDescription()).isEqualTo("Element description");
        assertThat(atom.getAlias()).isEqualTo("HYDROGEN");
        assertThat(atom.getId()).isEqualTo("H");
        assertThat(atom.getType()).isEqualTo("gas");
    }

    @Test
    @DisplayName("copy constructor")
    void consCopy() {
        var atom = new Atom("ELEM1", "Element description", "HYDROGEN", "H", "gas");
        var copied = new Atom(atom);

        assertThat(atom.getName()).isEqualTo("ELEM1");
        assertThat(atom.getDescription()).isEqualTo("Element description");
        assertThat(atom.getAlias()).isEqualTo("HYDROGEN");
        assertThat(atom.getId()).isEqualTo("H");
        assertThat(atom.getType()).isEqualTo("gas");
    }
}
