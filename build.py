#!/usr/bin/python
import os

def build():
	os.system("jjtree Interpreter/VizParser.jjt")
	os.system("javacc Interpreter/VizParser.jj")
	os.system("javac Interpreter/VizParser.java")
#	os.system("java VizParser < testProg.src")
	print "Rebuilt and executed"

build()	
