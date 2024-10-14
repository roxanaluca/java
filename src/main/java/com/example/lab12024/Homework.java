package com.example.lab12024;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.graph4j.generators.GraphGenerator;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.Locale;


@WebServlet
public class Homework extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Homework.class.getName());


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().collect(Collectors.joining());

        logger.info(request.getMethod() +
                " - " + request.getRemoteAddr() +
                " " + request.getHeader("User-Agent") +
                " " + Collections.list(request.getLocales()).stream().map(Locale::getLanguage).distinct().collect(Collectors.joining(", ")) +
                " " + requestData);
        response.setContentType("text/html;charset=UTF-8");

        JSONObject jsonObject = new JSONObject(requestData);
        int numVertices = jsonObject.getInt("numVertices");
        int numEdges = jsonObject.getInt("numEdges");

        Graph graph;
        try {
            graph = GraphGenerator.randomGnm(numVertices,numEdges);
        } catch (Exception e) {
            response.getWriter().println(e.getMessage());
            response.setStatus(400);
            return;
        }
        try {
            PrintWriter writer = response.getWriter();
            writer.println("<table> <tr><th></th>" );
            IntStream.rangeClosed(1, numVertices).forEach(
                (i) -> writer.println("<th>" + i + "</th>")
            );
            writer.println("</tr>");
            int[][] nodes = graph.adjacencyMatrix();
            for (int i = 0; i < numVertices; i++) {
                writer.println("<tr><th>" + (i+ 1) + "</th>" );
                Arrays.stream(nodes[i]).forEach(
                        (j) -> writer.println("<td>"+ j +"</td>")
                );
                writer.println("</tr>");
            }
            writer.println("</table>");
            response.setStatus(200);
        } catch (IOException e) {
            response.setStatus(400);
            throw new RuntimeException(e);
        }
    }
}
