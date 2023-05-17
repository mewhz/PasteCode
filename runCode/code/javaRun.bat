cd E:/runCode/code
javac Main.java 2> java-error.txt
java Main < java-input.txt > java-output.txt 2>> java-error.txt
del Main.java
del Main.class