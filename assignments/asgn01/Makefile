SHELL = /bin/sh
PDFLATEX = pdflatex
BIBER = biber

# Name of the report file
MAIN = report

# Latex file that will be compiled
TEXFILES = $(MAIN).tex 
# References file if you use biber
BIBFILES = references.bib 

# Put any images/assets you reference in here
ASSETS = assets/*

.PHONY: all pdf bib clean allclean

all: pdf

pdf: $(TEXFILES) $(ASSETS)
	$(PDFLATEX) $(MAIN)
	$(PDFLATEX) $(MAIN)

bib: $(TEXFILES) $(BIBFILES) $(ASSETS)
	$(PDFLATEX) $(MAIN)
	$(BIBER) $(MAIN)
	$(PDFLATEX) $(MAIN)
	$(PDFLATEX) $(MAIN)

clean:
	rm -f *.log *.aux *.out *.bcf *.bbl *.blg *.xml *.toc *.run.xml 

allclean: clean
	rm -f $(MAIN).pdf


