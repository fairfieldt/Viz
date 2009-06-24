#!/usr/bin/python
import os

def build():
	os.system("rm Interpreter/*.class")
	os.system("rm Viz/*.class")
	os.system("jjtree -OUTPUT_DIRECTORY=Interpreter Interpreter/VizParser.jjt")
	os.system("javacc -OUTPUT_DIRECTORY=Interpreter Interpreter/VizParser.jj")
	os.system("javac Interpreter/VizParser.java")
	os.system("javac viz/NewTest.java")
#	os.system("java VizParser < testProg.src")
	print "Rebuilt"

build()	
