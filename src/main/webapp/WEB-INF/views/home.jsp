<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<% response.addHeader("Refresh","60"); %>
<html>
<head>
	<title>Home</title>
	<script src="<c:url value="/resources/js/jquery-1.11.0.js" />"></script>
<style type="text/css" title="currentStyle">
    @import "resources/css/demo_page.css"; 
    @import "resources/css/demo_table_jui.css";
    @import "resources/css/smoothness/jquery-ui-1.10.3.custom.min.css"; 
</style>

<script type="text/javascript" src="resources/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="resources/js/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="resources/js/jquery-ui-1.10.3.custom.min.js"></script>

</head>
<body>
<h1>
	Social Feeds
</h1>

<P>  The time on the server is ${serverTime}. </P>

<p><strong>Twitter Feeds from a user with hashtag search</strong></p>
  
<table cellpadding="0" cellspacing="0" border="0" class="display datatable" id="twitterTable">
      <thead>
        <tr>
            <th>Id</th>
            <th>UserName</th>
            <th>Tweet</th>
        </tr>
    </thead>
    <tbody>
    <tr>
        <td colspan="5" class="dataTables_empty">Loading data from server</td>
    </tr>
    </tbody>
    <tfoot>
        <tr>
         <th>Id</th>
         <th>UserName</th>
         <th>Tweet</th>
    </tr>
    </tfoot>
</table>
   
<p><strong>Youtube Feeds from a user with specific channel</strong></p>
<table cellpadding="0" cellspacing="0" border="0" class="display datatable" id="youtubeTable">
      <thead>
        <tr>
            <th>Id</th>
            <th>Title</th>
            <th>Published At</th>
        </tr>
    </thead>
    <tbody>
    <tr>
        <td colspan="5" class="dataTables_empty">Loading data from server</td>
    </tr>
    </tbody>
    <tfoot>
        <tr>
         <th>Id</th>
         <th>Title</th>
         <th>Published At</th>
    </tr>
    </tfoot>
</table>   
   
<p> <strong>Instagram posts from specific user.</strong></p>
<p>
<tr>
	Instagram Picture - ${instauser}
</tr>
</p>


<script type="text/javascript">
$(document).ready(function() {
    $('#twitterTable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sPaginationType": "full_numbers",
        "bLenthChange" : true,
        "iDisplayLength" : 5,
        "bJQueryUI": true,
        "bAutoWidth": true,
        "aoColumns": [
                      { "sWidth": "10%", "sTitle" : "User ID", "mData" : "id" }, 
                      { "sWidth": "35%", "sTitle" : "UserName", "mData" : "userName" }, 
                      { "sWidth": "55%", "sTitle" : "Tweet", "mData" : "tweet" }],
        "bPaginate": true,
        "sAjaxSource": "/feeds/twitter/fetch.json",
        "fnServerData": function ( sSource, aoData, fnCallback ) {
            $.ajax( {
                "dataType": 'json',
                "type": "GET",
                "url": sSource,
                "data": aoData,
                "success": fnCallback
            } );
        }
    } );
    
    
    $('#youtubeTable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sPaginationType": "full_numbers",
        "bLenthChange" : true,
        "iDisplayLength" : 5,
        "bJQueryUI": true,
        "bAutoWidth": true,
        "aoColumns": [
                      { "sWidth": "10%", "sTitle" : "ID", "mData" : "id" }, 
                      { "sWidth": "35%", "sTitle" : "Title", "mData" : "title" }, 
                      { "sWidth": "55%", "sTitle" : "Published At", "mData" : "publishedAt" }],
        "bPaginate": true,
        "sAjaxSource": "/feeds/youtube/fetch.json",
        "fnServerData": function ( sSource, aoData, fnCallback ) {
            $.ajax( {
                "dataType": 'json',
                "type": "GET",
                "url": sSource,
                "data": aoData,
                "success": fnCallback
            } );
        }
    } );
    
} );
</script>

</body>
</html>
