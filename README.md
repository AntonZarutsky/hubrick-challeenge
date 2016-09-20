# hubrick-challenge

Application is designed to process employees data and generate corresponding reports.

File examples are represented in the following folder: 

    hubrick-backend-challenge/data


There are several assumptions were made in this challenge:
- Assume that files can contains wrong data (also in wrong format), duplicate data
- If Employee was not properly parsed (inc. cases as wrong department, no/bad age), this record are taken out of report generation and information about it put into <b>errors.log</b> file  
- Some handy 3rd party libraries were not used by the challenge conditions and were replaced with simplified analogs(self-made ones)
    as ex. com.fasterxml.jackson.dataformat:jackson-dataformat-csv is nice to work with csv
- Console is not used to show all information about application work. 
  Instead of that, errors are written in separate file <PATH-TO-FOLDER-WITH-DATA>/out/errors.log  
- Every developer know what percentile means and how to work with it. But what's about the proper algorthytm, how to calculate it.
  Honestly I "googled" for it and found out at least several variations which provide close but still a bit different results.
  I've choosen on of them.
  Implementation is under ReportService.percentile

Comments to code:
- Somewhere in code you can find static imports, which somebody like, somebody not really.
  Sometimes I find its handy to make code cleaner.
- I used to  use javax.annotation.Nonnull/Nullable to provide method/api contracts. but there are not a part of jdk.  
- Also I was missed about lombok and guava libraries, which could make code more nicer.
- In some places I've used BigDecimal for more precise results and comparing double values. I also notices that not everybody is cool with it.
- I've put some comments for better readability


Additional Features:
- errors logged in separate file
- you can use ~ in path parameter to provide relative to user home folder path.
- ReportService designed in the way to be able to generate reports with different groupings and aggregating different values.
- ReportService also allow calculte different percentiles.
  


To build application run:
    
    mvn clean install


To execute application run:
    
    java -jar target/hubrick-challenge.jar <PATH-TO-FOLDER-WITH-DATA>


