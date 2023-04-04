### FHR Cypher Parser Example

### Exploring the Cypher Parser API surface

* A Cypher String is where start our journey. We next marshal the String to a Statement and pass that as a parameter too CypherParser.parse(statement,options); 


* Below is a list of tasks that we can accomplish with this API

| Task | Description                                                                                                                 |
| --- |-----------------------------------------------------------------------------------------------------------------------------|
| determine if a query is reading or modifying data (query level) | You can do this through regex pattern matching on the relevant clauses. See Test.isWriteStatement() && Test.isReadStatement |
| determine what labels are referenced in the query | Main.getLabels()                                                                                                            |
| determine what relationships are referenced in the query (with the source and target labels) | I don't think you can figure out relationship direction from the parser libary (could be wrong)                             |
| determine what properties are used for either labels or properties | You can extract both Nodes Labels and Properties from StatementCatalog but it's unclear if you can map them together.       |
| for the three above parse out whether we're reading or modifying the specific elements (label, property, relationship) |                                                                                                                             |
| determine what procedures are being called |                                                                                                                             |
| determine parameters being passed in |                                                                                                                             |
| determine what is being returned from the query |                                                                                                                             |