package org.fournier;

import org.fournier.util.CypherSplitUtil;
import org.fournier.util.StatementCollector;
import org.neo4j.cypherdsl.core.Operation;
import org.neo4j.cypherdsl.core.Statement;
import org.neo4j.cypherdsl.core.StatementCatalog;
import org.neo4j.cypherdsl.parser.CypherParser;
import org.neo4j.cypherdsl.parser.ExpressionCreatedEventType;
import org.neo4j.cypherdsl.parser.Options;
import org.neo4j.cypherdsl.parser.internal.parser.javacc.Token;
import org.w3c.dom.html.HTMLAreaElement;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    private static final Path cypherFile = Paths.get("/Users/alexanderfournier/Downloads/cypherDSL/resources/cypher_statements.cypher");

    public static void main(String[] args) {


        List<String> statements = generateCypherStatements(cypherFile);
        String testStatementString = statements.get(1);


        Statement testStatement = getStatement(testStatementString);


        //all labels
        List<String> labels = getLabels(testStatement);

    }


    /***
     * A method that returns a List of Cypher Strings from a file, split using CypherSplitUtil
     * @param cypherFile
     * @return List<String> Cypher Statements
     */
    public static List<String> generateCypherStatements(Path cypherFile){
        List<String> cypherStatements = CypherSplitUtil.processFile(cypherFile, s -> s.split("(?m)^\\s*$"));
        return cypherStatements;
    }





    /**
     * A method that inputs the String statement and passes that to CypherParser.parse()
     * @param statement
     * @return
     */
    public static Statement getStatement(String statement){
        StatementCollector statementCollector = new StatementCollector();
        Options options = Options.newOptions()
                .withCallback(ExpressionCreatedEventType.ON_SET_LABELS, Operation.class, statementCollector)
                .build();
        return CypherParser.parse(statement,options);

    }




    public static List<String> getLabels(Statement statement){
        return statement.getCatalog().getNodeLabels()
                .stream()
                .map(token -> token.value())
                .collect(Collectors.toList());
    }





    /**
     * !!!THE METHODS BELOW ARE EITHER INCOMPLETE OR IMPROPERLY IMPLEMENTED!!!
     */

    /***
     * This method doesn't work because I don't think you can figure out relationship direction from statement.getCatalog
     * @param statement
     * @return
     */
    public static Map<String, Map<String, String>> getRelationshipMap(Statement statement) {

        Map<String, Map<String,String>> relationshipMap = new HashMap<>();

        statement.getCatalog().getRelationshipTypes()
                .forEach(token -> {
                    String relationshipType = token.value();
                    Map<String,String> labelMap = new HashMap<>();
                    statement.getCatalog().getRelationshipTypes()
                            .forEach(label -> {
                                String nodeLabel = label.value();
                               // labelMap.put(nodeLabel, direction); <--- I don't think you can get relationship direction from statement.getCatalogue
                            });

                    relationshipMap.put(relationshipType,labelMap);

                });
        return relationshipMap;

    }



    /***
     * I can't figure out how to create a Map<String<List<String>> of NodeLabels. when I iterate over the first statement.getCatalog.getNodeLabel() token I want to iterate over all  statement.getCatalog.getProperties() associated with that node..
     * @param statement
     * @return
     */
    public static Map<String,List<String>> getLabelProperties(Statement statement){

        HashMap<String,List<String>> labelPropertyMap = new HashMap<>();


        List<String> nodeLabels  = statement.getCatalog().getNodeLabels()
                .stream()
                .map(token -> token.value())
                .collect(Collectors.toList());

        List<String> nodeProperties = statement.getCatalog().getProperties()
                .stream()
                .map(token -> token.name())
                .collect(Collectors.toList());



        nodeLabels.stream().forEach(string -> System.out.println(string));
        nodeProperties.stream().forEach(string -> System.out.println(string));


        return labelPropertyMap;

    }





}