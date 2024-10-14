package com.example.lab12024;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.graph4j.Edge;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.stream.Collectors;

@WebServlet
public class Bonus extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        String requestData = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObject = new JSONObject(requestData);
        int numVertices = jsonObject.getInt("numVertices");
        spanningTreeAlgorithm(numVertices, response.getWriter());
        response.setStatus(200);

    }

    public void printGraph(SpanningTree current, PrintWriter writer) {
        int lg = current.nodes.size();
        writer.println("<p>");
        writer.println(" -- " + current.cost);

        writer.print("</p>");

        printTree(constructTreeFromEdges(current.edges), "", true, writer);

    }

    public static TreeNode constructTreeFromEdges(Edge[] edges) {
        Map<Integer, TreeNode> map = new HashMap<>();
        for (Edge edge : edges) {
            map.putIfAbsent(edge.target(), new TreeNode(edge.target()));
            map.putIfAbsent(edge.source(), new TreeNode(edge.source()));
            map.get(edge.target()).children.add(map.get(edge.source()));
        }
        return edges[0].target() <=
                edges[0].source() ?
                map.get(edges[0].target()) : map.get(edges[0].source());
    }

    public static void printTree(TreeNode node, String prefix, boolean isLast, PrintWriter writer) {
        writer.print(prefix + (isLast ? "`-&nbsp;" : "|-&nbsp;") + node.value);
        prefix += isLast ? "&nbsp;&nbsp;" : "|&nbsp;&nbsp;";
        writer.print("<br/>");
        int childCount = node.children.size();
        for (int i = 0; i < childCount; i++) {
            TreeNode child = node.children.get(i);
            boolean childIsLast = i == childCount - 1;
            printTree(child, prefix, childIsLast,writer);
        }
    }


    public void spanningTreeAlgorithm(int nodeVertices, PrintWriter writer) {
        PriorityQueue<SpanningTree> queue = new PriorityQueue<>();
        SpanningTree currentNode = new SpanningTree();
        currentNode.cost = 0;
        currentNode.edges = new Edge[nodeVertices - 1];
        currentNode.nodes = new LinkedHashSet<>();
        currentNode.nodes.add(0);

        queue.add(currentNode);
        while (!queue.isEmpty()) {
            currentNode = queue.poll();
            if (currentNode.nodes.size() == nodeVertices) {
                printGraph(currentNode, writer);
                continue;
            }

            for (int j : currentNode.nodes) {
                for (int i=1; i< nodeVertices; i++)
                    if (!currentNode.nodes.contains(i)  ) {
                        SpanningTree otherNode = currentNode.clone();
                        otherNode.nodes.add(i);
                        otherNode.cost += i + j;
                        otherNode.edges[currentNode.nodes.size() - 1] = new Edge(i, j, i + j);
                        queue.add(otherNode);
                    }
                }
            }
    }

}

class TreeNode {
    int value;
    List<TreeNode> children;

    public TreeNode(int value) {
        this.value = value;
        this.children = new ArrayList<>();
    }
}


class SpanningTree implements Comparable<SpanningTree> {
    Set<Integer> nodes;
    Edge[] edges;
    int cost;
    int lastNode;

    public SpanningTree clone() {
        SpanningTree cloneObject = new SpanningTree();
        cloneObject.nodes = new LinkedHashSet<>();
        cloneObject.nodes.addAll(this.nodes);
        cloneObject.edges = Arrays.copyOf(this.edges, edges.length);
        cloneObject.cost = this.cost;

        return cloneObject;
    }

    @Override
    public int compareTo(SpanningTree o) {
        return this.cost - o.cost;
    }
}