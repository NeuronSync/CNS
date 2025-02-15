compile:
    javac -cp lib/*:src src/com/university/Main.java -d out/

run:
    java -cp lib/*:out com.university.Main

db-setup:
    mysql -u root -p < resources/sql/schema.sql
    mysql -u root -p < resources/sql/sampledata.sql
