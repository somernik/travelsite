<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 9/16/2017
  Time: 6:28 PM
  To change this template use File | Settings | File Templates.
--%>
<div id="signup" class="modal">
    <div class="modal-content">
        <div class="row">
            <a href="#!" class="modal-action modal-close">
                <i class="material-icons right">close</i></a></div>
        <%@include file="../signup.jsp" %>
    </div>

</div>

<div class="modal modal-fixed-footer" id='iframe_modal'  style='width:98%;background:#ECEFF1;'>
    <div class="modal-content">
        <h5 id='modal-title'></h5>
        <iframe style='width:100%;border:none;'></iframe>
    </div>
    <div class="modal-footer">
        <a href="#!" class="modal-action modal-close waves-effect waves-green btn red">Close</a>
    </div>
</div>


<script>
    $(document).ready(function(){
        // the "href" attribute of the modal trigger must specify the modal ID that wants to be triggered
        $('.modal').modal();
    });
</script>