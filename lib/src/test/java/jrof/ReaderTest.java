import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.w3c.dom.Element;

import static org.assertj.core.api.Assertions.*;

import com.github.dandyvica.jrof.Record;
import com.github.dandyvica.jrof.Layout;
import com.github.dandyvica.jrof.Reader;

@DisplayName("Reader class tests")
class ReaderTest {

    Reader sample() throws Exception {
        var l = new Layout("src/test/resources/ssim.xml");
        var rdr = new Reader("src/test/resources/ssim.sample", l);

        rdr.setMapper(x -> x.substring(0, 1));

        return rdr;
    }

    @Test
    @DisplayName("constructor")
    void cons() throws Exception {
        var rdr = sample();
    }

    @Test
    @DisplayName("iterator")
    void iterate() throws Exception {
        var rdr = sample();
        var i = 1;

        for (Record r: rdr) {
            // skip '0' record
            if (r.getName().equals("0")) continue;

            assertThat(r.getName()).isIn("1", "2", "3", "4", "5");

            var resn = r.get("RESN").get(0).getValue();
            assertThat(Integer.parseInt(resn)).isEqualTo(i);
            i++;
        }
    }
}
