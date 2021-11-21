package com.github.dandyvica.jrof;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.io.BufferedReader;
import java.lang.UnsupportedOperationException;
import java.io.IOException;
import java.util.NoSuchElementException;

import com.github.dandyvica.jrof.Layout;
import com.github.dandyvica.jrof.Record;

/**
 * Allow to iterate through all records of a record-oriented file.
 */
public class Reader implements Iterable<Record> {

    /**
     * The file to read
     */
    private String recordOrientedFile;

    /**
     * The layout which describers the file structure
     */
    private Layout layout;

    /**
     * The bufferead reader use to read each line of the file
     */
    private BufferedReader br;

    /**
     * The function type to map a line read from the input file into a record name
     */
    public interface RecordMapper {
        String mapper(String line);
    }

    /**
     * The function (of type <tt>RecordMapper</tt>) to map a line read from the
     * input file into a record name
     */
    private RecordMapper mapper;

    /**
     * constructor
     * 
     * @param rof    record-oriented file to read
     * @param layout the layout describing the structure of the record-oriented file
     */
    public Reader(String rof, Layout layout) throws FileNotFoundException {
        recordOrientedFile = rof;
        this.layout = layout;

        // open file for reading
        br = new BufferedReader(new FileReader(rof));
    }

    /**
     * Defines the function used to map a line to a record name
     */
    public void setMapper(RecordMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Closes the bufreader associated to the record-oriented file
     */
    public void close() {
        try {
            br.close();
        } catch (IOException e) {
            System.err.printf("unexpected IO error <%e> when closing file <%s>", e, recordOrientedFile);
            System.exit(1);
        }
    }

    /**
     * Iterator to read records from a record-oriented file
     */
    @Override
    public Iterator<Record> iterator() {
        Iterator<Record> it = new Iterator<Record>() {

            // the line currently read
            String line;

            @Override
            public boolean hasNext() {
                try {
                    // read line
                    line = br.readLine();

                    // if EOF, no more to read: close the bufreader
                    if (line == null) {
                        Reader.this.close();
                    } 

                    return line != null;
                } catch (IOException e) {
                    // unexpected read error
                    return false;
                }
            }

            @Override
            public Record next() {
                // identify the record in the line
                var recordName = mapper.mapper(line);

                // now get record: check if record is present, if not stop iteration
                if (layout.containsRecord(recordName)) {
                    Record record = layout.get(recordName);
                    record.setValue(line);

                    return record;
                } else {
                    Reader.this.close();
                    throw new NoSuchElementException(String.format("record %s is not found", recordName));
                }
            }

            @Override
            @Deprecated
            public void remove() {
                Reader.this.close();
                throw new UnsupportedOperationException("You can't remove a line from this reader !");
            }
        };
        return it;
    }

}
