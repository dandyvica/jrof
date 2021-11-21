/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.util.Lists.list;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.dandyvica.jrof.Field;
import com.github.dandyvica.jrof.Record;
import jrof.test.Util;

@DisplayName("Record class tests")
class RecordTest {

    Record sample() throws Exception {
        var xml = "<record name='REC' description='Record header' alias='RECT'/>";
        var e = Util.fromXML(xml);
        var r = new Record(e);

        r.add(new Field(
                Util.fromXML("<field name='FIELD1' description='Field description' start='1' length='1' type='AN'/>")));
        r.add(new Field(
                Util.fromXML("<field name='FIELD2' description='Field description' start='2' length='1' type='AN'/>")));
        r.add(new Field(
                Util.fromXML("<field name='FIELD3' description='Field description' start='3' length='1' type='AN'/>")));
        r.add(new Field(
                Util.fromXML("<field name='FIELD1' description='Field description' start='4' length='1' type='AN'/>")));
        r.add(new Field(
                Util.fromXML("<field name='FIELD2' description='Field description' start='5' length='1' type='AN'/>")));

        return r;
    }

    @Test
    @DisplayName("constructor from XML fragment")
    void consXML() throws Exception {
        // with no end attribute
        var xml = "<record name='REC' description='Record header' alias='RECT'/>";
        var e = Util.fromXML(xml);
        var r = new Record(e);

        assertThat(r.getName()).isEqualTo("REC");
        assertThat(r.getDescription()).isEqualTo("Record header");
        assertThat(r.getAlias()).isEqualTo("RECT");
    }

    @Test
    @DisplayName("copy constructor")
    void copyCons() throws Exception {
        var r = sample();
        var copied = new Record(r);

        assertThat(copied.getName()).isEqualTo("REC");
        assertThat(copied.getDescription()).isEqualTo("Record header");
        assertThat(copied.getAlias()).isEqualTo("RECT");
        assertThat(copied.size()).isEqualTo(5);
        assertThat(copied).allMatch(f -> f.getLength() == 1);
        assertThat(copied).allMatch(f -> f.getType().equals("AN"));
        assertThat(copied).allMatch(f -> f.getName().substring(0, 5).equals("FIELD"));
    }

    @Test
    @DisplayName("add fields")
    void add() throws Exception {
        var r = sample();

        // get
        var f = r.get(0);
        assertThat(f.getName()).isEqualTo("FIELD1");
        // assertThat(r.get(10)).isInstanceOf(IndexOutOfBoundsException.class);

        // get
        var list = r.get("FIELD2");
        assertThat(list.size()).isEqualTo(2);
        assertThat(r.get("FOO")).isNull();

        // size
        assertThat(r.size()).isEqualTo(5);

        assertThat(r.containsField("FIELD1")).isTrue();
        assertThat(r.containsField("FIELD2")).isTrue();
        assertThat(r.containsField("FIELD3")).isTrue();
        assertThat(r.containsField("FIELD4")).isFalse();
    }

    @Test
    @DisplayName("record length")
    void length() throws Exception {
        var r = sample();

        assertThat(r.length()).isEqualTo(5);
    }

    @Test
    @DisplayName("wrong index")
    void wrongIndex() throws Exception {
        var r = sample();
    
        // raise excp
        Throwable thrown = catchThrowable(() -> r.get(10));
        assertThat(thrown).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    @DisplayName("iterator")
    void iterator() throws Exception {
        var r = sample();

        assertThat(r).hasSize(5);
        assertThat(r).allMatch(f -> f.getLength() == 1);
        assertThat(r).allMatch(f -> f.getType().equals("AN"));
        assertThat(r).allMatch(f -> f.getName().substring(0, 5).equals("FIELD"));
    }

    @Test
    @DisplayName("stream")
    void stream() throws Exception {
        var r = sample();
        var tab = r.stream().map(f -> f.getName()).collect(Collectors.toList());

        assertThat(tab).contains("FIELD1", "FIELD2", "FIELD3");        

    }

    @Test
    @DisplayName("value")
    void value() throws Exception {
        var r = sample();

        var s = "ABCDE";
        r.setValue(s);

        assertThat(r.get(0).getValue()).isEqualTo("A");
        assertThat(r.get(1).getValue()).isEqualTo("B");
        assertThat(r.get(2).getValue()).isEqualTo("C");
        assertThat(r.get(3).getValue()).isEqualTo("D");
        assertThat(r.get(4).getValue()).isEqualTo("E");
    }    

}