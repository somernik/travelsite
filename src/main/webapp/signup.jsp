<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 9/14/2017
  Time: 4:48 PM
  To change this template use File | Settings | File Templates.
--%>

<form class="col s12 m6 offset-m3">
    <div class="row">
        <div class="input-field col s6">
            <input id="first_name" type="text" class="validate">
            <label for="first_name">First Name</label>
        </div>
        <div class="input-field col s6">
            <input id="last_name" type="text" class="validate">
            <label for="last_name">Last Name</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input id="password" type="password" class="validate">
            <label for="password">Password</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input id="passwordCheck" type="password" class="validate">
            <label data-error="Does Not Match Password" data-success="Matches Password!" for="passwordCheck">Confirm Password</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input id="email" type="email" class="validate">
            <label for="email">Email</label>
        </div>
    </div>
    <button class="btn waves-effect waves-light" type="">Cancel</button>
    <button class="btn waves-effect waves-light" type="submit" name="action">Sign Up</button>
</form>
<script>
    $(document).ready(function(){
        // the "href" attribute of the modal trigger must specify the modal ID that wants to be triggered
        $('.modal').modal();
    });
</script>
