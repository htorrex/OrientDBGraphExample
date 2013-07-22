OrientDBGraphExample
====================

Very basic OrientDB Graph Example


Create an empty maven application project and add following lines to pom.xml:

<repositories>
  <repository>
    <id>Sonatype Releases</id>
    <url>https://oss.sonatype.org/content/repositories/releases/</url>
  </repository>
  <repository>
    <id>Sonatype Snapshots</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
  </repository>
</repositories>

We will be using snapshot version of OrientDB Graph Edition as it seems it is the only way to get it via maven.

And add this to dependencies:

<dependency>
  <groupId>com.orientechnologies</groupId>
  <artifactId>orientdb-graphdb</artifactId>
  <version>1.4.2-SNAPSHOT</version>
</dependency>

Try to build the project and make sure that all dependencies are downloaded. And then we can start with the actual code.

First lets initialize the database object:

OGraphDatabase db = new OGraphDatabase(PATH);
The PATH is a path to where the database is located. For the sake of this example I will be using an in-memory database, so my path is "memory:db".

We need to create a database:

db.create();
If you're using an in-file database, you would do this as: C:\\database

if (!db.exists()) {
  db.create();
} else {
  db.open("admin", "admin");
}
The default username/password is always admin/admin.

In the Graph Edition of the OrientDB we have a (vertex, edge) structure, where each  vertex and edge is a document. We can create custom classes for vertices and edges.

db.createVertexType("Student");
db.createVertexType("Course");
db.createEdgeType("Attends");

So here we create Student, Course and Attends classes. Now lets populate some data into the graph:

ODocument student1 = db.createVertex("Student");
student1.field("name", "John");
student1.save();
 
ODocument course1 = db.createVertex("Course");
course1.field("title", "Algebra");
course1.save();
 
ODocument attends = db.createEdge (student1, course1, "Attends");
attends.save();

To get the data from OrientDB we can use SQL language like this:

List<ODocument> results = db.query(new OSQLSynchQuery("select from Student"));
for (ODocument result: results) {
      //log.info("result.json(): "+result.toJSON());
    	log.info("result.field: "+result.field("name"));
}






