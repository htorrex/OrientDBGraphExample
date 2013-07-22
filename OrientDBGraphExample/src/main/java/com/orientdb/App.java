package com.orientdb;


import java.util.List;

import org.apache.log4j.Logger;

import com.orientechnologies.orient.core.db.graph.OGraphDatabase;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.graph.gremlin.OGremlinHelper;

/**
 * 
 * @author hector.torres
 */
public class App 
{
    private static String PATH = "memory:database2";
    private static final Logger log = Logger.getLogger(App.class);
    
    public static void main( String[] args )
    {
    	log.info("::::::::::::::::");
        OGraphDatabase db = new OGraphDatabase(PATH) ;
        OGremlinHelper.global().create();
        
        if (!db.exists()) {
            db.create();
            log.info("crea DB");
            
            db.createVertexType("Student");
            db.createVertexType("Course");
            db.createEdgeType("Attends");
            
            ODocument student1 = db.createVertex("Student");
            student1.field("name", "John");
            student1.save();
            
            ODocument student2 = db.createVertex("Student");
            student2.field("name", "Betty");
            student2.save();
            
            ODocument course1 = db.createVertex("Course");
            course1.field("title", "Algebra");
            course1.save();
            
            ODocument course2 = db.createVertex("Course");
            course2.field("title", "Literature");
            course2.save();
            
            ODocument attends = db.createEdge (student1, course1, "Attends");
            attends.save();
            ODocument attends2 = db.createEdge(student2, course1, "Attends");
            attends2.save();
            ODocument attends3 = db.createEdge(student2, course2, "Attends");
            attends3.save();
            
            
        } else {
            db.open("admin", "admin");
            log.info("ya existe DB");
        }
        
        //List<ODocument> results = db.query(new OSQLSynchQuery("select GREMLIN('current.outE.filter{it['@class']=='Attends'}.inV').title as value from Student"));
        List<ODocument> results = db.query(new OSQLSynchQuery("select from Student"));
        for (ODocument result: results) {
        	//log.info("result.json(): "+result.toJSON());
        	log.info("result.field: "+result.field("name"));
        }
        
        db.close();
    }
}
