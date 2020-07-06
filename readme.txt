SimpleWebCrawler is the console application, that traverses websites following predefined link depth (8 by default) and max visited pages limit (10000 by default). Web crawler
	starts from predefined URL (seed) and follows links found to dive deeper. The main purpose of this crawler to detect the presence of some terms on the page and collect statistics.
	The seed and terms are defined in project.properties file.
	Start configuration is following: The urlseed is https://en.wikipedia.org/wiki/Elon_Musk, and terms are Tesla, Musk, Gigafactory, Elon Mask.
	After starting the application, information about the visited site and statistics is displayed on the console. Also, all information found and top ten are  recorded in files with the csv extention.