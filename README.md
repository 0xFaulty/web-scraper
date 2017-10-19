"# web-scraper" 

Data scraping used for extracting data from websites.

Build:
- mvn package
	
Use:
- java -jar scraper-full.jar uriFile.txt my,words,here -v -w -c -e
- java -jar scraper-full.jar https://www.google.com my,words,here -v -w -c -e
	
Flags:
- -v - Count data get and data process time in ms.
- -w - Count number of provided word(s) occurrence on webpage(s).
- -c - Count number of characters of each web page.
- -e - Printing sentences’ which contain given words.
	
