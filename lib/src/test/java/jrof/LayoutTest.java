/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import java.util.Map.Entry;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import com.github.dandyvica.jrof.Layout;

@DisplayName("Layout class tests")
class LayoutTest {

    @Test
    @DisplayName("constructor")
    void cons() throws Exception {
        var l = new Layout("src/test/resources/ssim.xml");

        assertThat(l.getDescription()).isEqualTo("IATA SSIM file format");
        assertThat(l.getRecLength()).isEqualTo(200);
        assertThat(l.getVersion()).isEqualTo("2011");
    }

    @Test
    @DisplayName("test size method")
    void size() throws Exception {
        var l = new Layout("src/test/resources/ssim.xml");

        assertThat(l.size()).isEqualTo(6);
    }

    @Test
    @DisplayName("if the layout contains a record")
    void containsRecord() throws Exception {
        var l = new Layout("src/test/resources/ssim.xml");

        assertThat(l.containsRecord("0")).isTrue();
        assertThat(l.containsRecord("1")).isTrue();
        assertThat(l.containsRecord("2")).isTrue();
        assertThat(l.containsRecord("3")).isTrue();
        assertThat(l.containsRecord("5")).isTrue();
        assertThat(l.containsRecord("6")).isFalse();
    }

    @Test
    @DisplayName("if the layout contains a field in any record")
    void containsField() throws Exception {
        var l = new Layout("src/test/resources/ssim.xml");

        assertThat(l.containsField("RTYP")).isTrue();
        assertThat(l.containsField("ADES")).isTrue();
        assertThat(l.containsField("SPAR")).isTrue();
        assertThat(l.containsField("IVIO")).isTrue();
        assertThat(l.containsField("RESN")).isTrue();
        assertThat(l.containsField("FOO")).isFalse();
    }

    @Test
    @DisplayName("get a record from layout")
    void get() throws Exception {
        var l = new Layout("src/test/resources/ssim.xml");
        var r = l.get("1");

        assertThat(r.size()).isEqualTo(6);
        assertThat(r.containsField("NSEA")).isTrue();
    }

    @Test
    @DisplayName("iterator")
    void iterator() throws Exception {
        var l = new Layout("src/test/resources/ssim.xml");

        assertThat(l.entrySet().stream().anyMatch(x -> x.getKey().equals("0"))).isTrue();
        assertThat(l.entrySet().stream().allMatch(x -> x.getKey().length() == 1)).isTrue();
    }

    @Test
    @DisplayName("isr test")
    void largeXml() throws Exception {
        var l = new Layout("src/test/resources/isr.xml");

        assertThat(l.entrySet().stream().allMatch(x -> x.getKey().length() == 2)).isTrue();
        assertThat(l.entrySet().stream().allMatch(x -> x.getValue().length() == 400)).isTrue();
        assertThat(l.entrySet().stream().allMatch(x -> x.getValue().size() >= 8)).isTrue();

        assertThat(l.containsRecord("01")).isTrue();
        assertThat(l.containsRecord("00")).isFalse();

        assertThat(l.containsField("RCID")).isTrue();
        assertThat(l.containsField("FOO")).isFalse();

        var r01 = l.get("01");
        assertThat(r01.containsField("CJCP")).isTrue();
        assertThat(r01.get("FVFT").size()).isEqualTo(10);
        assertThat(r01.size()).isEqualTo(82);
    }
}