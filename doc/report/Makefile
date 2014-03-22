default_targets=article

article_includes=
article_figures=

presentation_includes=
presentation_figures=

CONVERT=convert
#CONVERTFLAGS=
LATEX=latex
#LATEXFLAGS=
PDFLATEX=pdflatex
#PDFLATEXFLAGS=
DVIPS=dvips
#DVIPSFLAGS=

%.eps: %.xpm
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.pdf: %.xpm
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.eps: %.xbm
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.pdf: %.xbm
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.eps: %.gif
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.pdf: %.gif
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.eps: %.jpg
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.pdf: %.jpg
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.eps: %.jpeg
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.pdf: %.jpeg
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.eps: %.png
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.pdf: %.png
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.eps: %.tiff
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.pdf: %.tiff
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.eps: %.tif
	$(CONVERT) $(CONVERTFLAGS) $< $@
%.pdf: %.tif
	$(CONVERT) $(CONVERTFLAGS) $< $@

%.dvi: %.tex
	$(LATEX) $(LATEXFLAGS) $<
	$(LATEX) $(LATEXFLAGS) $<

%.ps: %.dvi
	$(DVIPS) $(DVIPSFLAGS) $<

%.pdf: %.tex
	$(PDFLATEX) $(PDFLATEXFLAGS) $<
	$(PDFLATEX) $(PDFLATEXFLAGS) $<

.PHONY: all article presentation

all: $(default_targets)

article: article.dvi article.ps article.pdf
article.dvi: article.tex packages-common.tex packages-article.tex $(article_includes) $(addsuffix .eps,$(basename $(article_figures)))
article.ps: $(addsuffix .eps,$(basename $(article_figures)))
article.pdf: article.tex packages-common.tex packages-article.tex $(article_includes) $(addsuffix .pdf,$(basename $(article_figures)))

presentation: presentation.dvi presentation.ps presentation.pdf
presentation.dvi: presentation.tex packages-common.tex packages-presentation.tex $(presentation_includes) $(addsuffix .eps,$(basename $(presentation_figures)))
presentation.ps: $(addsuffix .eps,$(basename $(presentation_figures)))
presentation.pdf: presentation.tex packages-common.tex packages-presentation.tex $(presentation_includes) $(addsuffix .pdf,$(basename $(presentation_figures)))

clean:
	rm -f article.dvi presentation.dvi
	rm -f article.aux presentation.aux
	rm -f article.log presentation.log
	rm -f article.ps  presentation.ps
	rm -f article.pdf presentation.pdf
	rm -f article.out presentation.out
	rm -f article.snm presentation.snm
	rm -f article.toc presentation.toc
	rm -f article.nav presentation.nav
	rm -f article.bbl presentation.bbl
	rm -f article.blg presentation.blg
