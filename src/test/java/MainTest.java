import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Statement;
import org.neo4j.cypherdsl.core.StatementCatalog;
import org.neo4j.cypherdsl.parser.CypherParser;
import org.neo4j.cypherdsl.parser.Options;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainTest {

    private Statement statement;
    private static final String MATCH_PATTERN = "(?i)\\bMATCH\\b";
    private static final String MERGE_PATTERN = "(?i)\\bMERGE\\b";
    private static final String CREATE_PATTERN = "(?i)\\bCREATE\\b";
    private static final String DELETE_PATTERN = "(?i)\\bDELETE\\b";


    @BeforeEach
    public void setup(){
        statement = getStatement("MATCH (m:Person {id:100, name:alex}) MATCH (m:Person {id:10, name:jon});");

    }

    public static Statement getStatement(String cypherQuery){
        Options options = Options.newOptions().build();
        return CypherParser.parse(cypherQuery, options);
    }



    @Test
    public void isReadStatement(){

        String cyhperString = statement.getCypher();
        List<String> clauses = new ArrayList<>();
        Pattern pattern = Pattern.compile(String.format("%s|%s",MATCH_PATTERN,MERGE_PATTERN));
        Matcher matcher = pattern.matcher(cyhperString);

        while(matcher.find()){
            clauses.add(matcher.group());
        }

         Assertions.assertEquals(true, clauses.size() > 1 );

    }


    @Test
    public void isWriteStatement(){

        String cyhperString = statement.getCypher();
        List<String> clauses = new ArrayList<>();
        Pattern pattern = Pattern.compile(String.format("%s|%s",DELETE_PATTERN,MERGE_PATTERN,CREATE_PATTERN));
        Matcher matcher = pattern.matcher(cyhperString);
        while(matcher.find()){
            clauses.add(matcher.group());
        }

        Assertions.assertEquals(false, clauses.size() > 1 );

    }


}
