
function saveProductData(){

    alert("inside SaveProductData ... to add the product");

    var imageData = new FormData('#productForm');

    var productData = JSON.stringify(serializeObject($('#productForm')));


    $.ajax({
        url: '/addProductImage',
        type: 'POST',
        enctype: 'multipart/form-data',
        // dataType: "json",
        data: imageData,
        contentType: false,
        processType: false,
        success: function(productImageJson){
            lert("success inside adding image");
            productData.productImage.imageURL = productImageJson.imageURL;

        },

        error: function(erroObject){
            alert("Error in uploading file");


        }

    });

    $.ajax({
        url: '/addProduct',
        type: 'POST',
        dataType: 'json',
        data: productData,
        contentType: 'application/json',
        success: function(productObject){
            alert("inside addProduct ajax");
            $('#success').html("");
            $("#success").append( "<H3 align='center' class='btn-success'>Your are Succesfully added a " + productObject.category.name + "<H3>");
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

