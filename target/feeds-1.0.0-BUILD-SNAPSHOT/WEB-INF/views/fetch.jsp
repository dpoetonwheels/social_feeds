<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
 
<style type="text/css" title="currentStyle">
    @import "resources/css/demo_page.css"; 
    @import "resources/css/demo_table_jui.css";
    @import "resources/css/smoothness/jquery-ui-1.10.3.custom.min.css"; 
</style>
 
<body id="dt_example">
<div id="container">
    <div class="full_width big">
        user administration
    </div>
 
    <div id="demo">
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="userinfo">
        <thead>
            <tr>
                <th>Id</th>
                <th>Tweet</th>
                <th>Since Id</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
        <tfoot>
            <tr>
                <th>Id</th>
                <th>Tweet</th>
                <th>Since Id</th>
            </tr>
        </tfoot>      
         
        </table>
    </div>
 
         
</div> 
 
<script type="text/javascript" src="resources/js/jquery-1.11.0-.min.js"></script>
<script type="text/javascript" src="resources/js/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="resources/js/query-ui-1.10.3.custom.min.js"></script>

<script type="text/javascript">
 
$(document).ready( function () {
    $('#userinfo').dataTable( {
        "bJQueryUI": true,
        "sDom": '<"H"Tfr>t<"F"ip>',
        "sPaginationType": "full_numbers",
        "bProcessing": true,
        "sAjaxSource": 'feeds/fetch.json',
        "aoColumnDefs": [
                         { "bVisible": false,  "aTargets": [ 1 ] },
                         {
                           "fnRender": function ( oObj,sVal ) {
                             return '<a href="feeds/fetch?' + sVal + '">edit</a>';
                           },
                           "bUseRendered": false,
                           "aTargets": [ 3 ]
                         }
                       ]        
    } );
} );    
  

</script>
</html>