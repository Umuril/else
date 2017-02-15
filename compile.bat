mkdir -p bin
cp resources/* bin
cp -r lib bin/
javac -d bin -cp "lib/*" -sourcepath src src/com/ELSE/Main.java
