package mydbproject;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
            e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC Driver Registered!");
        /// if you have a error in this part, check jdbc driver(.jar file)

        Connection connection = null;

        try {
            connection  = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/project_movie", "postgres", "cse3207");     
        
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        /// if you have a error in this part, check DB information (db_name, user name, password)

        if (connection != null) {
            System.out.println(connection);
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        	
        ResultSet rs = null;
        Statement stmt = connection.createStatement();
        //director
        stmt.executeUpdate("create table director" + 
              "   (directorID   integer ," + 
              "   directorName   varchar(20)not null," + 
              "   dateOfBirth varchar(15)," + 
              "   dateOfDeath varchar(15)," + 
              "   primary key(directorID))");
        //actor
        stmt.executeUpdate("create table actor" + 
        	  "  (actorID  integer," + 
              "   actorName varchar(30) not null," + 
              "   dateOfBirth varchar(15)," + 
              "   dateOfDeath varchar(15)," + 
              "   gender varchar(10)," +  
              "   primary key(actorID))");
        //movie
        stmt.executeUpdate("create table movie" + 
              "   (movieID integer," + 
              "   movieName varchar(50) not null," + 
              "   releaseYear integer," + 
              "   releaseMonth integer," + 
              "   releaseDate integer," + 
              "   publisherName varchar(50)," + 
              "   avgRate numeric(2,1),"+ "primary key(movieID))");
        //award
        stmt.executeUpdate("create table award (awardID integer, awardName varchar(30) not null, primary key(awardID) )");
        //genre
        stmt.executeUpdate("create table genre(genreName varchar(30)not null, primary key(genreName) )");   
        //movieGenre
        stmt.executeUpdate("create table movieGenre" + 
              " (movieID integer," + 
              " genreName varchar(30)not null," + 
              " primary key(movieID,genreName),"
              +"foreign key(movieID) references movie(movieID) on delete cascade,"
              +"foreign key(genreName) references genre(genreName) on delete cascade)");
                 
        //movieObtain
        stmt.executeUpdate("create table movieObtain( movieID integer, awardID integer, year integer," + 
              "   primary key(movieID,awardID),"
              + "foreign key(movieID) references movie(movieID) on delete cascade,"
              +"foreign key(awardID) references award(awardID) on delete cascade)");

        //actorObtain
        stmt.executeUpdate("create table actorObtain(actorID integer,  awardID integer, year integer, primary key(actorID,awardID)," + 
              "foreign key(actorID) references actor(actorID) on delete cascade," + 
              "foreign key(awardID) references award(awardID)on delete cascade)");
        
        
        //directorObtain
        stmt.executeUpdate("create table directorObtain( directorID integer," + 
              "awardID integer,year integer,  primary key(directorID, awardID)," + 
              "foreign key(directorID) references director(directorID) on delete cascade,"+
              "foreign key(directorID) references award(awardID) on delete cascade)");
        //casting
        stmt.executeUpdate("create table casting( movieID integer, actorID integer, role varchar(20)," + 
              "primary key(movieID, actorID)," + 
              "foreign key(movieID) references movie(movieID) on delete cascade,"
              + "foreign key(actorID) references actor(actorID) on delete cascade)");
        //make
        stmt.executeUpdate("create table make( movieID integer,  directorID integer,  primary key(movieId, directorID)," + 
              "foreign key(movieID) references movie(movieID) on delete cascade," + 
              "foreign key(directorID) references director(directorID) on delete cascade)");
        //customer
        stmt.executeUpdate("create table customer( customerID integer, customerName varchar(20), dateOfBirth varchar(15), gender varchar(10), primary key(customerID))");
        //customerRate
        stmt.executeUpdate("create table customerRate( customerID integer, movieID integer, rate integer, primary key(customerID, movieID)," + 
              "foreign key(customerID) references customer(customerID) on delete cascade,"
              + "foreign key(movieID) references movie(movieID) on delete cascade)");
        
        /////////////////************
        //insert
        ///////////////////////***********
        
        //director
        stmt.executeUpdate("insert into director values(1,'Tim Burton','1958.8.25',NULL)");
        stmt.executeUpdate("insert into director values(2,'David Fincher','1962.8.28',NULL)");
        stmt.executeUpdate("insert into director values(3,'Christoper Nolan','1970.7.30',NULL)");
        //actor
        stmt.executeUpdate("insert into actor values(1,'Johnny Depp','1963.6.9',NULL,'Male')");
        stmt.executeUpdate("insert into actor values(2,'Winona Ryder','1971.10.29',NULL,'Female')");
        stmt.executeUpdate("insert into actor values(3,'Anne Hathaway','1982.11.12',NULL,'Female')");
        stmt.executeUpdate("insert into actor values(4, 'Christian Bale','1974.1.30',NULL,'Male')");
        stmt.executeUpdate("insert into actor values(5,'Heath Ledger','1794.4.4','2008.1.22','Male')");
        stmt.executeUpdate("insert into actor values(6,'Jesse Eisenberg','1983.10.5',NULL,'Male')");
        stmt.executeUpdate("insert into actor values(7,'Andrew Garfield','1983.8.20',NULL,'Male')");
        stmt.executeUpdate("insert into actor values(8,'Fionn whitehead','1997.7.18',NULL,'Male')");
        stmt.executeUpdate("insert into actor values(9,'Tom Hardy','1977.9.15',NULL,'Male')");
        
        //movie(movieID, movieName,releaseYear,releaseMonth, releaseDate,publishrName,avgRate)
        stmt.executeUpdate("insert into movie values(1,'Edward Scissorhands',1991,06,29,"
              + "'20th Century Fox Presents',0.0)");
        stmt.executeUpdate("insert into movie values(2,'Alice In Wonderland',2010,03,04,"
              + "'Korea Sony Pictures',0.0)");
        stmt.executeUpdate("insert into movie values(3,'The Social Network',2010,11,18,"
              + "'Korea Sony Pictures',0.0)");
        stmt.executeUpdate("insert into movie values(4,'The Dark Knight',2008,08,06,"
              + "'Warner Brothers Korea',0.0)");
        stmt.executeUpdate("insert into movie values(5,'Dunkirk',2017,07,13,"
              + "'Warner Brothers Korea',0.0)");
        
        //customer
        stmt.executeUpdate("insert into customer values(1,'Bob','1997.11.14','Male')");
        stmt.executeUpdate("insert into customer values(2,'John','1978.01.23','Male')");
        stmt.executeUpdate("insert into customer values(3,'Jack','1980.05.04','Male')");
        stmt.executeUpdate("insert into customer values(4,'Jill','1981.04.17','Female')");
        stmt.executeUpdate("insert into customer values(5,'Bell','1990.05.14','Female')");
        
        //genre
        stmt.executeUpdate("insert into genre values('Fantasy')");
        stmt.executeUpdate("insert into genre values('Romance')");
        stmt.executeUpdate("insert into genre values('Adventure')");
        stmt.executeUpdate("insert into genre values('Family')");
        stmt.executeUpdate("insert into genre values('Drama')");
        stmt.executeUpdate("insert into genre values('Action')");
        stmt.executeUpdate("insert into genre values('Mystery')");
        stmt.executeUpdate("insert into genre values('Thriller')");
        stmt.executeUpdate("insert into genre values('War')");
        
        //movieGenre(movieID,genreName)
        stmt.executeUpdate("insert into movieGenre values(1,'Fantasy'),(1,'Romance')");
        stmt.executeUpdate("insert into movieGenre values(2,'Fantasy'),(2,'Adventure'),(2,'Family')");
        stmt.executeUpdate("insert into movieGenre values(3,'Drama')");
        stmt.executeUpdate("insert into movieGenre values(4,'Action'),(4,'Drama'),(4,'Mystery'),(4,'Thriller')");
        stmt.executeUpdate("insert into movieGenre values(5,'Action'),(5,'Drama'),(5,'Thriller'),(5,'War')");
        
        
                
        //casting(movieID,actorID,role)
        stmt.executeUpdate("insert into casting values(1,1,'Main actor')");
        stmt.executeUpdate("insert into casting values(1,2,'Main actor')");
        stmt.executeUpdate("insert into casting values(2,1,'Main actor')");
        stmt.executeUpdate("insert into casting values(2,3,'Main actor')");
        stmt.executeUpdate("insert into casting values(3,6,'Main actor')");
        stmt.executeUpdate("insert into casting values(3,7,'Supporting Actor')");
        stmt.executeUpdate("insert into casting values(4,4,'Main actor')");
        stmt.executeUpdate("insert into casting values(4,5,'Main actor')");
        stmt.executeUpdate("insert into casting values(5,8,'Main actor')");
        stmt.executeUpdate("insert into casting values(5,9,'Main actor')");
        
        
        
        //make(movieID,directorId)
        stmt.executeUpdate("insert into make values(1,1)");
        stmt.executeUpdate("insert into make values(2,1)");
        stmt.executeUpdate("insert into make values(3,2)");
        stmt.executeUpdate("insert into make values(4,3)");
        stmt.executeUpdate("insert into make values(5,3)");
        
        //////////
        System.out.println("Initial data inserted!");
        System.out.println("\n2.Insert the proper data from the following statements.\n");
        System.out.println("Statement : Winona Ryder won the 'Best supporting actor' award in 1994");
       
      
        System.out.println("Translated SQL : insert into award values(1,'Best supporting actor')");
        System.out.println("Translated SQL : insert into actorObtain values (2, 1, 1994)");
        System.out.println("Updated Tables");
        stmt.executeUpdate("insert into award values(1,'Best supporting actor')");
        stmt.executeUpdate("insert into actorObtain values(2,1,1994)");
//        
        rs = stmt.executeQuery("select * from award");
        while(rs.next()) {
        	int awardID = rs.getInt("awardID");
			String awardName = rs.getString("awardName");
			System.out.println("-------<award>----------");
			System.out.println("awardID"+"\t\t"+"awardName");
			System.out.println(awardID + "\t\t" + awardName);
        }
        rs = stmt.executeQuery("select * from actorObtain");
        
        
        System.out.println("-------<actorObtain>------");
		System.out.println("actorID"+"\t\t"+"awardID"+"\t\t"+"year");
        while(rs.next()) {
        	int actorID = rs.getInt("actorID");
        	int awardID = rs.getInt("awardID");
			String year = rs.getString("year");
			System.out.println(actorID+"\t\t"+awardID + "\t\t" + year);
        }
        
       /// 
        System.out.println("Statement :Andrew Garfield won the'Best supporting actor' award in 2011");
        System.out.println("Translated SQL : select actorID from actor where actorName='Andrew Garfield'");
        System.out.println("Translated SQL : select awardID from award where awardName = 'Best supporting actor'");
        System.out.println("Translated SQL : insert into actorObtain values (7, 1, 2011)");
        
        stmt.executeUpdate("insert into actorObtain values(7,1,2011)");
        
        rs = stmt.executeQuery("select * from award");
        while(rs.next()) {
        	int awardID = rs.getInt("awardID");
			String awardName = rs.getString("awardName");
			System.out.println("-------<award>----------");
			System.out.println("awardID"+"\t\t"+"awardName");
			System.out.println(awardID + "\t\t" + awardName);
        }
        rs = stmt.executeQuery("select * from actorObtain");
        System.out.println("-------<actorObtain>------");
		System.out.println("actorID"+"\t\t"+"awardID"+"\t\t"+"year");
        while(rs.next()) {
        	int actorID = rs.getInt("actorID");
        	int awardID = rs.getInt("awardID");
			String year = rs.getString("year");
			System.out.println(actorID+"\t\t"+awardID + "\t\t" + year);
        }
       
        System.out.println("Statement: Jesse Eisenberg won the'Best main actor'award in 2011");
        System.out.println("Translated SQL: insert into award values(2,'Best main actor')");
        System.out.println("Translated SQL: insert into actorObtain values(6,2,2011)");
        
        stmt.executeUpdate("insert into award values(2,'Best main actor')");
        stmt.executeUpdate("insert into actorObtain values(6,2,2011)");
        
        rs = stmt.executeQuery("select * from award");
        System.out.println("-------<award>----------");
		System.out.println("awardID"+"\t\t"+"awardName");
        while(rs.next()) {
        	int awardID = rs.getInt("awardID");
			String awardName = rs.getString("awardName");
			System.out.println(awardID + "\t\t" + awardName);
        }
        
        rs = stmt.executeQuery("select * from actorObtain");
        System.out.println("-------<actorObtain>------");
		System.out.println("actorID"+"\t\t"+"awardID"+"\t\t"+"year");
		 while(rs.next()) {
	        	int actorID = rs.getInt("actorID");
	        	int awardID = rs.getInt("awardID");
				String year = rs.getString("year");
				System.out.println(actorID+"\t\t"+awardID + "\t\t" + year);
	        }
		 
		 
        System.out.println("Statement: Johnny Depp won the 'Best villain actor' award in 2011");
        System.out.println("Translated SQL: insert into award values(3,'Best villain actor')");
        System.out.println("Translated SQL: insert into actorObtain values(1,3,2011)");
        
        
        
        stmt.executeUpdate("insert into award values(3,'Best villain actor')");
        stmt.executeUpdate("insert into actorObtain values(1,3,2011)");
        
        rs = stmt.executeQuery("select * from award");
      System.out.println("-------<award>----------");
		System.out.println("awardID"+"\t\t"+"awardName");
      while(rs.next()) {
      	int awardID = rs.getInt("awardID");
			String awardName = rs.getString("awardName");
			System.out.println(awardID + "\t\t" + awardName);
      }
      
      rs = stmt.executeQuery("select * from actorObtain");
      System.out.println("-------<actorObtain>------");
      System.out.println("actorID"+"\t\t"+"awardID"+"\t\t"+"year");
      while(rs.next()) {
	        	int actorID = rs.getInt("actorID");
	        	int awardID = rs.getInt("awardID");
				String year = rs.getString("year");
				System.out.println(actorID+"\t\t"+awardID + "\t\t" + year);
	        }
       
      
      //2.5
      System.out.println("Statement: Edward Scissorhands won the 'Best fantasy movie'award in 1991");
      System.out.println("Translated SQL: insert into award values(4,'Best fantasy movie')");
      System.out.println("Translated SQL: insert into movieObtain values(1,4,1991)");
      
      
      
      stmt.executeUpdate("insert into award values(4,'Best fantasy movie')");
      stmt.executeUpdate("insert into movieObtain values(1,4,1991)");
      rs = stmt.executeQuery("select * from award");
      System.out.println("-------<award>----------");
		System.out.println("awardID"+"\t\t"+"awardName");
      while(rs.next()) {
      	int awardID = rs.getInt("awardID");
			String awardName = rs.getString("awardName");
			System.out.println(awardID + "\t\t" + awardName);
      }
      
      rs = stmt.executeQuery("select * from movieObtain");
      System.out.println("-------<movieObtain>------");
      System.out.println("movieID"+"\t\t"+"awardID"+"\t\t"+"year");
      while(rs.next()) {
	        	int movieID = rs.getInt("movieID");
	        	int awardID = rs.getInt("awardID");
				String year = rs.getString("year");
				System.out.println(movieID+"\t\t"+awardID + "\t\t" + year);
	        }
      
      //2.6
      System.out.println("Statement: The Dark Knight won the 'Best picture' award in 2009");
      System.out.println("Translated SQL: insert into award values(5,'Best picture')");
      System.out.println("Translated SQL: insert into movieObtain values(4,5,'2009')");
      
      
      
      stmt.executeUpdate("insert into award values(5,'Best picture')");
      stmt.executeUpdate("insert into movieObtain values(4,5,2009)");
      
      rs = stmt.executeQuery("select * from award");
      System.out.println("-------<award>----------");
		System.out.println("awardID"+"\t\t"+"awardName");
      while(rs.next()) {
      	int awardID = rs.getInt("awardID");
			String awardName = rs.getString("awardName");
			System.out.println(awardID + "\t\t" + awardName);
      }
      
      rs = stmt.executeQuery("select * from movieObtain");
      System.out.println("-------<movieObtain>------");
      System.out.println("movieID"+"\t\t"+"awardID"+"\t\t"+"year");
      while(rs.next()) {
	        	int movieID = rs.getInt("movieID");
	        	int awardID = rs.getInt("awardID");
				String year = rs.getString("year");
				System.out.println(movieID+"\t\t"+awardID + "\t\t" + year);
	        }
      //2.7
        System.out.println("Statement: Alice In Wonderland won the 'Best fantasy movie'award in 2011");
        System.out.println("Translated SQL : select awardID from award where awardName = 'Best fantasy movie'");
        System.out.println("Translated SQL : select movieID form movie where movieName = 'Alice In The Wonderland'");
        System.out.println("Translated SQL: insert into movieObtain values(2,4,2011)");
        
        
        
        stmt.executeUpdate("insert into movieObtain values(2,4,2011)");
        
        rs = stmt.executeQuery("select * from award");
        System.out.println("-------<award>----------");
  		System.out.println("awardID"+"\t\t"+"awardName");
        while(rs.next()) {
        	int awardID = rs.getInt("awardID");
  			String awardName = rs.getString("awardName");
  			System.out.println(awardID + "\t\t" + awardName);
        }
        
        rs = stmt.executeQuery("select * from movieObtain");
        System.out.println("-------<movieObtain>------");
        System.out.println("movieID"+"\t\t"+"awardID"+"\t\t"+"year");
        while(rs.next()) {
  	        	int movieID = rs.getInt("movieID");
  	        	int awardID = rs.getInt("awardID");
  				String year = rs.getString("year");
  				System.out.println(movieID+"\t\t"+awardID + "\t\t" + year);
  	        }
      //2.8
        System.out.println("Statement: David Fincher won the 'Best director'award in 2011");
        System.out.println("Translated SQL: insert into award values(6,'Best Director')");
        System.out.println("Translated SQL: insert into directorObtain values(2,6,2011)");
        
        
        stmt.executeUpdate("insert into award values(6,'Best Director')");
        stmt.executeUpdate(" insert into directorObtain values(2,6,2011)");
        
        rs = stmt.executeQuery("select * from award");
        System.out.println("-------<award>----------");
  		System.out.println("awardID"+"\t\t"+"awardName");
        while(rs.next()) {
        	int awardID = rs.getInt("awardID");
  			String awardName = rs.getString("awardName");
  			System.out.println(awardID + "\t\t" + awardName);
        }
        
        rs = stmt.executeQuery("select * from directorObtain");
        System.out.println("-------<directorObtain>------");
        System.out.println("directorID"+"\t"+"awardID"+"\t\t"+"year");
        while(rs.next()) {
  	        	int directorID = rs.getInt("directorID");
  	        	int awardID = rs.getInt("awardID");
  				String year = rs.getString("year");
  				System.out.println(directorID+"\t\t"+awardID + "\t\t" + year);
  	        }
      //3.1
        System.out.println("\n\n//3//\n3.1)Statement:Bob rates 5 to 'The Dark Knight'");
        System.out.println("Translated SQL :insert into customerRate values(1,4,5)");
        
         stmt.executeUpdate("insert into customerRate values(1,4,5)");
        
         stmt.executeUpdate("update movie M set avgRate =(select avg(rate) from customerRate where M.movieID = customerRate.movieID)");
       	System.out.println("Translated SQL:update movie M set avgRate =(select avg(rate) from customerRate where M.movieID = customerRate.movieID)");
        
        rs = stmt.executeQuery("select* from customerRate");
        System.out.println("\n//**customerRate**//-----------");
        System.out.println("<customerID>"+'\t'+"<movieID>"+'\t'+"<rate>");
        while(rs.next()) {
        	int cid = rs.getInt("customerID");
        	int mid = rs.getInt("movieID");
        	int rate = rs.getInt("rate");
        	System.out.println(cid+"\t\t"+mid+"\t\t"+rate);
        }
        
        
        
        rs=stmt.executeQuery("select * from movie");     
        System.out.println("\n//**movie**//------------------");
        System.out.println("<movieID>-----<movieName>----<releaseYear><releaseMonth><releaseDate>---<publisherName>-------<avgRate>");
        while(rs.next()) {
        	int ID = rs.getInt("movieID");
        	String mname =rs.getString("movieName");
        	int y = rs.getInt("releaseYear");
        	int m = rs.getInt("releaseMonth");
        	int d =rs.getInt("releaseDate");
        	String pname = rs.getString("publisherName");
        	double avg = rs.getDouble("avgRate");
        	System.out.println(ID+"\t"+mname+"///\t\t"+y+'.'+m+'.'+d+"\t\t"+pname+"/\t\t"+avg);
        	
        }
        
      //3.2  
        System.out.println("\n3.2)Statement:Bell rates 5 to the movies whose director is 'Tim Burton'");
        System.out.println("Translated SQL: select movieID from make natural join director where director ='Tim Burton");
        
        stmt.executeUpdate("insert into customerRate select 5 as customerID, movieID, 5.0 as Rate from make where directorID =1");
        System.out.println("Translated SQL: insert into customerRate select 5 as customerID, movieID, 5.0 as Rate from make where directorID =1");
       
        stmt.executeUpdate("update movie M set avgRate =(select avg(rate) from customerRate where M.movieID = customerRate.movieID)");
      	System.out.println("Translated SQL:update movie M set avgRate =(select avg(rate) from customerRate where M.movieID = customerRate.movieID)");
        
      	 rs = stmt.executeQuery("select * from customerRate");
         System.out.println("\n//**customerRate**//-----------");
         System.out.println("<customerID>"+'\t'+"<movieID>"+'\t'+"<rate>");
         
       while(rs.next()) {
       	int cid = rs.getInt("customerID");
       	int mid = rs.getInt("movieID");
       	int rate = rs.getInt("rate");
       	System.out.println(cid+"\t\t"+mid+"\t\t"+rate);
       }
        
        rs=stmt.executeQuery("select * from movie");
        System.out.println("\n//**movie**//------------------");
        System.out.println("<movieID>-----<movieName>----<releaseYear><releaseMonth><releaseDate>---<publisherName>-------<avgRate>");
        while(rs.next()) {
        	int ID = rs.getInt("movieID");
        	String mname =rs.getString("movieName");
        	int y = rs.getInt("releaseYear");
        	int m = rs.getInt("releaseMonth");
        	int d =rs.getInt("releaseDate");
        	String pname = rs.getString("publisherName");
        	double avg = rs.getDouble("avgRate");
        	System.out.println(ID+"\t"+mname+"///\t\t"+y+'.'+m+'.'+d+"\t\t"+pname+"/\t\t"+avg);
        	
        }
       //3.3
        System.out.println("\n3.3)Statement:Jill rates 4 to the movies whose main actor is female");
        
        stmt.executeUpdate("insert into customerRate select 4 as customerID, movieID,4.0 as rate from actor natural join casting where gender='Female' and role ='Main actor'");
        
       
        System.out.println("Translated SQL: insert into customerRate select 4 as customerID, movieID,4.0 as rate from actor natural join casting where gender='Female' and role ='Main actor'");
        
        
        rs = stmt.executeQuery("select * from customerRate");
        System.out.println("\n//**customerRate**//-----------");
        System.out.println("<customerID>"+'\t'+"<movieID>"+'\t'+"<rate>");
        
      while(rs.next()) {
      	int cid = rs.getInt("customerID");
      	int mid = rs.getInt("movieID");
      	int rate = rs.getInt("rate");
      	System.out.println(cid+"\t\t"+mid+"\t\t"+rate);
      }
    

      	stmt.executeUpdate("update movie M set avgRate =(select avg(rate) from customerRate where M.movieID = customerRate.movieID)");
      	System.out.println("Translated SQL:update movie M set avgRate =(select avg(rate) from customerRate where M.movieID = customerRate.movieID)");
      	
        rs=stmt.executeQuery("select * from movie");
        System.out.println("\n//**movie**//------------------");
        System.out.println("<movieID>-----<movieName>----<releaseYear><releaseMonth><releaseDate>---<publisherName>-------<avgRate>");
        while(rs.next()) {
        	int ID = rs.getInt("movieID");
        	String mname =rs.getString("movieName");
        	int y = rs.getInt("releaseYear");
        	int m = rs.getInt("releaseMonth");
        	int d =rs.getInt("releaseDate");
        	String pname = rs.getString("publisherName");
        	double avg = rs.getDouble("avgRate");
        	System.out.println(ID+"\t"+mname+"///\t\t"+y+'.'+m+'.'+d+"\t\t"+pname+"/\t\t"+avg);
        }
        
      //3.4  
        System.out.println("\n3.4)Statement:Jack rates 4 to the fantasy movies");
        
        System.out.println("Translated SQL: insert into customerRate select 3 as customerID, movieID, 4.0 as rate from movieGenre where genreName ='Fantasy'");
        stmt.executeUpdate("insert into customerRate select 3 as customerID, movieID, 4.0 as rate from movieGenre where genreName ='Fantasy'");
        
        
        rs = stmt.executeQuery("select * from customerRate");
        System.out.println("\n//**customerRate**//-----------");
        System.out.println("<customerID>"+'\t'+"<movieID>"+'\t'+"<rate>");
        
        while(rs.next()) {
        	int cid = rs.getInt("customerID");
        	int mid = rs.getInt("movieID");
        	int rate = rs.getInt("rate");
        	System.out.println(cid+"\t\t"+mid+"\t\t"+rate);
        } 
        
        stmt.executeUpdate("update movie M set avgRate =(select avg(rate) from customerRate where M.movieID = customerRate.movieID)");
        System.out.println("Translated SQL:update movie M set avgRate =(select avg(rate) from customerRate where M.movieID = customerRate.movieID)");
      	
        rs=stmt.executeQuery("select * from movie");
      System.out.println("\n//**movie**//------------------");
        System.out.println("<movieID>-----<movieName>----<releaseYear><releaseMonth><releaseDate>---<publisherName>-------<avgRate>");
        while(rs.next()) {
        	int ID = rs.getInt("movieID");
        	String mname =rs.getString("movieName");
        	int y = rs.getInt("releaseYear");
        	int m = rs.getInt("releaseMonth");
        	int d =rs.getInt("releaseDate");
        	String pname = rs.getString("publisherName");
        	double avg = rs.getDouble("avgRate");
        	System.out.println(ID+"\t"+mname+"///\t\t"+y+'.'+m+'.'+d+"\t\t"+pname+"/\t\t"+avg);
        }
        
        
        
        
        
        
      
      //3.5
        System.out.println("\n3.5)Statement:John rates 5 to the movies whose director won the 'Best director'");
        
        stmt.executeUpdate("insert into customerRate select 2 as customerID, movieID, 5.0 as rate "
        		+ "from make natural join directorObtain natural join award where awardName ='Best Director'");
        System.out.println("Translated SQL: insert into customerRate select 2 as customerID, movieID, 5.0 as rate "
        		+ "from make natural join directorObtain natural join award where awardName ='Best Director'");
        
        
        rs = stmt.executeQuery("select * from customerRate");
        System.out.println("\n//**customerRate**//-----------");
        System.out.println("<customerID>"+'\t'+"<movieID>"+'\t'+"<rate>");
        
        while(rs.next()) {
        	int cid = rs.getInt("customerID");
        	int mid = rs.getInt("movieID");
        	int rate = rs.getInt("rate");
        	System.out.println(cid+"\t\t"+mid+"\t\t"+rate);
        } 
        
        stmt.executeUpdate("update movie M set avgRate =(select avg(rate) from customerRate where M.movieID = customerRate.movieID)");
        System.out.println("Translated SQL:update movie M set avgRate =(select avg(rate) from customerRate where M.movieID = customerRate.movieID)");
      	
        rs=stmt.executeQuery("select * from movie");
        System.out.println("\n//**movie**//------------------");
        System.out.println("<movieID>-----<movieName>----<releaseYear><releaseMonth><releaseDate>---<publisherName>-------<avgRate>");
        while(rs.next()) {
        	int ID = rs.getInt("movieID");
        	String mname =rs.getString("movieName");
        	int y = rs.getInt("releaseYear");
        	int m = rs.getInt("releaseMonth");
        	int d =rs.getInt("releaseDate");
        	String pname = rs.getString("publisherName");
        	double avg = rs.getDouble("avgRate");
        	System.out.println(ID+"\t"+mname+"///\t\t"+y+'.'+m+'.'+d+"\t\t"+pname+"/\t\t"+avg);
        }
    
        
      //4
        System.out.println("\n\n//4//\nStatement: Select the names of the movies whose actor are dead");
        
        System.out.println("Translated SQL:select casting.movieID from casting, actor where actor.dateOfDeath is not NULL and actor.actorID = casting.actorID");
        rs =stmt.executeQuery("select movieID from casting natural join actor where dateOfDeath is not NULL");
        System.out.println("Translated SQL :  int getID=rs.getInt('movieID')");
        System.out.println("Translated SQL : SELECT movie.movieName from movie where movieID = 'getID'");
        
        System.out.println("------------------------------");
		while(rs.next()) {
			int getID = rs.getInt("movieID");
			Statement newStmt = connection.createStatement();
			ResultSet rs2 = null;
			rs2 = newStmt.executeQuery("select movieName from movie where movieID="+getID);
			
			
			while(rs2.next()) {
				String name = rs2.getString("movieName");
				System.out.println(name);
			}
		}
        
    
        //5
        System.out.println("\n\n//5//\nSelect the names of the directors who cast the same actor more than once.");
        System.out.println("Translated SQL: select directorName from (natural join make,casting,director) group by actorID,directorID, directorName having count(*) > 1 ");
        
        rs = stmt.executeQuery("select directorName from director natural join casting natural join make "
        		+ "group by actorID,directorID,directorName having count(*) > 1 ");
        System.out.println("------------");
        while(rs.next()) {
        	String s =rs.getString("directorName");
        	System.out.println(s);
        }
        
        
        //6
        System.out.println("\n\n//6//\nSelect the names of the movies and the genres, where movies have the common genre.");
        
        rs =stmt.executeQuery("select genreName,count(*) from movieGenre group by genreName");
        System.out.println("Translated SQL:select genreName,count(*) from movieGenre group by genreName");
 
        System.out.println("---<movieName>"+"--------------"+"<genreName>---");
        while(rs.next()) {
        	if(rs.getInt("count")>1) {
        		String cGenre = rs.getString("genreName");
    			Statement newStmt = connection.createStatement();
    			ResultSet rs2 = null;
    			rs2 = newStmt.executeQuery("select movieName, genreName  from movieGenre natural join movie where genreName ='"+cGenre+"'");
    			while(rs2.next()) {
    				String movie = rs2.getString("movieName");
    				String genre = rs2.getString("genreName");
    				System.out.println(movie +"////\t\t"+genre);
    				
    			}
    			rs2.close();
    			newStmt.close();
        
        	}
        }
        
        //7
        System.out.println("\n\n//7//\nStatement: Delete the movies whose director or actor did not get any award and delete data from rerlated tables");
        System.out.println("Translated SQL: delete from movie where movieID not in (select movieID from actorObtain natural join casting)"
       	 	 + "and movieID not in(select movieID from directorObtain natural join make)");
        
        stmt.executeUpdate("delete from movie where movieID not in (select movieID from actorObtain natural join casting)"
        	 	 + "and movieID not in(select movieID from directorObtain natural join make)");
        
        rs=stmt.executeQuery("select * from movie");
        System.out.println("\n//**movie**//------------------");
        System.out.println("<movieID>-----<movieName>----<releaseYear><releaseMonth><releaseDate>---<publisherName>-------<avgRate>");
        while(rs.next()) {
        	int ID = rs.getInt("movieID");
        	String mname =rs.getString("movieName");
        	int y = rs.getInt("releaseYear");
        	int m = rs.getInt("releaseMonth");
        	int d =rs.getInt("releaseDate");
        	String pname = rs.getString("publisherName");
        	double avg = rs.getDouble("avgRate");
        	System.out.println(ID+"\t"+mname+"///\t\t"+y+'.'+m+'.'+d+"\t\t"+pname+"/\t\t"+avg);
        }
        rs=stmt.executeQuery("select * from movieGenre");
        System.out.println("\n//**movieGenre**//------------");
        System.out.println("<movieID>------<genreName>------");        
        while(rs.next()) {
        	int ID = rs.getInt("movieID");
        	String name =rs.getString("genreName");
        	
        	System.out.println(ID+"\t"+name);
        }
        
        rs = stmt.executeQuery("select * from movieObtain");
        System.out.println("\n//**movieObtain**//------------");
        System.out.println("movieID"+"\t\t"+"awardID"+"\t\t"+"year");
        while(rs.next()) {
  	        	int movieID = rs.getInt("movieID");
  	        	int awardID = rs.getInt("awardID");
  				String year = rs.getString("year");
  				System.out.println(movieID+"\t\t"+awardID + "\t\t" + year);
  	        }
        rs = stmt.executeQuery("select * from make");
        System.out.println("\n//**make**//----------");
        System.out.println("<movieID>"+'\t'+"<directorID>");
        
        while(rs.next()) {
        	int mid = rs.getInt("movieID");
        	int did = rs.getInt("directorID");
        	System.out.println(mid+"\t\t"+did);
        } 
        
        rs = stmt.executeQuery("select * from casting");
        System.out.println("\n//**casting**//-----------");
        System.out.println("<movieID>"+'\t'+"<actorID>"+'\t'+"<role>");
        
        while(rs.next()) {
        	int mid = rs.getInt("movieID");
        	int aid = rs.getInt("actorID");
        	String r= rs.getString("role");
        	System.out.println(mid+"\t\t"+aid+"\t\t"+r);
        } 
        
        rs = stmt.executeQuery("select * from customerRate");
        System.out.println("\n//**customerRate**//-----------");
        System.out.println("<customerID>"+'\t'+"<movieID>"+'\t'+"<rate>");
        
        while(rs.next()) {
        	int cid = rs.getInt("customerID");
        	int mid = rs.getInt("movieID");
        	int rate = rs.getInt("rate");
        	System.out.println(cid+"\t\t"+mid+"\t\t"+rate);
        } 
        
        
        //8
        System.out.println("\n\n//8//\nStatement: Delete all customers and delete data from related tables");
        
        stmt.executeUpdate("delete from customer");
        System.out.println("Translated SQL :delete from customr");
        
        stmt.executeUpdate("update movie set avgRate=0");
        System.out.println("Translated SQL :update movie set avgRate=0 ");
        
        
        rs = stmt.executeQuery("select * from customer");
        System.out.println("\n//**customer Table**//-----------");
        System.out.println("<customerID>"+'\t'+"<customerName>"+'\t'+"<dateOfBirth>"+'\t'+"<gender>");
        
        while(rs.next()) {
        	int cid = rs.getInt("customerID");
        	String cname = rs.getString("customerName");
        	String birth = rs.getString("dateOfBirth");
        	String gender = rs.getString("gender");
        	System.out.println(cid+"\t"+cname+"\t"+birth+"\t"+gender);
        } 
        
        rs = stmt.executeQuery("select * from customerRate");
        System.out.println("\n//*customerRate Table*//------------------");
        System.out.println("<customerID>"+'\t'+"<movieID>"+'\t'+"<rate>");
        
        while(rs.next()) {
        	int cid = rs.getInt("customerID");
        	int mid = rs.getInt("movieID");
        	int rate = rs.getInt("rate");
        	System.out.println(cid+"\t\t"+mid+"\t\t"+rate);
        } 
        
        rs=stmt.executeQuery("select * from movie");
        System.out.println("\n//**movie Table**//---------------------");
        System.out.println("<movieID>-----<movieName>----<releaseYear><releaseMonth><releaseDate>---<publisherName>-------<avgRate>");
        while(rs.next()) {
        	int ID = rs.getInt("movieID");
        	String mname =rs.getString("movieName");
        	int y = rs.getInt("releaseYear");
        	int m = rs.getInt("releaseMonth");
        	int d =rs.getInt("releaseDate");
        	String pname = rs.getString("publisherName");
        	double avg = rs.getDouble("avgRate");
        	System.out.println(ID+"\t"+mname+"///\t\t"+y+'.'+m+'.'+d+"\t\t"+pname+"/\t\t"+avg);
        }
        
        
        //9
        System.out.println("\n\n//9//\nStatement:Delete all tables and data");
        
        stmt.executeUpdate("drop table casting");
        stmt.executeUpdate("drop table customerRate");
        stmt.executeUpdate("drop table make");
        stmt.executeUpdate("drop table actorObtain");
        stmt.executeUpdate("drop table movieObtain");
        stmt.executeUpdate("drop table directorObtain");
        stmt.executeUpdate("drop table customer");
        stmt.executeUpdate("drop table movieGenre");
        stmt.executeUpdate("drop table genre");
        stmt.executeUpdate("drop table award");
        stmt.executeUpdate("drop table movie");
        stmt.executeUpdate("drop table actor");
        stmt.executeUpdate("drop table director");
        
        
        System.out.println("Table removed");
        /////////////////////////////////////////////////////
        ////////// write your code on this ////////////
        /////////////////////////////////////////////////////

        connection.close();
    }
    }
