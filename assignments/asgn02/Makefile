SHELL = /bin/sh
PDFLATEX = pdflatex

# Name of the report file
MAIN = report

# Latex file that will be compiled
TEXFILES = $(MAIN).tex 

.PHONY: all pdf bib clean allclean

all: pdf

pdf: $(TEXFILES)
	$(PDFLATEX) $(MAIN)
	$(PDFLATEX) $(MAIN)

clean:
	rm -f *.log *.aux *.out *.bcf *.bbl *.blg *.xml *.toc *.run.xml 

allclean: clean
	rm -f $(MAIN).pdf



