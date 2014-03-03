package com.oxyent.pnl.neo4j;


import java.io.File;
import java.util.Iterator;

import org.neo4j.graphalgo.CommonEvaluators;
import org.neo4j.graphalgo.EstimateEvaluator;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class PathFindingDocTest
{
    private static GraphDatabaseService graphDb;
    private Transaction tx;

    public static void main( final String[] args )
    {
    	PathFindingDocTest hello = new PathFindingDocTest();
        hello.startDb();
    	hello.doBefore();
        hello.shortestPathExample();
        hello.shutdownDb();
        hello.doAfter();
    }
    
    private static enum ExampleTypes implements RelationshipType
    {
        MY_TYPE
    }

    public void startDb()
    {
        String storeDir = "target/pnl-demo";
        deleteFileOrDirectory( new File( storeDir ) );
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( storeDir );
    }

    public void doBefore()
    {
        tx = graphDb.beginTx();
    }


    public void doAfter()
    {
        tx.success();
        tx.close();
    }

    public void shutdownDb()
    {
        try
        {
            if ( graphDb != null ) graphDb.shutdown();
        }
        finally
        {
            graphDb = null;
        }
    }
    public void shortestPathExample()
    {
        // START SNIPPET: shortestPathUsage
        Node startNode = graphDb.createNode();
        Node middleNode1 = graphDb.createNode();
        Node middleNode2 = graphDb.createNode();
        Node middleNode3 = graphDb.createNode();
        Node endNode = graphDb.createNode();
        createRelationshipsBetween( startNode, middleNode1, endNode );
        createRelationshipsBetween( startNode, middleNode2, middleNode3, endNode );

        // Will find the shortest path between startNode and endNode via
        // "MY_TYPE" relationships (in OUTGOING direction), like f.ex:
        //
        // (startNode)-->(middleNode1)-->(endNode)
        //
        PathFinder<Path> finder = GraphAlgoFactory.shortestPath(
            PathExpanders.forTypeAndDirection( ExampleTypes.MY_TYPE, Direction.OUTGOING ), 15 );
        Iterable<Path> paths = finder.findAllPaths( startNode, endNode );
        // END SNIPPET: shortestPathUsage
        Path path = paths.iterator().next();
        Iterator<Node> iterator = path.nodes().iterator();
        System.out.println("----Shortest Path----="+path.length());
        iterator.next();
    }

    private void createRelationshipsBetween( final Node... nodes )
    {
        for ( int i = 0; i < nodes.length - 1; i++ )
        {
            nodes[i].createRelationshipTo( nodes[i+1], ExampleTypes.MY_TYPE );
        }
    }

    public void dijkstraUsage()
    {
        Node node1 = graphDb.createNode();
        Node node2 = graphDb.createNode();
        Relationship rel = node1.createRelationshipTo( node2, ExampleTypes.MY_TYPE );
        rel.setProperty( "cost", 1d );
        findCheapestPathWithDijkstra( node1, node2 );
    }

    public WeightedPath findCheapestPathWithDijkstra( final Node nodeA, final Node nodeB )
    {
        // START SNIPPET: dijkstraUsage
        PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(                
            PathExpanders.forTypeAndDirection( ExampleTypes.MY_TYPE, Direction.BOTH ), "cost" );

        WeightedPath path = finder.findSinglePath( nodeA, nodeB );

        // Get the weight for the found path
        path.weight();
        // END SNIPPET: dijkstraUsage
        return path;
    }

    private Node createNode( final Object... properties )
    {
        return setProperties( graphDb.createNode(), properties );
    }

    private <T extends PropertyContainer> T setProperties( final T entity, final Object[] properties )
    {
        for ( int i = 0; i < properties.length; i++ )
        {
            String key = properties[i++].toString();
            Object value = properties[i];
            entity.setProperty( key, value );
        }
        return entity;
    }

    private Relationship createRelationship( final Node start, final Node end,
            final Object... properties )
    {
        return setProperties( start.createRelationshipTo( end, ExampleTypes.MY_TYPE ),
                properties );
    }

    @SuppressWarnings( "unused" )
    public void astarExample()
    {
        // START SNIPPET: astarUsage
        Node nodeA = createNode( "name", "A", "x", 0d, "y", 0d );
        Node nodeB = createNode( "name", "B", "x", 7d, "y", 0d );
        Node nodeC = createNode( "name", "C", "x", 2d, "y", 1d );
        Relationship relAB = createRelationship( nodeA, nodeC, "length", 2d );
        Relationship relBC = createRelationship( nodeC, nodeB, "length", 3d );
        Relationship relAC = createRelationship( nodeA, nodeB, "length", 10d );

        EstimateEvaluator<Double> estimateEvaluator = new EstimateEvaluator<Double>()
        {
            @Override
            public Double getCost( final Node node, final Node goal )
            {
                double dx = (Double) node.getProperty( "x" ) - (Double) goal.getProperty( "x" );
                double dy = (Double) node.getProperty( "y" ) - (Double) goal.getProperty( "y" );
                double result = Math.sqrt( Math.pow( dx, 2 ) + Math.pow( dy, 2 ) );
                return result;
            }
        };
        PathFinder<WeightedPath> astar = GraphAlgoFactory.aStar(
                PathExpanders.allTypesAndDirections(),
                CommonEvaluators.doubleCostEvaluator( "length" ), estimateEvaluator );
        WeightedPath path = astar.findSinglePath( nodeA, nodeB );
        // END SNIPPET: astarUsage
    }

    public static void deleteFileOrDirectory( File file )
    {
        if ( !file.exists() )
        {
            return;
        }

        if ( file.isDirectory() )
        {
            for ( File child : file.listFiles() )
            {
                deleteFileOrDirectory( child );
            }
        }
        else
        {
            file.delete();
        }
    }
}
