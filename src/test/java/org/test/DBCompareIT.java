package org.test;

import org.testng.annotations.Test;
import org.citrusframework.TestAction;
import org.citrusframework.actions.ExecuteSQLQueryAction.Builder;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.citrusframework.actions.EchoAction.Builder.echo;
import static org.citrusframework.actions.ExecuteSQLAction.Builder.sql;
import static org.citrusframework.actions.ExecuteSQLQueryAction.Builder.query;


import javax.sql.DataSource;

@Test(groups = "citrus-test")
public class DBCompareIT extends TestNGCitrusSpringSupport {

    @Autowired
    @Qualifier("firstdb")
    private DataSource dataSource;
	
    @Autowired
    @Qualifier("encryptedDS")
    private DataSource encryptedDS;

    @CitrusTest
    public void echoToday() {
        variable("now", "citrus:currentDate()");

        run(echo("Today is: ${now}"));
    }

    @CitrusTest(name = "SampleJavaTest.sayHello")
    public void sayHello() {
        run(echo("Hello Citrus!"));
    }

   
    @CitrusTest
    public void testPostgresConnection() {
        System.out.println("start");
		$(Builder
            .query().dataSource(encryptedDS)
            .statement("SELECT '1' as one, now() as current_time_from_db")
            .validate("one", "1")
       );

        //$(echo("The current time from the database is: ${current_time_from_db}"));
        System.out.println("done");
    }
}
