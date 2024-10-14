package com.example.lab12024;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.NumericEntityEscaper;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@WebServlet
public class HelloApplication extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObject = new JSONObject(requestData);
        String inputString = jsonObject.getString("text");
        NumericEntityEscaper numericEntityEscaper = NumericEntityEscaper.between(0, Integer.MAX_VALUE);  // Create the escaper

        ArrayList<String> htmlCodes = new ArrayList<>();
        for (char c : inputString.toCharArray()) {
            String htmlEntityCode = numericEntityEscaper.translate(Character.toString(c));  // Get HTML entity code for each character
             // Append the HTML entity code to the result
            htmlCodes.add(StringEscapeUtils.escapeHtml4(htmlEntityCode) + " " + c);
        }
        Collections.sort(htmlCodes);

        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().println("<ol><li>" + String.join("</li><li>", htmlCodes) + "</li></ol>");
            response.setStatus(200);
        } catch (IOException e) {
            response.setStatus(400);
            throw new RuntimeException(e);
        }
    }
}