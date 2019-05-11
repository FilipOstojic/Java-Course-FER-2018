package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class QueryFilterTest {

	QueryFilter queryFilter;
	QueryParser parser;
	List<String> lines = null;
	List<StudentRecord> filteredRecords;
	
	public List<String> getLines() {
		try {
			lines = Files.readAllLines(Paths.get("./src/main/resources/database.txt"), StandardCharsets.UTF_8);
			return lines;
		} catch (IOException e) {
			System.err.println("Pogreška pri učitavanju putanje.");
		}
		return lines;
	}
	
	@Test
	public void test() {	
		StudentDatabase dataBase = new StudentDatabase(getLines());
		parser = new QueryParser(" lastName > \"B\" and lastName < \"C\" ");
		queryFilter = new QueryFilter(parser.getQuery());
		filteredRecords = dataBase.filter(queryFilter);
		
		for (StudentRecord record : filteredRecords) {
			System.out.println(record);
		}	
		System.out.println("");
	}
	
	//Both tests should have equal output!
	
	@Test
	public void test2() {	
		StudentDatabase dataBase = new StudentDatabase(getLines());
		parser = new QueryParser(" lastName LIKE \"B*\" ");
		queryFilter = new QueryFilter(parser.getQuery());
		filteredRecords = dataBase.filter(queryFilter);
		
		for (StudentRecord record : filteredRecords) {
			System.out.println(record);
		}
		System.out.println("");
	}

}
