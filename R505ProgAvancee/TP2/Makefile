JFLAGS=-g
JC=javac
JVM=java

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES=exo2.Main.java exo2.Affichage.java

MAIN=exo2.Main

default:classes

classes: $(CLASSES:.java=.class)

run:$(MAIN).class
	$(JVM) $(MAIN)

clean:
	rm *.class