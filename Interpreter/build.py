#!/usr/bin/python
import os

def build():
	os.system("jjtree VizParser.jjt")
	os.system("javacc VizParser.jj")
	os.system("javac VizParser.java")
#	os.system("java VizParser < testProg.src")
	print "Rebuilt and executed"

build()	
