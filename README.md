# Record-based file libraries for different languages

In some industries (mainly airline, bank, others welcome), a lot of files are exchanged using a record
organization. Usually, the file is plain vanilla ASCII file where each line is mapped to a record, and
each record to a field within this record. Record-oriented files are very popular on mainframes coming from Cobol programs.

The way to recognize a record is generally by having a record identifier (example: first 2 characters
of the line). Each record identifier defines the type of the record and how it is organized.
Each record is a set of contiguous or non-contiguous fields, each field having a length (in chars), a type (either 
representing an ascii or numeric field) and an offset from the beginning of the record.

This Java library allows to loop through all the records, loop through all fields of a record, using Java simple classes.
The definition (layout) of the file structure must be provided through an XML definition file.

An example of such a file is the SSIM (chapter 7) file. You can find an example of such a file at https://github.com/sthonnard/ssimparser.

The corresponding layout file is:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<rof
    xmlns="http://www.w3schools.com"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.w3schools.com rbf.xsd"
>

    <meta version="2011" description="IATA SSIM file format" reclength="200"/>

    <fieldtype type="AN" class="string" pattern=".*" format="%-*.*s"/>
	<fieldtype type="A" class="string"  pattern="[A-Za-z]+" format="%-*.*s"/>
	<fieldtype type="N" class="decimal" pattern="^[\d\.]+$" format="%0*.*g"/>
	<fieldtype type="I" class="integer" pattern="^\d+$" format="%0*.*d"/>
	<fieldtype type="D" class="date" pattern="[0-9]+" format="%Y%m%d"/>
	<fieldtype type="T" class="time" pattern="[0-9]+" format="%H%M"/>

	<record name="0" description="Decoration">
		<field name="DECO" description="Filled with 0" start="1" end="200" type="AN"/>
	</record>

	<record name="1" alias="HEADER" description="Header Record">
		<field name="RTYP" description="Record Type" start="1" end="1" type="N"/>
		<field name="TICO" description="Table of Contents" start="2" end="35" type="AN"/>
		<field name="SPAR" description="Spare" start="35" end="40" type="A"/>
		<field name="NSEA" description="Number of Seasons" start="41" end="41" type="N"/>
		<field name="SPAR" description="Spare" start="42" end="191" type="A"/>
		<field name="RESN" description="Record Serial Number" start="195" end="200" alias="COUNTER" type="N"/>
	</record>

	<record name="2" alias="CARRIER" description="Carrier Record">
		<field name="RTYP" description="Record Type" start="1" end="1" type="N"/>
		<field name="TMOD" description="Time Mode" start="2" end="2" type="A"/>
		<field name="ADES" description="Airline Designator" start="3" end="5" type="AN"/>
		<field name="SPAR" description="Spare" start="6" end="10" type="A"/>
		<field name="SEAS" description="Season" start="11" end="13" type="N"/>
		<field name="SPAR" description="Spare" start="14" end="14" type="A"/>
		<field name="POSV" description="Period of Schedule Validity" start="15" end="28" type="AN"/>
		<field name="CDAT" description="Creation Date" start="29" end="35" type="D"/>
		<field name="TDAT" description="Title of Data" start="36" end="64" type="AN"/>
		<field name="RDAT" description="Release Date" start="65" end="71" type="D"/>
		<field name="RSTA" description="Release Status" start="72" end="72" type="A"/>
		<field name="CREF" description="Creator Reference" start="73" end="107" type="AN"/>
		<field name="DADM" description="Duplicate Airline Designator Marker" start="108" end="108" type="A"/>
		<field name="GINF" description="General Information" start="109" end="169" type="AN"/>
		<field name="IFSI" description="In-Flight Service Information" start="170" end="188" type="AN"/>
		<field name="ETIN" description="Electronic Ticketing Information" start="189" end="190" type="A"/>
		<field name="CTIM" description="Creation Time" start="191" end="194" type="T"/>
		<field name="RESN" description="Record Serial Number" start="195" end="200" type="N"/>
	</record>

	<record name="3" alias="FLIGHT" description="Flight Leg Record">
		<field name="RTYP" description="Record Type" start="1" end="1" type="N"/>
		<field name="ALDS" description="Airline Designator" start="3" end="5" type="A"/>
		<field name="FNUM" description="Flight Number" start="6" end="9" type="N"/>
		<field name="IVID" description="Itinerary Variation Number" start="10" end="11" type="N"/>
		<field name="LESN" description="Leg Sequence Number" start="12" end="13" type="N"/>
		<field name="STYP" description="Service Type" start="14" end="14" type="AN"/>
		<field name="POPE" description="Period of Operation" start="15" end="28" type="AN"/>
		<field name="DOPE" description="Days of Operation" start="29" end="35" type="AN"/>
		<field name="FRAT" description="Frequency Rate" start="36" end="36" type="A"/>
		<field name="DSTA" description="Departure Station" start="37" end="39" type="AN"/>
		<field name="STPD" description="Schedule Time of Passenger Departure" start="40" end="43" type="AN"/>
		<field name="STAD" description="Schedule Time of Aircraft Departure" start="44" end="47" type="AN"/>
		<field name="ULTV" description="UTC/Local Time Variation" start="48" end="52" type="T"/>
		<field name="PTDS" description="Passenger Terminal for Departure Station" start="53" end="54" type="AN"/>
		<field name="ASTA" description="Arrival Station" start="55" end="57" type="AN"/>
		<field name="STAA" description="Scheduled Time of Aircraft Arrival" start="58" end="61" type="AN"/>
		<field name="STPA" description="Scheduled time of Passenger Arrival" start="62" end="65" type="AN"/>
		<field name="ULTV" description="UTC/Local Time Variation" start="66" end="70" type="AN"/>
		<field name="PTAS" description="Passenger Terminal for arrival station" start="71" end="72" type="AN"/>
		<field name="ATYP" description="Aircraft Type" start="73" end="75" type="AN"/>
		<field name="PRBD" description="Passenger Reservations Booking Designator" start="76" end="95" type="AN"/>
		<field name="PRBM" description="Passenger Reservations Booking Modifier" start="96" end="100" type="AN"/>
		<field name="MSNO" description="Meal Service Note" start="101" end="110" type="AN"/>
		<field name="JOAD" description="Joint Operation Airline Designators" start="111" end="119" type="AN"/>
		<field name="MCTS" description="Minimum Connecting Time International/Domestic Status" start="120" end="121" type="AN"/>
		<field name="SFIN" description="Secure Flight Indicator" start="122" end="122" type="AN"/>
		<field name="SPAR" description="Spare" start="123" end="127" type="AN"/>
		<field name="IVIO" description="Itinerary Variation Identifier Overflow" start="128" end="128" type="AN"/>
		<field name="AOWN" description="Aircraft Owner" start="129" end="131" type="AN"/>
		<field name="CCEM" description="Cockpit Crew Employer" start="132" end="134" type="AN"/>
		<field name="CCEP" description="Cabin Crew Employer" start="135" end="137" type="AN"/>
		<field name="ADES" description="Airline Designator" start="138" end="140" type="AN"/>
		<field name="FNUM" description="Flight Number" start="141" end="144" type="AN"/>
		<field name="ARLO" description="Aircraft Rotation Layover" start="145" end="145" type="AN"/>
		<field name="OSUF" description="Operational Suffix" start="146" end="146" type="AN"/>
		<field name="SPAR" description="Spare" start="147" end="147" type="AN"/>
		<field name="FTLO" description="Flight Transit Layover" start="148" end="148" type="AN"/>
		<field name="OADC" description="Operating Airline Disclosure" start="149" end="149" type="AN"/>
		<field name="TRCO" description="Traffic Restriction Code" start="150" end="160" type="AN"/>
		<field name="TRCL" description="Traffic Restriction Code Leg Overflow Indicator" start="161" end="161" type="AN"/>
		<field name="SPAR" description="Spare" start="162" end="172" type="AN"/>
		<field name="ACVE" description="Aircraft Configuration/Version" start="173" end="192" type="AN"/>
		<field name="DVAR" description="Date Variation" start="193" end="194" type="AN"/>
		<field name="RESN" description="Record Serial Number" start="195" end="200" type="N"/>
	</record>	

	<record name="4" alias="SEGMENT" description="Segment Data Record">
		<field name="RTYP" description="Record Type" start="1" end="1" type="N"/>
		<field name="OSUF" description="Operational Suffix" start="2" end="2" type="AN"/>
		<field name="ALDS" description="Airline Designator" start="3" end="5" type="AN"/>
		<field name="FNUM" description="Flight Number" start="6" end="9" type="N"/>
		<field name="IVID" description="Itinerary Variation Identifier" start="10" end="11" type="AN"/>
		<field name="LGSN" description="Leg Sequence Number" start="12" end="13" type="AN"/>
		<field name="STYP" description="Service Type" start="14" end="14" type="AN"/>
		<field name="SPAR" description="Spare" start="15" end="27" type="AN"/>
		<field name="IVIO" description="Itinerary Variation Identifier Overflow" start="28" end="28" type="AN"/>
		<field name="BOIN" description="Board Point Indicator" start="29" end="29" type="AN"/>
		<field name="OPIN" description="Off Point Indicator" start="30" end="30" type="AN"/>
		<field name="DEID" description="Data Element Identifier" start="31" end="33" type="AN"/>
		<field name="BDPT" description="Board Point" start="34" end="36" type="AN"/>
		<field name="OFPT" description="Off Point" start="37" end="39" type="AN"/>
		<field name="DATA" description="Data" start="40" end="194" type="AN"/>
		<field name="RESN" description="Record Serial Number" start="195" end="200" type="N"/>
	</record>	

	<record name="5" alias="TRAILER" description="Trailer Record">
		<field name="RTYP" description="Record Type" start="1" end="1" type="N"/>
		<field name="SPAR" description="Spare" start="2" end="2" type="A"/>
		<field name="ADES" description="Airline Designator" start="3" end="5" type="AN"/>
		<field name="RDAT" description="Release Date" start="6" end="12" type="D"/>
		<field name="SPAR" description="Spare" start="13" end="187" type="A"/>
		<field name="SNCR" description="Serial Number Check Reference" start="188" end="193" type="N"/>
		<field name="CCOD" description="Continuation/End Code" start="194" end="194" type="A"/>
		<field name="RESN" description="Record Serial Number" start="195" end="200" type="N"/>
	</record>

</rof>
```

It's largely inspired from the ISR file format for provided by ATPCO.

## Usage
Following is a Java code snippet to use the *jrof* library:

```java
// open the layout
try {
    var layout = new Layout("ssim.xml");

    // create a reader to get records from the rof
    var rdr = new Reader("ssim.sample", layout);

    // set the mapper function: it maps a line read from the record-oriented file to a record name
    // in the XML layout
    rdr.setMapper(x -> x.substring(0, 1));

    // Now loop throw the records
    for (Record rec : rdr) {
        // record "0" is not interesting in the SSIM file
        if (rec.getName().equals("0")) continue;

        // print out record name
        System.out.printf("found record: %s", rec.getName());
    }
} catch (IOException e) {
    System.out.printf("I/O error <%s> when opening file <%s>", e, xml);
} catch (Exception e) {
    System.out.printf("error <%s> when opening file <%s>", e, xml);
}
```

## Building the library
To build and test the library, just type:

```console
$ ./gradlew build --info
```

The library is located at: _./lib/build/libs/jrof-0.1.0.jar_

## Validating the XML layout
```console
$ xmllint --schema rof.xsd my_layout.xml --noout
```