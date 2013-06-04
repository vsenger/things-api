<%-- 
    Document   : setup
    Created on : Apr 1, 2013, 6:30:26 PM
    Author     : vsenger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Things Gateway Setup</title>
        <link rel="stylesheet" type="text/css" href="default.css" />

    </head>
    <body>
        <h1>Device List</h1>
        <form action="SetupSave" method="POST">
            <h2>Device 1</h2>
            <p>Name: <input type="text" name="device1Name"/></p>
            <p>Address / Port: <input type="text" name="device1Port"/></p>
            <h2>Device 2</h2>
            <p>Name: <input type="text" name="device2Name"/></p>
            <p>Address / Port: <input type="text" name="device2Port"/></p>
            <h2>Device 3</h2>
            <p>Name: <input type="text" name="device3Name"/></p>
            <p>Address / Port: <input type="text" name="device3Port"/></p>
            <h2>Device 4</h2>
            <p>Name: <input type="text" name="device4Name"/></p>
            <p>Address / Port: <input type="text" name="device4Port"/></p>
            <input type="submit" value="Save"/>
            <input type="reset" value="Reset"/>
        </form>
    </body>
</html>
