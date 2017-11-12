
build:
	find src/ -type f -name "*.java" -exec javac -d ./class {} \;

run:
	java -cp ./class StockMarketServer		
