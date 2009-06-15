#!/usr/bin/python
import os

def build():
	os.system("rm *.class")
	os.system("javac *.java")
	os.system("java TomTest")
	print "Rebuilt and executed"

build()	
