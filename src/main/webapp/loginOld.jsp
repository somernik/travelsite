<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 9/14/2017
  Time: 4:48 PM
  To change this template use File | Settings | File Templates.
--%>
<form class="col s12 m6 offset-m3" ACTION="j_security_check" METHOD="POST">
    <div class="row">
        <div class="input-field col s12">
            <input id="username" type="text" name="j_username" class="validate">
            <label for="username">Username</label>
        </div>
    </div>

    <div class="row">
        <div class="input-field col s12">
            <input id="password" type="password" name="j_password" class="validate">
            <label for="password">Password</label>
        </div>
    </div>

    <button class="btn waves-effect waves-light" type="" onclick="document.getElementById('login').style.display='none'">Cancel</button>
    <button class="btn waves-effect waves-light" type="submit" name="action">Login</button>

</form>
<script>
    $(document).ready(function(){
    // the "href" attribute of the modal trigger must specify the modal ID that wants to be triggered
    $('.modal').modal();
    });
</script>
