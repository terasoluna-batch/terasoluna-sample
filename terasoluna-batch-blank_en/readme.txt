*******************************************************************************
      TERASOLUNA Batch Framework for Java
      Procedure manual for implementing blank project

      Copyright 2015 NTT DATA Corporation.
*******************************************************************************

Brief

  This README is the procedure manual for using the blank project of TERASOLUNA Batch Framework for Java.
  By using this blank project an application development environment using TERASOLUNA Framework can be created.

Preconditions

  It is required that folloing softwares are installed in the development environment.
  The behavior has been confirmed for the version written on the right, however this does not imply limiting behavior in      environments other than the versions stated below.
  See the manuals on web if for installation as well as configuration procedure for further details.

   - Java SE Runtime Environment Standard Edition 1.7.0
   - Eclipse SDK 3.7.2 
   - PostgreSQL Database Server 8.4
     or
   - Oracle11g

How to import the project

  1. Unzip the zip file
     Unzip terasoluna-batch4j-blank-(version number).zip to any arbitrary location.
     Confirm that the unzipped folder name of terasoluna-batch4j-blank-(version number).zip is "terasoluna-batch-blank".
     Change the name into "terasoluna-batch-blank" if it was "terasoluna-batch4j-blank-(version number)". 

   2. Import to Eclipse
      (1) Open Eclipse and select "Import" from the "File" menu.
      (2) The import dialog will appear. From the list of import options, select "Existing Maven Projects"
          and then click "Next".
      (3) In the new dialog, click the "Browse" button on the right of "Root directory".
      (4) Click on the "Finish" button at the bottom of the dialog
          after confirming "/pom.xml xxxxxx.yyyyyy.zzzzzz:terasoluna-batch-blank:(version number).jar"
          check box is checked
-------------------------------------------------------------------------------
Copyright 2015 NTT DATA Corporation.
