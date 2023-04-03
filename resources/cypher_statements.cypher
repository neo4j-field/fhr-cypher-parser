CREATE (n:Person {name: 'Alice'})

MATCH (a:Person {name: 'Alice'}), (b:Person:Janitor {name: 'Bob'})
CREATE (a)-[r:KNOWS]->(b)

MATCH (n:Person {name: 'Alice'})
SET n.age = 30