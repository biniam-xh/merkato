
function saveUserData(){

    var userData = JSON.stringify(serializeObject($('#userForm')));

    $.ajax({
        url: '/add',
        type: 'POST',
        dataType: "json",
        data: userData,
        contentType: 'application/json',
        success: function(userObject){
            $('#success').html("");
            $("#success").append( '<H3 align="center" class="btn-success">Your are Succesfully registered! <H3>');
            $('#success').append("<H4 align='center'>First Name:  " + userObject.firstName + "</h4>"  );
            $('#success').append("<H4 align='center'>Last Name: " + userObject.lastName  + "</h4>" );
            $('#success').append("<H4 align='center'>Email: " + userObject.email  + "</h4>");
            $('#success').append("<H4 align='center'>Registered as: " + userObject.role  + "</h4>");
            $("#success").append('<h4 align="center"> <input type="button" value="CLOSE" class="btn-danger" onclick="toggle_visibility(\'success\');"></h4>');
            $('#success').show();
            $('#errors').hide();
        },

        error: function(erroObject){

            if (erroObject.responseJSON.errorType == "someErrorType") {
                $('#errors').html("");
                $("#errors").append( '<H3 align="center"> You provided invalid input: <H3>');
                $("#errors").append( '<p>');

                var errorList = erroObject.responseJSON.errors;
                $.each(errorList,  function(i,error) {
                    $("#errors").append( error.message + '<br>');
                });
                $("#errors").append( '</p>');
                $('#errors').show();
            }
            else {
                alert(erroObject.responseJSON.message);
            }
        }

    });
}

toggle_visibility = function(id) {
    var e = document.getElementById(id);
    resetForm('userForm');
    if(e.style.display == 'block')
        e.style.display = 'none';
    else
        e.style.display = 'block';
}

make_hidden = function(id) {
    var e = document.getElementById(id);
    e.style.display = 'none';
}

make_visible = function(id) {
    var e = document.getElementById(id);
    e.style.display = 'block';
}

resetForm = function(id) {
    var e = document.getElementById(id);
    $(e)[0].reset();

}

function serializeObject (form){
    var jsonObject = {};
    var array = form.serializeArray();
    $.each(array, function() {
        jsonObject[this.name] = this.value;
    });
    return jsonObject;

}

