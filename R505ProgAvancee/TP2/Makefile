JFLAGS=-g
JC=javac
JVM=java

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES=exo2.Main2.java exo2.Affichage.java

MAIN=exo2.Main2

default:classes

classes: $(CLASSES:.java=.class)

run:$(MAIN).class
	$(JVM) $(MAIN)

clean:
	rm *.class