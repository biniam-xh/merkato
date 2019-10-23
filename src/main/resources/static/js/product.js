
function deleteProduct(){

    alert("inside SaveProductData ... to add the product");

    var productData = JSON.stringify(serializeObject($('#productForm')));

    $.ajax({
        url: '/addProduct',
        type: 'POST',
        dataType: 'json',
        data: productData,
        contentType: 'application/json',
        success: function(productObject){
            alert("inside addProduct ajax");
            $('#success').html("");
            $("#success").append( "<H3 align='center' class='btn-success'>Your are Succesfully deleted a product " + "<H3>");
            $('#success').show();
            $('#errors').hide();
        },

        error: function(erroObject){

            if (erroObject.responseJSON.errorType == "someErrorType") {
                $('#errors').html("");
                $("#errors").append( '<H3 align="center"> Deleting a product is not successful <H3>');
                $("#errors").append( '<p>');

                var errorList = erroObject.responseJSON.errors;
                $.each(errorList,  function(i,error) {
                    $("#errors").append( error.message + '<br>');
                });
                $("#errors").append( '</p>');
                $('#errors').show();
            }
            else {
                // alert(erroObject.responseJSON.message);
                alert("I think error has happened!");
            }
        }

    });
}

toggle_visibility = function(id) {
    var e = document.getElementById(id);
    resetForm('productForm');
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

