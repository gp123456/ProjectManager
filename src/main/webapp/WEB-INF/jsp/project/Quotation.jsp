<%-- 
    Document   : Quotation
    Created on : Mar 11, 2016, 12:51:28 AM
    Author     : antonia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <br><h3>Select Location</h3>
        <table>
            <tbody>
                <tr>
                    <td>
                        <label class="custom-select">
                            <select id="location" onchange="changeLocationBitMaterialService()"></select>
                        </label>
                    </td>
                    <td>
                        <input type="button" class="button" value="Replace Item(s)" onclick="replaceBillMaterialService()"/>
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
