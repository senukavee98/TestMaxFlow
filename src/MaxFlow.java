import java.util.*;
import java.lang.*;
import java.util.LinkedList;

class MaxFlow
{


     static Random random = new Random();
     static int V = random.nextInt(11-4)+4 ; //Number of vertices in graph  -  6

    /* Returns true if there is a path from source 's' to sink
    't' in residual graph. Also fills parent[] to store the
    path */

    //Standard implementation of BFS
    boolean bfs(int rGraph[][], int s, int t, int parent[])
    {
        // Create a visited array and mark all vertices as not
        // visited
        boolean visited[] = new boolean[V];
        for(int i=0; i<V; ++i)
            visited[i]=false;

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s]=-1;

        // Standard BFS Loop
        while (queue.size()!=0)
        {
            int u = queue.poll();   //removing 1st element from the list

            for (int v=0; v < V; v++)
            {
                if (visited[v]==false && rGraph[u][v] > 0)
                {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        // If we reached sink in BFS starting from source, then
        // return true, else false
        return (visited[t] == true);

    }

    // Returns tne maximum flow from s to t in the given graph
    int fordFulkerson(int graph[][], int s)
    {
        int u, v;
        int t = V -1;

       /*  Create a residual graph and fill the residual graph
         with given capacities in the original graph as
         residual capacities in residual graph

         Residual graph where rGraph[i][j] indicates
         residual capacity of edge from i to j (if there
         is an edge. If rGraph[i][j] is 0, then there is
         not)*/


        int rGraph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];

        // This array is filled by BFS and to store path
        int parent[] = new int[V];

        int max_flow = 0; // There is no flow initially

        // Augment the flow while there is path from source to sink
        while (bfs(rGraph, s, t, parent))
        {
            // Find minimum residual capacity of the edges
            // along the path filled by BFS. Or we
            // can say
            // find the maximum flow through the path found.

            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            // Add path flow to overall flow
            max_flow += path_flow;
        }

        // Return the overall flow
        return max_flow;
    }

    // Driver program to test above functions
    public static void main (String[] arg)
    {

        Random r = new Random();
        int newGraph[][] = new int[V][V];


        for (int i =0; i<V ;i++){

            System.out.println("\n");
            System.out.print("Path Num " + (i+1) + " : " );
            for (int j= 0;j<V;j++){

                newGraph[i][j] = r.nextInt(21);
                System.out.print("  ->  " + newGraph[i][j]);
                System.out.print("");

            }
        }


        MaxFlow m = new MaxFlow();

        int value = r.nextInt(11-4)+4;
        //int sink = value;

        System.out.println();
        System.out.println();
        System.out.println(" ---------------------------------------------------- ");
        System.out.println(" Source Node : " + 0);
        System.out.println(" Sink Node   : " + value);

        System.out.println(" ---------------------------------------------------- ");
        System.out.println("     The maximum possible flow is " +
                m.fordFulkerson(newGraph, 0));

        System.out.println(" ---------------------------------------------------- ");



        //******************************************************************************************************//


    }
}
