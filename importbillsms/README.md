# Import Bills Service

From local directory
D:\workspace\googlecloud\importbillspoc\importbillsms\target

java -DGOOGLE_APPLICATION_CREDENTIALS=D:\workspace\googlecloud\importbillspoc\config\googlecloud\CG-HSBC-PoC-484cfa11e0db.json -Dspring.profiles.active=default -jar ImportBillsService-1.0.jar
Googlecloud start
From directory /var/www/code/importbillsms/target

java -Dspring.profiles.active=googlecloud -jar ImportBillsService-1.0.jar
From directoy /var/www/code/importbillspoc/POCVisionAPIClientLib/target

java -jar cloudVisionApi-0.0.1-SNAPSHOT.jar